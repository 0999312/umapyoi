package net.tracen.umapyoi.compat.jei.category;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.compat.jei.JEIPlugin;
import net.tracen.umapyoi.compat.jei.recipes.JEISimpleRecipe;

public class JEIDisassemblyCategory implements IRecipeCategory<JEISimpleRecipe> {
    private final Component title;
    private final IDrawable background;
    private final IDrawable icon;

    public JEIDisassemblyCategory(IGuiHelper helper) {
        title = Component.translatable("umapyoi.jei.disassembly");
        ResourceLocation backgroundImage = ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "textures/gui/jei_compat.png");
        background = helper.createDrawable(backgroundImage, 0, 0, 93, 46);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,
                new ItemStack(BlockRegistry.DISASSEMBLY_BLOCK.get()));
    }
    
    @Override
    public RecipeType<JEISimpleRecipe> getRecipeType() {
        return JEIPlugin.DISASSEMBLY_JEI_TYPE;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, JEISimpleRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 14, 15).setSlotName("inputSlot").addItemStacks(recipe.getInputs());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 63, 15).setSlotName("outputSlot").addItemStacks(recipe.getOutputs());
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

}
