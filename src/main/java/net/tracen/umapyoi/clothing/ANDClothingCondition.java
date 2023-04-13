package net.tracen.umapyoi.clothing;

import net.minecraft.world.entity.LivingEntity;

public class ANDClothingCondition implements ClothingCondition {
    private final ClothingCondition[] conditions;

    public ANDClothingCondition(ClothingCondition... conditions) {
        this.conditions = conditions;
    }

    @Override
    public boolean test(LivingEntity user) {
        for (ClothingCondition condition : this.getConditions()) {
            if (!condition.test(user))
                return false;
        }
        return true;
    }

    public ClothingCondition[] getConditions() {
        return conditions;
    }

}
