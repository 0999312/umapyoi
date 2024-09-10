package net.tracen.umapyoi.registry.umadata;

import java.util.Arrays;
import java.util.stream.IntStream;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.utils.GachaRanking;

public record UmaData(
		ResourceLocation identifier, 
		GachaRanking ranking, 
		int[] property, 
		int[] maxProperty,
        int[] propertyRate, 
        ResourceLocation uniqueSkill
        ) {
    public static final ResourceLocation DEFAULT_UMA_ID = ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "common_uma");
    public static final UmaData DEFAULT_UMA = UmaData.createNewUmamusume("common_uma", GachaRanking.R);

    public static final Codec<UmaData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("identifier").forGetter(UmaData::identifier),
            GachaRanking.CODEC.optionalFieldOf("ranking", GachaRanking.EASTER_EGG).forGetter(UmaData::ranking),
            Codec.INT_STREAM.xmap(IntStream::toArray, Arrays::stream).fieldOf("property").forGetter(UmaData::property),
            Codec.INT_STREAM.xmap(IntStream::toArray, Arrays::stream).fieldOf("maxProperty")
                    .forGetter(UmaData::maxProperty),
            Codec.INT_STREAM.xmap(IntStream::toArray, Arrays::stream).fieldOf("propertyRate")
                    .forGetter(UmaData::propertyRate),
            ResourceLocation.CODEC.fieldOf("uniqueSkill").forGetter(UmaData::uniqueSkill))
            .apply(instance, UmaData::new));

    public static final ResourceKey<Registry<UmaData>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "umadata"));

    public static UmaData createNewUmamusume(String name, GachaRanking ranking) {
        return new UmaData(ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, name), ranking, new int[] { 1, 1, 1, 1, 1 },
                new int[] { 18, 18, 18, 18, 18 }, new int[] { 0, 0, 0, 0, 0 },
                ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "basic_pace"));
    }

    public static UmaData createNewUmamusume(String name, GachaRanking ranking, int[] rate) {
        return new UmaData(ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, name), ranking, new int[] { 1, 1, 1, 1, 1 },
                new int[] { 18, 18, 18, 18, 18 }, rate, ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "basic_pace"));
    }
}
