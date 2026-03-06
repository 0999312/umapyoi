package net.tracen.umapyoi.effect;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tracen.umapyoi.Umapyoi;

public class MobEffectRegistry {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT,
            Umapyoi.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> PANICKING = EFFECTS.register("panicking", PanickingEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> MOOD_BONUS = EFFECTS.register("mood_bonus", MoodBonus::new);
    public static final DeferredHolder<MobEffect, MobEffect> SLOW_METABOLISM = EFFECTS.register("slow_metabolism", SlowMetabolismEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> NIGHT_OWL = EFFECTS.register("night_owl", NightOwlEffect::new);
}
