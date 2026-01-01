package net.tracen.umapyoi.registry.races;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.utils.RaceRanking;
import net.tracen.umapyoi.utils.UmaSoulUtils;

import static net.tracen.umapyoi.registry.races.RaceWithPredicate.RaceWithPredicateBuilder;

import java.util.function.Supplier;

public class RaceRegistry {
    public static final DeferredRegister<Race> RACES = DeferredRegister.create(Race.REGISTRY_KEY, Umapyoi.MODID);

    public static final Supplier<IForgeRegistry<Race>> REGISTRY = RACES.makeRegistry(RegistryBuilder::new);

    public static final RegistryObject<Race> DEFAULT = RACES.register("undetermined_race",
            new RaceWithPredicateBuilder().setPredicate((s, r) -> false)::create
    );

    public static final RegistryObject<Race> DEBUT_MILES = RACES.register("debut_miles",
            new RaceWithPredicateBuilder().setRanking(RaceRanking.DEBUT).setLength(1800)
                    .setPredicate((s, r) -> !UmaSoulUtils.hasUmaSoulDebut(s))
                    .setFollowup((s) -> s.getOrCreateTag().putBoolean("has_debut", true))::create
    );

    public static final RegistryObject<Race> TAKARAZUKA_KINEN = RACES.register("takarazuka_kinen",
            new RaceWithPredicateBuilder().setRanking(RaceRanking.GI).setLength(2200)
                    .setPredicate((s, r) -> UmaSoulUtils.hasUmaSoulDebut(s) && UmaSoulUtils.getProperty(s)[1] >= 5)
                    .setFollowup((s) -> {})::create
    );
}
