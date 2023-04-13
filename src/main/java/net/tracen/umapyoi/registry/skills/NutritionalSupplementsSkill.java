package net.tracen.umapyoi.registry.skills;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class NutritionalSupplementsSkill extends UmaSkill {
    private final int level;

    public NutritionalSupplementsSkill(Builder builder, int level) {
        super(builder);
        this.level = level;
    }

    @Override
    public void applySkill(Level level, Player user) {
        ItemStack soul = UmapyoiAPI.getUmaSoul(user);
        int skillTime = UmaSoulUtils.getProperty(soul)[4] >= 10 ? 240 : UmaSoulUtils.getProperty(soul)[4] >= 7 ? 160 : 120;
        user.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, skillTime, this.getSkillLevel()));
        user.addEffect(new MobEffectInstance(MobEffects.SATURATION, skillTime / 2, this.getSkillLevel()));
        user.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, skillTime / 2, 0));
    }

    public int getSkillLevel() {
        return level;
    }
    
    @Override
    public SoundEvent getSound() {
        return SoundEvents.PLAYER_BURP;
    }

}
