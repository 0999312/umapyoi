package net.tracen.umapyoi.registry.umadata;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class UmaStatus {
    public static final Codec<UmaStatus> CODEC = RecordCodecBuilder
            .create(instance -> instance
                    .group(ResourceLocation.CODEC.fieldOf("name").forGetter(UmaStatus::name),
                            Codec.INT_STREAM.xmap(IntStream::toArray, Arrays::stream).fieldOf("property")
                                    .forGetter(UmaStatus::property))
                    .apply(instance, (name, property) -> {
                        return UmaStatus.builder(name).property(property).build();
                    }));

    private ResourceLocation name;
    private int[] property;

    private Motivations motivation = Motivations.NORMAL;
    private Growth growth = Growth.UNTRAINED;

    private UmaStatus(Builder builder) {
        this.name = builder.name;
        this.property = builder.property;
    }

    public ResourceLocation name() {
        return name;
    }

    public int[] property() {
        return property;
    }

    public Motivations getMotivation() {
        return motivation;
    }
    
    public Growth getGrowth() {
        return growth;
    }

    public void setMotivation(Motivations motivation) {
        this.motivation = motivation;
    }
    
    public void setGrowth(Growth growth) {
        this.growth = growth;
    }

    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("name", name.toString());
        compoundTag.putIntArray("property", property);
        compoundTag.putString("motivation", motivation.name().toLowerCase());
        compoundTag.putString("growth", growth.name().toLowerCase());
        return compoundTag;
    }

    public void deserializeNBT(CompoundTag tag) {
        this.name = new ResourceLocation(tag.getString("name"));
        this.property = tag.getIntArray("property");
        this.motivation = Motivations.valueOf(tag.getString("motivation").toUpperCase());
        this.growth = Growth.valueOf(tag.getString("growth").toUpperCase());
    }

    public static Builder builder(ResourceLocation name) {
        return new Builder(name);
    }

    public static class Builder {
        private final ResourceLocation name;
        private int[] property;

        private Builder(ResourceLocation name) {
            this.name = Objects.requireNonNull(name);
        }

        public Builder property(int[] property) {
            this.property = property;
            return this;
        }

        public Builder property(int speed, int stamina, int strength, int guts, int wisdom) {
            return this.property(new int[] { speed, stamina, strength, guts, wisdom });
        }

        public UmaStatus build() {
            return new UmaStatus(this);
        }
    }

    public static enum Growth{
        UNTRAINED,
        TRAINED,
        RETIRED
    }
}
