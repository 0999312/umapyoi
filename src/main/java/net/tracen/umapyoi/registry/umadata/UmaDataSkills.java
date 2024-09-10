package net.tracen.umapyoi.registry.umadata;

import java.util.ArrayList;
import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public record UmaDataSkills(int skillSlot, int selectedSkill, List<ResourceLocation> skills) {
	public static final Codec<UmaDataSkills> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.INT.fieldOf("skill_slot").forGetter(UmaDataSkills::skillSlot),
			Codec.INT.fieldOf("selected_skill").forGetter(UmaDataSkills::selectedSkill),
			ResourceLocation.CODEC.listOf().fieldOf("skills").forGetter(UmaDataSkills::skills)
			).apply(instance, UmaDataSkills::new));
    
	public static final StreamCodec<ByteBuf, UmaDataSkills> STREAM = StreamCodec.composite(
			ByteBufCodecs.INT, UmaDataSkills::skillSlot,
			ByteBufCodecs.INT, UmaDataSkills::selectedSkill,
			ByteBufCodecs.collection(ArrayList::new, ResourceLocation.STREAM_CODEC), UmaDataSkills::skills,
			UmaDataSkills::new
	);
}
