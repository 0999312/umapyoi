package net.tracen.umapyoi.curios;

import java.util.UUID;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.events.ApplyUmasoulAttributeEvent;
import net.tracen.umapyoi.events.ResumeActionPointEvent;
import net.tracen.umapyoi.events.SettingPropertyEvent;
import net.tracen.umapyoi.registry.UmapyoiAttributesRegistry;
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
    public boolean canEquip(SlotContext slotContext) {
    	return slotContext.identifier().equals("uma_soul");
    }
    
    @Override
    public boolean canEquipFromUse(SlotContext slotContext) {
    	return slotContext.identifier().equals("uma_soul");
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
        if (this.getStack().isEmpty())
            return;
        if (!slotContext.identifier().equalsIgnoreCase("uma_soul"))
            return;
        
        LivingEntity entity = slotContext.entity();
        Level commandSenderWorld = entity.getCommandSenderWorld();

        if (!commandSenderWorld.isClientSide()) {
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
    
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        LivingEntity user = slotContext.entity();
        if (!slotContext.identifier().equalsIgnoreCase("uma_soul"))
            return atts;
        CuriosApi.addSlotModifier(atts, "uma_suit", uuid, 1.0, AttributeModifier.Operation.ADDITION);
        if (UmaSoulUtils.getGrowth(getStack()) == Growth.UNTRAINED)
            return atts;
        
        atts.put(UmapyoiAttributesRegistry.SPRINT_SPEED.get(),
                new AttributeModifier(uuid, "sprint_speed_running_bonus",
                        getExactProperty(user, StatusType.SPEED, UmapyoiConfig.UMASOUL_MAX_SPEED.get()),
                        UmapyoiConfig.UMASOUL_SPEED_PRECENT_ENABLE.get() ? AttributeModifier.Operation.MULTIPLY_TOTAL
                                : AttributeModifier.Operation.ADDITION));
        
        atts.put(ForgeMod.SWIM_SPEED.get(),
                new AttributeModifier(uuid, "speed_swiming_bonus",
                        getExactProperty(user, StatusType.SPEED, UmapyoiConfig.UMASOUL_MAX_SPEED.get()),
                        UmapyoiConfig.UMASOUL_SPEED_PRECENT_ENABLE.get() ? AttributeModifier.Operation.MULTIPLY_TOTAL
                                : AttributeModifier.Operation.ADDITION));
        
        atts.put(Attributes.ATTACK_DAMAGE,
                new AttributeModifier(uuid, "strength_attack_bonus",
                        getExactProperty(user, StatusType.STRENGTH, UmapyoiConfig.UMASOUL_MAX_STRENGTH_ATTACK.get()),
                        UmapyoiConfig.UMASOUL_STRENGTH_PRECENT_ENABLE.get() ? AttributeModifier.Operation.MULTIPLY_TOTAL
                                : AttributeModifier.Operation.ADDITION));
        atts.put(Attributes.MAX_HEALTH,
                new AttributeModifier(uuid, "strength_attack_bonus",
                        getExactProperty(user, StatusType.STAMINA, UmapyoiConfig.UMASOUL_MAX_STAMINA_HEALTH.get()),
                        UmapyoiConfig.UMASOUL_STAMINA_PRECENT_ENABLE.get() ? AttributeModifier.Operation.MULTIPLY_TOTAL
                                : AttributeModifier.Operation.ADDITION));
        atts.put(Attributes.ARMOR,
                new AttributeModifier(uuid, "guts_armor_bonus",
                        getExactProperty(user, StatusType.GUTS, UmapyoiConfig.UMASOUL_MAX_GUTS_ARMOR.get()),
                        UmapyoiConfig.UMASOUL_GUTS_PRECENT_ENABLE.get() ? AttributeModifier.Operation.MULTIPLY_TOTAL
                                : AttributeModifier.Operation.ADDITION));
        atts.put(Attributes.ARMOR_TOUGHNESS,
                new AttributeModifier(uuid, "guts_armor_toughness_bonus",
                        getExactProperty(user, StatusType.GUTS, UmapyoiConfig.UMASOUL_MAX_GUTS_ARMOR_TOUGHNESS.get()),
                        UmapyoiConfig.UMASOUL_GUTS_PRECENT_ENABLE.get() ? AttributeModifier.Operation.MULTIPLY_TOTAL
                                : AttributeModifier.Operation.ADDITION));

        ApplyUmasoulAttributeEvent event = new ApplyUmasoulAttributeEvent(this.getStack(), slotContext, uuid, atts);
		MinecraftForge.EVENT_BUS.post(event);
        return event.getAttributes();
    }

    public double getExactProperty(LivingEntity user, StatusType status, double limit) {
    	int num = status.getId();
        var retiredValue = UmaSoulUtils.getGrowth(getStack()) == Growth.RETIRED ? 1.0D : 0.25D;
        var propertyRate = 1.0D + (UmaSoulUtils.getPropertyRate(this.getStack())[num] / 100.0D);
        var totalProperty = propertyPercentage(num);
        SettingPropertyEvent event = new SettingPropertyEvent(user, this.getStack(), retiredValue, propertyRate, totalProperty);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getResultProperty() * limit;
    }

    private double propertyPercentage(int num) {
        var x = UmaSoulUtils.getProperty(this.getStack())[num];
        var statLimit = UmapyoiConfig.STAT_LIMIT_VALUE.get();
        var denominator = 1 + Math.pow(Math.E, 
                (x > statLimit ? (-0.125 * UmapyoiConfig.STAT_LIMIT_REDUCTION_RATE.get()) : -0.125) * 
                (x - statLimit)
                );
        return 1 / denominator;
    }
}