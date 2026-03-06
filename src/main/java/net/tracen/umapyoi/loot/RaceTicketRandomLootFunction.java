package net.tracen.umapyoi.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.registry.races.Race;
import net.tracen.umapyoi.utils.RaceRanking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record RaceTicketRandomLootFunction(RaceRanking least, RaceRanking most, boolean mode,
                                           Set<String> predicate) implements LootItemFunction {

    @Override
    public LootItemFunctionType getType() {
        return LootFunctionRegistry.RACE_TICKET_RANDOM.get();
    }

    @Override
    public ItemStack apply(ItemStack stack, LootContext lootContext) {
        RandomSource rand = lootContext.getRandom();
        ServerLevel level = lootContext.getLevel();
        List<Race> raceListOfPredicate = UmapyoiAPI.getRaceRegistry(level).stream()
                .filter(i -> least.compareTo(i.ranking()) <= 0 && i.ranking().compareTo(most) <= 0)
                .filter(i -> predicate.isEmpty() || (mode == predicate.contains(i.texturePredicateOverride)))
                .toList();
        if (raceListOfPredicate.isEmpty()) return ItemStack.EMPTY;
        Race raceDeterm = raceListOfPredicate.get(rand.nextInt(raceListOfPredicate.size()));
        ResourceLocation id = raceDeterm.id;
        stack.set(DataComponentsTypeRegistry.RACE_DATA, id);
        return stack;
    }

    public static MapCodec<RaceTicketRandomLootFunction> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            RaceRanking.CODEC.optionalFieldOf("least", RaceRanking.DEBUT).forGetter(r -> r.least),
            RaceRanking.CODEC.optionalFieldOf("most", RaceRanking.DEBUT).forGetter(r -> r.most),
            Codec.BOOL.optionalFieldOf("mode", true).forGetter(r -> r.mode),
            Codec.STRING.listOf().<Set<String>>xmap(HashSet::new, ArrayList::new).optionalFieldOf("predicate", Set.of()).forGetter(r -> r.predicate)
    ).apply(instance, RaceTicketRandomLootFunction::new));
}
