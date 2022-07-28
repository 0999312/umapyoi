package net.trc.umapyoi.client;

import com.mojang.blaze3d.vertex.VertexConsumer;

import cn.mcmod_mmf.mmlib.client.model.bedrock.BedrockVersion;
import cn.mcmod_mmf.mmlib.utils.ClientUtil;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trc.umapyoi.Umapyoi;
import net.trc.umapyoi.api.UmapyoiAPI;
import net.trc.umapyoi.client.model.UmaModels;
import net.trc.umapyoi.client.model.UmaPlayerModel;
import net.trc.umapyoi.registry.UmaData;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onPlayerRendering(RenderPlayerEvent.Pre event) {
        CuriosApi.getCuriosHelper().getCuriosHandler(event.getPlayer()).ifPresent(handler -> {
            handler.getStacksHandler("uma_soul").ifPresent(stacks -> {
                if (stacks.getSlots() <= 0)
                    return;
                ItemStack umasoul = stacks.getStacks().getStackInSlot(0);
                if (!umasoul.isEmpty()) {
                    event.getRenderer().getModel().setAllVisible(false);
                }
            });
        });
    }

    @SubscribeEvent
    public static void onPlayerArmRendering(RenderArmEvent event) {

        ItemStack umasoul = UmapyoiAPI.getUmaSoul(event.getPlayer());
        if (!umasoul.isEmpty()) {
            final UmaData data = UmapyoiAPI.getUmaData(umasoul);

            VertexConsumer vertexconsumer = event.getMultiBufferSource()
                    .getBuffer(RenderType.entityTranslucent(getTexture(data)));
            UmaPlayerModel<LivingEntity> base_model = new UmaPlayerModel<>(event.getPlayer(),
                    ClientUtil.getModelPOJO(UmaModels.getModel(data.name())), BedrockVersion.LEGACY);
            
            base_model.setModelProperties(event.getPlayer());
            base_model.attackTime = 0.0F;
            base_model.crouching = false;
            base_model.swimAmount = 0.0F;
            base_model.setupAnim(event.getPlayer(), 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            if (event.getArm() == HumanoidArm.RIGHT) {
                base_model.rightArm.xRot = 0.0F;
                base_model.rightArm.render(event.getPoseStack(), vertexconsumer, event.getPackedLight(),
                        OverlayTexture.NO_OVERLAY);
            } else {
                base_model.leftArm.xRot = 0.0F;
                base_model.leftArm.render(event.getPoseStack(), vertexconsumer, event.getPackedLight(),
                        OverlayTexture.NO_OVERLAY);
            }
            event.setCanceled(true);
        }

    }

    @SubscribeEvent
    public static void onPlayerLoggin(ClientPlayerNetworkEvent.LoggedInEvent event) {
        event.getPlayer().connection.registryAccess().registryOrThrow(UmaData.REGISTRY_KEY)
                .forEach(data -> ClientUtil.loadModel(UmaModels.getModel(data.name())));
    }

    private static ResourceLocation getTexture(UmaData data) {
        return new ResourceLocation(Umapyoi.MODID, "textures/model/" + data.name() + ".png");
    }
}
