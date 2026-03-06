package net.tracen.umapyoi.registry.umadata;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.tracen.umapyoi.utils.Aptitude;

import java.util.List;

public record UmaDataAptitude(
        Aptitude turf,
        Aptitude dirt,
        Aptitude synthetic,
        Aptitude sprint,
        Aptitude mile,
        Aptitude medium,
        Aptitude long_distance
) {
    public static final UmaDataAptitude DEFAULT = new UmaDataAptitude(Aptitude.C, Aptitude.C, Aptitude.B, Aptitude.C, Aptitude.C, Aptitude.C, Aptitude.C);

    public static UmaDataAptitude init(Aptitude[] surface, Aptitude[] distance) {
        return new UmaDataAptitude(
                surface[0],
                surface[1],
                surface[2],
                distance[0],
                distance[1],
                distance[2],
                distance[3]
        );
    }

    public static UmaDataAptitude init(Aptitude[] aptitudes) {
        return new UmaDataAptitude(aptitudes[0], aptitudes[1], aptitudes[2], aptitudes[3], aptitudes[4], aptitudes[5], aptitudes[6]);
    }

    public Aptitude[] getDistance() {
        return new Aptitude[]{ sprint, mile, medium, long_distance };
    }

    public Aptitude[] getSurface() {
        return new Aptitude[] { turf, dirt, synthetic };
    }

    public static final StreamCodec<ByteBuf, UmaDataAptitude> STREAM = StreamCodec.composite(
            Aptitude.STREAM.apply(ByteBufCodecs.list()), v -> List.of(
                    v.turf, v.dirt, v.synthetic, v.sprint, v.mile, v.medium, v.long_distance
            ),
            l -> UmaDataAptitude.init(l.toArray(new Aptitude[0]))
    );

    public static final Codec<UmaDataAptitude> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                Aptitude.CODEC.fieldOf("turf").forGetter(UmaDataAptitude::turf),
                Aptitude.CODEC.fieldOf("dirt").forGetter(UmaDataAptitude::dirt),
                Aptitude.CODEC.fieldOf("synthetic").forGetter(UmaDataAptitude::synthetic),
                Aptitude.CODEC.fieldOf("sprint").forGetter(UmaDataAptitude::sprint),
                Aptitude.CODEC.fieldOf("mile").forGetter(UmaDataAptitude::mile),
                Aptitude.CODEC.fieldOf("medium").forGetter(UmaDataAptitude::medium),
                Aptitude.CODEC.fieldOf("long").forGetter(UmaDataAptitude::long_distance)
            ).apply(instance, UmaDataAptitude::new)
    );
}
