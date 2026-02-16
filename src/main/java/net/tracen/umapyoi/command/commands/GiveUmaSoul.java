package net.tracen.umapyoi.command.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.command.utils.IntWithDefault;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.registry.umadata.Growth;
import net.tracen.umapyoi.registry.umadata.Motivations;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.utils.UmaSoulUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

public class GiveUmaSoul {
    private static List<SubCommand<GiveBuilder>> subCommands;

    private static void simpleRegister(SubCommand<GiveBuilder> sub) {
        if (subCommands == null) subCommands = new ArrayList<>();
        subCommands.add(sub);
    }

    static {
        simpleRegister(new SubCommand<>("ap") {
            @Override
            public ArgumentBuilder<CommandSourceStack, ?> build(Function<ArgumentBuilder<CommandSourceStack, ?>, ArgumentBuilder<CommandSourceStack, ?>> mapper){
                return mapper.apply(Commands.argument("ap", IntegerArgumentType.integer(0)));
            }

            @Override
            public void argumentExtraction(CommandContext<CommandSourceStack> ctx, GiveBuilder obj) {
                obj.setAP(IntegerArgumentType.getInteger(ctx, "ap"));
            }
        });

        simpleRegister(new SubCommand<>("prop") {
            @Override
            public ArgumentBuilder<CommandSourceStack, ?> build(Function<ArgumentBuilder<CommandSourceStack, ?>, ArgumentBuilder<CommandSourceStack, ?>> mapper){
                var retv = mapper.apply(Commands.argument("wisdom", new IntWithDefault(-1)));
                return Commands.argument("speed", new IntWithDefault(-1)).then(
                        Commands.argument("stamina", new IntWithDefault(-1)).then(
                                Commands.argument("power", new IntWithDefault(-1)).then(
                                        Commands.argument("guts", new IntWithDefault(-1)).then(
                                                retv
                                        )
                                )
                        )
                );
            }

            @Override
            public void argumentExtraction(CommandContext<CommandSourceStack> ctx, GiveBuilder obj) {
                obj.setProp(
                        IntWithDefault.getResult(ctx, "speed", null),
                        IntWithDefault.getResult(ctx, "stamina", null),
                        IntWithDefault.getResult(ctx, "power", null),
                        IntWithDefault.getResult(ctx, "guts", null),
                        IntWithDefault.getResult(ctx, "wisdom", null)
                );
            }
        });

        simpleRegister(new SubCommand<>("maxprop") {
            @Override
            public ArgumentBuilder<CommandSourceStack, ?> build(Function<ArgumentBuilder<CommandSourceStack, ?>, ArgumentBuilder<CommandSourceStack, ?>> mapper){
                var retv = mapper.apply(Commands.argument("maxwisdom", new IntWithDefault(-1)));
                return Commands.argument("maxspeed", new IntWithDefault(-1)).then(
                        Commands.argument("maxstamina", new IntWithDefault(-1)).then(
                                Commands.argument("maxpower", new IntWithDefault(-1)).then(
                                        Commands.argument("maxguts", new IntWithDefault(-1)).then(
                                                retv
                                        )
                                )
                        )
                );
            }

            @Override
            public void argumentExtraction(CommandContext<CommandSourceStack> ctx, GiveBuilder obj) {
                obj.setMaxProps(
                        IntWithDefault.getResult(ctx, "maxspeed", null),
                        IntWithDefault.getResult(ctx, "maxstamina", null),
                        IntWithDefault.getResult(ctx, "maxpower", null),
                        IntWithDefault.getResult(ctx, "maxguts", null),
                        IntWithDefault.getResult(ctx, "maxwisdom", null)
                );
            }
        });

        simpleRegister(new SubCommand<>("extra") {
            @Override
            public ArgumentBuilder<CommandSourceStack, ?> build(Function<ArgumentBuilder<CommandSourceStack, ?>, ArgumentBuilder<CommandSourceStack, ?>> mapper){
                var retv = mapper.apply(Commands.argument("maxap", new IntWithDefault(-1)));
                return Commands.argument("physique", new IntWithDefault(-1)).then(
                        Commands.argument("learningtimes", new IntWithDefault(-1)).then(
                                Commands.argument("skillslots", new IntWithDefault(-1)).then(
                                        retv
                                )
                        )
                );
            }

            @Override
            public void argumentExtraction(CommandContext<CommandSourceStack> ctx, GiveBuilder obj) {
                obj.setExtra(
                        IntWithDefault.getResult(ctx, "physique", null),
                        IntWithDefault.getResult(ctx, "learningtimes", null),
                        IntWithDefault.getResult(ctx, "skillslots", null),
                        IntWithDefault.getResult(ctx, "maxap", null)
                );
            }
        });

        simpleRegister(new SubCommand<>("growth") {
            @Override
            public ArgumentBuilder<CommandSourceStack, ?> build(Function<ArgumentBuilder<CommandSourceStack, ?>, ArgumentBuilder<CommandSourceStack, ?>> mapper){
                return mapper.apply(Commands.argument("growth", StringArgumentType.word())
                        .suggests((ctx, builder) -> SharedSuggestionProvider.suggest(
                                Arrays.stream(Growth.values()).sorted().map(Growth::name).map(String::toLowerCase).toList(), builder
                        )));
            }

            @Override
            public void argumentExtraction(CommandContext<CommandSourceStack> ctx, GiveBuilder obj) {
                try {
                    Growth growth = Growth.valueOf(StringArgumentType.getString(ctx, "growth").toUpperCase());
                    obj.setGrowth(growth);
                } catch (IllegalArgumentException ignored) {

                }
            }
        });

        simpleRegister(new SubCommand<>("motivation") {
            @Override
            public ArgumentBuilder<CommandSourceStack, ?> build(Function<ArgumentBuilder<CommandSourceStack, ?>, ArgumentBuilder<CommandSourceStack, ?>> mapper){
                return mapper.apply(Commands.argument("motivation", StringArgumentType.word())
                        .suggests((ctx, builder) -> SharedSuggestionProvider.suggest(
                                Arrays.stream(Motivations.values()).sorted().map(Motivations::name).map(String::toLowerCase).toList(), builder
                        )));
            }

            @Override
            public void argumentExtraction(CommandContext<CommandSourceStack> ctx, GiveBuilder obj) {
                Motivations motiv = Motivations.valueOf(StringArgumentType.getString(ctx, "motivation").toUpperCase());
                obj.setMotivation(motiv);
            }
        });
    }

