package net.tracen.umapyoi.villager.itemlisting;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.tracen.umapyoi.item.ItemRegistry;

public class RandomPriceSellItemListing implements ItemListing {

    private final ItemStack itemStack;
    private final int baseEmeraldCost;
    private final int minCount;
    private final int maxCount;
    private final int maxUses;
    private final int villagerXp;
    private final float priceMultiplier;

    public RandomPriceSellItemListing(ItemStack itemStack, int pBaseEmeraldCost, int pMaxUses, int pVillagerXp) {
        this(itemStack, pBaseEmeraldCost, 1, 1, pMaxUses, pVillagerXp, 0.05F);
     }
     
     public RandomPriceSellItemListing(ItemStack itemStack, int pBaseEmeraldCost, int pMaxUses, int pVillagerXp, float pPriceMultiplier) {
        this(itemStack, pBaseEmeraldCost, 1, 1, pMaxUses, pVillagerXp, pPriceMultiplier);
     }

     public RandomPriceSellItemListing(ItemStack itemStack, int pBaseEmeraldCost, int pMinCount, int pMaxCount, int pMaxUses, int pVillagerXp) {
        this(itemStack, pBaseEmeraldCost, pMinCount, pMaxCount, pMaxUses, pVillagerXp, 0.05F);
     }
     
     public RandomPriceSellItemListing(ItemStack itemStack, int pBaseEmeraldCost, int pMinCount, int pMaxCount, int pMaxUses, int pVillagerXp, float pPriceMultiplier) {
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
       int j = Math.min(this.baseEmeraldCost, 64);
       ItemStack itemstack = itemStack.copy();
       int count = Math.min(pRand.nextInt(minCount, maxCount + 1), 64);
       itemstack.setCount(count);
       ItemStack itemstack1 = new ItemStack(ItemRegistry.JEWEL.get(), j);
       return new MerchantOffer(itemstack, itemstack1, this.maxUses, this.villagerXp, this.priceMultiplier);
    }
}
