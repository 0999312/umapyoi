package net.tracen.umapyoi.registry.skills;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

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
        user.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, this.getSpeedTime(), this.getSpeedLevel()));
    }

    public int getSpeedLevel() {
        return level;
    }
    
    public int getSpeedTime() {
        return life;
    }

}
