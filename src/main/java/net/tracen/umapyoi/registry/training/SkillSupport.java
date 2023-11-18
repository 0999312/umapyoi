package net.tracen.umapyoi.registry.training;

import java.util.Random;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.skills.UmaSkill;
import net.tracen.umapyoi.utils.UmaSkillUtils;

public class SkillSupport extends TrainingSupport {
    public SkillSupport() {
        super();
    }

    @Override
    public boolean applySupport(ItemStack soul, SupportStack stack) {
        ResourceLocation skill = ResourceLocation.tryParse(stack.getOrCreateTag().getString("skill"));
        Random rand = new Random();
        if (rand.nextFloat() < (stack.getLevel() * 0.33)) {
                UmaSkillUtils.learnSkill(soul, skill);
                return true;
        }
        return false;
    }

    @Override
    public Component getDescription(SupportStack stack) {
        ResourceLocation skill = ResourceLocation.tryParse(stack.getOrCreateTag().getString("skill"));
        if (skill != null && UmaSkillRegistry.REGISTRY.get().containsKey(skill)) {
            UmaSkill result = UmaSkillRegistry.REGISTRY.get().getValue(skill);
            return result.getDescription();
        }
        return super.getDescription(stack);
    }
}
