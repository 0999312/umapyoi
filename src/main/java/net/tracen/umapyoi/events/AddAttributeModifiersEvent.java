package net.tracen.umapyoi.events;

import java.util.UUID;

import com.google.common.collect.Multimap;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;
import top.theillusivec4.curios.api.SlotContext;

public class AddAttributeModifiersEvent extends Event {
    private final SlotContext slotContext;
    private final UUID uuid;
    private final ItemStack soul;
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public AddAttributeModifiersEvent(
            ItemStack soul, 
            SlotContext slotContext, 
            UUID uuid, 
            Multimap<Attribute, AttributeModifier> attributeModifiers
            ) {
        this.soul = soul;
        this.slotContext = slotContext;
        this.uuid = uuid;
        this.attributeModifiers = attributeModifiers;
    }

    public SlotContext getSlotContext() {
        return slotContext;
    }

    public UUID getUuid() {
        return uuid;
    }

    public ItemStack getSoul() {
        return soul;
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers() {
        return attributeModifiers;
    }

}
