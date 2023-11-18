package net.tracen.umapyoi.compat.jei.category;

import java.util.Optional;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.compat.jei.JEIPlugin;
import net.tracen.umapyoi.compat.jei.recipes.JEISimpleRecipe;
import net.tracen.umapyoi.item.ItemRegistry;

public class JEIGachaCategory implements IRecipeCategory<JEISimpleRecipe> {
    private final Component title;
    private final ResourceLocation UID;
    private final IDrawable background;
    private final IDrawable chancedSlot;
    private final IDrawable icon;

    public JEIGachaCategory(IGuiHelper helper) {
        title = new TranslatableComponent("umapyoi.jei.gacha");
        ResourceLocation backgroundImage = new ResourceLocation(Umapyoi.MODID, "textures/gui/jei_compat.png");
        UID = new ResourceLocation(Umapyoi.MODID, "gacha");
        background = helper.createDrawable(backgroundImage, 0, 0, 93, 46);
        chancedSlot = helper.createDrawable(backgroundImage, 93, 0, 18, 18);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,
                new ItemStack(BlockRegistry.UMA_PEDESTAL.get()));
    }
    
    @Override
    public RecipeType<JEISimpleRecipe> getRecipeType() {
        return JEIPlugin.GACHA_JEI_TYPE;
    }

    @Override
    public void draw(JEISimpleRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX,
            double mouseY) {
        Optional<ItemStack> outputStack = recipeSlotsView.findSlotByName("outputSlot")
                .flatMap(slot -> slot.getDisplayedIngredient(VanillaTypes.ITEM_STACK));
        outputStack.ifPresent(output -> {
            if (output.is(ItemRegistry.SUPPORT_CARD.get())) {
                Minecraft minecraft = Minecraft.getInstance();
                Font fontRenderer = minecraft.font;
                var needBookText = new TranslatableComponent("umapyoi.jei.gacha.need_book");
                fontRenderer.drawShadow(stack, needBookText,
                        46 - (fontRenderer.width(needBookText.getVisualOrderText()) / 2.0F), 36, 0xFEFEFE);
                RenderSystem.setShaderColor(1, 1, 1, 1);
            }
        });
        if (recipe.getOutputs().size() > 1)
            chancedSlot.draw(stack, 62, 14);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, JEISimpleRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 14, 15).setSlotName("inputSlot").addItemStacks(recipe.getInputs());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 63, 15).setSlotName("outputSlot")
                .addItemStacks(recipe.getOutputs());
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

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends JEISimpleRecipe> getRecipeClass() {
        return JEISimpleRecipe.class;
    }

}
