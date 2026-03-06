package net.tracen.umapyoi.command.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.umadata.Motivations;
import net.tracen.umapyoi.registry.umadata.UmaDataBasicStatus;
import net.tracen.umapyoi.registry.umadata.UmaDataSkills;
import net.tracen.umapyoi.registry.umadata.UmaDataTraining;
import net.tracen.umapyoi.utils.UmaSoulUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static net.tracen.umapyoi.registry.UmaSkillRegistry.BASIC_PACE;

public class ModifyUmaSoul {
    private static HashMap<String, List<LiteralArgumentBuilder<CommandSourceStack>>> modesMap;

    private static List<LiteralArgumentBuilder<CommandSourceStack>> makeNodeOfModes(String mode) {
        if (modesMap == null) modesMap = new HashMap<>();
        return modesMap.computeIfAbsent(mode, (k) -> List.of(
                Commands.literal("property").then(
                        Commands.argument("type", IntegerArgumentType.integer(0, 4))
                                .then(Commands.argument("value", IntegerArgumentType.integer(-1)).executes(
                                        ctx -> modifyProperty(k, ctx, null)
                                ))
                ).then(
                        Commands.literal("speed").then(Commands.argument("value", IntegerArgumentType.integer(-1)).executes(
                                ctx -> modifyProperty(k, ctx, 0)
                        ))
                ).then(
                        Commands.literal("stamina").then(Commands.argument("value", IntegerArgumentType.integer(-1)).executes(
                                ctx -> modifyProperty(k, ctx, 1)
                        ))
                ).then(
                        Commands.literal("power").then(Commands.argument("value", IntegerArgumentType.integer(-1)).executes(
                                ctx -> modifyProperty(k, ctx, 2)
                        ))
                ).then(
                        Commands.literal("guts").then(Commands.argument("value", IntegerArgumentType.integer(-1)).executes(
                                ctx -> modifyProperty(k, ctx, 3)
                        ))
                ).then(
                        Commands.literal("wisdom").then(Commands.argument("value", IntegerArgumentType.integer(-1)).executes(
                                ctx -> modifyProperty(k, ctx, 4)
                        ))
                ),
                Commands.literal("ap").then(
                        Commands.literal("add").then(Commands.argument("ap", IntegerArgumentType.integer()).executes(ctx -> modifyAP(k, ctx)))
                ).then(
                        Commands.literal("set").then(Commands.argument("ap", IntegerArgumentType.integer(0)).executes(ctx -> setAP(k, ctx)))
                ),
                Commands.literal("extraap").then(
                        Commands.literal("add").then(Commands.argument("ap", IntegerArgumentType.integer()).executes(ctx -> modifyExtraAP(k, ctx)))
                ).then(
                        Commands.literal("set").then(Commands.argument("ap", IntegerArgumentType.integer(0)).executes(ctx -> setExtraAP(k, ctx)))
                ),
                Commands.literal("maxprop").then(
                        Commands.argument("type", IntegerArgumentType.integer(0, 4))
                                .then(Commands.argument("value", IntegerArgumentType.integer(-1)).executes(
                                        ctx -> modifyMaxProperty(k, ctx, null)
                                ))
                ).then(
                        Commands.literal("speed").then(Commands.argument("value", IntegerArgumentType.integer(-1)).executes(
                                ctx -> modifyMaxProperty(k, ctx, 0)
                        ))
                ).then(
                        Commands.literal("stamina").then(Commands.argument("value", IntegerArgumentType.integer(-1)).executes(
                                ctx -> modifyMaxProperty(k, ctx, 1)
                        ))
                ).then(
                        Commands.literal("power").then(Commands.argument("value", IntegerArgumentType.integer(-1)).executes(
                                ctx -> modifyMaxProperty(k, ctx, 2)
                        ))
                ).then(
                        Commands.literal("guts").then(Commands.argument("value", IntegerArgumentType.integer(-1)).executes(
                                ctx -> modifyMaxProperty(k, ctx, 3)
                        ))
                ).then(
                        Commands.literal("wisdom").then(Commands.argument("value", IntegerArgumentType.integer(-1)).executes(
                                ctx -> modifyMaxProperty(k, ctx, 4)
                        ))
                ),
                Commands.literal("physique").then(
                        Commands.literal("add").then(Commands.argument("physique", IntegerArgumentType.integer()).executes(ctx -> modifyPhysique(k, ctx)))
                ).then(
                        Commands.literal("set").then(Commands.argument("physique", IntegerArgumentType.integer()).executes(ctx -> setPhysique(k, ctx)))
                ),
                Commands.literal("learningtimes").then(
                        Commands.literal("add").then(Commands.argument("lt", IntegerArgumentType.integer()).executes(ctx -> modifyLT(k, ctx)))
                ).then(
                        Commands.literal("set").then(Commands.argument("lt", IntegerArgumentType.integer()).executes(ctx -> setLT(k, ctx)))
                ),
                Commands.literal("skillslots").then(
                        Commands.literal("add").then(Commands.argument("slots", IntegerArgumentType.integer()).executes(ctx -> modifySlots(k, ctx)))
                ).then(
                        Commands.literal("set").then(Commands.argument("slots", IntegerArgumentType.integer()).executes(ctx -> setSlots(k, ctx)))
                ),
                Commands.literal("growth").then(
                        Commands.argument("growth", StringArgumentType.word())
                                .suggests((ctx, builder) -> SharedSuggestionProvider.suggest(
                                        List.of("untrained", "trained", "retired"), builder
                                )).executes(ctx -> setGrowth(k, ctx))
                ),
                Commands.literal("motivation").then(
                        Commands.argument("motivation", StringArgumentType.word())
                                .suggests((ctx, builder) -> SharedSuggestionProvider.suggest(
                                        Arrays.stream(Motivations.values()).sorted().map(Motivations::name).map(String::toLowerCase).toList(), builder
                                )).executes(ctx -> setMotiv(k, ctx))
                ),
                Commands.literal("skills").then(
                        Commands.literal("add").then(
                                Commands.argument("id", ResourceLocationArgument.id())
                                        .suggests((ctx, builder) -> SharedSuggestionProvider.suggestResource(
                                                UmaSkillRegistry.REGISTRY.keySet(), builder
                                        )).executes(ctx -> addSkills(k, ctx, false)).then(
                                                Commands.literal("force").executes(ctx -> addSkills(k, ctx, true))
                                        )
                        )
                ).then(
                        Commands.literal("remove").then(
                                Commands.literal("last").executes(ctx -> removeLastSkill(k, ctx))
                        ).then(
                                Commands.literal("all").executes(ctx -> removeAllSkill(k, ctx))
                        ).then(
                                Commands.argument("id", ResourceLocationArgument.id())
                                        .suggests((ctx, builder) -> SharedSuggestionProvider.suggestResource(
                                                UmaSkillRegistry.REGISTRY.keySet(), builder
                                        )).executes(ctx -> removeSpecificSkill(k, ctx))
                        )
                )
        ));
    }

