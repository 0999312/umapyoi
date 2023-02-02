package net.tracen.umapyoi.data.loot;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import cn.mcmod_mmf.mmlib.data.AbstractLootTableProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class UmapyoiLootTableProvider extends AbstractLootTableProvider {

    public UmapyoiLootTableProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    public String getName() {
        return "Umapyoi Loot Table Provider";
    }

    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> tables = ImmutableList
            .of(Pair.of(UmapyoiBlockLoot::new, LootContextParamSets.BLOCK));

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> getTables() {
        return this.tables;
    }

}
