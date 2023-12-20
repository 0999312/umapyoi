package net.tracen.umapyoi.registry.skills;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class LowHealthHealSkill extends UmaSkill {

    public LowHealthHealSkill(Builder builder) {
        super(builder);
    }

    @Override
    public void applySkill(Level level, LivingEntity user) {
        boolean lowHealth = (user.getHealth() / user.getMaxHealth()) < 0.6;
        user.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100 * this.getSkillLevel(),
                lowHealth ? this.getSkillLevel() : this.getSkillLevel() - 1));
        user.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200 * this.getSkillLevel(),
                lowHealth ? this.getSkillLevel() : this.getSkillLevel() - 1));
        if(lowHealth)
            user.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, this.getSkillLevel() - 1));
    }


}
