package net.tracen.umapyoi.curios;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class UmaSuitCapProvider implements ICapabilitySerializable<CompoundTag> {

    private final ItemStack stack;
    private final ICurio capInstance;

    public UmaSuitCapProvider(ItemStack stack, CompoundTag nbt) {
        this.stack = stack;
        this.capInstance = new UmaSuitCuriosWrapper(stack);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == CuriosCapability.ITEM)
            return LazyOptional.of(this::getCuriosInstance).cast();
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
    }

    public ItemStack getItemStack() {
        return stack;
    }

    public ICurio getCuriosInstance() {
        return capInstance;
    }

}
