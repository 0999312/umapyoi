package net.tracen.umapyoi.data.tag;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
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
        tag(UmapyoiItemTags.SR_UMA_TICKET).add(ItemRegistry.SR_UMA_TICKET.get());
        tag(UmapyoiItemTags.SSR_UMA_TICKET).add(ItemRegistry.SSR_UMA_TICKET.get());
        tag(UmapyoiItemTags.SR_CARD_TICKET).add(ItemRegistry.SR_CARD_TICKET.get());
        tag(UmapyoiItemTags.SSR_CARD_TICKET).add(ItemRegistry.SSR_CARD_TICKET.get());
        tag(UmapyoiItemTags.UMA_TICKET).add(ItemRegistry.BLANK_TICKET.get()).add(ItemRegistry.UMA_TICKET.get())
                .addTag(UmapyoiItemTags.SR_UMA_TICKET).addTag(UmapyoiItemTags.SSR_UMA_TICKET);
        tag(UmapyoiItemTags.CARD_TICKET).add(ItemRegistry.BLANK_TICKET.get()).add(ItemRegistry.CARD_TICKET.get())
                .addTag(UmapyoiItemTags.SR_CARD_TICKET).addTag(UmapyoiItemTags.SSR_CARD_TICKET);
    }

}
