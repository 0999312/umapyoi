package net.tracen.umapyoi.container;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

import javax.annotation.Nullable;

public class ExtendedResultContainer implements Container, RecipeHolder {
    public final int size;
    protected final NonNullList<ItemStack> itemStacks;

    @Nullable
    private Recipe<?> recipeUsed;

    public ExtendedResultContainer(int size) {
        this.itemStacks = NonNullList.withSize(size, ItemStack.EMPTY);
        this.size = size;
    }

    public int getContainerSize() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.itemStacks.stream().allMatch(ItemStack::isEmpty);
    }

    public ItemStack getItem(int pIndex) {
        return this.itemStacks.get(pIndex);
    }

    public ItemStack removeItem(int pIndex, int pCount) {
        return ContainerHelper.takeItem(this.itemStacks, pIndex);
    }

    public ItemStack removeItemNoUpdate(int pIndex) {
        return ContainerHelper.takeItem(this.itemStacks, pIndex);
    }

    public void setItem(int pIndex, ItemStack pStack) {
        this.itemStacks.set(pIndex, pStack);
    }

    public void setChanged() {
    }

    public boolean stillValid(Player pPlayer) {
        return true;
    }

    public void clearContent() {
        this.itemStacks.clear();
    }

    public void setRecipeUsed(@Nullable Recipe<?> pRecipe) {
        this.recipeUsed = pRecipe;
    }

    @Nullable
    public Recipe<?> getRecipeUsed() {
        return this.recipeUsed;
    }
}
