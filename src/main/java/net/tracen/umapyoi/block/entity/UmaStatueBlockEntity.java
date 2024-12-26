package net.tracen.umapyoi.block.entity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import cn.mcmod_mmf.mmlib.block.entity.SyncedBlockEntity;
import cn.mcmod_mmf.mmlib.client.model.SimpleBedrockModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.tracen.umapyoi.client.model.UmaPlayerModel;
import net.tracen.umapyoi.item.ItemRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class UmaStatueBlockEntity extends SyncedBlockEntity {
    private final ItemStackHandler inventory;
    private final LazyOptional<IItemHandler> inputHandler;

    private final HashMap<String, Vec3> rotations = new HashMap<>();

    private Vec3 relativeOffset = new Vec3(0, 0, 0);

    @OnlyIn(Dist.CLIENT)
    public UmaPlayerModel<Player> model = new UmaPlayerModel<>();
    @OnlyIn(Dist.CLIENT)
    public SimpleBedrockModel suitModel = new SimpleBedrockModel();

    public UmaStatueBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.UMA_STATUES.get(), pos, state);
        inventory = createHandler();
        inputHandler = LazyOptional.of(() -> inventory);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        inventory.deserializeNBT(compound.getCompound("Inventory"));
        if(inventory.getSlots() <= 1){
            ArrayList<ItemStack> stacks = new ArrayList<>();
            for (int i = 0; i < inventory.getSlots(); i++) {
                stacks.add(inventory.getStackInSlot(i).copy());
            }
            inventory.setSize(2);
            for (int i = 0; i < stacks.size(); i++) {
                inventory.setStackInSlot(i, stacks.get(i));
            }
        }
        rotations.clear();
        var action = compound.getCompound("action");
        var raw_rotations = action.getCompound("rotations");
        for (String bone : raw_rotations.getAllKeys()) {
            var raw_rotation_entry = raw_rotations.getCompound(bone);
            rotations.put(
                    bone,
                    new Vec3(
                            raw_rotation_entry.getDouble("x"),
                            raw_rotation_entry.getDouble("y"),
                            raw_rotation_entry.getDouble("z")
                    ));
        }

        var raw_relative_offset = action.getCompound("relativeOffset");
        relativeOffset = new Vec3(
                raw_relative_offset.getDouble("x"),
                raw_relative_offset.getDouble("y"),
                raw_relative_offset.getDouble("z")
        );
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("Inventory", inventory.serializeNBT());
        var action = new CompoundTag();
        var raw_rotations = new CompoundTag();
        for (var entry : rotations.entrySet()) {
            var raw_rotation_entry = new CompoundTag();
            raw_rotation_entry.putDouble("x", entry.getValue().x);
            raw_rotation_entry.putDouble("y", entry.getValue().y);
            raw_rotation_entry.putDouble("z", entry.getValue().z);
            raw_rotations.put(entry.getKey(), raw_rotation_entry);
        }
        action.put("rotations", raw_rotations);
        var raw_relative_offset = new CompoundTag();
        raw_relative_offset.putDouble("x", relativeOffset.x);
        raw_relative_offset.putDouble("y", relativeOffset.y);
        raw_relative_offset.putDouble("z", relativeOffset.z);
        action.put("relativeOffset", raw_relative_offset);
        compound.put("action", action);
    }

    public boolean addItem(ItemStack itemStack) {
        if (inventory.getStackInSlot(0).isEmpty() && itemStack.is(ItemRegistry.UMA_SOUL.get())) {
            inventory.setStackInSlot(0, itemStack.split(1));
            inventoryChanged();
            return true;
        }
        if(inventory.getStackInSlot(1).isEmpty() && (
                itemStack.is(ItemRegistry.TRAINNING_SUIT.get()) ||
                itemStack.is(ItemRegistry.SWIMSUIT.get()) ||
                itemStack.is(ItemRegistry.WINTER_UNIFORM.get()) ||
                itemStack.is(ItemRegistry.SUMMER_UNIFORM.get())
        ) && !inventory.getStackInSlot(0).isEmpty()){
            inventory.setStackInSlot(1, itemStack.split(1));
            inventoryChanged();
            return true;
        }
        return false;
    }

    public ItemStack removeItem() {
        if (!isSoulEmpty()) {
            ItemStack item = getStoredItem().split(1);
            inventory.setStackInSlot(1, ItemStack.EMPTY);
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

    public boolean isSoulEmpty() {
        return inventory.getStackInSlot(0).isEmpty();
    }

    public boolean isSuitEmpty(){
        return inventory.getStackInSlot(1).isEmpty();
    }

    public boolean isPlaceable(ItemStack stack){
        return (
                stack.is(ItemRegistry.UMA_SOUL.get()) && isSoulEmpty()
                ) || (
                (stack.is(ItemRegistry.TRAINNING_SUIT.get()) || stack.is(ItemRegistry.SWIMSUIT.get()) || stack.is(ItemRegistry.WINTER_UNIFORM.get()) || stack.is(ItemRegistry.SUMMER_UNIFORM.get())) && isSuitEmpty()
        );
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

    public HashMap<String, Vec3> getRotations() {
        return rotations;
    }

    public Vec3 getRelativeOffset() {
        return relativeOffset;
    }
}
