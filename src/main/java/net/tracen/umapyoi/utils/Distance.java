package net.tracen.umapyoi.utils;

import net.minecraft.world.item.ItemStack;

public enum Distance {
    SPRINT(0, 1499), MILE(1500, 1899), MEDIUM(1900, 2499), LONG(2500, 2147483647);
    public final int minimum;
    public final int maximum;

    Distance(int min, int max) {
        this.minimum = min;
        this.maximum = max;
    }

    public static Distance match(int length) {
        for (Distance dist: Distance.values()) {
            if (dist.minimum <= length && length <= dist.maximum) return dist;
        }
        return null;
    }

    public double GetMultiplier(ItemStack umaSoul) {
        return 1.0d;
    }
}
