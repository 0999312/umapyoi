package net.tracen.umapyoi.registry.skills;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class NutritionalSupplementsSkill extends UmaSkill {

    public NutritionalSupplementsSkill(Builder builder) {
        super(builder);
    }

    @Override
    public void applySkill(Level level, LivingEntity user) {
        ItemStack soul = UmapyoiAPI.getUmaSoul(user);
        int skillTime = UmaSoulUtils.getProperty(soul)[4] >= 10 ? 180 : UmaSoulUtils.getProperty(soul)[4] >= 7 ? 120 : 80;
        user.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, skillTime, this.getSkillLevel() - 1));
        user.addEffect(new MobEffectInstance(MobEffects.SATURATION, skillTime / 2, this.getSkillLevel() - 1));
        user.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, skillTime / 2, 0));
    }

    @Override
    public SoundEvent getSound() {
        return SoundEvents.PLAYER_BURP;
    }

}
