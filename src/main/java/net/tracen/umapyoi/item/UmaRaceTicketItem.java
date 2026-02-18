package net.tracen.umapyoi.item;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.registry.races.Race;
import net.tracen.umapyoi.registry.races.RaceRegistry;
import net.tracen.umapyoi.utils.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;


public class UmaRaceTicketItem extends Item {
    public UmaRaceTicketItem() {
        super(Umapyoi.defaultItemProperties());
    }

    private static class RaceComparator implements Comparator<Holder.Reference<Race>> {
        public static final RaceComparator INSTANCE = new RaceComparator();
        @Override
        public int compare(Holder.Reference<Race> o1, Holder.Reference<Race> o2) {
            Year minLeft = o1.value().year.stream().min(Year::compareTo).orElse(null);
            Year minRight = o2.value().year.stream().min(Year::compareTo).orElse(null);
            if (minLeft == null) return 1;
            if (minRight == null) return -1;
            if (minLeft != minRight) return minLeft.compareTo(minRight);
            int timeLeft = o1.value().time;
            int timeRight = o2.value().time;
            if (timeLeft != timeRight) return timeLeft - timeRight;
            RaceRanking rankLeft = o1.value().ranking;
            RaceRanking rankRight = o2.value().ranking;
            if (rankLeft != rankRight) return rankRight.compareTo(rankLeft);
            Surface surfaceLeft = o1.value().surface;
            Surface surfaceRight = o2.value().surface;
            if (surfaceLeft != surfaceRight) return surfaceLeft.compareTo(surfaceRight);
            int lengthLeft = o1.value().length;
            int lengthRight = o2.value().length;
            if (lengthLeft != lengthRight) return lengthRight - lengthLeft;
            ResourceLocation locLeft = o1.key().location();
            ResourceLocation locRight = o2.key().location();
            return locLeft.compareTo(locRight);
        }
    }

    public static Stream<Holder.Reference<Race>> sortedRaceList(HolderLookup.Provider provider) {
        return UmapyoiAPI.getRaceRegistry(provider).listElements().sorted(RaceComparator.INSTANCE);
    }

    @Nonnull
    @Override
    public ItemStack getDefaultInstance() {
        ItemStack result = super.getDefaultInstance();
        result.getOrCreateTag().putString("race", "umapyoi:undetermined_race");
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
        RaceRanking ranking = Optional.ofNullable(getRace(pStack)).map(r -> r.ranking).orElse(RaceRanking.DEBUT);
        return ranking == RaceRanking.GI ? Rarity.EPIC :
                (ranking == RaceRanking.GII || ranking == RaceRanking.GIII) ? Rarity.UNCOMMON : Rarity.COMMON;
    }

    @Override
    @Deprecated
    @Nonnull
    public String getDescriptionId(@Nonnull ItemStack pStack) {
        if (getRaceID(pStack).equals(RaceRegistry.DEFAULT.location())) return super.getDescriptionId(pStack);
        return Util.makeDescriptionId("race", getRaceID(pStack)) + ".name";
    }

    public static MutableComponent getRaceNameInRawComponent(@Nonnull ItemStack pStack) {
        return Component.translatable(Util.makeDescriptionId("race", getRaceID(pStack)) + ".name");
    }

    public static MutableComponent getRaceNameInStyledComponent(@Nonnull ItemStack pStack) {
        return getRaceNameInRawComponent(pStack).withStyle(
                Optional.ofNullable(getRace(pStack)).map(r -> r.ranking).orElse(RaceRanking.DEBUT).color
        );
    }

    @Nonnull
    @Override
    public Component getName(@Nonnull ItemStack pStack) {
        if (getRaceID(pStack).equals(RaceRegistry.DEFAULT.location())) return super.getName(pStack);
        return Component.translatable(this.getDescriptionId(pStack)).withStyle(
                Optional.ofNullable(getRace(pStack)).map(r -> r.ranking).orElse(RaceRanking.DEBUT).color
        );
    }