    public static LiteralArgumentBuilder<CommandSourceStack> makeNodeOfMode(String mode) {
        LiteralArgumentBuilder<CommandSourceStack> baseNode = Commands.literal(mode);
        for (LiteralArgumentBuilder<CommandSourceStack> b: makeNodeOfModes(mode)) {
            baseNode = baseNode.then(b);
        }
        return baseNode;
    }

    public static LiteralArgumentBuilder<CommandSourceStack> registry(LiteralArgumentBuilder<CommandSourceStack> builder) {
        LiteralArgumentBuilder<CommandSourceStack> baseNode = Commands.literal("modify").requires(src -> src.hasPermission(2))// /umapyoi modify
                .then(makeNodeOfMode("hand"))
                .then(makeNodeOfMode("equip"));
        RequiredArgumentBuilder<CommandSourceStack, EntitySelector> playerNode = Commands.argument("player", EntityArgument.player())
                .then(makeNodeOfMode("hand"))
                .then(makeNodeOfMode("equip"));
        for (LiteralArgumentBuilder<CommandSourceStack> b: makeNodeOfModes("default")) {
            baseNode = baseNode.then(b);
            playerNode = playerNode.then(b);
        }
        return builder.then(baseNode.then(playerNode));
    }

    public static ItemStack getItemStackByMode(String mode, ServerPlayer player, Consumer<Component> sendFailure, Consumer<String> modeCallback) {
        ItemStack handStack = player.getMainHandItem();
        if (handStack.isEmpty() || !handStack.is(ItemRegistry.UMA_SOUL.get())) {
            handStack = player.getOffhandItem();
        }
        if (mode.equals("hand")) {
            if (handStack.isEmpty()) {
                sendFailure.accept(Component.translatable("umapyoi.command.modify.emptyhand"));
                return null;
            }
            if (!handStack.is(ItemRegistry.UMA_SOUL.get())) {
                sendFailure.accept(Component.translatable("umapyoi.command.modify.notumasoul"));
                return null;
            }
            modeCallback.accept("hand");
            return handStack;
        }
        ItemStack wearStack = UmapyoiAPI.getUmaSoul(player);
        if (mode.equals("equip")) {
            if (wearStack.isEmpty()) {
                sendFailure.accept(Component.translatable("umapyoi.command.modify.emptyequipment"));
                return null;
            }
            modeCallback.accept("equipped");
            return wearStack;
        }
        if (handStack.isEmpty() && wearStack.isEmpty()) {
            sendFailure.accept(Component.translatable("umapyoi.command.modify.emptytarget"));
            return null;
        }
        if (handStack.is(ItemRegistry.UMA_SOUL.get())) {
            modeCallback.accept("hand");
            return handStack;
        } else {
            if (!wearStack.isEmpty()) {
                modeCallback.accept("equipped");
                return wearStack;
            }
            sendFailure.accept(Component.translatable("umapyoi.command.modify.novalidsoulexist"));
            return null;
        }
    }

