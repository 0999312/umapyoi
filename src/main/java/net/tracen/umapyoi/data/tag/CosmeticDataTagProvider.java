package net.tracen.umapyoi.data.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.cosmetics.CosmeticData;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class CosmeticDataTagProvider extends TagsProvider<CosmeticData> {

    public CosmeticDataTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        this(output, provider, Umapyoi.MODID, existingFileHelper);
    }

    public CosmeticDataTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, CosmeticData.REGISTRY_KEY, provider, modId, existingFileHelper);
    }
    
    @Override
    public String getName() {
        return "Umamusume Costume Data Tag Provider";
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        this.tag(UmapyoiCostumeDataTags.HAT_HIDEHAIR);
    }

}
