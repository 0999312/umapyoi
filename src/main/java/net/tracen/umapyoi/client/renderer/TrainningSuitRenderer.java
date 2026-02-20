package net.tracen.umapyoi.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.utils.ClientUtils;

public class TrainningSuitRenderer extends AbstractSuitRenderer {

    @Override
    public ResourceLocation getModel(ItemStack stack) {
        return ClientUtils.TRAINNING_SUIT;
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, boolean tanned) {
        return tanned ? new ResourceLocation(Umapyoi.MODID, "textures/model/trainning_suit_tanned.png")
                : new ResourceLocation(Umapyoi.MODID, "textures/model/trainning_suit.png");
    }

    @Override
    public ResourceLocation getFlatModel(ItemStack stack) {
        return ClientUtils.TRAINNING_SUIT_FLAT;
    }

    @Override
    public ResourceLocation getFlatTexture(ItemStack stack, boolean tanned) {
        return tanned ? new ResourceLocation(Umapyoi.MODID, "textures/model/trainning_suit_tanned.png")
                : new ResourceLocation(Umapyoi.MODID, "textures/model/trainning_suit.png");
    }

}
