package net.tracen.umapyoi.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.events.MotivationEvent;
import net.tracen.umapyoi.utils.UmaStatusUtils;

import static net.tracen.umapyoi.UmapyoiConfig.NIGHT_OWL_PROBABILITY_DOWN_MOTIVATION;

@Mod.EventBusSubscriber
public class NightOwlEffect extends MobEffect {
    public NightOwlEffect() {
        super(MobEffectCategory.HARMFUL, 0);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return (pDuration % 20) == 0;
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        Level level = pLivingEntity.level();
        if (level.isClientSide) return;
        if (UmapyoiAPI.getUmaSoul(pLivingEntity).isEmpty()) return;
        if (level.random.nextDouble() <= NIGHT_OWL_PROBABILITY_DOWN_MOTIVATION.get()) {
            UmaStatusUtils.changeMotivation(pLivingEntity, -1);
        }
    }

    @SubscribeEvent
    public static void onMotivationChange(MotivationEvent event) {
        LivingEntity target = event.getTarget();
        if (!target.hasEffect(MobEffectRegistry.NIGHT_OWL.get())) return;
        if (event.getDoTriggerBonus() || event.getAfter().compareTo(event.previous) < 0) {
            event.setCanceled(true);
        }
    }
}
