package net.tracen.umapyoi.registry.training;

import java.util.List;

import net.minecraft.world.item.ItemStack;

public interface SupportContainer {
    public boolean isConsumable(ItemStack stack);
    public int getSupportLevel(ItemStack stack);
    public SupportType getSupportType(ItemStack stack);
    public List<SupportStack> getSupports(ItemStack stack);
}
