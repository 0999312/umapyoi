package net.tracen.umapyoi.events.handler;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.curios.UmaSoulCuriosWrapper;
import net.tracen.umapyoi.curios.UmaSuitCuriosWrapper;
import net.tracen.umapyoi.effect.MobEffectRegistry;
import net.tracen.umapyoi.events.ApplyFactorEvent;
import net.tracen.umapyoi.events.ApplyTrainingSupportEvent;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.registry.umadata.Motivations;
import net.tracen.umapyoi.utils.ResultRankingUtils;
import net.tracen.umapyoi.utils.UmaSkillUtils;
import net.tracen.umapyoi.utils.UmaSoulUtils;
import net.tracen.umapyoi.utils.UmaStatusUtils;
import top.theillusivec4.curios.api.CuriosCapability;

@EventBusSubscriber
public class CommonEvents {
    @SubscribeEvent
    public static void onDamageDownMotivation(LivingDamageEvent.Post event) {
        LivingEntity entityLiving = event.getEntity();
        ItemStack soul = UmapyoiAPI.getUmaSoul(entityLiving);
        if (soul.isEmpty())
            return;
        if (event.getNewDamage() < UmapyoiConfig.DAMAGE_MOTIVATION_EFFECT.get())
            return;
        if (UmapyoiConfig.CHANCE_MOTIVATION_EFFECT.get() > 0) {
            if (entityLiving.level().getRandom().nextDouble() <= UmapyoiConfig.CHANCE_MOTIVATION_EFFECT.get())
                UmaStatusUtils.downMotivation(soul);
        }
    }

    @SubscribeEvent
    public static void onDamagePanicking(LivingDamageEvent.Post event) {
        LivingEntity entityLiving = event.getEntity();
        ItemStack soul = UmapyoiAPI.getUmaSoul(entityLiving);
        if (soul.isEmpty() || UmaSoulUtils.getMotivation(soul) != Motivations.BAD)
            return;
        if (event.getNewDamage() < UmapyoiConfig.DAMAGE_MOTIVATION_EFFECT.get())
            return;
        if (UmapyoiConfig.CHANCE_MOTIVATION_EFFECT.get() > 0) {
            if (entityLiving.level().getRandom().nextDouble() <= UmapyoiConfig.CHANCE_MOTIVATION_EFFECT.get())
                entityLiving.addEffect(new MobEffectInstance(MobEffectRegistry.PANICKING, 3600));
        }

    }
    
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
    	event.registerItem(CuriosCapability.ITEM, 
    			(stack, context) -> new UmaSoulCuriosWrapper(stack), ItemRegistry.UMA_SOUL);
    	event.registerItem(CuriosCapability.ITEM, 
    			(stack, context) -> new UmaSuitCuriosWrapper(stack)
    			, ItemRegistry.TRAINNING_SUIT, ItemRegistry.SWIMSUIT
    			, ItemRegistry.SUMMER_UNIFORM, ItemRegistry.WINTER_UNIFORM
    			);
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
