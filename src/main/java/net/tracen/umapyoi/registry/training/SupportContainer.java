package net.tracen.umapyoi.registry.training;

import java.util.List;
import java.util.function.Predicate;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.utils.GachaRanking;

public interface SupportContainer {
    public boolean isConsumable(Level level, ItemStack stack);

    public GachaRanking getSupportLevel(Level level, ItemStack stack);

    public SupportType getSupportType(Level level, ItemStack stack);

    public List<SupportStack> getSupports(Level level, ItemStack stack);

    public Predicate<ItemStack> canSupport(Level level, ItemStack stack);
}
