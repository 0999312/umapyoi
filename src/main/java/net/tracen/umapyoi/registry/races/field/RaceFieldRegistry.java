package net.tracen.umapyoi.registry.races.field;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.utils.Distance;

import java.util.HashMap;
import java.util.Set;
import java.util.function.Function;

public class RaceFieldRegistry {
    public static final ResourceLocation CONST_ADAPTIVE = new ResourceLocation(Umapyoi.MODID, "adaptive");

    public static final ResourceKey<RaceField> HAKODATE = simpleRegister("hakodate", Set.of(Distance.SPRINT), Set.of(), Set.of()); //函馆
    public static final ResourceKey<RaceField> CHUKYO = simpleRegister("chukyo"); //中京
    public static final ResourceKey<RaceField> NIIGATA = simpleRegister("niigata"); //新潟
    public static final ResourceKey<RaceField> SAPPORO = simpleRegister("sapporo"); //札幌
    public static final ResourceKey<RaceField> KOKURA = simpleRegister("kokura"); //小倉
    public static final ResourceKey<RaceField> HANSHIN = simpleRegister("hanshin", Set.of(Distance.SPRINT, Distance.MILE), Set.of(), Set.of()); //阪神
    public static final ResourceKey<RaceField> NAKAYAMA = simpleRegister("nakayama", Set.of(Distance.MEDIUM), Set.of(Distance.SPRINT, Distance.MILE), Set.of()); //中山
    public static final ResourceKey<RaceField> TOKYO = simpleRegister("tokyo", Set.of(Distance.SPRINT, Distance.MILE, Distance.MEDIUM), Set.of(Distance.SPRINT, Distance.MILE), Set.of()); //东京
    public static final ResourceKey<RaceField> KYOTO = simpleRegister("kyoto", Set.of(Distance.MILE, Distance.MEDIUM), Set.of(Distance.SPRINT, Distance.MILE), Set.of()); //京都
    public static final ResourceKey<RaceField> FUKUSHIMA = simpleRegister("fukushima"); //福岛
    public static final ResourceKey<RaceField> KAWASAKI = simpleRegister("kawasaki"); //川崎
    public static final ResourceKey<RaceField> FUNABASHI = simpleRegister("funabashi"); //船桥
    public static final ResourceKey<RaceField> MORIOKA = simpleRegister("morioka"); //盛岡
    public static final ResourceKey<RaceField> OHI = simpleRegister("ohi"); //大井

    private static HashMap<ResourceKey<RaceField>, Function<ResourceLocation, RaceField>> forDataGenMap;

    public static ResourceKey<RaceField> simpleRegister(String name) {
        return simpleRegister(name, Set.of(), Set.of(), Set.of());
    }

    public static ResourceKey<RaceField> simpleRegister(String name, Set<Distance> turf, Set<Distance> dirt, Set<Distance> synthetic){
        ResourceLocation rLoc = new ResourceLocation(Umapyoi.MODID, name);
        ResourceKey<RaceField> rKey = ResourceKey.create(RaceField.REGISTRY_KEY, rLoc);
        if (forDataGenMap == null) forDataGenMap = new HashMap<>();
        forDataGenMap.put(rKey, (id) -> new RaceField(id, turf, dirt, synthetic));
        return rKey;
    }

    public static void registerAll(BootstapContext<RaceField> bootstep) {
        forDataGenMap.forEach((rKey, rFun) ->
                bootstep.register(rKey, rFun.apply(rKey.location())));
    }
}
