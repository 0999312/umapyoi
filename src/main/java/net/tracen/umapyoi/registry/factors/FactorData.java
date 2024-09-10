package net.tracen.umapyoi.registry.factors;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public record FactorData(ResourceLocation id, int level, Optional<CompoundTag> tag) {
	public static final Codec<FactorData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			ResourceLocation.CODEC.fieldOf("factor").forGetter(FactorData::id),
			Codec.INT.fieldOf("level").forGetter(FactorData::level),
			CompoundTag.CODEC.optionalFieldOf("tag").forGetter(FactorData::tag)
			).apply(instance, FactorData::new));
    
	public static final StreamCodec<ByteBuf, FactorData> STREAM = StreamCodec.composite(
			ResourceLocation.STREAM_CODEC, FactorData::id,
			ByteBufCodecs.INT, FactorData::level,
			ByteBufCodecs.OPTIONAL_COMPOUND_TAG, FactorData::tag,
			FactorData::new
	);
	
}
