package net.tracen.umapyoi.registry.races;

import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.utils.RaceRanking;

import java.util.function.BiFunction;
import java.util.function.Consumer;

public class RaceWithPredicate extends Race {
    private final BiFunction<ItemStack, Race, Boolean> predicate;
    private final Consumer<ItemStack> followup;
    public RaceWithPredicate(RaceRanking ranking, int length, BiFunction<ItemStack, Race, Boolean> avail, Consumer<ItemStack> followup) {
        super(ranking, length);
        this.predicate = avail;
        this.followup = followup;
    }

    @Override
    public boolean isAvailableToUmaSoul(ItemStack stack) {
        return super.isAvailableToUmaSoul(stack) && this.predicate.apply(stack, this);
    }

    @Override
    public void followUp(ItemStack stack) {
        this.followup.accept(stack);
    }

    public static class RaceWithPredicateBuilder {
        private BiFunction<ItemStack, Race, Boolean> pred;
        private Consumer<ItemStack> follow;
        private RaceRanking ranking;
        private int length;

        public RaceWithPredicateBuilder() {
            this.pred = (s, r) -> true;
            this.follow = (s) -> {};
            this.ranking = RaceRanking.DEBUT;
            this.length = 0;
        }

        public RaceWithPredicateBuilder setPredicate(BiFunction<ItemStack, Race, Boolean> predicate) {
            this.pred = predicate;
            return this;
        }

        public RaceWithPredicateBuilder setFollowup(Consumer<ItemStack> followUp) {
            this.follow = followUp;
            return this;
        }

        public RaceWithPredicateBuilder setLength(int len) {
            this.length = len;
            return this;
        }

        public RaceWithPredicateBuilder setRanking(RaceRanking rank) {
            this.ranking = rank;
            return this;
        }

        public Race create() {
            return new RaceWithPredicate(this.ranking, this.length, this.pred, this.follow);
        }
    }
}
