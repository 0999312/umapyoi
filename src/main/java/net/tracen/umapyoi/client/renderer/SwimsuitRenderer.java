package net.tracen.umapyoi.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.utils.ClientUtils;

public class SwimsuitRenderer extends AbstractSuitRenderer {

    @Override
    public ResourceLocation getModel(ItemStack stack) {
        return ClientUtils.SWIMSUIT;
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, boolean tanned) {
        return tanned ? ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "textures/model/swimsuit_tanned.png")
                : ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "textures/model/swimsuit.png");
    }

    @Override
    public ResourceLocation getFlatModel(ItemStack stack) {
        return ClientUtils.SWIMSUIT_FLAT;
    }

    @Override
    public ResourceLocation getFlatTexture(ItemStack stack, boolean tanned) {
        return tanned ? ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "textures/model/swimsuit_flat_tanned.png")
                : ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "textures/model/swimsuit_flat.png");
    }

}
