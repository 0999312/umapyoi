package net.tracen.umapyoi.registry.races.field;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.utils.Distance;
import net.tracen.umapyoi.utils.Surface;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public record RaceField(ResourceLocation id, Set<Distance> turfs, Set<Distance> dirts, Set<Distance> synthetics) implements Comparable<RaceField> {
    public static final Codec<RaceField> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    ResourceLocation.CODEC.fieldOf("id").forGetter(RaceField::id),
                    Distance.CODEC.listOf().xmap(Set::copyOf, ArrayList::new).fieldOf("turfs").forGetter(RaceField::turfs),
                    Distance.CODEC.listOf().xmap(Set::copyOf, ArrayList::new).fieldOf("dirts").forGetter(RaceField::dirts),
                    Distance.CODEC.listOf().xmap(Set::copyOf, ArrayList::new).fieldOf("synthetics").forGetter(RaceField::synthetics)
            ).apply(instance, RaceField::new));

    public static final ResourceKey<Registry<RaceField>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(new ResourceLocation(Umapyoi.MODID, "race_field"));

    public int compareToAbs(Distance distance, Surface surface) {
        AtomicInteger eval = new AtomicInteger(-1);

        this.turfs.stream().map(exist -> Mth.abs(exist.compareTo(distance))).min(Comparator.naturalOrder())
                .ifPresent(val -> eval.set(val + ((surface == Surface.TURF) ? 0 : 1)));
        this.dirts.stream().map(exist -> Mth.abs(exist.compareTo(distance))).min(Comparator.naturalOrder())
                .map(i -> i + (surface == Surface.DIRT ? 0 : 1))
                .ifPresent(val -> {if (eval.get() == -1 || eval.get() > val) eval.set(val + ((surface == Surface.TURF) ? 0 : 1));});
        this.synthetics.stream().map(exist -> Mth.abs(exist.compareTo(distance))).min(Comparator.naturalOrder())
                .map(i -> i + (surface == Surface.SYNTHETIC ? 0 : 1))
                .ifPresent(val -> {if (eval.get() == -1 || eval.get() > val) eval.set(val + ((surface == Surface.SYNTHETIC) ? 0 : 1));});

        return eval.get();
    }

    @Override
    public int compareTo(@Nonnull RaceField o) {
        return this.id().compareTo(o.id());
    }
}
