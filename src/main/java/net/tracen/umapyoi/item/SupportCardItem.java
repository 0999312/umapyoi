package net.tracen.umapyoi.item;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;

import com.google.common.base.Suppliers;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.capability.IUmaCapability;
import net.tracen.umapyoi.registry.training.SupportContainer;
import net.tracen.umapyoi.registry.training.SupportStack;
import net.tracen.umapyoi.registry.training.SupportType;
import net.tracen.umapyoi.registry.training.card.SupportCard;
import net.tracen.umapyoi.utils.ClientUtils;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class SupportCardItem extends Item implements SupportContainer{
    public SupportCardItem() {
        super(Umapyoi.defaultItemProperties().stacksTo(1));
    }
    
    @Override
    public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
        if (this.allowdedIn(pCategory)) {
            if (Minecraft.getInstance().getConnection() != null) {
                ClientUtils.getClientSupportCardRegistry().keySet()
                .forEach(card -> {
                    ItemStack result = this.getDefaultInstance();
                    result.getOrCreateTag().putString("support_card", card.toString());
                    pItems.add(result);
                });
            }
        }
    }
    
    @Override
    public String getDescriptionId(ItemStack pStack) {
        return Util.makeDescriptionId("support_card", this.getSupportCardID(pStack)) + ".name";
    }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableComponent("tooltip.umapyoi.supports").withStyle(ChatFormatting.AQUA));
        this.getSupports(worldIn, stack)
            .forEach(support->tooltip.add(support.getDescription().copy().withStyle(ChatFormatting.GRAY)));
        List<ResourceLocation> supporters = this.getSupportCard(worldIn, stack).getSupporters();
        if(!supporters.isEmpty()) {
            tooltip.add(new TranslatableComponent("tooltip.umapyoi.supporters").withStyle(ChatFormatting.AQUA));
            supporters.forEach(name->tooltip.add(UmaSoulUtils.getTranslatedUmaName(name).copy().withStyle(ChatFormatting.GRAY)));
        }
    }
    
    public ResourceLocation getSupportCardID(ItemStack stack) {
        return ResourceLocation.tryParse(stack.getOrCreateTag().getString("support_card"));
    }

    public SupportCard getSupportCard(Level level, ItemStack stack) {
        return UmapyoiAPI.getSupportCardRegistry(level).get(this.getSupportCardID(stack));
    }

    @Override
    public boolean isConsumable(Level level, ItemStack stack) {
        return false;
    }


    @Override
    public int getSupportLevel(Level level, ItemStack stack) {
        return this.getSupportCard(level, stack).getSupportCardLevel();
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
    public Predicate<IUmaCapability> canSupport(Level level, ItemStack stack) {
        return cap -> !(this.getSupportCard(level, stack).getSupporters().contains(cap.getUmaStatus().name()));
    }

}
