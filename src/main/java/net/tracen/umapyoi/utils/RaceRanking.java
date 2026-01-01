package net.tracen.umapyoi.utils;

import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
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

    public static RaceRanking getRaceRanking(ItemStack stack) {
        try {
            if (stack.getOrCreateTag().contains("race_ranking")) {
                return RaceRanking.valueOf(stack.getOrCreateTag().getString("race_ranking").toUpperCase());
            }
        } catch (IllegalArgumentException _ignored) {
            return DEBUT;
        }
        return DEBUT;
    }
}
