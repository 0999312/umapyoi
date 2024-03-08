package net.tracen.umapyoi.inventory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class CommonItemHandler implements IItemHandler {
    private final int slots_input;
    private final int slot_output;
    private final IItemHandler itemHandler;
    private final Direction side;

    public CommonItemHandler(IItemHandler itemHandler, @Nullable Direction side, int inputs, int output) {
        this.itemHandler = itemHandler;
        this.side = side;
        this.slots_input = inputs;
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
        if (side == null || side.equals(Direction.UP)) {
            return slot < slots_input ? itemHandler.insertItem(slot, stack, simulate) : stack;
        } else {
            return stack;
        }
    }

    @Override
    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (side == null || side.equals(Direction.UP)) {
            return slot < slots_input ? itemHandler.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
        } else {
            return slot == slot_output ? itemHandler.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        return itemHandler.getSlotLimit(slot);
    }
}
