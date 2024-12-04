package net.tracen.umapyoi.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import cn.mcmod_mmf.mmlib.client.model.SimpleBedrockModel;
import cn.mcmod_mmf.mmlib.client.model.bedrock.BedrockPart;
import cn.mcmod_mmf.mmlib.client.render.sections.BlockEntitySectionGeometryRenderer;
import cn.mcmod_mmf.mmlib.client.render.sections.SectionGeometryRenderContext;
import cn.mcmod_mmf.mmlib.utils.ClientUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.event.AddSectionGeometryEvent.SectionRenderingContext;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.block.UmaStatueBlock;
import net.tracen.umapyoi.block.entity.UmaStatueBlockEntity;
import net.tracen.umapyoi.utils.ClientUtils;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class UmaStatuesBlockRender implements BlockEntityRenderer<UmaStatueBlockEntity>, BlockEntitySectionGeometryRenderer<UmaStatueBlockEntity>{
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "textures/model/three_goddesses.png");
    private final SimpleBedrockModel model;

    public UmaStatuesBlockRender(BlockEntityRendererProvider.Context context) {
        model = new SimpleBedrockModel();
    }

    @Override
    public void render(UmaStatueBlockEntity tileEntity, float partialTicks, PoseStack poseStack,
            MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
    }

    private void renderModel(UmaStatueBlockEntity tileEntity, Direction direction, 
    		SectionRenderingContext context, PoseStack poseStack,
    		BlockPos pos, BlockPos regionOrigin, SectionGeometryRenderContext renderContext) {

        poseStack.pushPose();
        poseStack.translate(0.5D, 1.5D, 0.5D);

        poseStack.mulPose(Axis.YN.rotationDegrees(direction.toYRot()));
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        ItemStack item = tileEntity.getStoredItem();
        var pojo = tileEntity.isEmpty() ? ClientUtil.getModelPOJO(ClientUtils.UMA_STATUES) : ClientUtil.getModelPOJO(UmaSoulUtils.getName(item));

        if (model.needRefresh(pojo))
            model.loadModel(pojo);

        var leftArm = model.getChild("left_arm") != null ? model.getChild("left_arm") : new BedrockPart();
        var rightArm = model.getChild("right_arm") != null ? model.getChild("right_arm") : new BedrockPart();
        leftArm.zRot = ClientUtil.convertRotation(-5);
        rightArm.zRot = ClientUtil.convertRotation(5);
        
        ResourceLocation target = tileEntity.isEmpty() ? TEXTURE :
			ClientUtils.getTexture(UmaSoulUtils.getName(item));
        
        MultiBufferSource buffer = renderContext.getUncachedDynamicTranslucentBufferSource(target);
		VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(target));
        model.renderToBuffer(poseStack, vertexConsumer, renderContext.getPackedLight(), OverlayTexture.NO_OVERLAY);
//        
//        if(model.isEmissive()) {
//            VertexConsumer emissiveConsumer = buffer
//                    .getBuffer(RenderType.entityTranslucentEmissive(tileEntity.isEmpty() ? TEXTURE :
//                    	ClientUtils.getEmissiveTexture(UmaSoulUtils.getName(item))));
//            model.renderEmissiveParts(poseStack, emissiveConsumer, renderContext.getPackedLight(), OverlayTexture.NO_OVERLAY, -1);
//        }
//        
        poseStack.popPose();
    }

	@Override
	public void renderSectionGeometry(UmaStatueBlockEntity tileEntity, SectionRenderingContext context, PoseStack poseStack,
			BlockPos arg3, BlockPos arg4, SectionGeometryRenderContext renderContext) {
        Level world = tileEntity.getLevel();
        boolean flag = world != null;
        BlockState blockstate = flag ? tileEntity.getBlockState()
                : BlockRegistry.UMA_STATUES.get().defaultBlockState();
        if (blockstate.getBlock() instanceof UmaStatueBlock) {
            Direction direction = tileEntity.getBlockState().getValue(UmaStatueBlock.FACING);
            renderModel(tileEntity, direction, context, poseStack, arg3, arg4, renderContext);
        }
	}
}
