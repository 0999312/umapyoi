package net.trc.umapyoi.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.trc.umapyoi.Umapyoi;
import net.trc.umapyoi.capability.CapabilityRegistry;
import net.trc.umapyoi.item.UmaSoulItem;
import net.trc.umapyoi.registry.UmaData;
import net.trc.umapyoi.registry.UmaDataRegistry;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class UmapyoiAPI {

    public static ItemStack getUmaSoul(Player player) {
        if (CuriosApi.getCuriosHelper().getCuriosHandler(player).isPresent()) {
            var itemHandler = CuriosApi.getCuriosHelper().getCuriosHandler(player).orElse(null);
            if (itemHandler.getStacksHandler("uma_soul").isPresent()) {
                var stacksHandler = itemHandler.getStacksHandler("uma_soul").orElse(null);
                IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                if (stackHandler.getStackInSlot(0).getItem() instanceof UmaSoulItem) {
                    return stackHandler.getStackInSlot(0);
                }
            }
        }
        return ItemStack.EMPTY;
    }

    public static CompoundTag getUmaSoulData(ItemStack stack) {
        if (stack.getItem() instanceof UmaSoulItem) {
            if (stack.getCapability(CapabilityRegistry.UMACAP).isPresent()) {
                var umadata = stack.getCapability(CapabilityRegistry.UMACAP).orElse(null);
                return umadata.serializeNBT();
            }
        }
        return new CompoundTag();
    }

    public static UmaData getUmaData(ItemStack stack) {
        if (stack.getItem() instanceof UmaSoulItem) {
            return UmaData.CODEC.parse(NbtOps.INSTANCE, stack.getOrCreateTag().get("data"))
                    .resultOrPartial(msg -> Umapyoi.getLogger().error("Failed to parse {}: {}", stack.toString(), msg))
                    .orElseGet(UmaDataRegistry.COMMON_UMA);
        }
        return UmaDataRegistry.COMMON_UMA.get();
    }

    public static ItemStack setUmaData(ItemStack stack, UmaData data) {
        if (stack.getItem() instanceof UmaSoulItem) {
            UmaData.CODEC.encodeStart(NbtOps.INSTANCE, data).resultOrPartial(
                    msg -> Umapyoi.getLogger().error("Failed to encode {}: {}", data.getRegistryName().toString(), msg))
                    .ifPresent(tag -> stack.getOrCreateTag().put("data", tag));
        }
        return stack;
    }

}
