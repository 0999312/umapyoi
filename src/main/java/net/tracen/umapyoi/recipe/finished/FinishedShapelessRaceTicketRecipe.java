package net.tracen.umapyoi.recipe.finished;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.recipe.RecipeSerializerRegistry;

import javax.annotation.Nullable;
import java.util.List;

public record FinishedShapelessRaceTicketRecipe(ResourceLocation id, List<Ingredient> ingredients,
                                                ResourceLocation race) implements FinishedRecipe {
    @Nullable
    @Override
    public ResourceLocation getAdvancementId() {
        return null;
    }

    @Nullable
    @Override
    public JsonObject serializeAdvancement() {
        return null;
    }

    @Override
    public RecipeSerializer<?> getType() {
        return RecipeSerializerRegistry.SHAPELESS_RACE_TICKET.get();
    }

    @Override
    public ResourceLocation getId() {
        return this.id();
    }

    @Override
    public void serializeRecipeData(JsonObject pJson) {
        JsonArray ingredientsArray = new JsonArray();
        this.ingredients.forEach(i -> ingredientsArray.add(i.toJson()));
        pJson.add("ingredients", ingredientsArray);

        JsonObject resultJson = new JsonObject();
        resultJson.addProperty("item", ItemRegistry.UMA_RACE_TICKET.getId().toString());
        resultJson.addProperty("count", 1);
        pJson.add("result", resultJson);

        if (race != null) {
            pJson.addProperty("race", race().toString());
        }
    }
}
