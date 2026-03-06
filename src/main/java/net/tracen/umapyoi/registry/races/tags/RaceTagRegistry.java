package net.tracen.umapyoi.registry.races.tags;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.tracen.umapyoi.Umapyoi;

import java.util.HashMap;
import java.util.function.Function;

public class RaceTagRegistry {
    public static ResourceKey<RaceTag> TRIPLE_TIARA = simpleRegister("triple_tiara", 3, true,
            new int[]{1, 1, 1, 1, 1}); //note: 三后冠
    public static ResourceKey<RaceTag> TRIPLE_CROWN = simpleRegister("triple_crown", 3, true,
            new int[]{1, 1, 1, 1, 1}); //note: 三冠
    public static ResourceKey<RaceTag> EIGHT_GREAT_RACES = simpleRegister("eight_great_races", 8, true,
            new int[]{1, 1, 1, 1, 1}); //note: 八大赛事
    public static ResourceKey<RaceTag> SENIOR_SPRING_TRIPLE_CROWN = simpleRegister("senior_spring_triple_crown", 3, true,
            new int[]{1, 1, 1, 1, 1}); //note: 春季资深三冠
    public static ResourceKey<RaceTag> SENIOR_AUTUMN_TRIPLE_CROWN = simpleRegister("senior_autumn_triple_crown", 3, true,
            new int[]{1, 1, 1, 1, 1}); //note: 秋季资深三冠
    public static ResourceKey<RaceTag> GRAND_PRIX = simpleRegister("grand_prix", 2, true,
            new int[]{1, 1, 1, 1, 1}); //note: 春秋大奖赛（宝冢+有马）
    public static ResourceKey<RaceTag> MILE = simpleRegister("mile", 2, true,
            new int[]{1, 1, 1, 1, 1}); //note: 春秋一里赛（安田+一里）
    public static ResourceKey<RaceTag> SPRINT = simpleRegister("sprint", 2, true,
            new int[]{1, 1, 1, 1, 1}); //note: 春秋短途赛（高松宫纪念+短途马锦标） 编者注: 不是高松灯是高松宫
    public static ResourceKey<RaceTag> DIRT = simpleRegister("dirt", 2, true,
            new int[]{1, 1, 1, 1, 1}); //note: 春秋沙地赛（二月锦标+日本冠军）

    private static HashMap<ResourceKey<RaceTag>, Function<ResourceLocation, RaceTag>> forDataGenMap;

    public static ResourceKey<RaceTag> simpleRegister(String name, int max, boolean isUnique, int[] propertyReward) {
        ResourceLocation rLoc = ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, name);
        ResourceKey<RaceTag> rKey = ResourceKey.create(RaceTag.REGISTRY_KEY, rLoc);
        if (forDataGenMap == null) forDataGenMap = new HashMap<>();
        forDataGenMap.put(rKey, (rLocs) -> new RaceTag(max, rLocs, isUnique, propertyReward));
        return rKey;
    }

    public static ResourceKey<RaceTag> simpleRegister(String name, int max, boolean isUnique) {
        ResourceLocation rLoc = ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, name);
        ResourceKey<RaceTag> rKey = ResourceKey.create(RaceTag.REGISTRY_KEY, rLoc);
        if (forDataGenMap == null) forDataGenMap = new HashMap<>();
        forDataGenMap.put(rKey, (rLocs) -> new RaceTag(max, rLocs, isUnique, new int[5]));
        return rKey;
    }

    public static ResourceKey<RaceTag> simpleRegister(String name, int max) {
        return simpleRegister(name, max, max != 1);
    }

    public static void registerAll(BootstrapContext<RaceTag> bootstep) {
        forDataGenMap.forEach((rKey, rFun) ->
                bootstep.register(rKey, rFun.apply(rKey.location())));
    }
}