    public static int modifyProperty(String mode, CommandContext<CommandSourceStack> ctx, Integer type) throws CommandSyntaxException {
        ServerPlayer player;
        try {
            player = EntityArgument.getPlayer(ctx, "player");
        } catch (IllegalArgumentException e) {
            player = ctx.getSource().getPlayer();
        }
        Component playerName = player.getName();
        AtomicReference<String> typeString = new AtomicReference<>();
        ItemStack soul = getItemStackByMode(mode, player, ctx.getSource()::sendFailure, typeString::set);
        if (soul == null) return 0;

        int finalType = type == null ? IntegerArgumentType.getInteger(ctx, "type") : type;
        int value = IntegerArgumentType.getInteger(ctx, "value");
        soul.update(DataComponentsTypeRegistry.UMADATA_BASIC_STATUS, new UmaDataBasicStatus(1, 1, 1, 1, 1), t -> new UmaDataBasicStatus(
                finalType == 0 ? value : t.speed(),
                finalType == 1 ? value : t.stamina(),
                finalType == 2 ? value : t.strength(),
                finalType == 3 ? value : t.guts(),
                finalType == 4 ? value : t.wisdom()
        ));

        player.getInventory().setChanged();
        player.inventoryMenu.broadcastChanges();

        ctx.getSource().sendSuccess(() -> Component.translatable("umapyoi.command.modify.success", playerName, Component.translatable("umapyoi.command.part." + typeString.get())), true);
        return 1;
    }

    public static int modifyMaxProperty(String mode, CommandContext<CommandSourceStack> ctx, Integer type) throws CommandSyntaxException {
        ServerPlayer player;
        try {
            player = EntityArgument.getPlayer(ctx, "player");
        } catch (IllegalArgumentException e) {
            player = ctx.getSource().getPlayer();
        }
        Component playerName = player.getName();
        AtomicReference<String> typeString = new AtomicReference<>();
        ItemStack soul = getItemStackByMode(mode, player, ctx.getSource()::sendFailure, typeString::set);
        if (soul == null) return 0;

        int finalType = type == null ? IntegerArgumentType.getInteger(ctx, "type") : type;
        int value = IntegerArgumentType.getInteger(ctx, "value");
        soul.update(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS, new UmaDataBasicStatus(1, 1, 1, 1, 1), t -> new UmaDataBasicStatus(
                finalType == 0 ? value : t.speed(),
                finalType == 1 ? value : t.stamina(),
                finalType == 2 ? value : t.strength(),
                finalType == 3 ? value : t.guts(),
                finalType == 4 ? value : t.wisdom()
        ));

        player.getInventory().setChanged();
        player.inventoryMenu.broadcastChanges();

        ctx.getSource().sendSuccess(() -> Component.translatable("umapyoi.command.modify.success", playerName, Component.translatable("umapyoi.command.part." + typeString.get())), true);
        return 1;
    }

