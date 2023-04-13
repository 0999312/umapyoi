package net.tracen.umapyoi.container;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tracen.umapyoi.Umapyoi;

public class ContainerRegistry {
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = DeferredRegister
            .create(ForgeRegistries.CONTAINERS, Umapyoi.MODID);

    public static final RegistryObject<MenuType<ThreeGoddessContainer>> THREE_GODDESS = CONTAINER_TYPES
            .register("three_goddess", () -> IForgeMenuType.create(ThreeGoddessContainer::new));

    public static final RegistryObject<MenuType<TrainingFacilityContainer>> TRAINING_FACILITY = CONTAINER_TYPES
            .register("training_facility", () -> IForgeMenuType.create(TrainingFacilityContainer::new));

    public static final RegistryObject<MenuType<SkillLearningMenu>> SKILL_LEARNING_TABLE = CONTAINER_TYPES
            .register("skill_learning_table", () -> new MenuType<>((SkillLearningMenu::new)));

    public static final RegistryObject<MenuType<RetireRegisterMenu>> RETIRE_REGISTER = CONTAINER_TYPES
            .register("retire_register", () -> new MenuType<>((RetireRegisterMenu::new)));
    
    public static final RegistryObject<MenuType<DisassemblyBlockMenu>> DISASSEMBLY_BLOCK = CONTAINER_TYPES
            .register("disassembly_block", () -> new MenuType<>((DisassemblyBlockMenu::new)));
}
