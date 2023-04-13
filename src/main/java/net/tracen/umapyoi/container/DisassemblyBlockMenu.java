package net.tracen.umapyoi.container;

import javax.annotation.Nullable;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.item.SupportCardItem;
import net.tracen.umapyoi.item.UmaSoulItem;
import net.tracen.umapyoi.registry.training.SupportContainer;
import net.tracen.umapyoi.utils.GachaRanking;

public class DisassemblyBlockMenu extends AbstractContainerMenu {

    protected final ResultContainer resultSlots = new ResultContainer();
    protected final Container inputSlots = new SimpleContainer(1) {
        @Override
        public int getContainerSize() {
            return 1;
        };

        /**
         * For tile entities, ensures the chunk containing the tile entity is saved to
         * disk later - the game won't think it hasn't changed and skip it.
         */
        @Override
        public void setChanged() {
            super.setChanged();
            DisassemblyBlockMenu.this.slotsChanged(this);
        }
    };
    protected final ContainerLevelAccess access;
    protected final Player player;

    protected boolean mayPickup(Player pPlayer, boolean pHasStack) {
        return this.hasResult();
    }

    protected boolean hasResult() {
        ItemStack inputSoul = this.inputSlots.getItem(0);
        return inputSoul.getItem() instanceof UmaSoulItem || inputSoul.getItem() instanceof SupportCardItem;
    }

    protected void onTake(Player player, ItemStack resultStack) {
        resultStack.onCraftedBy(player.level, player, resultStack.getCount());
        this.resultSlots.awardUsedRecipes(player);
        this.shrinkStackInSlot(0);
        this.access.execute((level, pos) -> {
            player.playSound(SoundEvents.AMETHYST_BLOCK_BREAK, 1F, 1F);
        });
        
    }

    private void shrinkStackInSlot(int pIndex) {
        ItemStack itemstack = this.inputSlots.getItem(pIndex);
        itemstack.shrink(1);
        this.inputSlots.setItem(pIndex, itemstack);
    }
    
    protected boolean isValidBlock(BlockState pState) {
        return pState.is(BlockRegistry.DISASSEMBLY_BLOCK.get());
    }

    public DisassemblyBlockMenu(int pContainerId, Inventory pPlayerInventory) {
        this(pContainerId, pPlayerInventory, ContainerLevelAccess.NULL);
    }

    public DisassemblyBlockMenu(int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
        this(ContainerRegistry.RETIRE_REGISTER.get(), pContainerId, pPlayerInventory, pAccess);
    }

    public DisassemblyBlockMenu(@Nullable MenuType<?> pType, int pContainerId, Inventory pPlayerInventory,
            ContainerLevelAccess pAccess) {
        super(pType, pContainerId);
        this.access = pAccess;
        this.player = pPlayerInventory.player;
        this.addSlot(new Slot(this.inputSlots, 0, 44, 59));
        this.addSlot(new Slot(this.resultSlots, 1, 116, 59) {
            /**
             * Check if the stack is allowed to be placed in this slot, used for armor slots
             * as well as furnace fuel.
             */
            public boolean mayPlace(ItemStack p_39818_) {
                return false;
            }

            /**
             * Return whether this slot's stack can be taken from this slot.
             */
            public boolean mayPickup(Player p_39813_) {
                return DisassemblyBlockMenu.this.mayPickup(p_39813_, this.hasItem());
            }

            public void onTake(Player p_150604_, ItemStack p_150605_) {
                DisassemblyBlockMenu.this.onTake(p_150604_, p_150605_);
                p_150604_.onEnchantmentPerformed(p_150605_, 0);
            }
        });

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(pPlayerInventory, j + i * 9 + 9, 8 + j * 18, 94 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(pPlayerInventory, k, 8 + k * 18, 152));
        }

    }

    /**
     * called when the Anvil Input Slot changes, calculates the new result and puts
     * it in the output slot
     */
    public void createResult() {
        if (!this.hasResult()) {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
        } else {
            this.resultSlots.setItem(0, this.getResultItem());
        }
    }

    private ItemStack getResultItem() {
        
        ItemStack inputSoul = this.inputSlots.getItem(0).copy();
        boolean isSupport = inputSoul.getItem() instanceof SupportContainer;
        if (!(inputSoul.getItem() instanceof UmaSoulItem || isSupport))
            return ItemStack.EMPTY;
        if(!inputSoul.getOrCreateTag().contains("ranking")) 
            return ItemStack.EMPTY;
        
        ItemStack result = ItemStack.EMPTY;
        GachaRanking ranking = GachaRanking.valueOf(inputSoul.getOrCreateTag().getString("ranking").toUpperCase());
        switch(ranking) {
            case R : {
                result = isSupport
                        ? new ItemStack(ItemRegistry.HORSESHOE_SILVER.get())
                        : new ItemStack(ItemRegistry.CRYSTAL_SILVER.get()); 
                break;
                }
            case SR : {
                result = isSupport
                        ? new ItemStack(ItemRegistry.HORSESHOE_GOLD.get())
                        : new ItemStack(ItemRegistry.CRYSTAL_GOLD.get()); 
                break;
                }
            case SSR : {
                result = isSupport
                        ? new ItemStack(ItemRegistry.HORSESHOE_RAINBOW.get())
                        : new ItemStack(ItemRegistry.CRYSTAL_RAINBOW.get()); 
                break;
                }
            default:
                break;
        }
        return result;
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void slotsChanged(Container pInventory) {
        super.slotsChanged(pInventory);
        if (pInventory == this.inputSlots) {
            this.createResult();
        }

    }

    /**
     * Called when the container is closed.
     */
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.access.execute((level, pos) -> {
            this.clearContainer(pPlayer, this.inputSlots);
        });
    }

    /**
     * Determines whether supplied player can use this container
     */
    public boolean stillValid(Player pPlayer) {
        return this.access.evaluate((level, pos) -> {
            return !this.isValidBlock(level.getBlockState(pos)) ? false
                    : pPlayer.distanceToSqr((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D,
                            (double) pos.getZ() + 0.5D) <= 64.0D;
        }, true);
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this
     * moves the stack between the player inventory and the other inventory(s).
     */
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex == 1) {
                if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (pIndex != 0) {
                if (pIndex >= 2 && pIndex < 38) {
                    int i = 0;
                    if (!this.moveItemStackTo(itemstack1, i, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemstack1, 2, 38, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, itemstack1);
        }

        return itemstack;
    }

}
