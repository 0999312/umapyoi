package net.tracen.umapyoi.registry.training;

import com.mojang.serialization.Codec;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.TrainingSupportRegistry;

public class TrainingSupport extends ForgeRegistryEntry<TrainingSupport> {
    private String descriptionId;

    public static final Codec<TrainingSupport> CODEC = ResourceLocation.CODEC
            .xmap(loc -> TrainingSupportRegistry.REGISTRY.get().getValue(loc), instance -> instance.getRegistryName());

    public static final ResourceKey<Registry<TrainingSupport>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(new ResourceLocation(Umapyoi.MODID, "training_support"));

    public TrainingSupport() {
    }

    public void applySupport(ItemStack soul, SupportStack stack) {

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

    public Component getDescription(SupportStack stack) {
        return this.getFullDescription(stack.getLevel());
    }

    protected String getOrCreateDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("support", this.getRegistryName());
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
