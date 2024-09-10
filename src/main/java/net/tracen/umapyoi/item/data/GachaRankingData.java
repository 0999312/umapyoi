package net.tracen.umapyoi.item.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.tracen.umapyoi.utils.GachaRanking;

public record GachaRankingData(GachaRanking ranking) {
	public static final Codec<GachaRankingData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			GachaRanking.CODEC.fieldOf("ranking").forGetter(GachaRankingData::ranking)
			).apply(instance, GachaRankingData::new));
    
	public static final StreamCodec<ByteBuf, GachaRankingData> STREAM = StreamCodec.composite(
			ByteBufCodecs.STRING_UTF8.map(
			        // String -> Motivation
					string -> GachaRanking.valueOf(string.toUpperCase()),
			        // Motivation -> String
					instance -> instance.name().toLowerCase()
			    ), GachaRankingData::ranking,
			GachaRankingData::new
	);
}
