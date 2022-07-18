package net.trc.umapyoi.client.model;

import net.minecraft.resources.ResourceLocation;
import net.trc.umapyoi.Umapyoi;

public class UmaModels {
    public static final ResourceLocation COMMON_UMA = getModel("common_uma");
    public static final ResourceLocation GOLD_SHIP = getModel("gold_ship");
    public static final ResourceLocation TRAINNING_SUIT = getModel("trainning_suit");
    public static ResourceLocation getModel(String name) {
        return getModel(Umapyoi.MODID, name);
    }
    public static ResourceLocation getModel(String modid,String name) {
        return new ResourceLocation(modid, "models/entity/"+name+".json");
    }
}
