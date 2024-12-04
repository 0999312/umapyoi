package net.tracen.umapyoi.events.handler;

import cn.mcmod_mmf.mmlib.client.model.BedrockModelResourceLoader;
import cn.mcmod_mmf.mmlib.client.render.sections.dynamic.DynamicChunkBuffers;
import cn.mcmod_mmf.mmlib.client.render.sections.events.ReloadDynamicChunkBufferEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.block.entity.BlockEntityRegistry;
import net.tracen.umapyoi.client.ActionBarOverlay;
import net.tracen.umapyoi.client.MotivationOverlay;
import net.tracen.umapyoi.client.SkillOverlay;
import net.tracen.umapyoi.client.key.SkillKeyMapping;
import net.tracen.umapyoi.client.renderer.SwimsuitRenderer;
import net.tracen.umapyoi.client.renderer.TrainningSuitRenderer;
import net.tracen.umapyoi.client.renderer.UmaSoulRenderer;
import net.tracen.umapyoi.client.renderer.UmaUniformRenderer;
import net.tracen.umapyoi.client.renderer.blockentity.SilverSupportAlbumPedestalBlockRender;
import net.tracen.umapyoi.client.renderer.blockentity.SilverUmaPedestalBlockRender;
import net.tracen.umapyoi.client.renderer.blockentity.SupportAlbumPedestalBlockRender;
import net.tracen.umapyoi.client.renderer.blockentity.ThreeGoddessBlockRender;
import net.tracen.umapyoi.client.renderer.blockentity.UmaPedestalBlockRender;
import net.tracen.umapyoi.client.renderer.blockentity.UmaStatuesBlockRender;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.utils.ClientUtils;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

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
    public static void onReloadDynamicChunkBuffers(ReloadDynamicChunkBufferEvent event) {
        DynamicChunkBuffers.markTranslucentChunkBuffer(UmaStatuesBlockRender.TEXTURE);
        DynamicChunkBuffers.markTranslucentChunkBuffer(ThreeGoddessBlockRender.TEXTURE);
        
        if(Minecraft.getInstance().getConnection() != null) {
	        ClientUtils.getClientUmaDataRegistry().keySet().forEach(loc->{
	        	var texture = ClientUtils.getTexture(loc);
	        	var emissiveTexture = ClientUtils.getEmissiveTexture(loc);
	        	DynamicChunkBuffers.markTranslucentChunkBuffer(texture);
	        	DynamicChunkBuffers.markTranslucentChunkBuffer(emissiveTexture);
	        });
        }
        
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
    public static void registerGuiLayer(RegisterGuiLayersEvent event) {
        event.registerBelowAll(ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "skill_overlay"), SkillOverlay.INSTANCE);
        event.registerBelowAll(ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "motivation_overlay"), MotivationOverlay.INSTANCE);
        event.registerBelowAll(ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "action_bar"), ActionBarOverlay.INSTANCE);
    }

}
