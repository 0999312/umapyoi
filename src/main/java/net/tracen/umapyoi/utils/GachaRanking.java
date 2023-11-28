package net.tracen.umapyoi.utils;

import com.mojang.serialization.Codec;

import net.minecraft.world.item.ItemStack;

public enum GachaRanking {
    R, SR, SSR, EASTER_EGG;

    public static final Codec<GachaRanking> CODEC = Codec.STRING
            .xmap(string -> GachaRanking.valueOf(string.toUpperCase()), instance -> instance.name().toLowerCase());
    
    public static GachaRanking getGachaRanking(ItemStack stack) {
        return stack.getOrCreateTag().getString("ranking").isBlank() ? GachaRanking.R
                : GachaRanking.valueOf(stack.getOrCreateTag().getString("ranking").toUpperCase());
    }
}
