package net.tracen.umapyoi.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.command.commands.GiveUmaSoul;
import net.tracen.umapyoi.command.commands.ModifyUmaSoul;
import net.tracen.umapyoi.command.utils.IntWithDefault;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Umapyoi.MODID)
public class CommandRegistry {
    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> ARGUMENT_TYPES = DeferredRegister.create(ForgeRegistries.COMMAND_ARGUMENT_TYPES, Umapyoi.MODID);
    public static final RegistryObject<ArgumentTypeInfo<?, ?>> INT_WITH_DEFAULT =
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
