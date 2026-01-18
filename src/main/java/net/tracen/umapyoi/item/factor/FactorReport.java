package net.tracen.umapyoi.item.factor;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.registry.factors.UmaFactorStack;
import net.tracen.umapyoi.utils.UmaFactorUtils;

import javax.annotation.Nullable;
import java.util.List;

public class FactorReport extends Item {
    public FactorReport() {
        super(Umapyoi.defaultItemProperties());
    }

    public static List<UmaFactorStack> getFactorStacks(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return UmaFactorUtils.deserializeNBT(tag);
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        getFactorStacks(pStack).stream().forEach(factor -> {
            pTooltipComponents.add(factor.getDescription().copy().withStyle(switch (factor.getFactor().getFactorType()) {
                case STATUS -> ChatFormatting.BLUE;
                case UNIQUE -> ChatFormatting.GREEN;
                case EXTRASTATUS -> ChatFormatting.RED;
                default -> ChatFormatting.GRAY;
            }));
            if(UmapyoiConfig.DISPLAY_DETAIL.get()) {
                pTooltipComponents.add(factor.getDescriptionDetail().copy().withStyle(ChatFormatting.DARK_GRAY));
            }
        });
    }
}
