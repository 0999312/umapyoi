package net.tracen.umapyoi.registry.factors;

import net.tracen.umapyoi.capability.IUmaCapability;
import net.tracen.umapyoi.registry.UmaSkillRegistry;

public class SkillTestFactor extends UmaFactor {
    
    public SkillTestFactor() {
        super(FactorType.OTHER);
    }

    @Override
    public void applyFactor(IUmaCapability cap, UmaFactorStack stack) {
        if(!cap.getSkills().contains(UmaSkillRegistry.LAST_LEG.get()))
            cap.getSkills().add(UmaSkillRegistry.LAST_LEG.get());
    }

}
