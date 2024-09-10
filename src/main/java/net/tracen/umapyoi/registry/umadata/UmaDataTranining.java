package net.tracen.umapyoi.registry.umadata;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record UmaDataTranining(int physique, int talent) {
	public static final Codec<UmaDataTranining> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.INT.fieldOf("physique").forGetter(UmaDataTranining::physique),
			Codec.INT.fieldOf("talent").forGetter(UmaDataTranining::talent))
            .apply(instance, UmaDataTranining::new));
    
	public static final StreamCodec<ByteBuf, UmaDataTranining> STREAM = StreamCodec.composite(
			ByteBufCodecs.INT, UmaDataTranining::physique,
			ByteBufCodecs.INT, UmaDataTranining::talent,
			UmaDataTranining::new
	);
}
