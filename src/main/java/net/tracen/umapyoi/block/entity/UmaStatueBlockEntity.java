package net.tracen.umapyoi.block.entity;

import cn.mcmod_mmf.mmlib.block.entity.SyncedBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.item.ItemRegistry;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = Umapyoi.MODID)
public class UmaStatueBlockEntity extends SyncedBlockEntity {
    private final ItemStackHandler inventory;

    public UmaStatueBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.UMA_STATUES.get(), pos, state);
        
        inventory = createHandler();
    }
    
	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(
				Capabilities.ItemHandler.BLOCK,
				BlockEntityRegistry.UMA_STATUES.get(),
				(be, context) -> {
					return be.getInventory();
				}
		);
	}

    @Override
	public void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
		super.loadAdditional(compound, registries);
        inventory.deserializeNBT(registries, compound.getCompound("Inventory"));
    }

    @Override
	public void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
		super.saveAdditional(compound, registries);
        compound.put("Inventory", inventory.serializeNBT(registries));
    }

    public boolean addItem(ItemStack itemStack) {
        if (isEmpty() && itemStack.is(ItemRegistry.UMA_SOUL.get())) {
            inventory.setStackInSlot(0, itemStack.split(1));
            inventoryChanged();
            return true;
        }
        return false;
    }

    public ItemStack removeItem() {
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

    @Override
    public void setRemoved() {
        super.setRemoved();
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler() {
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
    
}
