package net.tracen.umapyoi.registry.umadata;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.tracen.umapyoi.Umapyoi;

public class UmaData extends ForgeRegistryEntry<UmaData> {
    public static final Codec<UmaData> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    UmaStatus.CODEC.fieldOf("status").forGetter(UmaData::status),
                    ResourceLocation.CODEC.fieldOf("uniqueSkill").forGetter(UmaData::uniqueSkill)
                  )
            .apply(instance, UmaData::new)
            );

    public static final ResourceKey<Registry<UmaData>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(new ResourceLocation(Umapyoi.MODID, "umadata"));

    private final UmaStatus status;
    private final ResourceLocation uniqueSkill;

    private String descriptionId;

    public UmaData(UmaStatus status, ResourceLocation uniqueSkill) {
        this.status = status;
        this.uniqueSkill = uniqueSkill;
    }
    
    public UmaStatus status() {
        return status;
    }

    public ResourceLocation uniqueSkill() {
        return uniqueSkill;
    }

    @Override
    public int hashCode() {
        return this.getRegistryName().hashCode();
    }

    public String toString() {
        return this.getRegistryName().toString();
    }
    
    public Component getDescription() {
        return new TranslatableComponent(this.getDescriptionId());
    }

    protected String getOrCreateDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("umadata", this.getRegistryName());
        }
        return this.descriptionId;
    }

    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }
}
