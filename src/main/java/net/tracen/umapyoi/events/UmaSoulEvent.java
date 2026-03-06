package net.tracen.umapyoi.events;

import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;

public abstract class UmaSoulEvent extends Event {
    protected ItemStack soul;
    public UmaSoulEvent(ItemStack soul) {
        this.soul = soul;
    }

    public ItemStack getUmaSoul() {
        return soul;
    }
    public void setUmaSoul(ItemStack soul) {
        this.soul = soul;
    }
}
