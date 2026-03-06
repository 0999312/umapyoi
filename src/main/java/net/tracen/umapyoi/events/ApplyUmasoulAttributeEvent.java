package net.tracen.umapyoi.events;

import com.google.common.collect.Multimap;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.tracen.umapyoi.utils.UmaStatusUtils;
import org.apache.commons.lang3.function.TriFunction;
import top.theillusivec4.curios.api.SlotContext;

public class ApplyUmasoulAttributeEvent extends UmaSoulEvent {
	private final SlotContext slotContext;
	private final ResourceLocation id;
	private final Multimap<Holder<Attribute>, AttributeModifier> atts;
	private final LivingEntity user;
	private final TriFunction<LivingEntity, UmaStatusUtils.StatusType, Double, Double> valueFunction;

	public ApplyUmasoulAttributeEvent(LivingEntity user, ItemStack soul, SlotContext slotContext, ResourceLocation id,
									  Multimap<Holder<Attribute>, AttributeModifier> atts,
									  TriFunction<LivingEntity, UmaStatusUtils.StatusType, Double, Double> valueFunction) {
		super(soul);
		this.id = id;
		this.slotContext = slotContext;
		this.atts = atts;
		this.valueFunction = valueFunction;
		this.user = user;
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

	@Override
	public void setUmaSoul(ItemStack soul) {
		throw new UnsupportedOperationException("Tried to set a new soul for ApplyUmasoulAttributeEvent");
	}

	public double getExactProperty(UmaStatusUtils.StatusType status, double limit) {
		return getExactProperty(this.user, status, limit);
	}

	public double getExactProperty(LivingEntity user, UmaStatusUtils.StatusType status, double limit) {
		return this.valueFunction.apply(user, status, limit);
	}
}