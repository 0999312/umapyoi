package net.tracen.umapyoi.item;

import java.util.List;
import java.util.Optional;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class FadedUmaSoulItem extends Item {

    public FadedUmaSoulItem() {
        super(Umapyoi.defaultItemProperties().stacksTo(1));
    }

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents,
			TooltipFlag tooltipFlag) {
		tooltipComponents.add(Component.translatable("tooltip.umapyoi.umadata.name",
            UmaSoulUtils.getTranslatedUmaName(this.getUmaName(stack))).withStyle(ChatFormatting.GRAY));
	}

    public ResourceLocation getUmaName(ItemStack stack) {
        if (!stack.has(DataComponentsTypeRegistry.DATA_LOCATION))
            return UmaData.DEFAULT_UMA_ID;
        return Optional.ofNullable(stack.get(DataComponentsTypeRegistry.DATA_LOCATION).name())
                .orElse(UmaData.DEFAULT_UMA_ID);
    }

}
