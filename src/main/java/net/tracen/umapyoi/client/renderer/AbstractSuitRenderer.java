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
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.client.model.UmaPlayerModel;
import net.tracen.umapyoi.data.tag.UmapyoiUmaDataTags;
import net.tracen.umapyoi.item.UmaSoulItem;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.utils.ClientUtils;
import net.tracen.umapyoi.utils.UmaSoulUtils;
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
        if (player instanceof Player && !slotContext.identifier().equalsIgnoreCase("uma_suit"))
            return;
        if (player.isInvisible())
            return;

        CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(itemHandler -> {
            itemHandler.getStacksHandler("uma_soul").ifPresent(stacksHandler -> {
                IDynamicStackHandler stackHandler = stacksHandler.getStacks();

                boolean flat_flag = false;
                if (stackHandler.getSlots() > 0) {
                    ItemStack stackInSlot = stackHandler.getStackInSlot(0);
                    if (stackInSlot.isEmpty())
                        return;
                    if (!(stackInSlot.getItem() instanceof UmaSoulItem))
                        return;
                    if (!(stacksHandler).getRenders().get(0))
                        return;

                    flat_flag = ClientUtils.getClientUmaDataRegistry()
                            .getOrCreateHolder(
                                    ResourceKey.create(UmaData.REGISTRY_KEY, UmaSoulUtils.getName(stackInSlot)))
                            .is(UmapyoiUmaDataTags.FLAT_CHEST);
                }

                VertexConsumer vertexconsumer = renderTypeBuffer
                        .getBuffer(RenderType.entityTranslucent(flat_flag ? getFlatTexture() : getTexture()));
                UmaPlayerModel<LivingEntity> base_model = new UmaPlayerModel<>(player,
                        ClientUtil.getModelPOJO(flat_flag ? getFlatModel() : getModel()), BedrockVersion.LEGACY);

                base_model.setModelProperties(player, false, true);
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
            });
        });

    }

    protected abstract ResourceLocation getModel();

    protected abstract ResourceLocation getTexture();

    protected abstract ResourceLocation getFlatModel();

    protected abstract ResourceLocation getFlatTexture();
}
