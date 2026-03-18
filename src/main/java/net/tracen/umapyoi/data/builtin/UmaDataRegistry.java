package net.tracen.umapyoi.data.builtin;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.utils.Aptitude;
import net.tracen.umapyoi.utils.GachaRanking;
import net.tracen.umapyoi.utils.Position;

public class UmaDataRegistry {

    public static final ResourceKey<UmaData> COMMON_UMA = register("common_uma");
    public static final ResourceKey<UmaData> COMMON_UMA_A = register("common_uma_a");
    public static final ResourceKey<UmaData> COMMON_UMA_B = register("common_uma_b");
    public static final ResourceKey<UmaData> COMMON_UMA_C = register("common_uma_c");
    public static final ResourceKey<UmaData> GOLD_SHIP = register("gold_ship");
    public static final ResourceKey<UmaData> SPECIAL_WEEK = register("special_week");
    public static final ResourceKey<UmaData> TOKAI_TEIO = register("tokai_teio");
    public static final ResourceKey<UmaData> OGURI_CAP = register("oguri_cap");
    public static final ResourceKey<UmaData> SAKURA_CHIYONO_O = register("sakura_chiyono_o");
    public static final ResourceKey<UmaData> OGURI_CAP_XMAS = register("oguri_cap_xmas");
    public static final ResourceKey<UmaData> AGNUS_TACHYON = register("agnus_tachyon");
    public static final ResourceKey<UmaData> HARU_URARA = register("haru_urara");
    public static final ResourceKey<UmaData> TAMAMO_CROSS = register("tamamo_cross");
    public static final ResourceKey<UmaData> SEIUN_SKY = register("seiun_sky");
    public static final ResourceKey<UmaData> MATIKANEFUKUKITARU = register("matikanefukukitaru");
    public static final ResourceKey<UmaData> RICE_SHOWER = register("rice_shower");
    public static final ResourceKey<UmaData> VODKA = register("vodka");
    public static final ResourceKey<UmaData> SAKURA_BAKUSHIN_O = register("sakura_bakushin_o");
    public static final ResourceKey<UmaData> SAKURA_BAKUSHIN_O_SPORTS = register("sakura_bakushin_o_sports");
    public static final ResourceKey<UmaData> MANHATTAN_CAFE = register("manhattan_cafe");
    public static final ResourceKey<UmaData> MEJIRO_ARDAN = register("mejiro_ardan");
    public static final ResourceKey<UmaData> DAITAKU_HELIOS = register("daitaku_helios");
    public static final ResourceKey<UmaData> SWEEP_TOSHO = register("sweep_tosho");
    public static final ResourceKey<UmaData> GOLD_CITY = register("gold_city");
    public static final ResourceKey<UmaData> GOLD_SHIP_WATER = register("gold_ship_water");
    public static final ResourceKey<UmaData> MR_CB = register("mr_cb");
    public static final ResourceKey<UmaData> GRASS_WONDER = register("grass_wonder");
    public static final ResourceKey<UmaData> CURREN_CHAN = register("curren_chan");
    public static final ResourceKey<UmaData> SILENCE_SUZUKA = register("silence_suzuka");
    public static final ResourceKey<UmaData> TAMAMO_CROSS_FESTIVAL = register("tamamo_cross_festival");
    public static final ResourceKey<UmaData> ASTON_MACHAN = register("aston_machan");
    public static final ResourceKey<UmaData> KITASAN_BLACK = register("kitasan_black");
    public static final ResourceKey<UmaData> SATONO_DIAMOND = register("satono_diamond");
    public static final ResourceKey<UmaData> NICE_NATURE = register("nice_nature");
    public static final ResourceKey<UmaData> MAYANO_TOP_GUN = register("mayano_top_gun");
    public static final ResourceKey<UmaData> NEO_UNIVERSE = register("neo_universe");
    public static final ResourceKey<UmaData> MEISHO_DOTOU = register("meisho_dotou");
    public static final ResourceKey<UmaData> TAIKI_SHUTTLE = register("taiki_shuttle");
    public static final ResourceKey<UmaData> CURREN_CHAN_DRESS = register("curren_chan_dress");
    public static final ResourceKey<UmaData> MEJIRO_MCQUEEN = register("mejiro_mcqueen");
    public static final ResourceKey<UmaData> COPANO_RICKEY = register("copano_rickey");
    
    public static final ResourceKey<UmaData> SYMBOLI_RUDOLF = register("symboli_rudolf");
    public static final ResourceKey<UmaData> NARITA_TOP_ROAD = register("narita_top_road");
    public static final ResourceKey<UmaData> VENUS_PARK = register("venus_park");
    
    public static final ResourceKey<UmaData> AGNUS_TACHYON_SWIM = register("agnus_tachyon_swim");
    public static final ResourceKey<UmaData> MIHONO_BOURBON = register("mihono_bourbon");
    public static final ResourceKey<UmaData> MATIKANETANNHAUSER = register("matikanetannhauser");
    public static final ResourceKey<UmaData> KAWAKAMI_PRINCESS = register("kawakami_princess");
    public static final ResourceKey<UmaData> TWIN_TURBO = register("twinturbo");
    public static final ResourceKey<UmaData> LITTLE_COCON = register("little_cocon");
    public static final ResourceKey<UmaData> SAKURA_LAUREL = register("sakura_laurel");
    public static final ResourceKey<UmaData> FINE_MOTION = register("fine_motion");
    public static final ResourceKey<UmaData> TM_OPERA_O = register("tm_opera_o");
    public static final ResourceKey<UmaData> ADMIRE_VEGA = register("admire_vega");
    public static final ResourceKey<UmaData> JUNGLE_POCKET = register("jungle_pocket");
    public static final ResourceKey<UmaData> NARITA_TAISHIN = register("narita_taishin");
    public static final ResourceKey<UmaData> GOLD_CITY_AUTUMN = register("gold_city_autumn");
    public static final ResourceKey<UmaData> GRASS_WONDER_UMANET = register("grass_wonder_umanet");
    public static final ResourceKey<UmaData> SATONO_DIAMOND_FRENCH = register("satono_diamond_french");
    public static final ResourceKey<UmaData> SYAMEIMARU_ZHENG = register("syameimaru_zheng");
    public static final ResourceKey<UmaData> STARDUST = register("stardust");
    public static final ResourceKey<UmaData> DUMNHEINT = register("dumnheint");
    public static final ResourceKey<UmaData> DARLEY_ARABIAN = register("darley_arabian");
    public static final ResourceKey<UmaData> GODOLPHIN_BARB = register("godolphin_barb");
    public static final ResourceKey<UmaData> BYERLEY_TURK = register("byerley_turk");
    public static final ResourceKey<UmaData> SMART_FALCON = register("smart_falcon");
    public static final ResourceKey<UmaData> MANHATTAN_CAFE_VALENTINE = register("manhattan_cafe_valentine");
    public static final ResourceKey<UmaData> HISHI_MIRACLE = register("hishi_miracle");
    public static final ResourceKey<UmaData> DAIWA_SCARLET = register("daiwa_scarlet");
    public static final ResourceKey<UmaData> WIN_VARIATION = register("win_variation");
    
