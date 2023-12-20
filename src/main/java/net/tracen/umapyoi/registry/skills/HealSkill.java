package net.tracen.umapyoi.registry.skills;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class HealSkill extends UmaSkill {

    public HealSkill(Builder builder) {
        super(builder);
    }

    @Override
    public void applySkill(Level level, LivingEntity user) {
        ItemStack soul = UmapyoiAPI.getUmaSoul(user);
        int skillTime = UmaSoulUtils.getProperty(soul)[4] >= 10 ? 200 : UmaSoulUtils.getProperty(soul)[4] >= 7 ? 160 : 120;
        user.addEffect(new MobEffectInstance(MobEffects.REGENERATION, skillTime, this.getSkillLevel() - 1));
    }



}
