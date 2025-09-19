package net.tracen.umapyoi.item;

import java.util.List;
import java.util.Optional;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.utils.GachaRanking;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class FadedUmaSoulItem extends Item {

    public FadedUmaSoulItem() {
        super(Umapyoi.defaultItemProperties().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack pStack, Level pLevel, List<Component> pTooltipComponents,
            TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable("tooltip.umapyoi.umadata.name",
                UmaSoulUtils.getTranslatedUmaName(this.getUmaName(pStack))).withStyle(ChatFormatting.GRAY));
    }

    public ResourceLocation getUmaName(ItemStack pStack) {
        if (pStack.getOrCreateTag().getString("name").isBlank())
            return UmaData.DEFAULT_UMA_ID;
        return Optional.ofNullable(ResourceLocation.tryParse(pStack.getOrCreateTag().getString("name")))
                .orElse(UmaData.DEFAULT_UMA_ID);
    }
    
    @Override
    public Component getName(ItemStack pStack) {
        GachaRanking ranking = GachaRanking.getGachaRanking(pStack);
        if(ranking == GachaRanking.EASTER_EGG) return super.getName(pStack).copy().withStyle(ChatFormatting.GREEN);
        return super.getName(pStack);
    }
    
    @Override
    public Rarity getRarity(ItemStack pStack) {
        GachaRanking ranking = GachaRanking.getGachaRanking(pStack);
        return ranking == GachaRanking.SSR || ranking == GachaRanking.EASTER_EGG ? Rarity.EPIC : ranking == GachaRanking.SR ? Rarity.UNCOMMON : Rarity.COMMON;
    }
    
    @Override
    public String getCreatorModId(ItemStack itemStack) {
    	return getUmaName(itemStack).getNamespace();
    }

    public static ItemStack genUmaSoul(String name, UmaData data) {
        ItemStack result = ItemRegistry.BLANK_UMA_SOUL.get().getDefaultInstance();
        result.getOrCreateTag().putString("name", name);
        result.getOrCreateTag().putString("identifier", data.getIdentifier().toString());
        result.getOrCreateTag().putString("ranking", data.getGachaRanking().toString().toLowerCase());
		return result;
	}
}
