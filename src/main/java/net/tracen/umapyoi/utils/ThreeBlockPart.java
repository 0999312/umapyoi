package net.tracen.umapyoi.utils;

import net.minecraft.util.StringRepresentable;

import javax.annotation.Nonnull;

public enum ThreeBlockPart implements StringRepresentable {
    LOWER, MIDDLE, UPPER;

    @Nonnull
    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }
}
