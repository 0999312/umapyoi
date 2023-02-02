package net.tracen.umapyoi.events;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.capability.CapabilityRegistry;
import net.tracen.umapyoi.utils.UmaSkillUtils;
import net.tracen.umapyoi.utils.UmaStatusUtils;

@Mod.EventBusSubscriber
public class CommonEvents {
    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if(event.getEntityLiving() instanceof Player player) {
            ItemStack soul = UmapyoiAPI.getUmaSoul(player);
            if(soul.isEmpty()) return ;
            if(event.getAmount() < UmapyoiConfig.DAMAGE_MOTIVATION_EFFECT.get()) return ;
            if(UmapyoiConfig.CHANCE_MOTIVATION_EFFECT.get() > 0) {
                if(player.getLevel().getRandom().nextDouble() <= UmapyoiConfig.CHANCE_MOTIVATION_EFFECT.get())
                soul.getCapability(CapabilityRegistry.UMACAP).ifPresent(cap->{
                    UmaStatusUtils.downMotivation(cap.getUmaStatus());
                });
            }
        }
    }
    
    @SubscribeEvent
    public static void onTrainingFinished(ApplyTrainingSupportEvent.Post event) {
        UmaSkillUtils.syncActionPoint(event.getCapability());
    }
    
    @SubscribeEvent
    public static void onTrainingFinished(ApplyFactorEvent.Post event) {
        UmaSkillUtils.syncActionPoint(event.getCapability());
    }
}
