package net.tracen.umapyoi.events.handler;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.data.tag.UmapyoiItemTags;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.registry.UmaDataRegistry;
import net.tracen.umapyoi.utils.GachaRanking;

@Mod.EventBusSubscriber
public class AnvilEvents {
    @SubscribeEvent
    public static void onAnvilEgg(AnvilUpdateEvent event) {
        ItemStack soul = event.getLeft();
        ItemStack material = event.getRight();
        
        venusParkSoul(event, soul, material);
        zhengSoul(event, soul, material);
        dumnheintSoul(event, soul, material);
        darleySoul(event, soul, material);
        byerleySoul(event, soul, material);
        godolphinSoul(event, soul, material);
    }

    public static void venusParkSoul(AnvilUpdateEvent event, ItemStack soul, ItemStack material) {
        if(!soul.is(ItemRegistry.BLANK_UMA_SOUL.get())) return;
        if(!material.is(UmapyoiItemTags.BREAD)) return;
        if(!event.getName().equalsIgnoreCase("vivelafrance")) return;
        
        var registry = UmapyoiAPI.getUmaDataRegistry(event.getPlayer().getLevel());
        ResourceLocation name = soul.getOrCreateTag().contains("name") ?
                ResourceLocation.tryParse(soul.getOrCreateTag().getString("name")) : UmaDataRegistry.COMMON_UMA.getId();
        if(!registry.containsKey(name) || registry.get(name).getGachaRanking() != GachaRanking.R) return;
        
        var id = UmaDataRegistry.VENUS_PARK.getId();
        if(!registry.containsKey(id)) return;
        ItemStack egg = ItemRegistry.BLANK_UMA_SOUL.get().getDefaultInstance();
        egg.getOrCreateTag().putString("name", id.toString());

        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
    }
    
    public static void zhengSoul(AnvilUpdateEvent event, ItemStack soul, ItemStack material) {
        if(!soul.is(ItemRegistry.BLANK_UMA_SOUL.get())) return;
        if(!material.is(Tags.Items.FEATHERS)) return;
        
        var registry = UmapyoiAPI.getUmaDataRegistry(event.getPlayer().getLevel());
        ResourceLocation name = soul.getOrCreateTag().contains("name") ?
                ResourceLocation.tryParse(soul.getOrCreateTag().getString("name")) : UmaDataRegistry.COMMON_UMA.getId();
        if(!registry.containsKey(name) || 
                !registry.get(name).getIdentifier().equals(UmaDataRegistry.AGNUS_TACHYON.get().getIdentifier())) 
            return;
        
        var id = UmaDataRegistry.SYAMEIMARU_ZHENG.getId();
        if(!registry.containsKey(id)) return;
        ItemStack egg = ItemRegistry.BLANK_UMA_SOUL.get().getDefaultInstance();
        egg.getOrCreateTag().putString("name", id.toString());

        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
    }
    
    public static void dumnheintSoul(AnvilUpdateEvent event, ItemStack soul, ItemStack material) {
        if(!soul.is(ItemRegistry.BLANK_UMA_SOUL.get())) return;
        if(!material.is(Tags.Items.GUNPOWDER)) return;
        if(!event.getName().equalsIgnoreCase("kino")) return;
        var registry = UmapyoiAPI.getUmaDataRegistry(event.getPlayer().getLevel());
        ResourceLocation name = soul.getOrCreateTag().contains("name") ?
                ResourceLocation.tryParse(soul.getOrCreateTag().getString("name")) : UmaDataRegistry.COMMON_UMA.getId();
        if(!registry.containsKey(name) || registry.get(name).getGachaRanking() != GachaRanking.R) return;
        
        var id = UmaDataRegistry.DUMNHEINT.getId();
        if(!registry.containsKey(id)) return;
        ItemStack egg = ItemRegistry.BLANK_UMA_SOUL.get().getDefaultInstance();
        egg.getOrCreateTag().putString("name", id.toString());

        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
    }
    
    public static void darleySoul(AnvilUpdateEvent event, ItemStack soul, ItemStack material) {
        if(!soul.is(ItemRegistry.BLANK_UMA_SOUL.get())) return;
        if(!material.is(ItemRegistry.THREE_GODDESS.get())) return;
        
        if(!event.getName().equalsIgnoreCase("darley")) return;
        
        var registry = UmapyoiAPI.getUmaDataRegistry(event.getPlayer().getLevel());
        
        var id = UmaDataRegistry.DARLEY_ARABIAN.getId();
        if(!registry.containsKey(id)) return;
        ItemStack egg = ItemRegistry.BLANK_UMA_SOUL.get().getDefaultInstance();
        egg.getOrCreateTag().putString("name", id.toString());

        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
    }
    
    public static void byerleySoul(AnvilUpdateEvent event, ItemStack soul, ItemStack material) {
        if(!soul.is(ItemRegistry.BLANK_UMA_SOUL.get())) return;
        if(!material.is(ItemRegistry.THREE_GODDESS.get())) return;
        
        if(!event.getName().equalsIgnoreCase("byerley")) return;
        
        var registry = UmapyoiAPI.getUmaDataRegistry(event.getPlayer().getLevel());
        
        var id = UmaDataRegistry.BYERLEY_TURK.getId();
        if(!registry.containsKey(id)) return;
        ItemStack egg = ItemRegistry.BLANK_UMA_SOUL.get().getDefaultInstance();
        egg.getOrCreateTag().putString("name", id.toString());

        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
    }
    
    public static void godolphinSoul(AnvilUpdateEvent event, ItemStack soul, ItemStack material) {
        if(!soul.is(ItemRegistry.BLANK_UMA_SOUL.get())) return;
        if(!material.is(ItemRegistry.THREE_GODDESS.get())) return;
        
        if(!event.getName().equalsIgnoreCase("godolphin")) return;
        
        var registry = UmapyoiAPI.getUmaDataRegistry(event.getPlayer().getLevel());
        
        var id = UmaDataRegistry.GODOLPHIN_BARB.getId();
        if(!registry.containsKey(id)) return;
        ItemStack egg = ItemRegistry.BLANK_UMA_SOUL.get().getDefaultInstance();
        egg.getOrCreateTag().putString("name", id.toString());

        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
    }
}
