package net.tracen.umapyoi.registry.skills;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.effect.MobEffectRegistry;
import net.tracen.umapyoi.utils.UmaStatusUtils;

public class SteelWillSkill extends UmaSkill {
    public SteelWillSkill(Builder builder) {
        super(builder);
    }

    @Override
    public void applySkill(Level level, LivingEntity user) {
        UmaStatusUtils.addMotivation(UmapyoiAPI.getUmaSoul(user));
        user.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 300, 1));
        user.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 1));
        if (user.hasEffect(MobEffectRegistry.PANICKING.get()))
            user.removeEffect(MobEffectRegistry.PANICKING.get());
    }

}
