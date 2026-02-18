package net.tracen.umapyoi.events.handler;

import java.util.Map;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.VertexConsumer;

import cn.mcmod_mmf.mmlib.client.model.pojo.BedrockModelPOJO;
import cn.mcmod_mmf.mmlib.utils.ClientUtil;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlot.Type;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.client.model.UmaCostumeModelUtils;
import net.tracen.umapyoi.client.model.UmaPlayerModel;
import net.tracen.umapyoi.data.tag.UmapyoiCostumeDataTags;
import net.tracen.umapyoi.data.tag.UmapyoiItemTags;
import net.tracen.umapyoi.events.client.RenderingUmaSoulEvent;
import net.tracen.umapyoi.item.UmaCostumeItem;
import net.tracen.umapyoi.registry.cosmetics.CosmeticData;
import net.tracen.umapyoi.utils.ClientUtils;
import net.tracen.umapyoi.utils.UmaSoulUtils;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientEvents {

    private static Map<EquipmentSlot, ItemStack> armor;

    @SubscribeEvent
    public static void preUmaSoulRendering(RenderingUmaSoulEvent.Pre event) {
        LivingEntity entity = event.getWearer();
        var model = event.getModel();
        boolean hideHair = false;
        if (UmapyoiAPI.isUmaSuitRendering(entity)) {
            model.setAllVisible(false);
            model.head.visible = true;
            model.tail.visible = true;
            if(UmapyoiAPI.isUmaSuitHasHat(entity)) {
        		ResourceLocation loc = UmaCostumeItem.getCostumeID(UmapyoiAPI.getUmaSuit(entity));
        		var costumeData = ClientUtils.getClientCosmeticDataRegistry().getHolder(
        				ResourceKey.create(CosmeticData.REGISTRY_KEY, loc)
        		);
        		if(costumeData.get().is(UmapyoiCostumeDataTags.HAT_HIDEHAIR)) {
        			hideHair = true;
        			model.longHairParts.forEach(part -> part.visible = false);
            	}else {
            		model.longHairParts.forEach(part -> part.visible = true);
            	}
            	model.hideHat();
            }
            else {
            	model.showHat();
            	
            }
        } else {
            model.setAllVisible(true);
        }
		if(hideHair) {
			model.longHairParts.forEach(part -> part.visible = false);
    	}else {
    		model.longHairParts.forEach(part -> part.visible = true);
    	}
        model.showEars();
    }
    
    @SubscribeEvent
    public static <T extends LivingEntity, M extends EntityModel<T>> void onPlayerRendering(RenderLivingEvent.Pre<T, M> event) {
        LivingEntity player = event.getEntity();
        ItemStack umasoul = UmapyoiAPI.getRenderingUmaSoul(event.getEntity());
        if (!umasoul.isEmpty()) {
        	if(event.getRenderer().getModel() instanceof HumanoidModel humanoid) {
        		humanoid.setAllVisible(false);
            if (!UmapyoiConfig.VANILLA_ARMOR_RENDER.get() && !umasoul.isEmpty()) {
            	//TODO: 重写这个方法以适配不同实体
            	
                armor = Maps.newHashMap();

            	for(EquipmentSlot slot : EquipmentSlot.values()) {
            		if(slot.getType() == Type.HAND)
            			continue;
            		ItemStack itemBySlot = player.getItemBySlot(slot);
					armor.put(slot, itemBySlot);
					
					boolean renderElytry = UmapyoiConfig.ELYTRA_RENDER.get()
							&& itemBySlot.getItem() instanceof ElytraItem;
					boolean shouldRender = itemBySlot.is(UmapyoiItemTags.SHOULD_RENDER);
					if (renderElytry || shouldRender)
						player.setItemSlot(slot, itemBySlot);
					else
						player.setItemSlot(slot, ItemStack.EMPTY);
            	}
            	
            	}
            }
        }
    }

    @SubscribeEvent
    public static <T extends LivingEntity, M extends EntityModel<T>> void onPlayerRenderingPost(RenderLivingEvent.Post<T, M>  event) {
    	LivingEntity player = event.getEntity();
        ItemStack umasoul = UmapyoiAPI.getRenderingUmaSoul(event.getEntity());
        if (!UmapyoiConfig.VANILLA_ARMOR_RENDER.get() && armor != null && !umasoul.isEmpty()) {
        	for(EquipmentSlot slot : EquipmentSlot.values()) {
        		if(slot.getType() == Type.HAND)
        			continue;
        		
				player.setItemSlot(slot, armor.get(slot));
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
		                OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
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
		                OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
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
