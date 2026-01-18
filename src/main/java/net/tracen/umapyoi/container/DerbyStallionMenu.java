package net.tracen.umapyoi.container;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.events.DerbyStallionEvent;
import net.tracen.umapyoi.registry.factors.FactorType;
import net.tracen.umapyoi.registry.factors.UmaFactorStack;
import net.tracen.umapyoi.utils.UmaFactorUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static net.tracen.umapyoi.block.BlockRegistry.DERBY_STALLION_TABLE;
import static net.tracen.umapyoi.item.ItemRegistry.FACTOR_REPORT;
import static net.tracen.umapyoi.item.ItemRegistry.UMA_FACTOR_ITEM;

public class DerbyStallionMenu extends AbstractContainerMenu {
    protected final ContainerLevelAccess access;
    protected final Player player;

    private final DataSlot factorSeed = DataSlot.standalone();
    public final DataSlot isTaking = DataSlot.standalone();
    public final DataSlot hasResult = DataSlot.standalone();
    public final List<? extends Slot> outputSlots;

    protected final ExtendedResultContainer resultSlots = new ExtendedResultContainer(9);

    protected final Container inputSlots = new SimpleContainer(1) {
        @Override
        public int getContainerSize() {
            return 1;
        };

        @Override
        public void setChanged() {
            super.setChanged();
            DerbyStallionMenu.this.slotsChanged(this);
        }
    };

    public boolean isTaking() {
        return this.isTaking.get() != 0;
    }

