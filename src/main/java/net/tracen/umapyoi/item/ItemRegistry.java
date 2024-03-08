package net.tracen.umapyoi.item;

import java.util.function.Supplier;

import cn.mcmod_mmf.mmlib.item.info.FoodInfo;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.item.food.EnergyDrinkMethods;
import net.tracen.umapyoi.item.food.UmaDrinkItem;
import net.tracen.umapyoi.item.food.UmaFoodItem;
import net.tracen.umapyoi.registry.TrainingSupportRegistry;
import net.tracen.umapyoi.registry.training.SupportType;
import net.tracen.umapyoi.utils.UmaStatusUtils;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Umapyoi.MODID);

    public static final RegistryObject<Item> SILVER_UMA_PEDESTAL = register("silver_uma_pedestal",
            () -> new BlockItem(BlockRegistry.SILVER_UMA_PEDESTAL.get(), Umapyoi.defaultItemProperties()));
    
    public static final RegistryObject<Item> UMA_PEDESTAL = register("uma_pedestal",
            () -> new BlockItem(BlockRegistry.UMA_PEDESTAL.get(), Umapyoi.defaultItemProperties()));

    public static final RegistryObject<Item> UMA_STATUE = register("uma_statue",
            () -> new BlockItem(BlockRegistry.UMA_STATUES.get(), Umapyoi.defaultItemProperties()));
    
    public static final RegistryObject<Item> THREE_GODDESS = register("three_goddess",
            () -> new BlockItem(BlockRegistry.THREE_GODDESS.get(), Umapyoi.defaultItemProperties()));

    public static final RegistryObject<Item> TRAINING_FACILITY = register("training_facility",
            () -> new BlockItem(BlockRegistry.TRAINING_FACILITY.get(), Umapyoi.defaultItemProperties()));

    public static final RegistryObject<Item> SKILL_LEARNING_TABLE = register("skill_learning_table",
            () -> new BlockItem(BlockRegistry.SKILL_LEARNING_TABLE.get(), Umapyoi.defaultItemProperties()));

    public static final RegistryObject<Item> REGISTER_LECTERN = register("register_lectern",
            () -> new BlockItem(BlockRegistry.REGISTER_LECTERN.get(), Umapyoi.defaultItemProperties()));
    
    public static final RegistryObject<Item> DISASSEMBLY_BLOCK = register("disassembly_block",
            () -> new BlockItem(BlockRegistry.DISASSEMBLY_BLOCK.get(), Umapyoi.defaultItemProperties()));
    
    public static final RegistryObject<Item> UMA_SELECT_BLOCK = register("uma_select_block",
            () -> new BlockItem(BlockRegistry.UMA_SELECT_BLOCK.get(), Umapyoi.defaultItemProperties()));

    public static final RegistryObject<Item> BLANK_UMA_SOUL = register("blank_uma_soul", FadedUmaSoulItem::new);

    public static final RegistryObject<Item> UMA_SOUL = register("uma_soul", UmaSoulItem::new);
    public static final RegistryObject<Item> UMA_FACTOR_ITEM = register("uma_factor_item", UmaFactorContainerItem::new);

    public static final RegistryObject<Item> SUMMER_UNIFORM = register("summer_uniform", UmaSuitItem::new);
    public static final RegistryObject<Item> WINTER_UNIFORM = register("winter_uniform", UmaSuitItem::new);
    public static final RegistryObject<Item> TRAINNING_SUIT = register("trainning_suit", UmaSuitItem::new);
    public static final RegistryObject<Item> SWIMSUIT = register("swimsuit", UmaSuitItem::new);
