package net.tracen.umapyoi.registry.skills;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class LastLegSkill extends UmaSkill {
    private final int level;
    private final int life;

    public LastLegSkill(Builder builder, int level, int life) {
        super(builder);
        this.level = level;
        this.life = life;
    }

    @Override
    public void applySkill(Level level, Player user) {
        ItemStack soul = UmapyoiAPI.getUmaSoul(user);
        int skillLevel = this.getSpeedLevel() + (UmaSoulUtils.getProperty(soul)[2] >= 10 ? 2 : UmaSoulUtils.getProperty(soul)[2] >= 7 ? 1 : 0);
        user.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, this.getSpeedTime(), skillLevel));
        user.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, this.getSpeedTime(), skillLevel));
    }

    public int getSpeedLevel() {
        return level;
    }

    public int getSpeedTime() {
        return life;
    }

}
