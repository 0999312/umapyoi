package net.trc.umapyoi.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.trc.umapyoi.Umapyoi;
import net.trc.umapyoi.client.model.UmaModels;

public class UmaUniformRenderer {
    public static class SummerUniformRenderer extends AbstractSuitRenderer {

        @Override
        protected ResourceLocation getModel() {
            return UmaModels.SUMMER_UNIFORM;
        }

        @Override
        protected ResourceLocation getTexture() {
            return new ResourceLocation(Umapyoi.MODID, "textures/model/summer_uniform.png");
        }

        @Override
        protected ResourceLocation getFlatModel() {
            return UmaModels.SUMMER_UNIFORM_FLAT;
        }

        @Override
        protected ResourceLocation getFlatTexture() {
            return new ResourceLocation(Umapyoi.MODID, "textures/model/summer_uniform.png");
        }

    }

    public static class WinterUniformRenderer extends AbstractSuitRenderer {

        @Override
        protected ResourceLocation getModel() {
            return UmaModels.WINTER_UNIFORM;
        }

        @Override
        protected ResourceLocation getTexture() {
            return new ResourceLocation(Umapyoi.MODID, "textures/model/winter_suit.png");
        }

        @Override
        protected ResourceLocation getFlatModel() {
            return UmaModels.WINTER_UNIFORM_FLAT;
        }

        @Override
        protected ResourceLocation getFlatTexture() {
            return new ResourceLocation(Umapyoi.MODID, "textures/model/winter_suit.png");
        }

    }
}
