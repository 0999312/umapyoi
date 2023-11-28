package net.tracen.umapyoi.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.block.SilverSupportAlbumPedestalBlock;
import net.tracen.umapyoi.block.entity.SilverSupportAlbumPedestalBlockEntity;

public class SilverSupportAlbumPedestalBlockRender implements BlockEntityRenderer<SilverSupportAlbumPedestalBlockEntity> {
    private final BookModel bookModel;

    public SilverSupportAlbumPedestalBlockRender(BlockEntityRendererProvider.Context context) {
        this.bookModel = new BookModel(context.bakeLayer(ModelLayers.BOOK));
    }

    @Override
    public void render(SilverSupportAlbumPedestalBlockEntity tileEntity, float partialTicks, PoseStack poseStack,
            MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        Level world = tileEntity.getLevel();
        boolean flag = world != null;
        BlockState blockstate = flag ? tileEntity.getBlockState()
                : BlockRegistry.SILVER_SUPPORT_ALBUM_PEDESTAL.get().defaultBlockState();
        if (blockstate.getBlock() instanceof SilverSupportAlbumPedestalBlock) {
            renderBook(tileEntity, partialTicks, poseStack, buffer, combinedLight, combinedOverlay);
            renderAnimation(tileEntity, partialTicks, poseStack, buffer, combinedLight, combinedOverlay);
        }
    }

    private void renderAnimation(SilverSupportAlbumPedestalBlockEntity tileEntity, float partialTicks, PoseStack poseStack,
            MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        ItemStack item = tileEntity.getStoredItem();
        if (item.isEmpty())
            return;

        BlockPos pPos = tileEntity.getBlockPos();
        this.renderItem(tileEntity, partialTicks, poseStack);
        Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemDisplayContext.FIXED, combinedLight,
                combinedOverlay, poseStack, buffer, tileEntity.getLevel(), (int) pPos.asLong());
    }

    private void renderItem(SilverSupportAlbumPedestalBlockEntity tileEntity, float partialTicks, PoseStack matrixStackIn) {
        float f = (tileEntity.getAnimationTime() + partialTicks) / 20.0F;
        float f1 = Mth.sin(f) * 0.1F + 0.1F;
        matrixStackIn.translate(0.5D, f1 + 1.5D, 0.5D);
        matrixStackIn.mulPose(Axis.YP.rotation(f));
        matrixStackIn.scale(0.6F, 0.6F, 0.6F);
    }

    public void renderBook(SilverSupportAlbumPedestalBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
            MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        pPoseStack.translate(0.5D, 1.0D, 0.5D);
        float f = (float) pBlockEntity.time + pPartialTick;
        pPoseStack.translate(0.0D, (double) (0.1F + Mth.sin(f * 0.1F) * 0.01F), 0.0D);

        float f1;
        for (f1 = pBlockEntity.rot - pBlockEntity.oRot; f1 >= (float) Math.PI; f1 -= ((float) Math.PI * 2F)) {
        }

        while (f1 < -(float) Math.PI) {
            f1 += ((float) Math.PI * 2F);
        }

        float f2 = pBlockEntity.oRot + f1 * pPartialTick;
        pPoseStack.mulPose(Axis.YP.rotation(-f2));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(80.0F));
        float f3 = Mth.lerp(pPartialTick, pBlockEntity.oFlip, pBlockEntity.flip);
        float f4 = Mth.frac(f3 + 0.25F) * 1.6F - 0.3F;
        float f5 = Mth.frac(f3 + 0.75F) * 1.6F - 0.3F;
        float f6 = Mth.lerp(pPartialTick, pBlockEntity.oOpen, pBlockEntity.open);
        this.bookModel.setupAnim(f, Mth.clamp(f4, 0.0F, 1.0F), Mth.clamp(f5, 0.0F, 1.0F), f6);
        VertexConsumer vertexconsumer = pBufferSource.getBuffer(
                RenderType.entitySolid(new ResourceLocation(Umapyoi.MODID, "textures/model/support_card_album.png")));
        this.bookModel.render(pPoseStack, vertexconsumer, pPackedLight, pPackedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
    }
}
