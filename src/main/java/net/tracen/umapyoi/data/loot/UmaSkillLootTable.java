package net.tracen.umapyoi.data.loot;

import net.minecraft.data.loot.LootTableSubProvider;
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

import java.util.function.BiConsumer;

public record UmaSkillLootTable() implements LootTableSubProvider, LootContextUser {
	public static final ResourceLocation COMPLEX_SKILLS = register("complex_skills");
	public static final ResourceLocation SIMPLE_SKILLS = register("simple_skills");

	@Override
	public void generate(BiConsumer<ResourceLocation, Builder> consumer) {
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

	private static ResourceLocation register(String name) {
		return new ResourceLocation(Umapyoi.MODID, name);
	}
}
