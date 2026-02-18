package net.tracen.umapyoi.events.handler;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.data.builtin.UmaDataRegistry;
import net.tracen.umapyoi.data.tag.UmapyoiItemTags;
import net.tracen.umapyoi.item.FadedUmaSoulItem;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.utils.GachaRanking;

import java.util.Optional;

@Mod.EventBusSubscriber
public class AnvilEvents {
    @SubscribeEvent
    public static void onAnvilEgg(AnvilUpdateEvent event) {
        ItemStack soul = event.getLeft();
        ItemStack material = event.getRight();
        
        venusParkSoul(event, soul, material);
        zhengSoul(event, soul, material);
        dumnheintSoul(event, soul, material);
        stardustSoul(event, soul, material);
        darleySoul(event, soul, material);
        byerleySoul(event, soul, material);
        godolphinSoul(event, soul, material);
        miyaSoul(event, soul, material);
        tycheSoul(event, soul, material);
        suzunaSoul(event, soul, material);
    }
    
    private static void suzunaSoul(AnvilUpdateEvent event, ItemStack soul, ItemStack material) {
        if(!soul.is(ItemRegistry.BLANK_UMA_SOUL.get())) return;
        if(!material.is(Items.DIAMOND_SWORD)) return;
        if(event.getName() ==null || !event.getName().equalsIgnoreCase("priconne")) return;
        var registry = UmapyoiAPI.getUmaDataRegistry(event.getPlayer().level());
        ResourceLocation name = soul.getOrCreateTag().contains("name") ?
                ResourceLocation.tryParse(soul.getOrCreateTag().getString("name")) : UmaDataRegistry.COMMON_UMA.location();
        if(!registry.containsKey(name) || registry.get(name).getGachaRanking() != GachaRanking.R) return;
        
        var id = UmaDataRegistry.SHENONE_SUZUNA.location();
        if(!registry.containsKey(id)) return;
        ItemStack egg = FadedUmaSoulItem.genUmaSoul(id.toString(), registry.get(id));

        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
    }
    
    private static void tycheSoul(AnvilUpdateEvent event, ItemStack soul, ItemStack material) {
        if(!soul.is(ItemRegistry.BLANK_UMA_SOUL.get())) return;
        if(!material.is(Tags.Items.FEATHERS)) return;
        if(event.getName() ==null || !event.getName().equalsIgnoreCase("tyche")) return;
        var registry = UmapyoiAPI.getUmaDataRegistry(event.getPlayer().level());
        ResourceLocation name = soul.getOrCreateTag().contains("name") ?
                ResourceLocation.tryParse(soul.getOrCreateTag().getString("name")) : UmaDataRegistry.COMMON_UMA.location();
        if(!registry.containsKey(name) || !registry.get(name).getIdentifier().equals(UmaDataRegistry.COMMON_UMA.location())) return;
        
        var id = UmaDataRegistry.TYCHE.location();
        if(!registry.containsKey(id)) return;
        ItemStack egg = FadedUmaSoulItem.genUmaSoul(id.toString(), registry.get(id));
        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
	}

    private static void miyaSoul(AnvilUpdateEvent event, ItemStack soul, ItemStack material) {
        if(!soul.is(ItemRegistry.BLANK_UMA_SOUL.get())) return;
        if(!material.is(UmapyoiItemTags.BAMBOO)) return;
        var registry = UmapyoiAPI.getUmaDataRegistry(event.getPlayer().level());
        ResourceLocation name = soul.getOrCreateTag().contains("name") ?
                ResourceLocation.tryParse(soul.getOrCreateTag().getString("name")) : UmaDataRegistry.COMMON_UMA.location();
        if(!registry.containsKey(name) || registry.get(name).getGachaRanking() != GachaRanking.R) return;
        
        var id = UmaDataRegistry.MIYA_YOMOGI.location();
        if(!registry.containsKey(id)) return;
 
        ItemStack egg = FadedUmaSoulItem.genUmaSoul(id.toString(), registry.get(id));
        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
	}

    private static void venusParkSoul(AnvilUpdateEvent event, ItemStack soul, ItemStack material) {
        if(!soul.is(ItemRegistry.BLANK_UMA_SOUL.get())) return;
        if(!material.is(UmapyoiItemTags.BREAD)) return;
        if(event.getName() ==null || !event.getName().equalsIgnoreCase("vivelafrance")) return;
        
        var registry = UmapyoiAPI.getUmaDataRegistry(event.getPlayer().level());
        ResourceLocation name = soul.getOrCreateTag().contains("name") ?
                ResourceLocation.tryParse(soul.getOrCreateTag().getString("name")) : UmaDataRegistry.COMMON_UMA.location();
        if(!registry.containsKey(name) || registry.get(name).getGachaRanking() != GachaRanking.R) return;
        
        var id = UmaDataRegistry.VENUS_PARK.location();
        if(!registry.containsKey(id)) return;
        ItemStack egg = FadedUmaSoulItem.genUmaSoul(id.toString(), registry.get(id));

        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
    }
    
