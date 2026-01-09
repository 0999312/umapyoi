package net.tracen.umapyoi.utils;

import com.mojang.serialization.Codec;

public enum Position {
    FRONT_RUNNER(0.9, 1.1),
    PACE_CHASER(0.95, 1.05),
    LATE_SURGER(1, 1),
    END_CLOSER(1.05, 0.95),
    RUNAWAY(0.7, 1.3);
    // todo: Move Runaray to skill as a Front-Runner only skill. Remove this if decided to kept "Runaway" as a position.

    public final double speedFactor;
    public final double staminaFactor;

    Position(double speed, double stamina) {
        this.speedFactor = speed;
        this.staminaFactor = stamina;
    }

    public static final Codec<Position> CODEC = Codec.STRING
            .xmap(string -> Position.valueOf(string.toUpperCase()), instance -> instance.name().toLowerCase());
}
