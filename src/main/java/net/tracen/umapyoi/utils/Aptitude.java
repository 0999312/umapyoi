package net.tracen.umapyoi.utils;

import com.mojang.serialization.Codec;

public enum Aptitude {
    G(.1d,.1d),
    F(.3d,.2d),
    E(.5d,.4d),
    D(.7d,.6d),
    C(.8d,.8d),
    B(.9d,.9d),
    A( 1d, 1d),
    S(1.05d,1.05d);
    public final double surfaceFactor;
    public final double distanceFactor;
    Aptitude(double surfaceFactor, double distanceFactor) {
        this.surfaceFactor = surfaceFactor;
        this.distanceFactor = distanceFactor;
    }

    public static Codec<Aptitude> CODEC = Codec.INT.xmap((i) -> 0 <= i && i <= 7 ?
            Aptitude.values()[i] :
            (i < 0 ? G : S),
            Aptitude::ordinal);
}
