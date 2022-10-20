package net.tracen.umapyoi.registry;

import java.util.function.Supplier;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.skills.TestSkill;
import net.tracen.umapyoi.registry.skills.UmaSkill;

public class UmaSkillRegistry {
    public static final DeferredRegister<UmaSkill> SKILLS = DeferredRegister.create(UmaSkill.REGISTRY_KEY,
            Umapyoi.MODID);

    public static final Supplier<IForgeRegistry<UmaSkill>> REGISTRY = SKILLS.makeRegistry(UmaSkill.class,
            RegistryBuilder::new);

    public static final RegistryObject<UmaSkill> TEST_1 = SKILLS.register("test_1",
            () -> new UmaSkill(new UmaSkill.Builder()));
    public static final RegistryObject<UmaSkill> TEST_2 = SKILLS.register("test_2",
            () -> new TestSkill(new UmaSkill.Builder()));
}
