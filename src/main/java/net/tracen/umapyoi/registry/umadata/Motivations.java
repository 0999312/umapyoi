package net.tracen.umapyoi.registry.umadata;

import com.mojang.serialization.Codec;

public enum Motivations {
    PERFECT(1.25F), GOOD(1.1F), NORMAL(1.0F), DOWN(0.9F), BAD(0.75F);

    public static final Codec<Motivations> CODEC = Codec.STRING
            .xmap(string -> Motivations.valueOf(string.toUpperCase()), instance -> instance.name().toLowerCase());

    private final float multiplier;

    private Motivations(float multiplier) {
        this.multiplier = multiplier;
    }

    public float getMultiplier() {
        return multiplier;
    }
}
