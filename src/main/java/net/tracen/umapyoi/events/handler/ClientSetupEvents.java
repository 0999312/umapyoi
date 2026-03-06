package net.tracen.umapyoi.events.handler;

import cn.mcmod_mmf.mmlib.client.model.BedrockModelResourceLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.block.entity.BlockEntityRegistry;
import net.tracen.umapyoi.client.ActionBarOverlay;
import net.tracen.umapyoi.client.MotivationOverlay;
import net.tracen.umapyoi.client.SkillOverlay;
import net.tracen.umapyoi.client.key.SkillKeyMapping;
import net.tracen.umapyoi.client.model.DynamicItemBakedModel;
import net.tracen.umapyoi.client.model.SupportCardItemModel;
import net.tracen.umapyoi.client.model.UmaCostumeItemModel;
import net.tracen.umapyoi.client.model.UmaRaceTicketItemModel;
import net.tracen.umapyoi.client.renderer.*;
import net.tracen.umapyoi.client.renderer.blockentity.*;
import net.tracen.umapyoi.item.ItemRegistry;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
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
            CuriosRendererRegistry.register(ItemRegistry.SWIMSUIT.get(),
                    SwimsuitRenderer::new);

            CuriosRendererRegistry.register(ItemRegistry.UMA_COSTUME.get(), UmaCostumeRenderer::new);
            
            BlockEntityRenderers.register(BlockEntityRegistry.THREE_GODDESS.get(), ThreeGoddessBlockRender::new);
            BlockEntityRenderers.register(BlockEntityRegistry.UMA_PEDESTAL.get(), UmaPedestalBlockRender::new);
            BlockEntityRenderers.register(BlockEntityRegistry.SUPPORT_ALBUM_PEDESTAL.get(),
            		SupportAlbumPedestalBlockRender::new);
            
            BlockEntityRenderers.register(BlockEntityRegistry.UMA_STATUES.get(), UmaStatuesBlockRender::new);
            BlockEntityRenderers.register(BlockEntityRegistry.SILVER_UMA_PEDESTAL.get(), SilverUmaPedestalBlockRender::new);
            BlockEntityRenderers.register(BlockEntityRegistry.SILVER_SUPPORT_ALBUM_PEDESTAL.get(),
                    SilverSupportAlbumPedestalBlockRender::new);
            BlockEntityRenderers.register(BlockEntityRegistry.GATE.get(), GateRender::new);
        });
    }

    @SubscribeEvent
    public static void registerModels(ModelEvent.RegisterAdditional event) {
        FileToIdConverter.json("models/item/costume")
                .listMatchingResources(Minecraft.getInstance().getResourceManager()).forEach((location, resource) -> {
                    Umapyoi.getLogger().info("Found resource:{}", location.toString());
                    event.register(resolveCostumeLocation(location));
                });

        Stream.of("race_ticket", "support_card")
                .forEachOrdered((domain) -> FileToIdConverter.json("models/item/" + domain)
                        .listMatchingResources(Minecraft.getInstance().getResourceManager())
                        .keySet()
                        .stream()
                        .map(loc -> resolveLocationGeneric(domain, loc))
                        .forEach(event::register));
    }

    public static ModelResourceLocation resolveLocationGeneric(String name, ResourceLocation location) {
        return new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(location.getNamespace(),
                "item/" + name + "/" + location.getPath().substring(13 + name.length() ,location.getPath().length() - 5)), "standalone");
    }

    public static ModelResourceLocation resolveCostumeLocation(ResourceLocation location) {
        return new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(location.getNamespace(),
                "item/costume/" + location.getPath().substring(20,location.getPath().length()-5)), "standalone");
    }

    public static void onBakedModelSub(ModelEvent.BakingCompleted event, ModelResourceLocation origin,
                                       BiFunction<BakedModel, ModelBakery, DynamicItemBakedModel> constructor) {
        Map<ModelResourceLocation, BakedModel> bakedTopLevelModels = event.getModelBakery().getBakedTopLevelModels();
        bakedTopLevelModels.put(origin,
                constructor.apply(bakedTopLevelModels.get(origin), event.getModelBakery()));
    }

    @SubscribeEvent
    public static void onBakedModel(ModelEvent.BakingCompleted event) {
        onBakedModelSub(event, new ModelResourceLocation(ItemRegistry.UMA_COSTUME.getId(), "inventory"), UmaCostumeItemModel::new);
        onBakedModelSub(event, new ModelResourceLocation(ItemRegistry.UMA_RACE_TICKET.getId(), "inventory"), UmaRaceTicketItemModel::new);
        onBakedModelSub(event, new ModelResourceLocation(ItemRegistry.SUPPORT_CARD.getId(), "inventory"), SupportCardItemModel::new);
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
        event.register(SkillKeyMapping.KEY_CONFIGURE_GUI);
    }
    
    @SubscribeEvent
    public static void registerGuiLayer(RegisterGuiLayersEvent event) {
        event.registerBelowAll(ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "skill_overlay"), SkillOverlay.INSTANCE);
        event.registerBelowAll(ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "motivation_overlay"), MotivationOverlay.INSTANCE);
        event.registerBelowAll(ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "action_bar"), ActionBarOverlay.INSTANCE);
    }

}
