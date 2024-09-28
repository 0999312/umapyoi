package net.tracen.umapyoi.curios;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class UmaSuitCuriosWrapper implements ICurio {
    private final ItemStack stack;

    public UmaSuitCuriosWrapper(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public void curioTick(SlotContext slotContext) {
        ICurio.super.curioTick(slotContext);
    }

    @Override
    public boolean canEquip(SlotContext slotContext) {
    	return CuriosApi.getEntitySlots(slotContext.entity()).containsKey("uma_suit") 
    			&& slotContext.identifier().equals("uma_suit");
    }

    @Override
    public SoundInfo getEquipSound(SlotContext slotContext) {
        return new SoundInfo(SoundEvents.ARMOR_EQUIP_LEATHER.value(), 1.0f, 1.0f);
    }

    @Override
    public ItemStack getStack() {
        return stack;
    }

}
