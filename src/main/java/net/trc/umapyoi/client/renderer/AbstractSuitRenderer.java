package net.trc.umapyoi.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import cn.mcmod_mmf.mmlib.client.model.bedrock.BedrockVersion;
import cn.mcmod_mmf.mmlib.utils.ClientUtil;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.trc.umapyoi.api.UmapyoiAPI;
import net.trc.umapyoi.client.model.UmaPlayerModel;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public abstract class AbstractSuitRenderer implements ICurioRenderer {

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext,
            PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer,
            int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw,
            float headPitch) {

        LivingEntity player = slotContext.entity();
        boolean flat_flag = false;

        if (CuriosApi.getCuriosHelper().getCuriosHandler(player).isPresent()) {
            var itemHandler = CuriosApi.getCuriosHelper().getCuriosHandler(player).orElse(null);
            if (itemHandler.getStacksHandler("uma_soul").isPresent()) {
                var stacksHandler = itemHandler.getStacksHandler("uma_soul").orElse(null);
                IDynamicStackHandler stackHandler = stacksHandler.getStacks();

                if (stackHandler.getSlots() > 0) {
                    flat_flag = UmapyoiAPI.getUmaData(stackHandler.getStackInSlot(0)).isFlat();
                }
            }
        }

        VertexConsumer vertexconsumer = renderTypeBuffer
                .getBuffer(RenderType.entityTranslucent(flat_flag ? getFlatTexture() : getTexture()));
        UmaPlayerModel<LivingEntity> base_model = new UmaPlayerModel<>(player,
                ClientUtil.getModelPOJO(flat_flag ? getFlatModel() : getModel()), BedrockVersion.LEGACY);

        base_model.setModelProperties(player, false, true);
        base_model.prepareMobModel(player, limbSwing, limbSwingAmount, partialTicks);
        base_model.setupAnim(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        base_model.renderToBuffer(matrixStack, vertexconsumer, light,
                LivingEntityRenderer.getOverlayCoords(player, 0.0F), 1, 1, 1, 1);
    }

    protected abstract ResourceLocation getModel();

    protected abstract ResourceLocation getTexture();

    protected abstract ResourceLocation getFlatModel();

    protected abstract ResourceLocation getFlatTexture();
}
