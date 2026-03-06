package net.tracen.umapyoi.events.handler;

import cn.mcmod_mmf.mmlib.client.model.pojo.BedrockModelPOJO;
import com.mojang.blaze3d.vertex.VertexConsumer;

import cn.mcmod_mmf.mmlib.utils.ClientUtil;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderArmEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.client.model.UmaCostumeModelUtils;
import net.tracen.umapyoi.client.model.UmaPlayerModel;
import net.tracen.umapyoi.data.tag.UmapyoiCostumeDataTags;
import net.tracen.umapyoi.events.client.RenderingUmaSoulEvent;
import net.tracen.umapyoi.item.UmaCostumeItem;
import net.tracen.umapyoi.registry.cosmetics.CosmeticData;
import net.tracen.umapyoi.utils.ClientUtils;
import net.tracen.umapyoi.utils.UmaSoulUtils;

@EventBusSubscriber(value = Dist.CLIENT)
public class ClientEvents {

    private static NonNullList<ItemStack> armor;

    @SubscribeEvent
    public static void preUmaSoulRendering(RenderingUmaSoulEvent.Pre event) {
        LivingEntity entity = event.getWearer();
        var model = event.getModel();

        if (UmapyoiAPI.isUmaSuitRendering(entity)) {
            model.setAllVisible(false);
            model.setHeadVisible(true);
            model.setTailVisible(true);
            if (UmapyoiAPI.isUmaSuitHasHat(entity)) {
                ResourceLocation loc = UmaCostumeItem.getCostumeID(UmapyoiAPI.getUmaSuit(entity));
                var costumeData = ClientUtils.getClientCosmeticDataRegistry().getHolder(
                        ResourceKey.create(CosmeticData.REGISTRY_KEY, loc)
                );
                if (costumeData.get().is(UmapyoiCostumeDataTags.HAT_HIDEHAIR)) {
                    model.setLongHairPartsVisible(false);
                }
                model.setHatAndEarsVisible(false, true);
            }
            else {
                model.setHatAndEarsVisible(true, true);
            }
        }
    }
    
    @SubscribeEvent
    public static void onPlayerRendering(RenderPlayerEvent.Pre event) {
        ItemStack umaSoul = UmapyoiAPI.getRenderingUmaSoul(event.getEntity());
        if (!umaSoul.isEmpty()) {
            if (event.getRenderer().getModel() instanceof HumanoidModel<?> humanoid) {
                humanoid.setAllVisible(false);
            }
        }
    }

    private static final UmaPlayerModel<LivingEntity> baseModel = new UmaPlayerModel<>();

    private static void renderArmModel(RenderArmEvent event, ResourceLocation name, VertexConsumer vertexconsumer,
                                       BedrockModelPOJO pojo) {
        if(baseModel.needRefresh(pojo))
            baseModel.loadModel(pojo);

        baseModel.setModelProperties(event.getPlayer());
        baseModel.attackTime = 0.0F;
        baseModel.crouching = false;
        baseModel.swimAmount = 0.0F;
        baseModel.setupAnim(event.getPlayer(), 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

        if (event.getArm() == HumanoidArm.RIGHT) {
            baseModel.rightArm.xRot = 0.0F;
            baseModel.rightArm.x -=1F;
            baseModel.rightArm.render(event.getPoseStack(), vertexconsumer, event.getPackedLight(),
                    OverlayTexture.NO_OVERLAY);
            if(baseModel.isEmissive()) {
                VertexConsumer emissiveConsumer = event.getMultiBufferSource()
                        .getBuffer(RenderType.entityTranslucentEmissive(ClientUtils.getEmissiveTexture(name)));
                baseModel.rightArm.renderEmissive(event.getPoseStack(), emissiveConsumer, event.getPackedLight(),
                        OverlayTexture.NO_OVERLAY);
            }
            baseModel.rightArm.x +=1F;
        } else {
            baseModel.leftArm.xRot = 0.0F;
            baseModel.leftArm.x +=1F;
            baseModel.leftArm.render(event.getPoseStack(), vertexconsumer, event.getPackedLight(),
                    OverlayTexture.NO_OVERLAY);
            if(baseModel.isEmissive()) {
                VertexConsumer emissiveConsumer = event.getMultiBufferSource()
                        .getBuffer(RenderType.entityTranslucentEmissive(ClientUtils.getEmissiveTexture(name)));
                baseModel.leftArm.renderEmissive(event.getPoseStack(), emissiveConsumer, event.getPackedLight(),
                        OverlayTexture.NO_OVERLAY);
            }
            baseModel.leftArm.x -=1F;
        }
    }

    @SubscribeEvent
    public static void onPlayerArmRendering(RenderArmEvent event) {
        Player player = event.getPlayer();
        ItemStack umasoul = UmapyoiAPI.getRenderingUmaSoul(player);
        ItemStack umasuit = UmapyoiAPI.getUmaSuit(player);
        if (!umasoul.isEmpty()) {
            ResourceLocation name = UmaSoulUtils.getName(umasoul);
            VertexConsumer vertexconsumer = event.getMultiBufferSource()
                    .getBuffer(RenderType.entityTranslucent(ClientUtils.getTexture(name)));
            var pojo = ClientUtil.getModelPOJO(name);
            if(!umasuit.isEmpty()) {
                boolean tanned = ClientUtils.isTannedSkin(umasoul);
                vertexconsumer = event.getMultiBufferSource()
                        .getBuffer(RenderType.entityTranslucent(UmaCostumeModelUtils.getCostumeTexture(umasuit, tanned)));
                pojo = ClientUtil.getModelPOJO(UmaCostumeModelUtils.getCostumeModel(umasuit));
            }
            renderArmModel(event, name, vertexconsumer, pojo);
            event.setCanceled(true);
        }
    }
}
