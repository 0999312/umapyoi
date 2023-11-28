package net.tracen.umapyoi.registry;

import java.util.function.Supplier;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.factors.StatusFactor;
import net.tracen.umapyoi.registry.factors.ExtraStatusFactor;
import net.tracen.umapyoi.registry.factors.SkillFactor;
import net.tracen.umapyoi.registry.factors.UmaFactor;
import net.tracen.umapyoi.registry.factors.UniqueSkillFactor;
import net.tracen.umapyoi.registry.factors.WhiteExtraStatusFactor;
import net.tracen.umapyoi.registry.factors.WhiteStatusFactor;
import net.tracen.umapyoi.utils.UmaStatusUtils.StatusType;

public class UmaFactorRegistry {
    public static final DeferredRegister<UmaFactor> FACTORS = DeferredRegister.create(UmaFactor.REGISTRY_KEY,
            Umapyoi.MODID);

    public static final Supplier<IForgeRegistry<UmaFactor>> REGISTRY = FACTORS.makeRegistry(RegistryBuilder::new);

    public static final RegistryObject<UmaFactor> SPEED_FACTOR = FACTORS.register("speed_factor",
            () -> new StatusFactor(StatusType.SPEED));

    public static final RegistryObject<UmaFactor> STAMINA_FACTOR = FACTORS.register("stamina_factor",
            () -> new StatusFactor(StatusType.STAMINA));

    public static final RegistryObject<UmaFactor> STRENGTH_FACTOR = FACTORS.register("strength_factor",
            () -> new StatusFactor(StatusType.STRENGTH));

    public static final RegistryObject<UmaFactor> GUTS_FACTOR = FACTORS.register("guts_factor",
            () -> new StatusFactor(StatusType.GUTS));

    public static final RegistryObject<UmaFactor> WISDOM_FACTOR = FACTORS.register("wisdom_factor",
            () -> new StatusFactor(StatusType.WISDOM));

    public static final RegistryObject<UmaFactor> SKILL_FACTOR = FACTORS.register("skill_factor", SkillFactor::new);
    public static final RegistryObject<UmaFactor> UNIQUE_SKILL_FACTOR = FACTORS.register("unique_skill_factor",
            UniqueSkillFactor::new);
    
    public static final RegistryObject<UmaFactor> WHITE_SPEED_FACTOR = FACTORS.register("white_speed_factor",
            () -> new WhiteStatusFactor(StatusType.SPEED));

    public static final RegistryObject<UmaFactor> WHITE_STAMINA_FACTOR = FACTORS.register("white_stamina_factor",
            () -> new WhiteStatusFactor(StatusType.STAMINA));

    public static final RegistryObject<UmaFactor> WHITE_STRENGTH_FACTOR = FACTORS.register("white_strength_factor",
            () -> new WhiteStatusFactor(StatusType.STRENGTH));

    public static final RegistryObject<UmaFactor> WHITE_GUTS_FACTOR = FACTORS.register("white_guts_factor",
            () -> new WhiteStatusFactor(StatusType.GUTS));

    public static final RegistryObject<UmaFactor> WHITE_WISDOM_FACTOR = FACTORS.register("white_wisdom_factor",
            () -> new WhiteStatusFactor(StatusType.WISDOM));
    
    public static final RegistryObject<UmaFactor> PHYSIQUE_FACTOR = FACTORS.register("physique_factor",
            () -> new ExtraStatusFactor(0));

    public static final RegistryObject<UmaFactor> TELENT_FACTOR = FACTORS.register("telent_factor",
            () -> new ExtraStatusFactor(1));

    public static final RegistryObject<UmaFactor> MEMORY_FACTOR = FACTORS.register("memory_factor",
            () -> new ExtraStatusFactor(2));

    public static final RegistryObject<UmaFactor> ACTIONS_FACTOR = FACTORS.register("actions_factor",
            () -> new ExtraStatusFactor(3));
    
    public static final RegistryObject<UmaFactor> WHITE_TELENT_FACTOR = FACTORS.register("white_telent_factor",
            () -> new WhiteExtraStatusFactor(1));

    public static final RegistryObject<UmaFactor> WHITE_ACTIONS_FACTOR = FACTORS.register("white_actions_factor",
            () -> new WhiteExtraStatusFactor(3));
}
