package net.tracen.umapyoi.compat.jei;

import java.util.List;

import com.google.common.collect.Lists;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.DifferenceIngredient;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.compat.jei.category.JEIDisassemblyCategory;
import net.tracen.umapyoi.compat.jei.category.JEIGachaCategory;
import net.tracen.umapyoi.compat.jei.recipes.JEISimpleRecipe;
import net.tracen.umapyoi.compat.jei.recipes.UmapyoiJEIRecipes;
import net.tracen.umapyoi.data.tag.UmapyoiItemTags;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.utils.GachaRanking;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    public static final ResourceLocation PLUGIN_ID = new ResourceLocation(Umapyoi.MODID, "jei_plugin");

    public static final mezz.jei.api.recipe.RecipeType<JEISimpleRecipe> GACHA_JEI_TYPE = mezz.jei.api.recipe.RecipeType
            .create(Umapyoi.MODID, "gacha_recipe", JEISimpleRecipe.class);

    public static final mezz.jei.api.recipe.RecipeType<JEISimpleRecipe> DISASSEMBLY_JEI_TYPE = mezz.jei.api.recipe.RecipeType
            .create(Umapyoi.MODID, "disassembly_recipe", JEISimpleRecipe.class);

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new JEIGachaCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new JEIDisassemblyCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(DISASSEMBLY_JEI_TYPE, getAllDisassemblyRecipes());
        registration.addRecipes(GACHA_JEI_TYPE, getAllGachaRecipes());
    }

    private List<JEISimpleRecipe> getAllDisassemblyRecipes() {
        List<JEISimpleRecipe> result = Lists.newArrayList();
        result.add(UmapyoiJEIRecipes.disassembleUmasoul(ItemRegistry.CRYSTAL_SILVER.get().getDefaultInstance(), GachaRanking.R));
        result.add(UmapyoiJEIRecipes.disassembleUmasoul(ItemRegistry.CRYSTAL_GOLD.get().getDefaultInstance(), GachaRanking.SR));
        result.add(UmapyoiJEIRecipes.disassembleUmasoul(ItemRegistry.CRYSTAL_RAINBOW.get().getDefaultInstance(), GachaRanking.SSR));
        
        result.add(UmapyoiJEIRecipes.disassembleSupportCard(ItemRegistry.HORSESHOE_SILVER.get().getDefaultInstance(), GachaRanking.R));
        result.add(UmapyoiJEIRecipes.disassembleSupportCard(ItemRegistry.HORSESHOE_GOLD.get().getDefaultInstance(), GachaRanking.SR));
        result.add(UmapyoiJEIRecipes.disassembleSupportCard(ItemRegistry.HORSESHOE_RAINBOW.get().getDefaultInstance(), GachaRanking.SSR));
        return result;
    }

    private List<JEISimpleRecipe> getAllGachaRecipes() {
        return Lists.newArrayList(
                UmapyoiJEIRecipes.gachaUmasoul(Ingredient.of(UmapyoiItemTags.COMMON_GACHA_ITEM), GachaRanking.R),
                UmapyoiJEIRecipes.gachaUmasoul(
                        DifferenceIngredient.of(
                                DifferenceIngredient.of(
                                        DifferenceIngredient.of(Ingredient.of(UmapyoiItemTags.UMA_TICKET),
                                                Ingredient.of(UmapyoiItemTags.SSR_UMA_TICKET)),
                                        Ingredient.of(UmapyoiItemTags.SR_UMA_TICKET)),
                                Ingredient.of(UmapyoiItemTags.COMMON_GACHA_ITEM)),
                        GachaRanking.R, GachaRanking.SR, GachaRanking.SSR),
                UmapyoiJEIRecipes.gachaUmasoul(Ingredient.of(UmapyoiItemTags.SR_UMA_TICKET), GachaRanking.SR,
                        GachaRanking.SSR),
                UmapyoiJEIRecipes.gachaUmasoul(Ingredient.of(UmapyoiItemTags.SSR_UMA_TICKET), GachaRanking.SSR),

                UmapyoiJEIRecipes.gachaSupportCard(Ingredient.of(UmapyoiItemTags.COMMON_GACHA_ITEM), GachaRanking.R),
                UmapyoiJEIRecipes.gachaSupportCard(
                        DifferenceIngredient.of(
                                DifferenceIngredient.of(
                                        DifferenceIngredient.of(Ingredient.of(UmapyoiItemTags.CARD_TICKET),
                                                Ingredient.of(UmapyoiItemTags.SSR_CARD_TICKET)),
                                        Ingredient.of(UmapyoiItemTags.SR_CARD_TICKET)),
                                Ingredient.of(UmapyoiItemTags.COMMON_GACHA_ITEM)),
                        GachaRanking.R, GachaRanking.SR, GachaRanking.SSR),
                UmapyoiJEIRecipes.gachaSupportCard(Ingredient.of(UmapyoiItemTags.SR_CARD_TICKET), GachaRanking.SR,
                        GachaRanking.SSR),
                UmapyoiJEIRecipes.gachaSupportCard(Ingredient.of(UmapyoiItemTags.SSR_CARD_TICKET), GachaRanking.SSR));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ItemRegistry.DISASSEMBLY_BLOCK.get().getDefaultInstance(), DISASSEMBLY_JEI_TYPE);

        registration.addRecipeCatalyst(ItemRegistry.UMA_PEDESTAL.get().getDefaultInstance(), GACHA_JEI_TYPE);
        registration.addRecipeCatalyst(ItemRegistry.SILVER_UMA_PEDESTAL.get().getDefaultInstance(), GACHA_JEI_TYPE);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }

}
