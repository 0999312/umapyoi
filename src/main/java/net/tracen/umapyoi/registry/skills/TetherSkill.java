package net.tracen.umapyoi.registry.skills;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.api.TargetSelector;

import java.util.List;

public class TetherSkill extends UmaSkill {

	public TetherSkill(Builder builder) {
		super(builder);
	}

	@Override
	public void applySkill(Level level, LivingEntity user) {
		List<Entity> founds = TargetSelector.getTargettableEntitiesWithinAABB(level, user,
				user.getBoundingBox().inflate(25.0D), TargetSelector.getResolvedReach(user) + 32D);
		for (Entity entity : founds) {
			if (entity instanceof LivingEntity) {
				((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
						200 * (this.getSkillLevel() + 1), this.getSkillLevel()));
			}
		}

	}

}
