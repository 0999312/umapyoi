package net.tracen.umapyoi.events;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import net.tracen.umapyoi.registry.umadata.Motivations;

import java.util.Objects;

public class MotivationEvent extends Event implements ICancellableEvent {
    public final Motivations previous;
    private final Motivations afterDefault;
    private Motivations after;
    private final boolean doTriggerBonusDefault;
    public Boolean doTriggerBonus;
    private final ItemStack targetSoul;
    private final LivingEntity target;

    public MotivationEvent(Motivations previous, Motivations after, boolean doTriggerBonus, ItemStack soul, LivingEntity target) {
        this.previous = previous;
        this.afterDefault = after;
        this.doTriggerBonus = doTriggerBonus;
        this.doTriggerBonusDefault = doTriggerBonus;
        this.targetSoul = soul;
        this.target = target;
    }

    public Motivations getAfter() {
        return Objects.requireNonNullElse(this.after, this.afterDefault);
    }

    public void setAfter(Motivations motiv) {
        this.after = motiv;
    }

    public boolean getDoTriggerBonus() {
        return Objects.requireNonNullElse(doTriggerBonus, doTriggerBonusDefault);
    }

    public void setDoTriggerBonus(Boolean trigger) {
        this.doTriggerBonus = trigger;
    }

    public ItemStack getTargetSoul() {
        return this.targetSoul;
    }

    public LivingEntity getTarget() {
        return this.target;
    }
}
