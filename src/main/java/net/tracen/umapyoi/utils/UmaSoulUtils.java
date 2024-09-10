package net.tracen.umapyoi.utils;

import java.util.List;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.data.builtin.UmaDataRegistry;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.item.data.DataLocation;
import net.tracen.umapyoi.item.data.GachaRankingData;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.umadata.Motivations;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.registry.umadata.UmaDataBasicStatus;
import net.tracen.umapyoi.registry.umadata.UmaDataExtraStatus;
import net.tracen.umapyoi.registry.umadata.UmaDataTranining;

public class UmaSoulUtils {
    
    public static Component getTranslatedUmaName(ItemStack stack) {
        return UmaSoulUtils.getTranslatedUmaName(UmaSoulUtils.getName(stack));
    }

    public static Component getTranslatedUmaName(ResourceLocation name) {
        return Component.translatable(Util.makeDescriptionId("umadata", name));
    }

    public static ItemStack initUmaSoul(ItemStack stack, ResourceLocation name, UmaData data) {
        ItemStack result = stack.copy();
        result.set(DataComponentsTypeRegistry.DATA_LOCATION, new DataLocation(name));
        result.set(DataComponentsTypeRegistry.GACHA_RANKING, new GachaRankingData(data.ranking()));
        result.set(DataComponentsTypeRegistry.UMADATA_BASIC_STATUS, UmaDataBasicStatus.init(data.property()));
        result.set(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS, UmaDataBasicStatus.init(data.maxProperty()));
        result.set(DataComponentsTypeRegistry.UMADATA_STATUS_RATE, UmaDataBasicStatus.init(data.propertyRate()));
        result.set(DataComponentsTypeRegistry.UMADATA_EXTRA_STATUS, 
        		new UmaDataExtraStatus(data.property()[4] * 200, 0, 
				ResultRankingUtils.generateRanking(result), Motivations.NORMAL));
        
        result.set(DataComponentsTypeRegistry.UMADATA_TRAINING, new UmaDataTranining(1, 6));
        return result;
    }

    public static ResourceLocation getName(ItemStack stack) {
        return stack.getOrDefault(DataComponentsTypeRegistry.DATA_LOCATION.get(), 
        		new DataLocation(UmaData.DEFAULT_UMA_ID)).name();
    }

    public static UmaDataBasicStatus getProperty(ItemStack stack) {
//        return stack.getOrCreateTag().getIntArray("property").length > 0
//                ? stack.getOrCreateTag().getIntArray("property")
//                : new int[] { 1, 1, 1, 1, 1 };
    	return stack.getOrDefault(DataComponentsTypeRegistry.UMADATA_BASIC_STATUS.get(), 
        		new UmaDataBasicStatus(1, 1, 1, 1, 1));
    }

    public static UmaDataBasicStatus getPropertyRate(ItemStack stack) {
//        return stack.getOrCreateTag().getIntArray("propertyRate").length > 0
//                ? stack.getOrCreateTag().getIntArray("propertyRate")
//                : new int[] { 0, 0, 0, 0, 0 };
    	return stack.getOrDefault(DataComponentsTypeRegistry.UMADATA_STATUS_RATE.get(), 
        		new UmaDataBasicStatus(0, 0, 0, 0, 0));
    }

//    public static int[] getExtraProperty(ItemStack stack) {
//        return stack.getOrCreateTag().getIntArray("extraProperty").length > 0
//                ? stack.getOrCreateTag().getIntArray("extraProperty")
//                : new int[] { 1, 6, 4, 0 };
//    }

    public static int[] getMaxProperty(ItemStack stack) {
        return stack.getOrCreateTag().getIntArray("maxProperty").length > 0
                ? stack.getOrCreateTag().getIntArray("maxProperty")
                : new int[] { 12, 12, 12, 12, 12 };
    }

    public static Motivations getMotivation(ItemStack stack) {
        return stack.get(DataComponentsTypeRegistry.UMADATA_EXTRA_STATUS).motivation();
    }

    public static void setMotivation(ItemStack stack, Motivations motivation) {
        stack.update(DataComponentsTypeRegistry.UMADATA_EXTRA_STATUS, UmaDataExtraStatus.DEFAULT, 
        		data->new UmaDataExtraStatus(data.actionPoint(), data.extraActionPoint(), data.resultRanking(), motivation));
    }

    public static List<ResourceLocation> getSkills(ItemStack stack) {
//        return stack.getList("skills", Tag.TAG_STRING);
    	
    }
    
    public static boolean hasSkill(ItemStack stack, ResourceLocation skill) {
//        for(Tag tag : stack.getOrCreateTag().getList("skills", Tag.TAG_STRING)) {
//            if(tag.getAsString().equals(skill.toString()))
//                return true;
//        }
        return false;
    }

    public static void addSkill(ItemStack stack, ResourceLocation skill) {
//        ListTag result = UmaSoulUtils.getSkills(stack);
//        result.add(StringTag.valueOf(skill.toString()));
//        stack.getOrCreateTag().put("skills", result);
//        stack.update(null, null, null)
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
        return Math.max(stack.get(DataComponentsTypeRegistry.UMADATA_EXTRA_STATUS).actionPoint(), 0);
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

    public static void setExtraActionPoint(ItemStack stack, int ap) {
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
