package net.tracen.umapyoi.curios;

import javax.annotation.Nonnull;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.tracen.umapyoi.capability.CapabilityRegistry;
import net.tracen.umapyoi.capability.IUmaCapability;
import net.tracen.umapyoi.capability.UmaCapability;
import top.theillusivec4.curios.api.CuriosCapability;

public class UmaSoulCapProvider implements ICapabilitySerializable<CompoundTag> {
    private final ItemStack stack;
    private final UmaSoulCuriosWrapper curiosInstance;
    private final IUmaCapability umaInstance;

    public UmaSoulCapProvider(ItemStack stack, CompoundTag nbt) {
        this.stack = stack;
        this.curiosInstance = new UmaSoulCuriosWrapper(stack);
        this.umaInstance = new UmaCapability(stack);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == CuriosCapability.ITEM)
            return LazyOptional.of(this::getCuriosInstance).cast();
        if (cap == CapabilityRegistry.UMACAP)
            return LazyOptional.of(this::getCapability).cast();
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.umaInstance.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.umaInstance.deserializeNBT(nbt);
    }

    @Nonnull
    private IUmaCapability getCapability() {
        return this.umaInstance;
    }

    public ItemStack getItemStack() {
        return stack;
    }

    public UmaSoulCuriosWrapper getCuriosInstance() {
        return curiosInstance;
    }

}
