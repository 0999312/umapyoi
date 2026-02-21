package net.tracen.umapyoi.compat.sbw;

import com.atsuishio.superbwarfare.api.event.RenderPlayerArmEvent;
import com.mojang.blaze3d.vertex.VertexConsumer;

import cn.mcmod_mmf.mmlib.client.model.pojo.BedrockModelPOJO;
import cn.mcmod_mmf.mmlib.utils.ClientUtil;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.client.model.UmaCostumeModelUtils;
import net.tracen.umapyoi.client.model.UmaPlayerModel;
import net.tracen.umapyoi.utils.ClientUtils;
import net.tracen.umapyoi.utils.UmaSoulUtils;

import static com.atsuishio.superbwarfare.client.animation.AnimationHelper.renderPartOverBone;

public class SBWCompat {
    @SubscribeEvent
    public static void onArmRendering(RenderPlayerArmEvent event) {
        Player player = event.getLocalPlayer();
        ItemStack umasoul = UmapyoiAPI.getRenderingUmaSoul(player);
        ItemStack umasuit = UmapyoiAPI.getUmaSuit(player);
        if (!umasoul.isEmpty()) {
            ResourceLocation name = UmaSoulUtils.getName(umasoul);
            VertexConsumer vertexconsumer = event.getCurrentBuffer()
                    .getBuffer(RenderType.entityTranslucent(ClientUtils.getTexture(name)));
            var pojo = ClientUtil.getModelPOJO(name);
            if(!umasuit.isEmpty()) {
                boolean tanned = ClientUtils.isTannedSkin(umasoul);
                vertexconsumer = event.getCurrentBuffer()
                        .getBuffer(RenderType.entityTranslucent(UmaCostumeModelUtils.getCostumeTexture(umasuit, tanned)));
                pojo = ClientUtil.getModelPOJO(UmaCostumeModelUtils.getCostumeModel(umasuit));
            }
            renderArmModel(event, name, vertexconsumer, pojo);
            event.setCanceled(true);
        }
    }

    private static final UmaPlayerModel<LivingEntity> baseModel = new UmaPlayerModel<>();

    private static void renderArmModel(RenderPlayerArmEvent event, ResourceLocation name, VertexConsumer vertexconsumer,
                                       BedrockModelPOJO pojo) {
        if(baseModel.needRefresh(pojo))
            baseModel.loadModel(pojo);

        baseModel.setModelProperties(event.getLocalPlayer());
        baseModel.attackTime = 0.0F;
        baseModel.crouching = false;
        baseModel.swimAmount = 0.0F;
        baseModel.setupAnim(event.getLocalPlayer(), 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

        if (event.getArm() == HumanoidArm.RIGHT) {
            // baseModel.rightArm.xRot = 0.0F;
            // baseModel.rightArm.x -= 1F;
            event.getStack().translate(-0.0625f, 0.125f, 0.0f);
            if (event.isUseOldHandRender()) {
                baseModel.rightArm.xRot = 0f;
                baseModel.rightArm.yRot = 0f;
                baseModel.rightArm.zRot = 0f;
                baseModel.rightArm.x = event.getBone().getPivotX();
                baseModel.rightArm.y = event.getBone().getPivotY();
                baseModel.rightArm.z = event.getBone().getPivotZ();
            } else {
                baseModel.rightArm.xRot = 0f;
                baseModel.rightArm.yRot = (float) Math.PI;
                baseModel.rightArm.zRot = (float) Math.PI;
                baseModel.rightArm.x = event.getBone().getPivotX();
                baseModel.rightArm.y = event.getBone().getPivotY() + 7f;
                baseModel.rightArm.z = event.getBone().getPivotZ();
            }
            baseModel.rightArm.render(event.getStack(), vertexconsumer, event.getPackedLightIn(),
                    OverlayTexture.NO_OVERLAY);
            if(baseModel.isEmissive()) {
                VertexConsumer emissiveConsumer = event.getCurrentBuffer()
                        .getBuffer(RenderType.entityTranslucentEmissive(ClientUtils.getEmissiveTexture(name)));
                baseModel.rightArm.renderEmissive(event.getStack(), emissiveConsumer, event.getPackedLightIn(),
                        OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            }
            // baseModel.rightArm.x += 1F;
        } else {
            // baseModel.leftArm.xRot = 0.0F;
            // baseModel.leftArm.x +=1F;
            event.getStack().translate(-0.0625f, 0.125f, 0f);
            if (event.isUseOldHandRender()) {
                baseModel.leftArm.xRot = 0f;
                baseModel.leftArm.yRot = 0f;
                baseModel.leftArm.zRot = 0f;
                baseModel.leftArm.x = event.getBone().getPivotX();
                baseModel.leftArm.y = event.getBone().getPivotY();
                baseModel.leftArm.z = event.getBone().getPivotZ();
            } else {
                baseModel.leftArm.xRot = 0f;
                baseModel.leftArm.yRot = (float) Math.PI;
                baseModel.leftArm.zRot = (float) Math.PI;
                baseModel.leftArm.x = event.getBone().getPivotX();
                baseModel.leftArm.y = event.getBone().getPivotY() + 7f;
                baseModel.leftArm.z = event.getBone().getPivotZ();
            }
            baseModel.leftArm.render(event.getStack(), vertexconsumer, event.getPackedLightIn(),
                    OverlayTexture.NO_OVERLAY);
            if(baseModel.isEmissive()) {
                VertexConsumer emissiveConsumer = event.getCurrentBuffer()
                        .getBuffer(RenderType.entityTranslucentEmissive(ClientUtils.getEmissiveTexture(name)));
                baseModel.leftArm.renderEmissive(event.getStack(), emissiveConsumer, event.getPackedLightIn(),
                        OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            }
            // baseModel.leftArm.x -=1F;
        }
    }
}