package net.tracen.umapyoi.utils;

import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ItemStack;

public enum Surface {
    TURF(ChatFormatting.GREEN), DIRT(ChatFormatting.GOLD);

    public final ChatFormatting color;
    Surface(ChatFormatting color) {
        this.color = color;
    }

    public double GetMultiplier(ItemStack soul) {
        return 1.0d;
    }

    public static final Codec<Surface> CODEC = Codec.STRING
            .xmap(string -> Surface.valueOf(string.toUpperCase()), instance -> instance.name().toLowerCase());
}
