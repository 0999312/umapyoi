package net.tracen.umapyoi.item;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import com.google.common.base.Suppliers;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.data.tag.UmapyoiItemTags;
import net.tracen.umapyoi.registry.training.SupportContainer;
import net.tracen.umapyoi.registry.training.SupportStack;
import net.tracen.umapyoi.registry.training.SupportType;
import net.tracen.umapyoi.registry.training.card.SupportCard;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.utils.ClientUtils;
import net.tracen.umapyoi.utils.GachaRanking;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class SupportCardItem extends Item implements SupportContainer {
    private static final Comparator<Reference<SupportCard>> COMPARATOR = new CardDataComparator();
    
    public SupportCardItem() {
        super(Umapyoi.defaultItemProperties().stacksTo(1));
    }

    public static Stream<Reference<SupportCard>> sortedCardDataList(HolderLookup.Provider provider) {
        return UmapyoiAPI.getSupportCardRegistry(provider).listElements().sorted(SupportCardItem.COMPARATOR);
    }
    
    @Override
    public ItemStack getDefaultInstance() {
        ItemStack result = super.getDefaultInstance();
        result.getOrCreateTag().putString("support_card", "umapyoi:blank_card");
        result.getOrCreateTag().putString("ranking", GachaRanking.R.name().toLowerCase());
        result.getOrCreateTag().putInt("maxDamage", 0);
        return result;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return this.getMaxDamage(stack) > 0;
    }
    
    @Override
    public int getMaxDamage(ItemStack stack) {
        return stack.getOrCreateTag().getInt("maxDamage");
    }

    @Override
    public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
        return pRepair.is(UmapyoiItemTags.HORSESHOE) || super.isValidRepairItem(pToRepair, pRepair);
    }
    
    @Override
    public Rarity getRarity(ItemStack pStack) {
        GachaRanking ranking = GachaRanking.getGachaRanking(pStack);
        return ranking == GachaRanking.SSR ? Rarity.EPIC : ranking == GachaRanking.SR ? Rarity.UNCOMMON : Rarity.COMMON;
    }

    @Override
    public String getDescriptionId(ItemStack pStack) {
        return Util.makeDescriptionId("support_card", this.getSupportCardID(pStack)) + ".name";
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, level, tooltip, flagIn);
        ResourceLocation cardID = this.getSupportCardID(stack);
        if (isEmptyCard(level, cardID))
            return ;
        if (Screen.hasShiftDown() || !UmapyoiConfig.TOOLTIP_SWITCH.get()) {
            tooltip.add(Component.translatable("tooltip.umapyoi.supports").withStyle(ChatFormatting.AQUA));
            this.getSupports(level, stack)
                    .forEach(support -> tooltip.add(support.getDescription().copy().withStyle(ChatFormatting.GRAY)));
        } else {
            tooltip.add(Component.translatable("tooltip.umapyoi.support_card.press_shift_for_supports")
                    .withStyle(ChatFormatting.AQUA));
        }

        List<ResourceLocation> supporters = ClientUtils.getClientSupportCardRegistry().get(cardID).getSupporters();
        if (!supporters.isEmpty()) {
            if (Screen.hasControlDown() || !UmapyoiConfig.TOOLTIP_SWITCH.get()) {
                tooltip.add(Component.translatable("tooltip.umapyoi.supporters").withStyle(ChatFormatting.AQUA));
                supporters.forEach(name -> tooltip
                        .add(UmaSoulUtils.getTranslatedUmaName(name).copy().withStyle(ChatFormatting.GRAY)));
            } else {
                tooltip.add(Component.translatable("tooltip.umapyoi.support_card.press_ctrl_for_supporters")
                        .withStyle(ChatFormatting.AQUA));
            }
        }
    }

    public ResourceLocation getSupportCardID(ItemStack stack) {
        if (stack.getOrCreateTag().contains("support_card"))
            return ResourceLocation.tryParse(stack.getOrCreateTag().getString("support_card"));
        return SupportCard.EMPTY_ID;
    }

    public SupportCard getSupportCard(Level level, ItemStack stack) {
        ResourceLocation cardID = this.getSupportCardID(stack);
        if (isEmptyCard(level, cardID))
            return SupportCard.EMPTY;
        return UmapyoiAPI.getSupportCardRegistry(level).get(cardID);
    }

    private boolean isEmptyCard(Level level, ResourceLocation cardID) {
        return level == null || cardID.equals(SupportCard.EMPTY_ID) || !UmapyoiAPI.getSupportCardRegistry(level).containsKey(cardID);
    }

    @Override
    public boolean isConsumable(Level level, ItemStack stack) {
        return false;
    }

    @Override
    public GachaRanking getSupportLevel(Level level, ItemStack stack) {
        if (level == null)
            return GachaRanking.R;
        return this.getSupportCard(level, stack).getGachaRanking();
    }

    @Override
    public SupportType getSupportType(Level level, ItemStack stack) {
        return this.getSupportCard(level, stack).getSupportType();
    }

    @Override
    public List<SupportStack> getSupports(Level level, ItemStack stack) {
        return Suppliers.memoize(this.getSupportCard(level, stack)::getSupportStacks).get();
    }

    @Override
    public Predicate<ItemStack> canSupport(Level level, ItemStack stack) {
        return itemstack -> {
            if (level == null)
                return false;
            var item = itemstack.getItem();
            if (item instanceof UmaSoulItem) {
                UmaData data = UmapyoiAPI.getUmaDataRegistry(level).get(UmaSoulUtils.getName(itemstack));
                return !(this.getSupportCard(level, stack).getSupporters().contains(data.getIdentifier()));
            }
            if (item instanceof SupportCardItem) {
                return this.checkSupports(level, stack, itemstack);
            }
            return true;
        };
    }

    public boolean checkSupports(Level level, ItemStack stack, ItemStack other) {
        if (stack.getItem()instanceof SupportCardItem) {
            var supportCardID = this.getSupportCardID(stack);
            var otherCardID = this.getSupportCardID(other);
            if (supportCardID.equals(otherCardID))
                return false;

            var supportCard = this.getSupportCard(level, stack);
            var otherCard = this.getSupportCard(level, other);

            for (ResourceLocation name : supportCard.getSupporters()) {
                if (otherCard.getSupporters().contains(name))
                    return false;
            }
        }
        return true;
    }
    
    private static class CardDataComparator implements Comparator<Reference<SupportCard>> {
        @Override
        public int compare(Reference<SupportCard> left,Reference<SupportCard> right) {
            var leftRanking = left.value().getGachaRanking();
            var rightRanking = right.value().getGachaRanking();
            if(leftRanking == rightRanking) {
                String leftName = left.key().location().toString();
                String rightName = right.key().location().toString();
                return leftName.compareToIgnoreCase(rightName);
            }
            return leftRanking.compareTo(rightRanking);
        }
    }
}
