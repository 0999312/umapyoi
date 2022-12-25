package net.tracen.umapyoi.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import cn.mcmod_mmf.mmlib.client.model.bedrock.BedrockVersion;
import cn.mcmod_mmf.mmlib.utils.ClientUtil;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.client.ClientUtils;
import net.tracen.umapyoi.client.model.UmaPlayerModel;
import net.tracen.umapyoi.item.UmaSuitItem;
import net.tracen.umapyoi.registry.umadata.UmaStatus;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class UmaSoulRenderer implements ICurioRenderer {

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext,
            PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer,
            int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw,
            float headPitch) {

        LivingEntity player = slotContext.entity();
        
        if(player.isInvisible()) return;
        
        UmaStatus data = UmapyoiAPI.getUmaStatus(stack);
        VertexConsumer vertexconsumer = renderTypeBuffer.getBuffer(RenderType.entityTranslucent(ClientUtils.getTexture(data)));
        UmaPlayerModel<LivingEntity> base_model = new UmaPlayerModel<>(player, ClientUtil.getModelPOJO(data.name()),
                BedrockVersion.LEGACY);
        boolean suit_flag = false;

        if (CuriosApi.getCuriosHelper().getCuriosHandler(player).isPresent()) {
            var itemHandler = CuriosApi.getCuriosHelper().getCuriosHandler(player).orElse(null);
            if (itemHandler.getStacksHandler("uma_suit").isPresent()) {
                var stacksHandler = itemHandler.getStacksHandler("uma_suit").orElse(null);
                IDynamicStackHandler stackHandler = stacksHandler.getStacks();

                if (stackHandler.getSlots() > 0 && stackHandler.getStackInSlot(0).getItem() instanceof UmaSuitItem) {
                    suit_flag = stacksHandler.getRenders().get(0);
                }
            }
        }

        base_model.setModelProperties(player, suit_flag, false);
        base_model.prepareMobModel(player, limbSwing, limbSwingAmount, partialTicks);

        if (renderLayerParent.getModel() instanceof HumanoidModel) {
            @SuppressWarnings("unchecked")
            HumanoidModel<LivingEntity> model = (HumanoidModel<LivingEntity>) renderLayerParent.getModel();

            base_model.copyAnim(base_model.head, model.head);
            base_model.copyAnim(base_model.body, model.body);
            base_model.copyAnim(base_model.leftArm, model.leftArm);
            base_model.copyAnim(base_model.leftLeg, model.leftLeg);
            base_model.copyAnim(base_model.rightArm, model.rightArm);
            base_model.copyAnim(base_model.rightLeg, model.rightLeg);
        }

        base_model.setupAnim(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        base_model.renderToBuffer(matrixStack, vertexconsumer, light,
                LivingEntityRenderer.getOverlayCoords(player, 0.0F), 1, 1, 1, 1);
    }

}
