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
import net.tracen.umapyoi.item.FadedUmaSoulItem;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.registry.training.card.SupportCard;
import net.tracen.umapyoi.registry.umadata.UmaData;

public class ShapelessSupportCardRecipe extends ShapelessRecipe {
    public static final RecipeSerializer<ShapelessSupportCardRecipe> SERIALIZER = new SupportCardRecipeSerializer<>(
            RecipeSerializer.SHAPELESS_RECIPE, ShapelessSupportCardRecipe::new);

    private final ResourceLocation outputUma;

    public ShapelessSupportCardRecipe(ShapelessRecipe compose, ResourceLocation output) {
        super(compose.getGroup(), compose.category(), getResultItem(output), compose.getIngredients());
        this.outputUma = output;
    }

    private static ItemStack getResultItem(ResourceLocation outputBlade) {
        Item bladeItem = BuiltInRegistries.ITEM.containsKey(outputBlade) ? BuiltInRegistries.ITEM.get(outputBlade)
                : ItemRegistry.SUPPORT_CARD.get();

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
        ItemStack result = ShapelessSupportCardRecipe.getResultItem(this.getOutputUma()).copy();
        if(access == RegistryAccess.EMPTY)
            return result;
        HolderLookup.RegistryLookup<SupportCard> registry = access.lookupOrThrow(SupportCard.REGISTRY_KEY);
        if (!BuiltInRegistries.ITEM.getKey(result.getItem()).equals(getOutputUma()))
            result = ItemRegistry.SUPPORT_CARD.get().getDefaultInstance();
        SupportCard data = registry.get(ResourceKey.create(SupportCard.REGISTRY_KEY, outputUma)).map(Holder.Reference::value).orElse(null);
        if(data == null)
            return ItemStack.EMPTY;
        SupportCard.init(result, this.outputUma, data);
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }
}
