package net.tracen.umapyoi.registry.factors;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.skills.UmaSkill;
import net.tracen.umapyoi.utils.UmaSkillUtils;

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
        if (skill != null && UmaSkillRegistry.REGISTRY.containsKey(skill)) {
            UmaSkill result = UmaSkillRegistry.REGISTRY.get(skill);
            return result.getDescription();
        }
        return super.getDescription(stack);
    }

}
