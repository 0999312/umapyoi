package net.tracen.umapyoi.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
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
import net.tracen.umapyoi.api.UmaSkillUtils;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.capability.CapabilityRegistry;
import net.tracen.umapyoi.curios.UmaSoulCapProvider;
import net.tracen.umapyoi.registry.umadata.UmaData;

public class UmaSoulItem extends Item {

    public UmaSoulItem() {
        super(Umapyoi.defaultItemProperties().stacksTo(1));
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        return new UmaSoulCapProvider(stack, nbt);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableComponent("tooltip.umapyoi.umadata.name",
                I18n.get("tooltip.umapyoi.uma." + UmapyoiAPI.getUmaStatus(stack).name().getPath())));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            if (Minecraft.getInstance().getConnection() != null)
                Minecraft.getInstance().getConnection().registryAccess().registryOrThrow(UmaData.REGISTRY_KEY).stream()
                        .forEach(data -> items.add(UmapyoiAPI.initUmaSoul(getDefaultInstance(), data)));
        }
    }

    @Nullable
    @Override
    public CompoundTag getShareTag(ItemStack stack) {
        var result = super.getShareTag(stack) == null ? stack.getOrCreateTag() : super.getShareTag(stack);
        stack.getCapability(CapabilityRegistry.UMACAP).ifPresent(cap -> {
            result.put("status", cap.getUmaStatus().serializeNBT());
            ListTag tagSkills = UmaSkillUtils.serializeNBT(cap.getSkills());
            result.put("skills", tagSkills);
            result.putString("selectedSkill", cap.getSelectedSkill().toString());
            result.putInt("skillCooldown", cap.getCooldown());
        });
        return result;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        super.readShareTag(stack, nbt);
        if (nbt != null) {
            stack.getCapability(CapabilityRegistry.UMACAP).ifPresent(cap -> {
                cap.deserializeNBT(nbt);
            });
        }
    }

}
