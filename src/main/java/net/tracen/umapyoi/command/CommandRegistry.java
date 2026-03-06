package net.tracen.umapyoi.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.command.commands.GiveUmaSoul;
import net.tracen.umapyoi.command.commands.ModifyUmaSoul;
import net.tracen.umapyoi.command.utils.IntWithDefault;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = Umapyoi.MODID)
public class CommandRegistry {
    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> ARGUMENT_TYPES = DeferredRegister.create(Registries.COMMAND_ARGUMENT_TYPE, Umapyoi.MODID);
    public static final DeferredHolder<ArgumentTypeInfo<?, ?>, IntWithDefault.IntWithDefaultArgumentInfo> INT_WITH_DEFAULT =
            ARGUMENT_TYPES.register("int_with_default", () -> ArgumentTypeInfos.registerByClass(IntWithDefault.class, IntWithDefault.IntWithDefaultArgumentInfo.INSTANCE));

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        //CommandBuildContext ctx = event.getBuildContext();
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        LiteralArgumentBuilder<CommandSourceStack> rootBuilder = Commands.literal("umapyoi");

        rootBuilder = GiveUmaSoul.registry(rootBuilder);
        ModifyUmaSoul.registry(rootBuilder);

        dispatcher.register(rootBuilder);
    }
}
