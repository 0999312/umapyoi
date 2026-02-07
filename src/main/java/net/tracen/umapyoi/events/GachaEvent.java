package net.tracen.umapyoi.events;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;

public abstract class GachaEvent extends Event {
    private final Collection<ResourceLocation> fulfills;
    private final ResourceLocation target;
    private final ItemStack defaultResult;
    private ItemStack output = null;
    private final RandomSource rnd;
    private final ItemStack input;
    public GachaEvent(ItemStack input, Collection<ResourceLocation> fulfills, ResourceLocation target, ItemStack defaultResult, RandomSource src) {
        this.fulfills = fulfills;
        this.target = target;
        this.defaultResult = defaultResult;
        this.rnd = src;
        this.input = input;
    }

    public ItemStack getInput() {
        return this.input;
    }

    public RandomSource getRandomSource() {
        return this.rnd;
    }

    public ItemStack getOutput() {
        return Objects.requireNonNullElse(this.output, this.defaultResult);
    }

    public void setOutput(@Nullable ItemStack output) {
        this.output = output;
    }

    public ResourceLocation getOriginalTarget() {
        return this.target;
    }

    public Collection<ResourceLocation> getOriginalFulfills() {
        return this.fulfills;
    }

    public abstract String getType();

    public static class UmaSoulGachaEvent extends GachaEvent {
        public UmaSoulGachaEvent(ItemStack input, Collection<ResourceLocation> fulfills, ResourceLocation target,
                                 ItemStack defaultResult, RandomSource src) {
            super(input, fulfills, target, defaultResult, src);
        }

        @Override
        public String getType() {
            return "umasoul";
        }
    }

    public static class SupportCardGachaEvent extends GachaEvent {
        public SupportCardGachaEvent(ItemStack input, Collection<ResourceLocation> fulfills, ResourceLocation target,
                                 ItemStack defaultResult, RandomSource src) {
            super(input, fulfills, target, defaultResult, src);
        }

        @Override
        public String getType() {
            return "supportcard";
        }
    }
}
