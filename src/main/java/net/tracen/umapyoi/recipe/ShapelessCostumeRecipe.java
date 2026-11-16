package net.tracen.umapyoi.recipe;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.registry.training.card.SupportCard;

public class ShapelessCostumeRecipe extends ShapelessRecipe {
    public static final RecipeSerializer<ShapelessCostumeRecipe> SERIALIZER = new CostumeRecipeSerializer<>(
            RecipeSerializer.SHAPELESS_RECIPE, ShapelessCostumeRecipe::new);

    private final ResourceLocation outputUma;

    public ShapelessCostumeRecipe(ShapelessRecipe compose, ResourceLocation output) {
        super(compose.getGroup(), compose.category(), getResultItem(output), compose.getIngredients());
        this.outputUma = output;
    }

    private static ItemStack getResultItem(ResourceLocation outputBlade) {
        Item bladeItem = BuiltInRegistries.ITEM.containsKey(outputBlade) ? BuiltInRegistries.ITEM.get(outputBlade)
                : ItemRegistry.BLANK_UMA_SOUL.get();

        return bladeItem.getDefaultInstance();
    }

    public ResourceLocation getOutputUma() {
        return outputUma;
    }

    public ItemStack assemble(CraftingInput p_345555_, HolderLookup.Provider provider) {
        return this.getResultItem(provider).copy();
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider access) {
        ItemStack result = ShapelessCostumeRecipe.getResultItem(this.getOutputUma()).copy();
        if (!BuiltInRegistries.ITEM.getKey(result.getItem()).equals(getOutputUma()))
            result = ItemRegistry.UMA_COSTUME.get().getDefaultInstance();
        result.set(DataComponentsTypeRegistry.COSMETIC_DATA, this.getOutputUma());
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }
}
