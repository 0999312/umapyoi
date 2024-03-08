package net.tracen.umapyoi.data;

import cn.mcmod_mmf.mmlib.data.AbstractItemModelProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.item.ItemRegistry;

public class UmapyoiItemModelProvider extends AbstractItemModelProvider {

    public UmapyoiItemModelProvider(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, Umapyoi.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ItemRegistry.ITEMS.getEntries().forEach((item) -> {
            if (item == ItemRegistry.HACHIMI_MID || item == ItemRegistry.HACHIMI_BIG)
                return;
            if (item == ItemRegistry.UMA_PEDESTAL) {
                withExistingParent(blockName(BlockRegistry.UMA_PEDESTAL),
                        new ResourceLocation("umapyoi:block/pedestal"));
                return;
            }
            
            if (item == ItemRegistry.SILVER_UMA_PEDESTAL) {
                withExistingParent(blockName(BlockRegistry.SILVER_UMA_PEDESTAL),
                        new ResourceLocation("umapyoi:block/silver_pedestal"));
                return;
            }
            
            if (item.get()instanceof BlockItem block
                    && !(item == ItemRegistry.THREE_GODDESS || item == ItemRegistry.UMA_STATUE))
                itemBlock(block::getBlock);

            else
                normalItem(item);
        });
    }

}
