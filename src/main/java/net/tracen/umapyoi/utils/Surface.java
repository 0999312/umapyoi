package net.tracen.umapyoi.utils;

import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.Comparator;

public enum Surface {
    TURF(ChatFormatting.GREEN), DIRT(ChatFormatting.GOLD), SYNTHETIC(ChatFormatting.RED), ADAPTIVE(ChatFormatting.WHITE);

    public final ChatFormatting color;
    Surface(ChatFormatting color) {
        this.color = color;
    }

    public static final class ADAPTIVE_COMPARATOR implements Comparator<Surface> {
        private final ItemStack soul;

        public ADAPTIVE_COMPARATOR(ItemStack soul) {
            this.soul = soul;
        }

        @Override
        public int compare(Surface o1, Surface o2) {
            Aptitude[] aptitudes = UmaSoulUtils.getSurfaceAptitudeReadonly(soul);
            Aptitude leftAptitude = aptitudes[o1.ordinal()];
            Aptitude rightAptitude = aptitudes[o2.ordinal()];
            if (leftAptitude != rightAptitude) {
                return leftAptitude.compareTo(rightAptitude);
            }
            return o1.compareTo(o2);
        }
    }

    public static Surface AdaptiveCollapse(ItemStack soul) {
        return Arrays.stream(Surface.values()).filter(s -> s != ADAPTIVE).max(new ADAPTIVE_COMPARATOR(soul)).orElseThrow();
    }

    public double GetMultiplier(ItemStack soul) {
        if (this == ADAPTIVE) {
            return AdaptiveCollapse(soul).GetMultiplier(soul);
        }
        return UmaSoulUtils.getSurfaceAptitudeReadonly(soul)[this.ordinal()].surfaceFactor;
    }

    public static final Codec<Surface> CODEC = Codec.STRING
            .xmap(string -> Surface.valueOf(string.toUpperCase()), instance -> instance.name().toLowerCase());
}
