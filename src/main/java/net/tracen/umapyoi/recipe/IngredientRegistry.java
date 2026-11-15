package net.tracen.umapyoi.recipe;

import net.neoforged.neoforge.common.crafting.IngredientType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.tracen.umapyoi.Umapyoi;

import java.util.function.Supplier;

public class IngredientRegistry {
    public static final DeferredRegister<IngredientType<?>> INGREDIENTS = DeferredRegister.create(NeoForgeRegistries.Keys.INGREDIENT_TYPES, Umapyoi.MODID);

    public static final Supplier<IngredientType<UmasoulIngredient>> UMASOUL_INGREDIENT =
            INGREDIENTS.register("umasoul", () -> new IngredientType<>(UmasoulIngredient.CODEC, UmasoulIngredient.STREAM_CODEC));
}
