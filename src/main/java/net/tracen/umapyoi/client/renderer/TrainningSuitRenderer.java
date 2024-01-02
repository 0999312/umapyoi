package net.tracen.umapyoi.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.utils.ClientUtils;

public class TrainningSuitRenderer extends AbstractSuitRenderer {

    @Override
    protected ResourceLocation getModel() {
        return ClientUtils.TRAINNING_SUIT;
    }

    @Override
    protected ResourceLocation getTexture(boolean tanned) {
        return tanned ? new ResourceLocation(Umapyoi.MODID, "textures/model/trainning_suit_tanned.png")
                : new ResourceLocation(Umapyoi.MODID, "textures/model/trainning_suit.png");
    }

    @Override
    protected ResourceLocation getFlatModel() {
        return ClientUtils.TRAINNING_SUIT_FLAT;
    }

    @Override
    protected ResourceLocation getFlatTexture(boolean tanned) {
        return tanned ? new ResourceLocation(Umapyoi.MODID, "textures/model/trainning_suit_tanned.png")
                : new ResourceLocation(Umapyoi.MODID, "textures/model/trainning_suit.png");
    }

}
