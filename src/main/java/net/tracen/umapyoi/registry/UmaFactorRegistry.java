package net.tracen.umapyoi.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
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

    public static final Registry<UmaFactor> REGISTRY = FACTORS.makeRegistry(builder->{
    	builder.defaultKey(ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "empty_factor"));
    });

    public static final DeferredHolder<UmaFactor, StatusFactor> SPEED_FACTOR = FACTORS.register("speed_factor",
            () -> new StatusFactor(StatusType.SPEED));

    public static final DeferredHolder<UmaFactor, StatusFactor> STAMINA_FACTOR = FACTORS.register("stamina_factor",
            () -> new StatusFactor(StatusType.STAMINA));

    public static final DeferredHolder<UmaFactor, StatusFactor> STRENGTH_FACTOR = FACTORS.register("strength_factor",
            () -> new StatusFactor(StatusType.STRENGTH));

    public static final DeferredHolder<UmaFactor, StatusFactor> GUTS_FACTOR = FACTORS.register("guts_factor",
            () -> new StatusFactor(StatusType.GUTS));

    public static final DeferredHolder<UmaFactor, StatusFactor> WISDOM_FACTOR = FACTORS.register("wisdom_factor",
            () -> new StatusFactor(StatusType.WISDOM));

    public static final DeferredHolder<UmaFactor, SkillFactor> SKILL_FACTOR = FACTORS.register("skill_factor", SkillFactor::new);
    public static final DeferredHolder<UmaFactor, UniqueSkillFactor> UNIQUE_SKILL_FACTOR = FACTORS.register("unique_skill_factor",
            UniqueSkillFactor::new);
    
    public static final DeferredHolder<UmaFactor, WhiteStatusFactor> WHITE_SPEED_FACTOR = FACTORS.register("white_speed_factor",
            () -> new WhiteStatusFactor(StatusType.SPEED));

    public static final DeferredHolder<UmaFactor, WhiteStatusFactor> WHITE_STAMINA_FACTOR = FACTORS.register("white_stamina_factor",
            () -> new WhiteStatusFactor(StatusType.STAMINA));

    public static final DeferredHolder<UmaFactor, WhiteStatusFactor> WHITE_STRENGTH_FACTOR = FACTORS.register("white_strength_factor",
            () -> new WhiteStatusFactor(StatusType.STRENGTH));

    public static final DeferredHolder<UmaFactor, WhiteStatusFactor> WHITE_GUTS_FACTOR = FACTORS.register("white_guts_factor",
            () -> new WhiteStatusFactor(StatusType.GUTS));

    public static final DeferredHolder<UmaFactor, WhiteStatusFactor> WHITE_WISDOM_FACTOR = FACTORS.register("white_wisdom_factor",
            () -> new WhiteStatusFactor(StatusType.WISDOM));
    
    public static final DeferredHolder<UmaFactor, ExtraStatusFactor> PHYSIQUE_FACTOR = FACTORS.register("physique_factor",
            () -> new ExtraStatusFactor(0));

    public static final DeferredHolder<UmaFactor, ExtraStatusFactor> TELENT_FACTOR = FACTORS.register("telent_factor",
            () -> new ExtraStatusFactor(1));

    public static final DeferredHolder<UmaFactor, ExtraStatusFactor> MEMORY_FACTOR = FACTORS.register("memory_factor",
            () -> new ExtraStatusFactor(2));

    public static final DeferredHolder<UmaFactor, ExtraStatusFactor> ACTIONS_FACTOR = FACTORS.register("actions_factor",
            () -> new ExtraStatusFactor(3));
    
    public static final DeferredHolder<UmaFactor, WhiteExtraStatusFactor> WHITE_TELENT_FACTOR = FACTORS.register("white_telent_factor",
            () -> new WhiteExtraStatusFactor(1));

    public static final DeferredHolder<UmaFactor, WhiteExtraStatusFactor> WHITE_ACTIONS_FACTOR = FACTORS.register("white_actions_factor",
            () -> new WhiteExtraStatusFactor(3));
}
