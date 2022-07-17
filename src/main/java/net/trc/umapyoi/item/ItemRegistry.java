package net.trc.umapyoi.item;

import java.util.function.Supplier;

import cn.mcmod_mmf.mmlib.item.ItemDrinkBase;
import cn.mcmod_mmf.mmlib.item.info.FoodInfo;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.trc.umapyoi.Umapyoi;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Umapyoi.MODID);
    public static final RegistryObject<Item> HACHIMI_MID = register("hachimi_mid", 
            () -> new ItemDrinkBase(
                    Umapyoi.defaultItemProperties(),
                    FoodInfo.builder().name("hachimi_mid")
                    .amountAndCalories(4, 0.6F).water(30F)
                    .nutrients(2F, 2F, 0F, 0F, 0F)
                    .decayModifier(1.5F).heatCapacity(1F)
                    .cookingTemp(480F).build()
            ));
    
    public static final RegistryObject<Item> HACHIMI_BIG = register("hachimi_big", 
            () -> new ItemDrinkBase(
                    Umapyoi.defaultItemProperties(),
                    FoodInfo.builder().name("hachimi_big")
                    .amountAndCalories(8, 0.8F).water(30F)
                    .nutrients(4F, 4F, 0F, 0F, 0F)
                    .decayModifier(1.5F).heatCapacity(1F)
                    .cookingTemp(480F).build()
            ));
    
    public static final RegistryObject<Item> UMA_SOUL = register("uma_soul", UmaSoulItem::new);

    public static final RegistryObject<Item> TRAINNING_SUIT = register("trainning_suit", UmaSuitItem::new);
    
    private static <V extends Item> RegistryObject<V> register(String name, Supplier<V> item) {
        return ITEMS.register(name, item);
    }
}
