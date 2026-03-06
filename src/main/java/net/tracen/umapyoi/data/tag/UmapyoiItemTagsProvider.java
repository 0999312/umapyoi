package net.tracen.umapyoi.data.tag;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.utils.RaceRanking;

public class UmapyoiItemTagsProvider extends ItemTagsProvider {

    public UmapyoiItemTagsProvider(PackOutput pGenerator, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> provider,
            ExistingFileHelper existingFileHelper) {
        super(pGenerator, lookupProvider, provider, Umapyoi.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        tag(UmapyoiItemTags.SHOULD_RENDER).add(Items.ELYTRA)
                .addOptional(ResourceLocation.tryParse("corn_delight:cob_pipe"))
                .addOptional(ResourceLocation.tryParse("create:goggles"));

        tag(UmapyoiItemTags.COMMON_GACHA_ITEM).add(ItemRegistry.JEWEL.get()).add(ItemRegistry.BLANK_TICKET.get());
        tag(UmapyoiItemTags.SR_UMA_TICKET).add(ItemRegistry.SR_UMA_TICKET.get());
        tag(UmapyoiItemTags.SSR_UMA_TICKET).add(ItemRegistry.SSR_UMA_TICKET.get());
        tag(UmapyoiItemTags.SR_CARD_TICKET).add(ItemRegistry.SR_CARD_TICKET.get());
        tag(UmapyoiItemTags.SSR_CARD_TICKET).add(ItemRegistry.SSR_CARD_TICKET.get());
        tag(UmapyoiItemTags.WATER).add(Items.WATER_BUCKET);
        tag(UmapyoiItemTags.SUGAR).add(Items.SUGAR);
        tag(UmapyoiItemTags.MILK).add(Items.MILK_BUCKET);

        tag(UmapyoiItemTags.BREAD).addTag(UmapyoiItemTags.BREAD_WHEAT);
        tag(UmapyoiItemTags.BREAD_WHEAT).add(Items.BREAD);
        tag(UmapyoiItemTags.BAMBOO).add(Items.BAMBOO);

        tag(UmapyoiItemTags.UMA_TICKET).addTag(UmapyoiItemTags.COMMON_GACHA_ITEM).add(ItemRegistry.UMA_TICKET.get())
                .addTag(UmapyoiItemTags.SR_UMA_TICKET).addTag(UmapyoiItemTags.SSR_UMA_TICKET);
        tag(UmapyoiItemTags.CARD_TICKET).addTag(UmapyoiItemTags.COMMON_GACHA_ITEM).add(ItemRegistry.CARD_TICKET.get())
                .addTag(UmapyoiItemTags.SR_CARD_TICKET).addTag(UmapyoiItemTags.SSR_CARD_TICKET);

        tag(UmapyoiItemTags.HORSESHOE).add(ItemRegistry.HORSESHOE_GOLD.get())
                .add(ItemRegistry.HORSESHOE_SILVER.get()).add(ItemRegistry.HORSESHOE_RAINBOW.get());
        tag(Tags.Items.CROPS_CARROT).addOptional(ResourceLocation.tryParse("tfc:food/carrot"));

        tag(UmapyoiItemTags.getMotivationFoodTag(1))
                .add(ItemRegistry.HACHIMI_MID.get())
                .add(ItemRegistry.CUPCAKE.get());

        tag(UmapyoiItemTags.getMotivationFoodTag(2))
                .add(ItemRegistry.HACHIMI_BIG.get())
                .add(ItemRegistry.SWEET_CUPCAKE.get());

        tag(UmapyoiItemTags.getMotivationFoodTag(-1))
                .add(ItemRegistry.ROYAL_BITTER.get());

        tag(UmapyoiItemTags.SLOW_METABOLISM)
                .add(ItemRegistry.HACHIMI_BIG.get()).add(ItemRegistry.SWEET_CUPCAKE.get());

        tag(UmapyoiItemTags.getRaceMaterialTag(RaceRanking.PREOP))
                .addTag(Tags.Items.INGOTS_COPPER);

        tag(UmapyoiItemTags.getRaceMaterialTag(RaceRanking.OP))
                .addTag(Tags.Items.INGOTS_IRON);

        tag(UmapyoiItemTags.getRaceMaterialTag(RaceRanking.GIII))
                .addTag(Tags.Items.INGOTS_GOLD);

        tag(UmapyoiItemTags.getRaceMaterialTag(RaceRanking.GII))
                .addTag(Tags.Items.GEMS_EMERALD);

        tag(UmapyoiItemTags.getRaceMaterialTag(RaceRanking.GI))
                .addTag(Tags.Items.GEMS_DIAMOND);

        tag(UmapyoiItemTags.RACE_CHAMPIONS_MATERIAL).add(Items.ENDER_EYE);
    }

}
