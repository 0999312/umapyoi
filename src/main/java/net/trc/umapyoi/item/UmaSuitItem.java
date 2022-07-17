package net.trc.umapyoi.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.trc.umapyoi.Umapyoi;
import net.trc.umapyoi.curios.UmaSuitCapProvider;

public class UmaSuitItem extends Item {

    public UmaSuitItem() {
        super(Umapyoi.defaultItemProperties().stacksTo(1));
    }
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        return new UmaSuitCapProvider(stack, nbt);
    }

}
