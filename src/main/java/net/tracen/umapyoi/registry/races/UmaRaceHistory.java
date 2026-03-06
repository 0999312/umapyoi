package net.tracen.umapyoi.registry.races;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.tracen.umapyoi.utils.Codecs;

import java.util.*;

public record UmaRaceHistory(Set<ResourceLocation> historyWon, Map<ResourceLocation, Integer> attended, int lastAttendTime,
                             boolean hasDebut, Map<ResourceLocation, Either<Integer, List<ResourceLocation>>> attendRaceTags) {
    public static final UmaRaceHistory DEFAULT = new UmaRaceHistory(Set.of(), Map.of(), 0, false, Map.of());

    public static final Codec<UmaRaceHistory> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    ResourceLocation.CODEC.listOf().<Set<ResourceLocation>>xmap(HashSet::new, ArrayList::new).optionalFieldOf("won_races", new HashSet<>()).forGetter(UmaRaceHistory::historyWon),
                    Codec.unboundedMap(ResourceLocation.CODEC, Codec.INT).optionalFieldOf("attended", Map.of()).forGetter(UmaRaceHistory::attended),
                    Codec.INT.optionalFieldOf("last_attend_time", -1).forGetter(UmaRaceHistory::lastAttendTime),
                    Codec.BOOL.optionalFieldOf("has_debut", false).forGetter(UmaRaceHistory::hasDebut),
                    Codec.unboundedMap(ResourceLocation.CODEC, Codec.either(Codec.INT, ResourceLocation.CODEC.listOf())).optionalFieldOf("attend_race_tag", Map.of()).forGetter(UmaRaceHistory::attendRaceTags)
            ).apply(instance, UmaRaceHistory::new)
    );

    public static final StreamCodec<ByteBuf, UmaRaceHistory> STREAM = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs.list()), v -> new ArrayList<>(v.historyWon),
            Codecs.streamUnboundedMap(ResourceLocation.STREAM_CODEC, ByteBufCodecs.INT), UmaRaceHistory::attended,
            ByteBufCodecs.INT, UmaRaceHistory::lastAttendTime,
            ByteBufCodecs.BOOL, UmaRaceHistory::hasDebut,
            Codecs.streamUnboundedMap(ResourceLocation.STREAM_CODEC, ByteBufCodecs.either(ByteBufCodecs.INT, ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs.list()))), UmaRaceHistory::attendRaceTags,
            (i, j, k, l, m) -> new UmaRaceHistory(
                    new HashSet<>(i), j, k, l, m
            )
    );
}
