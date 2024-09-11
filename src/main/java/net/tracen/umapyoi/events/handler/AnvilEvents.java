package net.tracen.umapyoi.events.handler;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.data.builtin.UmaDataRegistry;
import net.tracen.umapyoi.data.tag.UmapyoiItemTags;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.item.data.DataLocation;
import net.tracen.umapyoi.utils.GachaRanking;

@EventBusSubscriber
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
        
        var registry = UmapyoiAPI.getUmaDataRegistry(event.getPlayer().level());
        ResourceLocation name = soul.has(DataComponentsTypeRegistry.DATA_LOCATION) ?
        		soul.get(DataComponentsTypeRegistry.DATA_LOCATION).name() : UmaDataRegistry.COMMON_UMA.location();
        if(!registry.containsKey(name) || registry.get(name).ranking() != GachaRanking.R) return;
        
        var id = UmaDataRegistry.VENUS_PARK.location();
        if(!registry.containsKey(id)) return;
        ItemStack egg = ItemRegistry.BLANK_UMA_SOUL.get().getDefaultInstance();
        egg.set(DataComponentsTypeRegistry.DATA_LOCATION, new DataLocation(id));

        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
    }
    
    public static void zhengSoul(AnvilUpdateEvent event, ItemStack soul, ItemStack material) {
        if(!soul.is(ItemRegistry.BLANK_UMA_SOUL.get())) return;
        if(!material.is(Tags.Items.FEATHERS)) return;
        
        var registry = UmapyoiAPI.getUmaDataRegistry(event.getPlayer().level());
        ResourceLocation name = soul.has(DataComponentsTypeRegistry.DATA_LOCATION) ?
        		soul.get(DataComponentsTypeRegistry.DATA_LOCATION).name() : UmaDataRegistry.COMMON_UMA.location();
        if(!registry.containsKey(name) || 
                !registry.get(name).identifier().equals(UmaDataRegistry.AGNUS_TACHYON.location())) 
            return;
        
        var id = UmaDataRegistry.SYAMEIMARU_ZHENG.location();
        if(!registry.containsKey(id)) return;
        ItemStack egg = ItemRegistry.BLANK_UMA_SOUL.get().getDefaultInstance();
        egg.set(DataComponentsTypeRegistry.DATA_LOCATION, new DataLocation(id));

        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
    }
    
    public static void dumnheintSoul(AnvilUpdateEvent event, ItemStack soul, ItemStack material) {
        if(!soul.is(ItemRegistry.BLANK_UMA_SOUL.get())) return;
        if(!material.is(Tags.Items.GUNPOWDERS)) return;
        if(!event.getName().equalsIgnoreCase("kino")) return;
        var registry = UmapyoiAPI.getUmaDataRegistry(event.getPlayer().level());
        ResourceLocation name = soul.has(DataComponentsTypeRegistry.DATA_LOCATION) ?
        		soul.get(DataComponentsTypeRegistry.DATA_LOCATION).name() : UmaDataRegistry.COMMON_UMA.location();
        if(!registry.containsKey(name) || registry.get(name).ranking() != GachaRanking.R) return;
        
        var id = UmaDataRegistry.DUMNHEINT.location();
        if(!registry.containsKey(id)) return;
        ItemStack egg = ItemRegistry.BLANK_UMA_SOUL.get().getDefaultInstance();
        egg.set(DataComponentsTypeRegistry.DATA_LOCATION, new DataLocation(id));

        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
    }
    
    public static void darleySoul(AnvilUpdateEvent event, ItemStack soul, ItemStack material) {
        if(!soul.is(ItemRegistry.BLANK_UMA_SOUL.get())) return;
        if(!material.is(ItemRegistry.THREE_GODDESS.get())) return;
        
        if(!event.getName().equalsIgnoreCase("darley")) return;
        
        var registry = UmapyoiAPI.getUmaDataRegistry(event.getPlayer().level());
        
        var id = UmaDataRegistry.DARLEY_ARABIAN.location();
        if(!registry.containsKey(id)) return;
        ItemStack egg = ItemRegistry.BLANK_UMA_SOUL.get().getDefaultInstance();
        egg.set(DataComponentsTypeRegistry.DATA_LOCATION, new DataLocation(id));

        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
    }
    
    public static void byerleySoul(AnvilUpdateEvent event, ItemStack soul, ItemStack material) {
        if(!soul.is(ItemRegistry.BLANK_UMA_SOUL.get())) return;
        if(!material.is(ItemRegistry.THREE_GODDESS.get())) return;
        
        if(!event.getName().equalsIgnoreCase("byerley")) return;
        
        var registry = UmapyoiAPI.getUmaDataRegistry(event.getPlayer().level());
        
        var id = UmaDataRegistry.BYERLEY_TURK.location();
        if(!registry.containsKey(id)) return;
        ItemStack egg = ItemRegistry.BLANK_UMA_SOUL.get().getDefaultInstance();
        egg.set(DataComponentsTypeRegistry.DATA_LOCATION, new DataLocation(id));

        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
    }
    
    public static void godolphinSoul(AnvilUpdateEvent event, ItemStack soul, ItemStack material) {
        if(!soul.is(ItemRegistry.BLANK_UMA_SOUL.get())) return;
        if(!material.is(ItemRegistry.THREE_GODDESS.get())) return;
        
        if(!event.getName().equalsIgnoreCase("godolphin")) return;
        
        var registry = UmapyoiAPI.getUmaDataRegistry(event.getPlayer().level());
        
        var id = UmaDataRegistry.GODOLPHIN_BARB.location();
        if(!registry.containsKey(id)) return;
        ItemStack egg = ItemRegistry.BLANK_UMA_SOUL.get().getDefaultInstance();
        egg.set(DataComponentsTypeRegistry.DATA_LOCATION, new DataLocation(id));

        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
    }
    
}
