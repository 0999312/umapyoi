package net.tracen.umapyoi.item;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.UmaDataRegistry;
import net.tracen.umapyoi.registry.UmaFactorRegistry;
import net.tracen.umapyoi.registry.factors.UmaFactor;
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
        StringBuffer buffer = new StringBuffer("umadata.")
                .append(tag.getString("name").toString().replace(':', '.'));
        tooltip.add(new TranslatableComponent("tooltip.umapyoi.umadata.name", I18n.get(buffer.toString()))
                .withStyle(ChatFormatting.GRAY));
        List<UmaFactorStack> stackList = UmaFactorUtils.deserializeNBT(tag);
        tooltip.add(new TranslatableComponent("tooltip.umapyoi.umafactor.data",
                stackList.get(0).getFactor().getFullDescription(stackList.get(0).getLevel()), stackList.size())
                        .withStyle(ChatFormatting.GRAY));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            for (UmaFactor factor : UmaFactorRegistry.REGISTRY.get().getValues()) {
                if (factor == UmaFactorRegistry.SKILL_FACTOR.get())
                    continue;
                List<UmaFactorStack> stackList = Lists.newArrayList(new UmaFactorStack(factor, 1));
                ItemStack result = getDefaultInstance();
                result.getOrCreateTag().putString("name", UmaDataRegistry.COMMON_UMA.getId().toString());
                result.getOrCreateTag().put("factors", UmaFactorUtils.serializeNBT(stackList));
                items.add(result);
            }
        }
    }
}
