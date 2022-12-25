package net.tracen.umapyoi.container;

import java.util.Objects;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.block.entity.ThreeGoddessBlockEntity;
import net.tracen.umapyoi.item.ItemRegistry;

public class ThreeGoddessContainer extends AbstractContainerMenu {

    public final ThreeGoddessBlockEntity tileEntity;
    public final ItemStackHandler inventory;
    private final ContainerData containerData;
    private final ContainerLevelAccess canInteractWithCallable;

    public ThreeGoddessContainer(final int windowId, final Inventory playerInventory,
            final ThreeGoddessBlockEntity tileEntity, ContainerData dataIn) {
        super(ContainerRegistry.THREE_GODDESS.get(), windowId);
        this.tileEntity = tileEntity;
        this.inventory = tileEntity.getInventory();
        this.containerData = dataIn;
        this.canInteractWithCallable = ContainerLevelAccess.create(tileEntity.getLevel(), tileEntity.getBlockPos());
        int startX = 8;
        
        this.addSlot(new ThreeGoddessJewelSlot(inventory, 0, 80, 27));
        
        this.addSlot(new ThreeGoddessFactorSlot(inventory, 1, 50, 78));
        this.addSlot(new ThreeGoddessFactorSlot(inventory, 2, 109, 78));

        this.addSlot(new CommonResultSlot(playerInventory.player, inventory, 3, 80, 79));

        // Main Player Inventory
        int startPlayerInvY = 137;
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, startX + (column * 18),
                        startPlayerInvY + (row * 18)));
            }
        }

        // Hotbar
        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInventory, column, startX + (column * 18), 195));
        }

        this.addDataSlots(dataIn);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        // 0-3: Contain inventory
        // 4-30: Player inventory
        // 31-40: Hot bar in the player inventory

        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemStack = itemStack1.copy();

            if (index >= 0 && index <= 3) {
                if (!this.moveItemStackTo(itemStack1, 4, 40, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemStack1, itemStack);
            } else if (index >= 4) {
                if (index >= 4 && index < 31) {
                    if (!this.moveItemStackTo(itemStack1, 31, 40, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 31 && index < 40 && !this.moveItemStackTo(itemStack1, 4, 31, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack1, 4, 40, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack1.getCount() == 0) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemStack1.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemStack1);
        }

        return itemStack;
    }

    private static ThreeGoddessBlockEntity getTileEntity(final Inventory playerInventory, final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity tileAtPos = playerInventory.player.level.getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof ThreeGoddessBlockEntity) {
            return (ThreeGoddessBlockEntity) tileAtPos;
        }
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }

    public ThreeGoddessContainer(final int windowId, final Inventory playerInventory, final FriendlyByteBuf data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data), new SimpleContainerData(4));
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(canInteractWithCallable, playerIn, BlockRegistry.THREE_GODDESS.get());
    }

    @OnlyIn(Dist.CLIENT)
    public int getProgressionScaled() {
        int i = this.containerData.get(0);
        return i != 0 ? i * 158 / ThreeGoddessBlockEntity.MAX_PROCESS_TIME : 0;
    }

    public static class ThreeGoddessJewelSlot extends SlotItemHandler {

        public ThreeGoddessJewelSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.is(ItemRegistry.JEWEL.get());
        }
        
        @Override
        public int getMaxStackSize(ItemStack stack) {
            return 1;
        }
    }
    
    public static class ThreeGoddessFactorSlot extends SlotItemHandler {

        public ThreeGoddessFactorSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.is(ItemRegistry.UMA_FACTOR_ITEM.get());
        }
    }

}
