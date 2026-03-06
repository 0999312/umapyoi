package net.tracen.umapyoi.client.renderer.blockentity;

import cn.mcmod_mmf.mmlib.client.model.SimpleBedrockModel;
import cn.mcmod_mmf.mmlib.client.model.pojo.BedrockModelPOJO;
import cn.mcmod_mmf.mmlib.utils.ClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.block.GateDoor;
import net.tracen.umapyoi.block.ThreeGoddessBlock;
import net.tracen.umapyoi.block.entity.GateEntity;

public class GateRender implements BlockEntityRenderer<GateEntity> {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "textures/model/gate_door.png");
    private final SimpleBedrockModel model;

    public GateRender(BlockEntityRendererProvider.Context ctx) {
        model = new SimpleBedrockModel();
    }

    @Override
    public boolean shouldRender(GateEntity pBlockEntity, Vec3 pCameraPos) {
        Level world = pBlockEntity.getLevel();
        if (world == null) return false;
        return BlockEntityRenderer.super.shouldRender(pBlockEntity, pCameraPos);
    }

    @Override
    public void render(GateEntity tileEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        Level world = tileEntity.getLevel();
        if (world == null) return;
        BlockState state = tileEntity.getBlockState();
        if (state.getBlock() instanceof GateDoor) {
            Direction direction = tileEntity.getBlockState().getValue(ThreeGoddessBlock.FACING);
            renderModel(tileEntity, direction, poseStack, multiBufferSource, i, i1, v);
        }
    }

    private void renderModel(GateEntity tileEntity, Direction direction, PoseStack poseStack,
                             MultiBufferSource buffer, int combinedLight, int combinedOverlay, float partialTick) {
        VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutout(TEXTURE));
        poseStack.pushPose();
        poseStack.translate(0.5d, 1.5d + 0.25d, 0.5d);
        poseStack.mulPose(Axis.YN.rotationDegrees(direction.toYRot() + 180));
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        // poseStack.translate(0d, 0d, 7d/16d);
        BedrockModelPOJO pojo = ClientUtil.getModelPOJO(ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "gate_door"));
        if (model.needRefresh(pojo)) model.loadModel(pojo);
        BlockState state = tileEntity.getBlockState();
        boolean isOpen;
        try {
            isOpen = state.getValue(GateDoor.OPEN);
        } catch (IllegalArgumentException ignore) {
            isOpen = false;
        }
        float tuneTick = isOpen ? partialTick : -partialTick;
        float renderProgress = Mth.clamp(((float) tileEntity.open) + tuneTick, 0f, (float) GateEntity.MAX_OPEN) / (float) GateEntity.MAX_OPEN;
        double angle = Math.toRadians(Mth.rotLerp(renderProgress, 15f, 90f));
        model.getChild("door_left").yRot = (float) -angle;
        model.getChild("door_right").yRot = (float) angle;
        model.renderToBuffer(poseStack, vertexconsumer, combinedLight, combinedOverlay);
        poseStack.popPose();
    }
}