    private static abstract class SubCommand<T> {
        public final String name;
        public SubCommand(String name) {
            this.name = name;
        }

        public abstract ArgumentBuilder<CommandSourceStack, ?> build(Function<ArgumentBuilder<CommandSourceStack, ?>, ArgumentBuilder<CommandSourceStack, ?>> mapper);

        public abstract void argumentExtraction(CommandContext<CommandSourceStack> ctx, T obj);
    }

    @FunctionalInterface
    public interface ThrowFunction<T, R> {
        R apply(T t) throws CommandSyntaxException;
    }

    private static <T> ArgumentBuilder<CommandSourceStack, ?> recursiveBuilder(ArgumentBuilder<CommandSourceStack, ?> previous, List<SubCommand<T>> subsLeft, ThrowFunction<CommandContext<CommandSourceStack>, T> initializer, ThrowFunction<T, Integer> finalizer) {
        if (subsLeft.isEmpty()) return previous;
        for (int i = 0; i < subsLeft.size(); i++) {
            SubCommand<T> thisRound = subsLeft.get(i);
            Umapyoi.getLogger().debug("Init {} in {}/{}", thisRound.name, i + 1, subsLeft.size());
            int finalI = i;
            var inner = thisRound.build(innerNode -> {
                    List<SubCommand<T>> removes = IntStream.range(0, subsLeft.size()).filter(j -> j != finalI).mapToObj(subsLeft::get).toList();
                    return recursiveBuilder(innerNode, removes, (ctx) -> {
                        T beforeInit = initializer.apply(ctx);
                        thisRound.argumentExtraction(ctx, beforeInit);
                        return beforeInit;
                    }, finalizer).executes(ctx -> {
                        Umapyoi.getLogger().debug("Call Execute");
                        T beforeInit = initializer.apply(ctx);
                        thisRound.argumentExtraction(ctx, beforeInit);
                        return finalizer.apply(beforeInit);
                    });
                }
            );
            var literalNode = Commands.literal(thisRound.name).then(inner);
            previous = previous.then(literalNode);
        }
        return previous;
    }

