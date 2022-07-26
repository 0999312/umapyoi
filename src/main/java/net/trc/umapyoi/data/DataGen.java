package net.trc.umapyoi.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.trc.umapyoi.Umapyoi;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {
    @SubscribeEvent
    public static void dataGen(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        dataGenerator.addProvider(new UmaDataProvider(dataGenerator, existingFileHelper, Umapyoi.MODID));
        dataGenerator.addProvider(new UmapyoiItemModelProvider(dataGenerator, existingFileHelper));
        dataGenerator.addProvider(new UmapyoiLangProvider(dataGenerator));
    }

}
