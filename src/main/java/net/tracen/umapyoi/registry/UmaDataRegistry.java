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
                .property(1, 1, 1, 1, 1).build(), new ResourceLocation(Umapyoi.MODID, "basic_pace"));
    });

    public static final RegistryObject<UmaData> GOLD_SHIP = UMA_DATA.register("gold_ship", () -> {
        return new UmaData(UmaStatus.builder(new ResourceLocation(Umapyoi.MODID, "gold_ship"))
                .property(1, 1, 1, 1, 1).build(), new ResourceLocation(Umapyoi.MODID, "basic_pace"));
    });

    public static final RegistryObject<UmaData> SPECIAL_WEEK = UMA_DATA.register("special_week", () -> {
        return new UmaData(UmaStatus.builder(new ResourceLocation(Umapyoi.MODID, "special_week"))
                .property(1, 1, 1, 1, 1).build(), new ResourceLocation(Umapyoi.MODID, "basic_pace"));
    });

    public static final RegistryObject<UmaData> TOKAI_TEIO = UMA_DATA.register("tokai_teio", () -> {
        return new UmaData(UmaStatus.builder(new ResourceLocation(Umapyoi.MODID, "tokai_teio"))
                .property(1, 1, 1, 1, 1).build(), new ResourceLocation(Umapyoi.MODID, "basic_pace"),
                true);
    });

    public static final RegistryObject<UmaData> OGURI_CAP = UMA_DATA.register("oguri_cap", () -> {
        return new UmaData(UmaStatus.builder(new ResourceLocation(Umapyoi.MODID, "oguri_cap"))
                .property(1, 1, 1, 1, 1).build(), new ResourceLocation(Umapyoi.MODID, "basic_pace"));
    });

    public static final RegistryObject<UmaData> SAKURA_CHIYONO_O = UMA_DATA.register("sakura_sayono_o", () -> {
        return new UmaData(
                UmaStatus.builder(new ResourceLocation(Umapyoi.MODID, "sakura_sayono_o"))
                        .property(1, 1, 1, 1, 1).build(),
                new ResourceLocation(Umapyoi.MODID, "basic_pace"));
    });

    public static final RegistryObject<UmaData> OGURI_CAP_XMAS = UMA_DATA.register("oguri_cap_xmas", () -> {
        return new UmaData(
                UmaStatus.builder(new ResourceLocation(Umapyoi.MODID, "oguri_cap_xmas"))
                        .property(1, 1, 1, 1, 1).build(),
                new ResourceLocation(Umapyoi.MODID, "basic_pace"));
    });

    public static final RegistryObject<UmaData> AGNUS_TACHYON = UMA_DATA.register("agnus_tachyon", () -> {
        return new UmaData(
                UmaStatus.builder(new ResourceLocation(Umapyoi.MODID, "agnus_tachyon"))
                        .property(1, 1, 1, 1, 1).build(),
                new ResourceLocation(Umapyoi.MODID, "basic_pace"));
    });

    public static final RegistryObject<UmaData> HARU_URARA = UMA_DATA.register("haru_urara", () -> {
        return new UmaData(UmaStatus.builder(new ResourceLocation(Umapyoi.MODID, "haru_urara"))
                .property(1, 1, 1, 1, 1).build(), new ResourceLocation(Umapyoi.MODID, "basic_pace"),
                true);
    });
    
    public static final RegistryObject<UmaData> TAMAMO_CROSS = UMA_DATA.register("tamamo_cross", () -> {
        return new UmaData(UmaStatus.builder(new ResourceLocation(Umapyoi.MODID, "tamamo_cross"))
                .property(1, 1, 1, 1, 1).build(), new ResourceLocation(Umapyoi.MODID, "basic_pace"));
    });

    public static final RegistryObject<UmaData> SEIUN_SKY = UMA_DATA.register("seiun_sky", () -> {
        return new UmaData(UmaStatus.builder(new ResourceLocation(Umapyoi.MODID, "seiun_sky"))
                .property(1, 1, 1, 1, 1).build(), new ResourceLocation(Umapyoi.MODID, "basic_pace"));
    });
    
    public static final RegistryObject<UmaData> MATIKANEFUKUKITARU = UMA_DATA.register("matikanefukukitaru", () -> {
        return new UmaData(UmaStatus.builder(new ResourceLocation(Umapyoi.MODID, "matikanefukukitaru"))
                .property(1, 1, 1, 1, 1).build(), new ResourceLocation(Umapyoi.MODID, "basic_pace"));
    });
    
    public static final RegistryObject<UmaData> RICE_SHOWER = UMA_DATA.register("rice_shower", () -> {
        return new UmaData(UmaStatus.builder(new ResourceLocation(Umapyoi.MODID, "rice_shower"))
                .property(1, 1, 1, 1, 1).build(), new ResourceLocation(Umapyoi.MODID, "basic_pace"));
    });
    
    public static final RegistryObject<UmaData> VODKA = UMA_DATA.register("vodka", () -> {
        return new UmaData(UmaStatus.builder(new ResourceLocation(Umapyoi.MODID, "vodka"))
                .property(1, 1, 1, 1, 1).build(), new ResourceLocation(Umapyoi.MODID, "basic_pace"));
    });
    
    public static final RegistryObject<UmaData> SAKURA_BAKUSHIN_O = UMA_DATA.register("sakura_bakushin_o", () -> {
        return new UmaData(UmaStatus.builder(new ResourceLocation(Umapyoi.MODID, "sakura_bakushin_o"))
                .property(1, 1, 1, 1, 1).build(), new ResourceLocation(Umapyoi.MODID, "basic_pace"));
    });
}
