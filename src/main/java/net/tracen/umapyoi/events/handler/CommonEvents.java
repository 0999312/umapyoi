package net.tracen.umapyoi.events.handler;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.effect.MobEffectRegistry;
import net.tracen.umapyoi.events.ApplyFactorEvent;
import net.tracen.umapyoi.events.ApplyTrainingSupportEvent;
import net.tracen.umapyoi.registry.umadata.Motivations;
import net.tracen.umapyoi.utils.ResultRankingUtils;
import net.tracen.umapyoi.utils.UmaSkillUtils;
import net.tracen.umapyoi.utils.UmaSoulUtils;
import net.tracen.umapyoi.utils.UmaStatusUtils;

@Mod.EventBusSubscriber
public class CommonEvents {
    @SubscribeEvent
    public static void onDamageDownMotivation(LivingDamageEvent event) {
        LivingEntity entityLiving = event.getEntityLiving();
        ItemStack soul = UmapyoiAPI.getUmaSoul(entityLiving);
        if (soul.isEmpty())
            return;
        if (event.getAmount() < UmapyoiConfig.DAMAGE_MOTIVATION_EFFECT.get())
            return;
        if (UmapyoiConfig.CHANCE_MOTIVATION_EFFECT.get() > 0) {
            if (entityLiving.getLevel().getRandom().nextDouble() <= UmapyoiConfig.CHANCE_MOTIVATION_EFFECT.get())
                UmaStatusUtils.downMotivation(soul);
        }
    }

    @SubscribeEvent
    public static void onDamagePanicking(LivingDamageEvent event) {
        LivingEntity entityLiving = event.getEntityLiving();
        ItemStack soul = UmapyoiAPI.getUmaSoul(entityLiving);
        if (soul.isEmpty() || UmaSoulUtils.getMotivation(soul) != Motivations.BAD)
            return;
        if (event.getAmount() < UmapyoiConfig.DAMAGE_MOTIVATION_EFFECT.get())
            return;
        if (UmapyoiConfig.CHANCE_MOTIVATION_EFFECT.get() > 0) {
            if (entityLiving.getLevel().getRandom().nextDouble() <= UmapyoiConfig.CHANCE_MOTIVATION_EFFECT.get())
                entityLiving.addEffect(new MobEffectInstance(MobEffectRegistry.PANICKING.get(), 3600));
        }

    }

    @SubscribeEvent
    public static void onTrainingFinished(ApplyTrainingSupportEvent.Post event) {
        var umaSoul = event.getUmaSoul();
        UmaSkillUtils.syncActionPoint(umaSoul);
        CompoundTag tag = umaSoul.getOrCreateTag();
        tag.putInt("resultRanking", ResultRankingUtils.generateRanking(umaSoul));
    }

    @SubscribeEvent
    public static void onFactorFinished(ApplyFactorEvent.Post event) {
        var umaSoul = event.getUmaSoul();
        UmaSkillUtils.syncActionPoint(umaSoul);
        CompoundTag tag = umaSoul.getOrCreateTag();
        tag.putInt("resultRanking", ResultRankingUtils.generateRanking(umaSoul));
    }
}
