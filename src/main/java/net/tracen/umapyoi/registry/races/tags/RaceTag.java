package net.tracen.umapyoi.registry.races.tags;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.registry.races.Race;
import net.tracen.umapyoi.registry.races.UmaRaceHistory;
import net.tracen.umapyoi.registry.umadata.UmaDataBasicStatus;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record RaceTag(int maximum, ResourceLocation id, boolean isUnique, int[] propertyReward) {
    public static final Codec<RaceTag> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    Codec.INT.fieldOf("max").forGetter(RaceTag::maximum),
                    ResourceLocation.CODEC.fieldOf("id").forGetter(RaceTag::id),
                    Codec.BOOL.fieldOf("is_unique").forGetter(RaceTag::isUnique),
                    Codec.INT_STREAM.xmap(IntStream::toArray, Arrays::stream)
                            .optionalFieldOf("property_reward", new int[5])
                            .forGetter(RaceTag::propertyReward)
            ).apply(instance, RaceTag::new));

    public static final ResourceKey<Registry<RaceTag>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "race_tags"));

    public boolean applyToUmaSoul(ItemStack soul, Race race) {
        boolean isFulfill;
        UmaRaceHistory hist = soul.getOrDefault(DataComponentsTypeRegistry.UMA_RACE_HISTORY, UmaRaceHistory.DEFAULT);
        HashMap<ResourceLocation, Either<Integer, List<ResourceLocation>>> tagRace = new HashMap<>(hist.attendRaceTags());
        if (!this.isUnique) {
            int current = tagRace.containsKey(this.id) ? tagRace.get(this.id).left().orElse(0) : 0;
            if (current > this.maximum) return false;
            current++;
            tagRace.put(this.id, Either.left(current));
            isFulfill = current == this.maximum;
        } else {
            Set<ResourceLocation> tagList = new HashSet<>(tagRace.containsKey(this.id) ? tagRace.get(this.id).right().orElse(List.of()) : List.of());
            int current = tagList.size();
            if (current >= this.maximum) return false;
            tagList.add(race.id);
            tagRace.put(this.id, Either.right(new ArrayList<>(tagList)));
            isFulfill = tagList.size() == this.maximum;
        }
        if (isFulfill) {
            soul.update(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS, UmaDataBasicStatus.DEFAULT_MAX_STATUS, s -> new UmaDataBasicStatus(
                    Math.min(s.speed() + propertyReward[0], 39),
                    Math.min(s.stamina() + propertyReward[1], 39),
                    Math.min(s.strength() + propertyReward[2], 39),
                    Math.min(s.guts() + propertyReward[3], 39),
                    Math.min(s.wisdom() + propertyReward[4], 39)
            ));

            UmaDataBasicStatus propertiesCeil = soul.getOrDefault(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS, UmaDataBasicStatus.DEFAULT_MAX_STATUS);
            soul.update(DataComponentsTypeRegistry.UMADATA_BASIC_STATUS, UmaDataBasicStatus.DEFAULT_STATUS, s -> new UmaDataBasicStatus(
                    Math.min(s.speed() + propertyReward[0], propertiesCeil.speed()),
                    Math.min(s.stamina() + propertyReward[1], propertiesCeil.stamina()),
                    Math.min(s.strength() + propertyReward[2], propertiesCeil.strength()),
                    Math.min(s.guts() + propertyReward[3], propertiesCeil.guts()),
                    Math.min(s.wisdom() + propertyReward[4], propertiesCeil.wisdom())
            ));
        }
        soul.update(DataComponentsTypeRegistry.UMA_RACE_HISTORY, UmaRaceHistory.DEFAULT, i -> new UmaRaceHistory(i.historyWon(), i.attended(), i.lastAttendTime(), i.hasDebut(), tagRace));
        return true;
    }

    public static Map<ResourceLocation, Integer> queryUmaSoulTags(ItemStack soul) {
        return soul.getOrDefault(DataComponentsTypeRegistry.UMA_RACE_HISTORY, UmaRaceHistory.DEFAULT).attendRaceTags().keySet().stream().collect(Collectors.toMap(
                Function.identity(),
                i -> queryUmaSoulTagCount(soul, i)
        ));
    }

    public static int queryUmaSoulTagCount(ItemStack soul, ResourceLocation id) {
        UmaRaceHistory hist = soul.getOrDefault(DataComponentsTypeRegistry.UMA_RACE_HISTORY, UmaRaceHistory.DEFAULT);
        Either<Integer, List<ResourceLocation>> tagRace = hist.attendRaceTags().get(id);
        if (tagRace == null) return 0;
        return Either.unwrap(tagRace.mapBoth(i -> i, List::size));
    }

    public int queryUmaSoulTagCount(ItemStack soul) {
        return queryUmaSoulTagCount(soul, this.id);
    }
}
