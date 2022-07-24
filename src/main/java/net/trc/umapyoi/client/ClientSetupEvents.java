package net.trc.umapyoi.client;

import cn.mcmod_mmf.mmlib.utils.ClientUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.trc.umapyoi.client.model.UmaModels;
import net.trc.umapyoi.client.renderer.TrainningSuitRenderer;
import net.trc.umapyoi.client.renderer.UmaSoulRenderer;
import net.trc.umapyoi.client.renderer.UmaUniformRenderer;
import net.trc.umapyoi.item.ItemRegistry;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetupEvents {

    @SubscribeEvent
    public static void setupClient(final FMLClientSetupEvent event) {
        ClientUtil.loadModel(UmaModels.TRAINNING_SUIT);
        ClientUtil.loadModel(UmaModels.SUMMER_UNIFORM);
        ClientUtil.loadModel(UmaModels.WINTER_UNIFORM);
        
        ClientUtil.loadModel(UmaModels.TRAINNING_SUIT_FLAT);
        ClientUtil.loadModel(UmaModels.SUMMER_UNIFORM_FLAT);
        ClientUtil.loadModel(UmaModels.WINTER_UNIFORM_FLAT);
        
        CuriosRendererRegistry.register(ItemRegistry.UMA_SOUL.get(), UmaSoulRenderer::new);
        CuriosRendererRegistry.register(ItemRegistry.TRAINNING_SUIT.get(), TrainningSuitRenderer::new);
        
        CuriosRendererRegistry.register(ItemRegistry.SUMMER_UNIFORM.get(), UmaUniformRenderer.SummerUniformRenderer::new);
        CuriosRendererRegistry.register(ItemRegistry.WINTER_UNIFORM.get(), UmaUniformRenderer.WinterUniformRenderer::new);
    }
    

}
        