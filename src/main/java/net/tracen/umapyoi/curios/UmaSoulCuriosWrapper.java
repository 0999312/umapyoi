package net.tracen.umapyoi.curios;

import java.util.UUID;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import cn.mcmod_mmf.mmlib.utils.MathUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.capability.CapabilityRegistry;
import net.tracen.umapyoi.capability.IUmaCapability;
import net.tracen.umapyoi.capability.UmaCapability;
import net.tracen.umapyoi.registry.umadata.UmaStatus;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class UmaSoulCuriosWrapper implements ICurio {
    private final ItemStack stack;

    public UmaSoulCuriosWrapper(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public SoundInfo getEquipSound(SlotContext slotContext) {
        return new SoundInfo(SoundEvents.ARMOR_EQUIP_LEATHER, 1.0f, 1.0f);
    }

    @Override
    public ItemStack getStack() {
        return stack;
    }

    @Override
    public void curioTick(SlotContext slotContext) {
        LivingEntity entity = slotContext.entity();
        IUmaCapability cap = this.getStack().getCapability(CapabilityRegistry.UMACAP)
                .orElse(new UmaCapability(getStack()));
        Level commandSenderWorld = entity.getCommandSenderWorld();
        if (!commandSenderWorld.isClientSide && entity instanceof Player player) {
            applyStaminaEffect(cap, player);
            reduceCooldown(cap);
        }

    }
    
    private void reduceCooldown(IUmaCapability cap) {
        if(!cap.isSkillReady()) {
            cap.setCooldown(cap.getCooldown() - 1);
        }
    }

    private void applyStaminaEffect(IUmaCapability cap, Player player) {
        FoodData foodData = player.getFoodData();
        boolean isPlayerHealingWithSaturation = player.level.getGameRules()
                .getBoolean(GameRules.RULE_NATURAL_REGENERATION) && player.isHurt()
                && foodData.getSaturationLevel() > 0.0F && foodData.getFoodLevel() >= 20;
        if (!isPlayerHealingWithSaturation) {
            float exhaustion = foodData.getExhaustionLevel();
            float reduction = getStaminaExhaustion(cap.getUmaStatus().property()[UmaStatus.STAMINA],
                    cap.getUmaStatus().maxProperty()[UmaStatus.STAMINA]);
            if (exhaustion > 0.01F) {
                player.causeFoodExhaustion(-reduction);
            }
        }
    }

    public float getStaminaExhaustion(int stamina, int max_stamina) {
        double stam = Math.min(UmapyoiConfig.STAT_LIMIT_VALUE.get().doubleValue(), Math.max(1, stamina));
        double max_stam = Math.min(UmapyoiConfig.STAT_LIMIT_VALUE.get().doubleValue(), Math.max(1, max_stamina));
        return (float) MathUtil.lerp((stam / max_stam), 0.0, 0.0075);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();

        atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "speed_bonus",
                getBounsValue(UmaStatus.SPEED, 0.0025), AttributeModifier.Operation.MULTIPLY_TOTAL));

        atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "strength_attack_bonus",
                getBounsValue(UmaStatus.STRENGTH, 0.001), AttributeModifier.Operation.MULTIPLY_TOTAL));

        atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "guts_armor_toughness_bonus",
                getBounsValue(UmaStatus.GUTS, 0.002), AttributeModifier.Operation.ADDITION));

        CuriosApi.getCuriosHelper().addSlotModifier(atts, "uma_suit", uuid, 1.0, AttributeModifier.Operation.ADDITION);
        return atts;
    }

    private double getBounsValue(int num, double multiply) {
        IUmaCapability cap = this.getStack().getCapability(CapabilityRegistry.UMACAP)
                .orElse(new UmaCapability(getStack()));
        double limit = UmapyoiConfig.STAT_LIMIT_VALUE.get().doubleValue();
        double baseStat = Math.min(limit, Math.max(1, cap.getUmaStatus().property()[num]));
        double extraStat = Math.max(0, cap.getUmaStatus().property()[num] - limit);
        double maxStat = Math.max(1, cap.getUmaStatus().maxProperty()[num]);
        double extraStatLimit = maxStat - limit != 0 ? maxStat - limit : 1;

        return MathUtil.lerp((baseStat / limit), 0.0, multiply * limit) + MathUtil.lerp((extraStat / extraStatLimit),
                0.0, UmapyoiConfig.STAT_LIMIT_REDUCTION_RATE.get() * multiply * extraStatLimit);
    }
}