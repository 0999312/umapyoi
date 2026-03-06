package net.tracen.umapyoi.registry.training;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.registry.umadata.UmaDataExtraStatus;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class ExtraStatusSupport extends TrainingSupport {
    private final int statusType;

    public ExtraStatusSupport(int status) {
        super();
        this.statusType = status;
    }

    @Override
    public boolean applySupport(ItemStack soul, RandomSource rand, SupportStack stack) {
        switch (this.statusType) {
            case 0:
                UmaSoulUtils.setPhysique(soul, UmaSoulUtils.getPhysique(soul) + stack.getLevel());
                break;
            case 1:
                UmaSoulUtils.setLearningTimes(soul, UmaSoulUtils.getLearningTimes(soul) + stack.getLevel());
                break;
            case 2:
                UmaSoulUtils.setSkillSlots(soul, UmaSoulUtils.getSkillSlots(soul) + stack.getLevel());
                break;
            case 3:
                soul.update(DataComponentsTypeRegistry.UMADATA_EXTRA_STATUS, UmaDataExtraStatus.DEFAULT, data-> new UmaDataExtraStatus(data.actionPoint(), data.extraActionPoint() + stack.getLevel() * 100, data.resultRanking(), data.motivation()));
                break;
            default:
                throw new AssertionError("Unexpected value: " + this.statusType);
        }
        return true;
    }
}
