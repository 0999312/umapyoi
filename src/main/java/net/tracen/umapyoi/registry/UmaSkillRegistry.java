package net.tracen.umapyoi.registry;

import java.util.function.Supplier;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.skills.HealSkill;
import net.tracen.umapyoi.registry.skills.SkillType;
import net.tracen.umapyoi.registry.skills.SpeedSkill;
import net.tracen.umapyoi.registry.skills.UmaSkill;

public class UmaSkillRegistry {
    public static final DeferredRegister<UmaSkill> SKILLS = DeferredRegister.create(UmaSkill.REGISTRY_KEY,
            Umapyoi.MODID);

    public static final Supplier<IForgeRegistry<UmaSkill>> REGISTRY = SKILLS.makeRegistry(UmaSkill.class,
            RegistryBuilder::new);

    public static final RegistryObject<UmaSkill> BASIC_PACE = SKILLS.register("basic_pace",
            () -> new SpeedSkill(new UmaSkill.Builder().type(SkillType.BUFF), 0, 400));
    
    public static final RegistryObject<UmaSkill> LAST_LEG = SKILLS.register("last_leg",
            () -> new SpeedSkill(new UmaSkill.Builder().type(SkillType.BUFF).actionPoint(400).requiredWisdom(2), 1, 200));
    public static final RegistryObject<UmaSkill> HEART_AND_SOUL = SKILLS.register("heart_and_soul",
            () -> new SpeedSkill(new UmaSkill.Builder().type(SkillType.BUFF).actionPoint(600).requiredWisdom(4), 2, 200));
    
    public static final RegistryObject<UmaSkill> DEEP_BREATHS = SKILLS.register("deep_breaths",
            () -> new HealSkill(new UmaSkill.Builder().type(SkillType.HEAL).actionPoint(400).requiredWisdom(2), 0));
    public static final RegistryObject<UmaSkill> COOLDOWN = SKILLS.register("cooldown",
            () -> new HealSkill(new UmaSkill.Builder().type(SkillType.HEAL).actionPoint(600).requiredWisdom(4), 1));
}
