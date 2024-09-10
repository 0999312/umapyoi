package net.tracen.umapyoi.compat.jei.recipes;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import com.google.common.collect.Lists;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.utils.ClientUtils;
import net.tracen.umapyoi.utils.GachaRanking;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public final class UmapyoiJEIRecipes {
    public static JEISimpleRecipe gachaUmasoul(Ingredient ingredient, GachaRanking... list) {
        var registry = ClientUtils.getClientUmaDataRegistry();
        var keys = registry.keySet();
        
        List<ItemStack> input = Lists.newArrayList();
        Collections.addAll(input, ingredient.getItems());
        List<ItemStack> output = Lists.newArrayList();
        keys.stream().filter(UmapyoiJEIRecipes.umaSoulRanking(list)).forEach(key->{
            ItemStack result = ItemRegistry.BLANK_UMA_SOUL.get().getDefaultInstance();
            result.getOrCreateTag().putString("name", key.toString());
            output.add(result);
        });
        return new JEISimpleRecipe(input, output);
    }
    
    public static JEISimpleRecipe gachaSupportCard(Ingredient ingredient, GachaRanking... list) {
        var registry = ClientUtils.getClientSupportCardRegistry();
        var keys = registry.keySet();
        
        List<ItemStack> input = Lists.newArrayList();
        Collections.addAll(input, ingredient.getItems());
        List<ItemStack> output = Lists.newArrayList();
        keys.stream().filter(UmapyoiJEIRecipes.supportCardRanking(list)).forEach(key->{
            ItemStack result = ItemRegistry.SUPPORT_CARD.get().getDefaultInstance();
            result.getOrCreateTag().putString("support_card", key.toString());
            result.getOrCreateTag().putString("ranking", registry.get(key).getGachaRanking().name().toLowerCase());
            result.getOrCreateTag().putInt("maxDamage", registry.get(key).getMaxDamage());
            output.add(result);
        });
        return new JEISimpleRecipe(input, output);
    }
    
    public static JEISimpleRecipe disassembleUmasoul(ItemStack result, GachaRanking ranking) {
        var registry = ClientUtils.getClientUmaDataRegistry();
        var keys = registry.keySet();
        
        List<ItemStack> input = Lists.newArrayList();
        List<ItemStack> output = Lists.newArrayList(result);
        keys.stream().filter(key->registry.get(key).ranking() == ranking).forEach(key->{
            var initUmaSoul = UmaSoulUtils.initUmaSoul(ItemRegistry.UMA_SOUL.get().getDefaultInstance(), key, registry.get(key));
            UmaSoulUtils.setPhysique(initUmaSoul, 5);
            input.add(initUmaSoul);
        });
        return new JEISimpleRecipe(input, output);
    }
    
    public static JEISimpleRecipe disassembleSupportCard(ItemStack result, GachaRanking ranking) {
        var registry = ClientUtils.getClientSupportCardRegistry();
        var keys = registry.keySet();
        
        List<ItemStack> input = Lists.newArrayList();
        List<ItemStack> output = Lists.newArrayList(result);
        keys.stream().filter(key->registry.get(key).getGachaRanking() == ranking).forEach(key->{
            ItemStack card = ItemRegistry.SUPPORT_CARD.get().getDefaultInstance();
            card.getOrCreateTag().putString("support_card", key.toString());
            card.getOrCreateTag().putString("ranking", registry.get(key).getGachaRanking().name().toLowerCase());
            card.getOrCreateTag().putInt("maxDamage", registry.get(key).getMaxDamage());
            input.add(card);
        });
        return new JEISimpleRecipe(input, output);
    }
    
    private static Predicate<? super ResourceLocation> umaSoulRanking(GachaRanking... list) {
        return key ->{
            var registry = ClientUtils.getClientUmaDataRegistry();
            for (GachaRanking gachaRanking : list) {
                if(registry.get(key).ranking() == gachaRanking) 
                    return true;
            }
            return false;
        };
    }
    
    private static Predicate<? super ResourceLocation> supportCardRanking(GachaRanking... list) {
        return key ->{
            var registry = ClientUtils.getClientSupportCardRegistry();
            for (GachaRanking gachaRanking : list) {
                if(registry.get(key).getGachaRanking() == gachaRanking) 
                    return true;
            }
            return false;
        };
    }
}
