package net.tracen.umapyoi.data;

import java.util.function.Consumer;
import java.util.stream.Stream;

import cn.mcmod_mmf.mmlib.data.AbstractRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.item.ItemRegistry;

public class UmapyoiRecipeProvider extends AbstractRecipeProvider {

    public UmapyoiRecipeProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        UpgradeRecipeBuilder.smithing(Ingredient.of(Tags.Items.CROPS_CARROT),
                Ingredient.fromValues(
                        Stream.of(new Ingredient.TagValue(Tags.Items.GEMS_DIAMOND),
                        new Ingredient.TagValue(Tags.Items.GEMS_EMERALD))
                        ),
                ItemRegistry.JEWEL.get())
        .unlocks("has_gem", has(Tags.Items.GEMS_DIAMOND))
        .save(consumer, ItemRegistry.JEWEL.getId());
        
        ShapedRecipeBuilder.shaped(BlockRegistry.THREE_GODDESS.get())
            .pattern(" J ")
            .pattern("JLJ")
            .pattern("AAA")
            .define('A', Tags.Items.STONE)
            .define('L', Tags.Items.STORAGE_BLOCKS_QUARTZ)
            .define('J', ItemRegistry.JEWEL.get())
            .unlockedBy("has_item", has(ItemRegistry.JEWEL.get()))
            .save(consumer);
        
        ShapedRecipeBuilder.shaped(BlockRegistry.REGISTER_LECTERN.get())
            .pattern(" J ")
            .pattern(" G ")
            .pattern("GAG")
            .define('A', Items.LECTERN)
            .define('G', Tags.Items.INGOTS_GOLD)
            .define('J', ItemRegistry.JEWEL.get())
            .unlockedBy("has_item", has(ItemRegistry.JEWEL.get()))
            .save(consumer);
        
        ShapedRecipeBuilder.shaped(BlockRegistry.SKILL_LEARNING_TABLE.get())
            .pattern(" J")
            .pattern(" L")
            .define('L', Tags.Items.BOOKSHELVES)
            .define('J', ItemRegistry.JEWEL.get())
            .unlockedBy("has_item", has(ItemRegistry.JEWEL.get()))
            .save(consumer);
        
        ShapedRecipeBuilder.shaped(BlockRegistry.TRAINING_FACILITY.get())
            .pattern("IJI")
            .pattern("ILI")
            .define('I', Tags.Items.INGOTS_IRON)
            .define('L', Items.CRAFTING_TABLE)
            .define('J', ItemRegistry.JEWEL.get())
            .unlockedBy("has_item", has(ItemRegistry.JEWEL.get()))
            .save(consumer);
        
        ShapedRecipeBuilder.shaped(ItemRegistry.SUMMER_UNIFORM.get())
            .pattern("IJI")
            .pattern("ILI")
            .pattern("ILI")
            .define('I', Items.PURPLE_WOOL)
            .define('L', Items.WHITE_WOOL)
            .define('J', ItemRegistry.JEWEL.get())
            .unlockedBy("has_item", has(ItemRegistry.JEWEL.get()))
            .save(consumer);
        
        ShapedRecipeBuilder.shaped(ItemRegistry.WINTER_UNIFORM.get())
            .pattern("IJI")
            .pattern("III")
            .pattern("III")
            .define('I', Items.PURPLE_WOOL)
            .define('J', ItemRegistry.JEWEL.get())
            .unlockedBy("has_item", has(ItemRegistry.JEWEL.get()))
            .save(consumer);
        
        ShapedRecipeBuilder.shaped(ItemRegistry.TRAINNING_SUIT.get())
            .pattern("IJI")
            .pattern("ILI")
            .pattern("ILI")
            .define('I', Items.RED_WOOL)
            .define('L', Items.WHITE_WOOL)
            .define('J', ItemRegistry.JEWEL.get())
            .unlockedBy("has_item", has(ItemRegistry.JEWEL.get()))
            .save(consumer);
        
    }

}
