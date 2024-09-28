package net.tracen.umapyoi.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.umadata.UmaDataBasicStatus;

public final class ResultRankingUtils {
    public static int getRanking(ItemStack soul) {
    	if(soul.has(DataComponentsTypeRegistry.UMADATA_EXTRA_STATUS.get()))
    		return soul.get(DataComponentsTypeRegistry.UMADATA_EXTRA_STATUS.get()).resultRanking();
    	return 0;
    }

    public static int generateRanking(ItemStack soul) {
        UmaDataBasicStatus property = UmaSoulUtils.getProperty(soul);
        int skills = 0;
        
        for(ResourceLocation skill : UmaSoulUtils.getSkills(soul)) {
            skills += UmaSkillRegistry.REGISTRY.get(skill).getSkillLevel();
        }
        
        return ResultRankingUtils
                .generateRanking(
                		property.speed() + 
                		property.stamina() + 
                		property.strength() + 
                		property.guts() + 
                		property.wisdom() + 
                		skills);
    }

    public static int generateRanking(int score) {
        if (score >= 191)
            return 38;
        for (int i = 1; i < 39; i++) {
            if (score < (i * 5 + 1))
                return i - 1;
        }
        return 0;
    }

}
