package net.tracen.umapyoi.registry.races;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.registry.races.Field.RaceField;
import net.tracen.umapyoi.registry.races.Tags.RaceTag;
import net.tracen.umapyoi.registry.races.Tags.RaceTagRegistry;
import net.tracen.umapyoi.registry.umadata.Growth;
import net.tracen.umapyoi.utils.*;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class Race {
    public static final ResourceKey<Registry<Race>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(new ResourceLocation(Umapyoi.MODID, "races"));

    public static Codec<Race> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    ResourceLocation.CODEC.fieldOf("id").forGetter(Race::id),
                    RaceRanking.CODEC.fieldOf("ranking").forGetter(Race::ranking),
                    Codec.INT.fieldOf("length").forGetter(Race::length),
                    Codec.INT.fieldOf("time").forGetter(Race::time),
                    Year.CODEC.listOf().fieldOf("year").forGetter((r) -> r.year.stream().toList()),
                    Surface.CODEC.fieldOf("surface").forGetter(Race::surface),
                    ResourceLocation.CODEC.listOf().fieldOf("tags").forGetter((r) -> r.tags.stream().toList()),
                    ResourceLocation.CODEC.fieldOf("field").forGetter(Race::field)
            ).apply(instance, Race::new)
    );

    public final ResourceLocation id;
    public ResourceLocation id() { return this.id; }

    public final RaceRanking ranking;
    public RaceRanking ranking() { return this.ranking; }

    public final int length;
    public int length() {
        return this.length;
    }

    public final int time;
    public int time() {
        return this.time;
    }

    public final Set<Year> year;
    public final Set<ResourceLocation> tags;
    public final Surface surface;
    public Surface surface() { return this.surface; }
    public final Distance distance;
    public final ResourceLocation field;
    public ResourceLocation field() { return this.field; }

    public Race(ResourceLocation id, RaceRanking ranking, int length, int time, Set<Year> year, Surface surface, Set<ResourceLocation> tags, ResourceLocation field) {
        this.id = id;
        this.ranking = ranking;
        this.length = length;
        this.time = time;
        this.year = year;
        this.surface = surface;
        this.distance = Distance.match(length);
        this.tags = tags;
        this.field = field;
    }

    public Race(ResourceLocation id, RaceRanking ranking, int length, int time, List<Year> year, Surface surface, List<ResourceLocation> tags, ResourceLocation field) {
        this(id, ranking, length, time,
                new HashSet<>(year),
                surface,
                new HashSet<>(tags),
                field
        );
    }

    public boolean isAvailableToUmaSoul(ItemStack stack) {
        if (this.id.equals(RaceRegistry.DEFAULT.location())) return false;
        return stack.is(ItemRegistry.UMA_SOUL.get()) && UmaSoulUtils.getGrowth(stack) == Growth.RETIRED &&
                ((this.ranking == RaceRanking.DEBUT) ^ UmaSoulUtils.hasUmaSoulDebut(stack));
    }

    public double getUmaFactorCorrection(ItemStack stack) {
        return this.surface.GetMultiplier(stack) * this.distance.GetMultiplier(stack);
    }

    public void followUp(ItemStack stack, Level level) {
        Umapyoi.getLogger().debug("Tag: {}", this.tags);
        tags.stream().map(UmapyoiAPI.getRaceTagRegistry(level)::get).filter(Objects::nonNull)
                .forEach((t) -> t.applyToUmaSoul(stack, this));
        if (this.ranking == RaceRanking.DEBUT) {
            stack.getOrCreateTag().putBoolean("has_debut", true);
        }
    }

    public static class RaceBuilder {
        private BiFunction<ItemStack, Race, Boolean> pred;
        private Consumer<ItemStack> follow;
        private RaceRanking ranking;
        private int length;
        private int time;
        private final Set<Year> year;
        private final Set<ResourceLocation> tags;
        private Surface surface;
        private ResourceLocation field;

        public RaceBuilder() {
            this.ranking = RaceRanking.DEBUT;
            this.length = 0;
            this.time = 1;
            this.surface = Surface.TURF;
            this.year = new HashSet<>();
            this.tags = new HashSet<>();
            this.field = new ResourceLocation(Umapyoi.MODID, "unknown");
        }

        public RaceBuilder setLength(int len) {
            this.length = len;
            return this;
        }

        public RaceBuilder setRanking(RaceRanking rank) {
            this.ranking = rank;
            return this;
        }

        public RaceBuilder setTime(int time) {
            this.time = time;
            return this;
        }

        public RaceBuilder setTime(int month, boolean isLatter) {
            return this.setTime((month - 1) * 2 + (isLatter ? 2 : 1));
        }

        public RaceBuilder addYear(Year... years) {
            Collections.addAll(this.year, years);
            return this;
        }

        public RaceBuilder addTags(ResourceLocation... locs) {
            Collections.addAll(this.tags, locs);
            return this;
        }

        @SafeVarargs
        public final RaceBuilder addTags(ResourceKey<RaceTag>... locs) {
            Arrays.stream(locs).map(ResourceKey::location).forEach(this::addTags);
            return this;
        }

        public RaceBuilder setSurface(Surface surface) {
            this.surface = surface;
            return this;
        }

        public RaceBuilder setField(String field) {
            return this.setField(new ResourceLocation(Umapyoi.MODID, field));
        }

        public RaceBuilder setField(ResourceKey<RaceField> field) {
            return this.setField(field.location());
        }

        public RaceBuilder setField(ResourceLocation field) {
            this.field = field;
            return this;
        }

        public Race create(ResourceLocation id) {
            return new Race(id, this.ranking, this.length, this.time, this.year, this.surface, this.tags, this.field);
        }
    }
}
