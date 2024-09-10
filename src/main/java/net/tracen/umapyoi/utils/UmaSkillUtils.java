package net.tracen.umapyoi.utils;

import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.events.SkillEvent;
import net.tracen.umapyoi.item.ItemRegistry;
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
        result.getOrCreateTag().putString("skill", UmaSkillRegistry.REGISTRY.get().getKey(skill).toString());
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
        result.getOrCreateTag().putString("skill", UmaSkillRegistry.REGISTRY.get().getKey(skill).toString());
        return result;
    }
    
    public static void syncActionPoint(ItemStack stack) {
        int max_ap = UmaSoulUtils.getProperty(stack)[4] * 200;
        UmaSoulUtils.setMaxActionPoint(stack, max_ap);
        UmaSoulUtils.setActionPoint(stack, max_ap);
    }

    public static void learnSkill(ItemStack stack, ResourceLocation skill) {
        if (!UmaSoulUtils.hasEmptySkillSlot(stack))
            return;
        if (skill != null && UmaSkillRegistry.REGISTRY.get().containsKey(skill)) {
            ListTag skills = UmaSoulUtils.getSkills(stack);
            UmaSkill skillItem = UmaSkillRegistry.REGISTRY.get().getValue(skill);
            if(skillItem.getUpperSkill() !=null)
                if (hasLearnedSkill(stack, skillItem.getUpperSkill()))
                    return;
            
            StringTag tag = StringTag.valueOf(skill.toString());
            int lowerSkillIndex = getLowerSkillIndex(stack, skill);
            if (lowerSkillIndex != -1)
                skills.setTag(lowerSkillIndex, tag);
            
            if (!hasLearnedSkill(stack, skill))
                skills.add(tag);
        }
        MinecraftForge.EVENT_BUS.post(new SkillEvent.LearnSkillEvent(skill));
    }

    public static boolean hasLearnedSkill(ItemStack stack, ResourceLocation skill) {
        ListTag skills = UmaSoulUtils.getSkills(stack);
        StringTag tag = StringTag.valueOf(skill.toString());
        return skills.contains(tag);
    }
    
    public static int getLowerSkillIndex(ItemStack stack, ResourceLocation skill) {
        ListTag skills = UmaSoulUtils.getSkills(stack);
        UmaSkill target = null;
        for(int i = 0;i<skills.size();i++) {
            target = UmaSkillRegistry.REGISTRY.get().getValue(ResourceLocation.tryParse(skills.get(i).getAsString()));
            if(target.getUpperSkill() == null)
                continue;
            if(target !=null && target.getUpperSkill().equals(skill))
                return i;
        }
        // if doesn't have lower skill, return -1 for mark.
        return -1;
    }

}
