package net.tracen.umapyoi.utils;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.training.SupportStack;

public class TrainingSupportUtils {
    public static ListTag serializeNBT(List<SupportStack> factors) {
        ListTag result = new ListTag();

        for (SupportStack factor : factors) {
            SupportStack.CODEC.encodeStart(NbtOps.INSTANCE, factor)
                    .resultOrPartial(error -> Umapyoi.getLogger().error("Failed to encode SupportStack : {}", error))
                    .ifPresent(tag -> result.add(tag));
        }
        return result;
    }

    public static List<SupportStack> deserializeNBT(CompoundTag compound) {
        List<SupportStack> list = Lists.newArrayList();

        compound.getList("supports", Tag.TAG_COMPOUND).forEach(tag -> {
            SupportStack.CODEC.parse(NbtOps.INSTANCE, tag).resultOrPartial(error -> {
                Umapyoi.getLogger().error("Failed to parse SupportStack : {}", error);
            }).ifPresent(result -> list.add(result));
        });

        return list;
    }

    public static Component getTranslatedSupportCardName(ResourceLocation name) {
        return Component.translatable(Util.makeDescriptionId("support_card", name) + ".name");
    }
}
