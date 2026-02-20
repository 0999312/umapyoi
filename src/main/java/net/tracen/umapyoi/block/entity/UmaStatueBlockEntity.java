package net.tracen.umapyoi.block.entity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import cn.mcmod_mmf.mmlib.block.entity.SyncedBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.item.UmaCostumeItem;
import net.tracen.umapyoi.item.UmaSuitItem;

public class UmaStatueBlockEntity extends SyncedBlockEntity {
    private final ItemStackHandler inventory;
    private final LazyOptional<IItemHandler> inputHandler;

    public UmaStatueBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.UMA_STATUES.get(), pos, state);
        
        inventory = createHandler();
        inputHandler = LazyOptional.of(() -> inventory);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        inventory.deserializeNBT(compound.getCompound("Inventory"));
        if (inventory.getSlots() < 2) {
            ItemStack item = inventory.getStackInSlot(0);
            inventory.setSize(2);
            inventory.setStackInSlot(0, item);
        }
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("Inventory", inventory.serializeNBT());
    }

    public boolean addItem(ItemStack itemStack) {
        if (isEmpty() && itemStack.is(ItemRegistry.UMA_SOUL.get())) {
            inventory.setStackInSlot(0, itemStack.split(1));
            inventoryChanged();
            return true;
        }
        if (isCostumeEmpty() && !isEmpty() && (itemStack.getItem() instanceof UmaSuitItem || itemStack.getItem() instanceof UmaCostumeItem)) {
            inventory.setStackInSlot(1, itemStack.split(1));
            inventoryChanged();
            return true;
        }
        return false;
    }

    public ItemStack removeItem() {
        if (!isCostumeEmpty()) {
            ItemStack item = getCostume().split(1);
            inventoryChanged();
            return item;
        }
        if (!isEmpty()) {
            ItemStack item = getStoredItem().split(1);
            inventoryChanged();
            return item;
        }
        return ItemStack.EMPTY;
    }

    public IItemHandler getInventory() {
        return inventory;
    }

    public ItemStack getStoredItem() {
        return inventory.getStackInSlot(0);
    }

    public boolean isEmpty() {
        return inventory.getStackInSlot(0).isEmpty();
    }

    public ItemStack getCostume() {
        return inventory.getStackInSlot(1);
    }

    public boolean isCostumeEmpty() {
        return inventory.getStackInSlot(1).isEmpty();
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap.equals(ForgeCapabilities.ITEM_HANDLER)) {
            return inputHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        inputHandler.invalidate();
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(2) {
            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }

            @Override
            protected void onContentsChanged(int slot) {
                inventoryChanged();
            }
        };
    }
    
    @Override
    public AABB getRenderBoundingBox() {
    	return AABB.ofSize(getBlockPos().getCenter().add(0,1,0),1,3,1);
    }
}
