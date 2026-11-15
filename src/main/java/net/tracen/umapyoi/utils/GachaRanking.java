package net.tracen.umapyoi.utils;

import com.mojang.serialization.Codec;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;

public enum GachaRanking {
    R, SR, SSR, EASTER_EGG;

    public static final Codec<GachaRanking> CODEC = Codec.STRING
            .xmap(string -> GachaRanking.valueOf(string.toUpperCase()), instance -> instance.name().toLowerCase());
    
    public static GachaRanking getGachaRanking(ItemStack stack) {
        return !stack.has(DataComponentsTypeRegistry.GACHA_RANKING) ? GachaRanking.R
                : stack.get(DataComponentsTypeRegistry.GACHA_RANKING).ranking();
    }
}
