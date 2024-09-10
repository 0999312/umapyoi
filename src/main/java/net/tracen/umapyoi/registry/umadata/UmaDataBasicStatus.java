package net.tracen.umapyoi.registry.umadata;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record UmaDataBasicStatus(int speed, int stamina, int strength, int guts, int wisdom) {
	public static final Codec<UmaDataBasicStatus> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.INT.fieldOf("speed").forGetter(UmaDataBasicStatus::speed),
			Codec.INT.fieldOf("stamina").forGetter(UmaDataBasicStatus::stamina),
			Codec.INT.fieldOf("strength").forGetter(UmaDataBasicStatus::strength),
			Codec.INT.fieldOf("guts").forGetter(UmaDataBasicStatus::guts),
			Codec.INT.fieldOf("wisdom").forGetter(UmaDataBasicStatus::wisdom)
			).apply(instance, UmaDataBasicStatus::new));
    
	public static final StreamCodec<ByteBuf, UmaDataBasicStatus> STREAM = StreamCodec.composite(
			ByteBufCodecs.INT, UmaDataBasicStatus::speed,
			ByteBufCodecs.INT, UmaDataBasicStatus::stamina,
			ByteBufCodecs.INT, UmaDataBasicStatus::strength,
			ByteBufCodecs.INT, UmaDataBasicStatus::guts,
			ByteBufCodecs.INT, UmaDataBasicStatus::wisdom,
			UmaDataBasicStatus::new
	);
	
	public static final UmaDataBasicStatus DEFAULT_STATUS = new UmaDataBasicStatus(1, 1, 1, 1, 1);
	public static final UmaDataBasicStatus DEFAULT_MAX_STATUS = new UmaDataBasicStatus(12, 12, 12, 12, 12);
	
	public static UmaDataBasicStatus init(int[] data) {
		return new UmaDataBasicStatus(data[0], data[1], data[2], data[3], data[4]);
	}
}
