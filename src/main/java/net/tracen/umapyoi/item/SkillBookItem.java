package net.tracen.umapyoi.item;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.skills.UmaSkill;

public class SkillBookItem extends Item {
    public SkillBookItem() {
        super(Umapyoi.defaultItemProperties().stacksTo(1));

    }

    @Override
    public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
        if (this.allowdedIn(pCategory)) {
            for (UmaSkill skill : UmaSkillRegistry.REGISTRY.get().getValues()) {
                ItemStack result = getDefaultInstance();
                result.getOrCreateTag().putString("skill", skill.getRegistryName().toString());
                pItems.add(result);
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(this.getSkill(stack).getDescription().copy().withStyle(ChatFormatting.GRAY));
    }

    public UmaSkill getSkill(ItemStack stack) {
        ResourceLocation skillID = Optional.ofNullable(ResourceLocation.tryParse(stack.getOrCreateTag().getString("skill"))).orElse(UmaSkillRegistry.BASIC_PACE.getId());
        return UmaSkillRegistry.REGISTRY.get().getValue(skillID);
    }


}
