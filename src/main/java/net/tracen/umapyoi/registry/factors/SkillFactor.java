package net.tracen.umapyoi.registry.factors;

import java.util.Random;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.skills.UmaSkill;
import net.tracen.umapyoi.utils.UmaSkillUtils;

public class SkillFactor extends UmaFactor {

    public SkillFactor() {
        super(FactorType.OTHER);
    }

    @Override
    public void applyFactor(ItemStack soul, UmaFactorStack stack) {
        ResourceLocation skill = ResourceLocation.tryParse(stack.getOrCreateTag().getString("skill"));
        Random rand = new Random();
        if (rand.nextFloat() < (stack.getLevel() * 0.25))
            UmaSkillUtils.learnSkill(soul, skill);
    }

    @Override
    public Component getDescription(UmaFactorStack stack) {
        ResourceLocation skill = ResourceLocation.tryParse(stack.getOrCreateTag().getString("skill"));
        if (skill != null && UmaSkillRegistry.REGISTRY.get().containsKey(skill)) {
            UmaSkill result = UmaSkillRegistry.REGISTRY.get().getValue(skill);
            return result.getDescription().copy().append(" ")
                    .append(Component.translatable("enchantment.level." + stack.getLevel()));
        }
        return super.getDescription(stack);
    }

}
