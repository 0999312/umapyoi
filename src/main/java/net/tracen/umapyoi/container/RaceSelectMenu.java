package net.tracen.umapyoi.container;

import com.google.common.collect.Lists;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.data.tag.UmapyoiItemTags;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.item.UmaRaceTicketItem;
import net.tracen.umapyoi.registry.races.Race;
import net.tracen.umapyoi.registry.races.RaceRegistry;
import net.tracen.umapyoi.utils.RaceRanking;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class RaceSelectMenu extends AbstractContainerMenu implements IItemNameMutableMenu{

    private final ContainerLevelAccess access;
    public final Level level;
    private ResourceLocation itemName;
    private List<ResourceLocation> recipes = Lists.newArrayList();

    private ItemStack inputTicket = ItemStack.EMPTY;
    private ItemStack inputMaterial = ItemStack.EMPTY;

    final Slot inputTicketSlot;
    final Slot inputMaterialSlot;
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
            RaceSelectMenu.this.slotsChanged(this);
            RaceSelectMenu.this.slotUpdateListener.run();
        }
    };
    /** The inventory that stores the output of the crafting recipe. */
    final ResultContainer resultContainer = new ResultContainer();

    public RaceSelectMenu(int pContainerId, Inventory pPlayerInventory) {
        this(pContainerId, pPlayerInventory, ContainerLevelAccess.NULL);
    }

    public RaceSelectMenu(int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
        this(ContainerRegistry.RACE_SELECT_MENU.get(), pContainerId, pPlayerInventory, pAccess);
    }

    public RaceSelectMenu(@Nullable MenuType<?> pType, int pContainerId, Inventory pPlayerInventory,
                          ContainerLevelAccess pAccess) {
        super(pType, pContainerId);
        this.access = pAccess;
        this.level = pPlayerInventory.player.level();
        this.itemName = null;
        this.inputTicketSlot = this.addSlot(new Slot(this.container, 0, 19, 35) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return pStack.is(ItemRegistry.UMA_RACE_TICKET.get()) &&
                        UmaRaceTicketItem.getRaceID(pStack).equals(RaceRegistry.MAKE_DEBUT.location());
            }

        });

        this.inputMaterialSlot = this.addSlot(new Slot(this.container, 1, 19, 65) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return isValidMaterial(pStack);
            }
        });

        this.resultSlot = this.addSlot(new Slot(this.resultContainer, 2, 142, 51) {

            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            public void onTake(Player player, ItemStack stack) {
                stack.onCraftedBy(player.level(), player, stack.getCount());
                
                var ticket = RaceSelectMenu.this.inputTicketSlot.remove(1);
                var lapis = RaceSelectMenu.this.inputMaterialSlot.remove(1);
                if (!ticket.isEmpty() && !lapis.isEmpty()) {
                    RaceSelectMenu.this.setupResultSlot();
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
        return stillValid(access, playerIn, BlockRegistry.RACE_SELECT_BLOCK.get());
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
                    int i = this.isValidMaterial(itemstack) ? 1 : 0;
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

    protected boolean isValidMaterial(ItemStack pStack) {
        for (RaceRanking r: RaceRanking.values()) {
            if (pStack.is(UmapyoiItemTags.getRaceMaterialTag(r))) return true;
        }
        return pStack.is(UmapyoiItemTags.RACE_CHAMPIONS_MATERIAL);
    }

    public List<ResourceLocation> getRecipes() {
        return this.recipes;
    }

    public boolean hasInputItem() {
        return this.inputTicketSlot.hasItem() && this.inputMaterialSlot.hasItem() && !this.recipes.isEmpty();
    }

    public void registerUpdateListener(Runnable pListener) {
        this.slotUpdateListener = pListener;
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void slotsChanged(Container pInventory) {
        ItemStack ticket = this.inputTicketSlot.getItem();
        ItemStack material = this.inputMaterialSlot.getItem();
        boolean setupFlag = false;
        if (!ticket.is(this.inputTicket.getItem()) || !Objects.equals(UmaRaceTicketItem.getRace(ticket, level), UmaRaceTicketItem.getRace(this.inputTicket))) {
            this.inputTicket = ticket.copy();
            setupFlag = true;
        }
        if (!material.is(this.inputMaterial.getItem())) {
            this.inputMaterial = material.copy();
            setupFlag = true;
        }
        if (setupFlag) {
            this.setupRecipeList(pInventory, this.inputTicket, this.inputMaterial);
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
            this.recipes = UmapyoiAPI.getRaceRegistry(level).entrySet().stream()
                    .sorted(UmaRaceTicketItem.RaceEntryComparator.INSTANCE)
                    .map(Map.Entry::getKey)
                    .map(ResourceKey::location)
                    .collect(Collectors.toCollection(Lists::newArrayList));
        }
        this.broadcastChanges();
    }

    private void setupResultSlot() {
        if (!this.recipes.isEmpty() && this.getItemName() != null) {
            ItemStack result = ItemRegistry.UMA_RACE_TICKET.get().getDefaultInstance();
            result.getOrCreateTag().putString("race", this.getItemName().toString());
            this.resultSlot.set(result);
        } else {
            RaceSelectMenu.this.itemName = null;
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
        private Level level;
        public SelectComparator(Level level) { this.level = level; }
        @Override
        public int compare(ResourceLocation left, ResourceLocation right) {
            Race leftRace = UmapyoiAPI.getRaceRegistry(level).get(left);
            Race rightRace = UmapyoiAPI.getRaceRegistry(level).get(right);
            return UmaRaceTicketItem.RacePairComparator.INSTANCE.compare(
                    new AbstractMap.SimpleEntry<>(left, leftRace),
                    new AbstractMap.SimpleEntry<>(right, rightRace)
            );
        }
    }
}
