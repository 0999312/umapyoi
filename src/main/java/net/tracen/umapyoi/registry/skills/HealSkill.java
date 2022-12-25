package net.tracen.umapyoi.registry.skills;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class HealSkill extends UmaSkill {
    private final int level;
    public HealSkill(Builder builder, int level) {
        super(builder);
        this.level = level;
    }
    
    @Override
    public void applySkill(Level level, Player user) {
        user.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, this.getSpeedLevel()));
    }

    public int getSpeedLevel() {
        return level;
    }
    

}
