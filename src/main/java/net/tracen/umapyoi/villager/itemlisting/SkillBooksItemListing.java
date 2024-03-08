package net.tracen.umapyoi.villager.itemlisting;

import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.skills.UmaSkill;
import net.tracen.umapyoi.utils.UmaSkillUtils;

public class SkillBooksItemListing implements ItemListing {

    private final int villagerXp;

    public SkillBooksItemListing(int pVillagerXp) {
        this.villagerXp = pVillagerXp;
    }

    @Override
    public MerchantOffer getOffer(Entity pTrader, RandomSource pRand) {
        List<UmaSkill> list = UmaSkillRegistry.REGISTRY.get().getValues().stream().collect(Collectors.toList());
        UmaSkill skill = list.get(pRand.nextInt(list.size()));
        int i = skill.getSkillLevel() * 2;
        ItemStack itemstack = UmaSkillUtils.getSkillBook(skill);
        int j = 2 + pRand.nextInt(5 + i * 10) + 3 * i;
        if (j > 64) {
            j = 64;
        }

        return new MerchantOffer(new ItemStack(ItemRegistry.JEWEL.get(), j), ItemStack.EMPTY, itemstack, 12,
                this.villagerXp, 0.2F);
    }

}
