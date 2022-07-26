package net.trc.umapyoi.data;

import cn.mcmod_mmf.mmlib.data.AbstractLangProvider;
import net.minecraft.data.DataGenerator;
import net.trc.umapyoi.Umapyoi;
import net.trc.umapyoi.item.ItemRegistry;

public class UmapyoiLangProvider extends AbstractLangProvider {

    public UmapyoiLangProvider(DataGenerator gen) {
        super(gen, Umapyoi.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(ItemRegistry.HACHIMI_MID.get(), "Hachimi Drink");
        add(ItemRegistry.HACHIMI_BIG.get(), "Hachimi Extreme");
        add(ItemRegistry.ROYAL_BITTER.get(), "Royal BitterJuice");
        add(ItemRegistry.JEWEL.get(), "Carrot Jewel");
        
        add(ItemRegistry.CUPCAKE.get(), "Plain Cupcake");
        add(ItemRegistry.SWEET_CUPCAKE.get(), "Sweet Cupcake");
        add(ItemRegistry.BLANK_UMA_SOUL.get(), "Blank Umamusume Soul");
        add(ItemRegistry.UMA_SOUL.get(), "Umamusume Soul");
        
        add(ItemRegistry.TRAINNING_SUIT.get(), "Tracen Training Suit");
        add(ItemRegistry.SUMMER_UNIFORM.get(), "Tracen Summer Uniform");
        add(ItemRegistry.WINTER_UNIFORM.get(), "Tracen Winter Uniform");
        
        add(ItemRegistry.SPEED_LOW_ITEM.get(), "Speed Notepad");
        add(ItemRegistry.SPEED_MID_ITEM.get(), "Speed Writings");
        add(ItemRegistry.SPEED_HIGH_ITEM.get(), "Speed Scroll");
        
        add(ItemRegistry.STAMINA_LOW_ITEM.get(), "Stamina Notepad");
        add(ItemRegistry.STAMINA_MID_ITEM.get(), "Stamina Writings");
        add(ItemRegistry.STAMINA_HIGH_ITEM.get(), "Stamina Scroll");
        
        add(ItemRegistry.STRENGTH_LOW_ITEM.get(), "Strength Notepad");
        add(ItemRegistry.STRENGTH_MID_ITEM.get(), "Strength Writings");
        add(ItemRegistry.STRENGTH_HIGH_ITEM.get(), "Strength Scroll");
        
        add(ItemRegistry.MENTALITY_LOW_ITEM.get(), "Mentality Notepad");
        add(ItemRegistry.MENTALITY_MID_ITEM.get(), "Mentality Writings");
        add(ItemRegistry.MENTALITY_HIGH_ITEM.get(), "Mentality Scroll");
        
        add(ItemRegistry.WISDOM_LOW_ITEM.get(), "Wisdom Notepad");
        add(ItemRegistry.WISDOM_MID_ITEM.get(), "Wisdom Writings");
        add(ItemRegistry.WISDOM_HIGH_ITEM.get(), "Wisdom Scroll");
        
        add("curios.identifier.uma_soul", "Umamusume Soul");
        add("curios.identifier.uma_suit", "Umamusume Apparel");
        add("itemGroup.umapyoi", "Umapyoi");
        addTooltip(".umadata.name", "Umamusume's Name:%s");
        addTooltip(".uma.common_uma", "Nameless Umamusume");
        addTooltip(".uma.gold_ship", "Gold Ship");
        addTooltip(".uma.sakura_sayono_o", "Sakura Chiyono O");
        addTooltip(".uma.special_week", "Special Week");
        addTooltip(".uma.tokai_teio", "Tokai Teio");
        addTooltip(".uma.oguri_cap", "Oguri Cap");
        addTooltip(".uma.oguri_cap_xmas", "[Miraculous White Star] Oguri Cap");
    }

}
