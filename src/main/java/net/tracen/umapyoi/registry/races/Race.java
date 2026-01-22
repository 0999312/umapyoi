package net.tracen.umapyoi.registry.races;

import com.google.common.base.Functions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.registry.races.Field.RaceField;
import net.tracen.umapyoi.registry.races.Tags.RaceTag;
import net.tracen.umapyoi.registry.umadata.Growth;
import net.tracen.umapyoi.registry.umadata.Motivations;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.utils.*;

import java.util.*;
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
                    Codec.INT.optionalFieldOf("reference_level", 5).forGetter(Race::referenceLevel),
                    Codec.STRING.xmap(s -> Growth.valueOf(s.toUpperCase()), g -> g.name().toLowerCase()).listOf().optionalFieldOf("allow_status", List.of(Growth.TRAINED, Growth.RETIRED)).forGetter((r) -> r.allowStatus.stream().toList()),
                    Codec.BOOL.optionalFieldOf("exclusive", true).forGetter(Race::exclusive),
                    Codec.INT.optionalFieldOf("later_then", 0).forGetter(Race::laterThen),
                    ResourceLocation.CODEC.listOf().optionalFieldOf("after_race", List.of()).forGetter((r) -> r.afterRace.stream().toList()),
                    Codec.FLOAT.optionalFieldOf("texture_predicate_override").forGetter(r -> Optional.ofNullable(r.texturePredicateOverride))
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
    public final Set<Growth> allowStatus;
    public final boolean exclusive;
    public boolean exclusive() { return this.exclusive; }
    public final int laterThen;
    public int laterThen() { return this.laterThen; }
    public final Set<ResourceLocation> afterRace;
    public final Float texturePredicateOverride;
    public Float texturePredicateOverride() {
        return this.texturePredicateOverride;
    }
    public Race(ResourceLocation id, RaceRanking ranking, int length, int time, Set<Year> year, Surface surface,
                Set<ResourceLocation> tags, ResourceLocation field, Set<Integer> attrCorr, int referenceLevel, Set<Growth> allowStatus,
                boolean exclusive, int laterThen, Set<ResourceLocation> afterRace, Float texturePredicateOverride) {
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
        this.allowStatus = allowStatus;
        this.exclusive = exclusive;
        this.laterThen = laterThen;
        this.afterRace = afterRace;
        this.texturePredicateOverride = texturePredicateOverride;
    }

    public Race(ResourceLocation id, RaceRanking ranking, int length, int time, List<Year> year, Surface surface,
                List<ResourceLocation> tags, ResourceLocation field, List<Integer> attrCorr, int referenceLevel, List<Growth> allowStatus,
                boolean exclusive, int laterThen, List<ResourceLocation> afterRace, Optional<Float> texture) {
        this(id, ranking, length, time,
                new HashSet<>(year),
                surface,
                new HashSet<>(tags),
                field,
                new HashSet<>(attrCorr),
                referenceLevel,
                new HashSet<>(allowStatus),
                exclusive, laterThen, new HashSet<>(afterRace), texture.orElse(null)
        );
    }

    public boolean isAvailableToUmaSoul(ItemStack stack) {
        if (this.id.equals(RaceRegistry.DEFAULT.location())) return false;
        if (!(stack.is(ItemRegistry.UMA_SOUL.get()) &&
                ((this.ranking == RaceRanking.DEBUT) ^ UmaSoulUtils.hasUmaSoulDebut(stack)))) return false;
        CompoundTag tag = stack.getOrCreateTag();
        int last = tag.getInt("last_attend_time");
        if (this.exclusive) {
            boolean canAttend = false;
            for (Year year: this.year) {
                if (last < year.ordinal() * 24 + this.time) {
                    canAttend = true;
                    break;
                }
            }
            if (!canAttend) return false;
        }
        if (!this.afterRace.isEmpty()) {
            CompoundTag attended = tag.getCompound("attended");
            boolean canAttend = false;
            for (String key: attended.getAllKeys()){
                if (this.afterRace.contains(ResourceLocation.tryParse(key))) {
                    canAttend = true;
                    break;
                }
            }
            if (!canAttend) return false;
        }
        return this.allowStatus.contains(UmaSoulUtils.getGrowth(stack)) && last >= laterThen;
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

    public boolean isPassed(ItemStack stack, Level world) {
        ResourceLocation nameLoc = UmaSoulUtils.getName(stack);
        UmaData umaData = UmapyoiAPI.getUmaDataRegistry(world).getOptional(nameLoc).orElseGet(() -> {
            Umapyoi.getLogger().info("Warning: {} doesn't exist.", nameLoc);
            return UmaData.DEFAULT_UMA;
        });
        int[] propertiesAsLevel = UmaSoulUtils.getProperty(stack);
        Position umaPosition = umaData.position();
        double totalProperties = propertiesAsLevel[0] * (2 - umaPosition.speedFactor) * this.correction[0] + propertiesAsLevel[1]
                * (2 - umaPosition.staminaFactor) * this.correction[1] + propertiesAsLevel[2] * this.correction[2] +
                propertiesAsLevel[3] * this.correction[3] + propertiesAsLevel[4] * this.correction[4];
        Motivations motivation = UmaSoulUtils.getMotivation(stack);
        return totalProperties * motivation.getMultiplier() >= this.referenceLevel;
    }

    public void followUp(ItemStack stack, Level level) {
        CompoundTag tag = stack.getOrCreateTag();

        if (this.isPassed(stack, level)) {
            tags.stream().map(UmapyoiAPI.getRaceTagRegistry(level)::get).filter(Objects::nonNull)
                    .forEach((t) -> t.applyToUmaSoul(stack, this));
            ListTag list = tag.contains("won_races", CompoundTag.TAG_LIST) ? tag.getList("won_races", CompoundTag.TAG_STRING) : new ListTag();
            boolean has = false;
            for (int i = 0; i < list.size(); i++) {
                ResourceLocation rl = ResourceLocation.tryParse(list.getString(i));
                if (rl != null && rl.equals(this.id)) {
                    has = true;
                    break;
                }
            }
            if (!has) {
                list.add(StringTag.valueOf(this.id.toString()));
            }
            tag.put("won_races", list);
        }

        CompoundTag attended = tag.contains("attended", CompoundTag.TAG_COMPOUND) ? tag.getCompound("attended") : new CompoundTag();
        int count = attended.getInt(this.id.toString()) + 1;
        attended.putInt(this.id.toString(), count);
        tag.put("attended", attended);

        int lastAttend = tag.getInt("last_attend_time");
        this.year.stream().filter(y -> (y.ordinal() * 24 + this.time) > lastAttend).min(Comparator.naturalOrder()).ifPresentOrElse(
                y -> tag.putInt("last_attend_time", y.ordinal() * 24 + this.time),
                () -> {
                    Umapyoi.getLogger().error("Cannot calculate the right time.");
                    tag.putInt("last_attend_time", lastAttend + 1);
                }
        );

        if (this.ranking == RaceRanking.DEBUT) {
            tag.putBoolean("has_debut", true);
        }
    }

    public static class RaceBuilder {
        private RaceRanking ranking;
        private int length;
        private int time;
        private final Set<Year> year;
        private final Set<ResourceLocation> tags;
        private Surface surface;
        private ResourceLocation field;
        private final Set<Integer> attrCorr;
        private Integer referenceLevel;
        private boolean exclusive;
        private Set<Growth> allowStatus;
        private int later;
        private Set<ResourceLocation> afterRace;
        private Float texturePredicateOverride;

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
            this.exclusive = true;
            this.allowStatus = new HashSet<>(List.of(Growth.TRAINED, Growth.RETIRED));
            this.later = 0;
            this.afterRace = new HashSet<>();
            this.texturePredicateOverride = null;
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

        public RaceBuilder setAllowStatus(Growth... growth) {
            this.allowStatus = Arrays.stream(growth).collect(Collectors.toSet());
            return this;
        }

        public RaceBuilder addAllowStatus(Growth... growth) {
            this.allowStatus.addAll(Arrays.stream(growth).toList());
            return this;
        }

        public RaceBuilder setExclusive(boolean exclusive) {
            this.exclusive = exclusive;
            return this;
        }

        public RaceBuilder onlyIfLaterThen(int time) {
            this.later = time;
            return this;
        }

        public RaceBuilder onlyIfLaterThen(ResourceLocation... races){
            this.afterRace.addAll(Arrays.stream(races).toList());
            return this;
        }

        public RaceBuilder setTexture(Float texturePredicateOverride) {
            this.texturePredicateOverride = texturePredicateOverride;
            return this;
        }

        public Race create(ResourceLocation id) {
            return new Race(id, this.ranking, this.length, this.time, this.year, this.surface, this.tags, this.field,
                    this.attrCorr, Optional.ofNullable(this.referenceLevel).orElseGet(() ->
                        switch (this.ranking) {
                            case DEBUT, PREOP -> 5;
                            case OP -> 15;
                            case GIII -> 25;
                            case GII -> 30;
                            case GI -> 35;
                        }
            ), this.allowStatus, this.exclusive, this.later, this.afterRace, this.texturePredicateOverride);
        }
    }
}