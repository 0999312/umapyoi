package net.tracen.umapyoi.events;

import com.google.common.collect.Multimap;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import top.theillusivec4.curios.api.SlotContext;

public class ApplyUmasoulAttributeEvent extends Event {
	private final ItemStack soul;
	private final SlotContext slotContext;
	private final ResourceLocation id;
	private final Multimap<Holder<Attribute>, AttributeModifier> atts;
	public ApplyUmasoulAttributeEvent(ItemStack soul, SlotContext slotContext, ResourceLocation id, Multimap<Holder<Attribute>, AttributeModifier> atts) {
		this.soul = soul;
		this.id = id;
		this.slotContext = slotContext;
		this.atts = atts;
	}
	
	public SlotContext getSlotContext() {
		return slotContext;
	}
	
	public ResourceLocation getID() {
		return id;
	}
	
	public Multimap<Holder<Attribute>, AttributeModifier> getAttributes() {
		return atts;
	}

	public ItemStack getUmaSoul() {
		return soul;
	}

}
