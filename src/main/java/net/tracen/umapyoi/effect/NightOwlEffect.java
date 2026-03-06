package net.tracen.umapyoi.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.events.MotivationEvent;
import net.tracen.umapyoi.utils.UmaStatusUtils;

import static net.tracen.umapyoi.UmapyoiConfig.NIGHT_OWL_PROBABILITY_DOWN_MOTIVATION;

@EventBusSubscriber
public class NightOwlEffect extends MobEffect {
    public NightOwlEffect() {
        super(MobEffectCategory.HARMFUL, 0);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return (duration % 20) == 0;
    }

    @Override
    public boolean applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        Level level = pLivingEntity.level();
        if (level.isClientSide) return true;
        if (UmapyoiAPI.getUmaSoul(pLivingEntity).isEmpty()) return true;
        if (level.random.nextDouble() <= NIGHT_OWL_PROBABILITY_DOWN_MOTIVATION.get()) {
            UmaStatusUtils.changeMotivation(pLivingEntity, -1);
        }
        return true;
    }



    @SubscribeEvent
    public static void onMotivationChange(MotivationEvent event) {
        LivingEntity target = event.getTarget();
        if (!target.hasEffect(MobEffectRegistry.NIGHT_OWL)) return;
        if (event.getDoTriggerBonus() || event.getAfter().compareTo(event.previous) < 0) {
            event.setCanceled(true);
        }
    }
}
