package net.trc.umapyoi.curios;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.trc.umapyoi.item.UmaSoulItem;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

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
        boolean flag = false;

        if (CuriosApi.getCuriosHelper().getCuriosHandler(slotContext.entity()).isPresent()) {
            var itemHandler = CuriosApi.getCuriosHelper().getCuriosHandler(slotContext.entity()).orElse(null);
            if (itemHandler.getStacksHandler("uma_soul").isPresent()) {
                var stacksHandler = itemHandler.getStacksHandler("uma_soul").orElse(null);
                IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                if (stackHandler.getStackInSlot(0).getItem() instanceof UmaSoulItem) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    @Override
    public SoundInfo getEquipSound(SlotContext slotContext) {
        return new SoundInfo(SoundEvents.ARMOR_EQUIP_LEATHER, 1.0f, 1.0f);
    }

    @Override
    public ItemStack getStack() {
        return stack;
    }

}