    public static int modifyAP(String mode, CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player;
        try {
            player = EntityArgument.getPlayer(ctx, "player");
        } catch (IllegalArgumentException e) {
            player = ctx.getSource().getPlayer();
        }
        Component playerName = player.getName();
        AtomicReference<String> typeString = new AtomicReference<>();
        ItemStack soul = getItemStackByMode(mode, player, ctx.getSource()::sendFailure, typeString::set);
        if (soul == null) return 0;

        int ap = IntegerArgumentType.getInteger(ctx, "ap");
        UmaSoulUtils.addActionPoint(soul, ap);

        player.getInventory().setChanged();
        player.inventoryMenu.broadcastChanges();

        ctx.getSource().sendSuccess(() -> Component.translatable("umapyoi.command.modify.success", playerName, Component.translatable("umapyoi.command.part." + typeString.get())), true);
        return 1;
    }

    public static int setAP(String mode, CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player;
        try {
            player = EntityArgument.getPlayer(ctx, "player");
        } catch (IllegalArgumentException e) {
            player = ctx.getSource().getPlayer();
        }
        Component playerName = player.getName();
        AtomicReference<String> typeString = new AtomicReference<>();
        ItemStack soul = getItemStackByMode(mode, player, ctx.getSource()::sendFailure, typeString::set);
        if (soul == null) return 0;

        int ap = IntegerArgumentType.getInteger(ctx, "ap");
        UmaSoulUtils.setActionPoint(soul, ap);

        player.getInventory().setChanged();
        player.inventoryMenu.broadcastChanges();

        ctx.getSource().sendSuccess(() -> Component.translatable("umapyoi.command.modify.success", playerName, Component.translatable("umapyoi.command.part." + typeString.get())), true);
        return 1;
    }

    public static int modifyExtraAP(String mode, CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player;
        try {
            player = EntityArgument.getPlayer(ctx, "player");
        } catch (IllegalArgumentException e) {
            player = ctx.getSource().getPlayer();
        }
        Component playerName = player.getName();
        AtomicReference<String> typeString = new AtomicReference<>();
        ItemStack soul = getItemStackByMode(mode, player, ctx.getSource()::sendFailure, typeString::set);
        if (soul == null) return 0;

        int ap = IntegerArgumentType.getInteger(ctx, "ap");
        UmaSoulUtils.setExtraActionPoint(soul, UmaSoulUtils.getMaxActionPoint(soul) + ap);

        player.getInventory().setChanged();
        player.inventoryMenu.broadcastChanges();

        ctx.getSource().sendSuccess(() -> Component.translatable("umapyoi.command.modify.success", playerName, Component.translatable("umapyoi.command.part." + typeString.get())), true);
        return 1;
    }

    public static int setExtraAP(String mode, CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player;
        try {
            player = EntityArgument.getPlayer(ctx, "player");
        } catch (IllegalArgumentException e) {
            player = ctx.getSource().getPlayer();
        }
        Component playerName = player.getName();
        AtomicReference<String> typeString = new AtomicReference<>();
        ItemStack soul = getItemStackByMode(mode, player, ctx.getSource()::sendFailure, typeString::set);
        if (soul == null) return 0;

        int ap = IntegerArgumentType.getInteger(ctx, "ap");
        UmaSoulUtils.setExtraActionPoint(soul, ap);

        player.getInventory().setChanged();
        player.inventoryMenu.broadcastChanges();

        ctx.getSource().sendSuccess(() -> Component.translatable("umapyoi.command.modify.success", playerName, Component.translatable("umapyoi.command.part." + typeString.get())), true);
        return 1;
    }

    public static int modifyPhysique(String mode, CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player;
        try {
            player = EntityArgument.getPlayer(ctx, "player");
        } catch (IllegalArgumentException e) {
            player = ctx.getSource().getPlayer();
        }
        Component playerName = player.getName();
        AtomicReference<String> typeString = new AtomicReference<>();
        ItemStack soul = getItemStackByMode(mode, player, ctx.getSource()::sendFailure, typeString::set);
        if (soul == null) return 0;

        int physique = IntegerArgumentType.getInteger(ctx, "physique");
        UmaSoulUtils.setPhysique(soul, UmaSoulUtils.getPhysique(soul) + physique);

        player.getInventory().setChanged();
        player.inventoryMenu.broadcastChanges();

        ctx.getSource().sendSuccess(() -> Component.translatable("umapyoi.command.modify.success", playerName, Component.translatable("umapyoi.command.part." + typeString.get())), true);
        return 1;
    }

