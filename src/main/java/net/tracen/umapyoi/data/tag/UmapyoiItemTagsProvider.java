package net.tracen.umapyoi.data.tag;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.item.ItemRegistry;

public class UmapyoiItemTagsProvider extends ItemTagsProvider {

    public UmapyoiItemTagsProvider(DataGenerator pGenerator, BlockTagsProvider pBlockTagsProvider,
            ExistingFileHelper existingFileHelper) {
        super(pGenerator, pBlockTagsProvider, Umapyoi.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
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
        
        tag(UmapyoiItemTags.UMA_TICKET).addTag(UmapyoiItemTags.COMMON_GACHA_ITEM).add(ItemRegistry.UMA_TICKET.get())
                .addTag(UmapyoiItemTags.SR_UMA_TICKET).addTag(UmapyoiItemTags.SSR_UMA_TICKET);
        tag(UmapyoiItemTags.CARD_TICKET).addTag(UmapyoiItemTags.COMMON_GACHA_ITEM).add(ItemRegistry.CARD_TICKET.get())
                .addTag(UmapyoiItemTags.SR_CARD_TICKET).addTag(UmapyoiItemTags.SSR_CARD_TICKET);
        
        tag(UmapyoiItemTags.HORSESHOE).add(ItemRegistry.HORSESHOE_GOLD.get())
            .add(ItemRegistry.HORSESHOE_SILVER.get()).add(ItemRegistry.HORSESHOE_RAINBOW.get());
    }

}
