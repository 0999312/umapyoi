package net.tracen.umapyoi.registry;

import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.utils.GachaRanking;

public class UmaDataRegistry {
    public static final DeferredRegister<UmaData> UMA_DATA = DeferredRegister.create(UmaData.REGISTRY_KEY,
            Umapyoi.MODID);
    public static final Supplier<IForgeRegistry<UmaData>> UMA_DATA_REGISTRY = UMA_DATA.makeRegistry(UmaData.class,
            () -> new RegistryBuilder<UmaData>().disableSaving().dataPackRegistry(UmaData.CODEC, UmaData.CODEC));

    public static final RegistryObject<UmaData> COMMON_UMA = UMA_DATA.register("common_uma",
            () -> UmaDataRegistry.createNewUmamusume("common_uma", GachaRanking.R));
    
    public static final RegistryObject<UmaData> COMMON_UMA_A = UMA_DATA.register("common_uma_a",
            () -> UmaDataRegistry.createNewUmamusume("common_uma_a", GachaRanking.R));
    
    public static final RegistryObject<UmaData> COMMON_UMA_B = UMA_DATA.register("common_uma_b",
            () -> UmaDataRegistry.createNewUmamusume("common_uma_b", GachaRanking.R));
    
    public static final RegistryObject<UmaData> COMMON_UMA_C = UMA_DATA.register("common_uma_c",
            () -> UmaDataRegistry.createNewUmamusume("common_uma_c", GachaRanking.R));

    public static final RegistryObject<UmaData> GOLD_SHIP = UMA_DATA.register("gold_ship",
            () -> UmaDataRegistry.createNewUmamusume("gold_ship", GachaRanking.SR));

    public static final RegistryObject<UmaData> SPECIAL_WEEK = UMA_DATA.register("special_week",
            () -> UmaDataRegistry.createNewUmamusume("special_week", GachaRanking.SR));

    public static final RegistryObject<UmaData> TOKAI_TEIO = UMA_DATA.register("tokai_teio",
            () -> UmaDataRegistry.createNewUmamusume("tokai_teio", GachaRanking.SR));

    public static final RegistryObject<UmaData> OGURI_CAP = UMA_DATA.register("oguri_cap",
            () -> UmaDataRegistry.createNewUmamusume("oguri_cap", GachaRanking.SR));

    public static final RegistryObject<UmaData> SAKURA_CHIYONO_O = UMA_DATA.register("sakura_chiyono_o",
            () -> UmaDataRegistry.createNewUmamusume("sakura_chiyono_o", GachaRanking.SR));

    public static final RegistryObject<UmaData> OGURI_CAP_XMAS = UMA_DATA.register("oguri_cap_xmas",
            () -> UmaDataRegistry.createNewUmamusume("oguri_cap", GachaRanking.SSR));

    public static final RegistryObject<UmaData> AGNUS_TACHYON = UMA_DATA.register("agnus_tachyon",
            () -> UmaDataRegistry.createNewUmamusume("agnus_tachyon", GachaRanking.SR));

    public static final RegistryObject<UmaData> HARU_URARA = UMA_DATA.register("haru_urara",
            () -> UmaDataRegistry.createNewUmamusume("haru_urara", GachaRanking.SR));

    public static final RegistryObject<UmaData> TAMAMO_CROSS = UMA_DATA.register("tamamo_cross",
            () -> UmaDataRegistry.createNewUmamusume("tamamo_cross", GachaRanking.SR));

    public static final RegistryObject<UmaData> SEIUN_SKY = UMA_DATA.register("seiun_sky",
            () -> UmaDataRegistry.createNewUmamusume("seiun_sky", GachaRanking.SR));

    public static final RegistryObject<UmaData> MATIKANEFUKUKITARU = UMA_DATA.register("matikanefukukitaru",
            () -> UmaDataRegistry.createNewUmamusume("matikanefukukitaru", GachaRanking.SR));

    public static final RegistryObject<UmaData> RICE_SHOWER = UMA_DATA.register("rice_shower",
            () -> UmaDataRegistry.createNewUmamusume("rice_shower", GachaRanking.SR));

    public static final RegistryObject<UmaData> VODKA = UMA_DATA.register("vodka",
            () -> UmaDataRegistry.createNewUmamusume("vodka", GachaRanking.SR));

    public static final RegistryObject<UmaData> SAKURA_BAKUSHIN_O = UMA_DATA.register("sakura_bakushin_o",
            () -> UmaDataRegistry.createNewUmamusume("sakura_bakushin_o", GachaRanking.SR));

