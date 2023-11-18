package net.tracen.umapyoi.client.renderer;

import java.util.function.Function;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;

import cn.mcmod_mmf.mmlib.utils.ClientUtil;
import net.minecraft.Util;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.tracen.umapyoi.client.model.UmaPlayerModel;
import net.tracen.umapyoi.events.client.RenderingUmaSoulEvent;
import net.tracen.umapyoi.item.UmaSuitItem;
import net.tracen.umapyoi.utils.ClientUtils;
import net.tracen.umapyoi.utils.UmaSoulUtils;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

@OnlyIn(Dist.CLIENT)
public class UmaSoulRenderer implements ICurioRenderer {

    private final UmaPlayerModel<LivingEntity> baseModel;

    public UmaPlayerModel<LivingEntity> getBaseModel() {
        return baseModel;
    }

    public UmaSoulRenderer() {
        baseModel = new UmaPlayerModel<>();
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext,
            PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer,
            int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw,
            float headPitch) {

        if (!slotContext.visible())
            return;

        LivingEntity entity = slotContext.entity();
        if (!(entity instanceof ArmorStand) && !slotContext.identifier().equalsIgnoreCase("uma_soul"))
            return;
        if (entity.isInvisible() && !entity.isSpectator())
            return;

        boolean suit_flag = false;

        if (CuriosApi.getCuriosHelper().getCuriosHandler(entity).isPresent()) {
            var itemHandler = CuriosApi.getCuriosHelper().getCuriosHandler(entity).orElse(null);
            if (itemHandler.getStacksHandler("uma_suit").isPresent()) {
                var stacksHandler = itemHandler.getStacksHandler("uma_suit").orElse(null);
                IDynamicStackHandler stackHandler = stacksHandler.getStacks();

                if (stackHandler.getSlots() > 0 && stackHandler.getStackInSlot(0).getItem() instanceof UmaSuitItem) {
                    suit_flag = stacksHandler.getRenders().get(0);
                }
            }
        }

        ResourceLocation renderTarget = suit_flag
                ? ClientUtils.getClientUmaDataRegistry().get(UmaSoulUtils.getName(stack)).getIdentifier()
                : UmaSoulUtils.getName(stack);
        var pojo = ClientUtil.getModelPOJO(renderTarget);
        if (baseModel.needRefresh(pojo))
            baseModel.loadModel(pojo);

        VertexConsumer vertexConsumer = renderTypeBuffer
                .getBuffer(RenderType.entityTranslucent(ClientUtils.getTexture(renderTarget)));
        baseModel.setModelProperties(entity);
        baseModel.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
        if (MinecraftForge.EVENT_BUS.post(
                new RenderingUmaSoulEvent.Pre(entity, baseModel, partialTicks, matrixStack, renderTypeBuffer, light)))
            return;
        if (renderLayerParent.getModel()instanceof HumanoidModel<?> entityModel) {
            baseModel.copyAnim(baseModel.head, entityModel.head);
            baseModel.copyAnim(baseModel.body, entityModel.body);
            baseModel.copyAnim(baseModel.leftArm, entityModel.leftArm);
            baseModel.copyAnim(baseModel.leftLeg, entityModel.leftLeg);
            baseModel.copyAnim(baseModel.rightArm, entityModel.rightArm);
            baseModel.copyAnim(baseModel.rightLeg, entityModel.rightLeg);
        }
        baseModel.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        baseModel.renderToBuffer(matrixStack, vertexConsumer, light,
                LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1, 1, 1, 1);
        VertexConsumer emissiveConsumer = renderTypeBuffer
                .getBuffer(EmissiveRenderType.emissive(ClientUtils.getEmissiveTexture(renderTarget)));
        baseModel.renderEmissiveParts(matrixStack, emissiveConsumer, light,
                LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1, 1, 1, 1);
        MinecraftForge.EVENT_BUS.post(
                new RenderingUmaSoulEvent.Post(entity, baseModel, partialTicks, matrixStack, renderTypeBuffer, light));
    }

    private static class EmissiveRenderType extends RenderType {

        public EmissiveRenderType(String p_173178_, VertexFormat p_173179_, Mode p_173180_, int p_173181_,
                boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
            super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
        }

        private static final Function<ResourceLocation, RenderType> EMISSIVE = Util.memoize((p_173255_) -> {
            RenderStateShard.TextureStateShard renderstateshard$texturestateshard = new RenderStateShard.TextureStateShard(
                    p_173255_, false, false);
            return create("eyes", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false, true,
                    RenderType.CompositeState.builder()
                            .setShaderState(RENDERTYPE_EYES_SHADER)
                            .setTextureState(renderstateshard$texturestateshard)
                            .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                            .setCullState(NO_CULL)
                            .createCompositeState(false));
        });
        
        public static RenderType emissive(ResourceLocation pLocation) {
            return EMISSIVE.apply(pLocation);
        }
    }

}
