package net.tracen.umapyoi.registry.umadata;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record UmaDataTraninning(int physique, int talent) {
	public static final Codec<UmaDataTraninning> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.INT.fieldOf("physique").forGetter(UmaDataTraninning::physique),
			Codec.INT.fieldOf("talent").forGetter(UmaDataTraninning::talent))
            .apply(instance, UmaDataTraninning::new));
    
	public static final StreamCodec<ByteBuf, UmaDataTraninning> STREAM = StreamCodec.composite(
			ByteBufCodecs.INT, UmaDataTraninning::physique,
			ByteBufCodecs.INT, UmaDataTraninning::talent,
			UmaDataTraninning::new
	);
}
