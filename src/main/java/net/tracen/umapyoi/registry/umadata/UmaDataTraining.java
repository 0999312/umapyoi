package net.tracen.umapyoi.registry.umadata;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record UmaDataTraining(int physique, int talent, boolean hasTrained) {
	public static final Codec<UmaDataTraining> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.INT.fieldOf("physique").forGetter(UmaDataTraining::physique),
			Codec.INT.fieldOf("talent").forGetter(UmaDataTraining::talent),
			Codec.BOOL.optionalFieldOf("has_trained", false).forGetter(UmaDataTraining::hasTrained))
            .apply(instance, UmaDataTraining::new));
    
	public static final StreamCodec<ByteBuf, UmaDataTraining> STREAM = StreamCodec.composite(
			ByteBufCodecs.INT, UmaDataTraining::physique,
			ByteBufCodecs.INT, UmaDataTraining::talent,
			ByteBufCodecs.BOOL, UmaDataTraining::hasTrained,
			UmaDataTraining::new
	);
}
