package net.tracen.umapyoi.registry.training;

import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.utils.UmaSoulUtils;
import net.tracen.umapyoi.utils.UmaStatusUtils.StatusType;

public class StatusSupport extends TrainingSupport {
    private final StatusType statusType;

    public StatusSupport(StatusType status) {
        super();
        this.statusType = status;
    }

    @Override
    public void applySupport(ItemStack soul, SupportStack stack) {
        UmaSoulUtils.getProperty(soul)[statusType.getId()] = Math.min(12,
                UmaSoulUtils.getProperty(soul)[statusType.getId()] + stack.getLevel());
    }

}