    public DerbyStallionMenu(@Nullable MenuType<?> pType, int pContainerId, Inventory pPlayerInventory,
                             ContainerLevelAccess pAccess) {
        super(pType, pContainerId);
        this.access = pAccess;
        this.player = pPlayerInventory.player;
        this.addSlot(new Slot(this.inputSlots, 0, 80, 18) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return super.mayPlace(pStack) && pStack.is(UMA_FACTOR_ITEM.get());
            }
        });
        outputSlots = IntStream.range(0, 9).mapToObj(i -> new Slot(this.resultSlots, i, 8 + i * 18, 62) {
            public boolean mayPlace(@Nonnull ItemStack pStack) {
                return false;
            }

            public boolean mayPickup(@Nonnull Player pPlayer) {
                return !this.getItem().isEmpty() &&
                        (isTaking() || DerbyStallionMenu.this.hasResult.get() != 0);
            }

            public void onTake(@Nonnull Player pPlayer, @Nonnull ItemStack itemStack) {
                DerbyStallionMenu.this.factorSeed.set(pPlayer.getEnchantmentSeed());
                DerbyStallionMenu.this.onTake(pPlayer, itemStack);
            }
        }).peek(this::addSlot).toList();

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(pPlayerInventory, j + i * 9 + 9, 8 + j * 18, 104 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(pPlayerInventory, k, 8 + k * 18, 162));
        }

        this.addDataSlot(this.factorSeed).set(this.player.getEnchantmentSeed());
        this.addDataSlot(this.isTaking).set(0);
        this.addDataSlot(this.hasResult).set(0);
    }

    public DerbyStallionMenu(int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
        this(ContainerRegistry.DERBY_STALLION_MENU.get(), pContainerId, pPlayerInventory, pAccess);
    }

    public DerbyStallionMenu(int i, Inventory inventory) {
        this(i, inventory, ContainerLevelAccess.NULL);
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (1 <= pIndex && pIndex <= 9) {
                if (!this.moveItemStackTo(itemstack1, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (pIndex != 0) {
                if (pIndex >= 10 && pIndex < 46) {
                    int i = 0;
                    if (!this.moveItemStackTo(itemstack1, i, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemstack1, 10, 46, false)) {
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

    @Override
    public boolean stillValid(@Nonnull Player pPlayer) {
        return this.access.evaluate((level, pos) ->
                level.getBlockState(pos).is(DERBY_STALLION_TABLE.get()) &&
                        pPlayer.distanceToSqr(
                                (double) pos.getX() + 0.5D,
                                (double) pos.getY() + 0.5D,
                                (double) pos.getZ() + 0.5D
                        ) <= 64.0D,
                true);
    }

    protected boolean canProceedToResult() {
        if (isTaking()) {
            for (int i = 0; i < 9; i++) {
                if (this.resultSlots.getItem(i).isEmpty()) continue;
                return false;
            }
        }
        return this.inputSlots.getItem(0).is(UMA_FACTOR_ITEM.get());
    }

    @Override
    public void slotsChanged(@Nonnull Container pInventory) {
        super.slotsChanged(pInventory);
        if (pInventory == this.inputSlots) {
            if (!isTaking()) this.createResult(); // input slot changed
        }
    }

    public static boolean AllowContinueDefaultLogic(ItemStack factorStackCandidate) {
        if (!factorStackCandidate.is(UMA_FACTOR_ITEM.get())) return false;
        boolean isHasValidFactor = false;
        for (Tag tag: factorStackCandidate.getOrCreateTag().getList("factors", Tag.TAG_COMPOUND)) {
            if (tag instanceof CompoundTag compound) {
                Optional<UmaFactorStack> opt = UmaFactorStack.CODEC.parse(NbtOps.INSTANCE, compound).result();
                if (opt.isPresent() && opt.get().getLevel() > 0) {
                    isHasValidFactor = true;
                    break;
                }
            }
        }
        return isHasValidFactor;
    }

    public void createResult() {
        if (isTaking()) return;
        DerbyStallionEvent evt = new DerbyStallionEvent(this.inputSlots.getItem(0), this.factorSeed.get());
        if (!this.canProceedToResult() || MinecraftForge.EVENT_BUS.post(evt) || evt.getListOfReturn() == null) {
            this.resultSlots.clearContent();
            hasResult.set(0);
        } else {
            hasResult.set(1);
            int i = 0;
            List<ItemStack> returnStacks = evt.getListOfReturn().stream().map(s -> {
                ItemStack returnStack = new ItemStack(FACTOR_REPORT.get(), 1);
                CompoundTag tag = returnStack.getOrCreateTag();
                tag.put("factors", UmaFactorUtils.serializeNBT(List.of(s)));
                return returnStack;
            }).toList();
            for (ItemStack stack: returnStacks) {
                if (i >= 9) break;
                this.resultSlots.setItem(i++, stack);
            }
            for (; i < 9; i++) {
                this.resultSlots.setItem(i, ItemStack.EMPTY);
            }
        }
        outputSlots.forEach(Slot::setChanged);
    }

    public static List<UmaFactorStack> DefaultAlgResultStacks(ItemStack factorStack, int seed) {
        Random random = new Random();
        random.setSeed(seed);
        List<UmaFactorStack> listOfFactor = UmaFactorUtils.deserializeNBT(factorStack.getOrCreateTag()).stream().filter(s -> s.getFactor().getFactorType() != FactorType.UNIQUE).collect(Collectors.toCollection(ArrayList::new));
        List<UmaFactorStack> outputStack;
        if (listOfFactor.size() <= 9) {
            outputStack = listOfFactor;
        } else {
            // Use Fisher-Yates shuffling to randomize output
            // Warning for Mixin'ers: listOfFactor must guarantee to be mutable.
            for (int i = 0; i < 9; i++) {
                int j = i + random.nextInt(listOfFactor.size() - i);
                Collections.swap(listOfFactor, i, j);
            }
            outputStack = listOfFactor.subList(0, 9);
        }
        return outputStack;
    }

    @Override
    public void removed(@Nonnull Player pPlayer) {
        super.removed(pPlayer);
        this.access.execute((level, pos) -> {
            this.clearContainer(pPlayer, this.inputSlots);
            if (isTaking()) this.clearContainer(pPlayer, this.resultSlots);
        });
    }

    protected void onTake(Player player, ItemStack resultStack) {
        if (isTaking()) {
            boolean isEmpty = this.resultSlots.isEmpty();
            isTaking.set(isEmpty ? 0 : 1);
            if (isEmpty) {
                this.createResult();
            }
            return;
        }
        ItemStack factorItem = this.inputSlots.getItem(0);
        if (factorItem.is(UMA_FACTOR_ITEM.get())) {
            isTaking.set(1);
            resultStack.onCraftedBy(player.level(), player, resultStack.getCount());
            this.resultSlots.awardUsedRecipes(player, List.of(factorItem));
            factorItem.shrink(1);
            this.inputSlots.setItem(0, factorItem);
            if (player.level().isClientSide())
                player.playSound(SoundEvents.PLAYER_LEVELUP, 1F, 1F);
        }
    }
}
