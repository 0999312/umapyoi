package net.tracen.umapyoi.item;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.registry.umadata.UmaDataBasicStatus;
import net.tracen.umapyoi.utils.GachaRanking;
import net.tracen.umapyoi.utils.ResultRankingUtils;
import net.tracen.umapyoi.utils.UmaSoulUtils;
import net.tracen.umapyoi.utils.UmaStatusUtils;

public class UmaSoulItem extends Item {
    private static final Comparator<Reference<UmaData>> COMPARATOR = new UmaDataComparator();
    
    public UmaSoulItem() {
        super(Umapyoi.defaultItemProperties().stacksTo(1));
    }

    public static Stream<Reference<UmaData>> sortedUmaDataList(HolderLookup.Provider provider) {
        return provider.lookupOrThrow(UmaData.REGISTRY_KEY).listElements().sorted(UmaSoulItem.COMPARATOR);
    }
    
    @Override
    public Component getName(ItemStack pStack) {
        GachaRanking ranking = GachaRanking.getGachaRanking(pStack);
        if(ranking == GachaRanking.EASTER_EGG) return super.getName(pStack).copy().withStyle(ChatFormatting.GREEN);
        return super.getName(pStack);
    }
    
    @Override
    public String getDescriptionId(ItemStack pStack) {
        return Util.makeDescriptionId("umadata", UmaSoulUtils.getName(pStack));
    }
    
    @Override
    public boolean isFoil(ItemStack pStack) {
        return !pStack.has(DataComponentsTypeRegistry.UMADATA_TRAINING);
    }
    
    @Override
    public boolean isBarVisible(ItemStack pStack) {
    	if(!pStack.has(DataComponentsTypeRegistry.UMADATA_TRAINING))
    		return false;
        var physique = UmaSoulUtils.getPhysique(pStack);
        return physique != 5;
    }
    
    @Override
    public int getBarWidth(ItemStack pStack) {
        var physique = UmaSoulUtils.getPhysique(pStack);
        return Math.round(13.0F - (5 - physique) * 13.0F / 5);
    }
    
    @Override
    public int getBarColor(ItemStack pStack) {
        float stackMaxDamage = 5;
        var physique = UmaSoulUtils.getPhysique(pStack);
        float f = Math.max(0.0F, physique / stackMaxDamage);
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip,
    		TooltipFlag tooltipFlag) {
    	super.appendHoverText(stack, context, tooltip, tooltipFlag);
        int ranking = ResultRankingUtils.getRanking(stack);
        if(!stack.has(DataComponentsTypeRegistry.UMADATA_TRAINING))
            tooltip.add(Component.translatable("tooltip.umapyoi.uma_soul.ranking", UmaStatusUtils.getStatusLevel(ranking))
                            .withStyle(ChatFormatting.GOLD));
        if (Screen.hasShiftDown() || !UmapyoiConfig.TOOLTIP_SWITCH.get()) {
            tooltip.add(
                    Component.translatable("tooltip.umapyoi.uma_soul.soul_details").withStyle(ChatFormatting.AQUA));
            UmaDataBasicStatus property = UmaSoulUtils.getProperty(stack);
            UmaDataBasicStatus maxProperty = UmaSoulUtils.getMaxProperty(stack);

            tooltip.add(Component.translatable("tooltip.umapyoi.uma_soul.speed_details",
                    UmaStatusUtils.getStatusLevel(property.speed()),
                    UmaStatusUtils.getStatusLevel(maxProperty.speed()))
                            .withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(Component.translatable("tooltip.umapyoi.uma_soul.stamina_details",
                    UmaStatusUtils.getStatusLevel(property.stamina()),
                    UmaStatusUtils.getStatusLevel(maxProperty.stamina()))
                            .withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(Component.translatable("tooltip.umapyoi.uma_soul.strength_details",
                    UmaStatusUtils.getStatusLevel(property.strength()),
                    UmaStatusUtils.getStatusLevel(maxProperty.strength()))
                            .withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(Component.translatable("tooltip.umapyoi.uma_soul.guts_details",
                    UmaStatusUtils.getStatusLevel(property.guts()),
                    UmaStatusUtils.getStatusLevel(maxProperty.guts()))
                            .withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(Component.translatable("tooltip.umapyoi.uma_soul.wisdom_details",
                    UmaStatusUtils.getStatusLevel(property.wisdom()),
                    UmaStatusUtils.getStatusLevel(maxProperty.wisdom()))
                            .withStyle(ChatFormatting.DARK_GREEN));
        } else {
            tooltip.add(Component.translatable("tooltip.umapyoi.press_shift_for_details")
                    .withStyle(ChatFormatting.AQUA));
        }
      }

    private static class UmaDataComparator implements Comparator<Reference<UmaData>> {
        @Override
        public int compare(Reference<UmaData> left, Reference<UmaData> right) {
            var leftRanking = left.value().ranking();
            var rightRanking = right.value().ranking();
            if(leftRanking == rightRanking) {
                String leftName = left.key().location().toString();
                String rightName = right.key().location().toString();
                return leftName.compareToIgnoreCase(rightName);
            }
            return leftRanking.compareTo(rightRanking);
        }
    }
}
