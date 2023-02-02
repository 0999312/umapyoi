package net.tracen.umapyoi.data.tag;

import org.jetbrains.annotations.Nullable;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeRegistryTagsProvider;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.UmaDataRegistry;
import net.tracen.umapyoi.registry.umadata.UmaData;

public class UmaDataTagProvider extends ForgeRegistryTagsProvider<UmaData> {

    public UmaDataTagProvider(DataGenerator pGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        this(pGenerator, Umapyoi.MODID, existingFileHelper);
    }
    
    public UmaDataTagProvider(DataGenerator pGenerator, String modId,
            @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, UmaDataRegistry.UMA_DATA_REGISTRY.get(), modId, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Umamusume Data Tag Provider";
    }

    @Override
    protected void addTags() {
        this.tag(UmapyoiUmaDataTags.FLAT_CHEST)
        .add(UmaDataRegistry.TOKAI_TEIO.get())
        .add(UmaDataRegistry.TAMAMO_CROSS.get())
        .add(UmaDataRegistry.MANHATTAN_CAFE.get())
        .add(UmaDataRegistry.HARU_URARA.get());
    }

}
