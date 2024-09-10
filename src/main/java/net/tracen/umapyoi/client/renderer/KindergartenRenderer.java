package net.tracen.umapyoi.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.utils.ClientUtils;

public class KindergartenRenderer extends AbstractSuitRenderer {

    @Override
    protected ResourceLocation getModel() {
        return ClientUtils.KINDERGARTEN_UNIFORM;
    }

    @Override
    protected ResourceLocation getTexture(boolean tanned) {
        return ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "textures/model/kindergarten_uniform.png");
    }

    @Override
    protected ResourceLocation getFlatModel() {
        return ClientUtils.KINDERGARTEN_UNIFORM;
    }

    @Override
    protected ResourceLocation getFlatTexture(boolean tanned) {
        return ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "textures/model/kindergarten_uniform.png");
    }


}
