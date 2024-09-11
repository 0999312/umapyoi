package net.tracen.umapyoi.data;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import cn.mcmod_mmf.mmlib.data.AbstractRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.data.tag.UmapyoiItemTags;
import net.tracen.umapyoi.item.ItemRegistry;

public class UmapyoiRecipeProvider extends AbstractRecipeProvider {

    public UmapyoiRecipeProvider(PackOutput gen, CompletableFuture<HolderLookup.Provider> registries) {
        super(gen, Umapyoi.MODID, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.JEWEL.get())
                .requires(Tags.Items.CROPS_CARROT)
                .requires(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(Tags.Items.GEMS_DIAMOND),
                        new Ingredient.TagValue(Tags.Items.GEMS_EMERALD))))
                .unlockedBy("has_item", has(Tags.Items.CROPS_CARROT)).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.BLANK_TICKET.get(), 2).requires(Items.PAPER).requires(Items.PAPER)
        .requires(Tags.Items.GEMS_LAPIS).requires(ItemRegistry.JEWEL.get())
        .unlockedBy("has_item", has(ItemRegistry.JEWEL.get()))
        .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.UMA_TICKET.get())
                .requires(ItemRegistry.BLANK_TICKET.get()).requires(ItemRegistry.CRYSTAL_SILVER.get())
                .unlockedBy("has_item", has(ItemRegistry.BLANK_TICKET.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.SR_UMA_TICKET.get())
                .requires(ItemRegistry.BLANK_TICKET.get()).requires(ItemRegistry.CRYSTAL_GOLD.get())
                .unlockedBy("has_item", has(ItemRegistry.BLANK_TICKET.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.SSR_UMA_TICKET.get())
                .requires(ItemRegistry.BLANK_TICKET.get()).requires(ItemRegistry.CRYSTAL_RAINBOW.get())
                .unlockedBy("has_item", has(ItemRegistry.BLANK_TICKET.get())).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.CARD_TICKET.get())
                .requires(ItemRegistry.BLANK_TICKET.get()).requires(ItemRegistry.HORSESHOE_SILVER.get())
                .unlockedBy("has_item", has(ItemRegistry.BLANK_TICKET.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.SR_CARD_TICKET.get())
                .requires(ItemRegistry.BLANK_TICKET.get()).requires(ItemRegistry.HORSESHOE_GOLD.get())
                .unlockedBy("has_item", has(ItemRegistry.BLANK_TICKET.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.SSR_CARD_TICKET.get())
                .requires(ItemRegistry.BLANK_TICKET.get()).requires(ItemRegistry.HORSESHOE_RAINBOW.get())
                .unlockedBy("has_item", has(ItemRegistry.BLANK_TICKET.get())).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.DISASSEMBLY_BLOCK.get()).pattern(" J ")
                .pattern("ALA").pattern("AAA").define('A', Tags.Items.INGOTS_IRON).define('L', Items.BLAST_FURNACE)
                .define('J', ItemRegistry.BLANK_TICKET.get())
                .unlockedBy("has_item", has(ItemRegistry.BLANK_TICKET.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.UMA_SELECT_BLOCK.get()).pattern(" J ").pattern("BLB").pattern("AAA")
        .define('A', Tags.Items.GEMS_DIAMOND)
        .define('B', Items.NETHER_STAR).define('L', Items.LECTERN)
        .define('J', ItemRegistry.JEWEL.get())
        .unlockedBy("has_item", has(ItemRegistry.BLANK_TICKET.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.THREE_GODDESS.get()).pattern(" J ")
                .pattern("JLJ").pattern("AAA").define('A', Tags.Items.STONES)
                .define('L', Items.QUARTZ_BLOCK).define('J', ItemRegistry.JEWEL.get())
                .unlockedBy("has_item", has(ItemRegistry.JEWEL.get())).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.REGISTER_LECTERN.get()).pattern(" J ")
                .pattern(" G ").pattern("GAG").define('A', Items.LECTERN).define('G', Tags.Items.INGOTS_GOLD)
                .define('J', ItemRegistry.JEWEL.get()).unlockedBy("has_item", has(ItemRegistry.JEWEL.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.SILVER_UMA_PEDESTAL.get()).pattern("AJA")
                .pattern("GAG").pattern("AAA").define('A', Tags.Items.STONES).define('G', Tags.Items.INGOTS_IRON)
                .define('J', ItemRegistry.JEWEL.get()).unlockedBy("has_item", has(ItemRegistry.JEWEL.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.UMA_PEDESTAL.get()).pattern(" J ")
                .pattern("GAG").pattern("GGG").define('A', ItemRegistry.SILVER_UMA_PEDESTAL.get())
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('J',
                        Ingredient.fromValues(
                                Stream.of(new Ingredient.ItemValue(new ItemStack(ItemRegistry.CRYSTAL_GOLD.get())),
                                        new Ingredient.ItemValue(new ItemStack(ItemRegistry.HORSESHOE_GOLD.get())))))
                .unlockedBy("has_item", has(ItemRegistry.CRYSTAL_GOLD.get())).save(consumer);
        
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, 
                BlockRegistry.UMA_STATUES.get()).pattern(" J ").pattern(" A ").pattern("AAA")
        .define('A', Tags.Items.STONES)
        .define('J', ItemRegistry.JEWEL.get()).unlockedBy("has_item", has(ItemRegistry.JEWEL.get()))
        .save(consumer);
        
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.SWIMSUIT.get())
                .pattern("IJI")
                .pattern("ILI")
                .pattern(" I ")
                .define('I', Tags.Items.LEATHERS)
                .define('L', Tags.Items.DYES_BLUE)
                .define('J', ItemRegistry.JEWEL.get())
                .unlockedBy("has_item", has(ItemRegistry.JEWEL.get())).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.SKILL_LEARNING_TABLE.get()).pattern(" J")
                .pattern(" L").define('L', Tags.Items.BOOKSHELVES).define('J', ItemRegistry.JEWEL.get())
                .unlockedBy("has_item", has(ItemRegistry.JEWEL.get())).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.TRAINING_FACILITY.get()).pattern("IJI")
                .pattern("ILI").define('I', Tags.Items.INGOTS_IRON).define('L', Items.CRAFTING_TABLE)
                .define('J', ItemRegistry.JEWEL.get()).unlockedBy("has_item", has(ItemRegistry.JEWEL.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.SUMMER_UNIFORM.get()).pattern("IJI")
                .pattern("ILI").pattern("ILI").define('I', Items.PURPLE_WOOL).define('L', Items.WHITE_WOOL)
                .define('J', ItemRegistry.JEWEL.get()).unlockedBy("has_item", has(ItemRegistry.JEWEL.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.WINTER_UNIFORM.get()).pattern("IJI")
                .pattern("III").pattern("III").define('I', Items.PURPLE_WOOL).define('J', ItemRegistry.JEWEL.get())
                .unlockedBy("has_item", has(ItemRegistry.JEWEL.get())).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.TRAINNING_SUIT.get()).pattern("IJI")
                .pattern("ILI").pattern("ILI").define('I', Items.RED_WOOL).define('L', Items.WHITE_WOOL)
                .define('J', ItemRegistry.JEWEL.get()).unlockedBy("has_item", has(ItemRegistry.JEWEL.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD,ItemRegistry.HACHIMI_MID.get()).requires(Items.HONEY_BOTTLE)
                .requires(UmapyoiItemTags.SUGAR).requires(UmapyoiItemTags.SUGAR).requires(UmapyoiItemTags.WATER)
                .unlockedBy("has_item", has(Items.HONEY_BOTTLE)).save(consumer);
        
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD,ItemRegistry.HACHIMI_BIG.get()).requires(Items.HONEY_BOTTLE)
                .requires(Items.HONEY_BOTTLE).requires(UmapyoiItemTags.SUGAR).requires(UmapyoiItemTags.SUGAR)
                .requires(UmapyoiItemTags.WATER).unlockedBy("has_item", has(Items.HONEY_BLOCK)).save(consumer);
        
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD,ItemRegistry.SMALL_ENERGY_DRINK.get()).requires(Tags.Items.CROPS_CARROT)
                .requires(Tags.Items.CROPS_NETHER_WART).requires(UmapyoiItemTags.SUGAR).requires(UmapyoiItemTags.WATER)
                .unlockedBy("has_item", has(Tags.Items.CROPS_NETHER_WART)).save(consumer);
        
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD,ItemRegistry.MEDIUM_ENERGY_DRINK.get()).requires(Tags.Items.CROPS_CARROT)
                .requires(Tags.Items.CROPS_CARROT).requires(Tags.Items.DUSTS_REDSTONE)
                .requires(Tags.Items.CROPS_NETHER_WART).requires(UmapyoiItemTags.SUGAR).requires(UmapyoiItemTags.WATER)
                .unlockedBy("has_item", has(Tags.Items.CROPS_NETHER_WART)).save(consumer);
        
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD,ItemRegistry.LARGE_ENERGY_DRINK.get()).requires(Tags.Items.CROPS_CARROT)
                .requires(Tags.Items.CROPS_CARROT).requires(Tags.Items.DUSTS_REDSTONE)
                .requires(Tags.Items.DUSTS_GLOWSTONE).requires(Tags.Items.CROPS_NETHER_WART)
                .requires(UmapyoiItemTags.SUGAR).requires(UmapyoiItemTags.WATER)
                .unlockedBy("has_item", has(Tags.Items.CROPS_NETHER_WART)).save(consumer);
        
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD,ItemRegistry.ROYAL_BITTER.get())
                .requires(Tags.Items.SEEDS_WHEAT).requires(Items.GLISTERING_MELON_SLICE)
                .requires(Tags.Items.DUSTS_REDSTONE).requires(Tags.Items.CROPS_NETHER_WART)
                .requires(UmapyoiItemTags.WATER)
                .unlockedBy("has_item", has(Tags.Items.CROPS_NETHER_WART)).save(consumer);
        
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD,ItemRegistry.CUPCAKE.get()).requires(Tags.Items.CROPS_CARROT)
                .requires(Tags.Items.CROPS_WHEAT).requires(Tags.Items.EGGS).requires(UmapyoiItemTags.SUGAR)
                .requires(UmapyoiItemTags.MILK).unlockedBy("has_item", has(Tags.Items.CROPS_CARROT)).save(consumer);
        
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD,ItemRegistry.SWEET_CUPCAKE.get()).requires(Tags.Items.CROPS_CARROT)
                .requires(Tags.Items.CROPS_CARROT).requires(Tags.Items.CROPS_WHEAT).requires(Tags.Items.EGGS)
                .requires(UmapyoiItemTags.SUGAR).requires(UmapyoiItemTags.SUGAR).requires(UmapyoiItemTags.MILK)
                .unlockedBy("has_item", has(Tags.Items.CROPS_CARROT)).save(consumer);
        
        
    }

}
