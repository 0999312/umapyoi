package net.tracen.umapyoi.recipe;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tracen.umapyoi.Umapyoi;

import java.util.function.Supplier;

public class RecipeSerializerRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister
            .create(Registries.RECIPE_SERIALIZER, Umapyoi.MODID);

    public static final Supplier<RecipeSerializer<?>> SHAPED_UMASOUL = RECIPE_SERIALIZER
            .register("shaped_umasoul", () -> ShapedUmasoulRecipe.SERIALIZER);

    public static final Supplier<RecipeSerializer<?>> SHAPELESS_UMASOUL = RECIPE_SERIALIZER
            .register("shapeless_umasoul", () -> ShapelessUmasoulRecipe.SERIALIZER);

    public static final Supplier<RecipeSerializer<?>> SHAPED_CARD = RECIPE_SERIALIZER
            .register("shaped_card", () -> ShapedSupportCardRecipe.SERIALIZER);

    public static final Supplier<RecipeSerializer<?>> SHAPELESS_CARD = RECIPE_SERIALIZER
            .register("shapeless_card", () -> ShapelessSupportCardRecipe.SERIALIZER);

    public static final Supplier<RecipeSerializer<?>> SHAPED_COSTUME = RECIPE_SERIALIZER
            .register("shaped_costume", () -> ShapedCostumeRecipe.SERIALIZER);

    public static final Supplier<RecipeSerializer<?>> SHAPELESS_COSTUME = RECIPE_SERIALIZER
            .register("shapeless_costume", () -> ShapelessCostumeRecipe.SERIALIZER);
}
