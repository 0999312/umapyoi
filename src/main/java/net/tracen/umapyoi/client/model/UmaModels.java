package net.tracen.umapyoi.client.model;

import net.minecraft.resources.ResourceLocation;
import net.tracen.umapyoi.Umapyoi;

public class UmaModels {

    public static final ResourceLocation TRAINNING_SUIT = getModel("trainning_suit");
    public static final ResourceLocation SUMMER_UNIFORM = getModel("summer_uniform");
    public static final ResourceLocation WINTER_UNIFORM = getModel("winter_uniform");

    public static final ResourceLocation TRAINNING_SUIT_FLAT = getModel("trainning_suit_flat");
    public static final ResourceLocation SUMMER_UNIFORM_FLAT = getModel("summer_uniform_flat");
    public static final ResourceLocation WINTER_UNIFORM_FLAT = getModel("winter_uniform_flat");

    public static ResourceLocation getModel(String name) {
        return getModel(Umapyoi.MODID, name);
    }

    public static ResourceLocation getModel(String modid, String name) {
        return new ResourceLocation(modid, name);
    }
}