    private static void zhengSoul(AnvilUpdateEvent event, ItemStack soul, ItemStack material) {
        if(!soul.is(ItemRegistry.BLANK_UMA_SOUL.get())) return;
        if(!material.is(Tags.Items.FEATHERS)) return;
        
        var registry = UmapyoiAPI.getUmaDataRegistry(event.getPlayer().level());
        ResourceLocation name = soul.getOrCreateTag().contains("name") ?
                ResourceLocation.tryParse(soul.getOrCreateTag().getString("name")) : UmaDataRegistry.COMMON_UMA.location();
        if(!registry.containsKey(name) ||
                !registry.get(name).getIdentifier().equals(UmaDataRegistry.AGNUS_TACHYON.location()))
            return;
        
        var id = UmaDataRegistry.SYAMEIMARU_ZHENG.location();
        if(!registry.containsKey(id)) return;
        ItemStack egg = FadedUmaSoulItem.genUmaSoul(id.toString(), registry.get(id));

        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
    }
    
    private static void dumnheintSoul(AnvilUpdateEvent event, ItemStack soul, ItemStack material) {
        if(!soul.is(ItemRegistry.BLANK_UMA_SOUL.get())) return;
        if(!material.is(Items.LAVA_BUCKET)) return;
        if(event.getName() ==null || !event.getName().equalsIgnoreCase("cinnabar")) return;
        var registry = UmapyoiAPI.getUmaDataRegistry(event.getPlayer().level());
        ResourceLocation name = soul.getOrCreateTag().contains("name") ?
                ResourceLocation.tryParse(soul.getOrCreateTag().getString("name")) : UmaDataRegistry.COMMON_UMA.location();
        if(!registry.containsKey(name) || registry.get(name).getGachaRanking() != GachaRanking.R) return;
        
        var id = UmaDataRegistry.DUMNHEINT.location();
        if(!registry.containsKey(id)) return;
        ItemStack egg = FadedUmaSoulItem.genUmaSoul(id.toString(), registry.get(id));

        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
    }

    private static void stardustSoul(AnvilUpdateEvent event, ItemStack soul, ItemStack material) {
        Player player = event.getPlayer();
        if (!soul.is(ItemRegistry.BLANK_UMA_SOUL.get())) return;
        Potion potion = PotionUtils.getPotion(material);
        boolean flag1 = potion.getEffects().stream()
                .anyMatch(eff -> eff.getEffect().equals(MobEffects.MOVEMENT_SPEED));
        boolean flag2 = Optional.ofNullable(material.getFoodProperties(player))
                .map(FoodProperties::getEffects)
                .map(p -> p.stream()
                        .map(Pair::getFirst)
                        .anyMatch(eff -> eff.getEffect().equals(MobEffects.MOVEMENT_SPEED))
                )
                .orElse(false);
        if (!(flag1 || flag2)) return;
        if (event.getName() == null || !event.getName().equalsIgnoreCase("synchro")) return;
        var registry = UmapyoiAPI.getUmaDataRegistry(player.level());
        ResourceLocation name = soul.getOrCreateTag().contains("name") ?
                ResourceLocation.tryParse(soul.getOrCreateTag().getString("name")) :
                UmaDataRegistry.COMMON_UMA.location();
        if(!registry.containsKey(name) ||
                !registry.get(name).getIdentifier().equals(UmaDataRegistry.SILENCE_SUZUKA.location()))
            return;

        var id = UmaDataRegistry.STARDUST.location();
        if (!registry.containsKey(id)) return;

        ItemStack egg = FadedUmaSoulItem.genUmaSoul(id.toString(), registry.get(id));
        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
    }
    
    private static void darleySoul(AnvilUpdateEvent event, ItemStack soul, ItemStack material) {
        if(!soul.is(ItemRegistry.BLANK_UMA_SOUL.get())) return;
        if(!material.is(ItemRegistry.THREE_GODDESS.get())) return;
        
        if(event.getName() ==null || !event.getName().equalsIgnoreCase("darley")) return;
        
        var registry = UmapyoiAPI.getUmaDataRegistry(event.getPlayer().level());
        
        var id = UmaDataRegistry.DARLEY_ARABIAN.location();
        if(!registry.containsKey(id)) return;
        ItemStack egg = FadedUmaSoulItem.genUmaSoul(id.toString(), registry.get(id));

        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
    }
    
    private static void byerleySoul(AnvilUpdateEvent event, ItemStack soul, ItemStack material) {
        if(!soul.is(ItemRegistry.BLANK_UMA_SOUL.get())) return;
        if(!material.is(ItemRegistry.THREE_GODDESS.get())) return;
        
        if(event.getName() ==null || !event.getName().equalsIgnoreCase("byerley")) return;
        
        var registry = UmapyoiAPI.getUmaDataRegistry(event.getPlayer().level());
        
        var id = UmaDataRegistry.BYERLEY_TURK.location();
        if(!registry.containsKey(id)) return;
        ItemStack egg = FadedUmaSoulItem.genUmaSoul(id.toString(), registry.get(id));

        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
    }
    
    private static void godolphinSoul(AnvilUpdateEvent event, ItemStack soul, ItemStack material) {
        if(!soul.is(ItemRegistry.BLANK_UMA_SOUL.get())) return;
        if(!material.is(ItemRegistry.THREE_GODDESS.get())) return;
        
        if(event.getName() ==null || !event.getName().equalsIgnoreCase("godolphin")) return;
        
        var registry = UmapyoiAPI.getUmaDataRegistry(event.getPlayer().level());
        
        var id = UmaDataRegistry.GODOLPHIN_BARB.location();
        if(!registry.containsKey(id)) return;
        ItemStack egg = FadedUmaSoulItem.genUmaSoul(id.toString(), registry.get(id));
        
        event.setMaterialCost(1);
        event.setCost(5);
        event.setOutput(egg.copy());
    }
    
}
