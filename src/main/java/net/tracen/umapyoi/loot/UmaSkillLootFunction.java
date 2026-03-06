package net.tracen.umapyoi.loot;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.registry.UmaSkillRegistry;

import java.util.*;
import java.util.stream.Collectors;

public class UmaSkillLootFunction extends LootItemConditionalFunction {
	private final Optional<Set<ResourceLocation>> skills;
	private int level;
	
	public UmaSkillLootFunction(List<LootItemCondition> predicates, Optional<Set<ResourceLocation>> skills, int level) {
		super(predicates.toArray(new LootItemCondition[0]));
		this.skills = skills;
		this.level = level;
	}

	@Override
	public LootItemFunctionType getType() {
		return LootFunctionRegistry.UMASKILL_WITH_LEVEL.get();
	}
	
    public static <T> Builder<?> setSkillLevel(int level) {
        return simpleBuilder(p_331753_ -> new UmaSkillLootFunction(Arrays.stream(p_331753_).collect(Collectors.toCollection(ArrayList::new)), Optional.empty(), level));
    }

	@Override
	protected ItemStack run(ItemStack stack, LootContext context) {
		try {
        RandomSource random = context.getRandom();
        List<ResourceLocation> list = this.skills
                .orElseGet(() -> UmaSkillRegistry.REGISTRY.get().getKeys())
				.stream()
                .filter(e -> UmaSkillRegistry.REGISTRY.get().getValue(e).getSkillLevel() == level)
                .toList();
        Optional<ResourceLocation> optional = Util.getRandomSafe(list, random);
        if (optional.isEmpty()) {
            Umapyoi.getLogger().warn("Couldn't find a compatible skill for {}", stack);
        } else {
            ResourceLocation skill = optional.get();
            if(stack.is(ItemRegistry.SKILL_BOOK.get())) {
				stack.getOrCreateTag().putString("skill", skill.toString());
            }
        }
        return stack; } catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static class Serializer extends LootItemConditionalFunction.Serializer<UmaSkillLootFunction> {
		@Override
		public UmaSkillLootFunction deserialize(JsonObject pObject, JsonDeserializationContext pDeserializationContext, LootItemCondition[] pConditions) {
			Optional<Set<ResourceLocation>> skillSet = Optional.empty();
			if (pObject.has("skills")) {
				JsonArray array = pObject.getAsJsonArray("skills");
				ArrayList<String> stringOfArray = new ArrayList<>();
				array.forEach(e -> stringOfArray.add(e.getAsString()));
				skillSet = Optional.of(stringOfArray.stream().map(ResourceLocation::tryParse)
						.filter(UmaSkillRegistry.REGISTRY.get()::containsKey).collect(Collectors.toSet()));
			}
			int level = pObject.get("level").getAsInt();
			return new UmaSkillLootFunction(Arrays.asList(pConditions), skillSet, level);
		}

		@Override
		public void serialize(JsonObject pJson, UmaSkillLootFunction pLootItemConditionalFunction, JsonSerializationContext pSerializationContext) {
			super.serialize(pJson, pLootItemConditionalFunction, pSerializationContext);

			pLootItemConditionalFunction.skills.ifPresent(s -> {
				JsonArray array = new JsonArray();
				s.forEach(i -> array.add(i.toString()));
				pJson.add("skills", array);
			});
			pJson.addProperty("level", pLootItemConditionalFunction.level);
		}
	}

}
