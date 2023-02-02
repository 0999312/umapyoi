package net.tracen.umapyoi.data;

import java.util.function.Supplier;

import cn.mcmod_mmf.mmlib.data.AbstractLangProvider;
import net.minecraft.Util;
import net.minecraft.data.DataGenerator;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.block.BlockRegistry;
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
        
        add(ItemRegistry.SKILL_BOOK.get(), "Skill Book");
        
        add(BlockRegistry.THREE_GODDESS.get(), "Three Goddesses Statue");
        add(BlockRegistry.THREE_GODDESS_UPPER.get(), "Three Goddesses Statue");
        add(BlockRegistry.TRAINING_FACILITY.get(), "Training Facility");
        add(BlockRegistry.SKILL_LEARNING_TABLE.get(), "Skill Learning Table");
        add(BlockRegistry.REGISTER_LECTERN.get(), "Retire Register Lectern");
        
        add(BlockRegistry.UMA_PEDESTAL.get(), "Umamusume Pedestal");
        
        add(BlockRegistry.SUPPORT_ALBUM_PEDESTAL.get(), "Support Album Pedestal");

        add("curios.modifiers.uma_soul", "When being umamusume:");
        add("curios.modifiers.uma_suit", "When wearing apparel:");
        
        add("curios.identifier.uma_soul", "Umamusume Soul");
        add("curios.identifier.uma_suit", "Umamusume Apparel");
        add("itemGroup.umapyoi", "Umapyoi");
        
        addTooltip(".umadata.name", "Umamusume's Name:%s");        
        addTooltip(".umafactor.data", "Has %s and %d more factors.");
        
        addTooltip(".supports", "Supports:");        
        addTooltip(".supporters", "Supporters:");
        
        addSupport(TrainingSupportRegistry.SPEED_SUPPORT, "Speed Increase");
        addSupport(TrainingSupportRegistry.STAMINA_SUPPORT, "Stamina Increase");
        addSupport(TrainingSupportRegistry.STRENGTH_SUPPORT, "Strength Increase");
        addSupport(TrainingSupportRegistry.GUTS_SUPPORT, "Guts Increase");
        addSupport(TrainingSupportRegistry.WISDOM_SUPPORT, "Wisdom Increase");
        addSupport(TrainingSupportRegistry.SKILL_SUPPORT, "Learning Skill");
        
        addFactor(UmaFactorRegistry.SPEED_FACTOR, "Speed Factor");
        addFactor(UmaFactorRegistry.STAMINA_FACTOR, "Stamina Factor");
        addFactor(UmaFactorRegistry.STRENGTH_FACTOR, "Strength Factor");
        addFactor(UmaFactorRegistry.GUTS_FACTOR, "Guts Factor");
        addFactor(UmaFactorRegistry.WISDOM_FACTOR, "Wisdom Factor");
        
        addSkill(UmaSkillRegistry.BASIC_PACE, "Basic Pace");
        addSkill(UmaSkillRegistry.LAST_LEG, "Last Leg");
        addSkill(UmaSkillRegistry.HEART_AND_SOUL, "Heart and Soul");
        addSkill(UmaSkillRegistry.DEEP_BREATHS, "Deep Breaths");
        addSkill(UmaSkillRegistry.COOLDOWN, "Cooldown");
        
        add("container.umapyoi.three_goddess", "Three Goddesses Statue");
        add("container.umapyoi.training_facility", "Training Facility");
        add("container.umapyoi.skill_learning", "Skill Learning Table");
        add("container.umapyoi.retire_register", "Retire Register");
        
        add("key.category.umapyoi", "Umapyoi");
        add("key.umapyoi.use_skill", "Use Skill");
        add("key.umapyoi.select_former_skill", "Select Former Skill");
        add("key.umapyoi.select_latter_skill", "Select Former Skill");
        
        add("umapyoi.skill.no_require","Nothing required.");
        add("umapyoi.skill.require_wisdom","%s wisdom required.");
        add("umapyoi.skill_not_ready","Skill is not ready, %d seconds remaining.");
        
        add("umapyoi.motivation.bad", "Slump");
        add("umapyoi.motivation.down", "Low");
        add("umapyoi.motivation.normal", "Normal");
        add("umapyoi.motivation.good", "High");
        add("umapyoi.motivation.perfect", "Peak");
        
        add("umastatus.level.0", "G-");
        add("umastatus.level.1", "G");
        add("umastatus.level.2", "F");
        add("umastatus.level.3", "E");
        add("umastatus.level.4", "D");
        add("umastatus.level.5", "C");
        add("umastatus.level.6", "B");
        add("umastatus.level.7", "A");
        add("umastatus.level.8", "A+");
        add("umastatus.level.9", "S");
        add("umastatus.level.10", "S+");
        add("umastatus.level.11", "SS");
        add("umastatus.level.12", "SS+");
        
        addUma(UmaDataRegistry.COMMON_UMA, "Nameless Umamusume");
        addUma(UmaDataRegistry.GOLD_SHIP, "Gold Ship");
        addUma(UmaDataRegistry.SAKURA_CHIYONO_O, "Sakura Chiyono O");
        addUma(UmaDataRegistry.SPECIAL_WEEK, "Special Week");
        addUma(UmaDataRegistry.TOKAI_TEIO, "Tokai Teio");
        addUma(UmaDataRegistry.OGURI_CAP, "Oguri Cap");
        addUma(UmaDataRegistry.AGNUS_TACHYON, "Agnus Tachyon");
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
        
        addSupportCard(SupportCardRegistry.TEST_SUPPOERS, "Test Supporters Card");
        addSupportCard(SupportCardRegistry.TEST_1, "Test 1 Card");
        addSupportCard(SupportCardRegistry.TEST_2, "Test 2 Card");
        addSupportCard(SupportCardRegistry.TEST_3, "Test 3 Card");
        addSupportCard(SupportCardRegistry.TEST_4, "Test 4 Card");
        addSupportCard(SupportCardRegistry.TEST_5, "Test 5 Card");
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
        add(key.getDescriptionId(), name);
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
