package net.trc.umapyoi.client;

import cn.mcmod_mmf.mmlib.utils.ClientUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.trc.umapyoi.client.model.UmaModels;
import net.trc.umapyoi.client.renderer.TrainningSuitRenderer;
import net.trc.umapyoi.client.renderer.UmaSoulRenderer;
import net.trc.umapyoi.item.ItemRegistry;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetupEvents {

    @SubscribeEvent
    public static void setupClient(final FMLClientSetupEvent event) {
        ClientUtil.loadModel(UmaModels.COMMON_UMA);
        ClientUtil.loadModel(UmaModels.GOLD_SHIP);
        ClientUtil.loadModel(UmaModels.TRAINNING_SUIT);
        CuriosRendererRegistry.register(ItemRegistry.UMA_SOUL.get(), UmaSoulRenderer::new);
        CuriosRendererRegistry.register(ItemRegistry.TRAINNING_SUIT.get(), TrainningSuitRenderer::new);
    }
    

}
