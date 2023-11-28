package net.tracen.umapyoi.utils;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
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
}
