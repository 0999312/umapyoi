package net.tracen.umapyoi.item;

import net.minecraft.Util;
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
import net.tracen.umapyoi.registry.races.RaceRegistry;
import net.tracen.umapyoi.utils.RaceRanking;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;


public class UmaRacingSlipItem extends Item {
    public UmaRacingSlipItem() {
        super(Umapyoi.defaultItemProperties());
    }

    @Nonnull
    @Override
    public ItemStack getDefaultInstance() {
        ItemStack result = super.getDefaultInstance();
        result.getOrCreateTag().putString("race", "umapyoi:undetermined_race");
        result.getOrCreateTag().putString("race_rarity", "debut");
        result.getOrCreateTag().putInt("race_length", 0);
        return result;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isRepairable(@Nonnull ItemStack stack) {
        return false;
    }

    @Nonnull
    @Override
    @Deprecated
    public Rarity getRarity(@Nonnull ItemStack pStack) {
        RaceRanking ranking = getRaceRanking(pStack);
        return ranking == RaceRanking.GI ? Rarity.EPIC :
                (ranking == RaceRanking.GII || ranking == RaceRanking.GIII) ? Rarity.UNCOMMON : Rarity.COMMON;
    }

    @Override
    @Deprecated
    @Nonnull
    public String getDescriptionId(@Nonnull ItemStack pStack) {
        return Util.makeDescriptionId("race", getRaceID(pStack)) + ".name";
    }

    @Nonnull
    @Override
    public Component getName(@Nonnull ItemStack pStack) {
        return Component.translatable(this.getDescriptionId(pStack)).withStyle(getRaceRanking(pStack).color);
    }

    public static ResourceLocation getRaceID(ItemStack stack) {
        if (stack.getOrCreateTag().contains("race"))
            return Optional.ofNullable(ResourceLocation.tryParse(stack.getOrCreateTag().getString("race")))
                    .orElseGet(RaceRegistry.DEFAULT::getId); // sanity check (if nbt has been incorrectly modified through command)
        return RaceRegistry.DEFAULT.getId();
    }

    public static RaceRanking getRaceRanking(ItemStack stack) {
        return RaceRanking.getRaceRanking(stack);
    }

    public static int getLength(ItemStack stack) {
        try {
            return stack.getOrCreateTag().getInt("race_length");
        } catch (Exception _ignored) {
            return 0;
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, level, tooltip, flagIn);
        ResourceLocation raceID = getRaceID(stack);
        if (raceID == RaceRegistry.DEFAULT.getId()) return;
        RaceRanking ranking = getRaceRanking(stack);
        tooltip.add(Component.translatable("tooltip.umapyoi.race.tier.hint").append(
                Component.translatable("race.umapyoi.tier." + ranking.name().toLowerCase()).withStyle(ranking.color)
        ));
        tooltip.add(Component.translatable("tooltip.umapyoi.race.distance").append(
                Component.literal(Integer.toString(this.getLength(stack))
        ).append(Component.translatable("tooltip.umapyoi.race.unit"))));
    }
}
