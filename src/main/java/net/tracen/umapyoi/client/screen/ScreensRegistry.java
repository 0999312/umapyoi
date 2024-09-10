package net.tracen.umapyoi.client.screen;

import net.minecraft.client.gui.screens.MenuScreens;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.container.ContainerRegistry;

@EventBusSubscriber(bus = Bus.MOD, modid = Umapyoi.MODID, value = Dist.CLIENT)
public class ScreensRegistry {
    @SubscribeEvent
    public static void screenRegistry(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ContainerRegistry.THREE_GODDESS.get(), ThreeGoddessScreen::new);
            MenuScreens.register(ContainerRegistry.TRAINING_FACILITY.get(), TrainingFacilityScreen::new);
            MenuScreens.register(ContainerRegistry.SKILL_LEARNING_TABLE.get(), SkillLearningScreen::new);
            MenuScreens.register(ContainerRegistry.RETIRE_REGISTER.get(), RetireRegisterScreen::new);
            MenuScreens.register(ContainerRegistry.DISASSEMBLY_BLOCK.get(), DisassemblyBlockScreen::new);

            MenuScreens.register(ContainerRegistry.UMA_SELECT_MENU.get(), UmaSelectScreen::new);
        });
    }
}
