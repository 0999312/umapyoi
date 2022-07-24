package net.trc.umapyoi.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.trc.umapyoi.Umapyoi;
import net.trc.umapyoi.client.model.UmaModels;

public class TrainningSuitRenderer extends AbstractSuitRenderer {

    @Override
    protected ResourceLocation getModel() {
        return UmaModels.TRAINNING_SUIT;
    }

    @Override
    protected ResourceLocation getTexture() {
        return new ResourceLocation(Umapyoi.MODID, "textures/model/trainning_suit.png");
    }

    @Override
    protected ResourceLocation getFlatModel() {
        return UmaModels.TRAINNING_SUIT_FLAT;
    }

    @Override
    protected ResourceLocation getFlatTexture() {
        return new ResourceLocation(Umapyoi.MODID, "textures/model/trainning_suit.png");
    }

}
