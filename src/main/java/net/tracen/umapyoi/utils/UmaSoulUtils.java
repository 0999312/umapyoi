package net.tracen.umapyoi.utils;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.data.builtin.UmaDataRegistry;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.umadata.Growth;
import net.tracen.umapyoi.registry.umadata.Motivations;
import net.tracen.umapyoi.registry.umadata.UmaData;

public class UmaSoulUtils {
    
    public static Component getTranslatedUmaName(ItemStack stack) {
        return UmaSoulUtils.getTranslatedUmaName(UmaSoulUtils.getName(stack));
    }

    public static Component getTranslatedUmaName(ResourceLocation name) {
        return Component.translatable(Util.makeDescriptionId("umadata", name));
    }
    


    public static ItemStack initUmaSoul(ItemStack stack, ResourceLocation name, UmaData data) {
        ItemStack result = stack.copy();
        CompoundTag tag = result.getOrCreateTag();
        tag.putString("name", name.toString());
        tag.putString("ranking", data.getGachaRanking().name().toLowerCase());
        tag.putIntArray("property", data.property());
        tag.putIntArray("maxProperty", data.maxProperty());
        tag.putIntArray("propertyRate", data.propertyRate());
        UmaSoulUtils.addSkill(result, data.uniqueSkill());
        tag.putInt("actionPoint", data.property()[4] * 200);
        tag.putIntArray("extraProperty", new int[] { 1, 6, 5, 0 });
        tag.putInt("resultRanking", ResultRankingUtils.generateRanking(result));
        return result;
    }

    public static ResourceLocation getName(ItemStack stack) {
        return stack.getOrCreateTag().getString("name").isBlank() ? UmaDataRegistry.COMMON_UMA.location()
                : ResourceLocation.tryParse(stack.getOrCreateTag().getString("name"));
    }

    public static int[] getProperty(ItemStack stack) {
        return stack.getOrCreateTag().getIntArray("property").length > 0
                ? stack.getOrCreateTag().getIntArray("property")
                : new int[] { 1, 1, 1, 1, 1 };
    }

    public static int[] getPropertyRate(ItemStack stack) {
        return stack.getOrCreateTag().getIntArray("propertyRate").length > 0
                ? stack.getOrCreateTag().getIntArray("propertyRate")
                : new int[] { 0, 0, 0, 0, 0 };
    }

    public static int[] getExtraProperty(ItemStack stack) {
        return stack.getOrCreateTag().getIntArray("extraProperty").length > 0
                ? stack.getOrCreateTag().getIntArray("extraProperty")
                : new int[] { 1, 6, 4, 0 };
    }

    public static int[] getMaxProperty(ItemStack stack) {
        return stack.getOrCreateTag().getIntArray("maxProperty").length > 0
                ? stack.getOrCreateTag().getIntArray("maxProperty")
                : new int[] { 12, 12, 12, 12, 12 };
    }

    public static Motivations getMotivation(ItemStack stack) {
        return stack.getOrCreateTag().getString("motivation").isBlank() ? Motivations.NORMAL
                : Motivations.valueOf(stack.getOrCreateTag().getString("motivation").toUpperCase());
    }

    public static Growth getGrowth(ItemStack stack) {
        return stack.getOrCreateTag().getString("growth").isBlank() ? Growth.UNTRAINED
                : Growth.valueOf(stack.getOrCreateTag().getString("growth").toUpperCase());
    }

    public static void setMotivation(ItemStack stack, Motivations motivation) {
        stack.getOrCreateTag().putString("motivation", motivation.name().toLowerCase());
    }

    public static void setGrowth(ItemStack stack, Growth growth) {
        stack.getOrCreateTag().putString("growth", growth.name().toLowerCase());
    }

    public static ListTag getSkills(ItemStack stack) {
        return stack.getOrCreateTag().getList("skills", Tag.TAG_STRING);
    }
    
