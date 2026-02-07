package net.tracen.umapyoi.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.effect.MobEffectRegistry;
import net.tracen.umapyoi.events.MotivationEvent;
import net.tracen.umapyoi.registry.umadata.Motivations;

import javax.annotation.Nullable;
import java.util.Optional;

public class UmaStatusUtils {

    public static enum StatusType {
        SPEED(0), STAMINA(1), STRENGTH(2), GUTS(3), WISDOM(4);

        private Integer id;

        StatusType(Integer id) {
            this.id = id;
        }

        public Integer getId() {
            return this.id;
        }
    }

    public static Component getStatusLevel(int level) {
        return Component.translatable("umastatus.level." + level);
    }

    public static Motivations getMotivationAfterOffset(Motivations original, int offset) {
        int originalLevel = original.ordinal();
        int newLevel = Mth.clamp(originalLevel - offset, 0, Motivations.values().length - 1);
        return Motivations.values()[newLevel];
    }

    public static void changeMotivation(ItemStack stack, int level) {
        UmaSoulUtils.setMotivation(stack, getMotivationAfterOffset(UmaSoulUtils.getMotivation(stack), level));
    }

    public static void addMotivation(ItemStack stack) {
        changeMotivation(stack, 1);
    }

    public static void changeMotivation(@Nullable LivingEntity entity, int addMotivLevel) {
        ItemStack stack = UmapyoiAPI.getUmaSoul(entity);
        if (stack.isEmpty()) return;
        Motivations motivation = UmaSoulUtils.getMotivation(stack);
        boolean triggerMood = entity != null && motivation == Motivations.PERFECT && addMotivLevel > 0;
        Motivations resultMotivation = getMotivationAfterOffset(motivation, addMotivLevel);

        MotivationEvent evt = new MotivationEvent(motivation, resultMotivation, triggerMood, stack, entity);
        if (MinecraftForge.EVENT_BUS.post(evt)) return;

        if (evt.getDoTriggerBonus()) {
            int level = Optional.ofNullable(entity.getEffect(MobEffectRegistry.MOOD_BONUS.get()))
                    .map(MobEffectInstance::getAmplifier).orElse(-1) + 1;
            entity.addEffect(new MobEffectInstance(MobEffectRegistry.MOOD_BONUS.get(), 1200, level));
        }

        UmaSoulUtils.setMotivation(stack, evt.getAfter());
    }

    public static void addMotivation(@Nullable LivingEntity entity) {
        changeMotivation(entity, 1);
    }

    public static void downMotivation(ItemStack stack) {
        changeMotivation(stack, -1);
    }

}
