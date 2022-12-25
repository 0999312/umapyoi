package net.tracen.umapyoi.client;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.client.model.SimpleUmapyoiModel;
import net.tracen.umapyoi.registry.umadata.UmaStatus;

public class ClientUtils {

    public static final ResourceLocation TRAINNING_SUIT = getModel("trainning_suit");
    public static final ResourceLocation SUMMER_UNIFORM = getModel("summer_uniform");
    public static final ResourceLocation WINTER_UNIFORM = getModel("winter_uniform");

    public static final ResourceLocation TRAINNING_SUIT_FLAT = getModel("trainning_suit_flat");
    public static final ResourceLocation SUMMER_UNIFORM_FLAT = getModel("summer_uniform_flat");
    public static final ResourceLocation WINTER_UNIFORM_FLAT = getModel("winter_uniform_flat");
    
    public static final ResourceLocation THREE_GODDESS = getModel("three_goddesses");

    public static ResourceLocation getModel(String name) {
        return getModel(Umapyoi.MODID, name);
    }

    public static ResourceLocation getModel(String modid, String name) {
        return new ResourceLocation(modid, name);
    }
    
    public static ResourceLocation getTexture(UmaStatus data) {
        return getTexture(data.name());
    }
    
    public static ResourceLocation getTexture(ResourceLocation name) {
        return new ResourceLocation(name.getNamespace(), "textures/model/" + name.getPath() + ".png");
    }
    
    public static void renderModelInInventory(int pPosX, int pPosY, int pScale, SimpleUmapyoiModel pModel, ResourceLocation texture) {
        renderModelInInventory(pPosX, pPosY, pScale, null, pModel, texture);;
    }
    
    public static void renderModelInInventory(int pPosX, int pPosY, int pScale, Quaternion pQuaternion, SimpleUmapyoiModel pModel, ResourceLocation texture) {
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.translate((double)pPosX, (double)pPosY, 1050.0D);
        posestack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        PoseStack posestack1 = new PoseStack();
        posestack1.translate(0.0D, 0.0D, 1000.0D);
        posestack1.scale((float)pScale, (float)pScale, (float)pScale);
        if(pQuaternion != null)
            posestack1.mulPose(pQuaternion);
        Lighting.setupForEntityInInventory();
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer vertexconsumer = multibuffersource$buffersource.getBuffer(RenderType.entityTranslucent(ClientUtils.getTexture(texture)));
        pModel.renderToBuffer(posestack1, vertexconsumer, 15728880, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        multibuffersource$buffersource.endBatch();
        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
        Lighting.setupFor3DItems();
     }
}
