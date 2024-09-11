package net.tracen.umapyoi.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.training.ExtraAPSupport;
import net.tracen.umapyoi.registry.training.SkillSupport;
import net.tracen.umapyoi.registry.training.StatusSupport;
import net.tracen.umapyoi.registry.training.TrainingSupport;
import net.tracen.umapyoi.utils.UmaStatusUtils.StatusType;

public class TrainingSupportRegistry {
    public static final DeferredRegister<TrainingSupport> SUPPORTS = DeferredRegister
            .create(TrainingSupport.REGISTRY_KEY, Umapyoi.MODID);

    public static final Registry<TrainingSupport> REGISTRY = SUPPORTS
            .makeRegistry(builder->{
            	builder.defaultKey(ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "empty_support"));
            });

    public static final DeferredHolder<TrainingSupport, TrainingSupport> SPEED_SUPPORT = SUPPORTS.register("speed_support",
            () -> new StatusSupport(StatusType.SPEED));

    public static final DeferredHolder<TrainingSupport, TrainingSupport> STAMINA_SUPPORT = SUPPORTS.register("stamina_support",
            () -> new StatusSupport(StatusType.STAMINA));

    public static final DeferredHolder<TrainingSupport, TrainingSupport> STRENGTH_SUPPORT = SUPPORTS.register("strength_support",
            () -> new StatusSupport(StatusType.STRENGTH));

    public static final DeferredHolder<TrainingSupport, TrainingSupport> GUTS_SUPPORT = SUPPORTS.register("guts_support",
            () -> new StatusSupport(StatusType.GUTS));

    public static final DeferredHolder<TrainingSupport, TrainingSupport> WISDOM_SUPPORT = SUPPORTS.register("wisdom_support",
            () -> new StatusSupport(StatusType.WISDOM));

    public static final DeferredHolder<TrainingSupport, TrainingSupport> SKILL_SUPPORT = SUPPORTS.register("skill_support",
            SkillSupport::new);
    
    public static final DeferredHolder<TrainingSupport, TrainingSupport> AP_SUPPORT = SUPPORTS.register("actionpoint_support",
            () -> new ExtraAPSupport());

}
