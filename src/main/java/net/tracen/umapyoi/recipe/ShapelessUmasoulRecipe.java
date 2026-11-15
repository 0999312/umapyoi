package net.tracen.umapyoi.recipe;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.tracen.umapyoi.item.FadedUmaSoulItem;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.registry.umadata.UmaData;

public class ShapelessUmasoulRecipe extends ShapelessRecipe {
    public static final RecipeSerializer<ShapelessUmasoulRecipe> SERIALIZER = new UmasoulRecipeSerializer<>(
            RecipeSerializer.SHAPELESS_RECIPE, ShapelessUmasoulRecipe::new);

    private final ResourceLocation outputUma;

    public ShapelessUmasoulRecipe(ShapelessRecipe compose, ResourceLocation output) {
        super(compose.getGroup(), compose.category(), getResultItem(output), compose.getIngredients());
        this.outputUma = output;
    }

    private static ItemStack getResultItem(ResourceLocation output) {
        Item bladeItem = BuiltInRegistries.ITEM.containsKey(output) ? BuiltInRegistries.ITEM.get(output)
                : ItemRegistry.BLANK_UMA_SOUL.get();

        return bladeItem.getDefaultInstance();
    }

    public ResourceLocation getOutputUma() {
        return outputUma;
    }

    @Override
    public ItemStack assemble(CraftingInput p_345555_, HolderLookup.Provider provider) {
        return this.getResultItem(provider).copy();
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider access) {
        ItemStack result = ShapelessUmasoulRecipe.getResultItem(this.getOutputUma()).copy();
        if(access == RegistryAccess.EMPTY)
            return result;
        HolderLookup.RegistryLookup<UmaData> registry = access.lookupOrThrow(UmaData.REGISTRY_KEY);
        if (!BuiltInRegistries.ITEM.getKey(result.getItem()).equals(getOutputUma()))
            result = ItemRegistry.BLANK_UMA_SOUL.get().getDefaultInstance();
        UmaData data = registry.get(ResourceKey.create(UmaData.REGISTRY_KEY, outputUma)).map(Holder.Reference::value).orElse(null);
        if(data == null)
            return ItemStack.EMPTY;
        FadedUmaSoulItem.genUmaSoul(result, this.outputUma, data);
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }
}
