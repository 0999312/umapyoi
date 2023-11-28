package net.tracen.umapyoi.registry.training;

import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class ExtraStatusSupport extends TrainingSupport {
    private final int statusType;

    public ExtraStatusSupport(int status) {
        super();
        this.statusType = status;
    }

    @Override
    public boolean applySupport(ItemStack soul, SupportStack stack) {
        if (this.statusType == 3)
            UmaSoulUtils.getExtraProperty(soul)[statusType] += stack.getLevel() * 100;
        else
            UmaSoulUtils.getExtraProperty(soul)[statusType] += stack.getLevel();
        return true;
    }

}
