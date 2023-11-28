package net.tracen.umapyoi.curios;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosCapability;

public class UmaSoulCapProvider implements ICapabilityProvider {
    private final ItemStack stack;
    private final UmaSoulCuriosWrapper curiosInstance;

    public UmaSoulCapProvider(ItemStack stack) {
        this.stack = stack;
        this.curiosInstance = new UmaSoulCuriosWrapper(stack);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == CuriosCapability.ITEM)
            return LazyOptional.of(this::getCuriosInstance).cast();
        return LazyOptional.empty();
    }

    public ItemStack getItemStack() {
        return stack;
    }

    public UmaSoulCuriosWrapper getCuriosInstance() {
        return curiosInstance;
    }

}
