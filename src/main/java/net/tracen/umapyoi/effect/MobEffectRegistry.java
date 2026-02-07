package net.tracen.umapyoi.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tracen.umapyoi.Umapyoi;

public class MobEffectRegistry {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS,
            Umapyoi.MODID);

    public static final RegistryObject<MobEffect> PANICKING = EFFECTS.register("panicking", PanickingEffect::new);
    public static final RegistryObject<MobEffect> MOOD_BONUS = EFFECTS.register("mood_bonus", MoodBonus::new);
    public static final RegistryObject<MobEffect> SLOW_METABOLISM = EFFECTS.register("slow_metabolism", SlowMetabolismEffect::new);
    public static final RegistryObject<MobEffect> NIGHT_OWL = EFFECTS.register("night_owl", NightOwlEffect::new);
}
