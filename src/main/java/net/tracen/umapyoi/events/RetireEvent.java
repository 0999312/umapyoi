package net.tracen.umapyoi.events;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.registry.factors.UmaFactorStack;
import net.tracen.umapyoi.utils.UmaFactorUtils;
import net.tracen.umapyoi.utils.UmaSoulUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RetireEvent extends Event {
    @Cancelable
    public static class Pre extends RetireEvent {
        public final int randomSeed;
        private final List<UmaFactorStack> originalFactors;
        private List<UmaFactorStack> resultFactors;
        private final ItemStack soul;

        private ItemStack outputStack;

        public Pre(int seed, List<UmaFactorStack> factors, ItemStack soul) {
            this.randomSeed = seed;
            this.originalFactors = Collections.unmodifiableList(factors);
            this.resultFactors = new ArrayList<>(factors);
            this.soul = soul;
        }

        public List<UmaFactorStack> getOutputFactors() {
            return Objects.requireNonNullElse(resultFactors, originalFactors);
        }

        public void setOutputFactors(List<UmaFactorStack> factors) {
            this.resultFactors = factors;
        }

        public void setOutputStack(ItemStack outputStack) {
            this.outputStack = outputStack;
        }

        public ItemStack getOutputStack() {
            return Objects.requireNonNullElseGet(outputStack, this::getDefaultOutputStack);
        }

        public static ItemStack getDefaultOutputStack(ItemStack soul, List<UmaFactorStack> factors) {
            ItemStack result = ItemRegistry.UMA_FACTOR_ITEM.get().getDefaultInstance();
            result.getOrCreateTag().putString("name", UmaSoulUtils.getName(soul).toString());
            result.getOrCreateTag().put("factors", UmaFactorUtils.serializeNBT(factors));
            return result;
        }

        public ItemStack getDefaultOutputStack() {
            return getDefaultOutputStack(this.soul, this.getOutputFactors());
        }

        public ItemStack getSoul() {
            return this.soul;
        }
    }

    public static class Post extends RetireEvent {
        private final ItemStack stackSoulPre;
        private final ItemStack stackSoulPostDefault;
        private ItemStack stackSoulPost;
        private final ItemStack outputFactor;

        public Post(ItemStack pre, ItemStack post, ItemStack output) {
            this.stackSoulPre = pre;
            this.stackSoulPostDefault = post;
            this.outputFactor = output;
        }

        public ItemStack getStackSoulPre() {
            return this.stackSoulPre;
        }

        public ItemStack getOutputFactor() {
            return this.outputFactor;
        }

        public ItemStack getStackSoulPost() {
            return Objects.requireNonNullElse(this.stackSoulPost, this.stackSoulPostDefault);
        }

        public ItemStack getStackSoulPostDefault() {
            return this.stackSoulPostDefault;
        }

        public void setStackSoulPost(ItemStack stack) {
            this.stackSoulPost = stack;
        }
    }
}