    public static int setPhysique(String mode, CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player;
        try {
            player = EntityArgument.getPlayer(ctx, "player");
        } catch (IllegalArgumentException e) {
            player = ctx.getSource().getPlayer();
        }
        Component playerName = player.getName();
        AtomicReference<String> typeString = new AtomicReference<>();
        ItemStack soul = getItemStackByMode(mode, player, ctx.getSource()::sendFailure, typeString::set);
        if (soul == null) return 0;

        int physique = IntegerArgumentType.getInteger(ctx, "physique");
        UmaSoulUtils.setPhysique(soul, physique);

        player.getInventory().setChanged();
        player.inventoryMenu.broadcastChanges();

        ctx.getSource().sendSuccess(() -> Component.translatable("umapyoi.command.modify.success", playerName, Component.translatable("umapyoi.command.part." + typeString.get())), true);
        return 1;
    }

    public static int modifyLT(String mode, CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player;
        try {
            player = EntityArgument.getPlayer(ctx, "player");
        } catch (IllegalArgumentException e) {
            player = ctx.getSource().getPlayer();
        }
        Component playerName = player.getName();
        AtomicReference<String> typeString = new AtomicReference<>();
        ItemStack soul = getItemStackByMode(mode, player, ctx.getSource()::sendFailure, typeString::set);
        if (soul == null) return 0;

        int lt = IntegerArgumentType.getInteger(ctx, "lt");
        UmaSoulUtils.setLearningTimes(soul, UmaSoulUtils.getLearningTimes(soul) + lt);

        player.getInventory().setChanged();
        player.inventoryMenu.broadcastChanges();

        ctx.getSource().sendSuccess(() -> Component.translatable("umapyoi.command.modify.success", playerName, Component.translatable("umapyoi.command.part." + typeString.get())), true);
        return 1;
    }

    public static int setLT(String mode, CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player;
        try {
            player = EntityArgument.getPlayer(ctx, "player");
        } catch (IllegalArgumentException e) {
            player = ctx.getSource().getPlayer();
        }
        Component playerName = player.getName();
        AtomicReference<String> typeString = new AtomicReference<>();
        ItemStack soul = getItemStackByMode(mode, player, ctx.getSource()::sendFailure, typeString::set);
        if (soul == null) return 0;

        int lt = IntegerArgumentType.getInteger(ctx, "lt");
        UmaSoulUtils.setLearningTimes(soul, lt);

        player.getInventory().setChanged();
        player.inventoryMenu.broadcastChanges();

        ctx.getSource().sendSuccess(() -> Component.translatable("umapyoi.command.modify.success", playerName, Component.translatable("umapyoi.command.part." + typeString.get())), true);
        return 1;
    }

    public static int modifySlots(String mode, CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player;
        try {
            player = EntityArgument.getPlayer(ctx, "player");
        } catch (IllegalArgumentException e) {
            player = ctx.getSource().getPlayer();
        }
        Component playerName = player.getName();
        AtomicReference<String> typeString = new AtomicReference<>();
        ItemStack soul = getItemStackByMode(mode, player, ctx.getSource()::sendFailure, typeString::set);
        if (soul == null) return 0;

        int slots = IntegerArgumentType.getInteger(ctx, "slots");
        UmaSoulUtils.setSkillSlots(soul, UmaSoulUtils.getSkillSlots(soul) + slots);

        player.getInventory().setChanged();
        player.inventoryMenu.broadcastChanges();

        ctx.getSource().sendSuccess(() -> Component.translatable("umapyoi.command.modify.success", playerName, Component.translatable("umapyoi.command.part." + typeString.get())), true);
        return 1;
    }

    public static int setSlots(String mode, CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player;
        try {
            player = EntityArgument.getPlayer(ctx, "player");
        } catch (IllegalArgumentException e) {
            player = ctx.getSource().getPlayer();
        }
        Component playerName = player.getName();
        AtomicReference<String> typeString = new AtomicReference<>();
        ItemStack soul = getItemStackByMode(mode, player, ctx.getSource()::sendFailure, typeString::set);
        if (soul == null) return 0;

        int slots = IntegerArgumentType.getInteger(ctx, "slots");
        UmaSoulUtils.setSkillSlots(soul, slots);

        player.getInventory().setChanged();
        player.inventoryMenu.broadcastChanges();

        ctx.getSource().sendSuccess(() -> Component.translatable("umapyoi.command.modify.success", playerName, Component.translatable("umapyoi.command.part." + typeString.get())), true);
        return 1;
    }

