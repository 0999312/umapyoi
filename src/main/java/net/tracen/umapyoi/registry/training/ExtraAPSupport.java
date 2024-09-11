package net.tracen.umapyoi.registry.training;

import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.registry.umadata.UmaDataExtraStatus;

public class ExtraAPSupport extends TrainingSupport {

    public ExtraAPSupport() {
        super();
    }

    @Override
    public boolean applySupport(ItemStack soul, SupportStack stack) {
        soul.update(DataComponentsTypeRegistry.UMADATA_EXTRA_STATUS, UmaDataExtraStatus.DEFAULT, data->{
        	return new UmaDataExtraStatus(data.actionPoint(), data.extraActionPoint() + stack.getLevel() * 100, data.resultRanking(), data.motivation());
        });
        return true;
    }

}
