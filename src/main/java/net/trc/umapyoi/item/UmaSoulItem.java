package net.trc.umapyoi.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
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
import net.trc.umapyoi.capability.CapabilityRegistry;
import net.trc.umapyoi.curios.UmaSoulCapProvider;
import net.trc.umapyoi.registry.UmaData;

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
                I18n.get("tooltip.umapyoi.uma." + UmapyoiAPI.getUmaData(stack).name())));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            if (Minecraft.getInstance().getConnection() != null)
                Minecraft.getInstance().getConnection().registryAccess().registryOrThrow(UmaData.REGISTRY_KEY).stream()
                        .forEach(data -> items.add(UmapyoiAPI.setUmaData(getDefaultInstance(), data)));
        }
    }
    
    /**
     *  用来获取待同步的`Tag`，默认实现为获取`ItemStack`本身的 tag。
     *  注意！如果这里覆写方法没有调用 super，可能会导致`ItemStack`本身的同步失效。 
     */
    @Nullable
    @Override
    public CompoundTag getShareTag(ItemStack stack) {
        // 由于默认实现是 getTag，这里就不判空了，直接使用 stack.getOrCreateTag()
        var result = stack.getOrCreateTag();
        // 获取 capability 的值存入 result，这里拿 ENERGY 举例，因为我的上下文确定这里用的一定是 EnergyStorage 所以直接强转了，可以使用 instaceof INBTSerializable serializable 来判断是否可以直接 serializeNBT
        stack.getCapability(CapabilityRegistry.UMACAP).ifPresent(cap -> {
            result.put("soul", cap.serializeNBT());
        });
        return result;
    }

    /** 用来在`Tag`同步后读取的方法。 */
    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        super.readShareTag(stack, nbt);
        // 他不太可能是 null，但判空保平安，省得爆了再去找原因
        if (nbt == null) {
            return;
        }
        // 同样，获取 capability 的值。
        stack.getCapability(CapabilityRegistry.UMACAP).ifPresent(cap -> {
            cap.deserializeNBT(nbt.getCompound("soul"));
        });
    }
}
