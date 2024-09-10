package net.tracen.umapyoi.villager.itemlisting;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.tracen.umapyoi.item.ItemRegistry;

public class RandomPriceOrderItemListing implements ItemListing {

    private final ItemStack itemStack;
    private final int baseEmeraldCost;
    private final int minCount;
    private final int maxCount;
    private final int maxUses;
    private final int villagerXp;
    private final float priceMultiplier;

    public RandomPriceOrderItemListing(ItemStack itemStack, int pBaseEmeraldCost, int pMaxUses, int pVillagerXp) {
       this(itemStack, pBaseEmeraldCost, 1, 2, pMaxUses, pVillagerXp, 0.05F);
    }
    
    public RandomPriceOrderItemListing(ItemStack itemStack, int pBaseEmeraldCost, int pMaxUses, int pVillagerXp, float pPriceMultiplier) {
       this(itemStack, pBaseEmeraldCost, 1, 2, pMaxUses, pVillagerXp, pPriceMultiplier);
    }

    public RandomPriceOrderItemListing(ItemStack itemStack, int pBaseEmeraldCost, int pMinCount, int pMaxCount, int pMaxUses, int pVillagerXp) {
       this(itemStack, pBaseEmeraldCost, pMinCount, pMaxCount, pMaxUses, pVillagerXp, 0.05F);
    }
    
    public RandomPriceOrderItemListing(ItemStack itemStack, int pBaseEmeraldCost, int pMinCount, int pMaxCount, int pMaxUses, int pVillagerXp, float pPriceMultiplier) {
       this.itemStack = itemStack;
       this.baseEmeraldCost = pBaseEmeraldCost;
       this.minCount = pMinCount;
       this.maxCount = pMaxCount;
       this.maxUses = pMaxUses;
       this.villagerXp = pVillagerXp;
       this.priceMultiplier = pPriceMultiplier;
    }

    @Override
    public MerchantOffer getOffer(Entity pTrader, RandomSource pRand) {
       int i = 5 + pRand.nextInt(15);
       ItemStack itemstack = itemStack.copy();
       int count = Math.min(pRand.nextInt(minCount, maxCount + 1), 64);
       itemstack.setCount(count);
       int j = Math.min(this.baseEmeraldCost + i, 64);
       ItemCost itemstack1 = new ItemCost(ItemRegistry.JEWEL.get(), j);
       return new MerchantOffer(itemstack1, itemstack, this.maxUses, this.villagerXp, this.priceMultiplier);
    }
}
