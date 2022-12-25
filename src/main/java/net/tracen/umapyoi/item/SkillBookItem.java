package net.tracen.umapyoi.item;

import java.util.List;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.NbtOps;
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
import net.tracen.umapyoi.api.UmaSkillUtils;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.skills.UmaSkill;
import net.tracen.umapyoi.registry.training.SupportContainer;
import net.tracen.umapyoi.registry.training.SupportStack;
import net.tracen.umapyoi.registry.training.SupportType;

public class SkillBookItem extends Item implements SupportContainer {
    public SkillBookItem() {
        super(Umapyoi.defaultItemProperties().stacksTo(1));

    }

    private SupportStack getSkillSupport(ItemStack stack) {
        ResourceLocation skillLoc = ResourceLocation
                .tryParse(stack.getOrCreateTag().getCompound("support").getCompound("tag").getString("skill"));
        UmaSkill skill = UmaSkillRegistry.REGISTRY.get().containsKey(skillLoc)
                ? UmaSkillRegistry.REGISTRY.get().getValue(skillLoc)
                : null;
        SupportStack result = UmaSkillUtils.getSkillSupport(skill);
        return result;
    }

    @Override
    public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
        if (this.allowdedIn(pCategory)) {
            for (UmaSkill skill : UmaSkillRegistry.REGISTRY.get().getValues()) {
                SupportStack skillSupport = UmaSkillUtils.getSkillSupport(skill);
                ItemStack result = getDefaultInstance();
                SupportStack.CODEC.encodeStart(NbtOps.INSTANCE, skillSupport)
                        .resultOrPartial(msg -> Umapyoi.getLogger().error("Failed to encode {}: {}", skillSupport, msg))
                        .ifPresent(tag -> result.getOrCreateTag().put("support", tag));
                pItems.add(result);
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(this.getSkillSupport(stack).getDescription().copy().withStyle(ChatFormatting.GRAY));
    }

    @Override
    public List<SupportStack> getSupports(ItemStack stack) {
        return ImmutableList.of(this.getSkillSupport(stack));
    }

    @Override
    public SupportType getSupportType(ItemStack stack) {
        return SupportType.FRIENDSHIP;
    }

    @Override
    public boolean isConsumable(ItemStack stack) {
        return true;
    }

    @Override
    public int getSupportLevel(ItemStack stack) {
        return 1;
    }

}
