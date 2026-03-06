package net.tracen.umapyoi.item;

import java.util.List;
import java.util.Optional;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.item.data.DataLocation;
import net.tracen.umapyoi.item.data.GachaRankingData;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.utils.GachaRanking;
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

    @Override
    public Component getName(ItemStack pStack) {
        GachaRanking ranking = GachaRanking.getGachaRanking(pStack);
        if(ranking == GachaRanking.EASTER_EGG) return super.getName(pStack).copy().withStyle(ChatFormatting.GREEN);
        return super.getName(pStack);
    }

    @Override
    public String getCreatorModId(ItemStack itemStack) {
        return getUmaName(itemStack).getNamespace();
    }

    public static ItemStack genUmaSoul(ResourceLocation name, UmaData data) {
        ItemStack result = ItemRegistry.BLANK_UMA_SOUL.get().getDefaultInstance();
        result.set(DataComponentsTypeRegistry.DATA_LOCATION, new DataLocation(name));
        GachaRanking ranking = data.ranking();
        result.set(DataComponentsTypeRegistry.GACHA_RANKING, new GachaRankingData(ranking));
        result.set(DataComponents.RARITY, ranking == GachaRanking.SSR || ranking == GachaRanking.EASTER_EGG ? Rarity.EPIC : ranking == GachaRanking.SR ? Rarity.UNCOMMON : Rarity.COMMON);
        return result;
    }
}
