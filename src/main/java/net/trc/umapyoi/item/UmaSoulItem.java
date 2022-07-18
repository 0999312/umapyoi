package net.trc.umapyoi.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.trc.umapyoi.Umapyoi;
import net.trc.umapyoi.api.UmapyoiAPI;
import net.trc.umapyoi.curios.UmaSoulCapProvider;
import net.trc.umapyoi.registry.UmaData;
import net.trc.umapyoi.registry.UmaDataRegistry;

public class UmaSoulItem extends Item {

    public UmaSoulItem() {
        super(Umapyoi.defaultItemProperties().stacksTo(1));
    }
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        return new UmaSoulCapProvider(stack, nbt);
    }
    
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableComponent("umapyoi.umadata.name", I18n.get("umapyoi.uma."+UmapyoiAPI.getUmaData(stack).name())));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            for (UmaData data : UmaDataRegistry.UMA_DATA_REGISTRY.get().getValues()) {
                items.add(UmapyoiAPI.setUmaData(getDefaultInstance(), data));
            }
        }
    }
}
