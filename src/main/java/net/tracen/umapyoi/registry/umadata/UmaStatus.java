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
                                    .forGetter(UmaStatus::property),
                            Codec.INT_STREAM.xmap(IntStream::toArray, Arrays::stream).fieldOf("maxProperty")
                                    .forGetter(UmaStatus::maxProperty))
                    .apply(instance, (name, property, maxProperty) -> {
                        return new UmaStatus.Builder(name).property(property).maxProperty(maxProperty).build();
                    }));

    public static final int SPEED = 0;
    public static final int STAMINA = 1;
    public static final int STRENGTH = 2;
    public static final int GUTS = 3;
    public static final int WISDOM = 4;

    private ResourceLocation name;
    private int[] property;
    private int[] maxProperty;

    private int energy = 100;
    private int maxEnergy = 100;

    private Motivations motivation = Motivations.NORMAL;

    private UmaStatus(Builder builder) {
        this.name = builder.name;
        this.property = builder.property;
        this.maxProperty = builder.maxProperty;
    }

    public ResourceLocation name() {
        return name;
    }

    public int[] property() {
        return property;
    }

    public int[] maxProperty() {
        return maxProperty;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = Math.min(energy, this.getMaxEnergy());
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(int maxEnergy) {
        this.maxEnergy = maxEnergy;
        if (this.getMaxEnergy() < this.getEnergy()) {
            this.setEnergy(maxEnergy);
        }
    }

    public Motivations getMotivation() {
        return motivation;
    }

    public void setMotivation(Motivations motivation) {
        this.motivation = motivation;
    }

    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("name", name.toString());
        compoundTag.putIntArray("property", property);
        compoundTag.putIntArray("maxProperty", maxProperty);
        compoundTag.putInt("energy", energy);
        compoundTag.putInt("maxEnergy", maxEnergy);
        compoundTag.putString("maxEnergy", motivation.getName());
        return compoundTag;
    }

    public void deserializeNBT(CompoundTag tag) {
        this.name = new ResourceLocation(tag.getString("name"));
        this.property = tag.getIntArray("property");
        this.maxProperty = tag.getIntArray("maxProperty");
        this.energy = tag.getInt("energy");
        this.maxEnergy = tag.getInt("maxEnergy");
        this.motivation = Motivations.getMotivation(tag.getString("motivation"));
    }

    public static Builder builder(ResourceLocation name) {
        return new Builder(name);
    }

    public static class Builder {
        private final ResourceLocation name;
        private int[] property;
        private int[] maxProperty = new int[] { 1200, 1200, 1200, 1200, 1200 };

        private Builder(ResourceLocation name) {
            this.name = Objects.requireNonNull(name);
        }

        public Builder property(int[] property) {
            this.property = property;
            return this;
        }

        public Builder maxProperty(int[] maxProperty) {
            this.maxProperty = maxProperty;
            return this;
        }

        public Builder property(int speed, int stamina, int strength, int guts, int wisdom) {
            return this.property(new int[] { speed, stamina, strength, guts, wisdom });
        }

        public Builder maxProperty(int speed, int stamina, int strength, int guts, int wisdom) {
            this.maxProperty = new int[] { speed, stamina, strength, guts, wisdom };
            return this;
        }

        public UmaStatus build() {
            return new UmaStatus(this);
        }

    }

}
