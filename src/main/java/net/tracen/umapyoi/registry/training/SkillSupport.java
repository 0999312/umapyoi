package net.tracen.umapyoi.registry.training;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.skills.UmaSkill;
import net.tracen.umapyoi.utils.UmaSkillUtils;

public class SkillSupport extends TrainingSupport {
    public SkillSupport() {
        super();
    }

    @Override
    public boolean applySupport(ItemStack soul, RandomSource rand, SupportStack stack) {
        ResourceLocation skill = ResourceLocation.tryParse(stack.getOrCreateTag().getString("skill"));
        if (rand.nextFloat() < (stack.getLevel() * 0.33)) {
                UmaSkillUtils.learnSkill(soul, skill);
                return true;
        }
        return false;
    }

    @Override
    public Component getDescription(SupportStack stack) {
        ResourceLocation skill = ResourceLocation.tryParse(stack.getOrCreateTag().getString("skill"));
        if (skill != null && UmaSkillRegistry.REGISTRY.containsKey(skill)) {
            UmaSkill result = UmaSkillRegistry.REGISTRY.get(skill);
            return result.getDescription();
        }
        return super.getDescription(stack);
    }
}
