package net.tracen.umapyoi.item.factor;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.registry.factors.FactorData;
import net.tracen.umapyoi.registry.factors.UmaFactorStack;
import net.tracen.umapyoi.utils.UmaFactorUtils;

import java.util.List;

public class FactorReport extends Item {
    public FactorReport() {
        super(Umapyoi.defaultItemProperties());
    }

    public static List<UmaFactorStack> getFactorStacks(ItemStack stack) {
        List<FactorData> datas = stack.get(DataComponentsTypeRegistry.FACTOR_DATA);
        return UmaFactorUtils.deserializeData(datas);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext context, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, context, pTooltipComponents, pIsAdvanced);
        getFactorStacks(pStack).stream().forEach(factor -> {
            pTooltipComponents.add(factor.getDescription().copy().withStyle(switch (factor.getFactor().getFactorType()) {
                case STATUS -> ChatFormatting.BLUE;
                case UNIQUE -> ChatFormatting.GREEN;
                case EXTRASTATUS -> ChatFormatting.RED;
                default -> ChatFormatting.GRAY;
            }));
            if(pIsAdvanced.isAdvanced() || UmapyoiConfig.DISPLAY_DETAIL.get()) {
            	pTooltipComponents.add(factor.getDescriptionDetail().copy().withStyle(ChatFormatting.DARK_GRAY));
            }
        });
    }
}
