package net.tracen.umapyoi.client.screen;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.container.ContainerRegistry;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Umapyoi.MODID, value = Dist.CLIENT)
public class ScreensRegistry {
    @SubscribeEvent
    public static void screenRegistry(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ContainerRegistry.THREE_GODDESS.get(), ThreeGoddessScreen::new);
            MenuScreens.register(ContainerRegistry.TRAINING_FACILITY.get(), TrainingFacilityScreen::new);
            MenuScreens.register(ContainerRegistry.SKILL_LEARNING_TABLE.get(), SkillLearningScreen::new);
            MenuScreens.register(ContainerRegistry.RETIRE_REGISTER.get(), RetireRegisterScreen::new);
        });
    }
}
