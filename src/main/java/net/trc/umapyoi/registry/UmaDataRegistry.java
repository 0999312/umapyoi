package net.trc.umapyoi.registry;

import com.google.common.collect.Lists;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.trc.umapyoi.Umapyoi;

public class UmaDataRegistry {
    public static final DeferredRegister<UmaData> UMA_DATA = DeferredRegister.create(UmaData.REGISTRY_KEY, Umapyoi.MODID);
    
    public static final RegistryObject<UmaData> COMMON_UMA = UMA_DATA.register("common_uma", ()->{
        return new UmaData("common_uma", Lists.newArrayList(1,1,1,1,1), Lists.newArrayList(1200,1200,1200,1200,1200));
    });
}
