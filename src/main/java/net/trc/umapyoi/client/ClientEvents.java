package net.trc.umapyoi.client;

import cn.mcmod_mmf.mmlib.utils.ClientUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trc.umapyoi.client.model.UmaModels;
import net.trc.umapyoi.registry.UmaData;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onPlayerRendering(RenderPlayerEvent.Pre event) {
        CuriosApi.getCuriosHelper().getCuriosHandler(event.getPlayer()).ifPresent(handler -> {
            handler.getStacksHandler("uma_soul").ifPresent(stacks -> {
                ItemStack umasoul = stacks.getStacks().getStackInSlot(0);
                if (!umasoul.isEmpty()) {
                    event.getRenderer().getModel().setAllVisible(false);
                }
            });
        });
    }

    @SubscribeEvent
    public static void onPlayerLoggin(ClientPlayerNetworkEvent.LoggedInEvent event) {
        event.getPlayer().connection.registryAccess().registryOrThrow(UmaData.REGISTRY_KEY)
                .forEach(data -> ClientUtil.loadModel(UmaModels.getModel(data.name())));
    }

}
