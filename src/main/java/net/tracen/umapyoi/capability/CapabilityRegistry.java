package net.tracen.umapyoi.capability;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tracen.umapyoi.Umapyoi;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mod.EventBusSubscriber()
public class CapabilityRegistry {
    public static final Capability<ILevelTimer> NIGHT_OWL_TIMER = CapabilityManager.get(new CapabilityToken<>(){});

    @SubscribeEvent
    public static void onAttachCapability(AttachCapabilitiesEvent<Level> event) {
        Level level = event.getObject();
        if (level instanceof ServerLevel) {
            event.addCapability(new ResourceLocation(Umapyoi.MODID, "night_owl_timer"),
                    new ICapabilityProvider() {
                        private final NightOwlTimer timer = new NightOwlTimer();
                        @Override
                        public @Nonnull <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                            return cap == CapabilityRegistry.NIGHT_OWL_TIMER ? LazyOptional.of(() -> timer).cast() : LazyOptional.empty();
                        }
                    });
        }
    }
}