    public static boolean hasSkill(ItemStack stack, ResourceLocation skill) {
        for(Tag tag : stack.getOrCreateTag().getList("skills", Tag.TAG_STRING)) {
            if(tag.getAsString().equals(skill.toString()))
                return true;
        }
        return false;
    }

    public static void addSkill(ItemStack stack, ResourceLocation skill) {
        ListTag result = UmaSoulUtils.getSkills(stack);
        result.add(StringTag.valueOf(skill.toString()));
        stack.getOrCreateTag().put("skills", result);
    }

    public static int getSelectedSkillIndex(ItemStack stack) {
        return stack.getOrCreateTag().getInt("selectedSkill");
    }

    public static void setSelectedSkill(ItemStack stack, int slots) {
        stack.getOrCreateTag().putInt("selectedSkill", slots);
    }

    public static ResourceLocation getSelectedSkill(ItemStack stack) {
        String skill = UmaSoulUtils.getSkills(stack).getString(getSelectedSkillIndex(stack));
        return skill.isBlank() ? UmaSkillRegistry.BASIC_PACE.getId() : ResourceLocation.tryParse(skill);
    }

    public static int getSkillSlots(ItemStack stack) {
        return getExtraProperty(stack)[2];
    }

    public static void setSkillSlots(ItemStack stack, int slots) {
        getExtraProperty(stack)[2] = slots;
    }

    public static boolean hasEmptySkillSlot(ItemStack stack) {
        ListTag skills = UmaSoulUtils.getSkills(stack);
        if (UmaSoulUtils.getSkillSlots(stack) <= skills.size())
            return false;
        return true;
    }

    public static void selectFormerSkill(ItemStack stack) {
        if (UmaSoulUtils.getSkills(stack).size() <= 1)
            return;
        int slot = UmaSoulUtils.getSelectedSkillIndex(stack);
        UmaSoulUtils.setSelectedSkill(stack, slot == 0 ? UmaSoulUtils.getSkills(stack).size() - 1 : slot - 1);
    }

    public static void selectLatterSkill(ItemStack stack) {
        if (UmaSoulUtils.getSkills(stack).size() <= 1)
            return;
        int slot = UmaSoulUtils.getSelectedSkillIndex(stack);
        UmaSoulUtils.setSelectedSkill(stack, slot == UmaSoulUtils.getSkills(stack).size() - 1 ? 0 : slot + 1);
    }

    public static int getActionPoint(ItemStack stack) {
        return Math.max(stack.getOrCreateTag().getInt("actionPoint"), 0);
    }

    public static void setActionPoint(ItemStack stack, int ap) {
        stack.getOrCreateTag().putInt("actionPoint", ap);
    }

    public static void addActionPoint(ItemStack stack, int ap) {
        UmaSoulUtils.setActionPoint(stack,
                Math.min(UmaSoulUtils.getActionPoint(stack) + ap, UmaSoulUtils.getMaxActionPoint(stack)));
    }

    public static int getMaxActionPoint(ItemStack stack) {
        return getExtraProperty(stack)[3]
                + getProperty(stack)[4] * (int) (200 * (1.0D + (UmaSoulUtils.getPropertyRate(stack)[4] / 100.0D)));
    }

    public static void setMaxActionPoint(ItemStack stack, int ap) {
        getExtraProperty(stack)[3] = ap;
    }

    public static int getPhysique(ItemStack stack) {
        return getExtraProperty(stack)[0];
    }

    public static void setPhysique(ItemStack stack, int phy) {
        getExtraProperty(stack)[0] = phy;
    }

    public static void downPhysique(ItemStack stack) {
        int phy = Math.max(getPhysique(stack) - 1, 0);
        setPhysique(stack, phy);
    }

    public static int getLearningTimes(ItemStack stack) {
        return getExtraProperty(stack)[1];
    }

    public static void setLearningTimes(ItemStack stack, int learns) {
        getExtraProperty(stack)[1] = learns;
    }

    public static void downLearningTimes(ItemStack stack) {
        int learns = Math.max(getLearningTimes(stack) - 1, 0);
        setLearningTimes(stack, learns);
    }
}
