package net.tracen.umapyoi.registry.factors;

import com.mojang.serialization.Codec;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.UmaFactorRegistry;

public class UmaFactor{
    private final FactorType type;
    private String descriptionId;

    public static final ResourceKey<Registry<UmaFactor>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(new ResourceLocation(Umapyoi.MODID, "factor"));

    public static final Codec<UmaFactor> CODEC = ResourceLocation.CODEC
            .xmap(loc -> UmaFactorRegistry.REGISTRY.get().getValue(loc), instance -> UmaFactorRegistry.REGISTRY.get().getKey(instance));

    public UmaFactor(FactorType type) {
        this.type = type;
    }

    public void applyFactor(ItemStack soul, UmaFactorStack stack) {

    }

    public FactorType getFactorType() {
        return type;
    }

    public String toString() {
        return UmaFactorRegistry.REGISTRY.get().getKey(this).toString();
    }

    public Component getDescription() {
        return Component.translatable(this.getDescriptionId());
    }

    public Component getDescription(UmaFactorStack stack) {
        return this.getDescription();
    }

    protected String getOrCreateDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("uma_factor", UmaFactorRegistry.REGISTRY.get().getKey(this));
        }
        return this.descriptionId;
    }

    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }

    public Component getFullDescription(int pLevel) {
        MutableComponent mutablecomponent = this.getDescription().copy();
        mutablecomponent.withStyle(ChatFormatting.GRAY);
        mutablecomponent.append(" ").append(Component.translatable("enchantment.level." + pLevel));
        return mutablecomponent;
    }
}
