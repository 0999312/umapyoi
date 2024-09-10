package net.tracen.umapyoi.data.tag;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.block.BlockRegistry;

public class UmapyoiBlockTagProvider extends BlockTagsProvider {

    public UmapyoiBlockTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider,  ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, Umapyoi.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockRegistry.THREE_GODDESS.get())
                .add(BlockRegistry.SUPPORT_ALBUM_PEDESTAL.get()).add(BlockRegistry.UMA_PEDESTAL.get())
                .add(BlockRegistry.SILVER_SUPPORT_ALBUM_PEDESTAL.get()).add(BlockRegistry.SILVER_UMA_PEDESTAL.get())
                .add(BlockRegistry.UMA_STATUES.get())
                .add(BlockRegistry.THREE_GODDESS_UPPER.get()).add(BlockRegistry.TRAINING_FACILITY.get());
        
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(BlockRegistry.DISASSEMBLY_BLOCK.get()).add(BlockRegistry.SKILL_LEARNING_TABLE.get())
                .add(BlockRegistry.REGISTER_LECTERN.get()).add(BlockRegistry.UMA_SELECT_BLOCK.get());
        
        this.tag(UmapyoiBlockTags.TRACK_TURF)
                .add(Blocks.GRASS_BLOCK)
                .add(Blocks.DIRT_PATH)
                .add(Blocks.CRIMSON_NYLIUM)
                .add(Blocks.WARPED_NYLIUM);
        
        this.tag(UmapyoiBlockTags.TRACK_DIRT)
                .add(Blocks.DIRT)
                .add(Blocks.PODZOL)
                .add(Blocks.ROOTED_DIRT)
                .add(Blocks.COARSE_DIRT)
                .addTag(BlockTags.SAND);
        
        this.tag(UmapyoiBlockTags.TRACK_SNOW)
                .addTag(BlockTags.SNOW);
    }
}
