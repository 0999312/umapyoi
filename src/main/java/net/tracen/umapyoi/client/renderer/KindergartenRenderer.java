package net.tracen.umapyoi.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.utils.ClientUtils;

public class KindergartenRenderer extends AbstractSuitRenderer {

    @Override
    public ResourceLocation getModel(ItemStack stack) {
        return ClientUtils.KINDERGARTEN_UNIFORM;
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, boolean tanned) {
        return ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "textures/model/kindergarten_uniform.png");
    }

    @Override
    public ResourceLocation getFlatModel(ItemStack stack) {
        return ClientUtils.KINDERGARTEN_UNIFORM;
    }

    @Override
    public ResourceLocation getFlatTexture(ItemStack stack, boolean tanned) {
        return ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "textures/model/kindergarten_uniform.png");
    }


}
