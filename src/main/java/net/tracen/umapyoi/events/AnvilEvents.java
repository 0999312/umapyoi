package net.tracen.umapyoi.events;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.data.builtin.UmaDataRegistry;
import net.tracen.umapyoi.data.tag.UmapyoiItemTags;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.utils.GachaRanking;

@Mod.EventBusSubscriber
public class AnvilEvents {
    @SubscribeEvent
    public static void onAnvilEgg(AnvilUpdateEvent event) {
        ItemStack soul = event.getLeft();
        ItemStack material = event.getRight();
        
        if(!soul.is(ItemRegistry.BLANK_UMA_SOUL.get())) return;
        if(!material.is(UmapyoiItemTags.BREAD)) return;
        if(!event.getName().equalsIgnoreCase("vivelafrance")) return;
        
        var registry = UmapyoiAPI.getUmaDataRegistry(event.getPlayer().level());
        ResourceLocation name = soul.getOrCreateTag().contains("name") ?
                ResourceLocation.tryParse(soul.getOrCreateTag().getString("name")) : UmaData.DEFAULT_UMA_ID;
        if(!registry.containsKey(name) || registry.get(name).getGachaRanking() != GachaRanking.R) return;
        
        var id = UmaDataRegistry.VENUS_PARK.location();
        if(!registry.containsKey(id)) return;
        ItemStack egg = ItemRegistry.BLANK_UMA_SOUL.get().getDefaultInstance();
        egg.getOrCreateTag().putString("name", id.toString());

        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
    }
}
