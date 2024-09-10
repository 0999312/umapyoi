package net.tracen.umapyoi.item.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public record DataLocation(ResourceLocation name) {
	public static final Codec<DataLocation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			ResourceLocation.CODEC.fieldOf("name").forGetter(DataLocation::name))
            .apply(instance, DataLocation::new));
    
	public static final StreamCodec<ByteBuf, DataLocation> STREAM = StreamCodec.composite(
			ResourceLocation.STREAM_CODEC, DataLocation::name,
			DataLocation::new
	);
}
