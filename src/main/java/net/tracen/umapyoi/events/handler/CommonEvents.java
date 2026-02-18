package net.tracen.umapyoi.events.handler;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerSpawnPhantomsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.data.tag.UmapyoiItemTags;
import net.tracen.umapyoi.effect.MobEffectRegistry;
import net.tracen.umapyoi.events.ApplyFactorEvent;
import net.tracen.umapyoi.events.ApplyTrainingSupportEvent;
import net.tracen.umapyoi.events.SkillEvent;
import net.tracen.umapyoi.registry.umadata.Motivations;
import net.tracen.umapyoi.utils.ResultRankingUtils;
import net.tracen.umapyoi.utils.UmaSkillUtils;
import net.tracen.umapyoi.utils.UmaSoulUtils;
import net.tracen.umapyoi.utils.UmaStatusUtils;

@Mod.EventBusSubscriber
public class CommonEvents {
    @SubscribeEvent
    public static void onDamageDownMotivation(LivingDamageEvent event) {
        LivingEntity entityLiving = event.getEntity();
        ItemStack soul = UmapyoiAPI.getUmaSoul(entityLiving);
        if (soul.isEmpty())
            return;
        if (event.getAmount() < UmapyoiConfig.DAMAGE_MOTIVATION_EFFECT.get())
            return;
        if (UmapyoiConfig.CHANCE_MOTIVATION_EFFECT.get() > 0) {
            if (entityLiving.level().getRandom().nextDouble() <= UmapyoiConfig.CHANCE_MOTIVATION_EFFECT.get())
                UmaStatusUtils.downMotivation(soul);
        }
    }

    @SubscribeEvent
    public static void onDamagePanicking(LivingDamageEvent event) {
        LivingEntity entityLiving = event.getEntity();
        ItemStack soul = UmapyoiAPI.getUmaSoul(entityLiving);
        if (soul.isEmpty() || UmaSoulUtils.getMotivation(soul) != Motivations.BAD)
            return;
        if (event.getAmount() < UmapyoiConfig.DAMAGE_MOTIVATION_EFFECT.get())
            return;
        if (UmapyoiConfig.CHANCE_MOTIVATION_EFFECT.get() > 0) {
            if (entityLiving.level().getRandom().nextDouble() <= UmapyoiConfig.CHANCE_MOTIVATION_EFFECT.get()) {
                if (entityLiving.hasEffect(MobEffectRegistry.MOOD_BONUS.get())) {
                    entityLiving.removeEffect(MobEffectRegistry.MOOD_BONUS.get());
                    return;
                }
                entityLiving.addEffect(new MobEffectInstance(MobEffectRegistry.PANICKING.get(), 3600));
            }
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
    
    @SubscribeEvent
    public static void onSkillLearned(SkillEvent.LearnSkillEvent event) {
        var umaSoul = event.getUmaSoul();
        UmaSkillUtils.syncActionPoint(umaSoul);
        CompoundTag tag = umaSoul.getOrCreateTag();
        tag.putInt("resultRanking", ResultRankingUtils.generateRanking(umaSoul));
    }

    @SubscribeEvent
    public static void onConsumedItem(LivingEntityUseItemEvent.Finish evt) {
        ItemStack stack = evt.getItem();
        if (!stack.isEdible()) return;
        LivingEntity entity = evt.getEntity();
        if (entity == null) return;
        if (entity.level().isClientSide()) return;
        Level world = entity.level();
        if (UmapyoiAPI.getUmaSoul(entity).isEmpty()) return;

        int statusCnt = Motivations.values().length;
        for (int i = 1 - statusCnt; i < statusCnt; i++) {
            if (i == 0) continue;
            if (stack.is(UmapyoiItemTags.getMotivationFoodTag(i))) {
                UmaStatusUtils.changeMotivation(entity, i);
                break;
            }
        }

        if (stack.is(UmapyoiItemTags.SLOW_METABOLISM)) {
            double p = UmapyoiConfig.SLOW_METABOLISM_PROBABILITY.get();
            if (p != 0) {
                if (world.getRandom().nextDouble() <= p)
                    entity.addEffect(new MobEffectInstance(MobEffectRegistry.SLOW_METABOLISM.get(), 3600));
            }
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent evt) {
        if (evt.phase == TickEvent.Phase.END) {
            Level world = evt.level;
            if (world.isClientSide) return;
            if (world instanceof ServerLevel level) {
                for (ServerPlayer player: level.players()) {
                    if (player.isSpectator()) continue;
                    if (player.isSleeping()) {
                        if (player.hasEffect(MobEffectRegistry.NIGHT_OWL.get()))
                            player.removeEffect(MobEffectRegistry.NIGHT_OWL.get());
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPhantomEvent(PlayerSpawnPhantomsEvent event) {
        Player player = event.getEntity();
        if (player.isSpectator()) return;
        if (player.isSleeping()) return;
        if (player instanceof ServerPlayer serverplayer) {
            if (UmapyoiAPI.getUmaSoul(serverplayer).isEmpty()) return;
            ServerStatsCounter serverstatscounter = serverplayer.getStats();
            int timeSinceRest = serverstatscounter.getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));
            if (timeSinceRest >= 72000) {
                MobEffectInstance effectInstance = new MobEffectInstance(MobEffectRegistry.NIGHT_OWL.get(), -1);
                serverplayer.addEffect(effectInstance);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerSlept(PlayerSleepInBedEvent evt) {
        Player player = evt.getEntity();
        if (player == null) return;
        if (player.hasEffect(MobEffectRegistry.NIGHT_OWL.get())) {
            player.removeEffect(MobEffectRegistry.NIGHT_OWL.get());
        }
    }
}
