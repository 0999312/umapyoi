package net.tracen.umapyoi.events;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.tracen.umapyoi.registry.training.SupportStack;

public abstract class ApplyTrainingSupportEvent extends Event {
    private final SupportStack support;
    private final ItemStack soul;

    public ApplyTrainingSupportEvent(SupportStack stack, ItemStack soul) {
        this.support = stack;
        this.soul = soul;
    }

    public SupportStack getSupport() {
        return support;
    }

    public ItemStack getUmaSoul() {
        return this.soul;
    }

    @Cancelable
    public static class Pre extends ApplyTrainingSupportEvent {
        public Pre(SupportStack stack, ItemStack soul) {
            super(stack, soul);
        }
    }

    public static class Post extends ApplyTrainingSupportEvent {
        public Post(SupportStack stack, ItemStack soul) {
            super(stack, soul);
        }
    }
}