    public static final RegistryObject<UmaData> MANHATTAN_CAFE = UMA_DATA.register("manhattan_cafe",
            () -> UmaDataRegistry.createNewUmamusume("manhattan_cafe", GachaRanking.SR));

    public static final RegistryObject<UmaData> MEJIRO_ARDAN = UMA_DATA.register("mejiro_ardan",
            () -> UmaDataRegistry.createNewUmamusume("mejiro_ardan", GachaRanking.SR));

    public static final RegistryObject<UmaData> DAITAKU_HELIOS = UMA_DATA.register("daitaku_helios",
            () -> UmaDataRegistry.createNewUmamusume("daitaku_helios", GachaRanking.SR));

    public static final RegistryObject<UmaData> SWEEP_TOSHO = UMA_DATA.register("sweep_tosho",
            () -> UmaDataRegistry.createNewUmamusume("sweep_tosho", GachaRanking.SR));

    public static final RegistryObject<UmaData> GOLD_CITY = UMA_DATA.register("gold_city",
            () -> UmaDataRegistry.createNewUmamusume("gold_city", GachaRanking.SR));

    public static final RegistryObject<UmaData> GOLD_SHIP_WATER = UMA_DATA.register("gold_ship_water",
            () -> UmaDataRegistry.createNewUmamusume("gold_ship", GachaRanking.SSR));

    public static final RegistryObject<UmaData> MR_CB = UMA_DATA.register("mr_cb",
            () -> UmaDataRegistry.createNewUmamusume("mr_cb", GachaRanking.SR));

    public static final RegistryObject<UmaData> GRASS_WONDER = UMA_DATA.register("grass_wonder",
            () -> UmaDataRegistry.createNewUmamusume("grass_wonder", GachaRanking.SR));

    public static final RegistryObject<UmaData> CURREN_CHAN = UMA_DATA.register("curren_chan",
            () -> UmaDataRegistry.createNewUmamusume("curren_chan", GachaRanking.SR));

    public static final RegistryObject<UmaData> SILENCE_SUZUKA = UMA_DATA.register("silence_suzuka",
            () -> UmaDataRegistry.createNewUmamusume("silence_suzuka", GachaRanking.SR));

    public static final RegistryObject<UmaData> TAMAMO_CROSS_FESTIVAL = UMA_DATA.register("tamamo_cross_festival",
            () -> UmaDataRegistry.createNewUmamusume("tamamo_cross", GachaRanking.SSR));

    public static final RegistryObject<UmaData> ASTON_MACHAN = UMA_DATA.register("aston_machan",
            () -> UmaDataRegistry.createNewUmamusume("aston_machan", GachaRanking.SR));
    
    public static final RegistryObject<UmaData> KITASAN_BLACK = UMA_DATA.register("kitasan_black",
            () -> UmaDataRegistry.createNewUmamusume("kitasan_black", GachaRanking.SR));

    public static final RegistryObject<UmaData> SATONO_DIAMOND = UMA_DATA.register("satono_diamond",
            () -> UmaDataRegistry.createNewUmamusume("satono_diamond", GachaRanking.SR));

    public static final RegistryObject<UmaData> NICE_NATURE = UMA_DATA.register("nice_nature",
            () -> UmaDataRegistry.createNewUmamusume("nice_nature", GachaRanking.SR));

    public static final RegistryObject<UmaData> MAYANO_TOP_GUN = UMA_DATA.register("mayano_top_gun",
            () -> UmaDataRegistry.createNewUmamusume("mayano_top_gun", GachaRanking.SR));
    
    public static final RegistryObject<UmaData> NEO_UNIVERSE = UMA_DATA.register("neo_universe",
            () -> UmaDataRegistry.createNewUmamusume("neo_universe", GachaRanking.SR));
    
    public static final RegistryObject<UmaData> MEISHO_DOTOU = UMA_DATA.register("meisho_dotou",
            () -> UmaDataRegistry.createNewUmamusume("meisho_dotou", GachaRanking.SR));
    
    public static final RegistryObject<UmaData> TAIKI_SHUTTLE = UMA_DATA.register("taiki_shuttle",
            () -> UmaDataRegistry.createNewUmamusume("taiki_shuttle", GachaRanking.SR));

    public static UmaData createNewUmamusume(String name, GachaRanking ranking) {
        return new UmaData(new ResourceLocation(Umapyoi.MODID, name), ranking, new int[] { 1, 1, 1, 1, 1 },
                new int[] { 12, 12, 12, 12, 12 }, new ResourceLocation(Umapyoi.MODID, "basic_pace"));
    }
}
