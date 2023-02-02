package net.tracen.umapyoi.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
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
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.capability.CapabilityRegistry;
import net.tracen.umapyoi.curios.UmaSoulCapProvider;
import net.tracen.umapyoi.utils.ClientUtils;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class UmaSoulItem extends Item {

    public UmaSoulItem() {
        super(Umapyoi.defaultItemProperties().stacksTo(1));
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        if(nbt!=null) Umapyoi.getLogger().info("Init Cap from:{}", nbt.toString());
        else Umapyoi.getLogger().info("Init Cap from null NBT");
        return new UmaSoulCapProvider(stack, nbt);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableComponent("tooltip.umapyoi.umadata.name",
                UmaSoulUtils.getTranslatedUmaName(stack))
                .withStyle(ChatFormatting.GRAY));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            if (Minecraft.getInstance().getConnection() != null)
                ClientUtils.getClientUmaDataRegistry().forEach(data -> items.add(UmaSoulUtils.initUmaSoul(getDefaultInstance(), data)));
        }
    }

    @Nullable
    @Override
    public CompoundTag getShareTag(ItemStack stack) {
        var result = super.getShareTag(stack) == null ? stack.getOrCreateTag() : super.getShareTag(stack);
        stack.getCapability(CapabilityRegistry.UMACAP).ifPresent(cap -> {
            result.put("cap", cap.serializeNBT());
        });
        return result;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        super.readShareTag(stack, nbt);
        if (nbt != null) {
            stack.getCapability(CapabilityRegistry.UMACAP).ifPresent(cap -> {
                cap.deserializeNBT(nbt.getCompound("cap"));
            });
        }
    }

}
