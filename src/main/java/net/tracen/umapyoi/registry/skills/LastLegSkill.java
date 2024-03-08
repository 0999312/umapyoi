package net.tracen.umapyoi.registry.skills;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class LastLegSkill extends UmaSkill {
    private final int life;

    public LastLegSkill(Builder builder, int life) {
        super(builder);
        this.life = life;
    }

    @Override
    public void applySkill(Level level, LivingEntity user) {
        ItemStack soul = UmapyoiAPI.getUmaSoul(user);
        int skillLevel = this.getSkillLevel() - 1 + (UmaSoulUtils.getProperty(soul)[2] >= 10 ? 2 : UmaSoulUtils.getProperty(soul)[2] >= 7 ? 1 : 0);
        user.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, this.getSpeedTime(), skillLevel));
        user.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, this.getSpeedTime(), skillLevel));
    }

    public int getSpeedTime() {
        return life;
    }

}
