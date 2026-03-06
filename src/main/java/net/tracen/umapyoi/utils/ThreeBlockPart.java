package net.tracen.umapyoi.utils;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import javax.annotation.Nonnull;

public enum ThreeBlockPart implements StringRepresentable {
    LOWER, MIDDLE, UPPER;

    public static final EnumProperty<ThreeBlockPart> PART = EnumProperty.create("part", ThreeBlockPart.class);

    @Nonnull
    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }
}
