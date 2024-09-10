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
}
