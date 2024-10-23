package net.tracen.umapyoi.api;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tracen.umapyoi.item.UmaSoulItem;
import net.tracen.umapyoi.item.UmaSuitItem;
import net.tracen.umapyoi.registry.training.card.SupportCard;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.utils.ClientUtils;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class UmapyoiAPI {

    @OnlyIn(Dist.CLIENT)
    public static ItemStack getRenderingUmaSoul(LivingEntity entity) {
        if (CuriosApi.getCuriosInventory(entity).isPresent()) {
            var itemHandler = CuriosApi.getCuriosInventory(entity).orElse(null);
            if (itemHandler.getStacksHandler("uma_soul").isPresent()) {
                var stacksHandler = itemHandler.getStacksHandler("uma_soul").orElse(null);
                if(!stacksHandler.getRenders().get(0))
                    return ItemStack.EMPTY;
                ItemStack cosmeticStack = getFirstUmaSoul(stacksHandler.getCosmeticStacks());
                if(!cosmeticStack.isEmpty()) 
                    return cosmeticStack;
                return getFirstUmaSoul(stacksHandler.getStacks());
            }
        }
        return ItemStack.EMPTY;
    }
    
    public static ItemStack getUmaSoul(LivingEntity entity) {
        if (CuriosApi.getCuriosInventory(entity).isPresent()) {
            var itemHandler = CuriosApi.getCuriosInventory(entity).orElse(null);
            if (itemHandler.getStacksHandler("uma_soul").isPresent()) {
                var stacksHandler = itemHandler.getStacksHandler("uma_soul").orElse(null);
                
                return getFirstUmaSoul(stacksHandler.getStacks());
            }
        }
        return ItemStack.EMPTY;
    }
    
    private static ItemStack getFirstUmaSoul(IDynamicStackHandler stackHandler) {
        if (stackHandler.getSlots() <= 0)
            return ItemStack.EMPTY;
        ItemStack stackInSlot = stackHandler.getStackInSlot(0);
        if (stackInSlot.getItem() instanceof UmaSoulItem) {
            return stackInSlot;
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack getUmaSuit(LivingEntity entity) {
        if (CuriosApi.getCuriosInventory(entity).isPresent()) {
            var itemHandler = CuriosApi.getCuriosInventory(entity).orElse(null);
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

    public static boolean isUmaSuitRendering(LivingEntity player) {
        if (CuriosApi.getCuriosInventory(player).isPresent()) {
            var itemHandler = CuriosApi.getCuriosInventory(player).orElse(null);
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

    public static Registry<UmaData> getUmaDataRegistry(Level level) {
        if (level.isClientSide())
            return ClientUtils.getClientUmaDataRegistry();
        return level.registryAccess().registryOrThrow(UmaData.REGISTRY_KEY);
    }

    public static Registry<SupportCard> getSupportCardRegistry(Level level) {
        if (level.isClientSide())
            return ClientUtils.getClientSupportCardRegistry();
        return level.registryAccess().registryOrThrow(SupportCard.REGISTRY_KEY);
    }

    public static HolderLookup.RegistryLookup<UmaData> getUmaDataRegistry(HolderLookup.Provider provider) {
        return provider.lookupOrThrow(UmaData.REGISTRY_KEY);
    }

    public static HolderLookup.RegistryLookup<SupportCard> getSupportCardRegistry(HolderLookup.Provider provider) {
        return provider.lookupOrThrow(SupportCard.REGISTRY_KEY);
    }
}
