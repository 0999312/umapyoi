package net.tracen.umapyoi.item;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.registry.training.SupportContainer;
import net.tracen.umapyoi.registry.training.SupportStack;
import net.tracen.umapyoi.registry.training.SupportType;
import net.tracen.umapyoi.registry.training.TrainingSupport;
import net.tracen.umapyoi.utils.GachaRanking;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class TrainingItem extends Item implements SupportContainer {
    private final SupportType type;
    private final Supplier<TrainingSupport> support;
    private final int level;

    public TrainingItem(SupportType type, Supplier<TrainingSupport> support, int level) {
        super(Umapyoi.defaultItemProperties());
        this.type = type;
        this.support = support;
        this.level = level;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide)
            return super.use(pLevel, pPlayer, pUsedHand);
        ItemStack soul = UmapyoiAPI.getUmaSoul(pPlayer);
        ItemStack itemInHand = pPlayer.getItemInHand(pUsedHand);
        if (soul.isEmpty()) {
            pPlayer.displayClientMessage(new TranslatableComponent("umapyoi.no_umasoul_equiped"), true);
            return InteractionResultHolder.fail(itemInHand);
        }
        
        if (UmaSoulUtils.getLearningTimes(soul) <= 0) {
            pPlayer.displayClientMessage(new TranslatableComponent("umapyoi.learning.no_learning_time"), true);
            return InteractionResultHolder.fail(itemInHand);
        }
        
        if (this.getSupport().applySupport(soul)) {
            UmaSoulUtils.downLearningTimes(soul);
            pPlayer.getCooldowns().addCooldown(itemInHand.getItem(), 30);
            itemInHand.shrink(1);
            return InteractionResultHolder.success(itemInHand);
        } else {
            pPlayer.displayClientMessage(new TranslatableComponent("umapyoi.learning.can_not_learn"), true);
            return InteractionResultHolder.fail(itemInHand);
        }
    }

    protected SupportStack getSupport() {
        return Suppliers.memoize(() -> new SupportStack(this.support.get(), this.level)).get();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(this.getSupport().getDescription());
    }

    @Override
    public boolean isConsumable(Level level, ItemStack stack) {
        return true;
    }

    @Override
    public GachaRanking getSupportLevel(Level level, ItemStack stack) {
        return GachaRanking.values()[this.level - 1];
    }

    @Override
    public SupportType getSupportType(Level level, ItemStack stack) {
        return this.type;
    }

    @Override
    public List<SupportStack> getSupports(Level level, ItemStack stack) {
        return ImmutableList.of(this.getSupport());
    }

    @Override
    public Predicate<ItemStack> canSupport(Level level, ItemStack stack) {
        return soul -> true;
    }

}
