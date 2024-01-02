package net.tracen.umapyoi.data.tag;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.umadata.UmaData;

public class UmapyoiUmaDataTags {

    public static final TagKey<UmaData> FLAT_CHEST = UmapyoiUmaDataTags.umapyoiUmaDataTag("chest/flat");
    public static final TagKey<UmaData> TANNED_SKIN = UmapyoiUmaDataTags.umapyoiUmaDataTag("skin/tanned");

    public static TagKey<UmaData> umapyoiUmaDataTag(String path) {
        return TagKey.create(UmaData.REGISTRY_KEY, new ResourceLocation(Umapyoi.MODID, path));
    }

    public static TagKey<UmaData> modUmaDataTag(String modid, String path) {
        return TagKey.create(UmaData.REGISTRY_KEY, new ResourceLocation(modid, path));
    }
}
