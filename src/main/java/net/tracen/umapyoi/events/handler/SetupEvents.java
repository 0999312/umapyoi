package net.tracen.umapyoi.events.handler;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.curios.UmaSoulCuriosWrapper;
import net.tracen.umapyoi.curios.UmaSuitCuriosWrapper;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.registry.training.card.SupportCard;
import net.tracen.umapyoi.registry.umadata.UmaData;
import top.theillusivec4.curios.api.CuriosCapability;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = Umapyoi.MODID)
public class SetupEvents {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
    	event.registerItem(CuriosCapability.ITEM, 
    			(stack, context) -> new UmaSoulCuriosWrapper(stack), ItemRegistry.UMA_SOUL);
    	event.registerItem(CuriosCapability.ITEM, 
    			(stack, context) -> new UmaSuitCuriosWrapper(stack)
    			, ItemRegistry.TRAINNING_SUIT, ItemRegistry.SWIMSUIT
    			, ItemRegistry.SUMMER_UNIFORM, ItemRegistry.WINTER_UNIFORM
    			);
    }
    
    @SubscribeEvent
    public static void onDatapackRegister(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(UmaData.REGISTRY_KEY, UmaData.CODEC, UmaData.CODEC);
        event.dataPackRegistry(SupportCard.REGISTRY_KEY, SupportCard.CODEC, SupportCard.CODEC);
    }

}
