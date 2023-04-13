package net.tracen.umapyoi.utils;

import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.tracen.umapyoi.events.SkillEvent;
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
        result.getOrCreateTag().putString("skill", skill.getRegistryName().toString());
        return result;
    }

    public static SupportEntry getSkillSupportEnrty(ResourceLocation skill) {
        if (skill == null)
            return null;
        SupportEntry result = new SupportEntry(TrainingSupportRegistry.SKILL_SUPPORT.getId(), 1);
        result.getOrCreateTag().putString("skill", skill.toString());
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
            StringTag tag = StringTag.valueOf(skill.toString());
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

}
