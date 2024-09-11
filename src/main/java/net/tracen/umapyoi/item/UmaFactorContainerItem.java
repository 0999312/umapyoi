package net.tracen.umapyoi.item;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
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

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class UmaFactorContainerItem extends Item {

    public UmaFactorContainerItem() {
        super(Umapyoi.defaultItemProperties().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        List<FactorData> datas = stack.get(DataComponentsTypeRegistry.FACTOR_DATA);
        String buffer = "umadata." + stack.get(DataComponentsTypeRegistry.DATA_LOCATION).name().toLanguageKey();
        tooltipComponents.add(Component.translatable("tooltip.umapyoi.umadata.name", I18n.get(buffer))
                .withStyle(ChatFormatting.GRAY));
        if (Screen.hasShiftDown() || !UmapyoiConfig.TOOLTIP_SWITCH.get()) {
            tooltipComponents.add(Component.translatable("tooltip.umapyoi.factors.factors_details")
                    .withStyle(ChatFormatting.AQUA));
            List<UmaFactorStack> stackList = UmaFactorUtils.deserializeData(datas);

            stackList.forEach(factor -> {
                switch (factor.getFactor().getFactorType()) {
                case STATUS -> tooltipComponents.add(factor.getDescription().copy().withStyle(ChatFormatting.BLUE));
                case UNIQUE -> tooltipComponents.add(factor.getDescription().copy().withStyle(ChatFormatting.GREEN));
                case EXTRASTATUS -> tooltipComponents.add(factor.getDescription().copy().withStyle(ChatFormatting.RED));
                default -> tooltipComponents.add(factor.getDescription().copy().withStyle(ChatFormatting.GRAY));
                }
            });
        } else {
            tooltipComponents.add(Component.translatable("tooltip.umapyoi.press_shift_for_details")
                    .withStyle(ChatFormatting.AQUA));
        }
    }
}
