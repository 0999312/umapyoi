package net.tracen.umapyoi.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.block.SilverUmaPedestalBlock;
import net.tracen.umapyoi.block.entity.SilverUmaPedestalBlockEntity;

public class SilverUmaPedestalBlockRender implements BlockEntityRenderer<SilverUmaPedestalBlockEntity> {

    public SilverUmaPedestalBlockRender(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(SilverUmaPedestalBlockEntity tileEntity, float partialTicks, PoseStack poseStack,
            MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        Level world = tileEntity.getLevel();
        boolean flag = world != null;
        BlockState blockstate = flag ? tileEntity.getBlockState()
                : BlockRegistry.SILVER_UMA_PEDESTAL.get().defaultBlockState();
        if (blockstate.getBlock() instanceof SilverUmaPedestalBlock) {
            renderAnimation(tileEntity, partialTicks, poseStack, buffer, combinedLight, combinedOverlay);
        }
    }

    private void renderAnimation(SilverUmaPedestalBlockEntity tileEntity, float partialTicks, PoseStack poseStack,
            MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        ItemStack item = tileEntity.getStoredItem();
        if (item.isEmpty())
            return;

        BlockPos pPos = tileEntity.getBlockPos();
        this.renderItem(tileEntity, partialTicks, poseStack);
        Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemDisplayContext.FIXED, combinedLight,
                combinedOverlay, poseStack, buffer, tileEntity.getLevel(), (int) pPos.asLong());
    }

    private void renderItem(SilverUmaPedestalBlockEntity tileEntity, float partialTicks, PoseStack matrixStackIn) {
        float f = (tileEntity.getAnimationTime() + partialTicks) / 20.0F;
        float f1 = Mth.sin(f) * 0.1F + 0.1F;
        matrixStackIn.translate(0.5D, f1 + 1.5D, 0.5D);
        matrixStackIn.mulPose(Axis.YP.rotation(f));
        matrixStackIn.scale(0.6F, 0.6F, 0.6F);
    }
}
