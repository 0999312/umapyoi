package net.trc.umapyoi.data;

import cn.mcmod_mmf.mmlib.data.AbstractItemModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.trc.umapyoi.Umapyoi;
import net.trc.umapyoi.item.ItemRegistry;

public class UmapyoiItemModelProvider extends AbstractItemModelProvider {

    public UmapyoiItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Umapyoi.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
      ItemRegistry.ITEMS.getEntries().forEach((item)->{
          if(item == ItemRegistry.HACHIMI_MID || item == ItemRegistry.HACHIMI_BIG)
              return;
          if(item.get() instanceof BlockItem block)
              itemBlock(block::getBlock);
          else normalItem(item);
      });
    }

}
