package net.tracen.umapyoi.data;

import java.util.function.Consumer;
import java.util.stream.Stream;

import cn.mcmod_mmf.mmlib.data.AbstractRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.world.item.ItemStack;
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
        UpgradeRecipeBuilder
                .smithing(Ingredient.of(Tags.Items.CROPS_CARROT), Ingredient.of(Tags.Items.GEMS_DIAMOND),
                        ItemRegistry.JEWEL.get())
                .unlocks("has_gem", has(Tags.Items.GEMS_DIAMOND)).save(consumer, "umapyoi:jewel_diamond");

        UpgradeRecipeBuilder
                .smithing(Ingredient.of(Tags.Items.CROPS_CARROT), Ingredient.of(Tags.Items.GEMS_EMERALD),
                        ItemRegistry.JEWEL.get())
                .unlocks("has_gem", has(Tags.Items.GEMS_EMERALD)).save(consumer, "umapyoi:jewel_emerald");
        
        ShapelessRecipeBuilder.shapeless(ItemRegistry.BLANK_TICKET.get())
                .requires(Items.PAPER)
                .requires(ItemRegistry.JEWEL.get())
                .unlockedBy("has_item", has(ItemRegistry.JEWEL.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(ItemRegistry.UMA_TICKET.get())
                .requires(ItemRegistry.BLANK_TICKET.get())
                .requires(ItemRegistry.CRYSTAL_SILVER.get())
                .unlockedBy("has_item", has(ItemRegistry.BLANK_TICKET.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(ItemRegistry.SR_UMA_TICKET.get())
                .requires(ItemRegistry.BLANK_TICKET.get())
                .requires(ItemRegistry.CRYSTAL_GOLD.get())
                .unlockedBy("has_item", has(ItemRegistry.BLANK_TICKET.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(ItemRegistry.SSR_UMA_TICKET.get())
                .requires(ItemRegistry.BLANK_TICKET.get())
                .requires(ItemRegistry.CRYSTAL_RAINBOW.get())
                .unlockedBy("has_item", has(ItemRegistry.BLANK_TICKET.get())).save(consumer);
        
        ShapelessRecipeBuilder.shapeless(ItemRegistry.CARD_TICKET.get())
                .requires(ItemRegistry.BLANK_TICKET.get())
                .requires(ItemRegistry.HORSESHOE_SILVER.get())
                .unlockedBy("has_item", has(ItemRegistry.BLANK_TICKET.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(ItemRegistry.SR_CARD_TICKET.get())
                .requires(ItemRegistry.BLANK_TICKET.get())
                .requires(ItemRegistry.HORSESHOE_GOLD.get())
                .unlockedBy("has_item", has(ItemRegistry.BLANK_TICKET.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(ItemRegistry.SSR_CARD_TICKET.get())
                .requires(ItemRegistry.BLANK_TICKET.get())
                .requires(ItemRegistry.HORSESHOE_RAINBOW.get())
                .unlockedBy("has_item", has(ItemRegistry.BLANK_TICKET.get())).save(consumer);
        
        ShapedRecipeBuilder.shaped(BlockRegistry.DISASSEMBLY_BLOCK.get())
                .pattern(" J ")
                .pattern("ALA")
                .pattern("AAA")
                .define('A', Tags.Items.INGOTS_IRON)
                .define('L', Items.BLAST_FURNACE)
                .define('J', ItemRegistry.BLANK_TICKET.get())
                .unlockedBy("has_item", has(ItemRegistry.BLANK_TICKET.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(BlockRegistry.THREE_GODDESS.get()).pattern(" J ").pattern("JLJ").pattern("AAA")
                .define('A', Tags.Items.STONE).define('L', Tags.Items.STORAGE_BLOCKS_QUARTZ)
                .define('J', ItemRegistry.JEWEL.get()).unlockedBy("has_item", has(ItemRegistry.JEWEL.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(BlockRegistry.REGISTER_LECTERN.get()).pattern(" J ").pattern(" G ").pattern("GAG")
                .define('A', Items.LECTERN).define('G', Tags.Items.INGOTS_GOLD).define('J', ItemRegistry.JEWEL.get())
                .unlockedBy("has_item", has(ItemRegistry.JEWEL.get())).save(consumer);

        ShapedRecipeBuilder.shaped(BlockRegistry.SILVER_UMA_PEDESTAL.get())
                .pattern("AJA")
                .pattern("GAG")
                .pattern("AAA")
                .define('A', Tags.Items.STONE)
                .define('G', Tags.Items.INGOTS_IRON)
                .define('J', ItemRegistry.JEWEL.get())
                .unlockedBy("has_item", has(ItemRegistry.JEWEL.get())).save(consumer);
        
        ShapedRecipeBuilder.shaped(BlockRegistry.UMA_PEDESTAL.get())
                .pattern(" J ")
                .pattern("GAG")
                .pattern("GGG")
                .define('A', ItemRegistry.SILVER_UMA_PEDESTAL.get())
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('J', Ingredient.fromValues(Stream.of(
                        new Ingredient.ItemValue(new ItemStack(ItemRegistry.CRYSTAL_GOLD.get())),
                        new Ingredient.ItemValue(new ItemStack(ItemRegistry.HORSESHOE_GOLD.get()))
                )))
                .unlockedBy("has_item", has(ItemRegistry.CRYSTAL_GOLD.get())).save(consumer);

        ShapedRecipeBuilder.shaped(BlockRegistry.SKILL_LEARNING_TABLE.get()).pattern(" J").pattern(" L")
                .define('L', Tags.Items.BOOKSHELVES).define('J', ItemRegistry.JEWEL.get())
                .unlockedBy("has_item", has(ItemRegistry.JEWEL.get())).save(consumer);

        ShapedRecipeBuilder.shaped(BlockRegistry.TRAINING_FACILITY.get()).pattern("IJI").pattern("ILI")
                .define('I', Tags.Items.INGOTS_IRON).define('L', Items.CRAFTING_TABLE)
                .define('J', ItemRegistry.JEWEL.get()).unlockedBy("has_item", has(ItemRegistry.JEWEL.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ItemRegistry.SUMMER_UNIFORM.get()).pattern("IJI").pattern("ILI").pattern("ILI")
                .define('I', Items.PURPLE_WOOL).define('L', Items.WHITE_WOOL).define('J', ItemRegistry.JEWEL.get())
                .unlockedBy("has_item", has(ItemRegistry.JEWEL.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ItemRegistry.WINTER_UNIFORM.get()).pattern("IJI").pattern("III").pattern("III")
                .define('I', Items.PURPLE_WOOL).define('J', ItemRegistry.JEWEL.get())
                .unlockedBy("has_item", has(ItemRegistry.JEWEL.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ItemRegistry.TRAINNING_SUIT.get()).pattern("IJI").pattern("ILI").pattern("ILI")
                .define('I', Items.RED_WOOL).define('L', Items.WHITE_WOOL).define('J', ItemRegistry.JEWEL.get())
                .unlockedBy("has_item", has(ItemRegistry.JEWEL.get())).save(consumer);

        ShapelessRecipeBuilder.shapeless(ItemRegistry.HACHIMI_MID.get()).requires(Items.HONEY_BOTTLE)
                .requires(Items.SUGAR).requires(Items.SUGAR).requires(Items.WATER_BUCKET)
                .unlockedBy("has_item", has(Items.HONEY_BOTTLE)).save(consumer);

        ShapelessRecipeBuilder.shapeless(ItemRegistry.HACHIMI_BIG.get()).requires(Items.HONEY_BOTTLE)
                .requires(Items.HONEY_BOTTLE).requires(Items.SUGAR).requires(Items.SUGAR).requires(Items.WATER_BUCKET)
                .unlockedBy("has_item", has(Items.HONEY_BLOCK)).save(consumer);
    }

}
