package net.tracen.umapyoi.registry.umadata;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.resources.ResourceLocation;

public class UmaStatus {
    public static final Codec<UmaStatus> CODEC = RecordCodecBuilder
            .create(instance -> instance
                .group(
                        ResourceLocation.CODEC.fieldOf("name").forGetter(UmaStatus::name),
                        Codec.INT_STREAM.xmap(IntStream::toArray, Arrays::stream).fieldOf("property").forGetter(UmaStatus::property),
                        Motivations.CODEC.fieldOf("motivation").orElse(Motivations.NORMAL).forGetter(UmaStatus::motivation),
                        Growth.CODEC.fieldOf("growth").orElse(Growth.UNTRAINED).forGetter(UmaStatus::growth)
                      )
                .apply(instance, (name, property, motivation, growth) -> {
                    return UmaStatus.builder(name).property(property).motivation(motivation).growth(growth).build();
                }));

    private ResourceLocation name;
    private int[] property;

    private Motivations motivation;
    private Growth growth;

    private UmaStatus(Builder builder) {
        this.name = builder.name;
        this.property = builder.property;
        this.motivation = builder.motivation;
        this.growth = builder.growth;
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.property, this.growth, this.motivation);
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof UmaStatus otherStatus) {
            return Objects.equals(this.name, otherStatus.name) && 
                   Objects.equals(this.property, otherStatus.property) &&
                   Objects.equals(this.growth, otherStatus.growth) &&
                   Objects.equals(this.motivation, otherStatus.motivation);
        }
        return false;
    }

    public ResourceLocation name() {
        return name;
    }

    public int[] property() {
        return property;
    }

    public Motivations motivation() {
        return motivation;
    }
    
    public Growth growth() {
        return growth;
    }

    public void setMotivation(Motivations motivation) {
        this.motivation = motivation;
    }
    
    public void setGrowth(Growth growth) {
        this.growth = growth;
    }

    public static Builder builder(ResourceLocation name) {
        return new Builder(name);
    }

    public static class Builder {
        private final ResourceLocation name;
        private int[] property;
        private Motivations motivation = Motivations.NORMAL;
        private Growth growth = Growth.UNTRAINED;

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

        public Builder motivation(Motivations motivation) {
            this.motivation = motivation;
            return this;
        }

        public Builder growth(Growth growth) {
            this.growth = growth;
            return this;
        }

        public UmaStatus build() {
            return new UmaStatus(this);
        }
    }

    public static enum Growth{
        UNTRAINED,
        TRAINED,
        RETIRED;
        
        public static final Codec<Growth> CODEC = Codec.STRING.xmap(
                string -> Growth.valueOf(string.toUpperCase()),
                instance -> instance.name().toLowerCase()
        );
    }
}
