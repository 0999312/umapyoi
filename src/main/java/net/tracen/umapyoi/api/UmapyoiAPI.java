package net.tracen.umapyoi.api;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.capability.CapabilityRegistry;
import net.tracen.umapyoi.capability.UmaCapability;
import net.tracen.umapyoi.item.UmaSoulItem;
import net.tracen.umapyoi.item.UmaSuitItem;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.registry.umadata.UmaStatus;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class UmapyoiAPI {

    public static ItemStack getUmaSoul(Player player) {
        if (CuriosApi.getCuriosHelper().getCuriosHandler(player).isPresent()) {
            var itemHandler = CuriosApi.getCuriosHelper().getCuriosHandler(player).orElse(null);
            if (itemHandler.getStacksHandler("uma_soul").isPresent()) {
                var stacksHandler = itemHandler.getStacksHandler("uma_soul").orElse(null);
                IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                if (stackHandler.getSlots() <= 0)
                    return ItemStack.EMPTY;
                if (stackHandler.getStackInSlot(0).getItem() instanceof UmaSoulItem) {
                    return stackHandler.getStackInSlot(0);
                }
            }
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack getUmaSuit(Player player) {
        if (CuriosApi.getCuriosHelper().getCuriosHandler(player).isPresent()) {
            var itemHandler = CuriosApi.getCuriosHelper().getCuriosHandler(player).orElse(null);
            if (itemHandler.getStacksHandler("uma_suit").isPresent()) {
                var stacksHandler = itemHandler.getStacksHandler("uma_suit").orElse(null);
                IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                if (stackHandler.getSlots() <= 0)
                    return ItemStack.EMPTY;
                if (stackHandler.getStackInSlot(0).getItem() instanceof UmaSuitItem) {
                    return stackHandler.getStackInSlot(0);
                }
            }
        }
        return ItemStack.EMPTY;
    }

    public static boolean isUmaSoulRendering(Player player) {
        if (CuriosApi.getCuriosHelper().getCuriosHandler(player).isPresent()) {
            var itemHandler = CuriosApi.getCuriosHelper().getCuriosHandler(player).orElse(null);
            if (itemHandler.getStacksHandler("uma_soul").isPresent()) {
                var stacksHandler = itemHandler.getStacksHandler("uma_soul").orElse(null);
                IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                if (stackHandler.getSlots() <= 0)
                    return false;
                if (stackHandler.getStackInSlot(0).getItem() instanceof UmaSoulItem) {
                    return stacksHandler.getRenders().get(0);
                }
            }
        }
        return false;
    }

    public static boolean isUmaSuitRendering(Player player) {
        if (CuriosApi.getCuriosHelper().getCuriosHandler(player).isPresent()) {
            var itemHandler = CuriosApi.getCuriosHelper().getCuriosHandler(player).orElse(null);
            if (itemHandler.getStacksHandler("uma_suit").isPresent()) {
                var stacksHandler = itemHandler.getStacksHandler("uma_suit").orElse(null);
                IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                if (stackHandler.getSlots() <= 0)
                    return false;
                if (stackHandler.getStackInSlot(0).getItem() instanceof UmaSuitItem) {
                    return stacksHandler.getRenders().get(0);
                }
            }
        }
        return false;
    }

    public static UmaStatus getUmaStatus(ItemStack stack) {
        return stack.getCapability(CapabilityRegistry.UMACAP).orElse(new UmaCapability(stack)).getUmaStatus();
    }

    public static ItemStack initUmaSoul(ItemStack stack, UmaData data) {
        if (stack.getItem() instanceof UmaSoulItem) {
            stack.getOrCreateTag().put("status", data.status().serializeNBT());
        }
        return stack;
    }

}
