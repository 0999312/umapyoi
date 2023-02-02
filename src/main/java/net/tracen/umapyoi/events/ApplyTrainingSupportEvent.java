package net.tracen.umapyoi.events;

import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.tracen.umapyoi.capability.IUmaCapability;
import net.tracen.umapyoi.registry.training.SupportStack;

public abstract class ApplyTrainingSupportEvent extends Event {
    private final SupportStack support;
    private final IUmaCapability cap;
    public ApplyTrainingSupportEvent(SupportStack stack, IUmaCapability cap) {
        this.support = stack;
        this.cap = cap;
    }
    
    public SupportStack getSupport() {
        return support;
    }

    public IUmaCapability getCapability() {
        return cap;
    }

    @Cancelable
    public static class Pre extends ApplyTrainingSupportEvent{
        public Pre(SupportStack stack, IUmaCapability cap) {
            super(stack, cap);
        }
    }
    
    public static class Post extends ApplyTrainingSupportEvent{
        public Post(SupportStack stack, IUmaCapability cap) {
            super(stack, cap);
        }
    }
}
