package net.tracen.umapyoi.compat.jei.recipes;

import java.util.List;

import org.jetbrains.annotations.Unmodifiable;

import net.minecraft.world.item.ItemStack;

public interface IJEISimpleRecipe {
    @Unmodifiable
    List<ItemStack> getInputs();

    @Unmodifiable
    List<ItemStack> getOutputs();
}
