package net.tracen.umapyoi.data.loot;

import java.util.function.BiConsumer;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContextUser;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.loot.UmaSkillLootFunction;

public record UmaSkillLootTable(HolderLookup.Provider registries) implements LootTableSubProvider, LootContextUser {
	public static final ResourceKey<LootTable> COMPLEX_SKILLS = register("complex_skills");
	public static final ResourceKey<LootTable> SIMPLE_SKILLS = register("simple_skills");

	@Override
	public void generate(BiConsumer<ResourceKey<LootTable>, Builder> consumer) {
		consumer.accept(SIMPLE_SKILLS, LootTable.lootTable().withPool(LootPool.lootPool()
				.setRolls(UniformGenerator.between(0.0F, 4.0F))
				.add(LootItem.lootTableItem(ItemRegistry.SKILL_BOOK.get()).setWeight(10).apply(UmaSkillLootFunction.setSkillLevel(1)))
				.add(LootItem.lootTableItem(ItemRegistry.SKILL_BOOK.get()).setWeight(10).apply(UmaSkillLootFunction.setSkillLevel(1)))
				)
				);
		
		consumer.accept(COMPLEX_SKILLS, LootTable.lootTable().withPool(LootPool.lootPool()
				.setRolls(UniformGenerator.between(0.0F, 2.0F))
				.add(LootItem.lootTableItem(ItemRegistry.SKILL_BOOK.get()).setWeight(10).apply(UmaSkillLootFunction.setSkillLevel(2)))
				.add(LootItem.lootTableItem(ItemRegistry.SKILL_BOOK.get()).setWeight(10).apply(UmaSkillLootFunction.setSkillLevel(2)))
				)
				);
	}

	private static ResourceKey<LootTable> register(String name) {
		return ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, name));
	}
}