    public static int setGrowth(String mode, CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player;
        try {
            player = EntityArgument.getPlayer(ctx, "player");
        } catch (IllegalArgumentException e) {
            player = ctx.getSource().getPlayer();
        }
        Component playerName = player.getName();
        AtomicReference<String> typeString = new AtomicReference<>();
        ItemStack soul = getItemStackByMode(mode, player, ctx.getSource()::sendFailure, typeString::set);
        if (soul == null) return 0;

        String growth = StringArgumentType.getString(ctx, "growth");
        switch (growth.toUpperCase()) {
            case "UNTRAINED", "TRAINED":
                soul.update(DataComponentsTypeRegistry.UMADATA_TRAINING, new UmaDataTraining(1, 6, false), t -> new UmaDataTraining(t.physique(), t.talent(), growth.equalsIgnoreCase("TRAINED")));
                break;
            case "RETIRED":
                soul.remove(DataComponentsTypeRegistry.UMADATA_TRAINING);
                break;
            default:
                ctx.getSource().sendFailure(Component.translatable("umapyoi.command.parse.unknown.growth", growth));
                return 0;
        }

        player.getInventory().setChanged();
        player.inventoryMenu.broadcastChanges();

        ctx.getSource().sendSuccess(() -> Component.translatable("umapyoi.command.modify.success", playerName, Component.translatable("umapyoi.command.part." + typeString.get())), true);
        return 1;
    }

    public static int setMotiv(String mode, CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player;
        try {
            player = EntityArgument.getPlayer(ctx, "player");
        } catch (IllegalArgumentException e) {
            player = ctx.getSource().getPlayer();
        }
        Component playerName = player.getName();
        AtomicReference<String> typeString = new AtomicReference<>();
        ItemStack soul = getItemStackByMode(mode, player, ctx.getSource()::sendFailure, typeString::set);
        if (soul == null) return 0;

        String motiv = StringArgumentType.getString(ctx, "motivation");
        try {
            Motivations motivEnum = Motivations.valueOf(motiv.toUpperCase());
            UmaSoulUtils.setMotivation(soul, motivEnum);
        } catch (IllegalArgumentException ignore) {
            ctx.getSource().sendFailure(Component.translatable("umapyoi.command.parse.unknown.motivation", motiv));
            return 0;
        }

        player.getInventory().setChanged();
        player.inventoryMenu.broadcastChanges();

        ctx.getSource().sendSuccess(() -> Component.translatable("umapyoi.command.modify.success", playerName, Component.translatable("umapyoi.command.part." + typeString.get())), true);
        return 1;
    }

    public static int addSkills(String mode, CommandContext<CommandSourceStack> ctx, boolean force) throws CommandSyntaxException {
        ServerPlayer player;
        try {
            player = EntityArgument.getPlayer(ctx, "player");
        } catch (IllegalArgumentException e) {
            player = ctx.getSource().getPlayer();
        }
        Component playerName = player.getName();
        AtomicReference<String> typeString = new AtomicReference<>();
        ItemStack soul = getItemStackByMode(mode, player, ctx.getSource()::sendFailure, typeString::set);
        if (soul == null) return 0;

        if (!(force || UmaSoulUtils.hasEmptySkillSlot(soul))) {
            ctx.getSource().sendFailure(Component.translatable("umapyoi.skill.slot_needed"));
            return 0;
        }
        ResourceLocation rl = ResourceLocationArgument.getId(ctx, "id");
        if (!UmaSkillRegistry.REGISTRY.containsKey(rl)) {
            ctx.getSource().sendFailure(Component.translatable("umapyoi.command.parse.unknown.skill", rl));
            return 0;
        }
        UmaSoulUtils.addSkill(soul, rl);
        player.getInventory().setChanged();
        player.inventoryMenu.broadcastChanges();

        ctx.getSource().sendSuccess(() -> Component.translatable("umapyoi.command.modify.success", playerName, Component.translatable("umapyoi.command.part." + typeString.get())), true);
        return 1;
    }

