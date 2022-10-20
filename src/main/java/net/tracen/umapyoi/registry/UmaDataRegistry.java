package net.tracen.umapyoi.registry;

import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.registry.umadata.UmaStatus;

public class UmaDataRegistry {
    public static final DeferredRegister<UmaData> UMA_DATA = DeferredRegister.create(UmaData.REGISTRY_KEY,
            Umapyoi.MODID);
    public static final Supplier<IForgeRegistry<UmaData>> UMA_DATA_REGISTRY = UMA_DATA.makeRegistry(UmaData.class,
            () -> new RegistryBuilder<UmaData>().disableSaving().dataPackRegistry(UmaData.CODEC, UmaData.CODEC));

    public static final RegistryObject<UmaData> COMMON_UMA = UMA_DATA.register("common_uma", () -> {
        return new UmaData(UmaStatus.builder(new ResourceLocation(Umapyoi.MODID, "common_uma"))
                .property(100, 100, 100, 100, 100).build(), new int[] { 0, 0, 0, 0, 0 });
    });
    
    public static final RegistryObject<UmaData> TEST_MODEL = UMA_DATA.register("test_model", () -> {
        return new UmaData(UmaStatus.builder(new ResourceLocation(Umapyoi.MODID, "test_model"))
                .property(100, 100, 100, 100, 100).build(), new int[] { 0, 0, 0, 0, 0 });
    });
    
    public static final RegistryObject<UmaData> TAMAMO_CROSS = UMA_DATA.register("tamamo_cross", () -> {
        return new UmaData(UmaStatus.builder(new ResourceLocation(Umapyoi.MODID, "tamamo_cross"))
                .property(100, 100, 100, 100, 100).build(), new int[] { 0, 0, 0, 0, 0 });
    });

    public static final RegistryObject<UmaData> GOLD_SHIP = UMA_DATA.register("gold_ship", () -> {
        return new UmaData(UmaStatus.builder(new ResourceLocation(Umapyoi.MODID, "gold_ship"))
                .property(100, 100, 100, 100, 100).build(), new int[] { 0, 0, 0, 0, 0 });
    });

    public static final RegistryObject<UmaData> SPECIAL_WEEK = UMA_DATA.register("special_week", () -> {
        return new UmaData(UmaStatus.builder(new ResourceLocation(Umapyoi.MODID, "special_week"))
                .property(100, 100, 100, 100, 100).build(), new int[] { 0, 0, 0, 0, 0 });
    });

    public static final RegistryObject<UmaData> TOKAI_TEIO = UMA_DATA.register("tokai_teio", () -> {
        return new UmaData(UmaStatus.builder(new ResourceLocation(Umapyoi.MODID, "tokai_teio"))
                .property(100, 100, 100, 100, 100).build(), new int[] { 0, 0, 0, 0, 0 }, true);
    });

    public static final RegistryObject<UmaData> OGURI_CAP = UMA_DATA.register("oguri_cap", () -> {
        return new UmaData(UmaStatus.builder(new ResourceLocation(Umapyoi.MODID, "oguri_cap"))
                .property(100, 100, 100, 100, 100).build(), new int[] { 0, 0, 0, 0, 0 });
    });

    public static final RegistryObject<UmaData> SAKURA_CHIYONO_O = UMA_DATA.register("sakura_sayono_o", () -> {
        return new UmaData(UmaStatus.builder(new ResourceLocation(Umapyoi.MODID, "sakura_sayono_o"))
                .property(100, 100, 100, 100, 100).build(), new int[] { 0, 0, 0, 0, 0 });
    });

    public static final RegistryObject<UmaData> OGURI_CAP_XMAS = UMA_DATA.register("oguri_cap_xmas", () -> {
        return new UmaData(UmaStatus.builder(new ResourceLocation(Umapyoi.MODID, "oguri_cap_xmas"))
                .property(100, 100, 100, 100, 100).build(), new int[] { 0, 0, 0, 0, 0 });
    });

    public static final RegistryObject<UmaData> AGNUS_TACHYON = UMA_DATA.register("agnus_tachyon", () -> {
        return new UmaData(UmaStatus.builder(new ResourceLocation(Umapyoi.MODID, "agnus_tachyon"))
                .property(100, 100, 100, 100, 100).build(), new int[] { 0, 0, 0, 0, 0 });
    });

    public static final RegistryObject<UmaData> HARU_URARA = UMA_DATA.register("haru_urara", () -> {
        return new UmaData(UmaStatus.builder(new ResourceLocation(Umapyoi.MODID, "haru_urara"))
                .property(100, 100, 100, 100, 100).build(), new int[] { 0, 0, 0, 0, 0 }, true);
    });
}
