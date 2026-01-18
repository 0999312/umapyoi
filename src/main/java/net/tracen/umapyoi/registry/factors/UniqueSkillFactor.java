package net.tracen.umapyoi.registry.factors;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.skills.UmaSkill;
import net.tracen.umapyoi.utils.UmaSkillUtils;

import java.util.Objects;

public class UniqueSkillFactor extends UmaFactor {

    public UniqueSkillFactor() {
        super(FactorType.UNIQUE);
    }

    @Override
    public void applyFactor(ItemStack soul, UmaFactorStack stack) {
        ResourceLocation skill = ResourceLocation.tryParse(stack.getOrCreateTag().getString("skill"));
        UmaSkillUtils.learnSkill(soul, skill);
    }

    @Override
    public Component getDescription(UmaFactorStack stack) {
        ResourceLocation skill = ResourceLocation.tryParse(stack.getOrCreateTag().getString("skill"));
        if (skill != null && UmaSkillRegistry.REGISTRY.get().containsKey(skill)) {
            UmaSkill result = UmaSkillRegistry.REGISTRY.get().getValue(skill);
            return result.getDescription();
        }
        return super.getDescription(stack);
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
