package net.tracen.umapyoi.utils;

import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public enum RaceRanking {
    DEBUT(ChatFormatting.GREEN, "common"),
    PREOP(ChatFormatting.YELLOW, "common"),
    OP(ChatFormatting.GOLD, "common"),
    GIII(ChatFormatting.DARK_GREEN, "common"),
    GII(ChatFormatting.LIGHT_PURPLE, "g2"),
    GI(ChatFormatting.BLUE, "g1");

    public final ChatFormatting color;
    public final String textureSuffix;

    RaceRanking(ChatFormatting color, String textureSuffix) {
        this.color = color;
        this.textureSuffix = textureSuffix;
    }

    public static final Codec<RaceRanking> CODEC = Codec.STRING
            .xmap(string -> RaceRanking.valueOf(string.toUpperCase()), instance -> instance.name().toLowerCase());
}
