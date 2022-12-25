package net.tracen.umapyoi.registry.training;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.tracen.umapyoi.capability.IUmaCapability;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.skills.UmaSkill;

public class SkillSupport extends TrainingSupport {
    public SkillSupport(){
        super();
    }
    
    @Override
    public void applySupport(IUmaCapability cap, SupportStack stack) {
        ResourceLocation skill = ResourceLocation.tryParse(stack.getOrCreateTag().getString("skill"));
        if(skill!=null && UmaSkillRegistry.REGISTRY.get().containsKey(skill)) {
            UmaSkill result = UmaSkillRegistry.REGISTRY.get().getValue(skill);
            if(!cap.getSkills().contains(result))
                cap.getSkills().add(result);
        }
    }
    
    @Override
    public Component getDescription(SupportStack stack) {
        ResourceLocation skill = ResourceLocation.tryParse(stack.getOrCreateTag().getString("skill"));
        if(skill != null && UmaSkillRegistry.REGISTRY.get().containsKey(skill)) {
            UmaSkill result = UmaSkillRegistry.REGISTRY.get().getValue(skill);
            return result.getDescription();
        }
        return super.getDescription(stack);
    }
}
