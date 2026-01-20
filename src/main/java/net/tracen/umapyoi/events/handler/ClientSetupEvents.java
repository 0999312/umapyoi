package net.tracen.umapyoi.events.handler;

import java.util.Map;

import cn.mcmod_mmf.mmlib.client.model.BedrockModelResourceLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.block.entity.BlockEntityRegistry;
import net.tracen.umapyoi.client.ActionBarOverlay;
import net.tracen.umapyoi.client.MotivationOverlay;
import net.tracen.umapyoi.client.SkillOverlay;
import net.tracen.umapyoi.client.key.SkillKeyMapping;
import net.tracen.umapyoi.client.model.UmaCostumeItemModel;
import net.tracen.umapyoi.client.renderer.SwimsuitRenderer;
import net.tracen.umapyoi.client.renderer.TrainningSuitRenderer;
import net.tracen.umapyoi.client.renderer.UmaCostumeRenderer;
import net.tracen.umapyoi.client.renderer.UmaSoulRenderer;
import net.tracen.umapyoi.client.renderer.UmaUniformRenderer;
import net.tracen.umapyoi.client.renderer.blockentity.SilverSupportAlbumPedestalBlockRender;
import net.tracen.umapyoi.client.renderer.blockentity.SilverUmaPedestalBlockRender;
import net.tracen.umapyoi.client.renderer.blockentity.SupportAlbumPedestalBlockRender;
import net.tracen.umapyoi.client.renderer.blockentity.ThreeGoddessBlockRender;
import net.tracen.umapyoi.client.renderer.blockentity.UmaPedestalBlockRender;
import net.tracen.umapyoi.client.renderer.blockentity.UmaStatuesBlockRender;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.utils.GachaRanking;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetupEvents {

	@SubscribeEvent
	public static void setupClient(final FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			CuriosRendererRegistry.register(ItemRegistry.UMA_SOUL.get(), UmaSoulRenderer::new);
			CuriosRendererRegistry.register(ItemRegistry.TRAINNING_SUIT.get(), TrainningSuitRenderer::new);

			CuriosRendererRegistry.register(ItemRegistry.SUMMER_UNIFORM.get(),
					UmaUniformRenderer.SummerUniformRenderer::new);
			CuriosRendererRegistry.register(ItemRegistry.WINTER_UNIFORM.get(),
					UmaUniformRenderer.WinterUniformRenderer::new);
			CuriosRendererRegistry.register(ItemRegistry.SWIMSUIT.get(), SwimsuitRenderer::new);

			CuriosRendererRegistry.register(ItemRegistry.UMA_COSTUME.get(), UmaCostumeRenderer::new);
		});
		event.enqueueWork(() -> {
		BlockEntityRenderers.register(BlockEntityRegistry.THREE_GODDESS.get(), ThreeGoddessBlockRender::new);
		BlockEntityRenderers.register(BlockEntityRegistry.UMA_PEDESTAL.get(), UmaPedestalBlockRender::new);
		BlockEntityRenderers.register(BlockEntityRegistry.SUPPORT_ALBUM_PEDESTAL.get(),
				SupportAlbumPedestalBlockRender::new);

		BlockEntityRenderers.register(BlockEntityRegistry.UMA_STATUES.get(), UmaStatuesBlockRender::new);
		BlockEntityRenderers.register(BlockEntityRegistry.SILVER_UMA_PEDESTAL.get(), SilverUmaPedestalBlockRender::new);
		BlockEntityRenderers.register(BlockEntityRegistry.SILVER_SUPPORT_ALBUM_PEDESTAL.get(),
				SilverSupportAlbumPedestalBlockRender::new);
		});
	}

	@SubscribeEvent
	public static void registerModels(ModelEvent.RegisterAdditional event) {
		FileToIdConverter.json("models/item/costume")
				.listMatchingResources(Minecraft.getInstance().getResourceManager()).forEach((location, resource) -> {
					Umapyoi.getLogger().info("Found resource:{}", location.toString());
					event.register(resolveCostumeLocation(location));
				});
	}

	private static ModelResourceLocation resolveCostumeLocation(ResourceLocation location) {
		return new ModelResourceLocation(location.getNamespace(),
				"costume/" + location.getPath().substring(20,location.getPath().length()-5), "inventory");
	}

	@SubscribeEvent
	public static void onBakedModel(ModelEvent.BakingCompleted event) {
		ModelResourceLocation origin = new ModelResourceLocation(ItemRegistry.UMA_COSTUME.getId(), "inventory");
		Map<ResourceLocation, BakedModel> bakedTopLevelModels = event.getModelBakery().getBakedTopLevelModels();
		bakedTopLevelModels.put(origin,
				new UmaCostumeItemModel(bakedTopLevelModels.get(origin), event.getModelBakery()));
	}

	@SubscribeEvent
	public static void resourceLoadingListener(final RegisterClientReloadListenersEvent event) {
		event.registerReloadListener(new BedrockModelResourceLoader("models/umapyoi"));
	}

	@SubscribeEvent
	public static void registerKeyMapping(RegisterKeyMappingsEvent event) {
		event.register(SkillKeyMapping.KEY_USE_SKILL);
		event.register(SkillKeyMapping.KEY_FORMER_SKILL);
		event.register(SkillKeyMapping.KEY_LATTER_SKILL);
	}

	@SubscribeEvent
	public static void registerGuiOverlay(RegisterGuiOverlaysEvent event) {
		event.registerBelowAll("umapyoi.skill_overlay", SkillOverlay.INSTANCE);
		event.registerBelowAll("umapyoi.motivation_overlay", MotivationOverlay.INSTANCE);
		event.registerBelowAll("umapyoi.action_bar", ActionBarOverlay.INSTANCE);
	}

	@SubscribeEvent
	public static void registerItemPredicates(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			Umapyoi.getLogger().debug("Start predication register");
			ItemProperties.register(
					ItemRegistry.SUPPORT_CARD.get(),
					new ResourceLocation(Umapyoi.MODID, "ranking"),
					(stack, world, entity, seed) -> {
						CompoundTag tag = stack.getOrCreateTag();
						if (!tag.contains("ranking", CompoundTag.TAG_STRING)) return -1;
						try {
							return GachaRanking.valueOf(tag.getString("ranking").toUpperCase()).ordinal();
						} catch (IllegalArgumentException ignore) {
							return -1;
						}
					}
			);
		});
	}
}
