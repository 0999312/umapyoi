package net.tracen.umapyoi.events.client;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.tracen.umapyoi.client.model.UmaPlayerModel;

public abstract class RenderingUmaSuitEvent extends RenderingModelEvent {

    public RenderingUmaSuitEvent(LivingEntity entity, UmaPlayerModel<LivingEntity> model, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
        super(entity, model, partialTick, poseStack, multiBufferSource, packedLight);
    }
    
    @Cancelable
    public static class Pre extends RenderingUmaSuitEvent {
        public Pre(LivingEntity entity, UmaPlayerModel<LivingEntity> model, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
            super(entity, model, partialTick, poseStack, multiBufferSource, packedLight);
        }
    }

    @Cancelable
    public static class Post extends RenderingUmaSuitEvent {
        public Post(LivingEntity entity, UmaPlayerModel<LivingEntity> model, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
            super(entity, model, partialTick, poseStack, multiBufferSource, packedLight);
        }
    }
}