//    public static final RegistryObject<Item> KINDERGARTEN_UNIFORM = register("kindergarten_uniform", UmaSuitItem::new);

    public static final RegistryObject<Item> JEWEL = register("jewel", ItemRegistry::newMaterial);

    public static final RegistryObject<Item> BLANK_TICKET = register("blank_ticket", ItemRegistry::newMaterial);
    public static final RegistryObject<Item> UMA_TICKET = register("uma_ticket", UmaTicketItem::new);
    public static final RegistryObject<Item> SR_UMA_TICKET = register("sr_uma_ticket", UmaTicketItem::new);
    public static final RegistryObject<Item> SSR_UMA_TICKET = register("ssr_uma_ticket", UmaTicketItem::new);
    public static final RegistryObject<Item> CARD_TICKET = register("card_ticket", UmaTicketItem::new);
    public static final RegistryObject<Item> SR_CARD_TICKET = register("sr_card_ticket", UmaTicketItem::new);
    public static final RegistryObject<Item> SSR_CARD_TICKET = register("ssr_card_ticket", UmaTicketItem::new);
    
    public static final RegistryObject<Item> CRYSTAL_SILVER = register("crystal_silver", ItemRegistry::newMaterial);
    public static final RegistryObject<Item> CRYSTAL_GOLD = register("crystal_gold", ItemRegistry::newMaterial);
    public static final RegistryObject<Item> CRYSTAL_RAINBOW = register("crystal_rainbow", ItemRegistry::newMaterial);
    public static final RegistryObject<Item> HORSESHOE_SILVER = register("horseshoe_silver", ItemRegistry::newMaterial);
    public static final RegistryObject<Item> HORSESHOE_GOLD = register("horseshoe_gold", ItemRegistry::newMaterial);
    public static final RegistryObject<Item> HORSESHOE_RAINBOW = register("horseshoe_rainbow", ItemRegistry::newMaterial);

    public static final RegistryObject<Item> SPEED_LOW_ITEM = register("speed_low_item", () -> {
        return new TrainingItem(SupportType.SPEED, TrainingSupportRegistry.SPEED_SUPPORT, 1);
    });
    public static final RegistryObject<Item> SPEED_MID_ITEM = register("speed_mid_item", () -> {
        return new TrainingItem(SupportType.SPEED, TrainingSupportRegistry.SPEED_SUPPORT, 2);
    });
    public static final RegistryObject<Item> SPEED_HIGH_ITEM = register("speed_high_item", () -> {
        return new TrainingItem(SupportType.SPEED, TrainingSupportRegistry.SPEED_SUPPORT, 3);
    });

    public static final RegistryObject<Item> STAMINA_LOW_ITEM = register("stamina_low_item", () -> {
        return new TrainingItem(SupportType.STAMINA, TrainingSupportRegistry.STAMINA_SUPPORT, 1);
    });
    public static final RegistryObject<Item> STAMINA_MID_ITEM = register("stamina_mid_item", () -> {
        return new TrainingItem(SupportType.STAMINA, TrainingSupportRegistry.STAMINA_SUPPORT, 2);
    });
    public static final RegistryObject<Item> STAMINA_HIGH_ITEM = register("stamina_high_item", () -> {
        return new TrainingItem(SupportType.STAMINA, TrainingSupportRegistry.STAMINA_SUPPORT, 3);
    });

    public static final RegistryObject<Item> STRENGTH_LOW_ITEM = register("strength_low_item", () -> {
        return new TrainingItem(SupportType.STRENGTH, TrainingSupportRegistry.STRENGTH_SUPPORT, 1);
    });
    public static final RegistryObject<Item> STRENGTH_MID_ITEM = register("strength_mid_item", () -> {
        return new TrainingItem(SupportType.STRENGTH, TrainingSupportRegistry.STRENGTH_SUPPORT, 2);
    });
    public static final RegistryObject<Item> STRENGTH_HIGH_ITEM = register("strength_high_item", () -> {
        return new TrainingItem(SupportType.STRENGTH, TrainingSupportRegistry.STRENGTH_SUPPORT, 3);
    });

    public static final RegistryObject<Item> MENTALITY_LOW_ITEM = register("mentality_low_item", () -> {
        return new TrainingItem(SupportType.GUTS, TrainingSupportRegistry.GUTS_SUPPORT, 1);
    });
    public static final RegistryObject<Item> MENTALITY_MID_ITEM = register("mentality_mid_item", () -> {
        return new TrainingItem(SupportType.GUTS, TrainingSupportRegistry.GUTS_SUPPORT, 2);
    });
    public static final RegistryObject<Item> MENTALITY_HIGH_ITEM = register("mentality_high_item", () -> {
        return new TrainingItem(SupportType.GUTS, TrainingSupportRegistry.GUTS_SUPPORT, 3);
    });

    public static final RegistryObject<Item> WISDOM_LOW_ITEM = register("wisdom_low_item", () -> {
        return new TrainingItem(SupportType.WISDOM, TrainingSupportRegistry.WISDOM_SUPPORT, 1);
    });
    public static final RegistryObject<Item> WISDOM_MID_ITEM = register("wisdom_mid_item", () -> {
        return new TrainingItem(SupportType.WISDOM, TrainingSupportRegistry.WISDOM_SUPPORT, 2);
    });
    public static final RegistryObject<Item> WISDOM_HIGH_ITEM = register("wisdom_high_item", () -> {
        return new TrainingItem(SupportType.WISDOM, TrainingSupportRegistry.WISDOM_SUPPORT, 3);
    });

    public static final RegistryObject<Item> SKILL_BOOK = register("skill_book", SkillBookItem::new);

    public static final RegistryObject<Item> SUPPORT_CARD = register("support_card", SupportCardItem::new);

    public static final RegistryObject<Item> HACHIMI_MID = register("hachimi_mid",
            () -> new UmaDrinkItem(UmaStatusUtils::addMotivation,
                    FoodInfo.builder().name("hachimi_mid").alwaysEat().amountAndCalories(2, 0.6F).water(30F)
                            .nutrients(2F, 2F, 0F, 0F, 0F).decayModifier(1.5F).heatCapacity(1F).cookingTemp(480F)
                            .build()));

    public static final RegistryObject<Item> HACHIMI_BIG = register("hachimi_big", () -> new UmaDrinkItem(status -> {
        UmaStatusUtils.addMotivation(status);
        UmaStatusUtils.addMotivation(status);
    }, FoodInfo.builder().name("hachimi_big").alwaysEat().amountAndCalories(4, 0.8F).water(30F)
            .nutrients(4F, 4F, 0F, 0F, 0F).decayModifier(1.5F).heatCapacity(1F).cookingTemp(480F).build()));

    public static final RegistryObject<Item> ROYAL_BITTER = register("royal_bitter",
            () -> new UmaDrinkItem(EnergyDrinkMethods::royalBitter,
                    FoodInfo.builder().name("royal_bitter").alwaysEat().amountAndCalories(2, 0.6F).water(30F)
                            .nutrients(2F, 2F, 0F, 0F, 0F)
                            .heatCapacity(1F).cookingTemp(480F).build()));

    public static final RegistryObject<Item> CUPCAKE = register("cupcake",
            () -> new UmaFoodItem(UmaStatusUtils::addMotivation,
                    FoodInfo.builder().name("cupcake").amountAndCalories(5, 0.6F).water(0F)
                            .nutrients(2F, 2F, 0F, 0F, 0F).decayModifier(1.5F).heatCapacity(1F).cookingTemp(480F)
                            .build()));

    public static final RegistryObject<Item> SWEET_CUPCAKE = register("sweet_cupcake", () -> new UmaFoodItem(status -> {
        UmaStatusUtils.addMotivation(status);
        UmaStatusUtils.addMotivation(status);
    }, FoodInfo.builder().name("sweet_cupcake").amountAndCalories(7, 0.6F).water(0F).nutrients(4F, 4F, 0F, 0F, 0F)
            .decayModifier(1.5F).heatCapacity(1F).cookingTemp(480F).build()));
    
    public static final RegistryObject<Item> SMALL_ENERGY_DRINK = register("small_energy_drink",
            () -> new UmaDrinkItem(EnergyDrinkMethods::smallEnergy,
                    FoodInfo.builder().name("small_energy_drink").alwaysEat().amountAndCalories(2, 0.6F).water(30F)
                            .nutrients(2F, 2F, 0F, 0F, 0F)
                            .heatCapacity(1F).cookingTemp(480F).build()));
    
    public static final RegistryObject<Item> MEDIUM_ENERGY_DRINK = register("medium_energy_drink",
            () -> new UmaDrinkItem(EnergyDrinkMethods::mediumEnergy,
                    FoodInfo.builder().name("medium_energy_drink").alwaysEat().amountAndCalories(2, 0.6F).water(30F)
                            .nutrients(2F, 2F, 0F, 0F, 0F)
                            .heatCapacity(1F).cookingTemp(480F).build()));
    
    public static final RegistryObject<Item> LARGE_ENERGY_DRINK = register("large_energy_drink",
            () -> new UmaDrinkItem(EnergyDrinkMethods::largeEnergy,
                    FoodInfo.builder().name("large_energy_drink").alwaysEat().amountAndCalories(2, 0.6F).water(30F)
                            .nutrients(2F, 2F, 0F, 0F, 0F)
                            .heatCapacity(1F).cookingTemp(480F).build()));

    private static <V extends Item> RegistryObject<V> register(String name, Supplier<V> item) {
        return ITEMS.register(name, item);
    }

    private static Item newMaterial() {
        return new Item(Umapyoi.defaultItemProperties());
    }
}
