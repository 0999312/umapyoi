package net.tracen.umapyoi.registry.training;

import java.util.List;
import java.util.function.Predicate;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.capability.IUmaCapability;


public interface SupportContainer {
    public boolean isConsumable(Level level, ItemStack stack);
    public int getSupportLevel(Level level, ItemStack stack);
    public SupportType getSupportType(Level level, ItemStack stack);
    public List<SupportStack> getSupports(Level level, ItemStack stack);
    public Predicate<IUmaCapability> canSupport(Level level, ItemStack stack);
}
