package net.tracen.umapyoi.utils;

import java.util.List;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.tracen.umapyoi.capability.IUmaCapability;
import net.tracen.umapyoi.events.SkillEvent;
import net.tracen.umapyoi.registry.TrainingSupportRegistry;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.skills.UmaSkill;
import net.tracen.umapyoi.registry.training.SupportStack;
import net.tracen.umapyoi.registry.training.card.SupportEntry;

public class UmaSkillUtils {
    
    public static SupportStack getSkillSupport(UmaSkill skill) {
        if(skill == null) 
            return SupportStack.EMPTY;
        SupportStack result = new SupportStack(TrainingSupportRegistry.SKILL_SUPPORT.get(),1);
        result.getOrCreateTag().putString("skill", skill.getRegistryName().toString());
        return result;
    }
    
    public static SupportEntry getSkillSupportEnrty(ResourceLocation skill) {
        if(skill == null) 
            return null;
        SupportEntry result = new SupportEntry(TrainingSupportRegistry.SKILL_SUPPORT.getId(),1);
        result.getOrCreateTag().putString("skill", skill.toString());
        return result;
    }
    
    public static void syncActionPoint(IUmaCapability cap) {
        int max_ap = cap.getUmaStatus().property()[4] * 200;
        cap.setMaxActionPoint(max_ap);
        cap.setActionPoint(cap.getMaxActionPoint());
    }
    
    public static void learnSkill(IUmaCapability cap, ResourceLocation skill) {
        if(cap.getSkillSlots() <= cap.getSkills().size())
            return ;
        if(skill != null && UmaSkillRegistry.REGISTRY.get().containsKey(skill)) {
            if(!cap.getSkills().contains(skill))
                cap.getSkills().add(skill);
        }
        MinecraftForge.EVENT_BUS.post(new SkillEvent.LearnSkillEvent(skill, cap));
    }
    
    public static ListTag serializeNBT(List<ResourceLocation> skills) {
        ListTag result = new ListTag();
        for (ResourceLocation skill : skills) {
            result.add(StringTag.valueOf(skill.toString()));
        }
        return result;
    }

    public static void deserializeNBT(IUmaCapability cap, CompoundTag compound) {
        cap.getSkills().clear();
        compound.getList("skills", Tag.TAG_STRING).forEach(tag -> {
            cap.getSkills().add(ResourceLocation.tryParse(tag.getAsString()));
        });
    }
}
