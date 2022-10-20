package net.tracen.umapyoi.data;

import java.util.function.Consumer;

import cn.mcmod_mmf.mmlib.data.AbstractRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;

public class UmapyoiRecipeProvider extends AbstractRecipeProvider {

    public UmapyoiRecipeProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {

    }

}
