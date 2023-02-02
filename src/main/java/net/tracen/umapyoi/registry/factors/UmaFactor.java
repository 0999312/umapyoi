package net.tracen.umapyoi.registry.factors;

import com.mojang.serialization.Codec;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.capability.IUmaCapability;
import net.tracen.umapyoi.registry.UmaFactorRegistry;

public class UmaFactor extends ForgeRegistryEntry<UmaFactor>{
    private final FactorType type;
    private String descriptionId;

    public static final ResourceKey<Registry<UmaFactor>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(new ResourceLocation(Umapyoi.MODID, "factor"));
    
    public static final Codec<UmaFactor> CODEC = ResourceLocation.CODEC.xmap(
            loc -> UmaFactorRegistry.REGISTRY.get().getValue(loc),
            instance -> instance.getRegistryName()
    );
    
    public UmaFactor(FactorType type) {
        this.type = type;
    }
    
    public void applyFactor(IUmaCapability cap, UmaFactorStack stack) {
        
    }

    public FactorType getFactorType() {
        return type;
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
    
    public Component getDescription(UmaFactorStack stack) {
        return this.getDescription();
    }

    protected String getOrCreateDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("uma_factor", this.getRegistryName());
        }
        return this.descriptionId;
    }

    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }

    public Component getFullDescription(int pLevel) {
        MutableComponent mutablecomponent = new TranslatableComponent(this.getDescriptionId());
        mutablecomponent.withStyle(ChatFormatting.GRAY);
        mutablecomponent.append(" ").append(new TranslatableComponent("enchantment.level." + pLevel));
        return mutablecomponent;
    }
}
