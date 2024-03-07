package net.tracen.umapyoi.item;

import java.util.List;
import java.util.Optional;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.data.tag.UmapyoiItemTags;
import net.tracen.umapyoi.registry.SupportCardRegistry;
import net.tracen.umapyoi.registry.UmaDataRegistry;
import net.tracen.umapyoi.utils.TrainingSupportUtils;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class UmaTicketItem extends Item {

    public UmaTicketItem() {
        super(Umapyoi.defaultItemProperties());
    }

    @Override
    public void appendHoverText(ItemStack pStack, Level pLevel, List<Component> pTooltipComponents,
            TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        if (!pStack.getOrCreateTag().getString("name").isBlank()) {
            if(pStack.is(UmapyoiItemTags.CARD_TICKET))
                pTooltipComponents.add(new TranslatableComponent("tooltip.umapyoi.support_card.name",
                        TrainingSupportUtils.getTranslatedSupportCardName(this.getSupportCardID(pStack))).withStyle(ChatFormatting.GRAY));
            else{pTooltipComponents.add(new TranslatableComponent("tooltip.umapyoi.umadata.name",
                UmaSoulUtils.getTranslatedUmaName(this.getUmaName(pStack))).withStyle(ChatFormatting.GRAY));
            }
        }
    }

    public ResourceLocation getUmaName(ItemStack pStack) {
        return Optional.ofNullable(ResourceLocation.tryParse(pStack.getOrCreateTag().getString("name")))
                .orElse(UmaDataRegistry.COMMON_UMA.getId());
    }
    
    @Override
    public boolean isFoil(ItemStack pStack) {
        return !pStack.getOrCreateTag().getString("name").isBlank();
    }

    private ResourceLocation getSupportCardID(ItemStack stack) {
        if (stack.getOrCreateTag().contains("name"))
            return ResourceLocation.tryParse(stack.getOrCreateTag().getString("name"));
        return SupportCardRegistry.BLANK_CARD.getId();
    }
    
}
