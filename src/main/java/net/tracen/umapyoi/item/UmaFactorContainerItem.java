package net.tracen.umapyoi.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.registry.factors.UmaFactorStack;
import net.tracen.umapyoi.utils.UmaFactorUtils;

public class UmaFactorContainerItem extends Item {

    public UmaFactorContainerItem() {
        super(Umapyoi.defaultItemProperties().stacksTo(1));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        CompoundTag tag = stack.getOrCreateTag();
        StringBuffer buffer = new StringBuffer("umadata.").append(tag.getString("name").toString().replace(':', '.'));
        tooltip.add(Component.translatable("tooltip.umapyoi.umadata.name", I18n.get(buffer.toString()))
                .withStyle(ChatFormatting.GRAY));
        if (Screen.hasShiftDown() || !UmapyoiConfig.TOOLTIP_SWITCH.get()) {
            tooltip.add(Component.translatable("tooltip.umapyoi.factors.factors_details")
                    .withStyle(ChatFormatting.AQUA));
            List<UmaFactorStack> stackList = UmaFactorUtils.deserializeNBT(tag);

            stackList.forEach(factor -> {
                switch (factor.getFactor().getFactorType()) {
                case STATUS -> tooltip.add(factor.getDescription().copy().withStyle(ChatFormatting.BLUE));
                case UNIQUE -> tooltip.add(factor.getDescription().copy().withStyle(ChatFormatting.GREEN));
                case EXTRASTATUS -> tooltip.add(factor.getDescription().copy().withStyle(ChatFormatting.RED));
                default -> tooltip.add(factor.getDescription().copy().withStyle(ChatFormatting.GRAY));
                }
            });
        } else {
            tooltip.add(Component.translatable("tooltip.umapyoi.press_shift_for_details")
                    .withStyle(ChatFormatting.AQUA));
        }
    }
}
