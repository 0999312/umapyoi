package net.tracen.umapyoi.data.loot;

import com.google.gson.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.registry.races.Race;
import net.tracen.umapyoi.utils.RaceRanking;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RaceTicketRandomLootFunction implements LootItemFunction {
    public final RaceRanking least;
    public final RaceRanking most;
    public final boolean mode;
    public final Set<String> predicate;
    public RaceTicketRandomLootFunction(RaceRanking least, RaceRanking most, boolean mode, Set<String> predicate) {
        this.least = least;
        this.most = most;
        this.mode = mode;
        this.predicate = predicate;
    }

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
        stack.getOrCreateTag().putString("race", id.toString());
        return stack;
    }

    public static class RaceTicketRandomLootSerializer implements Serializer<RaceTicketRandomLootFunction> {
        @Nonnull
        @Override
        public RaceTicketRandomLootFunction deserialize(
                @Nonnull JsonObject jsonObject, @Nonnull JsonDeserializationContext jsonDeserializationContext) {
            RaceRanking least = jsonObject.has("least") ? RaceRanking.valueOf(jsonObject.get("least").getAsString().toUpperCase()) : RaceRanking.DEBUT;
            RaceRanking most = jsonObject.has("most") ? RaceRanking.valueOf(jsonObject.get("most").getAsString().toUpperCase()) : RaceRanking.GI;
            boolean mode = !jsonObject.has("mode") || jsonObject.get("mode").getAsBoolean();
            Set<String> predicate = jsonObject.has("predicate") ?
                    jsonObject.getAsJsonArray("predicate")
                            .asList()
                            .stream()
                            .map(JsonElement::getAsString)
                            .collect(Collectors.toSet())
                    : Set.of();
            return new RaceTicketRandomLootFunction(least, most, mode, predicate);
        }

        @Override
        public void serialize(JsonObject jsonObject,
                              @Nonnull RaceTicketRandomLootFunction raceTicketRandomLootFunction,
                              @Nonnull JsonSerializationContext jsonSerializationContext) {
            jsonObject.addProperty("least", raceTicketRandomLootFunction.least.name().toLowerCase());
            jsonObject.addProperty("most", raceTicketRandomLootFunction.most.name().toLowerCase());
            jsonObject.addProperty("mode", raceTicketRandomLootFunction.mode);
            JsonArray arr = new JsonArray();
            raceTicketRandomLootFunction.predicate.forEach(arr::add);
            jsonObject.add("predicate", arr);
        }
    }
}
