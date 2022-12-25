package net.tracen.umapyoi.data;

import cn.mcmod_mmf.mmlib.data.AbstractItemModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.item.ItemRegistry;

public class UmapyoiItemModelProvider extends AbstractItemModelProvider {

    public UmapyoiItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Umapyoi.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ItemRegistry.ITEMS.getEntries().forEach((item) -> {
            if (item == ItemRegistry.HACHIMI_MID || item == ItemRegistry.HACHIMI_BIG)
                return;
            if (item.get()instanceof BlockItem block && item != ItemRegistry.THREE_GODDESS)
                itemBlock(block::getBlock);
            else
                normalItem(item);
        });
    }

}
