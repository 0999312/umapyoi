package net.tracen.umapyoi.client;

import com.mojang.blaze3d.vertex.VertexConsumer;

import cn.mcmod_mmf.mmlib.client.model.bedrock.BedrockVersion;
import cn.mcmod_mmf.mmlib.utils.ClientUtil;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.client.model.UmaPlayerModel;
import net.tracen.umapyoi.registry.umadata.UmaStatus;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientEvents {

    private static NonNullList<ItemStack> armor;

    @SubscribeEvent
    public static void onPlayerRendering(RenderPlayerEvent.Pre event) {
        Player player = event.getPlayer();
        CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(handler -> {
            handler.getStacksHandler("uma_soul").ifPresent(stacks -> {
                if (stacks.getSlots() <= 0)
                    return;
                ItemStack umasoul = stacks.getStacks().getStackInSlot(0);
                if (!umasoul.isEmpty() && stacks.getRenders().get(0)) {
                    event.getRenderer().getModel().setAllVisible(false);
                    if (!UmapyoiConfig.VANILLA_ARMOR_RENDER.get() && !umasoul.isEmpty()) {
                        armor = NonNullList.create();
                        for (int i = 0; i < player.getInventory().armor.size(); ++i) {
                            armor.add(player.getInventory().armor.get(i).copy());
                            if (UmapyoiConfig.ELYTRA_RENDER.get()
                                    && player.getInventory().armor.get(i).getItem() instanceof ElytraItem)
                                player.getInventory().armor.set(i, player.getInventory().armor.get(i));
                            else
                                player.getInventory().armor.set(i, ItemStack.EMPTY);
                        }
                    }
                }
            });
        });
    }

    @SubscribeEvent
    public static void onPlayerRenderingPost(RenderPlayerEvent.Post event) {
        Player player = event.getPlayer();
        ItemStack umasoul = UmapyoiAPI.getUmaSoul(event.getPlayer());
        if (!UmapyoiConfig.VANILLA_ARMOR_RENDER.get() && armor != null && !umasoul.isEmpty()
                && UmapyoiAPI.isUmaSoulRendering(player)) {
            for (int i = 0; i < player.getInventory().armor.size(); ++i) {
                player.getInventory().armor.set(i, armor.get(i));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerArmRendering(RenderArmEvent event) {
        Player player = event.getPlayer();
        ItemStack umasoul = UmapyoiAPI.getUmaSoul(player);
        if (!umasoul.isEmpty() && UmapyoiAPI.isUmaSoulRendering(player)) {
            final UmaStatus data = UmapyoiAPI.getUmaStatus(umasoul);

            VertexConsumer vertexconsumer = event.getMultiBufferSource()
                    .getBuffer(RenderType.entityTranslucent(getTexture(data)));
            UmaPlayerModel<LivingEntity> base_model = new UmaPlayerModel<>(event.getPlayer(),
                    ClientUtil.getModelPOJO(data.name()), BedrockVersion.LEGACY);

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

    private static ResourceLocation getTexture(UmaStatus data) {
        return new ResourceLocation(data.name().getNamespace(), "textures/model/" + data.name().getPath() + ".png");
    }
}
