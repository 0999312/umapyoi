package net.tracen.umapyoi.data;

import org.jetbrains.annotations.Nullable;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.data.tag.UmapyoiBlockTags;

public class UmapyoiBlockTagProvider extends BlockTagsProvider {

    public UmapyoiBlockTagProvider(DataGenerator datagen, @Nullable ExistingFileHelper existingFileHelper) {
        super(datagen, Umapyoi.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(UmapyoiBlockTags.ROAD_TURF).add(Blocks.GRASS_BLOCK, Blocks.DIRT_PATH);
        tag(UmapyoiBlockTags.ROAD_DIRT).addTag(BlockTags.DIRT).addTag(BlockTags.SAND);
        tag(UmapyoiBlockTags.ROAD_SNOW).addTag(BlockTags.SNOW).addTag(BlockTags.ICE);
    }

}
