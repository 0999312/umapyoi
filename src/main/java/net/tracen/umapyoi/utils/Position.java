package net.tracen.umapyoi.utils;

import com.mojang.serialization.Codec;

public enum Position {
    FRONT_RUNNER(0.9, 1.1, 0.2d, 0.75d, 0.9d, 0.9d),
    PACE_CHASER(0.95, 1.05, 0.2d, 0.6d, 0.9d, 0.75d),
    LATE_SURGER(1, 1, 1/3d, 0.5d, 0.75d, 0.4d),
    END_CLOSER(1.05, 0.95, 0.25d, 0.4d, 0.7d, 0),
    RUNAWAY(0.7, 1.3, 0.3d, 1, 0.9d, 0.94d);
    // todo: Move Runaray to skill as a Front-Runner only skill. Remove this if decided to kept "Runaway" as a position.

    public final double speedFactor;
    public final double staminaFactor;
    public final double x1;
    public final double y1;
    public final double x2;
    public final double y2;

    Position(double speed, double stamina, double x1, double y1, double x2, double y2) {
        this.speedFactor = speed;
        this.staminaFactor = stamina;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public static final Codec<Position> CODEC = Codec.STRING
            .xmap(string -> Position.valueOf(string.toUpperCase()), instance -> instance.name().toLowerCase());
}