    public static ResourceLocation getRaceID(ItemStack stack) {
        if (stack.getOrCreateTag().contains("race"))
            return Optional.ofNullable(ResourceLocation.tryParse(stack.getOrCreateTag().getString("race")))
                    .orElseGet(RaceRegistry.DEFAULT::location); // sanity check (if nbt has been incorrectly modified through command)
        return RaceRegistry.DEFAULT.location();
    }

    public static Race getRace(ItemStack stack) {
        return  getRace(stack, null);
    }

    public static Race getRace(ItemStack stack, Level world) {
        try {
            if (!stack.getOrCreateTag().contains("race")) return null;
            String rawTag = stack.getOrCreateTag().getString("race");
            ResourceLocation loc = ResourceLocation.tryParse(rawTag);
            if (loc == null) return null;
            return Optional.ofNullable(world).map(UmapyoiAPI::getRaceRegistry).orElse(ClientUtils.getRaceRegistry()).get(loc);
        } catch (Exception _ignored) {
            return null;
        }
    }

    private static class ComponentCollector implements Collector<MutableComponent, MutableComponent, MutableComponent> {
        @Override
        public Supplier<MutableComponent> supplier() {
            return Component::empty;
        }

        @Override
        public BiConsumer<MutableComponent, MutableComponent> accumulator() {
            return (s1, s2) -> (s1.getString().isEmpty() ? s1 : s1.append("/")).append(s2);
        }

        @Override
        public BinaryOperator<MutableComponent> combiner() {
            return (s1, s2) -> (s1.getString().isEmpty() ? s1 : s1.append("/")).append(s2);
        }

        @Override
        public Function<MutableComponent, MutableComponent> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Set.of(Characteristics.IDENTITY_FINISH);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, level, tooltip, flagIn);
        ResourceLocation raceID = getRaceID(stack);
        if (raceID == RaceRegistry.DEFAULT.location()) return;
        Race raceObj = getRace(stack);
        if (raceObj == null) return;
        MutableComponent year = raceObj.year.stream().sorted().map(Year::name).map(String::toLowerCase)
                .map(s -> Component.translatable("tooltip.umapyoi.race.time." + s))
                        .collect(new ComponentCollector());
        year = year.append(" ").append(Component.translatable("tooltip.umapyoi.race.time." + raceObj.time));
        tooltip.add(year);

        RaceRanking ranking = raceObj.ranking;
        tooltip.add(Component.translatable("tooltip.umapyoi.race.tier.hint").append(
                Component.translatable("race.umapyoi.tier." + ranking.name().toLowerCase()).withStyle(ranking.color)
        ));

        Surface surface = raceObj.surface;
        tooltip.add(Component.translatable("tooltip.umapyoi.race.surface").append(
                Component.translatable("race.umapyoi.surface." + surface.name().toLowerCase()).withStyle(surface.color)
        ));

        MutableComponent baseComponent = Component.translatable("tooltip.umapyoi.race.distance")
                .append(Component.translatable("tooltip.umapyoi.race.distance." + raceObj.distance.name().toLowerCase()));

        if (raceObj.distance != Distance.ADAPTIVE) {
            baseComponent = baseComponent.append(" ")
                    .append(Component.literal(Integer.toString(raceObj.length))
                            .append(Component.translatable("tooltip.umapyoi.race.unit")));
        }

        tooltip.add(baseComponent);

        ResourceLocation locField = raceObj.field;
        tooltip.add(Component.translatable("tooltip.umapyoi.race.field").append(
                Component.translatable("race." + locField.getNamespace() + ".field." + locField.getPath())
        ));

        if (!raceObj.tags.isEmpty()) {
            tooltip.add(Component.literal(""));
            tooltip.add(Component.translatable("tooltip.umapyoi.race.gainable_tags").withStyle(ChatFormatting.BLUE));
            raceObj.tags.stream().sorted()
                    .map(ClientUtils.getRaceTagRegistry()::get)
                    .filter(Objects::nonNull)
                    .map(rl ->
                            Component.literal(" ")
                                    .append(Component.translatable("race." + rl.id().getNamespace() + ".tags." + rl.id().getPath()))
                                    .withStyle(ChatFormatting.DARK_GREEN)
                    )
                    .forEach(tooltip::add);
        }
    }
}
