package net.tracen.umapyoi.utils;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Mth;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.factors.UmaFactorStack;

public class UmaFactorUtils {
    public static ListTag serializeNBT(List<UmaFactorStack> factors) {
        ListTag result = new ListTag();

        for (UmaFactorStack factor : factors) {
            UmaFactorStack.CODEC.encodeStart(NbtOps.INSTANCE, factor)
                    .resultOrPartial(error -> Umapyoi.getLogger().error("Failed to encode FactorStack : {}", error))
                    .ifPresent(tag -> result.add(tag));
        }
        return result;
    }

    public static List<UmaFactorStack> deserializeNBT(CompoundTag compound) {
        List<UmaFactorStack> list = Lists.newArrayList();

        compound.getList("factors", Tag.TAG_COMPOUND).forEach(tag -> {
            UmaFactorStack.CODEC.parse(NbtOps.INSTANCE, tag).resultOrPartial(error -> {
                Umapyoi.getLogger().error("Failed to parse FactorStack : {}", error);
            }).ifPresent(result -> list.add(result));
        });

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
