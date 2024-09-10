package net.tracen.umapyoi.data.tag;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.tags.PoiTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.villager.VillageRegistry;

public class UmapyoiPOITagsProvider extends PoiTypeTagsProvider{

    public UmapyoiPOITagsProvider(PackOutput p_256012_, CompletableFuture<Provider> p_256617_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_256012_, p_256617_, Umapyoi.MODID, existingFileHelper);
    }
    
    @Override
    protected void addTags(Provider provider) {
        this.tag(PoiTypeTags.ACQUIRABLE_JOB_SITE).add(VillageRegistry.TRAINER_POI.getKey());
    }
}
