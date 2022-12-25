package net.tracen.umapyoi.curios;

import java.util.UUID;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

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
import net.tracen.umapyoi.api.UmaStatusUtils.StatusType;
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
        if (!cap.isSkillReady()) {
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
            UmaStatus umaStatus = cap.getUmaStatus();
            float reduction = getStaminaExhaustion(umaStatus.property()[StatusType.STRENGTH.getId()]) * umaStatus.getMotivation().getMultiplier();
            if (exhaustion > 0.01F) {
                player.causeFoodExhaustion(-reduction);
            }
        }
    }

    public float getStaminaExhaustion(int stamina) {
        return Math.max(1, stamina) * 0.00075f;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid) {
        IUmaCapability cap = this.getStack().getCapability(CapabilityRegistry.UMACAP).orElse(new UmaCapability(getStack()));
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        CuriosApi.getCuriosHelper().addSlotModifier(atts, "uma_suit", uuid, 1.0, AttributeModifier.Operation.ADDITION);
        
        if(cap.getUmaStatus().getGrowth() == UmaStatus.Growth.UNTRAINED) 
            return atts;
        
        atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "speed_bonus",
                getBounsValue(cap, StatusType.SPEED.getId(), 0.1), AttributeModifier.Operation.MULTIPLY_TOTAL));
        atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "strength_attack_bonus",
                getBounsValue(cap, StatusType.STRENGTH.getId(), 0.2), AttributeModifier.Operation.MULTIPLY_TOTAL));
        atts.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "strength_attack_bonus",
                getBounsValue(cap, StatusType.STAMINA.getId(), 2), AttributeModifier.Operation.ADDITION));
        atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "guts_armor_bonus",
                getBounsValue(cap, StatusType.GUTS.getId(), 1), AttributeModifier.Operation.ADDITION));
        atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "guts_armor_toughness_bonus",
                getBounsValue(cap, StatusType.GUTS.getId(), 2), AttributeModifier.Operation.ADDITION));
        return atts;
    }

    private double getBounsValue(IUmaCapability cap, int num, double multiply) {
        
        UmaStatus umaStatus = cap.getUmaStatus();

        return umaStatus.getMotivation().getMultiplier() * (Math.max(1, umaStatus.property()[num]) * multiply);
    }
}