    public static LiteralArgumentBuilder<CommandSourceStack> registry(LiteralArgumentBuilder<CommandSourceStack> builder) {
        var lastPosFix = Commands.argument("uma", ResourceLocationArgument.id()) // umapyoi give <uma>
                .suggests((ctx, suggestionsBuilder)
                                -> SharedSuggestionProvider.suggestResource(
                                UmapyoiAPI.getUmaDataRegistry(ctx.getSource().getLevel()).keySet(), suggestionsBuilder
                        )
                ).executes(ctx -> new GiveBuilder(ctx).create());
        recursiveBuilder(lastPosFix, subCommands, GiveBuilder::new, GiveBuilder::create);

        return builder.then(Commands.literal("give").requires(src -> src.hasPermission(2)) // /umapyoi give
                        .then(Commands.argument("target", EntityArgument.player())
                                .then(lastPosFix)
                        )
        );
    }

    private static class GiveBuilder {
        public final CommandContext<CommandSourceStack> ctx;
        public final ServerPlayer player;
        public final ResourceLocation umaLocation;
        private Growth growth;
        private Motivations motivation;
        private Integer ap;
        private Integer[] props;
        private Integer[] maxprops;
        private Integer[] extra;
        public GiveBuilder(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
            this.ctx = ctx;
            this.player = EntityArgument.getPlayer(ctx, "target");
            this.umaLocation = ResourceLocationArgument.getId(ctx, "uma");
            this.growth = Growth.UNTRAINED;
            this.motivation = Motivations.NORMAL;
            this.ap = null;
            this.props = null;
            this.maxprops = null;
            this.extra = null;
        }

        public GiveBuilder setGrowth(Growth growth) {
            this.growth = growth;
            return this;
        }

        public GiveBuilder setMotivation(Motivations motivation) {
            this.motivation = motivation;
            return this;
        }

        public GiveBuilder setAP(int ap) {
            this.ap = ap;
            return this;
        }

        public GiveBuilder setProp(Integer... props) {
            this.props = props;
            return this;
        }

        public GiveBuilder setMaxProps(Integer... maxProps) {
            this.maxprops = maxProps;
            return this;
        }

        public GiveBuilder setExtra(Integer... extra) {
            this.extra = extra;
            return this;
        }

        public int create() {
            return givePlayer(this.ctx, this.player, this.umaLocation, this.growth, this.motivation,
                    this.ap, this.props, this.maxprops, this.extra);
        }
    }

    private static int givePlayer(@Nonnull CommandContext<CommandSourceStack> ctx, @Nonnull ServerPlayer player,
                                  @Nonnull ResourceLocation uma, Growth growth, Motivations motivation,
                                  @Nullable Integer ap, @Nullable Integer[] props, @Nullable Integer[] maxprops,
                                  @Nullable Integer[] extras) {
        Level level = ctx.getSource().getLevel();
        UmaData umaData = UmapyoiAPI.getUmaDataRegistry(level).get(uma);
        if (umaData == null) {
            ctx.getSource().sendFailure(Component.translatable("umapyoi.command.parse.unknown.umadata", uma));
            return 0;
        }
        ItemStack umaSoul = UmaSoulUtils.initUmaSoul(ItemRegistry.UMA_SOUL.get().getDefaultInstance(), uma, umaData).copy();

        UmaSoulUtils.setGrowth(umaSoul, growth);
        UmaSoulUtils.setMotivation(umaSoul, motivation);
        UmaSoulUtils.setActionPoint(umaSoul, Optional.ofNullable(ap).orElse(UmaSoulUtils.getMaxActionPoint(umaSoul)));
        int[] originalProperty = UmaSoulUtils.getProperty(umaSoul);
        Optional.ofNullable(props).ifPresent(ps -> IntStream.range(0, 5).filter((i) -> props[i] != null).forEach((i) -> originalProperty[i] = props[i]));
        int[] originalMaxProperty = UmaSoulUtils.getMaxProperty(umaSoul);
        Optional.ofNullable(maxprops).ifPresent(ps -> IntStream.range(0, 5).filter((i) -> maxprops[i] != null).forEach((i) -> originalMaxProperty[i] = maxprops[i]));
        int[] originalExtras = UmaSoulUtils.getExtraProperty(umaSoul);
        Optional.ofNullable(extras).ifPresent(ps -> IntStream.range(0, 4).filter((i) -> extras[i] != null).forEach((i) -> originalExtras[i] = extras[i]));


        if (!player.getInventory().add(umaSoul)) {
            player.drop(umaSoul, false);
        }

        ctx.getSource().sendSuccess(() -> Component.translatable("umapyoi.command.give.success", player.getName(), UmaSoulUtils.getTranslatedUmaName(umaSoul)), true);
        return 1;
    }
}
