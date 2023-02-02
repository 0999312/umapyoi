package net.tracen.umapyoi.events;

import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.tracen.umapyoi.capability.IUmaCapability;
import net.tracen.umapyoi.registry.factors.UmaFactorStack;

public abstract class ApplyFactorEvent extends Event {
    private final UmaFactorStack factor;
    private final IUmaCapability cap;
    public ApplyFactorEvent(UmaFactorStack stack, IUmaCapability cap) {
        this.factor = stack;
        this.cap = cap;
    }
    
    public UmaFactorStack getFactor() {
        return factor;
    }

    public IUmaCapability getCapability() {
        return cap;
    }

    @Cancelable
    public static class Pre extends ApplyFactorEvent{
        public Pre(UmaFactorStack stack, IUmaCapability cap) {
            super(stack, cap);
        }
    }
    
    public static class Post extends ApplyFactorEvent{
        public Post(UmaFactorStack stack, IUmaCapability cap) {
            super(stack, cap);
        }
    }
}
