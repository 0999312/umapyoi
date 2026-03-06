package net.tracen.umapyoi.client.model;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public abstract class DynamicItemBakedModel implements BakedModel {
    private final BakedModel original;
    private final ItemOverrides itemHandler;

    public DynamicItemBakedModel(BakedModel original, ModelBakery loader) {
        this.original = original;
        var missing = loader.getModel(ModelBakery.MISSING_MODEL_LOCATION);

        ModelBaker emptyBaker = new ModelBaker() {
            @Override
            public Function<Material, TextureAtlasSprite> getModelTextureGetter() {
                return null;
            }

            @Override
            public BakedModel bake(ResourceLocation location, ModelState state, Function<Material, TextureAtlasSprite> sprites) {
                return null;
            }

            @Override
            public @Nullable BakedModel bakeUncached(UnbakedModel unbakedModel, ModelState modelState, Function<Material, TextureAtlasSprite> function) {
                return null;
            }

            @Override
            public UnbakedModel getModel(ResourceLocation resourceLocation) {
                return null;
            }

            @Nullable
            @Override
            public BakedModel bake(ResourceLocation resourceLocation, ModelState modelState) {
                return null;
            }

            @Override
            public @Nullable UnbakedModel getTopLevelModel(ModelResourceLocation modelResourceLocation) {
                return null;
            }
        };
        this.itemHandler = new ItemOverrides(emptyBaker, missing, Collections.emptyList(), emptyBaker.getModelTextureGetter()) {
            @Override
            public BakedModel resolve(BakedModel original, ItemStack stack,
                                      @Nullable ClientLevel world, @Nullable LivingEntity entity, int seed) {
                return DynamicItemBakedModel.this.resolveModel(original, stack, world, entity, seed);
            }
        };
    }

    public abstract BakedModel resolveModel(BakedModel original, ItemStack stack,
                                            @Nullable ClientLevel world, @Nullable LivingEntity entity, int seed);


    public BakedModel getOriginalModel() {
        return this.original;
    }

    @Override
    public ItemOverrides getOverrides() {
        return itemHandler;
    }


    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
        return Collections.emptyList();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return original.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return original.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return original.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return original.isCustomRenderer();
    }

    @SuppressWarnings("deprecation")
    @Override
    public TextureAtlasSprite getParticleIcon() {
        return original.getParticleIcon();
    }

    @SuppressWarnings("deprecation")
    @Override
    public ItemTransforms getTransforms() {
        return original.getTransforms();
    }
}
