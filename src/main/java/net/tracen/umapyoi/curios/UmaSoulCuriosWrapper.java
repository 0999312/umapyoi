package net.tracen.umapyoi.curios;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForge;
import net.tracen.umapyoi.UmapyoiConfig;
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
        return new SoundInfo(SoundEvents.ARMOR_EQUIP_LEATHER.value(), 1.0f, 1.0f);
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
        if (!commandSenderWorld.isClientSide()) {
            resumeActionPoint(entity);
        }

    }

    private void resumeActionPoint(LivingEntity entity) {
        if (NeoForge.EVENT_BUS.post(new ResumeActionPointEvent(entity, stack)).isCanceled())
            return;
        if (UmaSoulUtils.getActionPoint(this.getStack()) != UmaSoulUtils.getMaxActionPoint(this.getStack())) {
            UmaSoulUtils.setActionPoint(this.getStack(), Math.min(UmaSoulUtils.getActionPoint(this.getStack()) + 1,
                    UmaSoulUtils.getMaxActionPoint(this.getStack())));
        }
    }
    
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext,
    		ResourceLocation id) {
    	 Multimap<Holder<Attribute>, AttributeModifier> atts = LinkedHashMultimap.create();
         if (!slotContext.identifier().equalsIgnoreCase("uma_soul"))
             return atts;
         CuriosApi.addSlotModifier(atts, "uma_suit", id, 1.0, AttributeModifier.Operation.ADD_VALUE);
         if (UmaSoulUtils.getGrowth(getStack()) == Growth.UNTRAINED)
             return atts;
         atts.put(Attributes.MOVEMENT_SPEED,
                 new AttributeModifier(id, getExactProperty(StatusType.SPEED.getId(),
                		 UmapyoiConfig.UMASOUL_MAX_SPEED.get()),
                         UmapyoiConfig.UMASOUL_SPEED_PRECENT_ENABLE.get() ? AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                                 : AttributeModifier.Operation.ADD_VALUE));
         
         atts.put(Attributes.SNEAKING_SPEED,
                 new AttributeModifier(id, getExactProperty(StatusType.SPEED.getId(), 
                		 UmapyoiConfig.UMASOUL_MAX_SPEED.get()),
                         UmapyoiConfig.UMASOUL_SPEED_PRECENT_ENABLE.get() ? AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                                 : AttributeModifier.Operation.ADD_VALUE));
         
         atts.put(Attributes.ATTACK_DAMAGE,
                 new AttributeModifier(id, getExactProperty(StatusType.STRENGTH.getId(),
                		 UmapyoiConfig.UMASOUL_MAX_STRENGTH_ATTACK.get()),
                         UmapyoiConfig.UMASOUL_STRENGTH_PRECENT_ENABLE.get() ? AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                                 : AttributeModifier.Operation.ADD_VALUE));
         atts.put(Attributes.MAX_HEALTH,
                 new AttributeModifier(id, getExactProperty(StatusType.STAMINA.getId(),
                		 UmapyoiConfig.UMASOUL_MAX_STAMINA_HEALTH.get()),
                         UmapyoiConfig.UMASOUL_STAMINA_PRECENT_ENABLE.get() ? AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                                 : AttributeModifier.Operation.ADD_VALUE));
         atts.put(Attributes.ARMOR,
                 new AttributeModifier(id, getExactProperty(StatusType.GUTS.getId(),
                		 UmapyoiConfig.UMASOUL_MAX_GUTS_ARMOR.get()),
                         UmapyoiConfig.UMASOUL_GUTS_PRECENT_ENABLE.get() ? AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                                 : AttributeModifier.Operation.ADD_VALUE));
         atts.put(Attributes.ARMOR_TOUGHNESS,
                 new AttributeModifier(id, getExactProperty(StatusType.GUTS.getId(),
                		 UmapyoiConfig.UMASOUL_MAX_GUTS_ARMOR_TOUGHNESS.get()),
                         UmapyoiConfig.UMASOUL_GUTS_PRECENT_ENABLE.get() ? AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                                 : AttributeModifier.Operation.ADD_VALUE));

         return atts;
    }


    public double getExactProperty(int num, double limit) {
        var retiredValue = UmaSoulUtils.getGrowth(getStack()) == Growth.RETIRED ? 1.0D : 0.25D;
        var propertyRate = 1.0D + (UmaSoulUtils.getPropertyRate(this.getStack())[num] / 100.0D);
        var totalProperty = propertyPercentage(num);
        return UmaSoulUtils.getMotivation(this.getStack()).getMultiplier() * limit * propertyRate * retiredValue * totalProperty;
    }

    private double propertyPercentage(int num) {
        var x = UmaSoulUtils.getProperty(this.getStack())[num];
        var statLimit = UmapyoiConfig.STAT_LIMIT_VALUE.get();
        var denominator = 1 + Math.pow(Math.E, 
                (x > statLimit ? (-0.125 * UmapyoiConfig.STAT_LIMIT_REDUCTION_RATE.get()) : -0.125) * 
                (x - statLimit));
        return 1 / denominator;
    }
}