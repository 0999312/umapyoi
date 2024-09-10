package net.tracen.umapyoi.inventory;

import javax.annotation.Nonnull;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class TerminalResultHandler implements IItemHandler {
    private final int slot_output;
    private final IItemHandler itemHandler;

    public TerminalResultHandler(IItemHandler itemHandler, int output) {
        this.itemHandler = itemHandler;
        this.slot_output = output;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return itemHandler.isItemValid(slot, stack);
    }

    @Override
    public int getSlots() {
        return itemHandler.getSlots();
    }

    @Override
    @Nonnull
    public ItemStack getStackInSlot(int slot) {
        return itemHandler.getStackInSlot(slot);
    }

    @Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        return stack;
    }

    @Override
    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        var soul = itemHandler.getStackInSlot(slot_output);
        if(soul.is(ItemRegistry.UMA_SOUL.get())) {
            if(UmaSoulUtils.getPhysique(soul) <= 0)
                return slot == slot_output ? itemHandler.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        return itemHandler.getSlotLimit(slot);
    }
}
