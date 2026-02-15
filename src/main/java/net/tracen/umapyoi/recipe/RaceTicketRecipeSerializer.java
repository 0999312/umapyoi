package net.tracen.umapyoi.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.tracen.umapyoi.item.ItemRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public record RaceTicketRecipeSerializer<T extends Recipe<?>, U extends T> (RecipeSerializer<T> compose,
                                                                            BiFunction<T, ResourceLocation, U> converter)
implements RecipeSerializer<U> {

    @Override
    public U fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
        if (!pSerializedRecipe.has("result")) {
            JsonObject obj = new JsonObject();
            obj.addProperty("item", ItemRegistry.UMA_RACE_TICKET.getId().toString());
            pSerializedRecipe.add("result", obj);
        }
        T recipe = compose().fromJson(pRecipeId, pSerializedRecipe);
        if (pSerializedRecipe.has("race")) {
            ResourceLocation output = ResourceLocation.tryParse(GsonHelper.getAsString(pSerializedRecipe, "race"));
            return converter().apply(recipe, output);
        }
        return converter().apply(recipe, ResourceLocation.tryParse(GsonHelper.getAsString(pSerializedRecipe.getAsJsonObject("result"), "item")));
    }

    @Override
    public @Nullable U fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
        T recipe = compose().fromNetwork(pRecipeId, pBuffer);
        if (pBuffer.readBoolean())
            return converter().apply(recipe, pBuffer.readResourceLocation());
        return converter().apply(recipe, null);
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, U pRecipe) {
        compose().toNetwork(pBuffer, pRecipe);
        if (pRecipe instanceof ShapelessRaceTicketRecipe shapeless) {
            boolean hasName = shapeless.getKey() != null;
            pBuffer.writeBoolean(hasName);
            if (hasName) pBuffer.writeResourceLocation(shapeless.getKey());
        } else {
            pBuffer.writeBoolean(false);
        }
    }
}