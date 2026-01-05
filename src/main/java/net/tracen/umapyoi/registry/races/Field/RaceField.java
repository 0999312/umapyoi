package net.tracen.umapyoi.registry.races.Field;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.tracen.umapyoi.Umapyoi;

public record RaceField(ResourceLocation id) {
    public static final Codec<RaceField> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    ResourceLocation.CODEC.fieldOf("id").forGetter(RaceField::id)
            ).apply(instance, RaceField::new));
    public static final ResourceKey<Registry<RaceField>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(new ResourceLocation(Umapyoi.MODID, "race_field"));
}
