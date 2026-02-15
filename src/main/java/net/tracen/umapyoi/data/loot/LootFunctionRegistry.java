package net.tracen.umapyoi.data.loot;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.tracen.umapyoi.Umapyoi;

public class LootFunctionRegistry {
    public static final DeferredRegister<LootItemFunctionType> LOOT_FUNCTIONS =
            DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, Umapyoi.MODID);

    public static final RegistryObject<LootItemFunctionType> RACE_TICKET_RANDOM =
            LOOT_FUNCTIONS.register("race_ticket_random",
                    () -> new LootItemFunctionType(new RaceTicketRandomLootFunction.RaceTicketRandomLootSerializer()));
}
