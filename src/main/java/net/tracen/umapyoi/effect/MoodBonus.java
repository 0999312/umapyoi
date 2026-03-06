package net.tracen.umapyoi.effect;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.tracen.umapyoi.events.SettingPropertyEvent;
import net.tracen.umapyoi.events.SkillEvent;
import net.tracen.umapyoi.utils.UmaSoulUtils;

import java.util.Optional;

import static net.tracen.umapyoi.curios.UmaSoulCuriosWrapper.propertyPercentageByValue;

@EventBusSubscriber
public class MoodBonus extends MobEffect {
    public MoodBonus() {
        super(MobEffectCategory.BENEFICIAL, 0);
    }

    @SubscribeEvent
    public static void onSettingProperty(SettingPropertyEvent event) {
        Holder<MobEffect> thisEffect = MobEffectRegistry.MOOD_BONUS;
        LivingEntity entity = event.getLivingEntity();
        if (!entity.hasEffect(thisEffect)) return;
        MobEffectInstance instance = entity.getEffect(thisEffect);
        int levelAdd = Optional.ofNullable(instance).map(MobEffectInstance::getAmplifier).orElse(-1) + 1;
        ItemStack stack = event.getUmaSoul();
        int level = UmaSoulUtils.getProperty(stack).toArray()[event.getAspect().getId()] + levelAdd;
        double propertyFactor = propertyPercentageByValue(level);
        event.setPropertyPercentage(propertyFactor);
        event.setResultProperty(UmaSoulUtils.getMotivation(stack), event.getPropertyRate(), event.getRetiredValue(), propertyFactor);
    }

    @SubscribeEvent
    public static void onBeforeUseSkill(SkillEvent.UseSkillEvent event) {
        Holder<MobEffect> thisEffect = MobEffectRegistry.MOOD_BONUS;
        LivingEntity entity = event.getPlayer();
        if (!entity.hasEffect(thisEffect)) return;
        event.setAp(event.getAp() / 2);
    }
}
