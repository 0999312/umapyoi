package net.tracen.umapyoi.registry;

import java.util.function.Supplier;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.skills.HealSkill;
import net.tracen.umapyoi.registry.skills.LastLegSkill;
import net.tracen.umapyoi.registry.skills.LowHealthBuffSkill;
import net.tracen.umapyoi.registry.skills.LowHealthHealSkill;
import net.tracen.umapyoi.registry.skills.NutritionalSupplementsSkill;
import net.tracen.umapyoi.registry.skills.SereneSkill;
import net.tracen.umapyoi.registry.skills.SkillType;
import net.tracen.umapyoi.registry.skills.SpeedSkill;
import net.tracen.umapyoi.registry.skills.SteelWillSkill;
import net.tracen.umapyoi.registry.skills.UmaSkill;

public class UmaSkillRegistry {
    public static final DeferredRegister<UmaSkill> SKILLS = DeferredRegister.create(UmaSkill.REGISTRY_KEY,
            Umapyoi.MODID);

    public static final Supplier<IForgeRegistry<UmaSkill>> REGISTRY = SKILLS.makeRegistry(UmaSkill.class,
            RegistryBuilder::new);

    public static final RegistryObject<UmaSkill> BASIC_PACE = SKILLS.register("basic_pace",
            () -> new SpeedSkill(new UmaSkill.Builder().type(SkillType.BUFF), 0, 400));

    public static final RegistryObject<UmaSkill> LAST_LEG = SKILLS.register("last_leg",
            () -> new LastLegSkill(new UmaSkill.Builder().type(SkillType.BUFF).actionPoint(400).requiredWisdom(2), 1,
                    100));
    public static final RegistryObject<UmaSkill> HEART_AND_SOUL = SKILLS.register("heart_and_soul",
            () -> new LastLegSkill(new UmaSkill.Builder().type(SkillType.BUFF).actionPoint(900).requiredWisdom(4), 2,
                    100));

    public static final RegistryObject<UmaSkill> DEEP_BREATHS = SKILLS.register("deep_breaths",
            () -> new HealSkill(new UmaSkill.Builder().type(SkillType.HEAL).actionPoint(400).requiredWisdom(2), 0));
    public static final RegistryObject<UmaSkill> COOLDOWN = SKILLS.register("cooldown",
            () -> new HealSkill(new UmaSkill.Builder().type(SkillType.HEAL).actionPoint(900).requiredWisdom(4), 1));
    
    public static final RegistryObject<UmaSkill> LOW_HEALTH_HEAL = SKILLS.register("low_health_heal",
            () -> new LowHealthHealSkill(new UmaSkill.Builder().type(SkillType.HEAL).actionPoint(400).requiredWisdom(2), 0));
    public static final RegistryObject<UmaSkill> ADV_LOWHEALTH_HEAL = SKILLS.register("adv_low_health_heal",
            () -> new LowHealthHealSkill(new UmaSkill.Builder().type(SkillType.HEAL).actionPoint(900).requiredWisdom(4), 1));
    
    public static final RegistryObject<UmaSkill> LOW_HEALTH_BUFF = SKILLS.register("low_health_buff",
            () -> new LowHealthBuffSkill(new UmaSkill.Builder().type(SkillType.BUFF).actionPoint(400).requiredWisdom(2), 0));
    public static final RegistryObject<UmaSkill> ADV_LOWHEALTH_BUFF = SKILLS.register("adv_low_health_buff",
            () -> new LowHealthBuffSkill(new UmaSkill.Builder().type(SkillType.BUFF).actionPoint(900).requiredWisdom(4), 1));
    
    
    public static final RegistryObject<UmaSkill> NUTRITIONAL_SUPPLEMENTS = SKILLS.register("nutritional_supplements",
            () -> new NutritionalSupplementsSkill(new UmaSkill.Builder().type(SkillType.HEAL).actionPoint(600).requiredWisdom(3), 0));
    public static final RegistryObject<UmaSkill> BIG_EATER = SKILLS.register("big_eater",
            () -> new NutritionalSupplementsSkill(new UmaSkill.Builder().type(SkillType.HEAL).actionPoint(1000).requiredWisdom(5), 1));

    public static final RegistryObject<UmaSkill> SERENE = SKILLS.register("serene",
            () -> new SereneSkill(new UmaSkill.Builder().type(SkillType.HEAL).actionPoint(300).requiredWisdom(2)));
    
    public static final RegistryObject<UmaSkill> STEEL_WILL = SKILLS.register("steel_will",
            () -> new SteelWillSkill(new UmaSkill.Builder().type(SkillType.HEAL).actionPoint(1200).requiredWisdom(8)));
}
