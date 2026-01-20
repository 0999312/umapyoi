package net.tracen.umapyoi.item.food;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.utils.UmaSoulUtils;
import net.tracen.umapyoi.utils.UmaStatusUtils;

public final class EnergyDrinkMethods {
    public static void royalBitter(LivingEntity entity) {
        ItemStack soul = UmapyoiAPI.getUmaSoul(entity);
        UmaStatusUtils.downMotivation(soul);
        UmaSoulUtils.setActionPoint(soul, UmaSoulUtils.getMaxActionPoint(soul));
    }
    public static void smallEnergy(LivingEntity entity) {
        ItemStack soul = UmapyoiAPI.getUmaSoul(entity);
        UmaSoulUtils.addActionPoint(soul, (int) (UmaSoulUtils.getMaxActionPoint(soul) * 0.2));
    }
    public static void mediumEnergy(LivingEntity entity) {
        ItemStack soul = UmapyoiAPI.getUmaSoul(entity);
        UmaSoulUtils.addActionPoint(soul, (int) (UmaSoulUtils.getMaxActionPoint(soul) * 0.4));
    }
    public static void largeEnergy(LivingEntity entity) {
        ItemStack soul = UmapyoiAPI.getUmaSoul(entity);
        UmaSoulUtils.addActionPoint(soul, (int) (UmaSoulUtils.getMaxActionPoint(soul) * 0.65));
    }
}
