package net.tracen.umapyoi.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import cn.mcmod_mmf.mmlib.client.model.SimpleBedrockModel;
import cn.mcmod_mmf.mmlib.utils.ClientUtil;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.block.ThreeGoddessBlock;
import net.tracen.umapyoi.block.entity.ThreeGoddessBlockEntity;
import net.tracen.umapyoi.utils.ClientUtils;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ThreeGoddessBlockRender implements BlockEntityRenderer<ThreeGoddessBlockEntity> {
	public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID,
			"textures/model/three_goddesses.png");
	private final SimpleBedrockModel model;

	public ThreeGoddessBlockRender(BlockEntityRendererProvider.Context context) {
		model = new SimpleBedrockModel();
	}

	@Override
	public void render(ThreeGoddessBlockEntity tileEntity, float partialTicks, PoseStack poseStack,
			MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
		Level world = tileEntity.getLevel();
		boolean flag = world != null;
		BlockState blockstate = flag ? tileEntity.getBlockState()
				: BlockRegistry.THREE_GODDESS.get().defaultBlockState();
		if (blockstate.getBlock() instanceof ThreeGoddessBlock) {
			Direction direction = tileEntity.getBlockState().getValue(ThreeGoddessBlock.FACING);
			renderModel(tileEntity, direction, poseStack, buffer, combinedLight, combinedOverlay);
			renderAnimation(tileEntity, partialTicks, poseStack, buffer, combinedLight, combinedOverlay);
		}
	}

	private void renderModel(ThreeGoddessBlockEntity tileEntity, Direction direction, PoseStack poseStack,
			MultiBufferSource buffer, int combinedLight, int combinedOverlay) {

		VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutout(TEXTURE));
		poseStack.pushPose();
		poseStack.translate(0.5D, 1.5D, 0.5D);
		poseStack.mulPose(Axis.YN.rotationDegrees(direction.toYRot()));
		poseStack.mulPose(Axis.XP.rotationDegrees(180));
		var pojo = ClientUtil.getModelPOJO(ClientUtils.THREE_GODDESS);
		if (model.needRefresh(pojo))
			model.loadModel(pojo);
		model.renderToBuffer(poseStack, vertexconsumer, combinedLight, combinedOverlay);
		poseStack.popPose();
	}

	private void renderAnimation(ThreeGoddessBlockEntity tileEntity, float partialTicks, PoseStack poseStack,
			MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
		ItemStack jewel = tileEntity.getInventory().getStackInSlot(0);
		ItemStack soul = tileEntity.getInventory().getStackInSlot(3);
		if (jewel.isEmpty() && soul.isEmpty())
			return;

		BlockPos pPos = tileEntity.getBlockPos();
		this.renderItem(tileEntity, partialTicks, poseStack);
		Minecraft.getInstance().getItemRenderer().renderStatic(soul.isEmpty() ? jewel : soul, ItemDisplayContext.FIXED,
				combinedLight, combinedOverlay, poseStack, buffer, tileEntity.getLevel(), (int) pPos.asLong());
	}

	private void renderItem(ThreeGoddessBlockEntity tileEntity, float partialTicks, PoseStack matrixStackIn) {
		float f = (tileEntity.getAnimationTime() + partialTicks) / 20.0F;
		float f1 = Mth.sin(f) * 0.1F + 0.1F;
		matrixStackIn.translate(0.5D, f1 + 3.0D, 0.5D);
		matrixStackIn.mulPose(Axis.YP.rotation(f));
		matrixStackIn.scale(0.6F, 0.6F, 0.6F);
	}

}
