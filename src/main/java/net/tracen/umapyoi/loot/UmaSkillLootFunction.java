package net.tracen.umapyoi.loot;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.item.data.DataLocation;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.skills.UmaSkill;

public class UmaSkillLootFunction extends LootItemConditionalFunction {
	private final Optional<HolderSet<UmaSkill>> skills;
	private int level;
	
    public static final MapCodec<UmaSkillLootFunction> CODEC =
            // #commonFields adds the conditions field.
            RecordCodecBuilder.mapCodec(inst -> commonFields(inst).and(inst.group(
                    RegistryCodecs.homogeneousList(UmaSkill.REGISTRY_KEY)
                    .optionalFieldOf("skills")
                    .forGetter(e -> e.skills),
                    Codec.INT.fieldOf("level").forGetter(e -> e.level))
            ).apply(inst, UmaSkillLootFunction::new));
	
	public UmaSkillLootFunction(List<LootItemCondition> predicates, Optional<HolderSet<UmaSkill>> skills, int level) {
		super(predicates);
		this.skills = skills;
		this.level = level;
	}

	@Override
	public LootItemFunctionType<? extends LootItemConditionalFunction> getType() {
		return LootFunctionRegistry.UMASKILL_WITH_LEVEL.get();
	}
	
    public static <T> LootItemConditionalFunction.Builder<?> setSkillLevel(int level) {
        return simpleBuilder(p_331753_ -> new UmaSkillLootFunction(p_331753_, Optional.empty(), level));
    }

	@Override
	protected ItemStack run(ItemStack stack, LootContext context) {
        RandomSource random = context.getRandom();
        List<Holder<UmaSkill>> list = this.skills
                .map(HolderSet::stream)
                .orElseGet(() -> context.getLevel().registryAccess().lookupOrThrow(UmaSkill.REGISTRY_KEY).listElements().map(Function.identity()))
                .filter(e -> e.value().getSkillLevel() == level)
                .toList();
        Optional<Holder<UmaSkill>> optional = Util.getRandomSafe(list, random);
        if (optional.isEmpty()) {
            Umapyoi.getLogger().warn("Couldn't find a compatible skill for {}", stack);
        } else {
            Holder<UmaSkill> skill = optional.get();
            if(stack.is(ItemRegistry.SKILL_BOOK.get())) {
	            stack.update(DataComponentsTypeRegistry.DATA_LOCATION, 
	            		new DataLocation(UmaSkillRegistry.BASIC_PACE.getId()), 
	            		loc -> new DataLocation(skill.getKey().location())
	            		);
            }
        }
        return stack;
	}

}
