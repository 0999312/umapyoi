package net.tracen.umapyoi.data.tag;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.data.builtin.UmaDataRegistry;
import net.tracen.umapyoi.registry.umadata.UmaData;

public class UmaDataTagProvider extends TagsProvider<UmaData> {

    public UmaDataTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        this(output, provider, Umapyoi.MODID, existingFileHelper);
    }

    public UmaDataTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, UmaData.REGISTRY_KEY, provider, modId, existingFileHelper);
    }
    
    @Override
    public String getName() {
        return "Umamusume Data Tag Provider";
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        this.tag(UmapyoiUmaDataTags.FLAT_CHEST)
            .add(UmaDataRegistry.TOKAI_TEIO)
            .add(UmaDataRegistry.TAMAMO_CROSS)
            .add(UmaDataRegistry.MANHATTAN_CAFE)
            .add(UmaDataRegistry.SILENCE_SUZUKA)
            .add(UmaDataRegistry.TAMAMO_CROSS_FESTIVAL)
            .add(UmaDataRegistry.GRASS_WONDER)
            .add(UmaDataRegistry.HARU_URARA);
        this.tag(UmapyoiUmaDataTags.TANNED_SKIN).add(UmaDataRegistry.DARLEY_ARABIAN);
    }

}
