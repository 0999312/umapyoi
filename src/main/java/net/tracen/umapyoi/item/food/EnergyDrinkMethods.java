package net.tracen.umapyoi.item.food;

import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.utils.UmaSoulUtils;
import net.tracen.umapyoi.utils.UmaStatusUtils;

public final class EnergyDrinkMethods {
    public static void royalBitter(ItemStack soul) {
        UmaStatusUtils.downMotivation(soul);
        UmaSoulUtils.setActionPoint(soul, UmaSoulUtils.getMaxActionPoint(soul));
    }
    public static void smallEnergy(ItemStack soul) {
        UmaSoulUtils.addActionPoint(soul, (int) (UmaSoulUtils.getMaxActionPoint(soul) * 0.2));
    }
    public static void mediumEnergy(ItemStack soul) {
        UmaSoulUtils.addActionPoint(soul, (int) (UmaSoulUtils.getMaxActionPoint(soul) * 0.4));
    }
    public static void largeEnergy(ItemStack soul) {
        UmaSoulUtils.addActionPoint(soul, (int) (UmaSoulUtils.getMaxActionPoint(soul) * 0.65));
    }
}
