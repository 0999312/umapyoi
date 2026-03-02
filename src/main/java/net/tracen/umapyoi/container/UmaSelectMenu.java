package net.tracen.umapyoi.container;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.data.tag.UmapyoiItemTags;

public class UmaSelectMenu extends AbstractContainerMenu {

    private final ContainerLevelAccess access;
    private final Level level;
    private ResourceLocation itemName;
    private List<ResourceLocation> recipes = Lists.newArrayList();

    private ItemStack inputTicket = ItemStack.EMPTY;
    private ItemStack inputLapis = ItemStack.EMPTY;

    final Slot inputTicketSlot;
    final Slot inputLapisSlot;
    final Slot resultSlot;
    Runnable slotUpdateListener = () -> {
    };
    public final Container container = new SimpleContainer(2) {
        /**
         * For tile entities, ensures the chunk containing the tile entity is saved to
         * disk later - the game won't think it hasn't changed and skip it.
         */
        public void setChanged() {
            super.setChanged();
            UmaSelectMenu.this.slotsChanged(this);
            UmaSelectMenu.this.slotUpdateListener.run();
        }
    };
    /** The inventory that stores the output of the crafting recipe. */
    final ResultContainer resultContainer = new ResultContainer();

    public UmaSelectMenu(int pContainerId, Inventory pPlayerInventory) {
        this(pContainerId, pPlayerInventory, ContainerLevelAccess.NULL);
    }

    public UmaSelectMenu(int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
        this(ContainerRegistry.UMA_SELECT_MENU.get(), pContainerId, pPlayerInventory, pAccess);
    }

    public UmaSelectMenu(@Nullable MenuType<?> pType, int pContainerId, Inventory pPlayerInventory,
            ContainerLevelAccess pAccess) {
        super(pType, pContainerId);
        this.access = pAccess;
        this.level = pPlayerInventory.player.level();
        this.itemName = null;
        this.inputTicketSlot = this.addSlot(new Slot(this.container, 0, 19, 35) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return (pStack.is(UmapyoiItemTags.UMA_TICKET) || pStack.is(UmapyoiItemTags.CARD_TICKET))
                        && !pStack.is(UmapyoiItemTags.COMMON_GACHA_ITEM);
            }

        });

        this.inputLapisSlot = this.addSlot(new Slot(this.container, 1, 19, 65) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return pStack.is(Tags.Items.ENCHANTING_FUELS);
            }
        });

        this.resultSlot = this.addSlot(new Slot(this.resultContainer, 2, 142, 51) {

            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            public void onTake(Player player, ItemStack stack) {
                stack.onCraftedBy(player.level(), player, stack.getCount());
                
                var ticket = UmaSelectMenu.this.inputTicketSlot.remove(1);
                var lapis = UmaSelectMenu.this.inputLapisSlot.remove(1);
                if (!ticket.isEmpty() && !lapis.isEmpty()) {
                    UmaSelectMenu.this.setupResultSlot();
                }

                super.onTake(player, stack);
            }
        });

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(pPlayerInventory, j + i * 9 + 9, 8 + j * 18, 104 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(pPlayerInventory, k, 8 + k * 18, 162));
        }

    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(access, playerIn, BlockRegistry.UMA_SELECT_BLOCK.get());
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex == 2) {
                if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (pIndex != 0 && pIndex != 1) {
                if (pIndex >= 3 && pIndex < 39) {
                    int i = this.shouldQuickMoveToAdditionalSlot(itemstack) ? 1 : 0;
                    if (!this.moveItemStackTo(itemstack1, i, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemstack1, 3, 39, false)) {
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

    protected boolean shouldQuickMoveToAdditionalSlot(ItemStack pStack) {
        return pStack.is(Tags.Items.ENCHANTING_FUELS);
    }

    public List<ResourceLocation> getRecipes() {
        return this.recipes;
    }

    public boolean hasInputItem() {
        return this.inputTicketSlot.hasItem() && this.inputLapisSlot.hasItem() && !this.recipes.isEmpty();
    }

    public void registerUpdateListener(Runnable pListener) {
        this.slotUpdateListener = pListener;
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void slotsChanged(Container pInventory) {
        ItemStack ticket = this.inputTicketSlot.getItem();
        ItemStack lapis = this.inputLapisSlot.getItem();
        boolean setupFlag = false;
        if (!ticket.is(this.inputTicket.getItem())) {
            this.inputTicket = ticket.copy();
            setupFlag = true;
        }
        if (!lapis.is(this.inputLapis.getItem())) {
            this.inputLapis = lapis.copy();
            setupFlag = true;
        }
        if (setupFlag) {
            this.setupRecipeList(pInventory, this.inputTicket, this.inputLapis);
        }
    }

    public ResourceLocation getItemName() {
        return itemName;
    }

    public void setItemName(ResourceLocation itemName) {
        if(this.itemName == null || !this.itemName.equals(itemName)) {
            this.itemName = itemName;
            this.setupResultSlot();
        }
    }
    
    private void setupRecipeList(Container pContainer, ItemStack ticket, ItemStack lapis) {
        this.recipes.clear();
        this.resultSlot.set(ItemStack.EMPTY);
        if (!ticket.isEmpty() && !lapis.isEmpty()) {
            this.recipes = ticket.is(UmapyoiItemTags.CARD_TICKET)
                    ? UmapyoiAPI.getSupportCardRegistry(level).keySet().stream().sorted(SelectComparator.INSTANCE)
                            .collect(Collectors.toCollection(Lists::newArrayList))
                    : UmapyoiAPI.getUmaDataRegistry(level).keySet().stream().sorted(SelectComparator.INSTANCE)
                            .collect(Collectors.toCollection(Lists::newArrayList));
        }
        this.broadcastChanges();
    }

    private void setupResultSlot() {
        if (!this.recipes.isEmpty() && this.getItemName()!=null) {
            ItemStack result = this.inputTicket.copy();
            result.setCount(1);
            result.getOrCreateTag().putString("name", this.getItemName().toString());
            this.resultSlot.set(result);
        } else {
            UmaSelectMenu.this.itemName = null;
            this.resultSlot.set(ItemStack.EMPTY);
        }

        this.broadcastChanges();
    }


    public ContainerLevelAccess getAccess() {
        return access;
    }

    /**
     * Called when the container is closed.
     */
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.access.execute((level, pos) -> {
            this.clearContainer(pPlayer, this.container);
        });
    }
    
    public static class SelectComparator implements Comparator<ResourceLocation> {
        public static final SelectComparator INSTANCE = new SelectComparator();
        private SelectComparator() {}
        @Override
        public int compare(ResourceLocation left, ResourceLocation right) {
            String leftName = left.toString();
            String rightName = right.toString();
            return leftName.compareToIgnoreCase(rightName);
        }
    }
}
