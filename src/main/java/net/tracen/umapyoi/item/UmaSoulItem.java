package net.tracen.umapyoi.item;

import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.curios.UmaSoulCapProvider;
import net.tracen.umapyoi.registry.umadata.Growth;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.utils.ClientUtils;
import net.tracen.umapyoi.utils.GachaRanking;
import net.tracen.umapyoi.utils.ResultRankingUtils;
import net.tracen.umapyoi.utils.UmaSoulUtils;
import net.tracen.umapyoi.utils.UmaStatusUtils;
import net.tracen.umapyoi.utils.UmaStatusUtils.StatusType;

public class UmaSoulItem extends Item {
    private static final Comparator<Entry<ResourceKey<UmaData>, UmaData>> COMPARATOR = new UmaDataComparator();
    
    public UmaSoulItem() {
        super(Umapyoi.defaultItemProperties().stacksTo(1));
    }

    public static Stream<Entry<ResourceKey<UmaData>, UmaData>> sortedUmaDataList() {
        return ClientUtils.getClientUmaDataRegistry().entrySet().stream().sorted(UmaSoulItem.COMPARATOR);
    }
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
    	if(!stack.isEmpty() && stack.getItem() instanceof UmaSoulItem)
    		return new UmaSoulCapProvider(stack);
    	return null;
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
    public String getDescriptionId(ItemStack pStack) {
        return Util.makeDescriptionId("umadata", UmaSoulUtils.getName(pStack));
    }
    
    @Override
    public boolean isFoil(ItemStack pStack) {
        return UmaSoulUtils.getGrowth(pStack) == Growth.RETIRED;
    }
    
    @Override
    public boolean isBarVisible(ItemStack pStack) {
        var physique = UmaSoulUtils.getPhysique(pStack);
        return UmaSoulUtils.getGrowth(pStack) != Growth.RETIRED && physique != 5;
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
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        int ranking = ResultRankingUtils.getRanking(stack);
        if(UmaSoulUtils.getGrowth(stack) == Growth.RETIRED)
            tooltip.add(Component.translatable("tooltip.umapyoi.uma_soul.ranking", UmaStatusUtils.getStatusLevel(ranking))
                            .withStyle(ChatFormatting.GOLD));
        if (Screen.hasShiftDown() || !UmapyoiConfig.TOOLTIP_SWITCH.get()) {
            tooltip.add(
                    Component.translatable("tooltip.umapyoi.uma_soul.soul_details").withStyle(ChatFormatting.AQUA));
            int[] property = UmaSoulUtils.getProperty(stack);
            int[] maxProperty = UmaSoulUtils.getMaxProperty(stack);

            tooltip.add(Component.translatable("tooltip.umapyoi.uma_soul.speed_details",
                    UmaStatusUtils.getStatusLevel(property[StatusType.SPEED.getId()]),
                    UmaStatusUtils.getStatusLevel(maxProperty[StatusType.SPEED.getId()]))
                            .withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(Component.translatable("tooltip.umapyoi.uma_soul.stamina_details",
                    UmaStatusUtils.getStatusLevel(property[StatusType.STAMINA.getId()]),
                    UmaStatusUtils.getStatusLevel(maxProperty[StatusType.STAMINA.getId()]))
                            .withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(Component.translatable("tooltip.umapyoi.uma_soul.strength_details",
                    UmaStatusUtils.getStatusLevel(property[StatusType.STRENGTH.getId()]),
                    UmaStatusUtils.getStatusLevel(maxProperty[StatusType.STRENGTH.getId()]))
                            .withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(Component.translatable("tooltip.umapyoi.uma_soul.guts_details",
                    UmaStatusUtils.getStatusLevel(property[StatusType.GUTS.getId()]),
                    UmaStatusUtils.getStatusLevel(maxProperty[StatusType.GUTS.getId()]))
                            .withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(Component.translatable("tooltip.umapyoi.uma_soul.wisdom_details",
                    UmaStatusUtils.getStatusLevel(property[StatusType.WISDOM.getId()]),
                    UmaStatusUtils.getStatusLevel(maxProperty[StatusType.WISDOM.getId()]))
                            .withStyle(ChatFormatting.DARK_GREEN));
        } else {
            tooltip.add(Component.translatable("tooltip.umapyoi.press_shift_for_details")
                    .withStyle(ChatFormatting.AQUA));
        }
    }

    private static class UmaDataComparator implements Comparator<Entry<ResourceKey<UmaData>, UmaData>> {
        @Override
        public int compare(Entry<ResourceKey<UmaData>, UmaData> left, Entry<ResourceKey<UmaData>, UmaData> right) {
            var leftRanking = left.getValue().getGachaRanking();
            var rightRanking = right.getValue().getGachaRanking();
            if(leftRanking == rightRanking) {
                String leftName = left.getKey().location().toString();
                String rightName = right.getKey().location().toString();
                return leftName.compareToIgnoreCase(rightName);
            }
            return leftRanking.compareTo(rightRanking);
        }
    }
}
