package net.tracen.umapyoi.registry.umadata;

import java.util.Arrays;
import java.util.stream.IntStream;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.utils.GachaRanking;

public class UmaData extends ForgeRegistryEntry<UmaData> {
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

    public UmaData(ResourceLocation identifier, GachaRanking ranking, int[] property, int[] maxProperty, int[] propertyRate,
            ResourceLocation uniqueSkill) {
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
        return propertyRate != null ? propertyRate: new int[] {0,0,0,0,0};
    }

    public ResourceLocation uniqueSkill() {
        return uniqueSkill;
    }

}
