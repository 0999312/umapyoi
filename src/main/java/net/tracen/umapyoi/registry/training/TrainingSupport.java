package net.tracen.umapyoi.registry.training;

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
import net.tracen.umapyoi.registry.TrainingSupportRegistry;

public class TrainingSupport{
    private String descriptionId;

    public static final Codec<TrainingSupport> CODEC = ResourceLocation.CODEC
            .xmap(loc -> TrainingSupportRegistry.REGISTRY.get().getValue(loc), instance -> TrainingSupportRegistry.REGISTRY.get().getKey(instance));

    public static final ResourceKey<Registry<TrainingSupport>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(new ResourceLocation(Umapyoi.MODID, "training_support"));

    public TrainingSupport() {
    }

    public boolean applySupport(ItemStack soul, SupportStack stack) {
        return true;
    }

    public String toString() {
        return TrainingSupportRegistry.REGISTRY.get().getKey(this).toString();
    }

    public Component getDescription() {
        return Component.translatable(this.getDescriptionId());
    }

    public Component getDescription(SupportStack stack) {
        return this.getFullDescription(stack.getLevel());
    }

    protected String getOrCreateDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("support", TrainingSupportRegistry.REGISTRY.get().getKey(this));
        }
        return this.descriptionId;
    }

    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }

    public Component getFullDescription(int pLevel) {
        MutableComponent mutablecomponent = Component.translatable(this.getDescriptionId());
        mutablecomponent.withStyle(ChatFormatting.GRAY);
        mutablecomponent.append(" ").append(Component.translatable("enchantment.level." + pLevel));
        return mutablecomponent;
    }
}
