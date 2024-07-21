package net.tracen.umapyoi.villager;

import java.util.List;

import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.villager.itemlisting.RandomItemOrderItemListing;
import net.tracen.umapyoi.villager.itemlisting.RandomPriceOrderItemListing;
import net.tracen.umapyoi.villager.itemlisting.RandomPriceSellItemListing;
import net.tracen.umapyoi.villager.itemlisting.SkillBooksItemListing;

@Mod.EventBusSubscriber
public class VillagerTradeHandler {
    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        addVillageTrade(event, "farmer", 2,
                new RandomPriceOrderItemListing(new ItemStack(ItemRegistry.CUPCAKE.get()), 1, 4, 16, 16, 5));

        addVillageTrade(event, "farmer", 4,
                new RandomPriceOrderItemListing(new ItemStack(ItemRegistry.SWEET_CUPCAKE.get()), 2, 2, 16, 16, 30, 0.2F));
        
        addVillageTrade(event, "cleric", 3,
                new RandomPriceSellItemListing(new ItemStack(Items.EMERALD), 2, 1, 2, 8, 20));
        
        addVillageTrade(event, "mason", 3,
                new RandomPriceOrderItemListing(new ItemStack(ItemRegistry.UMA_STATUE.get()), 1, 1, 1, 8, 20));
        
        addVillageTrade(event, "mason", 5,
                new RandomPriceOrderItemListing(new ItemStack(ItemRegistry.THREE_GODDESS.get()), 1, 1, 1, 8, 30, 0.2F));
        
        addVillageTrade(event, "shepherd", 5,
                new RandomPriceOrderItemListing(new ItemStack(ItemRegistry.SUMMER_UNIFORM.get()), 1, 1, 1, 8, 30, 0.2F));
        
        addVillageTrade(event, "shepherd", 5,
                new RandomPriceOrderItemListing(new ItemStack(ItemRegistry.WINTER_UNIFORM.get()), 1, 1, 1, 8, 30, 0.2F));
        
        addVillageTrade(event, "shepherd", 5,
                new RandomPriceOrderItemListing(new ItemStack(ItemRegistry.TRAINNING_SUIT.get()), 1, 1, 1, 8, 30, 0.2F));
        
