package net.tracen.umapyoi.utils;

import java.util.List;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import net.tracen.umapyoi.events.SkillEvent;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.item.data.DataLocation;
import net.tracen.umapyoi.registry.TrainingSupportRegistry;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.skills.UmaSkill;
import net.tracen.umapyoi.registry.training.SupportStack;
import net.tracen.umapyoi.registry.training.card.SupportEntry;

public class UmaSkillUtils {

    public static SupportStack getSkillSupport(UmaSkill skill) {
        if (skill == null)
            return SupportStack.EMPTY;
        SupportStack result = new SupportStack(TrainingSupportRegistry.SKILL_SUPPORT.get(), 1);
        result.getOrCreateTag().putString("skill", UmaSkillRegistry.REGISTRY.getKey(skill).toString());
        return result;
    }

    public static SupportEntry getSkillSupportEnrty(ResourceLocation skill) {
        if (skill == null)
            return null;
        SupportEntry result = new SupportEntry(TrainingSupportRegistry.SKILL_SUPPORT.getId(), 1);
        result.getOrCreateTag().putString("skill", skill.toString());
        return result;
    }

    public static ItemStack getSkillBook(UmaSkill skill) {
        if (skill == null)
            return ItemStack.EMPTY;
        ItemStack result = new ItemStack(ItemRegistry.SKILL_BOOK.get());
        result.update(DataComponentsTypeRegistry.DATA_LOCATION, new DataLocation(UmaSkillRegistry.BASIC_PACE.getId()), 
        		loc -> new DataLocation(UmaSkillRegistry.REGISTRY.getKey(skill)));
        return result;
    }
    
    public static void syncActionPoint(ItemStack stack) {
        UmaSoulUtils.setActionPoint(stack, UmaSoulUtils.getMaxActionPoint(stack));
    }

    public static void learnSkill(ItemStack stack, ResourceLocation skill) {
        if (UmaSoulUtils.hasEmptySkillSlot(stack))
            return;
        if (skill != null && UmaSkillRegistry.REGISTRY.containsKey(skill)) {
        	List<ResourceLocation> skills = UmaSoulUtils.getSkills(stack);
            UmaSkill skillItem = UmaSkillRegistry.REGISTRY.get(skill);
            if(skillItem.getUpperSkill() !=null)
                if (hasLearnedSkill(stack, skillItem.getUpperSkill()))
                    return;
            
            int lowerSkillIndex = getLowerSkillIndex(stack, skill);
            if (lowerSkillIndex != -1)
                skills.set(lowerSkillIndex, skill);
            
            if (!hasLearnedSkill(stack, skill))
                skills.add(skill);
        }
        NeoForge.EVENT_BUS.post(new SkillEvent.LearnSkillEvent(skill, stack));
    }

    public static boolean hasLearnedSkill(ItemStack stack, ResourceLocation skill) {
        List<ResourceLocation> skills = UmaSoulUtils.getSkills(stack);
        return skills.contains(skill);
    }
    
    public static int getLowerSkillIndex(ItemStack stack, ResourceLocation skill) {
    	List<ResourceLocation> skills = UmaSoulUtils.getSkills(stack);
        UmaSkill target = null;
        for(int i = 0;i<skills.size();i++) {
            target = UmaSkillRegistry.REGISTRY.get(skills.get(i));
            if(target.getUpperSkill() == null)
                continue;
            if(target !=null && target.getUpperSkill().equals(skill))
                return i;
        }
        // if doesn't have lower skill, return -1 for mark.
        return -1;
    }

}
