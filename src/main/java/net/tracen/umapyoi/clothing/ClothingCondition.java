package net.tracen.umapyoi.clothing;

import net.minecraft.world.entity.LivingEntity;

public interface ClothingCondition {
    public boolean test(LivingEntity user);
}
