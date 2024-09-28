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
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
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
         atts.put(Attributes.MOVEMENT_SPEED,
                 new AttributeModifier(id, getExactProperty(StatusType.SPEED,
                		 UmapyoiConfig.UMASOUL_MAX_SPEED.get()),
                         UmapyoiConfig.UMASOUL_SPEED_PRECENT_ENABLE.get() ? AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                                 : AttributeModifier.Operation.ADD_VALUE));
        
         atts.put(Attributes.ATTACK_DAMAGE,
                 new AttributeModifier(id, getExactProperty(StatusType.STRENGTH,
                		 UmapyoiConfig.UMASOUL_MAX_STRENGTH_ATTACK.get()),
                         UmapyoiConfig.UMASOUL_STRENGTH_PRECENT_ENABLE.get() ? AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                                 : AttributeModifier.Operation.ADD_VALUE));
         atts.put(Attributes.MAX_HEALTH,
                 new AttributeModifier(id, getExactProperty(StatusType.STAMINA,
                		 UmapyoiConfig.UMASOUL_MAX_STAMINA_HEALTH.get()),
                         UmapyoiConfig.UMASOUL_STAMINA_PRECENT_ENABLE.get() ? AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                                 : AttributeModifier.Operation.ADD_VALUE));
         atts.put(Attributes.ARMOR,
                 new AttributeModifier(id, getExactProperty(StatusType.GUTS,
                		 UmapyoiConfig.UMASOUL_MAX_GUTS_ARMOR.get()),
                         UmapyoiConfig.UMASOUL_GUTS_PRECENT_ENABLE.get() ? AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                                 : AttributeModifier.Operation.ADD_VALUE));
         atts.put(Attributes.ARMOR_TOUGHNESS,
                 new AttributeModifier(id, getExactProperty(StatusType.GUTS,
                		 UmapyoiConfig.UMASOUL_MAX_GUTS_ARMOR_TOUGHNESS.get()),
                         UmapyoiConfig.UMASOUL_GUTS_PRECENT_ENABLE.get() ? AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                                 : AttributeModifier.Operation.ADD_VALUE));

         return atts;
    }


    public double getExactProperty(StatusType type, double limit) {
        var retiredValue = !(this.getStack().has(DataComponentsTypeRegistry.UMADATA_TRAINING)) ? 1.0D : 0.25D;
        int rate = 0;
    	switch (type) {
			case SPEED -> rate = UmaSoulUtils.getPropertyRate(this.getStack()).speed();
			case STAMINA -> rate = UmaSoulUtils.getPropertyRate(this.getStack()).stamina();
			case STRENGTH -> rate = UmaSoulUtils.getPropertyRate(this.getStack()).strength();
			case GUTS -> rate = UmaSoulUtils.getPropertyRate(this.getStack()).guts();
			case WISDOM -> rate = UmaSoulUtils.getPropertyRate(this.getStack()).wisdom();
		}
        var propertyRate = 1.0D + (rate / 100.0D);
        var totalProperty = propertyPercentage(type);
        return UmaSoulUtils.getMotivation(this.getStack()).getMultiplier() * limit * propertyRate * retiredValue * totalProperty;
    }

    private double propertyPercentage(StatusType type) {
    	int x = 0;
    	switch (type) {
			case SPEED -> x = UmaSoulUtils.getProperty(this.getStack()).speed();
			case STAMINA -> x = UmaSoulUtils.getProperty(this.getStack()).stamina();
			case STRENGTH -> x = UmaSoulUtils.getProperty(this.getStack()).strength();
			case GUTS -> x = UmaSoulUtils.getProperty(this.getStack()).guts();
			case WISDOM -> x = UmaSoulUtils.getProperty(this.getStack()).wisdom();
		}
        
        var statLimit = UmapyoiConfig.STAT_LIMIT_VALUE.get();
        var denominator = 1 + Math.pow(Math.E, 
                (x > statLimit ? (-0.125 * UmapyoiConfig.STAT_LIMIT_REDUCTION_RATE.get()) : -0.125) * 
                (x - statLimit));
        return 1 / denominator;
    }
}