package net.tracen.umapyoi.events;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
    	private final ItemStack umasoul;
        public LearnSkillEvent(ResourceLocation skill, ItemStack umasoul) {
            super(skill);
            this.umasoul = umasoul;
        }
        
		public ItemStack getUmaSoul() {
			return umasoul;
		}
    }

    @Cancelable
    public static class UseSkillEvent extends SkillEvent {
        private final Level level;
        private final Player user;
        private int ap;

        public UseSkillEvent(ResourceLocation skill, Level level, Player user, int ap) {
            super(skill);
            this.level = level;
            this.user = user;
            this.ap = ap;
        }

        // Left for old api capabilities. DO NOT USE.
        @Deprecated
        public UseSkillEvent(ResourceLocation skill, Level level, Player user) {
            this(skill, level, user, UmaSkillRegistry.REGISTRY.get().getValue(skill).getActionPoint());
        }

        public Level getLevel() {
            return level;
        }

        public Player getPlayer() {
            return user;
        }

        public void setAp(int ap) {
            this.ap = ap;
        }

        public int getAp() {
            return this.ap;
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
