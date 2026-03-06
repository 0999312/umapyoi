package net.tracen.umapyoi.utils;

import com.mojang.serialization.Codec;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.Comparator;

public enum Distance {
    SPRINT(0, 1499, 0, 1000), MILE(1500, 1899, 2, 1600),
    MEDIUM(1900, 2499, 3, 2400), LONG(2500, 2147483647, 1, 2500),
    ADAPTIVE(-1);

    public static final int CONST_ADAPTIVE = -1;
    public final int minimum;
    public final int maximum;
    private final int comparatorValue;
    public final int defaultLength;

    Distance(int constVal) {
        this.minimum = constVal;
        this.maximum = constVal;
        this.comparatorValue = constVal;
        this.defaultLength = constVal;
    }

    Distance(int min, int max, int com, int defaultLength) {
        this.minimum = min;
        this.maximum = max;
        this.comparatorValue = com;
        this.defaultLength = defaultLength;
    }

    public static Distance match(int length) {
        for (Distance dist: Distance.values()) {
            if (dist.minimum <= length && length <= dist.maximum) return dist;
        }
        return null;
    }

    public static class ADAPTIVE_COMPARATOR implements Comparator<Distance> {
        private final ItemStack stack;

        public ADAPTIVE_COMPARATOR(ItemStack stack) {
            this.stack = stack;
        }

        // MEDIUM > MILES > LONG > SPRINT
        @Override
        public int compare(Distance o1, Distance o2) {
            Aptitude[] umaAptitudes = UmaSoulUtils.getDistanceAptitude(stack);
            Aptitude leftApt = umaAptitudes[o1.ordinal()];
            Aptitude rightApt = umaAptitudes[o2.ordinal()];
            if (leftApt != rightApt) {
                return leftApt.compareTo(rightApt);
            }

            return o1.comparatorValue - o2.comparatorValue;
        }
    }

    public static Distance AdaptiveEvaluation(ItemStack umaSoul) {
        return Arrays.stream(Distance.values()).filter(d -> d != ADAPTIVE).max(new ADAPTIVE_COMPARATOR(umaSoul)).orElseThrow();
    }

    public double GetMultiplier(ItemStack umaSoul) {
        if (this == ADAPTIVE) {
            return AdaptiveEvaluation(umaSoul).GetMultiplier(umaSoul);
        }
        return UmaSoulUtils.getDistanceAptitude(umaSoul)[this.ordinal()].distanceFactor;
    }

    public static final Codec<Distance> CODEC = Codec.STRING.xmap(s -> Distance.valueOf(s.toUpperCase()), d -> d.name().toLowerCase());
}
