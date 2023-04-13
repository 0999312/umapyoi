package net.tracen.umapyoi.clothing;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ClothingData extends ForgeRegistryEntry<ClothingData> {
    private ResourceLocation defaultModel;

    public ResourceLocation getDefaultModel() {
        return defaultModel;
    }

}
