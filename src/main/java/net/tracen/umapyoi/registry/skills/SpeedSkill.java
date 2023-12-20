package net.tracen.umapyoi.registry.skills;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class SpeedSkill extends UmaSkill {
    private final int life;

    public SpeedSkill(Builder builder, int life) {
        super(builder);
        this.life = life;
    }

    @Override
    public void applySkill(Level level, LivingEntity user) {
        ItemStack soul = UmapyoiAPI.getUmaSoul(user);
        int skillTime = this.getSpeedTime() + (UmaSoulUtils.getProperty(soul)[4] >= 10 ? 80 : 0);
        int skillLevel = this.getSkillLevel() - 1 + (UmaSoulUtils.getProperty(soul)[2] >= 10 ? 1 : 0);
        user.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, skillTime, skillLevel));
    }

    public int getSpeedTime() {
        return life;
    }

}
