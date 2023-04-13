package net.tracen.umapyoi.events;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class ResumeActionPointEvent extends Event {
    private final LivingEntity entity;
    private final ItemStack soul;

    public ResumeActionPointEvent(LivingEntity entity, ItemStack soul) {
        this.entity = entity;
        this.soul = soul;
    }

    public LivingEntity getLivingEntity() {
        return entity;
    }

    public ItemStack getUmaSoul() {
        return this.soul;
    }

}
