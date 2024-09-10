package net.tracen.umapyoi.events;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.tracen.umapyoi.registry.training.card.SupportCard;
import net.tracen.umapyoi.registry.umadata.UmaData;

@EventBusSubscriber(bus = Bus.MOD)
public class DatapackEvents {
    
    @SubscribeEvent
    public static void onDatapackRegister(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(UmaData.REGISTRY_KEY, UmaData.CODEC, UmaData.CODEC);
        event.dataPackRegistry(SupportCard.REGISTRY_KEY, SupportCard.CODEC, SupportCard.CODEC);
    }

}
