package net.tracen.umapyoi.utils;

import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public enum RaceRanking {
    DEBUT(ChatFormatting.GREEN),
    PREOP(ChatFormatting.YELLOW),
    OP(ChatFormatting.GOLD),
    GIII(ChatFormatting.DARK_GREEN),
    GII(ChatFormatting.LIGHT_PURPLE),
    GI(ChatFormatting.BLUE);

    public final ChatFormatting color;

    RaceRanking(ChatFormatting color) {
        this.color = color;
    }

    public static final Codec<RaceRanking> CODEC = Codec.STRING
            .xmap(string -> RaceRanking.valueOf(string.toUpperCase()), instance -> instance.name().toLowerCase());
}
