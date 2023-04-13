package net.tracen.umapyoi.clothing;

import com.google.gson.JsonObject;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class EmptyClothingCondition implements ClothingCondition {

    @Override
    public boolean test(LivingEntity user) {
        return true;
    }

    public static class EmptyClothingConditionSerializer extends ClothingConditionSerializer<EmptyClothingCondition> {

        @Override
        public EmptyClothingCondition read(ResourceLocation location, JsonObject object) {
            return new EmptyClothingCondition();
        }

        @Override
        public JsonObject write(JsonObject object, EmptyClothingCondition instance) {
            return object;
        }

    }

}
