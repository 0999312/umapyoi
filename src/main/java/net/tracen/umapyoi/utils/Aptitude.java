package net.tracen.umapyoi.utils;

import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

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

    public Component styledComponent() {
        return Component.literal(this.name()).withStyle(switch (this) {
            case G -> ChatFormatting.GRAY;
            case F -> ChatFormatting.BLUE;
            case E -> ChatFormatting.DARK_PURPLE;
            case D -> ChatFormatting.AQUA;
            case C -> ChatFormatting.GREEN;
            case B -> ChatFormatting.LIGHT_PURPLE;
            case A -> ChatFormatting.RED;
            case S -> ChatFormatting.GOLD;
        });
    }
}
