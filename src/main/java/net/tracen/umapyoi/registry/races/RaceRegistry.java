package net.tracen.umapyoi.registry.races;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.utils.Distance;
import net.tracen.umapyoi.utils.RaceRanking;
import net.tracen.umapyoi.utils.Surface;
import net.tracen.umapyoi.utils.Year;

import java.util.HashMap;
import java.util.function.Function;

import static net.tracen.umapyoi.registry.races.Race.RaceBuilder;
import static net.tracen.umapyoi.registry.races.field.RaceFieldRegistry.*;
import static net.tracen.umapyoi.registry.races.tags.RaceTagRegistry.*;

public class RaceRegistry {
    public static final String PREDICATE_CHAMPIONS = "champions";
    
    public static final ResourceKey<Race> DEFAULT = simpleRegister("undetermined_race",
           new RaceBuilder()::create
    );

    // Classic Race

    public static final ResourceKey<Race> HAKODATE_JUNIOR_STAKES = simpleRegister("hakodate_junior_stakes",
            new RaceBuilder().setTime(7, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.GIII)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    .setField(HAKODATE)
                    ::create
    );

    public static final ResourceKey<Race> CHUKYO_JUNIOR_STAKES = simpleRegister("chukyo_junior_stakes",
            new RaceBuilder().setTime(7, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .setField(CHUKYO)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> NIIGATA_JUNIOR_STAKES = simpleRegister("niigata_junior_stakes",
            new RaceBuilder().setTime(8, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.GIII)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .setField(NIIGATA)
                    ::create
    );

    public static final ResourceKey<Race> CLOVER_SHO = simpleRegister("clover_sho",
            new RaceBuilder().setTime(8, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(1500)
                    .setField(SAPPORO)
                    ::create
    );

    public static final ResourceKey<Race> DAHLIA_SHO = simpleRegister("dahlia_sho",
            new RaceBuilder().setTime(8, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    .setField(NIIGATA)
                    ::create
    );

    public static final ResourceKey<Race> PHOENIX_SHO = simpleRegister("phoenix_sho",
            new RaceBuilder().setTime(8, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    .setField(KOKURA)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> COSMOS_SHO = simpleRegister("cosmos_sho",
            new RaceBuilder().setTime(8, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    .setField(SAPPORO)
                    ::create
    );

    public static final ResourceKey<Race> KIKYO_STAKES = simpleRegister("kikyo_stakes",
            new RaceBuilder().setTime(9, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    .setField(HANSHIN)
                    ::create
    );

    public static final ResourceKey<Race> FUYO_STAKES = simpleRegister("fuyo_stakes",
            new RaceBuilder().setTime(9, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .setField(NAKAYAMA)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> CANNA_STAKES = simpleRegister("canna_stakes",
            new RaceBuilder().setTime(9, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    .setField(NAKAYAMA)
                    ::create
    );

    public static final ResourceKey<Race> SAFFRON_SHO = simpleRegister("saffron_sho",
            new RaceBuilder().setTime(9, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.PREOP)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .setField(NAKAYAMA)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> SAPPORO_JUNIOR_STAKES = simpleRegister("sapporo_junior_stakes",
            new RaceBuilder().setTime(9, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.GIII)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    .setField(SAPPORO)
                    ::create
    );

    public static final ResourceKey<Race> KOKURA_JUNIOR_STAKES = simpleRegister("kokura_junior_stakes",
            new RaceBuilder().setTime(9, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.GIII)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    .setField(KOKURA)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> SUZURAN_SHO = simpleRegister("suzuran_sho",
            new RaceBuilder().setTime(9, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    .setField(SAPPORO)
                    ::create
    );

    public static final ResourceKey<Race> NOJIGIKU_STAKES = simpleRegister("nojigiku_stakes",
            new RaceBuilder().setTime(9, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    .setField(HANSHIN)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> ASTER_SHO = simpleRegister("aster_sho",
            new RaceBuilder().setTime(9, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.PREOP)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .setField(NAKAYAMA)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> ARTEMIS_STAKES = simpleRegister("artemis_stakes",
            new RaceBuilder().setTime(10, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.GIII)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .setField(TOKYO)
                    .addAttr(1, 3)
                    ::create
    );

    public static final ResourceKey<Race> IVY_STAKES = simpleRegister("ivy_stakes",
            new RaceBuilder().setTime(10, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    .setField(TOKYO)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> HAGI_STAKES = simpleRegister("hagi_stakes",
            new RaceBuilder().setTime(10, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    .setField(KYOTO)
                    ::create
    );

    public static final ResourceKey<Race> NADESHIKO_SHO = simpleRegister("nadeshiko_sho",
            new RaceBuilder().setTime(10, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.PREOP)
                    .setSurface(Surface.DIRT)
                    .setLength(1400)
                    .setField(KYOTO)
                    ::create
    );

    public static final ResourceKey<Race> SAUDI_ARABIA_ROYAL_CUP = simpleRegister("saudi_arabia_royal_cup",
            new RaceBuilder().setTime(10, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.GIII)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .setField(TOKYO)
                    .addAttr(1, 3)
                    ::create
    );

    public static final ResourceKey<Race> MOMIJI_STAKES = simpleRegister("momiji_stakes",
            new RaceBuilder().setTime(10, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    .setField(KYOTO)
                    ::create
    );

    public static final ResourceKey<Race> RINDO_SHO = simpleRegister("rindo_sho",
            new RaceBuilder().setTime(10, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.PREOP)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    .setField(KYOTO)
                    ::create
    );

    public static final ResourceKey<Race> SHIGIKU_SHO = simpleRegister("shigiku_sho",
            new RaceBuilder().setTime(10, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.PREOP)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .setField(KYOTO)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> PLATANUS_SHO = simpleRegister("platanus_sho",
            new RaceBuilder().setTime(10, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.PREOP)
                    .setSurface(Surface.DIRT)
                    .setLength(1600)
                    .setField(KYOTO)
                    .addAttr(0, 1)
                    ::create
    );

    public static final ResourceKey<Race> TOKYO_SPORTS_HAI_JUNIOR_STAKES = simpleRegister("tokyo_sports_hai_junior_stakes",
            new RaceBuilder().setTime(11, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.GIII)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    .setField(KYOTO)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> KYOTO_JUNIOR_STAKES = simpleRegister("kyoto_junior_stakes",
            new RaceBuilder().setTime(11, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.GIII)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .setField(KYOTO)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> MOCHINOKI_SHO = simpleRegister("mochinoki_sho",
            new RaceBuilder().setTime(11, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.PREOP)
                    .setSurface(Surface.DIRT)
                    .setLength(1800)
                    .setField(KYOTO)
                    ::create
    );

    public static final ResourceKey<Race> AKAMATSU_SHO = simpleRegister("akamatsu_sho",
            new RaceBuilder().setTime(11, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.PREOP)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .setField(TOKYO)
                    .addAttr(1, 3)
                    ::create
    );

    public static final ResourceKey<Race> SHUMEIGIKU_SHO = simpleRegister("shumeigiku_sho",
            new RaceBuilder().setTime(11, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.PREOP)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    .setField(KYOTO)
                    ::create
    );

    public static final ResourceKey<Race> CATTLEYA_SHO = simpleRegister("cattleya_sho",
            new RaceBuilder().setTime(11, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.PREOP)
                    .setSurface(Surface.DIRT)
                    .setLength(1600)
                    .setField(TOKYO)
                    .addAttr(0, 1)
                    ::create
    );

    public static final ResourceKey<Race> BEGONIA_SHO = simpleRegister("begonia_sho",
            new RaceBuilder().setTime(11, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.PREOP)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .setField(TOKYO)
                    .addAttr(1, 3)
                    ::create
    );

    public static final ResourceKey<Race> SHIRAGIKU_SHO = simpleRegister("shiragiku_sho",
            new RaceBuilder().setTime(11, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.PREOP)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .setField(KYOTO)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> HABOTAN_SHO = simpleRegister("habotan_sho",
            new RaceBuilder().setTime(11, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.PREOP)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .setField(NAKAYAMA)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> KOYAMAKI_SHO = simpleRegister("koyamaki_sho",
            new RaceBuilder().setTime(11, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.PREOP)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .setField(CHUKYO)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> KEIO_HAI_JUNIOR_STAKES = simpleRegister("keio_hai_junior_stakes",
            new RaceBuilder().setTime(11, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.GII)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    .setField(TOKYO)
                    .addAttr(1, 2)
                    ::create
    );

    public static final ResourceKey<Race> DAILY_HAI_JUNIOR_STAKES = simpleRegister("daily_hai_junior_stakes",
            new RaceBuilder().setTime(11, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.GII)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .setField(KYOTO)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> FANTASY_STAKES = simpleRegister("fantasy_stakes",
            new RaceBuilder().setTime(11, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.GIII)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    .setField(KYOTO)
                    ::create
    );

    public static final ResourceKey<Race> FUKUSHIMA_JUNIOR_STAKES = simpleRegister("fukushima_junior_stakes",
            new RaceBuilder().setTime(11, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    .setField(FUKUSHIMA)
                    ::create
    );

    public static final ResourceKey<Race> HYAKUNICHISO_TOKUBETSU = simpleRegister("hyakunichiso_tokubetsu",
            new RaceBuilder().setTime(11, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.PREOP)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .setField(TOKYO)
                    ::create
    );

    public static final ResourceKey<Race> KIMMOKUSEI_TOKUBETSU = simpleRegister("kimmokusei_tokubetsu",
            new RaceBuilder().setTime(11, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.PREOP)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    .setField(FUKUSHIMA)
                    .addAttr(1)
                    ::create
    );

    public static final ResourceKey<Race> OXALIS_SHO = simpleRegister("oxalis_sho",
            new RaceBuilder().setTime(11, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.PREOP)
                    .setSurface(Surface.DIRT)
                    .setLength(1400)
                    .setField(TOKYO)
                    .addAttr(1)
                    ::create
    );

    public static final ResourceKey<Race> KIGIKU_SHO = simpleRegister("kigiku_sho",
            new RaceBuilder().setTime(11, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.PREOP)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .setField(KYOTO)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> HOPE_STAKES = simpleRegister("hope_stakes",
            new RaceBuilder().setTime(12, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.GI)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .setField(NAKAYAMA)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> ZEN_NIPPON_JUNIOR_YUSHUN = simpleRegister("zen_nippon_junior_yushun",
            new RaceBuilder().setTime(12, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.GI)
                    .setSurface(Surface.DIRT)
                    .setLength(1600)
                    .setField(KAWASAKI)
                    .addAttr(4)
                    ::create
    );

    public static final ResourceKey<Race> CHRISTMAS_ROSE_STAKES = simpleRegister("christmas_rose_stakes",
            new RaceBuilder().setTime(12, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    .setField(NAKAYAMA)
                    ::create
    );

    public static final ResourceKey<Race> SENRYO_SHO = simpleRegister("senryo_sho",
            new RaceBuilder().setTime(12, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.PREOP)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .setField(HANSHIN)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> HANSHIN_JUVENILE_FILLIES = simpleRegister("hanshin_juvenile_fillies",
            new RaceBuilder().setTime(12, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.GI)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .setField(HANSHIN)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> ASAHI_HAI_FUTURITY_STAKES = simpleRegister("asahi_hai_futurity_stakes",
            new RaceBuilder().setTime(12, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.GI)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .setField(HANSHIN)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> MANRYO_SHO = simpleRegister("manryo_sho",
            new RaceBuilder().setTime(12, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.PREOP)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    .setField(HANSHIN)
                    ::create
    );

    public static final ResourceKey<Race> KUROMATSU_SHO = simpleRegister("kuromatsu_sho",
            new RaceBuilder().setTime(12, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.PREOP)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    .setField(NAKAYAMA)
                    ::create
    );

    public static final ResourceKey<Race> ERICA_SHO = simpleRegister("erica_sho",
            new RaceBuilder().setTime(12, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.PREOP)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .setField(HANSHIN)
                    .addAttr(3)
                    ::create
    );

    public static final ResourceKey<Race> TSUWABUKI_SHO = simpleRegister("tsuwabuki_sho",
            new RaceBuilder().setTime(12, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.PREOP)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    .setField(CHUKYO)
                    ::create
    );

    public static final ResourceKey<Race> HIIRAGI_SHO = simpleRegister("hiiragi_sho",
            new RaceBuilder().setTime(12, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.PREOP)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .setField(NAKAYAMA)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> SAZANKA_SHO = simpleRegister("sazanka_sho",
            new RaceBuilder().setTime(12, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.PREOP)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    .setField(HANSHIN)
                    ::create
    );

    public static final ResourceKey<Race> KANTSUBAKI_SHO = simpleRegister("kantsubaki_sho",
            new RaceBuilder().setTime(12, false)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.PREOP)
                    .setSurface(Surface.DIRT)
                    .setLength(1400)
                    .setField(CHUKYO)
                    ::create
    );

    public static final ResourceKey<Race> HIMAWARI_SHO = simpleRegister("himawari_sho",
            new RaceBuilder().setTime(7, true)
                    .addYear(Year.JUNIOR)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    .setField(KOKURA)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> WAKAGOMA_STAKES = simpleRegister("wakagoma_stakes",
            new RaceBuilder().setTime(1, true)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .setField(KYOTO)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> CROCUS_STAKES = simpleRegister("crocus_stakes",
            new RaceBuilder().setTime(1, true)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    .setField(TOKYO)
                    .addAttr(1, 2)
                    ::create
    );

    public static final ResourceKey<Race> SHINZAN_KINEN = simpleRegister("shinzan_kinen",
            new RaceBuilder().setTime(1, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GIII)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .setField(KYOTO)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> FAIRY_STAKES = simpleRegister("fairy_stakes",
            new RaceBuilder().setTime(1, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GIII)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .setField(NAKAYAMA)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> KEISEI_HAI = simpleRegister("keisei_hai",
            new RaceBuilder().setTime(1, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GIII)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .setField(NAKAYAMA)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> JUNIOR_CUP = simpleRegister("junior_cup",
            new RaceBuilder().setTime(1, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .setField(NAKAYAMA)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> KOBAI_STAKES = simpleRegister("kobai_stakes",
            new RaceBuilder().setTime(1, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    .setField(KYOTO)
                    ::create
    );

    public static final ResourceKey<Race> HYACINTH_STAKES = simpleRegister("hyacinth_stakes",
            new RaceBuilder().setTime(2, true)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.DIRT)
                    .setLength(1600)
                    .setField(TOKYO)
                    .addAttr(0, 1)
                    ::create
    );

    public static final ResourceKey<Race> SUMIRE_STAKES = simpleRegister("sumire_stakes",
            new RaceBuilder().setTime(2, true)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(2200)
                    .setField(HANSHIN)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> MARGUERITE_STAKES = simpleRegister("marguerite_stakes",
            new RaceBuilder().setTime(2, true)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.DIRT)
                    .setLength(1200)
                    .setField(HANSHIN)
                    ::create
    );

    public static final ResourceKey<Race> KISARAGI_SHO = simpleRegister("kisaragi_sho",
            new RaceBuilder().setTime(2, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GIII)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    .setField(KYOTO)
                    ::create
    );

    public static final ResourceKey<Race> QUEEN_CUP = simpleRegister("queen_cup",
            new RaceBuilder().setTime(2, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GIII)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .setField(TOKYO)
                    .addAttr(1, 3)
                    ::create
    );

    public static final ResourceKey<Race> KYODO_TSUSHIN_HAI = simpleRegister("kyodo_tsushin_hai",
            new RaceBuilder().setTime(2, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GIII)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    .setField(TOKYO)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> ELFIN_STAKES = simpleRegister("elfin_stakes",
            new RaceBuilder().setTime(2, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .setField(KYOTO)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> SPRING_STAKES = simpleRegister("spring_stakes",
            new RaceBuilder().setTime(3, true)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GII)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    .setField(NAKAYAMA)
                    ::create
    );

    public static final ResourceKey<Race> FALCON_STAKES = simpleRegister("falcon_stakes",
            new RaceBuilder().setTime(3, true)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GIII)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    .setField(CHUKYO)
                    ::create
    );

    public static final ResourceKey<Race> FLOWER_CUP = simpleRegister("flower_cup",
            new RaceBuilder().setTime(3, true)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GIII)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    .setField(NAKAYAMA)
                    ::create
    );

    public static final ResourceKey<Race> MAINICHI_HAI = simpleRegister("mainichi_hai",
            new RaceBuilder().setTime(3, true)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GIII)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    .setField(HANSHIN)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> WAKABA_STAKES = simpleRegister("wakaba_stakes",
            new RaceBuilder().setTime(3, true)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .setField(HANSHIN)
                    .addAttr(3)
                    ::create
    );

    public static final ResourceKey<Race> YAYOI_SHO = simpleRegister("yayoi_sho",
            new RaceBuilder().setTime(3, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GII)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .setField(NAKAYAMA)
                    .addAttr(0)
                    ::create
    ); // todo: Full name: 弥生賞ディープインパクト記念(Yayoi Sho Deep Impact Kinen, 弥生赏大震撼纪念), Consider to change registry key or remove this comment

    public static final ResourceKey<Race> FILLIES_REVIEW = simpleRegister("fillies_review",
            new RaceBuilder().setTime(3, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GII)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    .setField(HANSHIN)
                    ::create
    ); // todo: Translation varies: 报知杯雌马赛(zh-cn)/赛马娘赛(zh-tw), confirm one or accept variance in different language files

    public static final ResourceKey<Race> TURNIP_STAKES = simpleRegister("turnip_stakes",
            new RaceBuilder().setTime(3, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GII)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .setField(HANSHIN)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> ANEMONE_STAKES = simpleRegister("anemone_stakes",
            new RaceBuilder().setTime(3, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .setField(NAKAYAMA)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> SHORYU_STAKES = simpleRegister("shoryu_stakes",
            new RaceBuilder().setTime(3, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    .setField(CHUKYO)
                    ::create
    );

    public static final ResourceKey<Race> FLORA_STAKES = simpleRegister("flora_stakes",
            new RaceBuilder().setTime(4, true)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GII)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .setField(TOKYO)
                    ::create
    ); // todo: Translation varies: 芙洛拉锦标(zh-cn1)/费洛拉锦标(zh-cn2)/花仙锦标(zh-tw), confirm at least 1

    public static final ResourceKey<Race> AOBA_SHO = simpleRegister("aoba_sho",
            new RaceBuilder().setTime(4, true)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GII)
                    .setSurface(Surface.TURF)
                    .setLength(2400)
                    .setField(TOKYO)
                    ::create
    );

    public static final ResourceKey<Race> TACHIBANA_STAKES = simpleRegister("tachibana_stakes",
            new RaceBuilder().setTime(4, true)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    .setField(KYOTO)
                    ::create
    );

    public static final ResourceKey<Race> TANGO_STAKES = simpleRegister("tango_stakes",
            new RaceBuilder().setTime(4, true)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    .setField(KYOTO)
                    ::create
    );

    public static final ResourceKey<Race> SWEET_PEA_STAKES = simpleRegister("sweet_pea_stakes",
            new RaceBuilder().setTime(4, true)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    .setField(TOKYO)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> OKA_SHO = simpleRegister("oka_sho",
            new RaceBuilder().setTime(4, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GI)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .setField(HANSHIN)
                    .addAttr(2)
                    .addTags(TRIPLE_TIARA, EIGHT_GREAT_RACES)::create
    );

    public static final ResourceKey<Race> SATSUKI_SHO = simpleRegister("satsuki_sho",
            new RaceBuilder().setTime(4, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GI)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .setField(NAKAYAMA)
                    .addAttr(0)
                    .addTags(TRIPLE_CROWN, EIGHT_GREAT_RACES)::create
    );

    public static final ResourceKey<Race> NEW_ZEALAND_TROPHY = simpleRegister("new_zealand_trophy",
            new RaceBuilder().setTime(4, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GII)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .setField(NAKAYAMA)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> ARLINGTON_CUP = simpleRegister("arlington_cup",
            new RaceBuilder().setTime(4, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GIII)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .setField(HANSHIN)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> FUKURYU_STAKES = simpleRegister("fukuryu_stakes",
            new RaceBuilder().setTime(4, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.DIRT)
                    .setLength(1800)
                    .setField(NAKAYAMA)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> WASURENAGUSA_SHO = simpleRegister("wasurenagusa_sho",
            new RaceBuilder().setTime(4, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .setField(HANSHIN)
                    .addAttr(3)
                    ::create
    );

    public static final ResourceKey<Race> MARINE_CUP = simpleRegister("marine_cup",
            new RaceBuilder().setTime(4, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setSurface(Surface.DIRT)
                    .setLength(1600)
                    .setField(FUNABASHI)
                    ::create
    );

    public static final ResourceKey<Race> OAKS = simpleRegister("oaks",
            new RaceBuilder().setTime(5, true)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GI)
                    .setSurface(Surface.TURF)
                    .setLength(2400)
                    .setField(TOKYO)
                    .addTags(TRIPLE_TIARA, EIGHT_GREAT_RACES)::create
    ); // todo 1: Translation varies: 优骏牝马（日本橡树大赛） (zh-cn1)/奥克斯(zh-tw), choose one or accepts varies in different language files.

    public static final ResourceKey<Race> TOKYO_YUSHUN_JAPANESE_DERBY = simpleRegister("tokyo_yushun_japanese_derby",
            new RaceBuilder().setTime(5, true)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GI)
                    .setSurface(Surface.TURF)
                    .setLength(2400)
                    .setField(TOKYO)
                    .addTags(TRIPLE_CROWN, EIGHT_GREAT_RACES)::create
    );

    public static final ResourceKey<Race> AOI_STAKES = simpleRegister("aoi_stakes",
            new RaceBuilder().setTime(5, true)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GII)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    .setField(KYOTO)
                    ::create
    );

    public static final ResourceKey<Race> HOSU_STAKES = simpleRegister("hosu_stakes",
            new RaceBuilder().setTime(5, true)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.OP)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    .setField(KYOTO)
                    ::create
    );

    public static final ResourceKey<Race> SHIRAYURI_STAKES = simpleRegister("shirayuri_stakes",
            new RaceBuilder().setTime(5, true)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.OP)
                    .setField(KYOTO)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    ::create
    );

    public static final ResourceKey<Race> NHK_MILE_CUP = simpleRegister("nhk_mile_cup",
            new RaceBuilder().setTime(5, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GI)
                    .setField(TOKYO)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .addAttr(1, 3)
                    ::create
    );

    public static final ResourceKey<Race> KYOTO_SHIMBUN_HAI = simpleRegister("kyoto_shimbun_hai",
            new RaceBuilder().setTime(5, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GII)
                    .setField(KYOTO)
                    .setSurface(Surface.TURF)
                    .setLength(2200)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> PRINCIPAL_STAKES = simpleRegister("principal_stakes",
            new RaceBuilder().setTime(5, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.OP)
                    .setField(TOKYO)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    ::create
    );

    public static final ResourceKey<Race> SEIRYU_STAKES = simpleRegister("seiryu_stakes",
            new RaceBuilder().setTime(5, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.OP)
                    .setField(TOKYO)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .addAttr(0, 1)
                    ::create
    );

    public static final ResourceKey<Race> UNICORN_STAKES = simpleRegister("unicorn_stakes",
            new RaceBuilder().setTime(5, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.OP)
                    .setField(TOKYO)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .addAttr(0, 1)
                    ::create
    );

    public static final ResourceKey<Race> TAKARAZUKA_KINEN = simpleRegister("takarazuka_kinen",
            new RaceBuilder().setTime(6, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GI)
                    .setField(HANSHIN)
                    .setSurface(Surface.TURF)
                    .setLength(2200)
                    .addAttr(0)
                    .addTags(SENIOR_SPRING_TRIPLE_CROWN, GRAND_PRIX)
                    ::create
    );

    public static final ResourceKey<Race> HAKODATE_SPRINT_STAKES = simpleRegister("hakodate_sprint_stakes",
            new RaceBuilder().setTime(6, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(HAKODATE)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    ::create
    );

    public static final ResourceKey<Race> AKHAL_TEKE_STAKES = simpleRegister("akhal_teke_stakes",
            new RaceBuilder().setTime(6, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(TOKYO)
                    .setSurface(Surface.DIRT)
                    .setLength(1600)
                    .addAttr(0, 1)
                    ::create
    );

    public static final ResourceKey<Race> YONAGO_STAKES = simpleRegister("yonago_stakes",
            new RaceBuilder().setTime(6, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(HANSHIN)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> ONUMA_STAKES = simpleRegister("onuma_stakes",
            new RaceBuilder().setTime(6, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(HAKODATE)
                    .setSurface(Surface.DIRT)
                    .setLength(1700)
                    ::create
    );

    public static final ResourceKey<Race> PARADISE_STAKES = simpleRegister("paradise_stakes",
            new RaceBuilder().setTime(6, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(TOKYO)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    .addAttr(1, 2)
                    ::create
    );

    public static final ResourceKey<Race> SANNOMIYA_STAKES = simpleRegister("sannomiya_stakes",
            new RaceBuilder().setTime(6, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(HANSHIN)
                    .setSurface(Surface.DIRT)
                    .setLength(1800)
                    ::create
    );

    public static final ResourceKey<Race> KANTO_OAKS = simpleRegister("kanto_oaks",
            new RaceBuilder().setTime(6, true)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GII)
                    .setField(KAWASAKI)
                    .setSurface(Surface.DIRT)
                    .setLength(1600)
                    .addAttr(1, 4)
                    ::create
    );

    public static final ResourceKey<Race> YASUDA_KINEN = simpleRegister("yasuda_kinen",
            new RaceBuilder().setTime(6, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GI)
                    .setField(TOKYO)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .addTags(MILE)
                    .addAttr(1, 3)
                    ::create
    );

    public static final ResourceKey<Race> NARUO_KINEN = simpleRegister("naruo_kinen",
            new RaceBuilder().setTime(6, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(HANSHIN)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .addAttr(3)
                    ::create
    );

    public static final ResourceKey<Race> MERMAID_STAKES = simpleRegister("mermaid_stakes",
            new RaceBuilder().setTime(6, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(HANSHIN)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .addAttr(3)
                    ::create
    );

    public static final ResourceKey<Race> EPSOM_CUP = simpleRegister("epsom_cup",
            new RaceBuilder().setTime(6, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(TOKYO)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> TEMPOZAN_STAKES = simpleRegister("tempozan_stakes",
            new RaceBuilder().setTime(6, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(HANSHIN)
                    .setSurface(Surface.DIRT)
                    .setLength(1400)
                    ::create
    );

    public static final ResourceKey<Race> SLEIPNIR_STAKES = simpleRegister("sleipnir_stakes",
            new RaceBuilder().setTime(6, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(TOKYO)
                    .setSurface(Surface.DIRT)
                    .setLength(2100)
                    ::create
    );

    public static final ResourceKey<Race> CHUKYO_KINEN = simpleRegister("chukyo_kinen",
            new RaceBuilder().setTime(7, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(CHUKYO)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> IBIS_SUMMER_DASH = simpleRegister("ibis_summer_dash",
            new RaceBuilder().setTime(7, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(NIIGATA)
                    .setSurface(Surface.TURF)
                    .setLength(1000)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> QUEEN_STAKES = simpleRegister("queen_stakes",
            new RaceBuilder().setTime(7, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(SAPPORO)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    ::create
    );

    public static final ResourceKey<Race> MERCURY_CUP = simpleRegister("mercury_cup",
            new RaceBuilder().setTime(7, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(MORIOKA)
                    .setSurface(Surface.DIRT)
                    .setLength(2000)
                    .addAttr(1)
                    ::create
    );

    public static final ResourceKey<Race> FUKUSHIMA_TV_OPEN = simpleRegister("fukushima_tv_open",
            new RaceBuilder().setTime(7, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(FUKUSHIMA)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    ::create
    );

    public static final ResourceKey<Race> JAPAN_DIRT_DERBY = simpleRegister("japan_dirt_derby",
            new RaceBuilder().setTime(7, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GI)
                    .setField(OHI)
                    .setSurface(Surface.DIRT)
                    .setLength(2000)
                    .addAttr(1)
                    ::create
    );

    public static final ResourceKey<Race> RADIO_NIKKEI_SHO = simpleRegister("radio_nikkei_sho",
            new RaceBuilder().setTime(7, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GIII)
                    .setField(FUKUSHIMA)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    .addAttr(1)
                    ::create
    );

    public static final ResourceKey<Race> CBC_SHO = simpleRegister("cbc_sho",
            new RaceBuilder().setTime(7, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(CHUKYO)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    ::create
    );

    public static final ResourceKey<Race> PROCYON_STAKES = simpleRegister("procyon_stakes",
            new RaceBuilder().setTime(7, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(CHUKYO)
                    .setSurface(Surface.DIRT)
                    .setLength(1400)
                    ::create
    );

    public static final ResourceKey<Race> TANABATA_SHO = simpleRegister("tanabata_sho",
            new RaceBuilder().setTime(7, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(FUKUSHIMA)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .addAttr(1)
                    ::create
    );

    public static final ResourceKey<Race> HAKODATE_KINEN = simpleRegister("hakodate_kinen",
            new RaceBuilder().setTime(7, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(HAKODATE)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> SPARKING_LADY_CUP = simpleRegister("sparking_lady_cup",
            new RaceBuilder().setTime(7, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(KAWASAKI)
                    .setSurface(Surface.DIRT)
                    .setLength(1600)
                    .addAttr(4)
                    ::create
    );

    public static final ResourceKey<Race> TOMOE_SHO = simpleRegister("tomoe_sho",
            new RaceBuilder().setTime(7, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(HAKODATE)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> MARINE_STAKES = simpleRegister("marine_stakes",
            new RaceBuilder().setTime(7, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(HAKODATE)
                    .setSurface(Surface.TURF)
                    .setLength(1700)
                    ::create
    );

    public static final ResourceKey<Race> MEITETSU_HAI = simpleRegister("meitetsu_hai",
            new RaceBuilder().setTime(7, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(CHUKYO)
                    .setSurface(Surface.DIRT)
                    .setLength(1800)
                    .addAttr(1)
                    ::create
    );

    public static final ResourceKey<Race> SAPPORO_KINEN = simpleRegister("sapporo_kinen",
            new RaceBuilder().setTime(8, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(SAPPORO)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> KITAKYUSHU_KINEN = simpleRegister("kitakyushu_kinen",
            new RaceBuilder().setTime(8, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(KOKURA)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> KEENELAND_CUP = simpleRegister("keeneland_cup",
            new RaceBuilder().setTime(8, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(SAPPORO)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    ::create
    );

    public static final ResourceKey<Race> CLUSTER_CUP = simpleRegister("cluster_cup",
            new RaceBuilder().setTime(8, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(MORIOKA)
                    .setSurface(Surface.DIRT)
                    .setLength(1200)
                    .addAttr(1)
                    ::create
    );

    public static final ResourceKey<Race> NST_SHO = simpleRegister("nst_sho",
            new RaceBuilder().setTime(8, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(NIIGATA)
                    .setSurface(Surface.DIRT)
                    .setLength(1800)
                    ::create
    );

    public static final ResourceKey<Race> BSN_SHO = simpleRegister("bsn_sho",
            new RaceBuilder().setTime(8, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(NIIGATA)
                    .setSurface(Surface.DIRT)
                    .setLength(1200)
                    .addAttr(4)
                    ::create
    );

    public static final ResourceKey<Race> KOKURA_NIKKEI_OPEN = simpleRegister("kokura_nikkei_open",
            new RaceBuilder().setTime(8, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(KOKURA)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    ::create
    );

    public static final ResourceKey<Race> TOKI_STAKES = simpleRegister("toki_stakes",
            new RaceBuilder().setTime(8, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(NIIGATA)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    ::create
    );

    public static final ResourceKey<Race> LEOPARD_STAKES = simpleRegister("leopard_stakes",
            new RaceBuilder().setTime(8, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GIII)
                    .setField(NIIGATA)
                    .setSurface(Surface.DIRT)
                    .setLength(1800)
                    .addAttr(4)
                    ::create
    );

    public static final ResourceKey<Race> KOKURA_KINEN = simpleRegister("kokura_kinen",
            new RaceBuilder().setTime(8, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(KOKURA)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> SEKIYA_KINEN = simpleRegister("sekiya_kinen",
            new RaceBuilder().setTime(8, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(NIIGATA)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    ::create
    );

    public static final ResourceKey<Race> ELM_STAKES = simpleRegister("elm_stakes",
            new RaceBuilder().setTime(8, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(SAPPORO)
                    .setSurface(Surface.DIRT)
                    .setLength(1700)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> SAPPORO_NIKKEI_STAKES = simpleRegister("sapporo_nikkei_stakes",
            new RaceBuilder().setTime(8, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(SAPPORO)
                    .setSurface(Surface.TURF)
                    .setLength(2600)
                    .addAttr(1)
                    ::create
    );

    public static final ResourceKey<Race> UHB_SHO = simpleRegister("uhb_sho",
            new RaceBuilder().setTime(8, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(SAPPORO)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    ::create
    );

    public static final ResourceKey<Race> ASO_STAKES = simpleRegister("aso_stakes",
            new RaceBuilder().setTime(8, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(KOKURA)
                    .setSurface(Surface.DIRT)
                    .setLength(1700)
                    ::create
    );

    public static final ResourceKey<Race> KANETSU_STAKES = simpleRegister("kanetsu_stakes",
            new RaceBuilder().setTime(8, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(NIIGATA)
                    .setSurface(Surface.TURF)
                    .setLength(1700)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> ST_LITE_KINEN = simpleRegister("st_lite_kinen",
            new RaceBuilder().setTime(9, true)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GII)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.TURF)
                    .setLength(2200)
                    .addAttr(1, 3)
                    ::create
    );

    public static final ResourceKey<Race> KOBE_SHIMBUN_HAI = simpleRegister("kobe_shimbun_hai",
            new RaceBuilder().setTime(9, true)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GII)
                    .setField(HANSHIN)
                    .setSurface(Surface.TURF)
                    .setLength(2400)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> SPRINTERS_STAKES = simpleRegister("sprinters_stakes",
            new RaceBuilder().setTime(9, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GI)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    .addTags(SPRINT)
                    ::create
    );

    public static final ResourceKey<Race> ALL_COMERS = simpleRegister("all_comers",
            new RaceBuilder().setTime(9, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.TURF)
                    .setLength(2200)
                    .addAttr(1, 3)
                    ::create
    );

    public static final ResourceKey<Race> SAZANKA_TV_CUP = simpleRegister("sazanka_tv_cup",
            new RaceBuilder().setTime(9, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(FUNABASHI)
                    .setSurface(Surface.DIRT)
                    .setLength(1800)
                    ::create
    );
    // 编者吐槽: 这玩意原名叫日本TV杯 某游戏不知道发什么癫把整个名字改成山茶花TV杯
    // 害的上网找了半天没找到翻译 差点怀疑人生

    public static final ResourceKey<Race> SIRIUS_STAKES = simpleRegister("sirius_stakes",
            new RaceBuilder().setTime(9, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(HANSHIN)
                    .setSurface(Surface.DIRT)
                    .setLength(2000)
                    .addAttr(1, 2)
                    ::create
    );

    public static final ResourceKey<Race> PORT_ISLAND_STAKES = simpleRegister("port_island_stakes",
            new RaceBuilder().setTime(9, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(HANSHIN)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> NAGATSUKI_STAKES = simpleRegister("nagatsuki_stakes",
            new RaceBuilder().setTime(9, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.DIRT)
                    .setLength(1200)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> ROSE_STAKES = simpleRegister("rose_stakes",
            new RaceBuilder().setTime(9, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GII)
                    .setField(HANSHIN)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> SHION_STAKES = simpleRegister("shion_stakes",
            new RaceBuilder().setTime(9, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GIII)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> CENTAUR_STAKES = simpleRegister("centaur_stakes",
            new RaceBuilder().setTime(9, false)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GII)
                    .setField(HANSHIN)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    ::create
    );

    public static final ResourceKey<Race> NIIGATA_KINEN = simpleRegister("niigata_kinen",
            new RaceBuilder().setTime(9, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(NIIGATA)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .addAttr(1, 2)
                    ::create
    );

    public static final ResourceKey<Race> KEISEI_HAI_AUTUMN_HANDICAP = simpleRegister("keisei_hai_autumn_handicap",
            new RaceBuilder().setTime(9, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> TANCHO_STAKES = simpleRegister("tancho_stakes",
            new RaceBuilder().setTime(9, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(SAPPORO)
                    .setSurface(Surface.TURF)
                    .setLength(2600)
                    .addAttr(1)
                    ::create
    );

    public static final ResourceKey<Race> ENIF_STAKES = simpleRegister("enif_stakes",
            new RaceBuilder().setTime(9, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    ::create
    );

    public static final ResourceKey<Race> RADIO_NIPPON_SHO = simpleRegister("radio_nippon_sho",
            new RaceBuilder().setTime(9, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> SHUKA_SHO = simpleRegister("shuka_sho",
            new RaceBuilder().setTime(10, true)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GI)
                    .setField(KYOTO)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .addTags(TRIPLE_TIARA)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> KIKUKA_SHO = simpleRegister("kikuka_sho",
            new RaceBuilder().setTime(10, true)
                    .addYear(Year.CLASSIC)
                    .setRanking(RaceRanking.GI)
                    .setField(KYOTO)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .addAttr(2, 4)
                    .addTags(TRIPLE_CROWN, EIGHT_GREAT_RACES)
                    ::create
    );

    public static final ResourceKey<Race> TENNO_SHO_AUTUMN = simpleRegister("tenno_sho_autumn",
            new RaceBuilder().setTime(10, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GI)
                    .setField(TOKYO)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .addTags(SENIOR_AUTUMN_TRIPLE_CROWN, EIGHT_GREAT_RACES)
                    ::create
    );

    public static final ResourceKey<Race> SWAN_STAKES = simpleRegister("swan_stakes",
            new RaceBuilder().setTime(10, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(KYOTO)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    ::create
    );

    public static final ResourceKey<Race> FUJI_STAKES = simpleRegister("fuji_stakes",
            new RaceBuilder().setTime(10, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(TOKYO)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .addAttr(1, 3)
                    ::create
    );

    public static final ResourceKey<Race> MUROMAJI_STAKES = simpleRegister("muromaji_stakes",
            new RaceBuilder().setTime(10, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(KYOTO)
                    .setSurface(Surface.DIRT)
                    .setLength(1200)
                    ::create
    );

    public static final ResourceKey<Race> BRAZIL_CUP = simpleRegister("brazil_cup",
            new RaceBuilder().setTime(10, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(TOKYO)
                    .setSurface(Surface.DIRT)
                    .setLength(2100)
                    ::create
    );

    public static final ResourceKey<Race> CASSIOPEIA_STAKES = simpleRegister("cassiopeia_stakes",
            new RaceBuilder().setTime(10, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(KYOTO)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    ::create
    );

    public static final ResourceKey<Race> LUMIERE_AUTUMN_DASH = simpleRegister("lumiere_autumn_dash",
            new RaceBuilder().setTime(10, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(NIIGATA)
                    .setSurface(Surface.TURF)
                    .setLength(1000)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> MILE_CHAMPIONSHIP_NANBU_HAI = simpleRegister("mile_championship_nanbu_hai",
            new RaceBuilder().setTime(10, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GI)
                    .setField(MORIOKA)
                    .setSurface(Surface.DIRT)
                    .setLength(1600)
                    .addAttr(1, 4)
                    ::create
    );

    public static final ResourceKey<Race> MAINICHI_OKAN = simpleRegister("mainichi_okan",
            new RaceBuilder().setTime(10, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(TOKYO)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> KYOTO_DAISHOTEN = simpleRegister("kyoto_daishoten",
            new RaceBuilder().setTime(10, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(KYOTO)
                    .setSurface(Surface.TURF)
                    .setLength(2400)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> FUCHU_UMAMUSUME_STAKES = simpleRegister("fuchu_umamusume_stakes",
            new RaceBuilder().setTime(10, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(TOKYO)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> LADIES_PRELUDE = simpleRegister("ladies_prelude",
            new RaceBuilder().setTime(10, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(OHI)
                    .setSurface(Surface.DIRT)
                    .setLength(1800)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> TOKYO_HAI = simpleRegister("tokyo_hai",
            new RaceBuilder().setTime(10, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(OHI)
                    .setSurface(Surface.DIRT)
                    .setLength(1200)
                    .addAttr(3, 4)
                    ::create
    );

    public static final ResourceKey<Race> OPAL_STAKES = simpleRegister("opal_stakes",
            new RaceBuilder().setTime(10, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(KYOTO)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    ::create
    );

    public static final ResourceKey<Race> GREEN_CHANNEL_CUP = simpleRegister("green_channel_cup",
            new RaceBuilder().setTime(10, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(TOKYO)
                    .setSurface(Surface.DIRT)
                    .setLength(1400)
                    .addAttr(1)
                    ::create
    );

    public static final ResourceKey<Race> OCTOBER_STAKES = simpleRegister("october_stakes",
            new RaceBuilder().setTime(10, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(TOKYO)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    ::create
    );

    public static final ResourceKey<Race> SHINETSU_STAKES = simpleRegister("shinetsu_stakes",
            new RaceBuilder().setTime(10, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(NIIGATA)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    ::create
    );

    public static final ResourceKey<Race> UZUMASA_STAKES = simpleRegister("uzumasa_stakes",
            new RaceBuilder().setTime(10, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(KYOTO)
                    .setSurface(Surface.DIRT)
                    .setLength(1800)
                    ::create
    );

    public static final ResourceKey<Race> MILE_CHAMPIONSHIP = simpleRegister("mile_championship",
            new RaceBuilder().setTime(11, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GI)
                    .setField(KYOTO)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .addTags(MILE)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> JAPAN_CUP = simpleRegister("japan_cup",
            new RaceBuilder().setTime(11, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GI)
                    .setField(TOKYO)
                    .setSurface(Surface.TURF)
                    .setLength(2400)
                    .addTags(SENIOR_AUTUMN_TRIPLE_CROWN)
                    ::create
    );

    public static final ResourceKey<Race> KEIHAN_CUP = simpleRegister("keihan_cup",
            new RaceBuilder().setTime(11, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(KYOTO)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    ::create
    );

    public static final ResourceKey<Race> ANDROMEDA_STAKES = simpleRegister("andromeda_stakes",
            new RaceBuilder().setTime(11, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(TOKYO)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> SHIMOTSUKI_STAKES = simpleRegister("shimotsuki_stakes",
            new RaceBuilder().setTime(11, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(TOKYO)
                    .setSurface(Surface.DIRT)
                    .setLength(1400)
                    .addAttr(1)
                    ::create
    );

    public static final ResourceKey<Race> FUKUSHIMA_MINYU_CUP = simpleRegister("fukushima_minyu_cup",
            new RaceBuilder().setTime(11, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(FUKUSHIMA)
                    .setSurface(Surface.DIRT)
                    .setLength(1700)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> CAPITAL_STAKES = simpleRegister("capital_stakes",
            new RaceBuilder().setTime(11, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(TOKYO)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .addAttr(1, 3)
                    ::create
    );

    public static final ResourceKey<Race> AUTUMN_LEAF_STAKES = simpleRegister("autumn_leaf_stakes",
            new RaceBuilder().setTime(11, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(KYOTO)
                    .setSurface(Surface.DIRT)
                    .setLength(1200)
                    ::create
    );

    public static final ResourceKey<Race> QUEEN_ELIZABETH_II_CUP = simpleRegister("queen_elizabeth_ii_cup",
            new RaceBuilder().setTime(11, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GI)
                    .setField(KYOTO)
                    .setSurface(Surface.TURF)
                    .setLength(2200)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> JBC_LADIES_CLASSIC = simpleRegister("jbc_ladies_classic",
            new RaceBuilder().setTime(11, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GI)
                    .setField(OHI)
                    .setSurface(Surface.DIRT)
                    .setLength(1800)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> JBC_SPRINT = simpleRegister("jbc_sprint",
            new RaceBuilder().setTime(11, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GI)
                    .setField(OHI)
                    .setSurface(Surface.DIRT)
                    .setLength(1200)
                    .addAttr(3, 4)
                    ::create
    );

    public static final ResourceKey<Race> JBC_CLASSIC = simpleRegister("jbc_classic",
            new RaceBuilder().setTime(11, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GI)
                    .setField(OHI)
                    .setSurface(Surface.DIRT)
                    .setLength(2000)
                    .addAttr(1)
                    ::create
    );

    public static final ResourceKey<Race> COPA_REPUBLICA_ARGENTINA = simpleRegister("copa_republica_argentina",
            new RaceBuilder().setTime(11, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(TOKYO)
                    .setSurface(Surface.TURF)
                    .setLength(2500)
                    .addAttr(1)
                    ::create
    );

    public static final ResourceKey<Race> MIYAKO_STAKES = simpleRegister("miyako_stakes",
            new RaceBuilder().setTime(11, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(KYOTO)
                    .setSurface(Surface.DIRT)
                    .setLength(1800)
                    ::create
    );

    public static final ResourceKey<Race> MUSASHINO_STAKES = simpleRegister("musashino_stakes",
            new RaceBuilder().setTime(11, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(TOKYO)
                    .setSurface(Surface.DIRT)
                    .setLength(1600)
                    .addAttr(0, 1)
                    ::create
    );

    public static final ResourceKey<Race> FUKUSHIMA_KINEN = simpleRegister("fukushima_kinen",
            new RaceBuilder().setTime(11, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(FUKUSHIMA)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .addAttr(1)
                    ::create
    );

    public static final ResourceKey<Race> ORO_CUP = simpleRegister("oro_cup",
            new RaceBuilder().setTime(11, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(TOKYO)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    .addAttr(1, 2)
                    ::create
    );

    public static final ResourceKey<Race> ARIMA_KINEN = simpleRegister("arima_kinen",
            new RaceBuilder().setTime(12, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GI)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.TURF)
                    .setLength(2500)
                    .addAttr(1, 3)
                    .addTags(SENIOR_AUTUMN_TRIPLE_CROWN, GRAND_PRIX, EIGHT_GREAT_RACES)
                    ::create
    );

    public static final ResourceKey<Race> TOKYO_DAISHOTEN = simpleRegister("tokyo_daishoten",
            new RaceBuilder().setTime(12, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GI)
                    .setField(OHI)
                    .setSurface(Surface.DIRT)
                    .setLength(2000)
                    .addAttr(1)
                    ::create
    );

    public static final ResourceKey<Race> HANSHIN_CUP = simpleRegister("hanshin_cup",
            new RaceBuilder().setTime(12, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(HANSHIN)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    ::create
    );

    public static final ResourceKey<Race> GALAXY_STAKES = simpleRegister("galaxy_stakes",
            new RaceBuilder().setTime(12, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(HANSHIN)
                    .setSurface(Surface.DIRT)
                    .setLength(1400)
                    ::create
    );

    public static final ResourceKey<Race> BETELGEUSE_STAKES = simpleRegister("betelgeuse_stakes",
            new RaceBuilder().setTime(12, true)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(HANSHIN)
                    .setSurface(Surface.DIRT)
                    .setLength(1800)
                    ::create
    );

    public static final ResourceKey<Race> CHAMPIONS_CUP = simpleRegister("champions_cup",
            new RaceBuilder().setTime(12, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GI)
                    .setField(CHUKYO)
                    .setSurface(Surface.DIRT)
                    .setLength(1800)
                    .addTags(DIRT)
                    .addAttr(1)
                    ::create
    );

    public static final ResourceKey<Race> STAYERS_STAKES = simpleRegister("stayers_stakes",
            new RaceBuilder().setTime(12, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.TURF)
                    .setLength(3600)
                    .addAttr(1)
                    ::create
    );

    public static final ResourceKey<Race> CHALLENGER_CUP = simpleRegister("challenger_cup",
            new RaceBuilder().setTime(12, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(HANSHIN)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .addAttr(3)
                    ::create
    );

    public static final ResourceKey<Race> CHUNICHI_SHIMBUN_HAI = simpleRegister("chunichi_shimbun_hai",
            new RaceBuilder().setTime(12, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(CHUKYO)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    ::create
    );

    public static final ResourceKey<Race> CAPELLA_STAKES = simpleRegister("capella_stakes",
            new RaceBuilder().setTime(12, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.DIRT)
                    .setLength(1200)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> TURQUOISE_STAKES = simpleRegister("turquoise_stakes",
            new RaceBuilder().setTime(12, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> QUEEN_SHO = simpleRegister("queen_sho",
            new RaceBuilder().setTime(12, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(FUNABASHI)
                    .setSurface(Surface.DIRT)
                    .setLength(1800)
                    ::create
    );

    public static final ResourceKey<Race> LAPIS_LAZULI_STAKES = simpleRegister("lapis_lazuli_stakes",
            new RaceBuilder().setTime(12, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    ::create
    ); // 编者注: 这里的Lapis Lazuli就是mc里面的那个Lapis Lazuli, 青金石（比赛名: 青金石锦标）

    public static final ResourceKey<Race> SHIWASU_STAKES = simpleRegister("shiwasu_stakes",
            new RaceBuilder().setTime(12, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.DIRT)
                    .setLength(1800)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> RIGEL_STAKES = simpleRegister("rigel_stakes",
            new RaceBuilder().setTime(12, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(HANSHIN)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .addAttr(2)
                    ::create
    ); // 编者注: Rigel不是误拼 赛马娘动画中的Rigil队中的Rigil实际上是Rigil Kentaurus, 南门二 而Rigel是参宿七(Rigel A)

    public static final ResourceKey<Race> TANZANITE_STAKES = simpleRegister("tanzanite_stakes",
            new RaceBuilder().setTime(12, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(HANSHIN)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    ::create
    );

    public static final ResourceKey<Race> DECEMBER_STAKES = simpleRegister("december_stakes",
            new RaceBuilder().setTime(12, false)
                    .addYear(Year.CLASSIC, Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    ::create
    );

    public static final ResourceKey<Race> TOKAI_STAKES = simpleRegister("tokai_stakes",
            new RaceBuilder().setTime(1, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(CHUKYO)
                    .setSurface(Surface.TURF)
                    .setLength(2200)
                    .addAttr(1)
                    ::create
    );

    public static final ResourceKey<Race> AMERICAN_JOCKEY_CLUB_CUP = simpleRegister("american_jockey_club_cup",
            new RaceBuilder().setTime(1, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.TURF)
                    .setLength(2200)
                    .addAttr(1, 3)
                    ::create
    );

    public static final ResourceKey<Race> SLIK_ROAD_STAKES = simpleRegister("slik_road_stakes",
            new RaceBuilder().setTime(1, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(KYOTO)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    ::create
    );

    public static final ResourceKey<Race> NEGISHI_STAKES = simpleRegister("negishi_stakes",
            new RaceBuilder().setTime(1, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(TOKYO)
                    .setSurface(Surface.DIRT)
                    .setLength(1400)
                    .addAttr(1)
                    ::create
    );

    public static final ResourceKey<Race> TCK_JO_O_HAI = simpleRegister("tck_jo_o_hai",
            new RaceBuilder().setTime(1, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(OHI)
                    .setSurface(Surface.DIRT)
                    .setLength(1800)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> SUBARU_STAKES = simpleRegister("subaru_stakes",
            new RaceBuilder().setTime(1, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(KYOTO)
                    .setSurface(Surface.DIRT)
                    .setLength(1400)
                    ::create
    );

    public static final ResourceKey<Race> SHIRAFUJI_STAKES = simpleRegister("shirafuji_stakes",
            new RaceBuilder().setTime(1, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(TOKYO)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    ::create
    );

    public static final ResourceKey<Race> NIKKEI_SHINSHUN_HAI = simpleRegister("nikkei_shinshun_hai",
            new RaceBuilder().setTime(1, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(KYOTO)
                    .setSurface(Surface.TURF)
                    .setLength(2400)
                    .addAttr(1)
                    ::create
    );

    public static final ResourceKey<Race> KYOTO_KIMPAI = simpleRegister("kyoto_kimpai",
            new RaceBuilder().setTime(1, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(KYOTO)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> NAKAYAMA_KIMPAI = simpleRegister("nakayama_kimpai",
            new RaceBuilder().setTime(1, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> AICHI_HAI = simpleRegister("aichi_hai",
            new RaceBuilder().setTime(1, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(CHUKYO)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    ::create
    );

    public static final ResourceKey<Race> MANYO_STAKES = simpleRegister("manyo_stakes",
            new RaceBuilder().setTime(1, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(KYOTO)
                    .setSurface(Surface.TURF)
                    .setLength(3000)
                    .addAttr(2, 4)
                    ::create
    );

    public static final ResourceKey<Race> YODO_TANKYORI_STAKES = simpleRegister("yodo_tankyori_stakes",
            new RaceBuilder().setTime(1, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(KYOTO)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    ::create
    );

    public static final ResourceKey<Race> POLLUX_STAKES = simpleRegister("pollux_stakes",
            new RaceBuilder().setTime(1, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.DIRT)
                    .setLength(1800)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> JANRUARY_STAKES = simpleRegister("janruary_stakes",
            new RaceBuilder().setTime(1, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.DIRT)
                    .setLength(1200)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> NEW_YEAR_STAKES = simpleRegister("new_year_stakes",
            new RaceBuilder().setTime(1, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> CARBUNCLE_STAKES = simpleRegister("carbuncle_stakes",
            new RaceBuilder().setTime(1, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    ::create
    );

    public static final ResourceKey<Race> FEBRUARY_STAKES = simpleRegister("february_stakes",
            new RaceBuilder().setTime(2, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GI)
                    .setField(TOKYO)
                    .setSurface(Surface.DIRT)
                    .setLength(1600)
                    .addTags(DIRT)
                    .addAttr(0, 1)
                    ::create
    );

    public static final ResourceKey<Race> NAKAYAMA_KINEN = simpleRegister("nakayama_kinen",
            new RaceBuilder().setTime(2, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    ::create
    );

    public static final ResourceKey<Race> KYOTO_UMAMUSUME_STAKES = simpleRegister("kyoto_umamusume_stakes",
            new RaceBuilder().setTime(2, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(KYOTO)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    ::create
    );

    public static final ResourceKey<Race> DIAMOND_STAKES = simpleRegister("diamond_stakes",
            new RaceBuilder().setTime(2, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(TOKYO)
                    .setSurface(Surface.TURF)
                    .setLength(3400)
                    ::create
    );

    public static final ResourceKey<Race> KOKURA_DAISHOTEN = simpleRegister("kokura_daishoten",
            new RaceBuilder().setTime(2, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(KOKURA)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    ::create
    );

    public static final ResourceKey<Race> HANKYU_HAI = simpleRegister("hankyu_hai",
            new RaceBuilder().setTime(2, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(HANSHIN)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    ::create
    );

    public static final ResourceKey<Race> SOBU_STAKES = simpleRegister("sobu_stakes",
            new RaceBuilder().setTime(2, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.DIRT)
                    .setLength(1800)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> KITAKYUSHU_TANKYORI_STAKES = simpleRegister("kitakyushu_tankyori_stakes",
            new RaceBuilder().setTime(2, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(KOKURA)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> KAWASAKI_KINEN = simpleRegister("kawasaki_kinen",
            new RaceBuilder().setTime(2, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GI)
                    .setField(KAWASAKI)
                    .setSurface(Surface.DIRT)
                    .setLength(2100)
                    .addAttr(1, 4)
                    ::create
    );

    public static final ResourceKey<Race> KYOTO_KINEN = simpleRegister("kyoto_kinen",
            new RaceBuilder().setTime(2, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(KYOTO)
                    .setSurface(Surface.TURF)
                    .setLength(2200)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> TOKYO_SHIMBUN_HAI = simpleRegister("tokyo_shimbun_hai",
            new RaceBuilder().setTime(2, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(TOKYO)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .addAttr(1, 3)
                    ::create
    );

    public static final ResourceKey<Race> DAIWA_STAKES = simpleRegister("daiwa_stakes",
            new RaceBuilder().setTime(2, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(KYOTO)
                    .setSurface(Surface.DIRT)
                    .setLength(1200)
                    ::create
    );

    public static final ResourceKey<Race> RAKUYO_STAKES = simpleRegister("rakuyo_stakes",
            new RaceBuilder().setTime(2, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(KYOTO)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> ALDEBARAN_STAKES = simpleRegister("aldebaran_stakes",
            new RaceBuilder().setTime(2, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(KYOTO)
                    .setSurface(Surface.DIRT)
                    .setLength(1900)
                    ::create
    );

    public static final ResourceKey<Race> VALENTINE_STAKES = simpleRegister("valentine_stakes",
            new RaceBuilder().setTime(2, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(TOKYO)
                    .setSurface(Surface.DIRT)
                    .setLength(1400)
                    .addAttr(1)
                    ::create
    );

    public static final ResourceKey<Race> TAKAMATSUNOMIYA_KINEN = simpleRegister("takamatsunomiya_kinen",
            new RaceBuilder().setTime(3, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GI)
                    .setField(HANSHIN)
                    .setSurface(Surface.DIRT)
                    .setLength(1200)
                    .addTags(SPRINT)
                    ::create
    );

    public static final ResourceKey<Race> OSAKA_HAI = simpleRegister("osaka_hai",
            new RaceBuilder().setTime(3, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GI)
                    .setField(HANSHIN)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .addAttr(3)
                    ::create
    );

    public static final ResourceKey<Race> HANSHIN_DAISHOTEN = simpleRegister("hanshin_daishoten",
            new RaceBuilder().setTime(3, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(HANSHIN)
                    .setSurface(Surface.TURF)
                    .setLength(3000)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> NIKKEI_SHO = simpleRegister("nikkei_sho",
            new RaceBuilder().setTime(3, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.TURF)
                    .setLength(2500)
                    .addAttr(1, 3)
                    ::create
    );

    public static final ResourceKey<Race> DIOLITE_KINEN = simpleRegister("diolite_kinen",
            new RaceBuilder().setTime(3, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(FUNABASHI)
                    .setSurface(Surface.DIRT)
                    .setLength(2400)
                    .addAttr(1)
                    ::create
    );
    /*
    * 编者注:
    * 这场比赛(ダイオライト記念)的中文/英文翻译都有歧义，一说Diolite Kinen(大尾光纪念赛, 简中, HKJC采用翻译), 一说Diorite Kinen(闪长岩纪念赛, 繁中采用)
    * 编者认为大尾光纪念赛更加符合原意，原因如下：
    * 根据地方竞马全国协会(NAR)在官方网站对ダイオライト記念的历史介绍中，有:
    * > 英国から輸入され千葉県の下総御料牧場に繋養された種牡馬ダイオライトの名を冠した伝統の重賞。
    * > (翻译: 这项传统重赏赛事以种牡马“ダイオライト (Diolite)”命名。该马自英国引入，并被饲养在千叶县的下总御料牧场。)
    * 而Diolite是官方的欧字表记。
    * 所以这场比赛的英文应当采用这匹马的欧字表记，即Diolite Kinen
    * 而中文则应该采用这匹马的中文译名"大尾光"(HKJC在粤语中做的音译+意译的尝试), 或者迪奥莱特 / 戴奥莱特, 但"闪长岩"的确偏离原意。
    */

    public static final ResourceKey<Race> MARCH_STAKES = simpleRegister("march_stakes",
            new RaceBuilder().setTime(3, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.DIRT)
                    .setLength(1800)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> CHIBA_STAKES = simpleRegister("chiba_stakes",
            new RaceBuilder().setTime(3, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.DIRT)
                    .setLength(1200)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> ROKKO_STAKES = simpleRegister("rokko_stakes",
            new RaceBuilder().setTime(3, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(HANSHIN)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> KINKO_SHO = simpleRegister("kinko_sho",
            new RaceBuilder().setTime(3, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(CHUKYO)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    ::create
    );

    public static final ResourceKey<Race> EMPRESS_HAI = simpleRegister("empress_hai",
            new RaceBuilder().setTime(3, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(KAWASAKI)
                    .setSurface(Surface.DIRT)
                    .setLength(2100)
                    .addAttr(1, 4)
                    ::create
    );

    public static final ResourceKey<Race> OCEAN_STAKES = simpleRegister("ocean_stakes",
            new RaceBuilder().setTime(3, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    ::create
    );

    public static final ResourceKey<Race> NAKAYAMA_UMAMUSUME_STAKES = simpleRegister("nakayama_umamusume_stakes",
            new RaceBuilder().setTime(3, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    ::create
    );

    public static final ResourceKey<Race> OSAKAJO_STAKES = simpleRegister("osakajo_stakes",
            new RaceBuilder().setTime(3, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(HANSHIN)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> POLARIS_STAKES = simpleRegister("polaris_stakes",
            new RaceBuilder().setTime(3, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(HANSHIN)
                    .setSurface(Surface.DIRT)
                    .setLength(1400)
                    ::create
    );

    public static final ResourceKey<Race> NIGAWA_STAKES = simpleRegister("nigawa_stakes",
            new RaceBuilder().setTime(3, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(HANSHIN)
                    .setSurface(Surface.DIRT)
                    .setLength(2000)
                    .addAttr(2, 1)
                    ::create
    );

    public static final ResourceKey<Race> KOCHI_STAKES = simpleRegister("kochi_stakes",
            new RaceBuilder().setTime(3, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> TENNO_SHO_SPRING = simpleRegister("tenno_sho_spring",
            new RaceBuilder().setTime(4, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GI)
                    .setField(KYOTO)
                    .setSurface(Surface.TURF)
                    .setLength(3200)
                    .addTags(SENIOR_SPRING_TRIPLE_CROWN, EIGHT_GREAT_RACES)
                    ::create
    );

    public static final ResourceKey<Race> MILERS_CUP = simpleRegister("milers_cup",
            new RaceBuilder().setTime(4, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(KYOTO)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> FUKUSHIMA_UMAMUSUME_STAKES = simpleRegister("fukushima_umamusume_stakes",
            new RaceBuilder().setTime(4, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(FUKUSHIMA)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    .addAttr(1)
                    ::create
    );

    public static final ResourceKey<Race> TOKYO_SPRINT = simpleRegister("tokyo_sprint",
            new RaceBuilder().setTime(4, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(OHI)
                    .setSurface(Surface.DIRT)
                    .setLength(1200)
                    .addAttr(3, 4)
                    ::create
    );

    public static final ResourceKey<Race> OASIS_STAKES = simpleRegister("oasis_stakes",
            new RaceBuilder().setTime(4, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(TOKYO)
                    .setSurface(Surface.DIRT)
                    .setLength(1600)
                    .addAttr(0, 1)
                    ::create
    );

    public static final ResourceKey<Race> TENNOZAN_STAKES = simpleRegister("tennozan_stakes",
            new RaceBuilder().setTime(4, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(KYOTO)
                    .setSurface(Surface.DIRT)
                    .setLength(1200)
                    ::create
    );

    public static final ResourceKey<Race> HANSHIN_UMAMUSUME_STAKES = simpleRegister("hanshin_umamusume_stakes",
            new RaceBuilder().setTime(4, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(HANSHIN)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> LORD_DERBY_CHALLENGE_TROPHY = simpleRegister("lord_derby_challenge_trophy",
            new RaceBuilder().setTime(4, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> ANTERES_STAKES = simpleRegister("anteres_stakes",
            new RaceBuilder().setTime(4, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(HANSHIN)
                    .setSurface(Surface.DIRT)
                    .setLength(1800)
                    ::create
    );

    public static final ResourceKey<Race> CORAL_STAKES = simpleRegister("coral_stakes",
            new RaceBuilder().setTime(4, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(HANSHIN)
                    .setSurface(Surface.DIRT)
                    .setLength(1400)
                    ::create
    );

    public static final ResourceKey<Race> KEIYO_STAKES = simpleRegister("keiyo_stakes",
            new RaceBuilder().setTime(4, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.DIRT)
                    .setLength(1200)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> SHUNRAI_STAKES = simpleRegister("shunrai_stakes",
            new RaceBuilder().setTime(4, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(NAKAYAMA)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    ::create
    );

    public static final ResourceKey<Race> FUKUSHIMA_MIMPO_HAI = simpleRegister("fukushima_mimpo_hai",
            new RaceBuilder().setTime(4, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(FUKUSHIMA)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .addAttr(1)
                    ::create
    );

    public static final ResourceKey<Race> AZUMAKOFUJI_STAKES = simpleRegister("azumakofuji_stakes",
            new RaceBuilder().setTime(4, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(FUKUSHIMA)
                    .setSurface(Surface.DIRT)
                    .setLength(1700)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> MEGURO_KINEN = simpleRegister("meguro_kinen",
            new RaceBuilder().setTime(5, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(TOKYO)
                    .setSurface(Surface.TURF)
                    .setLength(2500)
                    .addAttr(1)
                    ::create
    );

    public static final ResourceKey<Race> HEIAN_STAKES = simpleRegister("heian_stakes",
            new RaceBuilder().setTime(5, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(KYOTO)
                    .setSurface(Surface.DIRT)
                    .setLength(1900)
                    ::create
    );

    public static final ResourceKey<Race> MAY_STAKES = simpleRegister("may_stakes",
            new RaceBuilder().setTime(5, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(TOKYO)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    .addAttr(0)
                    ::create
    );

    public static final ResourceKey<Race> IDATEN_STAKES = simpleRegister("idaten_stakes",
            new RaceBuilder().setTime(5, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(NIIGATA)
                    .setSurface(Surface.TURF)
                    .setLength(1000)
                    .addAttr(2)
                    ::create
    );

    public static final ResourceKey<Race> KEYAKI_STAKES = simpleRegister("keyaki_stakes",
            new RaceBuilder().setTime(5, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(TOKYO)
                    .setSurface(Surface.DIRT)
                    .setLength(1400)
                    .addAttr(1)
                    ::create
    );

    public static final ResourceKey<Race> AZUCHIJO_STAKES = simpleRegister("azuchijo_stakes",
            new RaceBuilder().setTime(5, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(KYOTO)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    ::create
    );

    public static final ResourceKey<Race> VICTORIA_MILE = simpleRegister("victoria_mile",
            new RaceBuilder().setTime(5, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GI)
                    .setField(TOKYO)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    .addAttr(1, 3)
                    ::create
    );

    public static final ResourceKey<Race> KASHIWA_KINEN = simpleRegister("kashiwa_kinen",
            new RaceBuilder().setTime(5, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GI)
                    .setField(FUNABASHI)
                    .setSurface(Surface.DIRT)
                    .setLength(1600)
                    ::create
    );

    public static final ResourceKey<Race> KEIO_SPRING_CUP = simpleRegister("keio_spring_cup",
            new RaceBuilder().setTime(5, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GII)
                    .setField(TOKYO)
                    .setSurface(Surface.TURF)
                    .setLength(1400)
                    .addAttr(1, 2)
                    ::create
    );

    public static final ResourceKey<Race> NIIGATA_DAISHOTEN = simpleRegister("niigata_daishoten",
            new RaceBuilder().setTime(5, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GIII)
                    .setField(NIIGATA)
                    .setSurface(Surface.TURF)
                    .setLength(2000)
                    .addAttr(1, 2)
                    ::create
    );

    public static final ResourceKey<Race> TANIGAWADAKE_STAKES = simpleRegister("tanigawadake_stakes",
            new RaceBuilder().setTime(5, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(NIIGATA)
                    .setSurface(Surface.TURF)
                    .setLength(1600)
                    ::create
    );

    public static final ResourceKey<Race> METROPOLITAN_STAKES = simpleRegister("metropolitan_stakes",
            new RaceBuilder().setTime(5, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(TOKYO)
                    .setSurface(Surface.TURF)
                    .setLength(2400)
                    ::create
    );

    public static final ResourceKey<Race> KURAMA_STAKES = simpleRegister("kurama_stakes",
            new RaceBuilder().setTime(5, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(KYOTO)
                    .setSurface(Surface.TURF)
                    .setLength(1200)
                    ::create
    );

    public static final ResourceKey<Race> BRILLIANT_STAKES = simpleRegister("brilliant_stakes",
            new RaceBuilder().setTime(5, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(TOKYO)
                    .setSurface(Surface.DIRT)
                    .setLength(2100)
                    ::create
    );

    public static final ResourceKey<Race> MIYAKOOJI_STAKES = simpleRegister("miyakooji_stakes",
            new RaceBuilder().setTime(5, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(KYOTO)
                    .setSurface(Surface.TURF)
                    .setLength(1800)
                    ::create
    );

    public static final ResourceKey<Race> RITTO_STAKES = simpleRegister("ritto_stakes",
            new RaceBuilder().setTime(5, false)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.OP)
                    .setField(KYOTO)
                    .setSurface(Surface.DIRT)
                    .setLength(1400)
                    ::create
    );

    public static final ResourceKey<Race> TEIO_SHO = simpleRegister("teio_sho",
            new RaceBuilder().setTime(6, true)
                    .addYear(Year.SENIOR)
                    .setRanking(RaceRanking.GI)
                    .setField(OHI)
                    .setSurface(Surface.DIRT)
                    .setLength(2000)
                    .addAttr(1)
                    ::create
    );

    // Debut

    public static final ResourceKey<Race> MAKE_DEBUT = simpleRegister("make_debut",
            new RaceBuilder().setRanking(RaceRanking.DEBUT)
                    .addYear(Year.JUNIOR)
                    .setSurface(Surface.ADAPTIVE)
                    .setLength(Distance.CONST_ADAPTIVE)
                    .setField(CONST_ADAPTIVE)
                    ::create
    );

    // Final

    public static final ResourceKey<Race> URA_FINALS = simpleRegister("ura_finals",
            new RaceBuilder().setRanking(RaceRanking.GI)
                    .addYear(Year.SENIOR)
                    .setTime(12, true)
                    .setSurface(Surface.ADAPTIVE)
                    .setLength(Distance.CONST_ADAPTIVE)
                    .setField(CONST_ADAPTIVE)
                    .setExclusive(false)
                    // .onlyIfLaterThen(71)
                    .setReferenceLevel(30)
                    ::create
    );

    // Monthly Race

    public static final ResourceKey<Race> TAURUS_CUP = simpleRegister("taurus_cup",
            new RaceBuilder().setRanking(RaceRanking.GI)
                    .addYear(Year.AFTER_REGULAR)
                    .setTime(5, false)
                    .setSurface(Surface.TURF)
                    .setField(TOKYO)
                    .setLength(2400)
                    .setExclusive(false)
                    .setAllowStatus("retired")
                    .onlyIfLaterThen(URA_FINALS.location())
                    .setReferenceLevel(30)
                    .setTexture(PREDICATE_CHAMPIONS)
                    ::create
    );

    public static final ResourceKey<Race> GEMINI_CUP = simpleRegister("gemini_cup",
            new RaceBuilder().setRanking(RaceRanking.GI)
                    .addYear(Year.AFTER_REGULAR)
                    .setTime(6, false)
                    .setSurface(Surface.TURF)
                    .setField(KYOTO)
                    .setLength(3200)
                    .setExclusive(false)
                    .setAllowStatus("retired")
                    .onlyIfLaterThen(URA_FINALS.location())
                    .setReferenceLevel(30)
                    .setTexture(PREDICATE_CHAMPIONS)
                    ::create
    );

    public static final ResourceKey<Race> CANCER_CUP = simpleRegister("cancer_cup",
            new RaceBuilder().setRanking(RaceRanking.GI)
                    .addYear(Year.AFTER_REGULAR)
                    .setTime(7, false)
                    .setSurface(Surface.TURF)
                    .setField(TOKYO)
                    .setLength(1600)
                    .setExclusive(false)
                    .setAllowStatus("retired")
                    .onlyIfLaterThen(URA_FINALS.location())
                    .setReferenceLevel(30)
                    .setTexture(PREDICATE_CHAMPIONS)
                    ::create
    );

    public static final ResourceKey<Race> LEO_CUP = simpleRegister("leo_cup",
            new RaceBuilder().setRanking(RaceRanking.GI)
                    .addYear(Year.AFTER_REGULAR)
                    .setTime(8, false)
                    .setSurface(Surface.TURF)
                    .setField(HANSHIN)
                    .setLength(2200)
                    .setExclusive(false)
                    .setAllowStatus("retired")
                    .onlyIfLaterThen(URA_FINALS.location())
                    .setReferenceLevel(30)
                    .setTexture(PREDICATE_CHAMPIONS)
                    ::create
    );


    public static final ResourceKey<Race> VIRGO_CUP = simpleRegister("virgo_cup",
            new RaceBuilder().setRanking(RaceRanking.GI)
                    .addYear(Year.AFTER_REGULAR)
                    .setTime(9, false)
                    .setSurface(Surface.TURF)
                    .setField(HANSHIN)
                    .setLength(1600)
                    .setExclusive(false)
                    .setAllowStatus("retired")
                    .onlyIfLaterThen(URA_FINALS.location())
                    .setReferenceLevel(30)
                    .setTexture(PREDICATE_CHAMPIONS)
                    ::create
    );

    public static final ResourceKey<Race> LIBRA_CUP = simpleRegister("libra_cup",
            new RaceBuilder().setRanking(RaceRanking.GI)
                    .addYear(Year.AFTER_REGULAR)
                    .setTime(10, false)
                    .setSurface(Surface.TURF)
                    .setField(KYOTO)
                    .setLength(3000)
                    .setExclusive(false)
                    .setAllowStatus("retired")
                    .onlyIfLaterThen(URA_FINALS.location())
                    .setReferenceLevel(30)
                    .setTexture(PREDICATE_CHAMPIONS)
                    ::create
    );

    public static final ResourceKey<Race> SCORPIO_CUP = simpleRegister("scorpio_cup",
            new RaceBuilder().setRanking(RaceRanking.GI)
                    .addYear(Year.AFTER_REGULAR)
                    .setTime(11, false)
                    .setSurface(Surface.TURF)
                    .setField(TOKYO)
                    .setLength(2000)
                    .setExclusive(false)
                    .setAllowStatus("retired")
                    .onlyIfLaterThen(URA_FINALS.location())
                    .setReferenceLevel(30)
                    .setTexture(PREDICATE_CHAMPIONS)
                    ::create
    );

    public static final ResourceKey<Race> SAGITTARIUS_CUP = simpleRegister("sagittarius_cup",
            new RaceBuilder().setRanking(RaceRanking.GI)
                    .addYear(Year.AFTER_REGULAR)
                    .setTime(12, false)
                    .setSurface(Surface.TURF)
                    .setField(NAKAYAMA)
                    .setLength(2500)
                    .setExclusive(false)
                    .setAllowStatus("retired")
                    .onlyIfLaterThen(URA_FINALS.location())
                    .setReferenceLevel(30)
                    .setTexture(PREDICATE_CHAMPIONS)
                    ::create
    );

    public static final ResourceKey<Race> CAPRICORNUS_CUP = simpleRegister("capricornus_cup",
            new RaceBuilder().setRanking(RaceRanking.GI)
                    .addYear(Year.AFTER_REGULAR)
                    .setTime(1, false)
                    .setSurface(Surface.TURF)
                    .setField(CHUKYO)
                    .setLength(1200)
                    .setExclusive(false)
                    .setAllowStatus("retired")
                    .onlyIfLaterThen(URA_FINALS.location())
                    .setReferenceLevel(30)
                    .setTexture(PREDICATE_CHAMPIONS)
                    ::create
    );

    public static final ResourceKey<Race> AQUARIUS_CUP = simpleRegister("aquarius_cup",
            new RaceBuilder().setRanking(RaceRanking.GI)
                    .addYear(Year.AFTER_REGULAR)
                    .setTime(2, false)
                    .setSurface(Surface.DIRT)
                    .setField(TOKYO)
                    .setLength(1600)
                    .setExclusive(false)
                    .setAllowStatus("retired")
                    .onlyIfLaterThen(URA_FINALS.location())
                    .setReferenceLevel(30)
                    .setTexture(PREDICATE_CHAMPIONS)
                    ::create
    );

    public static final ResourceKey<Race> PISCES_CUP = simpleRegister("pisces_cup",
            new RaceBuilder().setRanking(RaceRanking.GI)
                    .addYear(Year.AFTER_REGULAR)
                    .setTime(3, false)
                    .setSurface(Surface.TURF)
                    .setField(HANSHIN)
                    .setLength(3200)
                    .setExclusive(false)
                    .setAllowStatus("retired")
                    .onlyIfLaterThen(URA_FINALS.location())
                    .setReferenceLevel(30)
                    .setTexture(PREDICATE_CHAMPIONS)
                    ::create
    );

    public static final ResourceKey<Race> ARIES_CUP = simpleRegister("aries_cup",
            new RaceBuilder().setRanking(RaceRanking.GI)
                    .addYear(Year.AFTER_REGULAR)
                    .setTime(4, false)
                    .setSurface(Surface.TURF)
                    .setField(NAKAYAMA)
                    .setLength(2000)
                    .setExclusive(false)
                    .setAllowStatus("retired")
                    .onlyIfLaterThen(URA_FINALS.location())
                    .setReferenceLevel(30)
                    .setTexture(PREDICATE_CHAMPIONS)
                    ::create
    );

    private static HashMap<ResourceKey<Race>, Function<ResourceLocation, Race>> forDataGenMap;

    public static ResourceKey<Race> simpleRegister(String name, Function<ResourceLocation, Race> factory){
        ResourceLocation rLoc = ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, name);
        ResourceKey<Race> rKey = ResourceKey.create(Race.REGISTRY_KEY, rLoc);
        if (forDataGenMap == null) forDataGenMap = new HashMap<>();
        forDataGenMap.put(rKey, factory);
        return rKey;
    }

    public static void registerAll(BootstrapContext<Race> bootstep) {
        forDataGenMap.forEach((rKey, rFun) ->
                bootstep.register(rKey, rFun.apply(rKey.location())));
    }
}
