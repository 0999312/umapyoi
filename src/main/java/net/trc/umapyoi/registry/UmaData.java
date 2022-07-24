package net.trc.umapyoi.registry;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.trc.umapyoi.Umapyoi;

public class UmaData extends ForgeRegistryEntry<UmaData> {
    public static final Codec<UmaData> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(Codec.STRING.fieldOf("name").forGetter(UmaData::name),
                    Codec.INT.listOf().fieldOf("property").forGetter(UmaData::property),
                    Codec.INT.listOf().fieldOf("max_property").forGetter(UmaData::maxProperty),
                    Codec.BOOL.optionalFieldOf("is_flat", false).forGetter(UmaData::isFlat)
                    )
            .apply(instance, UmaData::new));

    public static final ResourceKey<Registry<UmaData>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(new ResourceLocation(Umapyoi.MODID, "umadata"));

    private final String name;
    private final List<Integer> property;
    private final List<Integer> max_property;
    private boolean isFlat = false;
    
    public UmaData(final String value, List<Integer> s, List<Integer> max_s) {
        this(value, s, max_s, false);
    }

    public UmaData(final String value, List<Integer> s, List<Integer> max_s, boolean flat) {
        this.name = value;
        this.property = s;
        this.max_property = max_s;
        this.isFlat = flat;
    }

    public String name() {
        return name;
    }

    public List<Integer> property() {
        return property;
    }

    public List<Integer> maxProperty() {
        return max_property;
    }
    
    public boolean isFlat() {
        return isFlat;
    }
}
