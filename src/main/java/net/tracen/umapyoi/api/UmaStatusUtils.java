package net.tracen.umapyoi.api;

import java.util.function.Consumer;

import net.tracen.umapyoi.registry.umadata.UmaStatus;

public class UmaStatusUtils {
    public static Consumer<UmaStatus> addPropety(int stat, int value) {
        return status -> {
            status.property()[stat] = Math.min(status.maxProperty()[stat], status.property()[stat] + value);
        };
    }

    public static Consumer<UmaStatus> addMaxPropety(int stat, int value) {
        return status -> {
            status.maxProperty()[stat] += value;
        };
    }

    public static Consumer<UmaStatus> addEnergy(int energy) {
        return addEnergy(energy, 0);
    }

    public static Consumer<UmaStatus> addEnergy(int energy, int maxEnergy) {
        return status -> {
            status.setEnergy(status.getEnergy() + energy);
            status.setMaxEnergy(status.getMaxEnergy() + maxEnergy);
        };
    }
}
