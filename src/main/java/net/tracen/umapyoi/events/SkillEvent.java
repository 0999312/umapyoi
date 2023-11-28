package net.tracen.umapyoi.events;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.skills.UmaSkill;

public abstract class SkillEvent extends Event {
    private final ResourceLocation skill;

    public SkillEvent(ResourceLocation skill) {
        this.skill = skill;
    }

    public ResourceLocation getSkillResourceLocation() {
        return skill;
    }

    public UmaSkill getSkill() {
        return UmaSkillRegistry.REGISTRY.get().getValue(getSkillResourceLocation());
    }

    public static class LearnSkillEvent extends SkillEvent {
        public LearnSkillEvent(ResourceLocation skill) {
            super(skill);
        }
    }

    @Cancelable
    public static class UseSkillEvent extends SkillEvent {
        private final Level level;
        private final Player user;

        public UseSkillEvent(ResourceLocation skill, Level level, Player user) {
            super(skill);
            this.level = level;
            this.user = user;
        }

        public Level getLevel() {
            return level;
        }

        public Player getPlayer() {
            return user;
        }
    }

    public static class ApplySkillEvent extends SkillEvent {
        private final Level level;
        private final Player user;

        public ApplySkillEvent(ResourceLocation skill, Level level, Player user) {
            super(skill);
            this.level = level;
            this.user = user;
        }

        public Level getLevel() {
            return level;
        }

        public Player getPlayer() {
            return user;
        }
    }
}
