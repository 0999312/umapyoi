package net.tracen.umapyoi.data.tag;

import cn.mcmod_mmf.mmlib.utils.TagUtils;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.tracen.umapyoi.Umapyoi;

public class UmapyoiBlockTags {
    public static final TagKey<Block> ROAD_TURF = TagUtils.modBlockTag(Umapyoi.MODID, "road/turf");
    public static final TagKey<Block> ROAD_DIRT = TagUtils.modBlockTag(Umapyoi.MODID, "road/dirf");
    public static final TagKey<Block> ROAD_SNOW = TagUtils.modBlockTag(Umapyoi.MODID, "road/snow");
}
