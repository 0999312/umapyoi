package net.tracen.umapyoi.command.utils;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.util.Either;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.FriendlyByteBuf;
import net.tracen.umapyoi.Umapyoi;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;

public class IntWithDefault implements ArgumentType<Either<Integer, String>> {
    private static final Collection<String> EXAMPLES = Arrays.asList("default", "0", "123", "-123");

    private final int minimum;
    private final int maximum;
    private final String defaultStr;

    public IntWithDefault(final int minimum, final int maximum, final String defaultStr) {
        this.minimum = minimum;
        this.maximum = maximum;
        this.defaultStr = defaultStr;
    }

    public IntWithDefault() {
        this(Integer.MIN_VALUE);
    }

    public IntWithDefault(final int min) {
        this(min, Integer.MAX_VALUE);
    }

    public IntWithDefault(final int min, final int max) {
        this(min, max, "default");
    }

    @Override
    public Either<Integer, String> parse(final StringReader reader) throws CommandSyntaxException {
        final int start = reader.getCursor();
        String readedString = reader.readString();
        if (readedString.equals(this.defaultStr)) {
            return Either.right(this.defaultStr);
        }
        reader.setCursor(start);
        int result;
        try {
            result = reader.readInt();
        } catch (CommandSyntaxException e) {
            Umapyoi.getLogger().debug(e.getType().toString());
            Umapyoi.getLogger().debug(e.getType().getClass().toString());
            if (e.getType().equals(CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerExpectedInt())) {
                reader.setCursor(start);
                throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerExpectedSymbol().createWithContext(reader, this.defaultStr + "/numbers");
            }
            throw e;
        }
        if (result < minimum) {
            reader.setCursor(start);
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.integerTooLow().createWithContext(reader, result, minimum);
        }
        if (result > maximum) {
            reader.setCursor(start);
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.integerTooHigh().createWithContext(reader, result, maximum);
        }
        return Either.left(result);
    }

    public static Integer getResult(CommandContext<?> ctx, String name, @Nullable Integer defaultValue) {
        Either<Integer, String> result = ctx.getArgument(name, Either.class);
        AtomicReference<Integer> ret = new AtomicReference<>();
        result.ifLeft(ret::set).ifRight((j) -> ret.set(defaultValue));
        return ret.get();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public static class Template implements ArgumentTypeInfo.Template<IntWithDefault> {
        private int min;
        private int max;
        private String def;

        public Template(int min, int max, String def) {
            this.min = min;
            this.max = max;
            this.def = def;
        }

        @Override
        public IntWithDefault instantiate(CommandBuildContext pContext) {
            return new IntWithDefault(min, max, def);
        }

        @Override
        public ArgumentTypeInfo<IntWithDefault, ?> type() {
            return IntWithDefaultArgumentInfo.INSTANCE;
        }
    }

    public static class IntWithDefaultArgumentInfo implements ArgumentTypeInfo<IntWithDefault, IntWithDefault.Template> {
        public static IntWithDefaultArgumentInfo INSTANCE = new IntWithDefaultArgumentInfo();
        @Override
        public void serializeToNetwork(IntWithDefault.Template pTemplate, FriendlyByteBuf pBuffer) {
            pBuffer.writeInt(pTemplate.min);
            pBuffer.writeInt(pTemplate.max);
            pBuffer.writeUtf(pTemplate.def);
        }

        @Override
        public IntWithDefault.Template deserializeFromNetwork(FriendlyByteBuf pBuffer) {
            int min = pBuffer.readInt();
            int max = pBuffer.readInt();
            String def = pBuffer.readUtf();
            return new IntWithDefault.Template(min, max, def);
        }

        @Override
        public void serializeToJson(IntWithDefault.Template pTemplate, JsonObject pJson) {
            pJson.addProperty("min", pTemplate.min);
            pJson.addProperty("max", pTemplate.max);
            pJson.addProperty("default", pTemplate.def);
        }

        @Override
        public IntWithDefault.Template unpack(IntWithDefault pArgument) {
            return new IntWithDefault.Template(pArgument.minimum, pArgument.maximum, pArgument.defaultStr);
        }
    }
}