package net.tracen.umapyoi.registry.skills;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.utils.UmaStatusUtils;

public class TopUmamusumeSkill extends UmaSkill {

    public TopUmamusumeSkill(Builder builder) {
        super(builder);
    }

    @Override
    public void applySkill(Level level, LivingEntity user) {
        UmaStatusUtils.addMotivation(UmapyoiAPI.getUmaSoul(user));
        user.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300, 1));
        user.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 1));
    }

}
