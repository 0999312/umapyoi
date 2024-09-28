package net.tracen.umapyoi.utils;

import java.util.List;

import net.minecraft.Util;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.item.data.DataLocation;
import net.tracen.umapyoi.item.data.GachaRankingData;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.umadata.Motivations;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.registry.umadata.UmaDataBasicStatus;
import net.tracen.umapyoi.registry.umadata.UmaDataExtraStatus;
import net.tracen.umapyoi.registry.umadata.UmaDataSkills;
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
        GachaRanking ranking = data.ranking();
		result.set(DataComponentsTypeRegistry.GACHA_RANKING, new GachaRankingData(ranking));
        result.set(DataComponentsTypeRegistry.UMADATA_BASIC_STATUS, UmaDataBasicStatus.init(data.property()));
        result.set(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS, UmaDataBasicStatus.init(data.maxProperty()));
        result.set(DataComponentsTypeRegistry.UMADATA_STATUS_RATE, UmaDataBasicStatus.init(data.propertyRate()));
        result.set(DataComponentsTypeRegistry.UMADATA_SKILLS, new UmaDataSkills(UmaDataSkills.DEFAULT.skillSlot(), 0, 
        		List.of(data.uniqueSkill())
        		));
        result.set(DataComponentsTypeRegistry.UMADATA_EXTRA_STATUS, 
        		new UmaDataExtraStatus(data.property()[4] * 200, 0, 
				ResultRankingUtils.generateRanking(result), Motivations.NORMAL));
        result.set(DataComponents.RARITY, 
        		ranking == GachaRanking.SSR ? Rarity.EPIC : ranking == GachaRanking.SR ? Rarity.UNCOMMON : Rarity.COMMON
        		);
        result.set(DataComponentsTypeRegistry.UMADATA_TRAINING, new UmaDataTranining(1, 6));
        return result;
    }

    public static ResourceLocation getName(ItemStack stack) {
        return stack.getOrDefault(DataComponentsTypeRegistry.DATA_LOCATION.get(), 
        		new DataLocation(UmaData.DEFAULT_UMA_ID)).name();
    }

    public static UmaDataBasicStatus getProperty(ItemStack stack) {
    	return stack.getOrDefault(DataComponentsTypeRegistry.UMADATA_BASIC_STATUS.get(), 
        		new UmaDataBasicStatus(1, 1, 1, 1, 1));
    }

    public static UmaDataBasicStatus getPropertyRate(ItemStack stack) {
    	return stack.getOrDefault(DataComponentsTypeRegistry.UMADATA_STATUS_RATE.get(), 
        		new UmaDataBasicStatus(0, 0, 0, 0, 0));
    }

    public static UmaDataBasicStatus getMaxProperty(ItemStack stack) {
    	return stack.getOrDefault(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS.get(), 
        		new UmaDataBasicStatus(12, 12, 12, 12, 12));
    }

    public static Motivations getMotivation(ItemStack stack) {
        return stack.getOrDefault(DataComponentsTypeRegistry.UMADATA_EXTRA_STATUS, UmaDataExtraStatus.DEFAULT).motivation();
    }

    public static void setMotivation(ItemStack stack, Motivations motivation) {
        stack.update(DataComponentsTypeRegistry.UMADATA_EXTRA_STATUS, UmaDataExtraStatus.DEFAULT, 
        		data->new UmaDataExtraStatus(data.actionPoint(), data.extraActionPoint(), data.resultRanking(), motivation));
    }

    public static List<ResourceLocation> getSkills(ItemStack stack) {
    	return stack.getOrDefault(DataComponentsTypeRegistry.UMADATA_SKILLS, UmaDataSkills.DEFAULT).skills();
    }
    
    public static boolean hasSkill(ItemStack stack, ResourceLocation skill) {
    	for(var loc : stack.getOrDefault(DataComponentsTypeRegistry.UMADATA_SKILLS, UmaDataSkills.DEFAULT).skills()) {
    		if(skill.equals(loc))
    			return true;
    	}

        return false;
    }

    public static void addSkill(ItemStack stack, ResourceLocation skill) {
        stack.update(DataComponentsTypeRegistry.UMADATA_SKILLS, UmaDataSkills.DEFAULT,
        		data->{
        			data.skills().add(skill);
        			return data;
        		});
    }

    public static int getSelectedSkillIndex(ItemStack stack) {
        return stack.getOrDefault(DataComponentsTypeRegistry.UMADATA_SKILLS, UmaDataSkills.DEFAULT).selectedSkill();
    }

    public static void setSelectedSkill(ItemStack stack, int slot) {
    	stack.update(DataComponentsTypeRegistry.UMADATA_SKILLS, UmaDataSkills.DEFAULT, 
        		data->new UmaDataSkills(data.skillSlot(), slot, data.skills()));
    }

    public static ResourceLocation getSelectedSkill(ItemStack stack) {
        ResourceLocation skill = UmaSoulUtils.getSkills(stack).get(getSelectedSkillIndex(stack));
        return skill == null ? UmaSkillRegistry.BASIC_PACE.getId() : skill;
    }

    public static int getSkillSlots(ItemStack stack) {
        return stack.get(DataComponentsTypeRegistry.UMADATA_SKILLS).skillSlot();
    }

    public static void setSkillSlots(ItemStack stack, int slots) {
    	stack.update(DataComponentsTypeRegistry.UMADATA_SKILLS, UmaDataSkills.DEFAULT, 
        		data->new UmaDataSkills(slots, data.selectedSkill(), data.skills()));
    }

    public static boolean hasEmptySkillSlot(ItemStack stack) {
    	var slots = stack.get(DataComponentsTypeRegistry.UMADATA_SKILLS).skillSlot();
    	var learned = stack.get(DataComponentsTypeRegistry.UMADATA_SKILLS).skills().size();
    	return learned < slots;
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
    	stack.update(DataComponentsTypeRegistry.UMADATA_EXTRA_STATUS, UmaDataExtraStatus.DEFAULT, 
    			data-> new UmaDataExtraStatus(ap, data.extraActionPoint(), data.resultRanking(), data.motivation()));
    }

    public static void addActionPoint(ItemStack stack, int ap) {
        UmaSoulUtils.setActionPoint(stack,
                Math.min(UmaSoulUtils.getActionPoint(stack) + ap, UmaSoulUtils.getMaxActionPoint(stack)));
    }

    public static int getMaxActionPoint(ItemStack stack) {
        return stack.get(DataComponentsTypeRegistry.UMADATA_EXTRA_STATUS).extraActionPoint()
                + getProperty(stack).wisdom() * (int) (200 * (1.0D + (UmaSoulUtils.getPropertyRate(stack).wisdom() / 100.0D)));
    }

    public static int getExtraActionPoint(ItemStack stack) {
        return Math.max(stack.get(DataComponentsTypeRegistry.UMADATA_EXTRA_STATUS).extraActionPoint(), 0);
    }
    
    public static void setExtraActionPoint(ItemStack stack, int ap) {
    	stack.update(DataComponentsTypeRegistry.UMADATA_EXTRA_STATUS, UmaDataExtraStatus.DEFAULT, 
    			data-> new UmaDataExtraStatus(data.actionPoint(), ap, data.resultRanking(), data.motivation()));
    }

    public static int getPhysique(ItemStack stack) {
        return stack.get(DataComponentsTypeRegistry.UMADATA_TRAINING).physique();
    }

    public static void setPhysique(ItemStack stack, int phy) {
    	stack.update(DataComponentsTypeRegistry.UMADATA_TRAINING, new UmaDataTranining(1, 6), 
    			data-> new UmaDataTranining(phy, data.talent()));
    }

    public static void downPhysique(ItemStack stack) {
        int phy = Math.max(getPhysique(stack) - 1, 0);
        setPhysique(stack, phy);
    }

    public static int getLearningTimes(ItemStack stack) {
        return stack.get(DataComponentsTypeRegistry.UMADATA_TRAINING).talent();
    }

    public static void setLearningTimes(ItemStack stack, int learns) {
        stack.update(DataComponentsTypeRegistry.UMADATA_TRAINING, new UmaDataTranining(1, 6), 
    			data-> new UmaDataTranining(data.physique(), learns));
    }

    public static void downLearningTimes(ItemStack stack) {
        int learns = Math.max(getLearningTimes(stack) - 1, 0);
        setLearningTimes(stack, learns);
    }
}
