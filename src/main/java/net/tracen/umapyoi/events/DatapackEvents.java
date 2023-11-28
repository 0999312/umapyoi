package net.tracen.umapyoi.events;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DataPackRegistryEvent;
import net.tracen.umapyoi.registry.training.card.SupportCard;
import net.tracen.umapyoi.registry.umadata.UmaData;

@Mod.EventBusSubscriber(bus = Bus.MOD)
public class DatapackEvents {
    
    @SubscribeEvent
    public static void onDatapackRegister(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(UmaData.REGISTRY_KEY, UmaData.CODEC, UmaData.CODEC);
        event.dataPackRegistry(SupportCard.REGISTRY_KEY, SupportCard.CODEC, SupportCard.CODEC);
    }

}
