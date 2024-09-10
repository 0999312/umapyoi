package net.tracen.umapyoi.events;

import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import net.tracen.umapyoi.registry.factors.UmaFactorStack;

public abstract class ApplyFactorEvent extends Event implements ICancellableEvent{
    private final UmaFactorStack factor;
    private final ItemStack soul;

    public ApplyFactorEvent(UmaFactorStack stack, ItemStack soul) {
        this.factor = stack;
        this.soul = soul;
    }

    public UmaFactorStack getFactor() {
        return factor;
    }

    public ItemStack getUmaSoul() {
        return this.soul;
    }

    
    public static class Pre extends ApplyFactorEvent {
        public Pre(UmaFactorStack stack, ItemStack soul) {
            super(stack, soul);
        }
    }

    public static class Post extends ApplyFactorEvent {
        public Post(UmaFactorStack stack, ItemStack soul) {
            super(stack, soul);
        }
    }
}
