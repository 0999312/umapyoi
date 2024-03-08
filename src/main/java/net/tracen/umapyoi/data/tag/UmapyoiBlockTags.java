package net.tracen.umapyoi.data.tag;

import cn.mcmod_mmf.mmlib.utils.TagUtils;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.tracen.umapyoi.Umapyoi;

public class UmapyoiBlockTags {
    public static final TagKey<Block> TRACK_TURF = TagUtils.modBlockTag(Umapyoi.MODID, "track/turf");
    public static final TagKey<Block> TRACK_DIRT = TagUtils.modBlockTag(Umapyoi.MODID, "track/dirt");
    public static final TagKey<Block> TRACK_SNOW = TagUtils.modBlockTag(Umapyoi.MODID, "track/snow");
}
