package net.tracen.umapyoi.container;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tracen.umapyoi.Umapyoi;

public class ContainerRegistry {
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = DeferredRegister
            .create(BuiltInRegistries.MENU, Umapyoi.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<ThreeGoddessContainer>> THREE_GODDESS = CONTAINER_TYPES
            .register("three_goddess", () -> IMenuTypeExtension.create(ThreeGoddessContainer::new));

    public static final DeferredHolder<MenuType<?>, MenuType<TrainingFacilityContainer>> TRAINING_FACILITY = CONTAINER_TYPES
            .register("training_facility", () -> IMenuTypeExtension.create(TrainingFacilityContainer::new));

    public static final DeferredHolder<MenuType<?>, MenuType<SkillLearningMenu>> SKILL_LEARNING_TABLE = CONTAINER_TYPES
            .register("skill_learning_table", () -> new MenuType<>((SkillLearningMenu::new), FeatureFlags.DEFAULT_FLAGS));

    public static final DeferredHolder<MenuType<?>, MenuType<RetireRegisterMenu>> RETIRE_REGISTER = CONTAINER_TYPES
            .register("retire_register", () -> new MenuType<>((RetireRegisterMenu::new), FeatureFlags.DEFAULT_FLAGS));
    
    public static final DeferredHolder<MenuType<?>, MenuType<DisassemblyBlockMenu>> DISASSEMBLY_BLOCK = CONTAINER_TYPES
            .register("disassembly_block", () -> new MenuType<>((DisassemblyBlockMenu::new), FeatureFlags.DEFAULT_FLAGS));
    
    public static final DeferredHolder<MenuType<?>, MenuType<UmaSelectMenu>> UMA_SELECT_MENU = CONTAINER_TYPES
            .register("uma_select_menu", () -> new MenuType<>((UmaSelectMenu::new), FeatureFlags.DEFAULT_FLAGS));
}
