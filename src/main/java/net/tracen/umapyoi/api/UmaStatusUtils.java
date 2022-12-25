package net.tracen.umapyoi.api;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.tracen.umapyoi.registry.umadata.Motivations;
import net.tracen.umapyoi.registry.umadata.UmaStatus;

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
        return new TranslatableComponent("umastatus.level." + level);
    }

    public static void addMotivation(UmaStatus status) {
        Motivations motivation = status.getMotivation();
        switch (motivation) {
        case PERFECT -> status.setMotivation(Motivations.PERFECT);
        case GOOD -> status.setMotivation(Motivations.PERFECT);
        case NORMAL -> status.setMotivation(Motivations.GOOD);
        case DOWN -> status.setMotivation(Motivations.NORMAL);
        case BAD -> status.setMotivation(Motivations.DOWN);
        default -> throw new IllegalArgumentException("Unexpected motivation value: " + motivation);
        }
    }

    public static void downMotivation(UmaStatus status) {
        Motivations motivation = status.getMotivation();
        switch (motivation) {
        case PERFECT -> status.setMotivation(Motivations.GOOD);
        case GOOD -> status.setMotivation(Motivations.NORMAL);
        case NORMAL -> status.setMotivation(Motivations.DOWN);
        case DOWN -> status.setMotivation(Motivations.BAD);
        case BAD -> status.setMotivation(Motivations.BAD);
        default -> throw new IllegalArgumentException("Unexpected motivation value: " + motivation);
        }
    }
}
