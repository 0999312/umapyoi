package net.tracen.umapyoi.utils;

import com.mojang.serialization.Codec;

public enum Year {
    JUNIOR, CLASSIC, SENIOR;

    public static final Codec<Year> CODEC = Codec.STRING
            .xmap(string -> Year.valueOf(string.toUpperCase()), instance -> instance.name().toLowerCase());
}
