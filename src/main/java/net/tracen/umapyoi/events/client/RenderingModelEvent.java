package net.tracen.umapyoi.events.client;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.Event;
import net.tracen.umapyoi.client.model.UmaPlayerModel;

@OnlyIn(Dist.CLIENT)
public abstract class RenderingModelEvent extends Event {
    private final LivingEntity entity;
    private final UmaPlayerModel<LivingEntity> model;
    private final float partialTick;
    private final PoseStack poseStack;
    private final MultiBufferSource multiBufferSource;
    private final int packedLight;
    
    public RenderingModelEvent(LivingEntity entity, UmaPlayerModel<LivingEntity> model, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
        this.entity = entity;
        this.model = model;
        this.partialTick = partialTick;
        this.poseStack = poseStack;
        this.multiBufferSource = multiBufferSource;
        this.packedLight = packedLight;
    }

    public LivingEntity getWearer() {
        return entity;
    }

    public UmaPlayerModel<LivingEntity> getModel() {
        return model;
    }

    public float getPartialTick() {
        return partialTick;
    }

    public PoseStack getPoseStack() {
        return poseStack;
    }

    public MultiBufferSource getMultiBufferSource() {
        return multiBufferSource;
    }

    public int getPackedLight() {
        return packedLight;
    }

}
