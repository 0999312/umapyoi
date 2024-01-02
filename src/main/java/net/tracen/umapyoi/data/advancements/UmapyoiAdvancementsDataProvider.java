package net.tracen.umapyoi.data.advancements;

import java.util.function.Consumer;

import org.slf4j.Logger;

import cn.mcmod_mmf.mmlib.data.advancement.AbstractAdvancementProvider;
import cn.mcmod_mmf.mmlib.data.advancement.AbstractAdvancements;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.tracen.umapyoi.Umapyoi;

public class UmapyoiAdvancementsDataProvider extends AbstractAdvancementProvider {

    public UmapyoiAdvancementsDataProvider(DataGenerator gen, ExistingFileHelper fileHelperIn) {
        super(gen, fileHelperIn);
    }

    @Override
    public Logger getLogger() {
        return Umapyoi.getLogger();
    }

    @Override
    public Consumer<Consumer<Advancement>>[] getAdvancementTabs() {
        return new AbstractAdvancements[] {new UmapyoiAdvancements()};
    }

}
