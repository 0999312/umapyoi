package net.tracen.umapyoi.registry.skills;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class LowHealthBuffSkill extends UmaSkill {

    public LowHealthBuffSkill(Builder builder) {
        super(builder);
    }

    @Override
    public void applySkill(Level level, LivingEntity user) {
        boolean lowHealth = (user.getHealth() / user.getMaxHealth()) < 0.6;
        user.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100 * this.getSkillLevel(),
                lowHealth ? this.getSkillLevel() : this.getSkillLevel() - 1));
        user.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100 * this.getSkillLevel(),
                lowHealth ? this.getSkillLevel() : this.getSkillLevel() - 1));
    }

}
