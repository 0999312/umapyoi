package net.tracen.umapyoi.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.registry.umadata.Motivations;

public class UmaStatusUtils {

    public static enum StatusType {
        SPEED(0), STAMINA(1), STRENGTH(2), GUTS(3), WISDOM(4);

        private Integer id;

        StatusType(Integer id) {
            this.id = id;
        }

        public Integer getId() {
            return this.id;
        }
    }

    public static Component getStatusLevel(int level) {
        return Component.translatable("umastatus.level." + level);
    }

    public static void addMotivation(ItemStack stack) {
        Motivations motivation = UmaSoulUtils.getMotivation(stack);
        switch (motivation) {
        case PERFECT -> UmaSoulUtils.setMotivation(stack, Motivations.PERFECT);
        case GOOD -> UmaSoulUtils.setMotivation(stack, Motivations.PERFECT);
        case NORMAL -> UmaSoulUtils.setMotivation(stack, Motivations.GOOD);
        case DOWN -> UmaSoulUtils.setMotivation(stack, Motivations.NORMAL);
        case BAD -> UmaSoulUtils.setMotivation(stack, Motivations.DOWN);
        default -> throw new IllegalArgumentException("Unexpected motivation value: " + motivation);
        }
    }

    public static void downMotivation(ItemStack stack) {
        Motivations motivation = UmaSoulUtils.getMotivation(stack);
        switch (motivation) {
        case PERFECT -> UmaSoulUtils.setMotivation(stack, Motivations.GOOD);
        case GOOD -> UmaSoulUtils.setMotivation(stack, Motivations.NORMAL);
        case NORMAL -> UmaSoulUtils.setMotivation(stack, Motivations.DOWN);
        case DOWN -> UmaSoulUtils.setMotivation(stack, Motivations.BAD);
        case BAD -> UmaSoulUtils.setMotivation(stack, Motivations.BAD);
        default -> throw new IllegalArgumentException("Unexpected motivation value: " + motivation);
        }
    }

}
