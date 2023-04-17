package net.tracen.umapyoi.item;

import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
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
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.utils.ClientUtils;
import net.tracen.umapyoi.utils.GachaRanking;
import net.tracen.umapyoi.utils.UmaSoulUtils;
import net.tracen.umapyoi.utils.UmaStatusUtils;
import net.tracen.umapyoi.utils.UmaStatusUtils.StatusType;

public class UmaSoulItem extends Item {

    public UmaSoulItem() {
        super(Umapyoi.defaultItemProperties().stacksTo(1));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            if (Minecraft.getInstance().getConnection() != null) {
                for (Entry<ResourceKey<UmaData>, UmaData> entry : ClientUtils.getClientUmaDataRegistry().entrySet()) {
                    items.add(UmaSoulUtils.initUmaSoul(getDefaultInstance(), entry.getKey().location(),
                            entry.getValue()));
                }
            }
        }
    }
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        return new UmaSoulCapProvider(stack);
    }
    
    @Override
    public Rarity getRarity(ItemStack pStack) {
        GachaRanking ranking = GachaRanking.getGachaRanking(pStack);
        return ranking == GachaRanking.SSR ? Rarity.EPIC : ranking == GachaRanking.SR ? Rarity.UNCOMMON : Rarity.COMMON;
    }

    @Override
    public String getDescriptionId(ItemStack pStack) {
        return Util.makeDescriptionId("umadata", UmaSoulUtils.getName(pStack));
    }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if (Screen.hasShiftDown() || !UmapyoiConfig.TOOLTIP_SWITCH.get()) {
            tooltip.add(
                    new TranslatableComponent("tooltip.umapyoi.uma_soul.soul_details").withStyle(ChatFormatting.AQUA));
            int[] property = UmaSoulUtils.getProperty(stack);
            int[] maxProperty = UmaSoulUtils.getMaxProperty(stack);

            tooltip.add(new TranslatableComponent("tooltip.umapyoi.uma_soul.speed_details",
                    UmaStatusUtils.getStatusLevel(property[StatusType.SPEED.getId()]),
                    UmaStatusUtils.getStatusLevel(maxProperty[StatusType.SPEED.getId()]))
                            .withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(new TranslatableComponent("tooltip.umapyoi.uma_soul.stamina_details",
                    UmaStatusUtils.getStatusLevel(property[StatusType.STAMINA.getId()]),
                    UmaStatusUtils.getStatusLevel(maxProperty[StatusType.STAMINA.getId()]))
                            .withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(new TranslatableComponent("tooltip.umapyoi.uma_soul.strength_details",
                    UmaStatusUtils.getStatusLevel(property[StatusType.STRENGTH.getId()]),
                    UmaStatusUtils.getStatusLevel(maxProperty[StatusType.STRENGTH.getId()]))
                            .withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(new TranslatableComponent("tooltip.umapyoi.uma_soul.guts_details",
                    UmaStatusUtils.getStatusLevel(property[StatusType.GUTS.getId()]),
                    UmaStatusUtils.getStatusLevel(maxProperty[StatusType.GUTS.getId()]))
                            .withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(new TranslatableComponent("tooltip.umapyoi.uma_soul.wisdom_details",
                    UmaStatusUtils.getStatusLevel(property[StatusType.WISDOM.getId()]),
                    UmaStatusUtils.getStatusLevel(maxProperty[StatusType.WISDOM.getId()]))
                            .withStyle(ChatFormatting.DARK_GREEN));

        } else {
            tooltip.add(new TranslatableComponent("tooltip.umapyoi.press_shift_for_details")
                    .withStyle(ChatFormatting.AQUA));
        }
    }

}
