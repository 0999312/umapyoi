package net.tracen.umapyoi.api;

import com.google.common.collect.Lists;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TargetSelector {
	// Copied from slashblade, need refine.
	public static final TargetingConditions areaTarget = TargetingConditions.forCombat().range(12.0D);
	public static class AttackablePredicate implements Predicate<LivingEntity> {

		public boolean test(LivingEntity livingentity) {

//			if (!SlashBladeConfig.PVP_ENABLE.get() && livingentity instanceof Player)
//				return false;

			if (livingentity instanceof ArmorStand)
				if (((ArmorStand) livingentity).isMarker())
					return true;
				else
					return false;

//			if (livingentity.getTags().contains(AttackableTag)) {
//				livingentity.removeTag(AttackableTag);
//				return true;
//			}
//			
//			if (!SlashBladeConfig.FRIENDLY_ENABLE.get() && !(livingentity instanceof Enemy)) {
//				return false;
//			}

			if (livingentity.hasPassenger(entity -> entity instanceof Player))
				return false;

			if (livingentity.isCurrentlyGlowing())
				return true;

			if (livingentity.getTeam() != null)
				return true;

//			return !livingentity.getType().is(EntityTypeTags.ATTACKABLE_BLACKLIST);
			return true;
		}
	}

	public static List<Entity> getExtinguishableEntitiesWithinAABB(LivingEntity attacker) {
		double reach = TargetSelector.getResolvedReach(attacker);

		AABB aabb = getResolvedAxisAligned(attacker.getBoundingBox(), attacker.getLookAngle(), reach);
		Level world = attacker.level();
		return world.getEntitiesOfClass(PrimedTnt.class, aabb).stream()
				.filter(e -> (e.distanceToSqr(attacker) < (reach * reach))).collect(Collectors.toList());
	}

	public static List<Entity> getTargettableEntitiesWithinAABB(Level world, LivingEntity attacker) {
		return getTargettableEntitiesWithinAABB(world, attacker,
				getResolvedAxisAligned(attacker.getBoundingBox(), attacker.getLookAngle(), getResolvedReach(attacker)));
	}

	public static List<Entity> getTargettableEntitiesWithinAABB(Level world, LivingEntity attacker, AABB aabb) {
		double reach = TargetSelector.getResolvedReach(attacker);

		return getTargettableEntitiesWithinAABB(world, attacker, aabb, reach);
	}

	public static List<Entity> getReflectableEntitiesWithinAABB(LivingEntity attacker) {
		double reach = TargetSelector.getResolvedReach(attacker);

		AABB aabb = getResolvedAxisAligned(attacker.getBoundingBox(), attacker.getLookAngle(), reach);
		Level world = attacker.level();
		return Stream.of(world.getEntitiesOfClass(Projectile.class, aabb).stream()
				.filter(e -> ((e.getOwner()/* getThrower() */ == null || e.getOwner()/* getThrower() */ != attacker))))

				.flatMap(s -> s).filter(e -> (e.distanceToSqr(attacker) < (reach * reach)))
				.collect(Collectors.toList());
	}
	
	public static List<Entity> getTargettableEntitiesWithinAABB(Level world, LivingEntity attacker, AABB aabb,
			double reach) {
		List<Entity> list1 = Lists.newArrayList();

		list1.addAll(getReflectableEntitiesWithinAABB(attacker));
		list1.addAll(getExtinguishableEntitiesWithinAABB(attacker));

		list1.addAll(world.getEntitiesOfClass(LivingEntity.class, aabb.inflate(5), e -> e.isMultipartEntity()).stream()
				.flatMap(e -> (e.isMultipartEntity()) ? Stream.of(e.getParts()) : Stream.of(e)).filter(t -> {
					boolean result = false;
					var check = new AttackablePredicate();
					if (t instanceof LivingEntity living) {
						result = check.test(living);
					} else if (t instanceof PartEntity<?> part) {
						if (part.getParent() instanceof LivingEntity living)
							result = check.test(living) && part.distanceToSqr(attacker) < (reach * reach);
					}
					return result;
				}).collect(Collectors.toList()));

		TargetingConditions predicate = areaTarget.range(reach);

		list1.addAll(world.getEntitiesOfClass(LivingEntity.class, aabb).stream()
				.flatMap(e -> (e.isMultipartEntity()) ? Stream.of(e.getParts()) : Stream.of(e)).filter(t -> {
					boolean result = false;
					if (t instanceof LivingEntity living) {
						result = predicate.test(attacker, living);
					} else if (t instanceof PartEntity<?> part) {
						if (part.getParent() instanceof LivingEntity living)
							result = predicate.test(attacker, living) && part.distanceToSqr(attacker) < (reach * reach);
					}
					return result;
				}).collect(Collectors.toList()));

		return list1;
	}

	public static AABB getResolvedAxisAligned(AABB bb, Vec3 dir, double reach) {
		final double padding = 1.0;

		if (dir == Vec3.ZERO) {
			bb = bb.inflate(reach * 2);
		} else {
			bb = bb.move(dir.scale(reach * 0.5)).inflate(reach);
		}

		bb = bb.inflate(padding);

		return bb;
	}

	public static double getResolvedReach(LivingEntity user) {
		double reach = 4.0D; /* 4 block */
		AttributeInstance attrib = user.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
		if (attrib != null) {
			reach = attrib.getValue() - 1;
		}
		return reach;
	}
}