        addTrainerTrade(event);
    }

    public static void addTrainerTrade(VillagerTradesEvent event) {
        addVillageTrade(event, "trainer", 1,
                new RandomPriceSellItemListing(new ItemStack(ItemRegistry.CRYSTAL_SILVER.get()), 1, 1, 2, 16, 1));

        addVillageTrade(event, "trainer", 1,
                new RandomPriceSellItemListing(new ItemStack(ItemRegistry.HORSESHOE_SILVER.get()), 1, 1, 2, 16, 1));

        addVillageTrade(event, "trainer", 1,
                new RandomPriceOrderItemListing(new ItemStack(ItemRegistry.UMA_TICKET.get()), 1, 1, 4, 16, 2));

        addVillageTrade(event, "trainer", 1,
                new RandomPriceOrderItemListing(new ItemStack(ItemRegistry.CARD_TICKET.get()), 1, 1, 4, 16, 2));

        addVillageTrade(event, "trainer", 2,
                new RandomItemOrderItemListing(Lists.newArrayList(new ItemStack(ItemRegistry.SPEED_LOW_ITEM.get()),
                        new ItemStack(ItemRegistry.STAMINA_LOW_ITEM.get()),
                        new ItemStack(ItemRegistry.STRENGTH_LOW_ITEM.get()),
                        new ItemStack(ItemRegistry.MENTALITY_LOW_ITEM.get()),
                        new ItemStack(ItemRegistry.WISDOM_LOW_ITEM.get())), 1, 2, 5, 16, 10));

        addVillageTrade(event, "trainer", 2, new SkillBooksItemListing(10));

        addVillageTrade(event, "trainer", 3,
                new RandomPriceSellItemListing(new ItemStack(ItemRegistry.CRYSTAL_GOLD.get()), 3, 1, 2, 16, 10));

        addVillageTrade(event, "trainer", 3,
                new RandomPriceSellItemListing(new ItemStack(ItemRegistry.HORSESHOE_GOLD.get()), 3, 1, 2, 16, 10));

        addVillageTrade(event, "trainer", 3,
                new RandomPriceOrderItemListing(new ItemStack(ItemRegistry.SR_UMA_TICKET.get()), 1, 1, 3, 12, 20));

        addVillageTrade(event, "trainer", 3,
                new RandomPriceOrderItemListing(new ItemStack(ItemRegistry.SR_CARD_TICKET.get()), 1, 1, 3, 12, 20));

        addVillageTrade(event, "trainer", 3, new SkillBooksItemListing(20));

        addVillageTrade(event, "trainer", 4,
                new RandomItemOrderItemListing(Lists.newArrayList(new ItemStack(ItemRegistry.SPEED_MID_ITEM.get()),
                        new ItemStack(ItemRegistry.STAMINA_MID_ITEM.get()),
                        new ItemStack(ItemRegistry.STRENGTH_MID_ITEM.get()),
                        new ItemStack(ItemRegistry.MENTALITY_MID_ITEM.get()),
                        new ItemStack(ItemRegistry.WISDOM_MID_ITEM.get())), 2, 2, 5, 6, 30));

        addVillageTrade(event, "trainer", 4, new SkillBooksItemListing(30));

        addVillageTrade(event, "trainer", 5,
                new RandomPriceSellItemListing(new ItemStack(ItemRegistry.CRYSTAL_RAINBOW.get()), 5, 1, 2, 16, 30, 0.2F));

        addVillageTrade(event, "trainer", 5,
                new RandomPriceSellItemListing(new ItemStack(ItemRegistry.HORSESHOE_RAINBOW.get()), 5, 1, 2, 16, 30, 0.2F));

        addVillageTrade(event, "trainer", 5,
                new RandomPriceOrderItemListing(new ItemStack(ItemRegistry.SSR_UMA_TICKET.get()), 2, 1, 2, 12, 30, 0.2F));

        addVillageTrade(event, "trainer", 5,
                new RandomPriceOrderItemListing(new ItemStack(ItemRegistry.SSR_CARD_TICKET.get()), 2, 1, 2, 12, 30, 0.2F));

        addVillageTrade(event, "trainer", 5, new SkillBooksItemListing(30));

        addVillageTrade(event, "trainer", 5,
                new RandomItemOrderItemListing(Lists.newArrayList(new ItemStack(ItemRegistry.SPEED_HIGH_ITEM.get()),
                        new ItemStack(ItemRegistry.STAMINA_HIGH_ITEM.get()),
                        new ItemStack(ItemRegistry.STRENGTH_HIGH_ITEM.get()),
                        new ItemStack(ItemRegistry.MENTALITY_HIGH_ITEM.get()),
                        new ItemStack(ItemRegistry.WISDOM_HIGH_ITEM.get())), 2, 2, 5, 6, 30, 0.2F));
    }

    @SubscribeEvent
    public static void onWandererTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        genericTrades
                .add(new RandomPriceOrderItemListing(new ItemStack(ItemRegistry.HACHIMI_MID.get()), 1, 1, 4, 32, 2));
        genericTrades
                .add(new RandomPriceOrderItemListing(new ItemStack(ItemRegistry.HACHIMI_BIG.get()), 2, 1, 4, 32, 2));
        
        genericTrades
            .add(new RandomPriceOrderItemListing(new ItemStack(ItemRegistry.SMALL_ENERGY_DRINK.get()), 1, 1, 4, 32, 2));
        genericTrades
            .add(new RandomPriceOrderItemListing(new ItemStack(ItemRegistry.MEDIUM_ENERGY_DRINK.get()), 2, 1, 4, 32, 2));

        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();
        
        rareTrades
            .add(new RandomPriceOrderItemListing(new ItemStack(ItemRegistry.LARGE_ENERGY_DRINK.get()), 3, 1, 2, 32, 15));
        rareTrades
                .add(new RandomPriceOrderItemListing(new ItemStack(ItemRegistry.ROYAL_BITTER.get()), 2, 1, 4, 32, 15));
    }

    public static void addVillageTrade(VillagerTradesEvent event, String villager, int level,
            VillagerTrades.ItemListing listing) {
        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
        VillagerProfession profession = event.getType();
        if (profession.name() == null)
            return;
        if (profession.name().equals(villager)) {
            trades.get(level).add(listing);
        }
    }
}
