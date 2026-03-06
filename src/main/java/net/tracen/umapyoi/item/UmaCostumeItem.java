package net.tracen.umapyoi.item;

import net.minecraft.Util;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.registry.cosmetics.CosmeticData;

import java.util.Comparator;
import java.util.stream.Stream;

public class UmaCostumeItem extends Item {
	private static final Comparator<Reference<CosmeticData>> COMPARATOR = new DataComparator();
    public UmaCostumeItem() {
        super(Umapyoi.defaultItemProperties().stacksTo(1));
    }
    
    public static Stream<Reference<CosmeticData>> sortedCosmeticDataList(HolderLookup.Provider provider) {
        return UmapyoiAPI.getCosmeticDataRegistry(provider).listElements().sorted(UmaCostumeItem.COMPARATOR);
    }
    
    @Override
    public String getCreatorModId(ItemStack itemStack) {
    	return getCostumeID(itemStack).getNamespace();
    }

    @Override
    public String getDescriptionId(ItemStack pStack) {
        return Util.makeDescriptionId("item", UmaCostumeItem.getCostumeID(pStack)) + ".name";
    }

    public static ResourceLocation getCostumeID(ItemStack stack) {
        return stack.getOrDefault(DataComponentsTypeRegistry.COSMETIC_DATA, CosmeticData.COMMON_COSTUME);
	}

	public static ItemStack getCostume(ResourceLocation loc) {
		ItemStack defaultInstance = ItemRegistry.UMA_COSTUME.get().getDefaultInstance();
		defaultInstance.set(DataComponentsTypeRegistry.COSMETIC_DATA, loc);
		return defaultInstance;
	}
    
    private static class DataComparator implements Comparator<Reference<CosmeticData>> {
        @Override
        public int compare(Reference<CosmeticData> left,Reference<CosmeticData> right) {
            String leftName = left.key().location().toString();
            String rightName = right.key().location().toString();
            return leftName.compareToIgnoreCase(rightName);
        }
    }
}
