package net.tracen.umapyoi.registry.umadata;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public record UmaDataIdentifier(ResourceLocation name) {
	public static final Codec<UmaDataIdentifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			ResourceLocation.CODEC.fieldOf("name").forGetter(UmaDataIdentifier::name))
            .apply(instance, UmaDataIdentifier::new));
    
	public static final StreamCodec<ByteBuf, UmaDataIdentifier> STREAM = StreamCodec.composite(
			ResourceLocation.STREAM_CODEC, UmaDataIdentifier::name,
			UmaDataIdentifier::new
	);
}