    public static final ResourceKey<UmaData> EL_CONDOR_PASA = register("el_condor_pasa");
    
    public static final ResourceKey<UmaData> HOKKO_TARUMAE = register("hokko_tarumae");
    public static final ResourceKey<UmaData> KING_HALO = register("king_halo");
    public static final ResourceKey<UmaData> KING_HALO_CHEER = register("king_halo_cheer");
    public static final ResourceKey<UmaData> KING_HALO_WEDDING = register("king_halo_wedding");
    public static final ResourceKey<UmaData> MATIKANETANNHAUSER_SPORTS = register("matikanetannhauser_sports");
    
    public static final ResourceKey<UmaData> CHEVAL_GRAND = register("cheval_grand");
    public static final ResourceKey<UmaData> VERXINA = register("verxina");
    public static final ResourceKey<UmaData> VIVLOS = register("vivlos");
    
    public static final ResourceKey<UmaData> FUJI_KISEKI = register("fuji_kiseki");
    public static final ResourceKey<UmaData> FUJIMASA_MARCH = register("fujimasa_march");
    
    public static final ResourceKey<UmaData> HOKKO_TARUMAE_SWIM = register("hokko_tarumae_swim");
    
    public static final ResourceKey<UmaData> MEJIRO_PALMER = register("mejiro_palmer");
    public static final ResourceKey<UmaData> TRANSCEND = register("transcend");
    public static final ResourceKey<UmaData> DURANDAL = register("durandal");
    public static final ResourceKey<UmaData> CALSTONE_LIGHT_O = register("calstone_light_o");
    
    public static final ResourceKey<UmaData> DAIICHI_RUBY = register("daiichi_ruby");
    
    public static final ResourceKey<UmaData> KATSURAGI_ACE = register("katsuragi_ace");
    public static final ResourceKey<UmaData> HAPPY_MEEK = register("happy_meek");
    public static final ResourceKey<UmaData> STILL_IN_LOVE = register("still_in_love");
    
    public static final ResourceKey<UmaData> RHEIN_KRAFT = register("rhein_kraft");
    
    public static final ResourceKey<UmaData> BUENA_VISTA = register("buena_vista");
    
    public static final ResourceKey<UmaData> KS_MIRACLE = register("ks_miracle");
    public static final ResourceKey<UmaData> EISHIN_FLASH = register("eishin_flash");
    
    public static final ResourceKey<UmaData> AGNES_DIGITAL = register("agnes_digital");
    
    public static final ResourceKey<UmaData> YAMANIN_ZEPHYR = register("yamanin_zephyr");
    public static final ResourceKey<UmaData> SATONO_CROWN = register("satono_crown");
    
    public static final ResourceKey<UmaData> MIYA_YOMOGI = register("miya_yomogi");
    
    public static final ResourceKey<UmaData> ALMOND_EYE = register("almond_eye");
    public static final ResourceKey<UmaData> FUSAICHI_PANDORA = register("fusaichi_pandora");
    
    public static final ResourceKey<UmaData> MEJIRO_RYAN = register("mejiro_ryan");
    public static final ResourceKey<UmaData> TYCHE = register("tyche");
    
    public static final ResourceKey<UmaData> NICE_NATURE_CHEER = register("nice_nature_cheer");
    
    public static final ResourceKey<UmaData> HISHI_AKEBONO = register("hishi_akebono");
    public static final ResourceKey<UmaData> SHENONE_SUZUNA = register("shenone_suzuna");
    
    public static final ResourceKey<UmaData> VIVLOS_SWIM = register("vivlos_swim");
    
    public static final ResourceKey<UmaData> MARUZENSKY = register("maruzensky");
    
    public static final ResourceKey<UmaData> AGNES_DIGITAL_KYOSHI = register("agnes_digital_kyoshi");
    public static final ResourceKey<UmaData> DANTSU_FLAME = register("dantsu_flame");

    public static final ResourceKey<UmaData> NARITA_BRIAN = register("narita_brian");
    public static final ResourceKey<UmaData> CESARIO = register("cesario");
    public static final ResourceKey<UmaData> NISHINO_FLOWER = register("nishino_flower");
    public static final ResourceKey<UmaData> INES_FUJIN = register("ines_fujin");
    public static final ResourceKey<UmaData> HISHI_AMAZON = register("hishi_amazon");
    public static final ResourceKey<UmaData> KISEKI = register("kiseki");
    public static final ResourceKey<UmaData> MEJIRO_RAMONU = register("mejiro_ramonu");
    
