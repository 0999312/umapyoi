package net.tracen.umapyoi.client.screen;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.container.ContainerRegistry;

@EventBusSubscriber(bus = Bus.MOD, modid = Umapyoi.MODID, value = Dist.CLIENT)
public class ScreensRegistry {
    @SubscribeEvent
    public static void screenRegistry(final RegisterMenuScreensEvent event) {
            event.register(ContainerRegistry.THREE_GODDESS.get(), ThreeGoddessScreen::new);
            event.register(ContainerRegistry.TRAINING_FACILITY.get(), TrainingFacilityScreen::new);
            event.register(ContainerRegistry.SKILL_LEARNING_TABLE.get(), SkillLearningScreen::new);
            event.register(ContainerRegistry.RETIRE_REGISTER.get(), RetireRegisterScreen::new);
            event.register(ContainerRegistry.DISASSEMBLY_BLOCK.get(), DisassemblyBlockScreen::new);
            event.register(ContainerRegistry.UMA_SELECT_MENU.get(), UmaSelectScreen::new);
    }
}
