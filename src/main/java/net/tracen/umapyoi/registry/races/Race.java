package net.tracen.umapyoi.registry.races;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.registry.umadata.Growth;
import net.tracen.umapyoi.utils.RaceRanking;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class Race {
    public static final ResourceKey<Registry<Race>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(new ResourceLocation(Umapyoi.MODID, "races"));

    public final RaceRanking ranking;
    public final int length;

    public Race(RaceRanking ranking, int length) {
        this.ranking = ranking;
        this.length = length;
    }

    public boolean isAvailableToUmaSoul(ItemStack stack) {
        return stack.is(ItemRegistry.UMA_SOUL.get()) && UmaSoulUtils.getGrowth(stack) == Growth.RETIRED;
    }

    public void followUp(ItemStack stack) {
        return;
    }
}
