package net.tracen.umapyoi.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.tracen.umapyoi.registry.UmaFactorRegistry;
import net.tracen.umapyoi.registry.factors.FactorData;
import net.tracen.umapyoi.registry.factors.UmaFactorStack;

public class UmaFactorUtils {
    public static List<FactorData> serializeData(List<UmaFactorStack> factors) {
    	List<FactorData> result = new ArrayList<FactorData>();

        factors.forEach(factor->result.add(new FactorData(UmaFactorRegistry.REGISTRY.getKey(factor.getFactor()),
        		factor.getLevel(), Optional.ofNullable(factor.getTag()))));
        return result;
    }

    public static List<UmaFactorStack> deserializeData(List<FactorData> datas) {
        List<UmaFactorStack> list = Lists.newArrayList();

        datas.forEach(data->list.add(new UmaFactorStack(UmaFactorRegistry.REGISTRY.get(data.id()), data.level(), data.tag())));

        return list;
    }

    public static UmaFactorStack cloneWithLevel(UmaFactorStack orig, int level, boolean forced) {
        UmaFactorStack stack = new UmaFactorStack(orig.getFactor(), forced ? level : Mth.clamp(level, 1, orig.getFactor().getMaxLevel()));
        stack.setTag(orig.getTag());
        return stack;
    }

    public static UmaFactorStack merge(UmaFactorStack left, UmaFactorStack right) {
        assert left.getFactor().withStackEquals(left, right);
        CompoundTag tagLeft = left.getOrCreateTag().copy();
        CompoundTag finalTag = tagLeft.merge(right.getOrCreateTag());
        int level = Math.min(
                left.getLevel() == right.getLevel() ? left.getLevel() + 1 : (Math.max(left.getLevel(), right.getLevel())),
                left.getFactor().getMaxLevel()
        );
        UmaFactorStack rStack = new UmaFactorStack(left.getFactor(), level);
        rStack.setTag(finalTag);
        return rStack;
    }
}
