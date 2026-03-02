package net.tracen.umapyoi.loot;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tracen.umapyoi.Umapyoi;

public class LootFunctionRegistry {
	public static final DeferredRegister<LootItemFunctionType<?>> LOOT_FUNCTION_TYPES = DeferredRegister
			.create(Registries.LOOT_FUNCTION_TYPE, Umapyoi.MODID);
	public static final DeferredHolder<LootItemFunctionType<?>, LootItemFunctionType<UmaSkillLootFunction>> 
			UMASKILL_WITH_LEVEL = LOOT_FUNCTION_TYPES
			.register("umaskill_with_level", () -> new LootItemFunctionType<>(UmaSkillLootFunction.CODEC));
}
