package net.tracen.umapyoi.item.factor;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.factors.FactorType;

public class UmaFactorAmplifierItem extends Item {
	// TODO: 实现overrideStackedOnOther对指定FactorType的因子进行随机等级增长。
	// 是否指定某个因子根据后续设计考虑。
	
	private final FactorType type;
	public UmaFactorAmplifierItem(FactorType type) {
		super(Umapyoi.defaultItemProperties());
		this.type = type;
	}
	
	@Override
	public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
//		ItemStack stack = pSlot.getItem();
//		CompoundTag tag = stack.getOrCreateTag();
//		List<UmaFactorStack> stackList = UmaFactorUtils.deserializeNBT(tag);
//		stackList.stream().filter(factor->factor.getFactor().getFactorType() == this.getFactorType() && factor.getLevel()).findAny();
		return super.overrideStackedOnOther(pStack, pSlot, pAction, pPlayer);
	}
	
    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
//        CompoundTag tag = stack.getOrCreateTag();
//        StringBuffer buffer = new StringBuffer("umadata.").append(tag.getString("name").toString().replace(':', '.'));
//        tooltip.add(Component.translatable("tooltip.umapyoi.umadata.name", I18n.get(buffer.toString()))
//                .withStyle(ChatFormatting.GRAY));
//        if (Screen.hasShiftDown() || !UmapyoiConfig.TOOLTIP_SWITCH.get()) {
//            tooltip.add(Component.translatable("tooltip.umapyoi.factors.factors_details")
//                    .withStyle(ChatFormatting.AQUA));
//            List<UmaFactorStack> stackList = UmaFactorUtils.deserializeNBT(tag);
//
//            stackList.forEach(factor -> {
//                switch (factor.getFactor().getFactorType()) {
//                case STATUS -> tooltip.add(factor.getDescription().copy().withStyle(ChatFormatting.BLUE));
//                case UNIQUE -> tooltip.add(factor.getDescription().copy().withStyle(ChatFormatting.GREEN));
//                case EXTRASTATUS -> tooltip.add(factor.getDescription().copy().withStyle(ChatFormatting.RED));
//                default -> tooltip.add(factor.getDescription().copy().withStyle(ChatFormatting.GRAY));
//                }
//            });
//        } else {
//            tooltip.add(Component.translatable("tooltip.umapyoi.press_shift_for_details")
//                    .withStyle(ChatFormatting.AQUA));
//        }
    }

	public FactorType getFactorType() {
		return type;
	}

}
