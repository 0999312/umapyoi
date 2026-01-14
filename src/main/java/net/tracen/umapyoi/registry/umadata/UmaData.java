package net.tracen.umapyoi.registry.umadata;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.utils.Aptitude;
import net.tracen.umapyoi.utils.GachaRanking;
import net.tracen.umapyoi.utils.Position;
import net.tracen.umapyoi.utils.Surface;

public class UmaData {
    private static final int[] EMPTY_PROPERTY_RATE = new int[] { 0, 0, 0, 0, 0 };
	private static final int[] DEFAULT_MAX_PROPERTY = new int[] { 18, 18, 18, 18, 18 };
	private static final int[] DEFAULT_PROPERTY = new int[] { 1, 1, 1, 1, 1 };
    public static final Aptitude[] DEFAULT_SURFACE_APTITUDE = new Aptitude[]{Aptitude.C, Aptitude.C, Aptitude.B};
    public static final Aptitude[] DEFAULT_DISTANCE_APTITUDE = new Aptitude[]{Aptitude.C, Aptitude.C, Aptitude.C, Aptitude.C};
    private static final Position DEFAULT_POSITION = Position.FRONT_RUNNER;
	public static final ResourceLocation DEFAULT_UMA_ID = new ResourceLocation(Umapyoi.MODID, "common_uma");
    public static final UmaData DEFAULT_UMA = UmaData.createNewUmamusume("common_uma", GachaRanking.R, new Aptitude[]{Aptitude.C, Aptitude.C, Aptitude.C, Aptitude.C, Aptitude.C, Aptitude.C}, Position.FRONT_RUNNER);

    public static final Codec<UmaData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("identifier").forGetter(UmaData::getIdentifier),
            GachaRanking.CODEC.optionalFieldOf("ranking", GachaRanking.EASTER_EGG).forGetter(UmaData::getGachaRanking),
            Codec.INT_STREAM.xmap(IntStream::toArray, Arrays::stream).optionalFieldOf("property", DEFAULT_PROPERTY).forGetter(UmaData::property),
            Codec.INT_STREAM.xmap(IntStream::toArray, Arrays::stream).optionalFieldOf("maxProperty", DEFAULT_MAX_PROPERTY)
                    .forGetter(UmaData::maxProperty),
            Codec.INT_STREAM.xmap(IntStream::toArray, Arrays::stream).optionalFieldOf("propertyRate", EMPTY_PROPERTY_RATE)
                    .forGetter(UmaData::propertyRate),
            ResourceLocation.CODEC.optionalFieldOf("uniqueSkill", UmaSkillRegistry.BASIC_PACE.getId()).forGetter(UmaData::uniqueSkill),
            Aptitude.CODEC.listOf().xmap((lst) -> lst.toArray(Aptitude[]::new), Arrays::asList).optionalFieldOf("surface_aptitude", DEFAULT_SURFACE_APTITUDE).forGetter(UmaData::surfaceAptitude),
            Aptitude.CODEC.listOf().xmap((lst) -> lst.toArray(Aptitude[]::new), Arrays::asList).optionalFieldOf("distance_aptitude", DEFAULT_DISTANCE_APTITUDE).forGetter(UmaData::distanceAptitude),
            Position.CODEC.optionalFieldOf("position", DEFAULT_POSITION).forGetter(UmaData::position))
            .apply(instance, UmaData::new));

    public static final ResourceKey<Registry<UmaData>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(new ResourceLocation(Umapyoi.MODID, "umadata"));

    private final ResourceLocation identifier;
    private final GachaRanking ranking;
    private final int[] property;
    private final int[] maxProperty;
    private final int[] propertyRate;

    private final ResourceLocation uniqueSkill;

    private final Aptitude[] surfaceAptitude;
    public Aptitude[] surfaceAptitude() {
        return this.surfaceAptitude;
    }

    private final Aptitude[] distanceAptitude;
    public Aptitude[] distanceAptitude() {
        return this.distanceAptitude;
    }

    private final Position position;
    public Position position() {return this.position;}

    public UmaData(ResourceLocation identifier, GachaRanking ranking, int[] property, int[] maxProperty,
            int[] propertyRate, ResourceLocation uniqueSkill, Aptitude[] surfaceAptitude, Aptitude[] distanceAptitude, Position position) {
        this.identifier = identifier;
        this.ranking = ranking;
        this.property = property;
        this.maxProperty = maxProperty;
        this.propertyRate = propertyRate;
        this.uniqueSkill = uniqueSkill;
        this.surfaceAptitude = surfaceAptitude;
        this.distanceAptitude = distanceAptitude;
        this.position = position;
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
        return propertyRate != null ? propertyRate : EMPTY_PROPERTY_RATE;
    }

    public ResourceLocation uniqueSkill() {
        return uniqueSkill;
    }

    public static UmaData createNewUmamusume(String name, GachaRanking ranking,
                                             Aptitude[] aptitudes, Position pos) {
        return createNewUmamusume(name, ranking, EMPTY_PROPERTY_RATE, aptitudes, pos);
    }

    public static UmaData createNewUmamusume(String name, GachaRanking ranking, int[] rate, Aptitude[] aptitudes, Position pos) {
        Aptitude[] surfaceAptitude = DEFAULT_SURFACE_APTITUDE.clone();
        System.arraycopy(aptitudes, 0, surfaceAptitude, 0, aptitudes.length - 4);
        Aptitude[] distanceAptitude = new Aptitude[4];
        System.arraycopy(aptitudes, aptitudes.length - 4, distanceAptitude, 0, 4);
        return new UmaData(new ResourceLocation(Umapyoi.MODID, name), ranking, DEFAULT_PROPERTY,
                DEFAULT_MAX_PROPERTY, rate, new ResourceLocation(Umapyoi.MODID, "basic_pace"), surfaceAptitude, distanceAptitude, pos);
    }
}
