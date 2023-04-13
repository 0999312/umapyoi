package net.tracen.umapyoi.clothing;

import net.minecraft.world.entity.LivingEntity;

public class NOTClothingCondition implements ClothingCondition {
    private final ClothingCondition condition;

    public NOTClothingCondition(ClothingCondition condition) {
        this.condition = condition;
    }

    @Override
    public boolean test(LivingEntity user) {
        return !this.getCondition().test(user);
    }

    public ClothingCondition getCondition() {
        return this.condition;
    }

}
