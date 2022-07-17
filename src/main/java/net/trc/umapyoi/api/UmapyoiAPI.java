package net.trc.umapyoi.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.trc.umapyoi.capability.CapabilityRegistry;
import net.trc.umapyoi.item.UmaSoulItem;
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

    public static CompoundTag getUmaData(ItemStack stack) {
        if (stack.getItem() instanceof UmaSoulItem) {
            if(stack.getCapability(CapabilityRegistry.UMACAP).isPresent()) {
                var umadata = stack.getCapability(CapabilityRegistry.UMACAP).orElse(null);
                return umadata.serializeNBT();
            }
        }
        return new CompoundTag();
    }

//    public static int getSpeed(ItemStack stack) {
//        return getUmaData(stack).getInt("speed");
//    }
//
//    public static void setSpeed(ItemStack stack, int speed) {
//        if (stack.getItem() instanceof UmaSoulItem) {
//            stack.getCapability(CapabilityRegistry.UMACAP).ifPresent(cap -> {
//                    cap.setSpeed(speed);
//            });
//        }
//    }
//
//    public static int getStamina(ItemStack stack) {
//        return getUmaData(stack).getInt("stamina");
//    }
//
//    public static void setStamina(ItemStack stack, int stamina) {
//        if (stack.getItem() instanceof UmaSoulItem) {
//            stack.getCapability(CapabilityRegistry.UMACAP).ifPresent(cap -> {
//                    cap.setStamina(stamina);
//            });
//        }
//    }
//
//    public static int getStrength(ItemStack stack) {
//        return getUmaData(stack).getInt("strength");
//    }
//
//    public static void setStrength(ItemStack stack, int strength) {
//        if (stack.getItem() instanceof UmaSoulItem) {
//            stack.getCapability(CapabilityRegistry.UMACAP).ifPresent(cap -> {
//                    cap.setStrength(strength);
//            });
//        }
//    }
//
//    public static int getMentality(ItemStack stack) {
//        return getUmaData(stack).getInt("mentality");
//    }
//
//    public static void setMentality(ItemStack stack, int mentality) {
//        if (stack.getItem() instanceof UmaSoulItem) {
//            stack.getCapability(CapabilityRegistry.UMACAP).ifPresent(cap -> {
//                    cap.setMentality(mentality);
//            });
//        }
//    }
//
//    public static int getWisdom(ItemStack stack) {
//        return getUmaData(stack).getInt("wisdom");
//    }
//
//    public static void setWisdom(ItemStack stack, int wisdom) {
//        if (stack.getItem() instanceof UmaSoulItem) {
//            stack.getCapability(CapabilityRegistry.UMACAP).ifPresent(cap -> {
//                    cap.setWisdom(wisdom);
//            });
//        }
//    }

}
