package net.trc.umapyoi.registry;

import java.util.function.Supplier;

import com.google.common.collect.Lists;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import net.trc.umapyoi.Umapyoi;

public class UmaDataRegistry {
    public static final DeferredRegister<UmaData> UMA_DATA = DeferredRegister.create(UmaData.REGISTRY_KEY, Umapyoi.MODID);
    public static final Supplier<IForgeRegistry<UmaData>> UMA_DATA_REGISTRY = UMA_DATA.makeRegistry(UmaData.class,
            () -> new RegistryBuilder<UmaData>().disableSaving().dataPackRegistry(UmaData.CODEC, UmaData.CODEC));
    public static final RegistryObject<UmaData> COMMON_UMA = UMA_DATA.register("common_uma", ()->{
        return new UmaData("common_uma", Lists.newArrayList(1,1,1,1,1), Lists.newArrayList(1200,1200,1200,1200,1200));
    });
    public static final RegistryObject<UmaData> GOLD_SHIP = UMA_DATA.register("gold_ship", ()->{
        return new UmaData("gold_ship", Lists.newArrayList(100,100,100,100,100), Lists.newArrayList(1200,1200,1200,1200,1200));
    });
    public static final RegistryObject<UmaData> SPECIAL_WEEK = UMA_DATA.register("special_week", ()->{
        return new UmaData("special_week", Lists.newArrayList(100,100,100,100,100), Lists.newArrayList(1200,1200,1200,1200,1200));
    });
    public static final RegistryObject<UmaData> TOKAI_TEIO = UMA_DATA.register("tokai_teio", ()->{
        return new UmaData("tokai_teio", Lists.newArrayList(100,100,100,100,100), Lists.newArrayList(1200,1200,1200,1200,1200), true);
    });
    
    public static final RegistryObject<UmaData> OGURI_CAP = UMA_DATA.register("oguri_cap", ()->{
        return new UmaData("oguri_cap", Lists.newArrayList(100,100,100,100,100), Lists.newArrayList(1200,1200,1200,1200,1200));
    });
    public static final RegistryObject<UmaData> SAKURA_CHIYONO_O = UMA_DATA.register("sakura_sayono_o", ()->{
        return new UmaData("sakura_sayono_o", Lists.newArrayList(100,100,100,100,100), Lists.newArrayList(1200,1200,1200,1200,1200), true);
    });
    
    public static final RegistryObject<UmaData> OGURI_CAP_XMAS = UMA_DATA.register("oguri_cap_xmas", ()->{
        return new UmaData("oguri_cap_xmas", Lists.newArrayList(120,100,100,100,100), Lists.newArrayList(1200,1200,1200,1200,1200));
    });
    
    public static final RegistryObject<UmaData> AGNUS_TACHYON = UMA_DATA.register("agnus_tachyon", ()->{
        return new UmaData("agnus_tachyon", Lists.newArrayList(120,100,100,100,100), Lists.newArrayList(1200,1200,1200,1200,1200));
    });
}
