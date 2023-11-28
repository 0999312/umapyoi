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
    public boolean applySupport(ItemStack soul, SupportStack stack) {
        if (UmaSoulUtils.getMaxProperty(soul)[statusType.getId()] > UmaSoulUtils.getProperty(soul)[statusType
                .getId()]) {
            UmaSoulUtils.getProperty(soul)[statusType.getId()] = Math.min(
                    UmaSoulUtils.getMaxProperty(soul)[statusType.getId()],
                    UmaSoulUtils.getProperty(soul)[statusType.getId()] + stack.getLevel());
            return true;
        }
        return false;
    }

}
