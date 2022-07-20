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

}
