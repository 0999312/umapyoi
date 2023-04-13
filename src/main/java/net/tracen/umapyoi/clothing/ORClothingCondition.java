package net.tracen.umapyoi.clothing;

import net.minecraft.world.entity.LivingEntity;

public class ORClothingCondition implements ClothingCondition {
    private final ClothingCondition[] conditions;

    public ORClothingCondition(ClothingCondition... conditions) {
        this.conditions = conditions;
    }

    @Override
    public boolean test(LivingEntity user) {
        for (ClothingCondition condition : this.getConditions()) {
            if (condition.test(user))
                return true;
        }
        return false;
    }

    public ClothingCondition[] getConditions() {
        return conditions;
    }

}
