package net.tracen.umapyoi.registry.skills;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.effect.MobEffectRegistry;

public class SereneSkill extends UmaSkill {
    public SereneSkill(Builder builder) {
        super(builder);
    }

    @Override
    public void applySkill(Level level, LivingEntity user) {
        
        user.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 160, 0));
        if (user.hasEffect(MobEffectRegistry.PANICKING.get()))
            user.removeEffect(MobEffectRegistry.PANICKING.get());
    }

    @Override
    public SoundEvent getSound() {
        return SoundEvents.EXPERIENCE_ORB_PICKUP;
    }

}
