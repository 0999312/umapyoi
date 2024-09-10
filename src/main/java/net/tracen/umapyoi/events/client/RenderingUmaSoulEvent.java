package net.tracen.umapyoi.events.client;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.ICancellableEvent;
import net.tracen.umapyoi.client.model.UmaPlayerModel;

public abstract class RenderingUmaSoulEvent extends RenderingModelEvent {

    public RenderingUmaSoulEvent(LivingEntity entity, UmaPlayerModel<LivingEntity> model, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
        super(entity, model, partialTick, poseStack, multiBufferSource, packedLight);
    }
    
    public static class Pre extends RenderingUmaSoulEvent implements ICancellableEvent{
        public Pre(LivingEntity entity, UmaPlayerModel<LivingEntity> model, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
            super(entity, model, partialTick, poseStack, multiBufferSource, packedLight);
        }
    }

    public static class Post extends RenderingUmaSoulEvent {
        public Post(LivingEntity entity, UmaPlayerModel<LivingEntity> model, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
            super(entity, model, partialTick, poseStack, multiBufferSource, packedLight);
        }
    }
}
