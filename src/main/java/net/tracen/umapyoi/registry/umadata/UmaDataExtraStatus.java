package net.tracen.umapyoi.registry.umadata;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record UmaDataExtraStatus(int actionPoint, int extraActionPoint, int resultRanking, Motivations motivation) {
	public static final Codec<UmaDataExtraStatus> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.INT.fieldOf("action_point").forGetter(UmaDataExtraStatus::actionPoint),
			Codec.INT.fieldOf("extra_action_point").forGetter(UmaDataExtraStatus::extraActionPoint),
			Codec.INT.fieldOf("result_ranking").forGetter(UmaDataExtraStatus::resultRanking),
			Motivations.CODEC.fieldOf("motivation").forGetter(UmaDataExtraStatus::motivation)
			).apply(instance, UmaDataExtraStatus::new));
    
	public static final StreamCodec<ByteBuf, UmaDataExtraStatus> STREAM = StreamCodec.composite(
			ByteBufCodecs.INT, UmaDataExtraStatus::actionPoint,
			ByteBufCodecs.INT, UmaDataExtraStatus::extraActionPoint,
			ByteBufCodecs.INT, UmaDataExtraStatus::resultRanking,
			ByteBufCodecs.STRING_UTF8.map(
			        // String -> Motivation
					string -> Motivations.valueOf(string.toUpperCase()),
			        // Motivation -> String
					instance -> instance.name().toLowerCase()
			    ), UmaDataExtraStatus::motivation,
			UmaDataExtraStatus::new
	);
	
	public static final UmaDataExtraStatus DEFAULT = new UmaDataExtraStatus(0, 0, 0, Motivations.NORMAL);
}
