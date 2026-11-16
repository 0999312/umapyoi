package net.tracen.umapyoi.recipe;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.tracen.umapyoi.item.ItemRegistry;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public record CostumeRecipeSerializer<T extends Recipe<?>, U extends T> (RecipeSerializer<T> compose,
                                                                         BiFunction<T, @Nullable ResourceLocation, U> converter) implements RecipeSerializer<U> {
    private static final MapCodec<Optional<ResourceLocation>> CODECS =
            ResourceLocation.CODEC.optionalFieldOf("cosmetic");

    @Override
    public MapCodec<U> codec() {
        return new MapCodec<>() {
            @Override
            public <O> Stream<O> keys(DynamicOps<O> ops) {
                return Stream.concat(compose.codec().keys(ops), CODECS.keys(ops));
            }

            @Override
            public <O> DataResult<U> decode(DynamicOps<O> ops, MapLike<O> input) {
                MapLike<O> patchedInput = input;
                if (input.get("result") == null) {
                    DataResult<O> defaultResult = ItemStack.STRICT_CODEC.encodeStart(ops, ItemRegistry.UMA_COSTUME.get().getDefaultInstance());
                    if (defaultResult.error().isPresent()) {
                        return DataResult.error(() -> "Failed to encode: " + defaultResult.error().get().message());
                    }
                    O resultKey = ops.createString("result");
                    O resultVal = defaultResult.getOrThrow();
                    patchedInput = new MapLike<>() {
                        @Override
                        public @Nullable O get(O key) {
                            return key.equals(resultKey) ? resultVal : input.get(key);
                        }

                        @Override
                        public @Nullable O get(String key) {
                            return "result".equals(key) ? resultVal : input.get(key);
                        }

                        @Override
                        public Stream<Pair<O, O>> entries() {
                            return Stream.concat(Stream.of(Pair.of(resultKey, resultVal)), input.entries());
                        }
                    };
                }
                DataResult<T> baseResult = compose.codec().decode(ops, patchedInput);
                DataResult<Optional<ResourceLocation>> umaResult = CODECS.decode(ops, input);

                final var finalPatchedInput = patchedInput;

                return baseResult.apply2((base, opt) -> {
                    DataResult<ItemStack> stackResult = opt.isPresent()
                            ? DataResult.success(ItemStack.EMPTY)
                            : ItemStack.STRICT_CODEC.parse(ops, finalPatchedInput.get("result"));

                    ResourceLocation output = opt.orElseGet(() -> BuiltInRegistries.ITEM.getKey(stackResult.getOrThrow().getItem()));
                    return converter.apply(base, output);
                }, umaResult);
            }

            @Override
            public <O> RecordBuilder<O> encode(U input, DynamicOps<O> ops, RecordBuilder<O> prefix) {
                RecordBuilder<O> builder = compose.codec().encode(input, ops, prefix);
                return CODECS.encode(Optional.ofNullable(getOutputUma(input)), ops, builder);
            }
        };
    }

    @Nullable
    private static <U extends Recipe<?>> ResourceLocation getOutputUma(U recipe) {
        if (recipe instanceof ShapedCostumeRecipe umaRecipe) {
            return umaRecipe.getOutputUma();
        } else if (recipe instanceof ShapelessCostumeRecipe umaRecipe) {
            return umaRecipe.getOutputUma();
        }
        return null;
    }

    @Override
    @Nonnull
    public StreamCodec<RegistryFriendlyByteBuf, U> streamCodec() {
        return StreamCodec.composite(
                compose.streamCodec(),
                recipe -> recipe,
                ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC),
                recipe -> Optional.ofNullable(getOutputUma(recipe)),
                (base, optUma) -> converter.apply(base, optUma.orElse(null))
        );
    }
}
