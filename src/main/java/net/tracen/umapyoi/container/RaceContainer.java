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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Objects;

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
        this(i, playerInv, getTileEntity(playerInv, data), new SimpleContainerData(2));
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

        this.addSlot(new UmaSoulSlot(this.inventory, 0, 8, 19));
        this.addSlot(new SlotItemHandler(this.inventory, 1, 25, 19){
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.is(ItemRegistry.UMA_RACE_TICKET.get());
            }
        });

        for (int j = 0; j <= 3; j++) {
            this.addSlot(new SlotItemHandler(this.inventory, j + 2, (j & 1) * 17 + 138, 19 + (j / 2) * 18));
        }

        int startPlayerInvY = 120;
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInv, 9 + (row * 9) + column, 8 + (column * 18),
                        startPlayerInvY + (row * 18)));
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
    public float getProgression() {
        return this.containerData.get(0) / (float) this.containerData.get(1);
    }
}
