package net.tracen.umapyoi.registry.training;

import com.mojang.serialization.Codec;

public enum SupportType {
    SPEED, STAMINA, STRENGTH, GUTS, WISDOM, FRIENDSHIP, GROUP;

    public static final Codec<SupportType> CODEC = Codec.STRING
            .xmap(string -> SupportType.valueOf(string.toUpperCase()), instance -> instance.name().toLowerCase());
}
