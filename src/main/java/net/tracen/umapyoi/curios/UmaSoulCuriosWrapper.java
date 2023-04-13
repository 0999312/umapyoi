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
import net.minecraftforge.common.MinecraftForge;
import net.tracen.umapyoi.events.ResumeActionPointEvent;
import net.tracen.umapyoi.registry.umadata.Growth;
import net.tracen.umapyoi.utils.UmaSoulUtils;
import net.tracen.umapyoi.utils.UmaStatusUtils.StatusType;
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
        if (!slotContext.identifier().equalsIgnoreCase("uma_soul"))
            return;
        LivingEntity entity = slotContext.entity();
        Level commandSenderWorld = entity.getCommandSenderWorld();
        if (this.getStack().isEmpty())
            return;
        if (!commandSenderWorld.isClientSide && entity instanceof Player player) {
            applyStaminaEffect(player);
            resumeActionPoint(entity);
        }

    }

    private void resumeActionPoint(LivingEntity entity) {
        if (MinecraftForge.EVENT_BUS.post(new ResumeActionPointEvent(entity, stack)))
            return;
        if (UmaSoulUtils.getActionPoint(this.getStack()) != UmaSoulUtils.getMaxActionPoint(this.getStack())) {
            UmaSoulUtils.setActionPoint(this.getStack(), Math.min(UmaSoulUtils.getActionPoint(this.getStack()) + 1,
                    UmaSoulUtils.getMaxActionPoint(this.getStack())));
        }
    }

    private void applyStaminaEffect(Player player) {
        FoodData foodData = player.getFoodData();
        boolean isPlayerHealingWithSaturation = player.level.getGameRules()
                .getBoolean(GameRules.RULE_NATURAL_REGENERATION) && player.isHurt()
                && foodData.getSaturationLevel() > 0.0F && foodData.getFoodLevel() >= 20;
        if (!isPlayerHealingWithSaturation) {
            float exhaustion = foodData.getExhaustionLevel();
            float reduction = getStaminaExhaustion(
                    UmaSoulUtils.getProperty(this.getStack())[StatusType.STRENGTH.getId()])
                    * UmaSoulUtils.getMotivation(this.getStack()).getMultiplier();
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
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        if (!slotContext.identifier().equalsIgnoreCase("uma_soul"))
            return atts;
        CuriosApi.getCuriosHelper().addSlotModifier(atts, "uma_suit", uuid, 1.0, AttributeModifier.Operation.ADDITION);
        if (UmaSoulUtils.getGrowth(getStack()) == Growth.UNTRAINED)
            return atts;
        atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "speed_bonus",
                getBounsValue(StatusType.SPEED.getId(), 0.1), AttributeModifier.Operation.MULTIPLY_TOTAL));
        atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "strength_attack_bonus",
                getBounsValue(StatusType.STRENGTH.getId(), 0.2), AttributeModifier.Operation.MULTIPLY_TOTAL));
        atts.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "strength_attack_bonus",
                getBounsValue(StatusType.STAMINA.getId(), 2), AttributeModifier.Operation.ADDITION));
        atts.put(Attributes.ARMOR, new AttributeModifier(uuid, "guts_armor_bonus",
                getBounsValue(StatusType.GUTS.getId(), 1), AttributeModifier.Operation.ADDITION));
        atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "guts_armor_toughness_bonus",
                getBounsValue(StatusType.GUTS.getId(), 2), AttributeModifier.Operation.ADDITION));

        return atts;
    }

    private double getBounsValue(int num, double multiply) {
        return UmaSoulUtils.getMotivation(this.getStack()).getMultiplier()
                * (Math.max(1, UmaSoulUtils.getProperty(this.getStack())[num]) * multiply);
    }
}