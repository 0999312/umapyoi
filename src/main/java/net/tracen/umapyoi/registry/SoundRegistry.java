package net.tracen.umapyoi.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tracen.umapyoi.Umapyoi;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(Registries.SOUND_EVENT, Umapyoi.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> GATE_OPEN = SOUNDS.register("gate_open",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "gate_open")));

    public static final DeferredHolder<SoundEvent, SoundEvent> GATE_CLOSE = SOUNDS.register("gate_close",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "gate_close")));
}
