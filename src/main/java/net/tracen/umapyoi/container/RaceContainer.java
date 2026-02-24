package net.tracen.umapyoi.container;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.block.entity.RaceRegisterBlockEntity;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.utils.Position;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Objects;

import static net.tracen.umapyoi.block.entity.RaceRegisterBlockEntity.DATA_SLOT_SIZE;

public class RaceContainer extends AbstractContainerMenu {
    public final RaceRegisterBlockEntity tileEntity;
    public final ItemStackHandler inventory;
    private final ContainerData containerData;
    private final ContainerLevelAccess canInteractWithCallable;

    private static RaceRegisterBlockEntity getTileEntity(final Inventory playerInventory,
                                                             final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity tileAtPos = playerInventory.player.level().getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof RaceRegisterBlockEntity) {
            return (RaceRegisterBlockEntity) tileAtPos;
        }
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }

    public RaceContainer(final int i, final Inventory playerInv, final FriendlyByteBuf data) {
        this(i, playerInv, getTileEntity(playerInv, data), new SimpleContainerData(DATA_SLOT_SIZE));
    }

    public static class UmaSoulSlot extends SlotItemHandler {
        public UmaSoulSlot(IItemHandler handler, int index, int x, int y) {
            super(handler, index, x, y);
        }

        @Override
        public boolean mayPlace(@Nonnull ItemStack stack) {
            return stack.is(ItemRegistry.UMA_SOUL.get());
        }

        @Override
        public int getMaxStackSize(@Nonnull ItemStack stack) {
            return 1;
        }
    }

    public RaceContainer(final int i, final Inventory playerInv, final RaceRegisterBlockEntity te, ContainerData containerData) {
        super(ContainerRegistry.RACE_REGISTER.get(), i);
        this.tileEntity = te;
        this.inventory = te.getInventory();
        this.containerData = containerData;
        assert tileEntity.getLevel() != null;
        this.canInteractWithCallable = ContainerLevelAccess.create(tileEntity.getLevel(), tileEntity.getBlockPos());

        this.addSlot(new UmaSoulSlot(this.inventory, 0, 26, 83));
        this.addSlot(new SlotItemHandler(this.inventory, 1, 44, 83){
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.is(ItemRegistry.UMA_RACE_TICKET.get());
            }
        });

        for (int j = 0; j < 4; j++) {
            // actually, I am not so sure that if bit ops optimization still works in java
            this.addSlot(new SlotItemHandler(this.inventory, j + 2, (j & 1) * 18 + 98, 75 + (j >> 1) * 18));
        }

        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInv, 9 + (row * 9) + column, 8 + (column * 18),
                        120 + (row * 18))); // inline playerInventoryY=120 for opt.
            }
        }

        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInv, column, 8 + (column * 18), 178));
        }

        this.addDataSlots(containerData);
    }

    @Override
    @Nonnull
    public ItemStack quickMoveStack(@Nonnull Player pPlayer, int pIndex) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);

        if (slot.hasItem()) {
            ItemStack stack1 = slot.getItem();
            stack = stack1.copy();

            if (pIndex >= 0 && pIndex < 6) {
                // from container to player inventory
                if (!this.moveItemStackTo(stack1, 6, 6 + 36, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(stack1, stack);
            } else if (pIndex >= 6) {
                // from player inventory to container
                if (pIndex < 6 + 36) {
                    // only move from 0 to 1
                    if (!this.moveItemStackTo(stack1, 0, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (stack1.getCount() == 0) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack1.getCount() == stack.getCount()) return ItemStack.EMPTY;

            slot.onTake(pPlayer, stack1);
        }

        return stack;
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(canInteractWithCallable, playerIn, BlockRegistry.RACE_REGISTER_BLOCK.get());
    }

    @OnlyIn(Dist.CLIENT)
    public int getProgressInTick() {
        return this.containerData.get(0);
    }

    @OnlyIn(Dist.CLIENT)
    public float getProgression() {
        return this.getProgressInTick() / (float) this.containerData.get(1);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shallSoulWin() {
        return this.containerData.get(2) != 0;
    }

    @OnlyIn(Dist.CLIENT)
    public long getWinnerReplaceSeed() {
        return ((long) this.containerData.get(3)) | ((long) this.containerData.get(4) << 32);
    }

    @OnlyIn(Dist.CLIENT)
    public int getAnimationTickMod(int modVal) {
        return Math.floorMod(this.containerData.get(0), modVal);
    }

    @OnlyIn(Dist.CLIENT)
    public int getGoalType() {
        int indices = this.containerData.get(5);
        if (indices == -1) indices = 0;
        return (indices < 0 ? -indices : Math.min(5 - indices, 3));
    }

    @OnlyIn(Dist.CLIENT)
    public double getBaseScaleFactor() {
        int valueInInt = this.containerData.get(6);
        long valueInLong = valueInInt & 0xffffffffL;
        return valueInLong / (double) ((1L << 32) - 1);
    }

    @OnlyIn(Dist.CLIENT)
    public Position getSoulTactic() {
        return Position.values()[this.containerData.get(8)];
    }
}
