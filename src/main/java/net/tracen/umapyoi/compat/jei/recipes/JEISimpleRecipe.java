package net.tracen.umapyoi.compat.jei.recipes;

import java.util.List;

import org.jetbrains.annotations.Unmodifiable;

import net.minecraft.world.item.ItemStack;

public class JEISimpleRecipe implements IJEISimpleRecipe{
    
    private final List<ItemStack> inputs;
    private final List<ItemStack> outputs;
    
    public JEISimpleRecipe(List<ItemStack> input, List<ItemStack> output) {
        this.inputs=List.copyOf(input);
        this.outputs=List.copyOf(output);
    }

    @Override
    public @Unmodifiable List<ItemStack> getInputs() {
        return inputs;
    }

    @Override
    public @Unmodifiable List<ItemStack> getOutputs() {
        return outputs;
    }

}