    public static void registerAll(BootstapContext<UmaData> bootstrap) {
    	
        bootstrap.register(AGNES_DIGITAL, UmaData.createNewUmamusume("agnes_digital", GachaRanking.SR, new int[] {8, 8, 7, 0, 7}, new Aptitude[]{Aptitude.A, Aptitude.A, Aptitude.F, Aptitude.A, Aptitude.A, Aptitude.G}, Position.LATE_SURGER));
        
        bootstrap.register(AGNES_DIGITAL_KYOSHI, UmaData.createNewUmamusume("agnes_digital", GachaRanking.SSR, new int[] {10, 10, 10, 10, 0}, new Aptitude[]{Aptitude.A, Aptitude.A, Aptitude.F, Aptitude.A, Aptitude.A, Aptitude.G}, Position.LATE_SURGER));
        
        bootstrap.register(DANTSU_FLAME, UmaData.createNewUmamusume("dantsu_flame", GachaRanking.SR, new int[] {0, 10, 0, 20, 0}, new Aptitude[]{Aptitude.A, Aptitude.F, Aptitude.E, Aptitude.B, Aptitude.A, Aptitude.D}, Position.PACE_CHASER));
        
        bootstrap.register(COMMON_UMA, UmaData.createNewUmamusume("common_uma", GachaRanking.R, new Aptitude[]{Aptitude.C, Aptitude.C, Aptitude.C, Aptitude.C, Aptitude.C, Aptitude.C}, Position.FRONT_RUNNER));
        bootstrap.register(COMMON_UMA_A, UmaData.createNewUmamusume("common_uma_a", GachaRanking.R, new Aptitude[]{Aptitude.C, Aptitude.C, Aptitude.C, Aptitude.C, Aptitude.C, Aptitude.C}, Position.PACE_CHASER));
        bootstrap.register(COMMON_UMA_B, UmaData.createNewUmamusume("common_uma_b", GachaRanking.R, new Aptitude[]{Aptitude.C, Aptitude.C, Aptitude.C, Aptitude.C, Aptitude.C, Aptitude.C}, Position.LATE_SURGER));
        bootstrap.register(COMMON_UMA_C, UmaData.createNewUmamusume("common_uma_c", GachaRanking.R, new Aptitude[]{Aptitude.C, Aptitude.C, Aptitude.C, Aptitude.C, Aptitude.C, Aptitude.C}, Position.END_CLOSER));

        bootstrap.register(GOLD_SHIP, UmaData.createNewUmamusume("gold_ship", GachaRanking.SR, new int[] {0, 20, 10, 0, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.C, Aptitude.A, Aptitude.A}, Position.END_CLOSER));
        bootstrap.register(SPECIAL_WEEK, UmaData.createNewUmamusume("special_week", GachaRanking.SR, new int[] {0, 20, 0, 0, 10}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.F, Aptitude.C, Aptitude.A, Aptitude.A}, Position.LATE_SURGER)); // Late Surger vs Pace Chaser
        bootstrap.register(TOKAI_TEIO, UmaData.createNewUmamusume("tokai_teio", GachaRanking.SR, new int[] {20, 10, 0, 0, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.F, Aptitude.E, Aptitude.A, Aptitude.B}, Position.PACE_CHASER));
        bootstrap.register(OGURI_CAP, UmaData.createNewUmamusume("oguri_cap", GachaRanking.SR, new int[] {20, 0, 10, 0, 0}, new Aptitude[]{Aptitude.A, Aptitude.B, Aptitude.E, Aptitude.A, Aptitude.A, Aptitude.B}, Position.LATE_SURGER));
        bootstrap.register(SAKURA_CHIYONO_O, UmaData.createNewUmamusume("sakura_chiyono_o", GachaRanking.SR, new int[] {10, 0, 0, 10, 10}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.E, Aptitude.A, Aptitude.A, Aptitude.E}, Position.PACE_CHASER));
        bootstrap.register(OGURI_CAP_XMAS, UmaData.createNewUmamusume("oguri_cap", GachaRanking.SSR, new int[] {15, 15, 0, 0, 10}, new Aptitude[]{Aptitude.A, Aptitude.B, Aptitude.E, Aptitude.A, Aptitude.A, Aptitude.B}, Position.LATE_SURGER));
        bootstrap.register(AGNUS_TACHYON, UmaData.createNewUmamusume("agnus_tachyon", GachaRanking.SR, new int[] {20, 0, 0, 10, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.D, Aptitude.A, Aptitude.B}, Position.PACE_CHASER));
        bootstrap.register(HARU_URARA, UmaData.createNewUmamusume("haru_urara", GachaRanking.SR, new int[] {0, 0, 10, 20, 0}, new Aptitude[]{Aptitude.G, Aptitude.A, Aptitude.A, Aptitude.B, Aptitude.G, Aptitude.G}, Position.LATE_SURGER)); // Miles B(1star) vs A(3star)
        bootstrap.register(TAMAMO_CROSS, UmaData.createNewUmamusume("tamamo_cross", GachaRanking.SR, new int[] {0, 20, 10, 0, 0}, new Aptitude[]{Aptitude.A, Aptitude.F, Aptitude.G, Aptitude.E, Aptitude.A, Aptitude.A}, Position.LATE_SURGER)); // Pace Chaser (A) / Late Surger (A) / End CLoser (A)
        bootstrap.register(SEIUN_SKY, UmaData.createNewUmamusume("seiun_sky", GachaRanking.SR, new int[] {0, 10, 0, 0, 20}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.C, Aptitude.A, Aptitude.A}, Position.FRONT_RUNNER));
        bootstrap.register(MATIKANEFUKUKITARU, UmaData.createNewUmamusume("matikanefukukitaru", GachaRanking.SR, new int[] {0, 20, 10, 0, 0}, new Aptitude[]{Aptitude.A, Aptitude.F, Aptitude.F, Aptitude.C, Aptitude.A, Aptitude.A}, Position.LATE_SURGER));
        bootstrap.register(RICE_SHOWER, UmaData.createNewUmamusume("rice_shower", GachaRanking.SR, new int[] {0, 10, 0, 20, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.E, Aptitude.C, Aptitude.A, Aptitude.A}, Position.PACE_CHASER));
        bootstrap.register(VODKA, UmaData.createNewUmamusume("vodka", GachaRanking.SR, new int[] {10, 0, 20, 0, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.F, Aptitude.A, Aptitude.A, Aptitude.F}, Position.LATE_SURGER)); // Long F (2 star) / E (3 star)
        bootstrap.register(SAKURA_BAKUSHIN_O, UmaData.createNewUmamusume("sakura_bakushin_o", GachaRanking.SR, new int[] {20, 0, 0, 0, 10}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.A, Aptitude.B, Aptitude.G, Aptitude.G}, Position.PACE_CHASER)); // Pace Chaser (A) / Front Runner (A)
        bootstrap.register(SAKURA_BAKUSHIN_O_SPORTS, UmaData.createNewUmamusume("sakura_bakushin_o", GachaRanking.SSR, new int[] {18, 0, 11, 0, 11}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.A, Aptitude.B, Aptitude.G, Aptitude.G}, Position.PACE_CHASER)); // Pace Chaser (A) / Front Runner (A)
        bootstrap.register(MANHATTAN_CAFE, UmaData.createNewUmamusume("manhattan_cafe", GachaRanking.SR, new int[] {0, 30, 0, 0, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.F, Aptitude.B, Aptitude.A}, Position.LATE_SURGER));
        bootstrap.register(MEJIRO_ARDAN, UmaData.createNewUmamusume("mejiro_ardan", GachaRanking.SR, new int[] {10, 0, 0, 0, 20}, new Aptitude[]{Aptitude.A, Aptitude.F, Aptitude.E, Aptitude.B, Aptitude.A, Aptitude.D}, Position.PACE_CHASER));
        bootstrap.register(DAITAKU_HELIOS, UmaData.createNewUmamusume("daitaku_helios", GachaRanking.SR, new int[] {15, 0, 15, 0, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.B, Aptitude.A, Aptitude.B, Aptitude.E}, Position.RUNAWAY)); // Runaway
        bootstrap.register(SWEEP_TOSHO, UmaData.createNewUmamusume("sweep_tosho", GachaRanking.SR, new int[] {10, 0, 20, 0, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.E, Aptitude.A, Aptitude.A, Aptitude.D}, Position.LATE_SURGER)); // Late Surger (A) / End Closer (A)
        bootstrap.register(GOLD_CITY, UmaData.createNewUmamusume("gold_city", GachaRanking.SR, new int[] {0, 0, 10, 20, 0}, new Aptitude[]{Aptitude.A, Aptitude.D, Aptitude.F, Aptitude.A, Aptitude.B, Aptitude.B}, Position.FRONT_RUNNER));
        bootstrap.register(GOLD_SHIP_WATER, UmaData.createNewUmamusume("gold_ship", GachaRanking.SSR, new int[] {0, 0, 20, 0, 20}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.C, Aptitude.A, Aptitude.A}, Position.END_CLOSER));
        bootstrap.register(MR_CB, UmaData.createNewUmamusume("mr_cb", GachaRanking.SR, new int[] {10, 10, 0, 0, 10}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.B, Aptitude.A, Aptitude.A}, Position.LATE_SURGER)); // Late Surger (A) / End Closer (A)
        bootstrap.register(GRASS_WONDER, UmaData.createNewUmamusume("grass_wonder", GachaRanking.SR, new int[] {20, 0, 10, 0, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.A, Aptitude.B, Aptitude.A}, Position.LATE_SURGER)); // Pace Chaser (A) / Late Surger (A)
        bootstrap.register(CURREN_CHAN, UmaData.createNewUmamusume("curren_chan", GachaRanking.SR, new int[] {10, 0, 20, 0, 0}, new Aptitude[]{Aptitude.A, Aptitude.F, Aptitude.A, Aptitude.D, Aptitude.G, Aptitude.G}, Position.PACE_CHASER));
        bootstrap.register(SILENCE_SUZUKA, UmaData.createNewUmamusume("silence_suzuka", GachaRanking.SR, new int[] {20, 0, 0, 10, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.D, Aptitude.A, Aptitude.A, Aptitude.E}, Position.RUNAWAY)); // Runaway
        bootstrap.register(TAMAMO_CROSS_FESTIVAL, UmaData.createNewUmamusume("tamamo_cross", GachaRanking.SSR, new int[] {15, 10, 0, 15, 0}, new Aptitude[]{Aptitude.A, Aptitude.F, Aptitude.G, Aptitude.E, Aptitude.A, Aptitude.A}, Position.LATE_SURGER)); // Pace Chaser (A) / Late Surger (A) / End CLoser (A)
        bootstrap.register(ASTON_MACHAN, UmaData.createNewUmamusume("aston_machan", GachaRanking.SR, new int[] {20, 0, 0, 10, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.A, Aptitude.B, Aptitude.G, Aptitude.G}, Position.PACE_CHASER)); // Front Runner (A) / Pace Chaser (A)
        bootstrap.register(KITASAN_BLACK, UmaData.createNewUmamusume("kitasan_black", GachaRanking.SR, new int[] {20, 10, 0, 0, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.E, Aptitude.C, Aptitude.A, Aptitude.A}, Position.FRONT_RUNNER));
        bootstrap.register(SATONO_DIAMOND, UmaData.createNewUmamusume("satono_diamond", GachaRanking.SR, new int[] {0, 15, 0, 0, 15}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.C, Aptitude.A, Aptitude.A}, Position.LATE_SURGER));
        bootstrap.register(NICE_NATURE, UmaData.createNewUmamusume("nice_nature", GachaRanking.SR, new int[] {0, 0, 20, 0, 10}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.C, Aptitude.A, Aptitude.A}, Position.LATE_SURGER));
        bootstrap.register(MAYANO_TOP_GUN, UmaData.createNewUmamusume("mayano_top_gun", GachaRanking.SR, new int[] {0, 20, 0, 10, 0}, new Aptitude[]{Aptitude.A, Aptitude.E, Aptitude.D, Aptitude.D, Aptitude.A, Aptitude.A}, Position.PACE_CHASER)); // In fact, all positions are in candidate
        bootstrap.register(NEO_UNIVERSE, UmaData.createNewUmamusume("neo_universe", GachaRanking.SR, new int[] {0, 0, 0, 0, 30}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.F, Aptitude.B, Aptitude.A, Aptitude.B}, Position.PACE_CHASER)); // Pace Chaser (A) / Late Surger (A)
        bootstrap.register(MEISHO_DOTOU, UmaData.createNewUmamusume("meisho_dotou", GachaRanking.SR, new int[] {0, 20, 0, 10, 0}, new Aptitude[]{Aptitude.A, Aptitude.E, Aptitude.G, Aptitude.F, Aptitude.A, Aptitude.A}, Position.PACE_CHASER));
        bootstrap.register(TAIKI_SHUTTLE, UmaData.createNewUmamusume("taiki_shuttle", GachaRanking.SR, new int[] {20, 0, 0, 0, 10}, new Aptitude[]{Aptitude.A, Aptitude.B, Aptitude.A, Aptitude.A, Aptitude.E, Aptitude.G}, Position.PACE_CHASER));
        bootstrap.register(CURREN_CHAN_DRESS, UmaData.createNewUmamusume("curren_chan", GachaRanking.SSR, new int[] {10, 0, 20, 0, 10}, new Aptitude[]{Aptitude.A, Aptitude.F, Aptitude.A, Aptitude.D, Aptitude.G, Aptitude.G}, Position.PACE_CHASER));
        bootstrap.register(MEJIRO_MCQUEEN, UmaData.createNewUmamusume("mejiro_mcqueen", GachaRanking.SR, new int[] {0, 20, 0, 0, 10}, new Aptitude[]{Aptitude.A, Aptitude.E, Aptitude.G, Aptitude.F, Aptitude.A, Aptitude.A}, Position.PACE_CHASER));
        bootstrap.register(COPANO_RICKEY, UmaData.createNewUmamusume("copano_rickey", GachaRanking.SR, new int[] {0, 0, 10, 0, 20}, new Aptitude[]{Aptitude.F, Aptitude.A, Aptitude.C, Aptitude.A, Aptitude.A, Aptitude.G}, Position.PACE_CHASER)); // Front Runner (A) / Pace Chaser (A)
        
        bootstrap.register(SYMBOLI_RUDOLF, UmaData.createNewUmamusume("symboli_rudolf", GachaRanking.SR, new int[] {0, 20, 0, 10, 0}, new Aptitude[]{Aptitude.A, Aptitude.B, Aptitude.E, Aptitude.C, Aptitude.A, Aptitude.A}, Position.PACE_CHASER)); // Pace Chaser (A) / Late Surger (A)
        bootstrap.register(NARITA_TOP_ROAD, UmaData.createNewUmamusume("narita_top_road", GachaRanking.SR, new int[] {20, 10, 0, 0, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.C, Aptitude.A, Aptitude.A}, Position.PACE_CHASER));
        bootstrap.register(VENUS_PARK, UmaData.createNewUmamusume("venus_park", GachaRanking.EASTER_EGG, new int[] {10, 10, 10, 10, 10}, new Aptitude[]{Aptitude.A, Aptitude.B, Aptitude.C, Aptitude.D, Aptitude.E, Aptitude.F}, Position.PACE_CHASER)); // Unknown data for Venus Paques: Not implemented as a playable character in Umamusume: Pretty Derby
        
        bootstrap.register(AGNUS_TACHYON_SWIM, UmaData.createNewUmamusume("agnus_tachyon", GachaRanking.SSR, new int[] {15, 0, 10, 0, 15}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.D, Aptitude.A, Aptitude.B}, Position.PACE_CHASER));
        bootstrap.register(MIHONO_BOURBON, UmaData.createNewUmamusume("mihono_bourbon", GachaRanking.SR, new int[] {0, 20, 10, 0, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.C, Aptitude.B, Aptitude.A, Aptitude.B}, Position.FRONT_RUNNER));
        bootstrap.register(MATIKANETANNHAUSER, UmaData.createNewUmamusume("matikanetannhauser", GachaRanking.SR, new int[] {0, 20, 0, 10, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.D, Aptitude.A, Aptitude.A}, Position.PACE_CHASER)); // Pace Chaser (A) / Late Surger (A)
        bootstrap.register(KAWAKAMI_PRINCESS, UmaData.createNewUmamusume("kawakami_princess", GachaRanking.SR, new int[] {0, 10, 0, 20, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.D, Aptitude.B, Aptitude.A, Aptitude.F}, Position.LATE_SURGER));
        bootstrap.register(TWIN_TURBO, UmaData.createNewUmamusume("twinturbo", GachaRanking.SR, new int[] {30, 0, 0, 0, 0}, new Aptitude[]{Aptitude.A, Aptitude.F, Aptitude.G, Aptitude.A, Aptitude.A, Aptitude.E}, Position.RUNAWAY));
        bootstrap.register(LITTLE_COCON, UmaData.createNewUmamusume("little_cocon", GachaRanking.SR, new int[] {10, 0, 10, 0, 10}, new Aptitude[]{Aptitude.A, Aptitude.B, Aptitude.C, Aptitude.D, Aptitude.E, Aptitude.F}, Position.PACE_CHASER)); // Unknown data for Little Cocon: Not implemented as a playable character in Umamusume: Pretty Derby
        bootstrap.register(SAKURA_LAUREL, UmaData.createNewUmamusume("sakura_laurel", GachaRanking.SR, new int[] {0, 20, 10, 0, 0}, new Aptitude[]{Aptitude.A, Aptitude.E, Aptitude.G, Aptitude.C, Aptitude.A, Aptitude.B}, Position.LATE_SURGER));
        
        bootstrap.register(FINE_MOTION,UmaData.createNewUmamusume("fine_motion", GachaRanking.SR, new int[] {0, 0, 15, 0, 15}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.F, Aptitude.A, Aptitude.A, Aptitude.C}, Position.PACE_CHASER));
        bootstrap.register(TM_OPERA_O,UmaData.createNewUmamusume("tm_opera_o", GachaRanking.SR, new int[] {0, 20, 0, 0, 10}, new Aptitude[]{Aptitude.A, Aptitude.E, Aptitude.G, Aptitude.E, Aptitude.A, Aptitude.A}, Position.PACE_CHASER)); // Pace Chaser (A) / Late Surger (A)
        bootstrap.register(ADMIRE_VEGA,UmaData.createNewUmamusume("admire_vega", GachaRanking.SR, new int[] {10, 0, 20, 0, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.F, Aptitude.C, Aptitude.A, Aptitude.C}, Position.END_CLOSER));
        bootstrap.register(JUNGLE_POCKET,UmaData.createNewUmamusume("jungle_pocket", GachaRanking.SR, new int[] {10, 0, 20, 0, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.C, Aptitude.A, Aptitude.B}, Position.LATE_SURGER));
        bootstrap.register(NARITA_TAISHIN,UmaData.createNewUmamusume("narita_taishin", GachaRanking.SR, new int[] {10, 0, 0, 20, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.F, Aptitude.D, Aptitude.A, Aptitude.A}, Position.END_CLOSER));
        bootstrap.register(GOLD_CITY_AUTUMN,UmaData.createNewUmamusume("gold_city", GachaRanking.SSR, new int[] {10, 0, 15, 0, 15}, new Aptitude[]{Aptitude.A, Aptitude.D, Aptitude.F, Aptitude.A, Aptitude.B, Aptitude.B}, Position.FRONT_RUNNER));
        bootstrap.register(GRASS_WONDER_UMANET,UmaData.createNewUmamusume("grass_wonder", GachaRanking.SSR, new int[] {15, 0, 10, 0, 15}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.A, Aptitude.B, Aptitude.A}, Position.LATE_SURGER)); // Pace Chaser (A) / Late Surger (A)
        bootstrap.register(SATONO_DIAMOND_FRENCH,UmaData.createNewUmamusume("satono_diamond", GachaRanking.SSR, new int[] {10, 15, 0, 15, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.C, Aptitude.A, Aptitude.A}, Position.LATE_SURGER));
        bootstrap.register(SYAMEIMARU_ZHENG,UmaData.createNewUmamusume("syameimaru_zheng", GachaRanking.EASTER_EGG, new int[] {20, 0, 0, 10, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.D, Aptitude.A, Aptitude.B}, Position.PACE_CHASER)); // FYR, Tachyon's property is used
        bootstrap.register(STARDUST,UmaData.createNewUmamusume("stardust", GachaRanking.EASTER_EGG, new int[] {10, 15, 0, 0, 15}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.D, Aptitude.A, Aptitude.A, Aptitude.E}, Position.RUNAWAY)); // FYR, Silence Suzuka's property is used
        bootstrap.register(DUMNHEINT,UmaData.createNewUmamusume("dumnheint", GachaRanking.EASTER_EGG, new int[] {20, 20, 10, 0, 0}, new Aptitude[]{Aptitude.A, Aptitude.B, Aptitude.C, Aptitude.D, Aptitude.E, Aptitude.F}, Position.PACE_CHASER)); // Unknown data for Dumnheint: Original Umamusume
        bootstrap.register(DARLEY_ARABIAN,UmaData.createNewUmamusume("darley_arabian", GachaRanking.EASTER_EGG, new int[] {10, 10, 10, 10, 10}, new Aptitude[]{Aptitude.S, Aptitude.S, Aptitude.S, Aptitude.S, Aptitude.S, Aptitude.S}, Position.PACE_CHASER)); // using all-S for Darley Arabian as example value
        bootstrap.register(GODOLPHIN_BARB,UmaData.createNewUmamusume("godolphin_barb", GachaRanking.EASTER_EGG, new int[] {25, 0, 0, 0, 25}, new Aptitude[]{Aptitude.S, Aptitude.S, Aptitude.S, Aptitude.S, Aptitude.S, Aptitude.S}, Position.PACE_CHASER)); // using all-S for Godolphin Barb as example value
        bootstrap.register(BYERLEY_TURK,UmaData.createNewUmamusume("byerley_turk", GachaRanking.EASTER_EGG, new int[] {0, 15, 20, 15, 0}, new Aptitude[]{Aptitude.S, Aptitude.S, Aptitude.S, Aptitude.S, Aptitude.S, Aptitude.S}, Position.PACE_CHASER)); // using all-S for Byerley Turk as example value
        bootstrap.register(SMART_FALCON,UmaData.createNewUmamusume("smart_falcon", GachaRanking.SR, new int[] {20, 0, 10, 0, 0}, new Aptitude[]{Aptitude.E, Aptitude.A, Aptitude.B, Aptitude.A, Aptitude.A, Aptitude.E}, Position.FRONT_RUNNER));
        bootstrap.register(MANHATTAN_CAFE_VALENTINE,UmaData.createNewUmamusume("manhattan_cafe", GachaRanking.SSR, new int[] {0, 20, 20, 0, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.F, Aptitude.B, Aptitude.F}, Position.LATE_SURGER));

        bootstrap.register(HISHI_MIRACLE, UmaData.createNewUmamusume("hishi_miracle", GachaRanking.SR, new int[] {7, 8, 7, 8, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.G, Aptitude.A, Aptitude.A}, Position.LATE_SURGER));

        bootstrap.register(DAIWA_SCARLET, UmaData.createNewUmamusume("daiwa_scarlet", GachaRanking.SR, new int[] {10, 0, 0, 20, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.A, Aptitude.A, Aptitude.B}, Position.PACE_CHASER)); // FAAB(2 star)/FBAA(3 star), Front Runner (A) / Pace Chaser (A)
        bootstrap.register(WIN_VARIATION, UmaData.createNewUmamusume("win_variation", GachaRanking.SR, new int[] {10, 0, 0, 20, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.E, Aptitude.A, Aptitude.A}, Position.LATE_SURGER)); // Late Surger (A) / End Closer (A)
        bootstrap.register(EL_CONDOR_PASA, UmaData.createNewUmamusume("el_condor_pasa", GachaRanking.SR, new int[] {20, 0, 0, 0, 10}, new Aptitude[]{Aptitude.A, Aptitude.B, Aptitude.F, Aptitude.A, Aptitude.A, Aptitude.B}, Position.PACE_CHASER)); // Pace Chaser (A) / Late Surger (A)

        bootstrap.register(HOKKO_TARUMAE, UmaData.createNewUmamusume("hokko_tarumae", GachaRanking.SR, new int[] {0, 20, 10, 0, 0}, new Aptitude[]{Aptitude.G, Aptitude.A, Aptitude.F, Aptitude.A, Aptitude.A, Aptitude.E}, Position.PACE_CHASER));
        bootstrap.register(KING_HALO, UmaData.createNewUmamusume("king_halo", GachaRanking.SR, new int[] {0, 20, 0, 0, 10}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.A, Aptitude.B, Aptitude.B, Aptitude.C}, Position.LATE_SURGER));
        bootstrap.register(KING_HALO_CHEER, UmaData.createNewUmamusume("king_halo", GachaRanking.SSR, new int[] {10, 10, 10, 10, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.A, Aptitude.B, Aptitude.B, Aptitude.C}, Position.LATE_SURGER));
        bootstrap.register(KING_HALO_WEDDING, UmaData.createNewUmamusume("king_halo", GachaRanking.SSR, new int[] {10, 0, 10, 10, 10}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.A, Aptitude.A, Aptitude.B, Aptitude.C}, Position.LATE_SURGER));
        bootstrap.register(MATIKANETANNHAUSER_SPORTS, UmaData.createNewUmamusume("matikanetannhauser", GachaRanking.SSR, new int[] {20, 0, 20, 0, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.D, Aptitude.A, Aptitude.A}, Position.PACE_CHASER));
        
        bootstrap.register(CHEVAL_GRAND, UmaData.createNewUmamusume("cheval_grand", GachaRanking.SR, new int[] {0, 10, 0, 10, 10}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.G, Aptitude.A, Aptitude.A}, Position.PACE_CHASER));
        bootstrap.register(VERXINA, UmaData.createNewUmamusume("verxina", GachaRanking.SR, new int[] {10, 0, 0, 10, 10}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.D, Aptitude.A, Aptitude.A, Aptitude.G}, Position.PACE_CHASER)); // Front Runner (A) / Pace Chaser (A)
        bootstrap.register(VIVLOS, UmaData.createNewUmamusume("vivlos", GachaRanking.SR, new int[] {10, 0, 10, 0, 10}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.E, Aptitude.A, Aptitude.A, Aptitude.G}, Position.LATE_SURGER));
    	
    	bootstrap.register(FUJI_KISEKI, UmaData.createNewUmamusume("fuji_kiseki", GachaRanking.SR, new int[] {0, 0, 20, 0, 10}, new Aptitude[]{Aptitude.A, Aptitude.F, Aptitude.B, Aptitude.A, Aptitude.B, Aptitude.E}, Position.PACE_CHASER));
        bootstrap.register(FUJIMASA_MARCH, UmaData.createNewUmamusume("fujimasa_march", GachaRanking.SR, new int[] {6, 6, 6, 6, 6}, new Aptitude[]{Aptitude.A, Aptitude.F, Aptitude.B, Aptitude.A, Aptitude.B, Aptitude.E}, Position.PACE_CHASER));
    	
        bootstrap.register(HOKKO_TARUMAE_SWIM, UmaData.createNewUmamusume("hokko_tarumae", GachaRanking.SSR, new int[] {20, 10, 0, 0, 10}, new Aptitude[]{Aptitude.G, Aptitude.A, Aptitude.F, Aptitude.A, Aptitude.A, Aptitude.E}, Position.PACE_CHASER));
        bootstrap.register(MEJIRO_PALMER, UmaData.createNewUmamusume("mejiro_palmer", GachaRanking.SR, new int[] {10, 10, 0, 10, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.F, Aptitude.A, Aptitude.A}, Position.RUNAWAY)); // Runaway
        bootstrap.register(TRANSCEND, UmaData.createNewUmamusume("transcend", GachaRanking.SR, new int[] {10, 0, 10, 0, 10}, new Aptitude[]{Aptitude.F, Aptitude.A, Aptitude.G, Aptitude.A, Aptitude.A, Aptitude.G}, Position.FRONT_RUNNER));
        bootstrap.register(DURANDAL, UmaData.createNewUmamusume("durandal", GachaRanking.SR, new int[] {10, 0, 20, 0, 0}, new Aptitude[]{Aptitude.A, Aptitude.A, Aptitude.G, Aptitude.A, Aptitude.F, Aptitude.G}, Position.LATE_SURGER));
        bootstrap.register(CALSTONE_LIGHT_O, UmaData.createNewUmamusume("calstone_light_o", GachaRanking.SR, new int[] {15, 0, 15, 0, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.A, Aptitude.D, Aptitude.G, Aptitude.G}, Position.FRONT_RUNNER));
        
        bootstrap.register(DAIICHI_RUBY, UmaData.createNewUmamusume("daiichi_ruby", GachaRanking.SR, new int[] {0, 0, 20, 0, 10}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.A, Aptitude.A, Aptitude.C, Aptitude.G}, Position.LATE_SURGER)); // Late Surger (A) / End Closer (A)
        
        bootstrap.register(KATSURAGI_ACE, UmaData.createNewUmamusume("katsuragi_ace", GachaRanking.SR, new int[] {10, 0, 10, 10, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.E, Aptitude.B, Aptitude.A, Aptitude.B}, Position.RUNAWAY)); // Runaway
        bootstrap.register(HAPPY_MEEK, UmaData.createNewUmamusume("happy_meek", GachaRanking.SR, new int[] {6, 6, 6, 6, 6}, new Aptitude[]{Aptitude.A, Aptitude.B, Aptitude.C, Aptitude.D, Aptitude.E, Aptitude.F}, Position.PACE_CHASER)); // Unknown data for Happy Meek
        bootstrap.register(STILL_IN_LOVE, UmaData.createNewUmamusume("still_in_love", GachaRanking.SR, new int[] {20, 0, 0, 10, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.C, Aptitude.A, Aptitude.A, Aptitude.G}, Position.PACE_CHASER)); // Pace Chaser (A) / Late Surger (A)

        bootstrap.register(RHEIN_KRAFT, UmaData.createNewUmamusume("rhein_kraft", GachaRanking.SR, new int[] {0, 0, 15, 15, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.A, Aptitude.A, Aptitude.B, Aptitude.G}, Position.PACE_CHASER));
        
        bootstrap.register(BUENA_VISTA, UmaData.createNewUmamusume("buena_vista", GachaRanking.SR, new int[] {0, 0, 15, 15, 0}, new Aptitude[]{Aptitude.A, Aptitude.F, Aptitude.G, Aptitude.A, Aptitude.A, Aptitude.C}, Position.LATE_SURGER)); // Late Surger (A) / End Closer (A)
    	
    	bootstrap.register(KS_MIRACLE, UmaData.createNewUmamusume("ks_miracle", GachaRanking.SR, new int[] {15, 0, 0, 15, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.A, Aptitude.B, Aptitude.G, Aptitude.G}, Position.PACE_CHASER));
        bootstrap.register(EISHIN_FLASH, UmaData.createNewUmamusume("eishin_flash", GachaRanking.SR, new int[] {0, 0, 10, 0, 20}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.F, Aptitude.A, Aptitude.A}, Position.LATE_SURGER));
        
        bootstrap.register(MIYA_YOMOGI, UmaData.createNewUmamusume("miya_yomogi", GachaRanking.EASTER_EGG, new int[] {10, 0, 0, 20, 20}, new Aptitude[]{Aptitude.A, Aptitude.B, Aptitude.C, Aptitude.D, Aptitude.E, Aptitude.F}, Position.PACE_CHASER)); // Unknown data for Miya Yogomi (original)
    	
    	bootstrap.register(YAMANIN_ZEPHYR, UmaData.createNewUmamusume("yamanin_zephyr", GachaRanking.SR, new int[] {10, 0, 0, 10, 10}, new Aptitude[]{Aptitude.A, Aptitude.D, Aptitude.B, Aptitude.A, Aptitude.A, Aptitude.G}, Position.PACE_CHASER));
        bootstrap.register(SATONO_CROWN, UmaData.createNewUmamusume("satono_crown", GachaRanking.SR, new int[] {0, 0, 15, 15, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.B, Aptitude.A, Aptitude.E}, Position.LATE_SURGER));
        
        bootstrap.register(ALMOND_EYE, UmaData.createNewUmamusume("almond_eye", GachaRanking.SSR, new int[] {10, 5, 10, 10, 5}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.A, Aptitude.A, Aptitude.F}, Position.FRONT_RUNNER));
        bootstrap.register(FUSAICHI_PANDORA, UmaData.createNewUmamusume("fusaichi_pandora", GachaRanking.SR, new int[] {0, 0, 15, 15, 0}, new Aptitude[]{Aptitude.A, Aptitude.E, Aptitude.G, Aptitude.B, Aptitude.A, Aptitude.G}, Position.PACE_CHASER));
        
        bootstrap.register(MEJIRO_RYAN, UmaData.createNewUmamusume("mejiro_ryan", GachaRanking.SR, new int[] {0, 0, 20, 0, 10}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.E, Aptitude.C, Aptitude.A, Aptitude.B}, Position.PACE_CHASER)); // Pace Chaser (A) / Late Surger (A)
        bootstrap.register(TYCHE, UmaData.createNewUmamusume("tyche", GachaRanking.EASTER_EGG, new int[] {20, 20, 0, 10, 0}, new Aptitude[]{Aptitude.A, Aptitude.B, Aptitude.C, Aptitude.D, Aptitude.E, Aptitude.F}, Position.PACE_CHASER)); // Unknown data for Tyche: Original Umamusume
        
        bootstrap.register(NICE_NATURE_CHEER, UmaData.createNewUmamusume("nice_nature", GachaRanking.SSR, new int[] {0, 10, 20, 0, 10}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.C, Aptitude.A, Aptitude.A}, Position.LATE_SURGER));

        bootstrap.register(HISHI_AKEBONO, UmaData.createNewUmamusume("hishi_akebono", GachaRanking.SR, new int[] {0, 0, 20, 10, 0}, new Aptitude[]{Aptitude.A, Aptitude.F, Aptitude.A, Aptitude.B, Aptitude.F, Aptitude.G}, Position.PACE_CHASER));
        bootstrap.register(SHENONE_SUZUNA, UmaData.createNewUmamusume("shenone_suzuna", GachaRanking.EASTER_EGG, new int[] {20, 10, 5, 0, 10}, new Aptitude[]{Aptitude.A, Aptitude.B, Aptitude.C, Aptitude.D, Aptitude.E, Aptitude.F}, Position.PACE_CHASER)); // Unknown data for Shenone Suzuna: Original Umamusume
        
        bootstrap.register(VIVLOS_SWIM, UmaData.createNewUmamusume("vivlos", GachaRanking.SSR, new int[] {10, 0, 0, 10, 20}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.E, Aptitude.A, Aptitude.A, Aptitude.G}, Position.LATE_SURGER));
        
    	bootstrap.register(MARUZENSKY, UmaData.createNewUmamusume("maruzensky", GachaRanking.SR, new int[] {10, 0, 0, 0, 20}, new Aptitude[]{Aptitude.A, Aptitude.D, Aptitude.B, Aptitude.A, Aptitude.B, Aptitude.C}, Position.FRONT_RUNNER));

        bootstrap.register(NARITA_BRIAN, UmaData.createNewUmamusume("narita_brian", GachaRanking.SR, new int[] {10, 20, 0, 0, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.F, Aptitude.B, Aptitude.A, Aptitude.A}, Position.PACE_CHASER)); // pace chaser / late surger
        bootstrap.register(CESARIO, UmaData.createNewUmamusume("cesario", GachaRanking.SR, new int[] {10, 0, 10, 0, 10}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.A, Aptitude.A, Aptitude.F}, Position.PACE_CHASER)); // pace chaser / late surger
        bootstrap.register(NISHINO_FLOWER, UmaData.createNewUmamusume("nishino_flower", GachaRanking.SR, new int[] {15, 0, 15, 0, 0}, new Aptitude[]{Aptitude.A, Aptitude.F, Aptitude.A, Aptitude.A, Aptitude.E, Aptitude.G}, Position.PACE_CHASER)); // pace chaser / late surger
        bootstrap.register(INES_FUJIN, UmaData.createNewUmamusume("ines_fujin", GachaRanking.SR, new int[] {15, 0, 0, 15, 0}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.A, Aptitude.A, Aptitude.C}, Position.FRONT_RUNNER));
        bootstrap.register(HISHI_AMAZON, UmaData.createNewUmamusume("hishi_amazon", GachaRanking.SR, new int[] {0, 0, 20, 10, 0}, new Aptitude[]{Aptitude.A, Aptitude.E, Aptitude.D, Aptitude.A, Aptitude.A, Aptitude.B}, Position.LATE_SURGER));
        bootstrap.register(KISEKI, UmaData.createNewUmamusume("kiseki", GachaRanking.SR, new int[] {10, 0, 0, 10, 10}, new Aptitude[]{Aptitude.A, Aptitude.G, Aptitude.G, Aptitude.C, Aptitude.A, Aptitude.A}, Position.FRONT_RUNNER)); // front runner / late surger
        bootstrap.register(MEJIRO_RAMONU, UmaData.createNewUmamusume("mejiro_ramonu", GachaRanking.SR, new int[] {15, 0, 0, 0, 15}, new Aptitude[]{Aptitude.A, Aptitude.F, Aptitude.B, Aptitude.A, Aptitude.A, Aptitude.E}, Position.PACE_CHASER)); // pace chaser / late surger

    }

    private static ResourceKey<UmaData> register(String id) {
        return ResourceKey.create(UmaData.REGISTRY_KEY, new ResourceLocation(Umapyoi.MODID, id));
    }
}
