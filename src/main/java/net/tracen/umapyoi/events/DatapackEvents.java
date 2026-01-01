package net.tracen.umapyoi.events;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DataPackRegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.recipe.UmasoulIngredient;
import net.tracen.umapyoi.registry.cosmetics.CosmeticData;
import net.tracen.umapyoi.registry.races.Race;
import net.tracen.umapyoi.registry.training.card.SupportCard;
import net.tracen.umapyoi.registry.umadata.UmaData;

@Mod.EventBusSubscriber(bus = Bus.MOD)
public class DatapackEvents {
    
    @SubscribeEvent
    public static void onDatapackRegister(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(UmaData.REGISTRY_KEY, UmaData.CODEC, UmaData.CODEC);
        event.dataPackRegistry(SupportCard.REGISTRY_KEY, SupportCard.CODEC, SupportCard.CODEC);
        event.dataPackRegistry(CosmeticData.REGISTRY_KEY, CosmeticData.CODEC, CosmeticData.CODEC);
    }

    @SubscribeEvent
    public static void registerSerializers(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS, helper -> CraftingHelper
                .register(new ResourceLocation(Umapyoi.MODID, "umasoul"), UmasoulIngredient.Serializer.INSTANCE));
    }
}
