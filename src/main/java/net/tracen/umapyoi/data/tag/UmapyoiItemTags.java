package net.tracen.umapyoi.data.tag;

import cn.mcmod_mmf.mmlib.utils.TagUtils;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.tracen.umapyoi.Umapyoi;

public class UmapyoiItemTags {
    public static final TagKey<Item> UMA_TICKET = TagUtils.modItemTag(Umapyoi.MODID, "uma_ticket");
    public static final TagKey<Item> SR_UMA_TICKET = TagUtils.modItemTag(Umapyoi.MODID, "sr_uma_ticket");
    public static final TagKey<Item> SSR_UMA_TICKET = TagUtils.modItemTag(Umapyoi.MODID, "ssr_uma_ticket");

    public static final TagKey<Item> CARD_TICKET = TagUtils.modItemTag(Umapyoi.MODID, "card_ticket");
    public static final TagKey<Item> SR_CARD_TICKET = TagUtils.modItemTag(Umapyoi.MODID, "sr_card_ticket");
    public static final TagKey<Item> SSR_CARD_TICKET = TagUtils.modItemTag(Umapyoi.MODID, "ssr_card_ticket");
}
