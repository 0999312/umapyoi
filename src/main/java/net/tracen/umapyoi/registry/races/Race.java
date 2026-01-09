package net.tracen.umapyoi.registry.races;

import com.google.common.base.Functions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import cpw.mods.util.Lazy;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.item.UmaSoulItem;
import net.tracen.umapyoi.registry.races.Field.RaceField;
import net.tracen.umapyoi.registry.races.Tags.RaceTag;
import net.tracen.umapyoi.registry.races.Tags.RaceTagRegistry;
import net.tracen.umapyoi.registry.umadata.Growth;
import net.tracen.umapyoi.registry.umadata.Motivations;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.utils.*;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static net.tracen.umapyoi.registry.races.Field.RaceFieldRegistry.CONST_ADAPTIVE;

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
                    ResourceLocation.CODEC.fieldOf("field").forGetter(Race::field),
                    Codec.INT.listOf().optionalFieldOf("attribute_correction", List.of()).forGetter((r) -> r.attrCorr.stream().toList()),
                    Codec.INT.optionalFieldOf("reference_level", 5).forGetter(Race::referenceLevel)
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

    public Distance distance(ItemStack stack) {
        return this.distance != Distance.ADAPTIVE ? this.distance : Distance.AdaptiveEvaluation(stack);
    }

    public int length(ItemStack stack) {
        return this.distance != Distance.ADAPTIVE ? this.length : Distance.AdaptiveEvaluation(stack).defaultLength;
    }

    public final int time;
    public int time() {
        return this.time;
    }

    public final Set<Year> year;
    public final Set<ResourceLocation> tags;
    public final Surface surface;
    public Surface surface() { return this.surface; }
    public Surface surface(ItemStack stack) {
        return this.surface != Surface.ADAPTIVE ? this.surface : Surface.AdaptiveCollapse(stack);
    }
    public final Distance distance;
    public final ResourceLocation field;
    public ResourceLocation field() { return this.field; }
    public RaceField field(Level world, ItemStack stack) {
        Registry<RaceField> registry = UmapyoiAPI.getRaceFieldRegistry(world);
        if (this.field.equals(CONST_ADAPTIVE)) {
            Map<RaceField, Integer> makeupMap = registry.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toMap(
                    Functions.identity(),
                    field -> field.compareToAbs(this.distance(stack), this.surface(stack))
            ));
            int minimum = makeupMap.values().stream().filter(i -> i != -1).min(Comparator.naturalOrder()).orElse(-1);
            List<RaceField> fieldCandidate = makeupMap.entrySet().stream().filter(et -> (minimum == -1 || et.getValue() == minimum)).map(Map.Entry::getKey).sorted().toList(); // 确保可复现性
            return fieldCandidate.get(world.getRandom().nextInt(0, fieldCandidate.size()));
        }
        return registry.get(this.field);
    }
    public final Set<Integer> attrCorr;
    private final double[] correction;
    public final int referenceLevel;
    public int referenceLevel() { return this.referenceLevel; }

    public Race(ResourceLocation id, RaceRanking ranking, int length, int time, Set<Year> year, Surface surface, Set<ResourceLocation> tags, ResourceLocation field, Set<Integer> attrCorr, int referenceLevel) {
        this.id = id;
        this.ranking = ranking;
        this.length = length;
        this.time = time;
        this.year = year;
        this.surface = surface;
        this.distance = Distance.match(length);
        this.tags = tags;
        this.field = field;
        this.attrCorr = attrCorr;
        this.correction = new double[]{
                attrCorr.contains(0) ? 1.05 : 1,
                attrCorr.contains(1) ? 1.05 : 1,
                attrCorr.contains(2) ? 1.05 : 1,
                attrCorr.contains(3) ? 1.05 : 1,
                attrCorr.contains(4) ? 1.05 : 1
        };
        this.referenceLevel = referenceLevel;
    }

    public Race(ResourceLocation id, RaceRanking ranking, int length, int time, List<Year> year, Surface surface, List<ResourceLocation> tags, ResourceLocation field, List<Integer> attrCorr, int referenceLevel) {
        this(id, ranking, length, time,
                new HashSet<>(year),
                surface,
                new HashSet<>(tags),
                field,
                new HashSet<>(attrCorr),
                referenceLevel
        );
    }

    public boolean isAvailableToUmaSoul(ItemStack stack) {
        if (this.id.equals(RaceRegistry.DEFAULT.location())) return false;
        return stack.is(ItemRegistry.UMA_SOUL.get()) && UmaSoulUtils.getGrowth(stack) == Growth.RETIRED &&
                ((this.ranking == RaceRanking.DEBUT) ^ UmaSoulUtils.hasUmaSoulDebut(stack));
    }

    public double getUmaFactorCorrection(ItemStack stack, Level world) {
        int[] propertiesAsLevel = UmaSoulUtils.getProperty(stack);
        int fieldSituation = world.getRandom().nextIntBetweenInclusive(0, 3);
        ResourceLocation nameLoc = UmaSoulUtils.getName(stack);
        UmaData umaData = UmapyoiAPI.getUmaDataRegistry(world).getOptional(nameLoc).orElseGet(() -> {
            Umapyoi.getLogger().info("Warning: {} doesn't exist.", nameLoc);
            return UmaData.DEFAULT_UMA;
        });
        Position umaPosition = umaData.position();
        double distanceFactor = this.distance.GetMultiplier(stack);
        double surfaceFactor = this.surface.GetMultiplier(stack);
        double fieldSituationFactor = 1 - (fieldSituation * 0.05d); // last factor
        Motivations motivation = UmaSoulUtils.getMotivation(stack);

        double totalProperties = propertiesAsLevel[0] * (2 - umaPosition.speedFactor) * this.correction[0] + propertiesAsLevel[1]
                * (2 - umaPosition.staminaFactor) * this.correction[1] + propertiesAsLevel[2] * this.correction[2] +
                propertiesAsLevel[3] * this.correction[3] + propertiesAsLevel[4] * this.correction[4];
        Umapyoi.getLogger().debug("Properties: {} / {}", totalProperties, this.referenceLevel);
        Umapyoi.getLogger().debug("Motivation: {}", motivation.getMultiplier());
        Umapyoi.getLogger().debug("Surface distance field total: {} {} {} {}", surfaceFactor, distanceFactor,
                fieldSituationFactor, totalProperties / this.referenceLevel * motivation.getMultiplier() * surfaceFactor
                        * distanceFactor * fieldSituationFactor);
        return totalProperties / this.referenceLevel * motivation.getMultiplier() * surfaceFactor * distanceFactor * fieldSituationFactor;
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
        private final Set<Integer> attrCorr;
        private Integer referenceLevel;

        public RaceBuilder() {
            this.ranking = RaceRanking.DEBUT;
            this.length = 0;
            this.time = 1;
            this.surface = Surface.TURF;
            this.year = new HashSet<>();
            this.tags = new HashSet<>();
            this.field = new ResourceLocation(Umapyoi.MODID, "unknown");
            this.attrCorr = new HashSet<>();
            this.referenceLevel = null;
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

        public RaceBuilder addAttr(int... attrs) {
            for (int v: attrs) this.attrCorr.add(v);
            return this;
        }

        public RaceBuilder setReferenceLevel(int referenceLevel) {
            this.referenceLevel = referenceLevel;
            return this;
        }

        public Race create(ResourceLocation id) {
            return new Race(id, this.ranking, this.length, this.time, this.year, this.surface, this.tags, this.field,
                    this.attrCorr, Optional.ofNullable(this.referenceLevel).orElseGet(() ->
                        switch (this.ranking) {
                            case DEBUT -> 5;
                            case PREOP -> 5;
                            case OP -> 15;
                            case GIII -> 25;
                            case GII -> 30;
                            case GI -> 35;
                        }
            ));
        }
    }
}