    public static int removeSpecificSkill(String mode, CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player;
        try {
            player = EntityArgument.getPlayer(ctx, "player");
        } catch (IllegalArgumentException e) {
            player = ctx.getSource().getPlayer();
        }
        Component playerName = player.getName();
        AtomicReference<String> typeString = new AtomicReference<>();
        ItemStack soul = getItemStackByMode(mode, player, ctx.getSource()::sendFailure, typeString::set);
        if (soul == null) return 0;

        ResourceLocation rl = ResourceLocationArgument.getId(ctx, "id");
        if (!UmaSkillRegistry.REGISTRY.containsKey(rl)) {
            ctx.getSource().sendFailure(Component.translatable("umapyoi.command.parse.unknown.skill", rl));
            return 0;
        }
        if (!UmaSoulUtils.hasSkill(soul, rl)) {
            ctx.getSource().sendFailure(Component.translatable("umapyoi.command.skill.remove.notexist", rl));
            return 0;
        }
        ArrayList<ResourceLocation> lTag = new ArrayList<>();
        UmaSoulUtils.getSkills(soul).stream().filter(tag -> !tag.equals(rl)).forEach(lTag::add);
        if (lTag.isEmpty()) {
            lTag.add(BASIC_PACE.getId());
        }
        soul.update(DataComponentsTypeRegistry.UMADATA_SKILLS, UmaDataSkills.DEFAULT, t -> new UmaDataSkills(t.skillSlot(), t.selectedSkill(), lTag));

        if (UmaSoulUtils.getSelectedSkillIndex(soul) >= UmaSoulUtils.getSkills(soul).size()) {
            UmaSoulUtils.setSelectedSkill(soul, UmaSoulUtils.getSkills(soul).size() - 1);
        }
        player.getInventory().setChanged();
        player.inventoryMenu.broadcastChanges();

        ctx.getSource().sendSuccess(() -> Component.translatable("umapyoi.command.modify.success", playerName, Component.translatable("umapyoi.command.part." + typeString.get())), true);
        return 1;
    }

    public static int removeLastSkill(String mode, CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player;
        try {
            player = EntityArgument.getPlayer(ctx, "player");
        } catch (IllegalArgumentException e) {
            player = ctx.getSource().getPlayer();
        }
        Component playerName = player.getName();
        AtomicReference<String> typeString = new AtomicReference<>();
        ItemStack soul = getItemStackByMode(mode, player, ctx.getSource()::sendFailure, typeString::set);
        if (soul == null) return 0;

        List<ResourceLocation> ltag = new ArrayList<>(UmaSoulUtils.getSkills(soul));
        ltag.remove(UmaSoulUtils.getSkills(soul).size() - 1);
        if (ltag.isEmpty()) {
            ltag.add(BASIC_PACE.getId());
        }

        soul.update(DataComponentsTypeRegistry.UMADATA_SKILLS, UmaDataSkills.DEFAULT, t -> new UmaDataSkills(t.skillSlot(), t.selectedSkill(), ltag));

        if (UmaSoulUtils.getSelectedSkillIndex(soul) >= UmaSoulUtils.getSkills(soul).size()) {
            UmaSoulUtils.setSelectedSkill(soul, UmaSoulUtils.getSkills(soul).size() - 1);
        }
        player.getInventory().setChanged();
        player.inventoryMenu.broadcastChanges();

        ctx.getSource().sendSuccess(() -> Component.translatable("umapyoi.command.modify.success", playerName, Component.translatable("umapyoi.command.part." + typeString.get())), true);
        return 1;
    }

    public static int removeAllSkill(String mode, CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player;
        try {
            player = EntityArgument.getPlayer(ctx, "player");
        } catch (IllegalArgumentException e) {
            player = ctx.getSource().getPlayer();
        }
        Component playerName = player.getName();
        AtomicReference<String> typeString = new AtomicReference<>();
        ItemStack soul = getItemStackByMode(mode, player, ctx.getSource()::sendFailure, typeString::set);
        if (soul == null) return 0;

        soul.update(DataComponentsTypeRegistry.UMADATA_SKILLS, UmaDataSkills.DEFAULT, t -> new UmaDataSkills(t.skillSlot(), 0, List.of(BASIC_PACE.getId())));

        player.getInventory().setChanged();
        player.inventoryMenu.broadcastChanges();
        ctx.getSource().sendSuccess(() -> Component.translatable("umapyoi.command.modify.success", playerName, Component.translatable("umapyoi.command.part." + typeString.get())), true);
        return 1;
    }
}
