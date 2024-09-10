package net.tracen.umapyoi.events;

import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import net.tracen.umapyoi.registry.training.SupportStack;

public abstract class ApplyTrainingSupportEvent extends Event implements ICancellableEvent{
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
