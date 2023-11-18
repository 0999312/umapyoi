package net.tracen.umapyoi.registry.factors;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.utils.UmaSoulUtils;
import net.tracen.umapyoi.utils.UmaStatusUtils.StatusType;

public class StatusFactor extends UmaFactor {

    private final StatusType statusType;

    public StatusFactor(StatusType status) {
        super(FactorType.STATUS);
        this.statusType = status;
    }

    public StatusType getStatusType() {
        return statusType;
    }
    
    @Override
    public void applyFactor(ItemStack soul, UmaFactorStack stack) {
        UmaSoulUtils.getMaxProperty(soul)[statusType.getId()] += stack.getLevel();
        int maxStatusLevel = UmaSoulUtils.getMaxProperty(soul)[statusType.getId()];
        int statusLevel = UmaSoulUtils.getProperty(soul)[statusType.getId()];
        UmaSoulUtils.getProperty(soul)[statusType.getId()] = Math.min(maxStatusLevel, statusLevel + stack.getLevel());
    }

    @Override
    public Component getDescription(UmaFactorStack stack) {
        return this.getFullDescription(stack.getLevel());
    }

}
