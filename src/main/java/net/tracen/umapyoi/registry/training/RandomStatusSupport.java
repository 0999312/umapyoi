package net.tracen.umapyoi.registry.training;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.utils.UmaSoulUtils;

import java.util.stream.IntStream;

public class RandomStatusSupport extends TrainingSupport {

    public RandomStatusSupport() {
        super();
    }

    @Override
    public boolean applySupport(ItemStack soul, RandomSource rand, SupportStack stack) {
		boolean hasApply = false;
		int[] originalProperty = UmaSoulUtils.getProperty(soul);
		int[] maxProperty = UmaSoulUtils.getMaxProperty(soul);
		for (int i = 0; i < stack.getLevel(); i++) {
			int[] available = IntStream.range(0, 5).filter(j -> originalProperty[j] < maxProperty[j]).toArray();
			if (available.length == 0) break;
			int id = available[rand.nextInt(available.length)];
			originalProperty[id] = Math.min(
					maxProperty[id],
					originalProperty[id] + stack.getLevel());
			hasApply = true;
		}
    	return hasApply;
    }

}
