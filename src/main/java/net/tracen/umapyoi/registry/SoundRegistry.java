package net.tracen.umapyoi.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tracen.umapyoi.Umapyoi;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Umapyoi.MODID);

    public static final RegistryObject<SoundEvent> GATE_OPEN = SOUNDS.register("gate_open",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Umapyoi.MODID, "gate_open")));

    public static final RegistryObject<SoundEvent> GATE_CLOSE = SOUNDS.register("gate_close",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Umapyoi.MODID, "gate_close")));
}
