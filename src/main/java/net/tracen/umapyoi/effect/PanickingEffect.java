package net.tracen.umapyoi.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.tracen.umapyoi.events.ResumeActionPointEvent;

@EventBusSubscriber
public class PanickingEffect extends MobEffect {
    public PanickingEffect() {
        super(MobEffectCategory.HARMFUL, 0);
    }

    @SubscribeEvent
    public static void onResumeAP(ResumeActionPointEvent event) {
        if (event.getLivingEntity().hasEffect(MobEffectRegistry.PANICKING))
            event.setCanceled(true);
    }
}
