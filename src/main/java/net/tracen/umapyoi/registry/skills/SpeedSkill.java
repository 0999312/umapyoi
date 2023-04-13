package net.tracen.umapyoi.registry.skills;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class SpeedSkill extends UmaSkill {
    private final int level;
    private final int life;

    public SpeedSkill(Builder builder, int level, int life) {
        super(builder);
        this.level = level;
        this.life = life;
    }

    @Override
    public void applySkill(Level level, Player user) {
        ItemStack soul = UmapyoiAPI.getUmaSoul(user);
        int skillTime = this.getSpeedTime() + (UmaSoulUtils.getProperty(soul)[4] >= 10 ? 80 : UmaSoulUtils.getProperty(soul)[4] >= 7 ? 40 : 0);
        int skillLevel = this.getSpeedLevel() + (UmaSoulUtils.getProperty(soul)[2] >= 10 ? 2 : UmaSoulUtils.getProperty(soul)[2] >= 7 ? 1 : 0);
        user.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, skillTime, skillLevel));
    }

    public int getSpeedLevel() {
        return level;
    }

    public int getSpeedTime() {
        return life;
    }

}
