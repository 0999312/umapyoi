package net.tracen.umapyoi.events;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class SettingPropertyEvent extends Event {
    private final LivingEntity entity;
    private final ItemStack soul;
    private double retiredValue;
    private double propertyRate;
    private double propertyPercentage;
    private double resultProperty;

    public SettingPropertyEvent(LivingEntity entity, ItemStack soul, double retiredValue, double propertyRate, double propertyPercentage) {
        this.entity = entity;
        this.soul = soul;
        this.retiredValue = retiredValue;
        this.propertyRate = propertyRate;
        this.propertyPercentage = propertyPercentage;
        this.setResultProperty(UmaSoulUtils.getMotivation(soul).getMultiplier() * propertyRate * retiredValue * propertyPercentage);
    }

    public LivingEntity getLivingEntity() {
        return entity;
    }

    public ItemStack getUmaSoul() {
        return this.soul;
    }

	public double getPropertyPercentage() {
		return propertyPercentage;
	}

	public void setPropertyPercentage(double propertyPercentage) {
		this.propertyPercentage = propertyPercentage;
	}

	public double getRetiredValue() {
		return retiredValue;
	}

	public void setRetiredValue(double retiredValue) {
		this.retiredValue = retiredValue;
	}

	public double getPropertyRate() {
		return propertyRate;
	}

	public void setPropertyRate(double propertyRate) {
		this.propertyRate = propertyRate;
	}

	public double getResultProperty() {
		return resultProperty;
	}

	public void setResultProperty(double resultProperty) {
		this.resultProperty = resultProperty;
	}
}
