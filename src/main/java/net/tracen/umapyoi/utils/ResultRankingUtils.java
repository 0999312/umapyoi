package net.tracen.umapyoi.utils;

import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.registry.UmaSkillRegistry;

public final class ResultRankingUtils {
    public static int getRanking(ItemStack soul) {
        return soul.get(DataComponentsTypeRegistry.UMADATA_EXTRA_STATUS.get()).resultRanking();
    }

    public static int generateRanking(ItemStack soul) {
        int[] property = UmaSoulUtils.getProperty(soul);
        int skills = 0;
        
        for(Tag tag : UmaSoulUtils.getSkills(soul)) {
            skills += UmaSkillRegistry.REGISTRY.get(ResourceLocation.tryParse(tag.getAsString())).getSkillLevel();
        }
        
        return ResultRankingUtils
                .generateRanking(property[0] + property[1] + property[2] + property[3] + property[4] + skills);
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
