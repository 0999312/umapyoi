package net.tracen.umapyoi.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import cn.mcmod_mmf.mmlib.utils.ClientUtil;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import net.tracen.umapyoi.client.model.UmaPlayerModel;
import net.tracen.umapyoi.events.client.RenderingUmaSuitEvent;
import net.tracen.umapyoi.item.UmaSoulItem;
import net.tracen.umapyoi.utils.ClientUtils;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public abstract class AbstractSuitRenderer implements ICurioRenderer {

    private final UmaPlayerModel<LivingEntity> baseModel;

    public UmaPlayerModel<LivingEntity> getBaseModel() {
        return baseModel;
    }

    public AbstractSuitRenderer() {
        baseModel = new UmaPlayerModel<>();
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext,
            PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer,
            int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw,
            float headPitch) {

        LivingEntity entity = slotContext.entity();
        if (entity.isInvisible())
            return;
        if (!slotContext.identifier().equalsIgnoreCase("uma_suit"))
            return;

        CuriosApi.getCuriosInventory(entity).ifPresent(itemHandler -> {
            itemHandler.getStacksHandler("uma_soul").ifPresent(stacksHandler -> {
                IDynamicStackHandler stackHandler = stacksHandler.getStacks();

                boolean flat_flag = false;
                boolean tanned = false;
                if (stackHandler.getSlots() > 0) {
                    ItemStack stackInSlot = stackHandler.getStackInSlot(0);
                    if (stackInSlot.isEmpty())
                        return;
                    if (!(stackInSlot.getItem() instanceof UmaSoulItem))
                        return;
                    if (!(stacksHandler).getRenders().get(0))
                        return;
                    
                    flat_flag = ClientUtils.isFlatUmamusume(stackInSlot);
                    
                    tanned = ClientUtils.isTannedSkin(stackInSlot);
                }

                VertexConsumer vertexconsumer = renderTypeBuffer.getBuffer(
                        RenderType.entityTranslucentCull(flat_flag ? getFlatTexture(stack, tanned) : getTexture(stack, tanned)));

                var pojo = ClientUtil.getModelPOJO(flat_flag ? getFlatModel(stack) : getModel(stack));
                if (baseModel.needRefresh(pojo))
                    baseModel.loadModel(pojo);
                baseModel.setModelProperties(entity);
                baseModel.head.visible = false;
                baseModel.tail.visible = false;
                baseModel.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
                if (NeoForge.EVENT_BUS.post(new RenderingUmaSuitEvent.Pre(entity, baseModel, partialTicks,
                        matrixStack, renderTypeBuffer, light)).isCanceled())
                    return;

                if (renderLayerParent.getModel() instanceof HumanoidModel) {
                    @SuppressWarnings("unchecked")
                    HumanoidModel<LivingEntity> model = (HumanoidModel<LivingEntity>) renderLayerParent.getModel();

                    baseModel.copyAnim(baseModel.head, model.head);
                    baseModel.copyAnim(baseModel.body, model.body);
                    baseModel.copyAnim(baseModel.leftArm, model.leftArm);
                    baseModel.copyAnim(baseModel.leftLeg, model.leftLeg);
                    baseModel.copyAnim(baseModel.rightArm, model.rightArm);
                    baseModel.copyAnim(baseModel.rightLeg, model.rightLeg);
                }

                baseModel.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

                baseModel.renderToBuffer(matrixStack, vertexconsumer, light,
                        LivingEntityRenderer.getOverlayCoords(entity, 0.0F));
                NeoForge.EVENT_BUS.post(new RenderingUmaSuitEvent.Post(entity, baseModel, partialTicks,
                        matrixStack, renderTypeBuffer, light));
            });
        });
    }

    public abstract ResourceLocation getModel(ItemStack stack);

    public abstract ResourceLocation getTexture(ItemStack stack, boolean tanned);

    public abstract ResourceLocation getFlatModel(ItemStack stack);

    public abstract ResourceLocation getFlatTexture(ItemStack stack, boolean tanned);

}
