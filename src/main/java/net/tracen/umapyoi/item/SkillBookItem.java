package net.tracen.umapyoi.item;

import java.util.List;
import java.util.Optional;

import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.skills.UmaSkill;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SkillBookItem extends Item {
    public SkillBookItem() {
        super(Umapyoi.defaultItemProperties().stacksTo(1));

    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(this.getSkill(stack).getDescription().copy().withStyle(ChatFormatting.GRAY));
    }

    public UmaSkill getSkill(ItemStack stack) {
        ResourceLocation skillID = Optional
                .ofNullable(stack.get(DataComponentsTypeRegistry.DATA_LOCATION).name())
                .orElse(UmaSkillRegistry.BASIC_PACE.getId());
        return UmaSkillRegistry.REGISTRY.get(skillID);
    }

}
