package net.tracen.umapyoi.registry.umadata;

import java.util.Arrays;
import java.util.stream.IntStream;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.tracen.umapyoi.Umapyoi;

public class UmaData extends ForgeRegistryEntry<UmaData> {
    public static final Codec<UmaData> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(UmaStatus.CODEC.fieldOf("status").forGetter(UmaData::status),
                    Codec.INT_STREAM.xmap(IntStream::toArray, Arrays::stream).fieldOf("growth").forGetter(
                            UmaData::growth),
                    Codec.BOOL.optionalFieldOf("is_flat", false).forGetter(UmaData::isFlat))
            .apply(instance, UmaData::new));

    public static final ResourceKey<Registry<UmaData>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(new ResourceLocation(Umapyoi.MODID, "umadata"));

    private final UmaStatus status;
    private final int[] growth;
    
//    private ;
    
    private boolean isFlat = false;

    public UmaData(UmaStatus status, int[] growth) {
        this(status, growth, false);
    }

    public UmaData(UmaStatus status, int[] growth, boolean flat) {
        this.status = status;
        this.growth = growth;
        this.isFlat = flat;
    }

    public UmaStatus status() {
        return status;
    }

    public int[] growth() {
        return growth;
    }

    public boolean isFlat() {
        return isFlat;
    }

    public String toString() {
        return "umadata:" + this.getRegistryName().toString();
    }
}
