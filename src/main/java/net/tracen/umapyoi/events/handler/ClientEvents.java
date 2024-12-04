package net.tracen.umapyoi.events.handler;

import com.mojang.blaze3d.vertex.VertexConsumer;

import cn.mcmod_mmf.mmlib.client.render.sections.dynamic.DynamicChunkBuffers;
import cn.mcmod_mmf.mmlib.utils.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RenderArmEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.client.model.UmaPlayerModel;
import net.tracen.umapyoi.events.client.RenderingUmaSoulEvent;
import net.tracen.umapyoi.utils.ClientUtils;
import net.tracen.umapyoi.utils.UmaSoulUtils;

@EventBusSubscriber(value = Dist.CLIENT)
public class ClientEvents {

    private static NonNullList<ItemStack> armor;
    
    @SubscribeEvent
    public static void onPlayerLoggedIn(ClientPlayerNetworkEvent.LoggingIn event) {
        if(event.getConnection() != null) {
	        ClientUtils.getClientUmaDataRegistry().keySet().forEach(loc->{
	        	var texture = ClientUtils.getTexture(loc);
	        	DynamicChunkBuffers.markTranslucentChunkBuffer(texture);
	        });
	        Minecraft.getInstance().levelRenderer.allChanged();
        }
	}
    
    @SubscribeEvent
    public static void preUmaSoulRendering(RenderingUmaSoulEvent.Pre event) {
        LivingEntity entity = event.getWearer();
        var model = event.getModel();

        if (UmapyoiAPI.isUmaSuitRendering(entity)) {
            model.setAllVisible(false);
            model.head.visible = true;
            model.tail.visible = true;
            model.hat.visible = true;
        } else {
            model.setAllVisible(true);
        }
    }
    
    @SubscribeEvent
    public static void onPlayerRendering(RenderPlayerEvent.Pre event) {
        Player player = event.getEntity();
        ItemStack umasoul = UmapyoiAPI.getRenderingUmaSoul(event.getEntity());
        if (!umasoul.isEmpty()) {
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
    }

    @SubscribeEvent
    public static void onPlayerRenderingPost(RenderPlayerEvent.Post event) {
        Player player = event.getEntity();
        ItemStack umasoul = UmapyoiAPI.getRenderingUmaSoul(event.getEntity());
        if (!UmapyoiConfig.VANILLA_ARMOR_RENDER.get() && armor != null && !umasoul.isEmpty()) {
            for (int i = 0; i < player.getInventory().armor.size(); ++i) {
                player.getInventory().armor.set(i, armor.get(i));
            }
        }
    }

    private static final UmaPlayerModel<LivingEntity> baseModel = new UmaPlayerModel<>();
    
    @SubscribeEvent
    public static void onPlayerArmRendering(RenderArmEvent event) {
        Player player = event.getPlayer();
        ItemStack umasoul = UmapyoiAPI.getRenderingUmaSoul(player);
        if (!umasoul.isEmpty()) {
            ResourceLocation name = UmaSoulUtils.getName(umasoul);
            VertexConsumer vertexconsumer = event.getMultiBufferSource()
                    .getBuffer(RenderType.entityTranslucent(getTexture(name)));
            var pojo = ClientUtil.getModelPOJO(name);
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
                baseModel.rightArm.x +=1F;
            } else {
                baseModel.leftArm.xRot = 0.0F;
                baseModel.leftArm.x +=1F;
                baseModel.leftArm.render(event.getPoseStack(), vertexconsumer, event.getPackedLight(),
                        OverlayTexture.NO_OVERLAY);
                baseModel.leftArm.x -=1F;
            }
            event.setCanceled(true);
        }
    }

    private static ResourceLocation getTexture(ResourceLocation name) {
        return ResourceLocation.tryBuild(name.getNamespace(), "textures/model/" + name.getPath() + ".png");
    }
}
