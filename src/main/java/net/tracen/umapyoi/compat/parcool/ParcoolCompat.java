package net.tracen.umapyoi.compat.parcool;

import com.alrex.parcool.api.Attributes;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.curios.UmaSoulCuriosWrapper;
import net.tracen.umapyoi.effect.MobEffectRegistry;
import net.tracen.umapyoi.events.ApplyUmasoulAttributeEvent;
import net.tracen.umapyoi.events.SettingPropertyEvent;
import net.tracen.umapyoi.registry.umadata.Growth;
import net.tracen.umapyoi.utils.UmaSoulUtils;
import net.tracen.umapyoi.utils.UmaStatusUtils.StatusType;

public class ParcoolCompat {
	
	@SubscribeEvent
	public static void onApplyAttribute(ApplyUmasoulAttributeEvent event) {
		
		var user = event.getSlotContext().entity();
		if(!(user instanceof Player)) return;
		boolean hasFatique = user.hasEffect(MobEffectRegistry.SLOW_METABOLISM.get());
		var map = event.getAttributes();
		map.put(Attributes.MAX_STAMINA.get(),
				new AttributeModifier(event.getUUID(), "stamina_health_bonus",
                getExactProperty(user, event.getUmaSoul(), 
                		StatusType.STAMINA, UmapyoiConfig.UMASOUL_MAX_STAMINA_HEALTH.get()) 
                * (hasFatique ? 1.05 : 1),
                UmapyoiConfig.UMASOUL_STAMINA_PRECENT_ENABLE.get() ? AttributeModifier.Operation.MULTIPLY_TOTAL
                        : AttributeModifier.Operation.ADDITION));
	}
	
    public static double getExactProperty(LivingEntity user, ItemStack stack, StatusType status, double limit) {
    	int num = status.getId();
        var retiredValue = UmaSoulUtils.getGrowth(stack) == Growth.RETIRED ? 1.0D : 0.25D;
        var propertyRate = 1.0D + (UmaSoulUtils.getPropertyRate(stack)[num] / 100.0D);
        var totalProperty = propertyPercentage(stack, num);
        SettingPropertyEvent event = new SettingPropertyEvent(user, stack, retiredValue, propertyRate, totalProperty, status);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getResultProperty() * limit;
    }

	private static double propertyPercentage(ItemStack stack, int num) {
        var x = UmaSoulUtils.getProperty(stack)[num];
        return UmaSoulCuriosWrapper.propertyPercentageByValue(x);
	}

}
