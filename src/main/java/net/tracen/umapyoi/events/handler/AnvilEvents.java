package net.tracen.umapyoi.events.handler;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.data.builtin.UmaDataRegistry;
import net.tracen.umapyoi.data.tag.UmapyoiItemTags;
import net.tracen.umapyoi.item.FadedUmaSoulItem;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.utils.GachaRanking;

import java.util.stream.StreamSupport;

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
        miyaSoul(event, soul, material);
        tycheSoul(event, soul, material);
        suzunaSoul(event, soul, material);
        stardustSoul(event, soul, material);
    }

    private static void suzunaSoul(AnvilUpdateEvent event, ItemStack soul, ItemStack material) {
        if(!soul.is(ItemRegistry.BLANK_UMA_SOUL.get())) return;
        if(!material.is(Items.DIAMOND_SWORD)) return;
        if(event.getName() ==null || !event.getName().equalsIgnoreCase("priconne")) return;
        var registry = UmapyoiAPI.getUmaDataRegistry(event.getPlayer().level());
        ResourceLocation name = soul.has(DataComponentsTypeRegistry.DATA_LOCATION) ?
                soul.get(DataComponentsTypeRegistry.DATA_LOCATION).name() : UmaDataRegistry.COMMON_UMA.location();
        if(!registry.containsKey(name) || registry.get(name).ranking() != GachaRanking.R) return;

        var id = UmaDataRegistry.SHENONE_SUZUNA.location();
        if(!registry.containsKey(id)) return;
        ItemStack egg = FadedUmaSoulItem.genUmaSoul(id, registry.get(id));

        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
    }

    private static void tycheSoul(AnvilUpdateEvent event, ItemStack soul, ItemStack material) {
        if(!soul.is(ItemRegistry.BLANK_UMA_SOUL.get())) return;
        if(!material.is(Tags.Items.FEATHERS)) return;
        if(event.getName() ==null || !event.getName().equalsIgnoreCase("tyche")) return;
        var registry = UmapyoiAPI.getUmaDataRegistry(event.getPlayer().level());
        ResourceLocation name = soul.has(DataComponentsTypeRegistry.DATA_LOCATION) ?
                soul.get(DataComponentsTypeRegistry.DATA_LOCATION).name() : UmaDataRegistry.COMMON_UMA.location();
        if(!registry.containsKey(name) || !registry.get(name).identifier().equals(UmaDataRegistry.COMMON_UMA.location())) return;

        var id = UmaDataRegistry.TYCHE.location();
        if(!registry.containsKey(id)) return;
        ItemStack egg = FadedUmaSoulItem.genUmaSoul(id, registry.get(id));
        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
    }

    private static void miyaSoul(AnvilUpdateEvent event, ItemStack soul, ItemStack material) {
        if(!soul.is(ItemRegistry.BLANK_UMA_SOUL.get())) return;
        if(!material.is(UmapyoiItemTags.BAMBOO)) return;
        var registry = UmapyoiAPI.getUmaDataRegistry(event.getPlayer().level());
        ResourceLocation name = soul.has(DataComponentsTypeRegistry.DATA_LOCATION) ?
                soul.get(DataComponentsTypeRegistry.DATA_LOCATION).name() : UmaDataRegistry.COMMON_UMA.location();
        if(!registry.containsKey(name) || registry.get(name).ranking() != GachaRanking.R) return;

        var id = UmaDataRegistry.MIYA_YOMOGI.location();
        if(!registry.containsKey(id)) return;

        ItemStack egg = FadedUmaSoulItem.genUmaSoul(id, registry.get(id));
        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
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
        ItemStack egg = FadedUmaSoulItem.genUmaSoul(id, registry.get(id));

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
        ItemStack egg = FadedUmaSoulItem.genUmaSoul(id, registry.get(id));

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
        ItemStack egg = FadedUmaSoulItem.genUmaSoul(id, registry.get(id));

        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
    }

    private static void stardustSoul(AnvilUpdateEvent event, ItemStack soul, ItemStack material) {
        Player player = event.getPlayer();
        if (!soul.is(ItemRegistry.BLANK_UMA_SOUL.get())) return;
        PotionContents contents = material.get(DataComponents.POTION_CONTENTS);
        if (contents == null) return;
        if (StreamSupport.stream(contents.getAllEffects().spliterator(), false).noneMatch(eff -> eff.getEffect().equals(MobEffects.MOVEMENT_SPEED))) return;
        if (event.getName() == null || !event.getName().equalsIgnoreCase("synchro")) return;
        var registry = UmapyoiAPI.getUmaDataRegistry(player.level());
        ResourceLocation name = soul.has(DataComponentsTypeRegistry.DATA_LOCATION) ?
                soul.get(DataComponentsTypeRegistry.DATA_LOCATION).name() : UmaDataRegistry.COMMON_UMA.location();
        if(!registry.containsKey(name) ||
                !registry.get(name).identifier().equals(UmaDataRegistry.SILENCE_SUZUKA.location()))
            return;

        var id = UmaDataRegistry.STARDUST.location();
        if (!registry.containsKey(id)) return;

        ItemStack egg = FadedUmaSoulItem.genUmaSoul(id, registry.get(id));
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
        ItemStack egg = FadedUmaSoulItem.genUmaSoul(id, registry.get(id));

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
        ItemStack egg = FadedUmaSoulItem.genUmaSoul(id, registry.get(id));

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
        ItemStack egg = FadedUmaSoulItem.genUmaSoul(id, registry.get(id));

        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
    }
    
}
