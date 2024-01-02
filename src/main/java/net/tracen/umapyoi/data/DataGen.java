package net.tracen.umapyoi.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.tracen.umapyoi.data.advancements.UmapyoiAdvancementsDataProvider;
import net.tracen.umapyoi.data.loot.UmapyoiLootTableProvider;
import net.tracen.umapyoi.data.tag.UmaDataTagProvider;
import net.tracen.umapyoi.data.tag.UmapyoiBlockTagProvider;
import net.tracen.umapyoi.data.tag.UmapyoiItemTagsProvider;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {
    @SubscribeEvent
    public static void dataGen(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        if(event.includeClient()) {
            dataGenerator.addProvider(new UmapyoiBlockStateProvider(dataGenerator, existingFileHelper));
            dataGenerator.addProvider(new UmapyoiItemModelProvider(dataGenerator, existingFileHelper));
            dataGenerator.addProvider(new UmapyoiLangProvider(dataGenerator));
        }
        if(event.includeServer()) {
            
            dataGenerator.addProvider(new UmaDataProvider(dataGenerator, existingFileHelper));
            dataGenerator.addProvider(new SupportCardDataProvider(dataGenerator, existingFileHelper));
            UmapyoiBlockTagProvider blockTagProvider = new UmapyoiBlockTagProvider(dataGenerator, existingFileHelper);
            dataGenerator.addProvider(blockTagProvider);
            dataGenerator.addProvider(new UmapyoiItemTagsProvider(dataGenerator, blockTagProvider, existingFileHelper));
            dataGenerator.addProvider(new UmapyoiLootTableProvider(dataGenerator));
            dataGenerator.addProvider(new UmaDataTagProvider(dataGenerator, existingFileHelper));
            dataGenerator.addProvider(new UmapyoiRecipeProvider(dataGenerator));
            dataGenerator.addProvider(new UmapyoiAdvancementsDataProvider(dataGenerator, existingFileHelper));
        }
    }

}
