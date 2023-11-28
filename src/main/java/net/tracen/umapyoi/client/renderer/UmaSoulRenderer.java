package net.tracen.umapyoi.client.renderer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import cn.mcmod_mmf.mmlib.client.model.pojo.BedrockModelPOJO;
import cn.mcmod_mmf.mmlib.utils.ClientUtil;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.client.model.UmaPlayerModel;
import net.tracen.umapyoi.item.UmaSuitItem;
import net.tracen.umapyoi.utils.ClientUtils;
import net.tracen.umapyoi.utils.UmaSoulUtils;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class UmaSoulRenderer implements ICurioRenderer {
    private final Cache<BedrockModelPOJO, UmaPlayerModel<LivingEntity>> models;
    public UmaSoulRenderer() {
        models = CacheBuilder.newBuilder()
                .expireAfterWrite(10L, TimeUnit.MINUTES)
                .build();
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
        ResourceLocation name = UmaSoulUtils.getName(stack);
        VertexConsumer vertexconsumer = renderTypeBuffer
                .getBuffer(RenderType.entityTranslucent(ClientUtils.getTexture(name)));
        boolean suit_flag = false;

        if (CuriosApi.getCuriosInventory(entity).isPresent()) {
            var itemHandler = CuriosApi.getCuriosInventory(entity).orElse(null);
            if (itemHandler.getStacksHandler("uma_suit").isPresent()) {
                var stacksHandler = itemHandler.getStacksHandler("uma_suit").orElse(null);
                IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                
                if (stackHandler.getSlots() > 0 && stackHandler.getStackInSlot(0).getItem() instanceof UmaSuitItem) {
                    suit_flag = stacksHandler.getRenders().get(0);
                }
            }
        }
        var pojo = ClientUtil.getModelPOJO(name);
        UmaPlayerModel<LivingEntity> baseModel;
        try {
            baseModel = models.get(pojo, ()->{
                Umapyoi.getLogger().info("Need Loading Model to cache, {}", pojo);
                return new UmaPlayerModel<>(pojo);
                });        
            baseModel.setModelProperties(entity, suit_flag, false);
            baseModel.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
            if (renderLayerParent.getModel() instanceof HumanoidModel) {
                HumanoidModel<?> model = (HumanoidModel<?>) renderLayerParent.getModel();
                baseModel.copyAnim(baseModel.head, model.head);
                baseModel.copyAnim(baseModel.body, model.body);
                baseModel.copyAnim(baseModel.leftArm, model.leftArm);
                baseModel.copyAnim(baseModel.leftLeg, model.leftLeg);
                baseModel.copyAnim(baseModel.rightArm, model.rightArm);
                baseModel.copyAnim(baseModel.rightLeg, model.rightLeg);
            }
            baseModel.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

            baseModel.renderToBuffer(matrixStack, vertexconsumer, light,
                    LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1, 1, 1, 1);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
