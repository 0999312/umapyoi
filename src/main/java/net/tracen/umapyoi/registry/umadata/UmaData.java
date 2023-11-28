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

public class UmaData {
    public static final ResourceLocation DEFAULT_UMA_ID = new ResourceLocation(Umapyoi.MODID, "common_uma");
    public static final UmaData DEFAULT_UMA = UmaData.createNewUmamusume("common_uma", GachaRanking.R);

    public static final Codec<UmaData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("identifier").forGetter(UmaData::getIdentifier),
            GachaRanking.CODEC.optionalFieldOf("ranking", GachaRanking.EASTER_EGG).forGetter(UmaData::getGachaRanking),
            Codec.INT_STREAM.xmap(IntStream::toArray, Arrays::stream).fieldOf("property").forGetter(UmaData::property),
            Codec.INT_STREAM.xmap(IntStream::toArray, Arrays::stream).fieldOf("maxProperty")
                    .forGetter(UmaData::maxProperty),
            Codec.INT_STREAM.xmap(IntStream::toArray, Arrays::stream).fieldOf("propertyRate")
                    .forGetter(UmaData::propertyRate),
            ResourceLocation.CODEC.fieldOf("uniqueSkill").forGetter(UmaData::uniqueSkill))
            .apply(instance, UmaData::new));

    public static final ResourceKey<Registry<UmaData>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(new ResourceLocation(Umapyoi.MODID, "umadata"));

    private final ResourceLocation identifier;
    private final GachaRanking ranking;
    private final int[] property;
    private final int[] maxProperty;
    private final int[] propertyRate;

    private final ResourceLocation uniqueSkill;

    public UmaData(ResourceLocation identifier, GachaRanking ranking, int[] property, int[] maxProperty,
            int[] propertyRate, ResourceLocation uniqueSkill) {
        this.identifier = identifier;
        this.ranking = ranking;
        this.property = property;
        this.maxProperty = maxProperty;
        this.propertyRate = propertyRate;
        this.uniqueSkill = uniqueSkill;
    }

    public ResourceLocation getIdentifier() {
        return identifier;
    }

    public GachaRanking getGachaRanking() {
        return ranking;
    }

    public int[] property() {
        return property;
    }

    public int[] maxProperty() {
        return maxProperty;
    }

    public int[] propertyRate() {
        return propertyRate != null ? propertyRate : new int[] { 0, 0, 0, 0, 0 };
    }

    public ResourceLocation uniqueSkill() {
        return uniqueSkill;
    }

    public static UmaData createNewUmamusume(String name, GachaRanking ranking) {
        return new UmaData(new ResourceLocation(Umapyoi.MODID, name), ranking, new int[] { 1, 1, 1, 1, 1 },
                new int[] { 18, 18, 18, 18, 18 }, new int[] { 0, 0, 0, 0, 0 },
                new ResourceLocation(Umapyoi.MODID, "basic_pace"));
    }

    public static UmaData createNewUmamusume(String name, GachaRanking ranking, int[] rate) {
        return new UmaData(new ResourceLocation(Umapyoi.MODID, name), ranking, new int[] { 1, 1, 1, 1, 1 },
                new int[] { 18, 18, 18, 18, 18 }, rate, new ResourceLocation(Umapyoi.MODID, "basic_pace"));
    }
}
