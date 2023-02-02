package net.tracen.umapyoi.registry;

import java.util.function.Supplier;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.factors.StatusFactor;
import net.tracen.umapyoi.registry.factors.SkillFactor;
import net.tracen.umapyoi.registry.factors.UmaFactor;
import net.tracen.umapyoi.utils.UmaStatusUtils.StatusType;

public class UmaFactorRegistry {
    public static final DeferredRegister<UmaFactor> FACTORS = DeferredRegister.create(UmaFactor.REGISTRY_KEY,
            Umapyoi.MODID);

    public static final Supplier<IForgeRegistry<UmaFactor>> REGISTRY = FACTORS.makeRegistry(UmaFactor.class,
            RegistryBuilder::new);

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
}
