package net.tracen.umapyoi.registry.factors;

import java.util.Objects;
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
        if (skill != null && UmaSkillRegistry.REGISTRY.get().containsKey(skill)) {
            UmaSkill result = UmaSkillRegistry.REGISTRY.get().getValue(skill);
            if(!result.isInheritable())
            	return;
	        Random rand = new Random();
	        if (rand.nextFloat() < (stack.getLevel() * 0.25))
	            UmaSkillUtils.learnSkill(soul, skill);
        }
    }
    
    @Override
    public Component getDescriptionDetail(UmaFactorStack stack) {
        ResourceLocation skill = ResourceLocation.tryParse(stack.getOrCreateTag().getString("skill"));
        if (skill != null && UmaSkillRegistry.REGISTRY.get().containsKey(skill)) {
            UmaSkill result = UmaSkillRegistry.REGISTRY.get().getValue(skill);
            return result.getDescriptionDetail();
        }
        return Component.empty();
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

    @Override
    public boolean withStackEquals(UmaFactorStack left, UmaFactorStack right) {
        return super.withStackEquals(left, right) &&
                Objects.equals(ResourceLocation.tryParse(left.getOrCreateTag().getString("skill")), ResourceLocation.tryParse(right.getOrCreateTag().getString("skill")));
    }

    @Override
    public int hashCode(UmaFactorStack stack) {
        return Objects.hash(
                super.hashCode(stack),
                ResourceLocation.tryParse(stack.getOrCreateTag().getString("skill"))
        );
    }
}
