package net.tracen.umapyoi.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.utils.ClientUtils;

public class SwimsuitRenderer extends AbstractSuitRenderer {

    @Override
    protected ResourceLocation getModel() {
        return ClientUtils.SWIMSUIT;
    }

    @Override
    protected ResourceLocation getTexture(boolean tanned) {
        return tanned ? ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "textures/model/swimsuit_tanned.png")
                : ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "textures/model/swimsuit.png");
    }

    @Override
    protected ResourceLocation getFlatModel() {
        return ClientUtils.SWIMSUIT_FLAT;
    }

    @Override
    protected ResourceLocation getFlatTexture(boolean tanned) {
        return tanned ? ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "textures/model/swimsuit_flat_tanned.png")
                : ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "textures/model/swimsuit_flat.png");
    }

}
