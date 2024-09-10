package net.tracen.umapyoi.villager.itemlisting;

import java.util.List;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.tracen.umapyoi.item.ItemRegistry;

public class RandomItemOrderItemListing implements ItemListing {

    private final List<ItemStack> itemStacks;
    private final int baseEmeraldCost;
    private final int minCount;
    private final int maxCount;
    private final int maxUses;
    private final int villagerXp;
    private final float priceMultiplier;

    public RandomItemOrderItemListing(List<ItemStack> itemStacks, int pBaseEmeraldCost, int pMaxUses, int pVillagerXp) {
        this(itemStacks, pBaseEmeraldCost, 1, 1, pMaxUses, pVillagerXp, 0.05F);
    }

    public RandomItemOrderItemListing(List<ItemStack> itemStacks, int pBaseEmeraldCost, int pMaxUses, int pVillagerXp,
            float pPriceMultiplier) {
        this(itemStacks, pBaseEmeraldCost, 1, 1, pMaxUses, pVillagerXp, pPriceMultiplier);
    }

    public RandomItemOrderItemListing(List<ItemStack> itemStacks, int pBaseEmeraldCost, int pMinCount, int pMaxCount,
            int pMaxUses, int pVillagerXp) {
        this(itemStacks, pBaseEmeraldCost, pMinCount, pMaxCount, pMaxUses, pVillagerXp, 0.05F);
    }

    public RandomItemOrderItemListing(List<ItemStack> itemStacks, int pBaseEmeraldCost, int pMinCount, int pMaxCount,
            int pMaxUses, int pVillagerXp, float pPriceMultiplier) {
        this.itemStacks = itemStacks;
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
        ItemStack itemstack = itemStacks.get(pRand.nextInt(itemStacks.size())).copy();
        int count = Math.min(pRand.nextInt(minCount, maxCount + 1), 64);
        itemstack.setCount(count);
        ItemCost itemstack1 = new ItemCost(ItemRegistry.JEWEL.get(), j);
        return new MerchantOffer(itemstack1, itemstack, this.maxUses, this.villagerXp, this.priceMultiplier);
    }
    
    
}
