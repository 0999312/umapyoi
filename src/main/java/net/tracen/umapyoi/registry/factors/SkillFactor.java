package net.tracen.umapyoi.registry.factors;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.tracen.umapyoi.capability.IUmaCapability;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.skills.UmaSkill;
import net.tracen.umapyoi.utils.UmaSkillUtils;

public class SkillFactor extends UmaFactor {
    
    public SkillFactor() {
        super(FactorType.OTHER);
    }
    
    @Override
    public void applyFactor(IUmaCapability cap, UmaFactorStack stack) {
        ResourceLocation skill = ResourceLocation.tryParse(stack.getOrCreateTag().getString("skill"));
        UmaSkillUtils.learnSkill(cap, skill);
    }
    
    @Override
    public Component getDescription(UmaFactorStack stack) {
        ResourceLocation skill = ResourceLocation.tryParse(stack.getOrCreateTag().getString("skill"));
        if(skill != null && UmaSkillRegistry.REGISTRY.get().containsKey(skill)) {
            UmaSkill result = UmaSkillRegistry.REGISTRY.get().getValue(skill);
            return result.getDescription();
        }
        return super.getDescription(stack);
    }

}
