package net.tracen.umapyoi.item;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.skills.UmaSkill;

public class SkillBookItem extends Item {
    public SkillBookItem() {
        super(Umapyoi.defaultItemProperties().stacksTo(1));

    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(this.getSkill(stack).getDescription().copy().withStyle(ChatFormatting.GRAY));
        if(flagIn.isAdvanced() || UmapyoiConfig.DISPLAY_DETAIL.get()) {
        	tooltip.add(this.getSkill(stack).getDescriptionDetail().copy().withStyle(ChatFormatting.DARK_GRAY));
        }
    }
    
    @Override
    public String getCreatorModId(ItemStack itemStack) {
        ResourceLocation skillID = Optional
                .ofNullable(ResourceLocation.tryParse(itemStack.getOrCreateTag().getString("skill")))
                .orElse(UmaSkillRegistry.BASIC_PACE.getId());
    	return skillID.getNamespace();
    }
    
    public UmaSkill getSkill(ItemStack stack) {
        ResourceLocation skillID = Optional
                .ofNullable(ResourceLocation.tryParse(stack.getOrCreateTag().getString("skill")))
                .orElse(UmaSkillRegistry.BASIC_PACE.getId());
        if(!UmaSkillRegistry.REGISTRY.get().containsKey(skillID))
        	skillID = UmaSkillRegistry.BASIC_PACE.getId();
        return UmaSkillRegistry.REGISTRY.get().getValue(skillID);
    }

}
