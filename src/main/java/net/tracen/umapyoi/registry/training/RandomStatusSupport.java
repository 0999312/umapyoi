package net.tracen.umapyoi.registry.training;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.registry.umadata.UmaDataBasicStatus;
import net.tracen.umapyoi.utils.UmaSoulUtils;

import java.util.stream.IntStream;

public class RandomStatusSupport extends TrainingSupport {

    public RandomStatusSupport() {
        super();
    }

//    @Override
//    public boolean applySupport(ItemStack soul, RandomSource rand, SupportStack stack) {
//    	for(int i = 0; i < stack.getLevel(); i++) {
//	        int id = rand.nextInt(5);
//			if (UmaSoulUtils.getMaxProperty(soul)[id] > UmaSoulUtils.getProperty(soul)[id]) {
//	            UmaSoulUtils.getProperty(soul)[id] = Math.min(
//	                    UmaSoulUtils.getMaxProperty(soul)[id],
//	                    UmaSoulUtils.getProperty(soul)[id] + stack.getLevel());
//	            return true;
//	        }
//    	}
//		return false;
//    }


    @Override
    public boolean applySupport(ItemStack soul, RandomSource rand, SupportStack stack) {
        boolean hasApply = false;
        for (int i = 0; i < stack.getLevel(); i++) {
            int[] originalProperty = UmaSoulUtils.getProperty(soul).toArray();
            int[] maxProperty = UmaSoulUtils.getMaxProperty(soul).toArray();
            int[] available = IntStream.range(0, 5).filter(j -> originalProperty[j] < maxProperty[j]).toArray();
            if (available.length == 0) break;
            int id = available[rand.nextInt(available.length)];
            originalProperty[id] = Math.min(maxProperty[id], originalProperty[id] + stack.getLevel());
            soul.set(DataComponentsTypeRegistry.UMADATA_BASIC_STATUS, UmaDataBasicStatus.init(originalProperty));
            hasApply = true;
        }
        return hasApply;
    }
}
