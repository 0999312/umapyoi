package net.tracen.umapyoi.data;

import java.util.function.Supplier;

import cn.mcmod_mmf.mmlib.data.AbstractLangProvider;
import net.minecraft.Util;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.effect.MobEffectRegistry;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.registry.SupportCardRegistry;
import net.tracen.umapyoi.registry.TrainingSupportRegistry;
import net.tracen.umapyoi.registry.UmaDataRegistry;
import net.tracen.umapyoi.registry.UmaFactorRegistry;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.factors.UmaFactor;
import net.tracen.umapyoi.registry.skills.UmaSkill;
import net.tracen.umapyoi.registry.training.TrainingSupport;
import net.tracen.umapyoi.registry.training.card.SupportCard;
import net.tracen.umapyoi.registry.umadata.UmaData;

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
        add(ItemRegistry.BLANK_UMA_SOUL.get(), "Faded Umamusume Soul");
        add(ItemRegistry.UMA_SOUL.get(), "Umamusume Soul");
        add(ItemRegistry.UMA_FACTOR_ITEM.get(), "Umamusume Wish");

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

        add(ItemRegistry.MENTALITY_LOW_ITEM.get(), "Guts Notepad");
        add(ItemRegistry.MENTALITY_MID_ITEM.get(), "Guts Writings");
        add(ItemRegistry.MENTALITY_HIGH_ITEM.get(), "Guts Scroll");

        add(ItemRegistry.WISDOM_LOW_ITEM.get(), "Wisdom Notepad");
        add(ItemRegistry.WISDOM_MID_ITEM.get(), "Wisdom Writings");
        add(ItemRegistry.WISDOM_HIGH_ITEM.get(), "Wisdom Scroll");
        
        add(ItemRegistry.SMALL_ENERGY_DRINK.get(), "Vital 20");
        add(ItemRegistry.MEDIUM_ENERGY_DRINK.get(), "Vital 40");
        add(ItemRegistry.LARGE_ENERGY_DRINK.get(), "Vital 65");

        add(ItemRegistry.SKILL_BOOK.get(), "Skill Book");
        
        add(ItemRegistry.BLANK_TICKET.get(), "Blank Ticket");
        
        add(ItemRegistry.UMA_TICKET.get(), "Umamusume Ticket");
        add(ItemRegistry.SR_UMA_TICKET.get(), "Golden Umamusume Ticket");
        add(ItemRegistry.SSR_UMA_TICKET.get(), "Rainbow Umamusume Ticket");
        
        add(ItemRegistry.CARD_TICKET.get(), "Support Card Ticket");
        add(ItemRegistry.SR_CARD_TICKET.get(), "Golden Support Card Ticket");
        add(ItemRegistry.SSR_CARD_TICKET.get(), "Rainbow Support Card Ticket");
        
        add(ItemRegistry.CRYSTAL_SILVER.get(), "Silver Umamusume Crystal");
        add(ItemRegistry.CRYSTAL_GOLD.get(), "Golden Umamusume Crystal");
        add(ItemRegistry.CRYSTAL_RAINBOW.get(), "Rainbow Umamusume Crystal");
        
        add(ItemRegistry.HORSESHOE_SILVER.get(), "Silver Horseshoe Crystal");
        add(ItemRegistry.HORSESHOE_GOLD.get(), "Golden Horseshoe Crystal");
        add(ItemRegistry.HORSESHOE_RAINBOW.get(), "Rainbow Horseshoe Crystal");

        add(BlockRegistry.THREE_GODDESS.get(), "Three Goddesses Statue");
        add(BlockRegistry.THREE_GODDESS_UPPER.get(), "Three Goddesses Statue");
        add(BlockRegistry.TRAINING_FACILITY.get(), "Training Terminal");
        add(BlockRegistry.SKILL_LEARNING_TABLE.get(), "Skill Learning Table");
        add(BlockRegistry.REGISTER_LECTERN.get(), "Retire Register Lectern");
        
        add(BlockRegistry.SILVER_UMA_PEDESTAL.get(), "Silver Umamusume Pedestal");
        add(BlockRegistry.SILVER_SUPPORT_ALBUM_PEDESTAL.get(), "Silver Support Album Pedestal");

        add(BlockRegistry.UMA_PEDESTAL.get(), "Golden Umamusume Pedestal");
        add(BlockRegistry.SUPPORT_ALBUM_PEDESTAL.get(), "Golden Support Album Pedestal");
        
        add(BlockRegistry.DISASSEMBLY_BLOCK.get(), "Transfer Register Lectern");
        add(BlockRegistry.UMA_SELECT_BLOCK.get(), "Selection Lectern");
        
        add(BlockRegistry.UMA_STATUES.get(), "Umamusume Statue");
        
        add(ItemRegistry.SWIMSUIT.get(), "Tracen Swimsuit");

        add(MobEffectRegistry.PANICKING.get(), "Panicking");

        add("curios.modifiers.uma_soul", "When being umamusume:");
        add("curios.modifiers.uma_suit", "When wearing apparel:");

        add("curios.identifier.uma_soul", "Umamusume Soul");
        add("curios.identifier.uma_suit", "Umamusume Apparel");
        add("itemGroup.umapyoi", "Umapyoi");
        
        addTooltip(".umadata.name", "Umamusume's Name:%s");
        addTooltip(".support_card.name", "Support Card:%s");
        addTooltip(".umafactor.data", "Has %s and %d more factors.");

        addTooltip(".supports", "Supports:");
        addTooltip(".supporters", "Supporters:");

        addTooltip(".support_card.press_shift_for_supports", "Press Shift Button for support details.");
        addTooltip(".support_card.press_ctrl_for_supporters", "Press Ctrl Button for supporter details.");
        addTooltip(".uma_soul.soul_details", "Umamusume Soul Status:");
        addTooltip(".press_shift_for_details", "Press Shift Button for details.");
        addTooltip(".factors.factors_details", "Factors:");
        
        addTooltip(".uma_soul.ranking", "Total Ranking: %s");

        addTooltip(".uma_soul.speed_details", "Speed: %s / %s");
        addTooltip(".uma_soul.stamina_details", "Stamina: %s / %s");
        addTooltip(".uma_soul.strength_details", "Strength: %s / %s");
        addTooltip(".uma_soul.guts_details", "Guts: %s / %s");
        addTooltip(".uma_soul.wisdom_details", "Wisdom: %s / %s");

        addSupport(TrainingSupportRegistry.SPEED_SUPPORT, "Speed Increase");
        addSupport(TrainingSupportRegistry.STAMINA_SUPPORT, "Stamina Increase");
        addSupport(TrainingSupportRegistry.STRENGTH_SUPPORT, "Strength Increase");
        addSupport(TrainingSupportRegistry.GUTS_SUPPORT, "Guts Increase");
        addSupport(TrainingSupportRegistry.WISDOM_SUPPORT, "Wisdom Increase");
        addSupport(TrainingSupportRegistry.SKILL_SUPPORT, "Learning Skill");
        addSupport(TrainingSupportRegistry.AP_SUPPORT, "Action Pt Increase");

        addFactor(UmaFactorRegistry.SPEED_FACTOR, "Speed Factor");
        addFactor(UmaFactorRegistry.STAMINA_FACTOR, "Stamina Factor");
        addFactor(UmaFactorRegistry.STRENGTH_FACTOR, "Strength Factor");
        addFactor(UmaFactorRegistry.GUTS_FACTOR, "Guts Factor");
        addFactor(UmaFactorRegistry.WISDOM_FACTOR, "Wisdom Factor");
        
        addFactor(UmaFactorRegistry.PHYSIQUE_FACTOR, "Physique Factor");
        addFactor(UmaFactorRegistry.TELENT_FACTOR, "Talent Factor");
        addFactor(UmaFactorRegistry.MEMORY_FACTOR, "Memory Factor");
        addFactor(UmaFactorRegistry.ACTIONS_FACTOR, "Action Pt Factor");
        
        addFactor(UmaFactorRegistry.WHITE_SPEED_FACTOR, "Speed Experiences");
        addFactor(UmaFactorRegistry.WHITE_STAMINA_FACTOR, "Stamina Experiences");
        addFactor(UmaFactorRegistry.WHITE_STRENGTH_FACTOR, "Strength Experiences");
        addFactor(UmaFactorRegistry.WHITE_GUTS_FACTOR, "Guts Experiences");
        addFactor(UmaFactorRegistry.WHITE_WISDOM_FACTOR, "Wisdom Experiences");
        addFactor(UmaFactorRegistry.WHITE_TELENT_FACTOR, "Effective Learning");
        addFactor(UmaFactorRegistry.WHITE_ACTIONS_FACTOR, "Action Planning");

        addSkill(UmaSkillRegistry.BASIC_PACE, "Basic Pace");
        addSkill(UmaSkillRegistry.LAST_LEG, "Last Leg");
        addSkill(UmaSkillRegistry.HEART_AND_SOUL, "Heart and Soul");
        addSkill(UmaSkillRegistry.DEEP_BREATHS, "Deep Breaths");
        addSkill(UmaSkillRegistry.COOLDOWN, "Cooldown");
        addSkill(UmaSkillRegistry.SERENE, "Serene");
        
        addSkill(UmaSkillRegistry.STEEL_WILL, "Will of Steel");
        addSkill(UmaSkillRegistry.BIG_EATER, "Big Eater");
        addSkill(UmaSkillRegistry.NUTRITIONAL_SUPPLEMENTS, "Nutritional Supplements");
        
        addSkill(UmaSkillRegistry.LOW_HEALTH_BUFF, "All I Have");
        addSkill(UmaSkillRegistry.ADV_LOWHEALTH_BUFF, "Prepared to Die");
        addSkill(UmaSkillRegistry.LOW_HEALTH_HEAL, "One Chance");
        addSkill(UmaSkillRegistry.ADV_LOWHEALTH_HEAL, "From the Brink");
        
        addSkill(UmaSkillRegistry.MOUNTAIN_CLIMBER, "Mountain Climber");
        addSkill(UmaSkillRegistry.DIG_SPEED, "Mining Skills");
        addSkill(UmaSkillRegistry.TURF_RUNNER, "Turf Runner");
        addSkill(UmaSkillRegistry.DIRT_RUNNER, "Dirt Runner");
        addSkill(UmaSkillRegistry.SNOW_RUNNER, "Snow Runner");

        add("container.umapyoi.three_goddess", "Three Goddesses Statue");
        add("container.umapyoi.training_facility", "Training Terminal");
        add("container.umapyoi.skill_learning", "Skill Learning Table");
        add("container.umapyoi.retire_register", "Retire Register");
        add("container.umapyoi.disassembly_block", "Transfer Register");
        add("container.umapyoi.umaselect", "Selection Register");

        add("key.category.umapyoi", "Umapyoi");
        add("key.umapyoi.use_skill", "Use Skill");
        add("key.umapyoi.select_former_skill", "Select Former Skill");
        add("key.umapyoi.select_latter_skill", "Select Former Skill");

        add("umapyoi.skill.no_require", "Nothing required.");
        add("umapyoi.skill.require_wisdom", "%s wisdom required.");
        add("umapyoi.skill.slot_needed", "Has reached the learning limit.");
        add("umapyoi.skill.has_retired", "This Umamusume is retired.");
        add("umapyoi.skill.has_learned_skill", "This skill has learned.");
        add("umapyoi.skill.passive", "This is a passive skill.");
        add("umapyoi.not_enough_ap", "Not enough action points.");
        add("umapyoi.uma_pedestal.cannot_add_item", "Can not add item anymore.");

        add("umapyoi.motivation.bad", "Slump");
        add("umapyoi.motivation.down", "Low");
        add("umapyoi.motivation.normal", "Normal");
        add("umapyoi.motivation.good", "High");
        add("umapyoi.motivation.perfect", "Peak");
        
        add("book.umapyoi.trainers_manual.title", "Tracen Trainers' Manual");
        add("book.umapyoi.trainers_manual.subtitle", "Tracen Academy");
        add("book.umapyoi.trainers_manual.landing_text", "This is the manual of Umapyoi mod.");
        
        add("umapyoi.no_umasoul_equiped", "Please equip Umamusume Soul first.");
        add("umapyoi.learning.no_learning_time", "No longer able to learn more.");
        add("umapyoi.learning.can_not_learn", "No longer possible to continue with this part of the learning process.");
        
        add("umapyoi.jei.disassembly", "Transfer Register");
        add("umapyoi.jei.gacha", "Pedestal Summon");
        add("umapyoi.jei.gacha.need_book", "Need a book on pedestal");

        add("entity.minecraft.villager.umapyoi.trainer", "Trainer");
        
        add("umastatus.level.0", "§7G-");
        add("umastatus.level.1", "§7G");
        add("umastatus.level.2", "§7G+");
        add("umastatus.level.3", "§5F");
        add("umastatus.level.4", "§5F+");
        add("umastatus.level.5", "§dE");
        add("umastatus.level.6", "§dE+");
        add("umastatus.level.7", "§bD");
        add("umastatus.level.8", "§bD+");
        add("umastatus.level.9", "§aC");
        add("umastatus.level.10", "§aC+");
        add("umastatus.level.11", "§4B");
        add("umastatus.level.12", "§4B+");
        add("umastatus.level.13", "§cA");
        add("umastatus.level.14", "§cA+");
        add("umastatus.level.15", "§eS");
        add("umastatus.level.16", "§eS+");
        add("umastatus.level.17", "§eSS");
        add("umastatus.level.18", "§eSS+");
        
        add("umastatus.level.19", "§9UG-");
        add("umastatus.level.20", "§9UG");
        add("umastatus.level.21", "§9UG+");
        add("umastatus.level.22", "§9UF-");
        add("umastatus.level.23", "§9UF");
        add("umastatus.level.24", "§9UF+");
        add("umastatus.level.25", "§9UE");
        add("umastatus.level.26", "§9UE+");
        add("umastatus.level.27", "§9UD");
        add("umastatus.level.28", "§9UD+");
        add("umastatus.level.29", "§9UC");
        add("umastatus.level.30", "§9UC+");
        add("umastatus.level.31", "§9UB");
        add("umastatus.level.32", "§9UB+");
        add("umastatus.level.33", "§9UA");
        add("umastatus.level.34", "§9UA+");
        add("umastatus.level.35", "§9US");
        add("umastatus.level.36", "§9US+");
        add("umastatus.level.37", "§9USS");
        add("umastatus.level.38", "§9USS+");
        add("umastatus.level.39", "§6MAX");
        
        addUma(UmaDataRegistry.COMMON_UMA, "Nameless Bay Umamusume");
        addUma(UmaDataRegistry.COMMON_UMA_A, "Nameless Perlino Umamusume");
        addUma(UmaDataRegistry.COMMON_UMA_B, "Nameless Gray Umamusume");
        addUma(UmaDataRegistry.COMMON_UMA_C, "Nameless Aqua Umamusume");
        
        addUma(UmaDataRegistry.MEJIRO_MCQUEEN, "Mejiro McQueen");
        addUma(UmaDataRegistry.GOLD_SHIP, "Gold Ship");
        addUma(UmaDataRegistry.SAKURA_CHIYONO_O, "Sakura Chiyono O");
        addUma(UmaDataRegistry.SPECIAL_WEEK, "Special Week");
        addUma(UmaDataRegistry.TOKAI_TEIO, "Tokai Teio");
        addUma(UmaDataRegistry.OGURI_CAP, "Oguri Cap");
        addUma(UmaDataRegistry.AGNUS_TACHYON, "Agnes Tachyon");
        addUma(UmaDataRegistry.HARU_URARA, "Haru Urara");
        addUma(UmaDataRegistry.TAMAMO_CROSS, "Tamamo Cross");
        addUma(UmaDataRegistry.OGURI_CAP_XMAS, "[Miraculous White Star] Oguri Cap");
        addUma(UmaDataRegistry.GOLD_SHIP_WATER, "[Run! Fun! Watergun!!] Gold Ship");
        addUma(UmaDataRegistry.SAKURA_BAKUSHIN_O, "Sakura Bakushin O");
        addUma(UmaDataRegistry.MATIKANEFUKUKITARU, "Matikane Fukukitaru");
        addUma(UmaDataRegistry.RICE_SHOWER, "Rice Shower");
        addUma(UmaDataRegistry.SEIUN_SKY, "Seiun Sky");
        addUma(UmaDataRegistry.VODKA, "Vodka");

        addUma(UmaDataRegistry.MANHATTAN_CAFE, "Manhattan cafe");
        addUma(UmaDataRegistry.MEJIRO_ARDAN, "Mejiro Ardan");

        addUma(UmaDataRegistry.DAITAKU_HELIOS, "Daitaku Helios");
        addUma(UmaDataRegistry.SWEEP_TOSHO, "Sweep Tosho");
        addUma(UmaDataRegistry.GOLD_CITY, "Gold City");

        addUma(UmaDataRegistry.CURREN_CHAN, "Curren Chan");
        addUma(UmaDataRegistry.ASTON_MACHAN, "Aston Machan");
        addUma(UmaDataRegistry.SILENCE_SUZUKA, "Silence Suzuka");
        addUma(UmaDataRegistry.MR_CB, "Mr.CB");
        addUma(UmaDataRegistry.TAMAMO_CROSS_FESTIVAL, "[Ferocious Thunder] Tamamo Cross");
        addUma(UmaDataRegistry.GRASS_WONDER, "Grass Wonder");
        
        addUma(UmaDataRegistry.NEO_UNIVERSE, "Neo Universe");
        addUma(UmaDataRegistry.NICE_NATURE, "Nice Nature");
        addUma(UmaDataRegistry.MAYANO_TOP_GUN, "Mayano Topgun");
        addUma(UmaDataRegistry.TAIKI_SHUTTLE, "Taiki Shuttle");
        addUma(UmaDataRegistry.MEISHO_DOTOU, "Meisho Doto");
        addUma(UmaDataRegistry.KITASAN_BLACK, "Kitasan Black");
        addUma(UmaDataRegistry.SATONO_DIAMOND, "Satono Diamond");
        addUma(UmaDataRegistry.COPANO_RICKEY, "Copano Rickey");
        
        addUma(UmaDataRegistry.CURREN_CHAN_DRESS, "[Calend's Ma Chérie] Curren Chan");
        
        addUma(UmaDataRegistry.SYMBOLI_RUDOLF, "Symboli Rudolf");
        addUma(UmaDataRegistry.NARITA_TOP_ROAD, "Narita Top Road");
        
        addUma(UmaDataRegistry.VENUS_PARK, "Venus Park");
        
        addUma(UmaDataRegistry.AGNUS_TACHYON_SWIM, "[Lunatic Lab] Agnus Tachyon");
        addUma(UmaDataRegistry.MIHONO_BOURBON, "Mihono Bourbon");
        addUma(UmaDataRegistry.MATIKANETANNHAUSER, "Makikanetannhauser");
        addUma(UmaDataRegistry.KAWAKAMI_PRINCESS, "Kawakami Princess");
        addUma(UmaDataRegistry.TWIN_TURBO, "Twin Turbo");
        addUma(UmaDataRegistry.LITTLE_COCON, "Little Cocon");
        addUma(UmaDataRegistry.SAKURA_LAUREL, "Sakura Laurel");
        
        addUma(UmaDataRegistry.NARITA_TAISHIN, "Narita Taishin");
        addUma(UmaDataRegistry.TM_OPERA_O, "TM Opera O");
        addUma(UmaDataRegistry.ADMIRE_VEGA, "Admire Vega");
        addUma(UmaDataRegistry.JUNGLE_POCKET, "Jungle Pocket");
        addUma(UmaDataRegistry.SYAMEIMARU_ZHENG, "Syameimaru Zheng");
        addUma(UmaDataRegistry.DUMNHEINT, "Dumnheint");
        addUma(UmaDataRegistry.DARLEY_ARABIAN, "Darley Arabian");
        addUma(UmaDataRegistry.GODOLPHIN_BARB, "Godolphin Barb");
        addUma(UmaDataRegistry.BYERLEY_TURK, "Byerley Turk");
        addUma(UmaDataRegistry.FINE_MOTION, "Fine Motion");
        addUma(UmaDataRegistry.SMART_FALCON, "Smart Falcon");
        
        addUma(UmaDataRegistry.HISHI_MIRACLE, "Hishi Miracle");

        addUma(UmaDataRegistry.GOLD_CITY_AUTUMN, "[Akizakura Danzatrice] Gold City");
        addUma(UmaDataRegistry.GRASS_WONDER_UMANET, "[Saint Jade Healer] Grass Wonder");
        addUma(UmaDataRegistry.SATONO_DIAMOND_FRENCH, "[Chevalier Blue] Satono Diamond");
        
        addUma(UmaDataRegistry.MANHATTAN_CAFE_VALENTINE, "[Willow Night] Manhattan cafe");
        
        add(Util.makeDescriptionId("umadata", new ResourceLocation(Umapyoi.MODID, "super_creek")), "Super Creek");
        add(Util.makeDescriptionId("umadata", new ResourceLocation(Umapyoi.MODID, "ks_miracle")), "K.S. Miracle");
        add(Util.makeDescriptionId("umadata", new ResourceLocation(Umapyoi.MODID, "mejiro_ramonu")), "Mejiro Ramonu");

        addSupportCard(SupportCardRegistry.BLANK_CARD, "Blank Support Card");
        
        addSupportCard(SupportCardRegistry.BASIC_SPEED_CARD, "[Basic Training] Speed Training");
        addSupportCard(SupportCardRegistry.BASIC_STAMINA_CARD, "[Basic Training] Stamina Training");
        addSupportCard(SupportCardRegistry.BASIC_STRENGTH_CARD, "[Basic Training] Strength Training");
        addSupportCard(SupportCardRegistry.BASIC_GUTS_CARD, "[Basic Training] Guts Training");
        addSupportCard(SupportCardRegistry.BASIC_WISDOM_CARD, "[Basic Training] Wisdom Training");
        
        addSupportCard(SupportCardRegistry.ADV_SPEED_CARD, "[Advanced Training] Speed Training");
        addSupportCard(SupportCardRegistry.ADV_STAMINA_CARD, "[Advanced Training] Stamina Training");
        addSupportCard(SupportCardRegistry.ADV_STRENGTH_CARD, "[Advanced Training] Strength Training");
        addSupportCard(SupportCardRegistry.ADV_GUTS_CARD, "[Advanced Training] Guts Training");
        addSupportCard(SupportCardRegistry.ADV_WISDOM_CARD, "[Advanced Training] Wisdom Training");
        
        addSupportCard(SupportCardRegistry.SPEED_MASTER_CARD, "[Master Training] Speed Training");
        addSupportCard(SupportCardRegistry.STAMINA_MASTER_CARD, "[Master Training] Stamina Training");
        addSupportCard(SupportCardRegistry.STRENGTH_MASTER_CARD, "[Master Training] Strength Training");
        addSupportCard(SupportCardRegistry.GUTS_MASTER_CARD, "[Master Training] Guts Training");
        addSupportCard(SupportCardRegistry.WISDOM_MASTER_CARD, "[Master Training] Wisdom Training");
        
        addSupportCard(SupportCardRegistry.R_AGNUS_TACHYON, "[Tracen Academy] Agnes Tachyon");
        addSupportCard(SupportCardRegistry.R_KITASANBLACK, "[Tracen Academy] Kitasan Black");
        addSupportCard(SupportCardRegistry.R_KS_MIRACLE, "[Tracen Academy] K.S. Miracle");
        addSupportCard(SupportCardRegistry.R_OGURICAP, "[Tracen Academy] Oguri Cap");
        addSupportCard(SupportCardRegistry.R_SUPERCREEK, "[Tracen Academy] Super Creek");
        
        addSupportCard(SupportCardRegistry.R_TURF_TRAINING, "[Tracen Academy] Standard Turf Training");
        addSupportCard(SupportCardRegistry.R_DIRT_TRAINING, "[Tracen Academy] Standard Dirt Training");
        addSupportCard(SupportCardRegistry.R_SNOW_TRAINING, "[Tracen Academy] Standard Snow Training");
        
        addSupportCard(SupportCardRegistry.SR_AGNUS_TACHYON, "[Experimental Study of Lifeform A] Agnes Tachyon");
        addSupportCard(SupportCardRegistry.SSR_AGNUS_TACHYON, "[Q!=0] Agnes Tachyon");
        addSupportCard(SupportCardRegistry.SSR_OGURICAP, "['You'll Be Dearly Beloved'] Oguri Cap");
        addSupportCard(SupportCardRegistry.SSR_KS_MIRACLE, "[To you] K.S. Miracle");
        addSupportCard(SupportCardRegistry.SSR_KITASANBLACK, "[Pushed by the Approaching Passion] Kitasan Black");
        addSupportCard(SupportCardRegistry.SSR_SUPERCREEK, "[A Grain of Peace] Super Creek");
        
        addSupportCard(SupportCardRegistry.SSR_FINE_MOTION, "[Gratitude Up to One's Fingertips] Fine Motion");
        
        addSupportCard(SupportCardRegistry.SSR_RUDOLF_G, "[Unmistakable Emperor] Symboli Rudolf");
        addSupportCard(SupportCardRegistry.SSR_MEJIRO_RAMONU_W, "[Radiant] Mejiro Ramonu");
        
        addAdvTitle("umapyoi.root", "Welcome to Tracen Academy!");
        addAdvDesc("umapyoi.root", "Start your adventure with umamusume!");
        
        addAdvTitle("umapyoi.three_goddesses", "The Three Goddesses");
        addAdvDesc("umapyoi.three_goddesses", "Build the Three Goddesses Statue.");
        
        addAdvTitle("umapyoi.summon_pedestal", "Gacha Time!");
        addAdvDesc("umapyoi.summon_pedestal", "Craft a new Umamusume Pedestal.");
        
        addAdvTitle("umapyoi.gold_pedestal", "Getting an Upgrade, But Pedestal");
        addAdvDesc("umapyoi.gold_pedestal", "Upgrade your Umamusume Pedestal.");
        
        addAdvTitle("umapyoi.blank_uma_soul", "Starting Future");
        addAdvDesc("umapyoi.blank_uma_soul", "Got your first Umamusume Soul.");
        
        addAdvTitle("umapyoi.uma_soul", "Aoharu Soul");
        addAdvDesc("umapyoi.uma_soul", "Bless your faded soul in the Three Goddesses Statue.");
        
        addAdvTitle("umapyoi.training", "Three years between you and her have begun.");
        addAdvDesc("umapyoi.training", "Craft the Training Terminal.");

        addAdvTitle("umapyoi.support_pedestal", "Support Cards");
        addAdvDesc("umapyoi.support_pedestal", "Use a book to Umamusume Pedestal to get Support Pedestal.");
        
        addAdvTitle("umapyoi.skill_book", "Skill books");
        addAdvDesc("umapyoi.skill_book", "Found Skill books in dungeon or trainer villager.");
        
        addAdvTitle("umapyoi.register_lectern", "Three years between you and her have ended?");
        addAdvDesc("umapyoi.register_lectern", "Craft the Register Lectern to end your training.");
        
        addAdvTitle("umapyoi.inheritance", "Entrust");
        addAdvDesc("umapyoi.inheritance", "Finish a Umamusume's training and got her wishes.");
        
        addAdvTitle("umapyoi.transfer", "Special Transfer");
        addAdvDesc("umapyoi.transfer", "Craft the Transfer Lectern.");
        
        addAdvTitle("umapyoi.skill_learning_table", "Learning Time");
        addAdvDesc("umapyoi.skill_learning_table", "Craft the Skill Learning Table.");
        
        addAdvTitle("umapyoi.uma_ticket", "Wastepaper");
        addAdvDesc("umapyoi.uma_ticket", "Got the Blank Ticket.");
    }

    private void addSupportCard(Supplier<SupportCard> key, String name) {
        addSupportCard(key.get(), name);
    }

    private void addSupportCard(SupportCard key, String name) {
        add(Util.makeDescriptionId("support_card", key.getRegistryName()) + ".name", name);
    }

    private void addUma(Supplier<UmaData> key, String name) {
        addUma(key.get(), name);
    }

    private void addSupport(Supplier<TrainingSupport> key, String name) {
        addSupport(key.get(), name);
    }

    private void addSkill(Supplier<UmaSkill> key, String name) {
        addSkill(key.get(), name);
    }

    private void addFactor(Supplier<UmaFactor> key, String name) {
        addFactor(key.get(), name);
    }

    private void addUma(UmaData key, String name) {
        add(Util.makeDescriptionId("umadata", key.getRegistryName()), name);
    }

    private void addSupport(TrainingSupport key, String name) {
        add(key.getDescriptionId(), name);
    }

    private void addSkill(UmaSkill key, String name) {
        add(key.getDescriptionId(), name);
    }

    private void addFactor(UmaFactor key, String name) {
        add(key.getDescriptionId(), name);
    }
}
