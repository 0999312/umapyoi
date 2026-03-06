package net.tracen.umapyoi.inventory;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;

import javax.annotation.Nonnull;

public class UniversalIOItemHandler implements IItemHandler {
    // This class implements a Universal Input/Output Item Handler.
    // Constraints: all slot numbers < inputs is input, otherwise output.

    private final int slots_input;
    private final IItemHandler itemHandler;

    public UniversalIOItemHandler(IItemHandler itemHandler, int inputs) {
        this.itemHandler = itemHandler;
        this.slots_input = inputs;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return (slot < this.slots_input) && itemHandler.isItemValid(slot, stack);
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
        return slot < slots_input ? itemHandler.insertItem(slot, stack, simulate) : stack;
    }

    @Override
    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return slot >= slots_input ? itemHandler.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        return itemHandler.getSlotLimit(slot);
    }
}
