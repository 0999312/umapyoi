package net.tracen.umapyoi.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.effect.MobEffectRegistry;
import net.tracen.umapyoi.item.ItemRegistry;
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

    public static void addMotivation(ItemStack stack) {
        Motivations motivation = UmaSoulUtils.getMotivation(stack);
        switch (motivation) {
            case PERFECT, GOOD -> UmaSoulUtils.setMotivation(stack, Motivations.PERFECT);
            case NORMAL -> UmaSoulUtils.setMotivation(stack, Motivations.GOOD);
            case DOWN -> UmaSoulUtils.setMotivation(stack, Motivations.NORMAL);
            case BAD -> UmaSoulUtils.setMotivation(stack, Motivations.DOWN);
            default -> throw new IllegalArgumentException("Unexpected motivation value: " + motivation);
        }
    }

    public static void addMotivation(@Nullable LivingEntity entity) {
        ItemStack stack = UmapyoiAPI.getUmaSoul(entity);
        if (stack.isEmpty()) return;
        Motivations motivation = UmaSoulUtils.getMotivation(stack);
        if (entity != null && motivation == Motivations.PERFECT) {
            int level = Optional.ofNullable(entity.getEffect(MobEffectRegistry.MOOD_BONUS.get()))
                    .map(MobEffectInstance::getAmplifier).orElse(-1) + 1;
            entity.addEffect(new MobEffectInstance(MobEffectRegistry.MOOD_BONUS.get(), 1200, level));
        }
        addMotivation(stack);
    }

    public static void downMotivation(ItemStack stack) {
        Motivations motivation = UmaSoulUtils.getMotivation(stack);
        switch (motivation) {
            case PERFECT -> UmaSoulUtils.setMotivation(stack, Motivations.GOOD);
            case GOOD -> UmaSoulUtils.setMotivation(stack, Motivations.NORMAL);
            case NORMAL -> UmaSoulUtils.setMotivation(stack, Motivations.DOWN);
            case DOWN, BAD -> UmaSoulUtils.setMotivation(stack, Motivations.BAD);
            default -> throw new IllegalArgumentException("Unexpected motivation value: " + motivation);
        }
    }

}
