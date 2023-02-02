package net.tracen.umapyoi.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.EnchantTableRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.block.SupportAlbumPedestalBlock;
import net.tracen.umapyoi.block.entity.SupportAlbumPedestalBlockEntity;

public class SupportAlbumPedestalBlockRender implements BlockEntityRenderer<SupportAlbumPedestalBlockEntity> {
    private final BookModel bookModel;
    public SupportAlbumPedestalBlockRender(BlockEntityRendererProvider.Context context) {
        this.bookModel = new BookModel(context.bakeLayer(ModelLayers.BOOK));
    }

    @Override
    public void render(SupportAlbumPedestalBlockEntity tileEntity, float partialTicks, PoseStack poseStack,
            MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        Level world = tileEntity.getLevel();
        boolean flag = world != null;
        BlockState blockstate = flag ? tileEntity.getBlockState()
                : BlockRegistry.SUPPORT_ALBUM_PEDESTAL.get().defaultBlockState();
        if (blockstate.getBlock() instanceof SupportAlbumPedestalBlock) {
            renderBook(tileEntity, partialTicks, poseStack, buffer, combinedLight, combinedOverlay);
            
            renderAnimation(tileEntity, partialTicks, poseStack, buffer, combinedLight, combinedOverlay); 
        }
    }


    private void renderAnimation(SupportAlbumPedestalBlockEntity tileEntity, float partialTicks, PoseStack poseStack,
            MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        ItemStack item = tileEntity.getStoredItem();
        if (item.isEmpty())
            return;
        
        BlockPos pPos = tileEntity.getBlockPos();
        this.renderItem(partialTicks, poseStack);
        Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemTransforms.TransformType.FIXED, 
                combinedLight, combinedOverlay, poseStack, buffer,
                (int) pPos.asLong());
    }
    
    private int degree = 0;
    private void renderItem(float partialTicks, PoseStack matrixStackIn) {
        float f = (degree + partialTicks) / 20.0F;
        float f1 = Mth.sin(f) * 0.1F + 0.1F;
        matrixStackIn.translate(0.5D, f1 + 1.5D, 0.5D);
        matrixStackIn.mulPose(Vector3f.YP.rotation(f));
        matrixStackIn.scale(0.6F, 0.6F, 0.6F);
        if (f >= Math.PI * 2) 
            degree = 0;
        degree++;
    }
    
    public void renderBook(SupportAlbumPedestalBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        pPoseStack.translate(0.5D, 1.0D, 0.5D);
        float f = (float)pBlockEntity.time + pPartialTick;
        pPoseStack.translate(0.0D, (double)(0.1F + Mth.sin(f * 0.1F) * 0.01F), 0.0D);

        float f1;
        for(f1 = pBlockEntity.rot - pBlockEntity.oRot; f1 >= (float)Math.PI; f1 -= ((float)Math.PI * 2F)) {
        }

        while(f1 < -(float)Math.PI) {
           f1 += ((float)Math.PI * 2F);
        }

        float f2 = pBlockEntity.oRot + f1 * pPartialTick;
        pPoseStack.mulPose(Vector3f.YP.rotation(-f2));
        pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(80.0F));
        float f3 = Mth.lerp(pPartialTick, pBlockEntity.oFlip, pBlockEntity.flip);
        float f4 = Mth.frac(f3 + 0.25F) * 1.6F - 0.3F;
        float f5 = Mth.frac(f3 + 0.75F) * 1.6F - 0.3F;
        float f6 = Mth.lerp(pPartialTick, pBlockEntity.oOpen, pBlockEntity.open);
        this.bookModel.setupAnim(f, Mth.clamp(f4, 0.0F, 1.0F), Mth.clamp(f5, 0.0F, 1.0F), f6);
        VertexConsumer vertexconsumer = EnchantTableRenderer.BOOK_LOCATION.buffer(pBufferSource, RenderType::entitySolid);
        this.bookModel.render(pPoseStack, vertexconsumer, pPackedLight, pPackedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
     }
}
