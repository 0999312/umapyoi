package net.tracen.umapyoi.item;

import java.util.function.Supplier;

import cn.mcmod_mmf.mmlib.item.ItemDrinkBase;
import cn.mcmod_mmf.mmlib.item.ItemFoodBase;
import cn.mcmod_mmf.mmlib.item.info.FoodInfo;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.api.UmaStatusUtils;
import net.tracen.umapyoi.registry.umadata.UmaStatus;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Umapyoi.MODID);

    public static final RegistryObject<Item> BLANK_UMA_SOUL = register("blank_uma_soul",
            () -> new Item(Umapyoi.defaultItemProperties()));
    public static final RegistryObject<Item> UMA_SOUL = register("uma_soul", UmaSoulItem::new);

    public static final RegistryObject<Item> SUMMER_UNIFORM = register("summer_uniform", UmaSuitItem::new);
    public static final RegistryObject<Item> WINTER_UNIFORM = register("winter_uniform", UmaSuitItem::new);
    public static final RegistryObject<Item> TRAINNING_SUIT = register("trainning_suit", UmaSuitItem::new);

    public static final RegistryObject<Item> JEWEL = register("jewel", () -> new Item(Umapyoi.defaultItemProperties()));

    public static final RegistryObject<Item> SPEED_LOW_ITEM = register("speed_low_item",
            () -> new StatusUpItem(Umapyoi.defaultItemProperties(),
                    UmaStatusUtils.addPropety(UmaStatus.SPEED, UmapyoiConfig.ITEM_LOW_VALUE.get())));
    public static final RegistryObject<Item> SPEED_MID_ITEM = register("speed_mid_item",
            () -> new StatusUpItem(Umapyoi.defaultItemProperties(),
                    UmaStatusUtils.addPropety(UmaStatus.SPEED, UmapyoiConfig.ITEM_MID_VALUE.get())));
    public static final RegistryObject<Item> SPEED_HIGH_ITEM = register("speed_high_item",
            () -> new StatusUpItem(Umapyoi.defaultItemProperties(),
                    UmaStatusUtils.addPropety(UmaStatus.SPEED, UmapyoiConfig.ITEM_HIGH_VALUE.get())));

    public static final RegistryObject<Item> STAMINA_LOW_ITEM = register("stamina_low_item",
            () -> new StatusUpItem(Umapyoi.defaultItemProperties(),
                    UmaStatusUtils.addPropety(UmaStatus.STAMINA, UmapyoiConfig.ITEM_LOW_VALUE.get())));
    public static final RegistryObject<Item> STAMINA_MID_ITEM = register("stamina_mid_item",
            () -> new StatusUpItem(Umapyoi.defaultItemProperties(),
                    UmaStatusUtils.addPropety(UmaStatus.STAMINA, UmapyoiConfig.ITEM_MID_VALUE.get())));
    public static final RegistryObject<Item> STAMINA_HIGH_ITEM = register("stamina_high_item",
            () -> new StatusUpItem(Umapyoi.defaultItemProperties(),
                    UmaStatusUtils.addPropety(UmaStatus.STAMINA, UmapyoiConfig.ITEM_HIGH_VALUE.get())));

    public static final RegistryObject<Item> STRENGTH_LOW_ITEM = register("strength_low_item",
            () -> new StatusUpItem(Umapyoi.defaultItemProperties(),
                    UmaStatusUtils.addPropety(UmaStatus.STRENGTH, UmapyoiConfig.ITEM_LOW_VALUE.get())));
    public static final RegistryObject<Item> STRENGTH_MID_ITEM = register("strength_mid_item",
            () -> new StatusUpItem(Umapyoi.defaultItemProperties(),
                    UmaStatusUtils.addPropety(UmaStatus.STRENGTH, UmapyoiConfig.ITEM_MID_VALUE.get())));
    public static final RegistryObject<Item> STRENGTH_HIGH_ITEM = register("strength_high_item",
            () -> new StatusUpItem(Umapyoi.defaultItemProperties(),
                    UmaStatusUtils.addPropety(UmaStatus.STRENGTH, UmapyoiConfig.ITEM_HIGH_VALUE.get())));

    public static final RegistryObject<Item> MENTALITY_LOW_ITEM = register("mentality_low_item",
            () -> new StatusUpItem(Umapyoi.defaultItemProperties(),
                    UmaStatusUtils.addPropety(UmaStatus.GUTS, UmapyoiConfig.ITEM_LOW_VALUE.get())));
    public static final RegistryObject<Item> MENTALITY_MID_ITEM = register("mentality_mid_item",
            () -> new StatusUpItem(Umapyoi.defaultItemProperties(),
                    UmaStatusUtils.addPropety(UmaStatus.GUTS, UmapyoiConfig.ITEM_MID_VALUE.get())));
    public static final RegistryObject<Item> MENTALITY_HIGH_ITEM = register("mentality_high_item",
            () -> new StatusUpItem(Umapyoi.defaultItemProperties(),
                    UmaStatusUtils.addPropety(UmaStatus.GUTS, UmapyoiConfig.ITEM_HIGH_VALUE.get())));

    public static final RegistryObject<Item> WISDOM_LOW_ITEM = register("wisdom_low_item",
            () -> new StatusUpItem(Umapyoi.defaultItemProperties(),
                    UmaStatusUtils.addPropety(UmaStatus.WISDOM, UmapyoiConfig.ITEM_LOW_VALUE.get())));
    public static final RegistryObject<Item> WISDOM_MID_ITEM = register("wisdom_mid_item",
            () -> new StatusUpItem(Umapyoi.defaultItemProperties(),
                    UmaStatusUtils.addPropety(UmaStatus.WISDOM, UmapyoiConfig.ITEM_MID_VALUE.get())));
    public static final RegistryObject<Item> WISDOM_HIGH_ITEM = register("wisdom_high_item",
            () -> new StatusUpItem(Umapyoi.defaultItemProperties(),
                    UmaStatusUtils.addPropety(UmaStatus.WISDOM, UmapyoiConfig.ITEM_HIGH_VALUE.get())));

    public static final RegistryObject<Item> HACHIMI_MID = register("hachimi_mid",
            () -> new ItemDrinkBase(Umapyoi.defaultItemProperties(),
                    FoodInfo.builder().name("hachimi_mid").amountAndCalories(4, 0.6F).water(30F)
                            .nutrients(2F, 2F, 0F, 0F, 0F).decayModifier(1.5F).heatCapacity(1F).cookingTemp(480F)
                            .build()));

    public static final RegistryObject<Item> HACHIMI_BIG = register("hachimi_big",
            () -> new ItemDrinkBase(Umapyoi.defaultItemProperties(),
                    FoodInfo.builder().name("hachimi_big").amountAndCalories(8, 0.8F).water(30F)
                            .nutrients(4F, 4F, 0F, 0F, 0F).decayModifier(1.5F).heatCapacity(1F).cookingTemp(480F)
                            .build()));

    public static final RegistryObject<Item> ROYAL_BITTER = register("royal_bitter", () -> new ItemDrinkBase(
            Umapyoi.defaultItemProperties(),
            FoodInfo.builder().name("royal_bitter").amountAndCalories(4, 0.6F).water(30F).nutrients(4F, 4F, 0F, 0F, 0F)
                    .addEffect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600), 1F)
                    .addEffect(() -> new MobEffectInstance(MobEffects.CONFUSION, 20), 0.5F).decayModifier(1.5F)
                    .heatCapacity(1F).cookingTemp(480F).build()));

    public static final RegistryObject<Item> CUPCAKE = register("cupcake",
            () -> new ItemFoodBase(Umapyoi.defaultItemProperties(),
                    FoodInfo.builder().name("cupcake").amountAndCalories(5, 0.6F).water(0F)
                            .nutrients(2F, 2F, 0F, 0F, 0F).decayModifier(1.5F).heatCapacity(1F).cookingTemp(480F)
                            .build()));

    public static final RegistryObject<Item> SWEET_CUPCAKE = register("sweet_cupcake",
            () -> new ItemFoodBase(Umapyoi.defaultItemProperties(),
                    FoodInfo.builder().name("sweet_cupcake").amountAndCalories(7, 0.6F).water(0F)
                            .nutrients(4F, 4F, 0F, 0F, 0F).decayModifier(1.5F).heatCapacity(1F).cookingTemp(480F)
                            .build()));

    private static <V extends Item> RegistryObject<V> register(String name, Supplier<V> item) {
        return ITEMS.register(name, item);
    }

}
