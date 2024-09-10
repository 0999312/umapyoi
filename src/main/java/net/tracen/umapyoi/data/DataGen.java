package net.tracen.umapyoi.data;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.data.builtin.SupportCardRegistry;
import net.tracen.umapyoi.data.builtin.UmaDataRegistry;
import net.tracen.umapyoi.data.loot.UmapyoiBlockLoot;
import net.tracen.umapyoi.data.tag.UmaDataTagProvider;
import net.tracen.umapyoi.data.tag.UmapyoiBlockTagProvider;
import net.tracen.umapyoi.data.tag.UmapyoiItemTagsProvider;
import net.tracen.umapyoi.data.tag.UmapyoiPOITagsProvider;
import net.tracen.umapyoi.registry.training.card.SupportCard;
import net.tracen.umapyoi.registry.umadata.UmaData;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class DataGen {
    @SubscribeEvent
    public static void dataGen(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<Provider> lookupProvider = event.getLookupProvider();

        PackOutput packOutput = dataGenerator.getPackOutput();
        dataGenerator.addProvider(event.includeClient(), new UmapyoiBlockStateProvider(packOutput, existingFileHelper));
        dataGenerator.addProvider(event.includeClient(), new UmapyoiItemModelProvider(packOutput, existingFileHelper));
        dataGenerator.addProvider(event.includeClient(), new UmapyoiLangProvider(packOutput));

        final RegistrySetBuilder umaDataBuilder = new RegistrySetBuilder().add(UmaData.REGISTRY_KEY,
                UmaDataRegistry::registerAll);

        final RegistrySetBuilder supportCardBuilder = new RegistrySetBuilder().add(SupportCard.REGISTRY_KEY,
                SupportCardRegistry::registerAll);

        dataGenerator.addProvider(event.includeServer(),
                new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, umaDataBuilder, Set.of(Umapyoi.MODID)) {
                    
                    @Override
                    public String getName() {
                        return "UmaData Registry";
                    }

                });
        dataGenerator.addProvider(event.includeServer(), 
                new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, supportCardBuilder, Set.of(Umapyoi.MODID)) {

                    @Override
                    public String getName() {
                        return "SupportCard Registry";
                    }
                });

        UmapyoiBlockTagProvider blockTagProvider = new UmapyoiBlockTagProvider(packOutput, lookupProvider,
                existingFileHelper);
        dataGenerator.addProvider(event.includeServer(), blockTagProvider);
        dataGenerator.addProvider(event.includeServer(), new UmapyoiItemTagsProvider(packOutput, lookupProvider,
                blockTagProvider.contentsGetter(), existingFileHelper));
        dataGenerator.addProvider(event.includeServer(), DataGen.getLootTableProvider(packOutput));
        dataGenerator.addProvider(event.includeServer(), new UmaDataTagProvider(packOutput,
                lookupProvider.thenApply(r -> append(r, umaDataBuilder)), existingFileHelper));
        dataGenerator.addProvider(event.includeServer(), new UmapyoiPOITagsProvider(packOutput, lookupProvider, existingFileHelper));
        dataGenerator.addProvider(event.includeServer(), new UmapyoiRecipeProvider(packOutput));

    }

    private static LootTableProvider getLootTableProvider(PackOutput packOutput) {
        LootTableProviderBuilder builder = LootTableProviderBuilder.create();
        builder.addSubProvider(
                new LootTableProvider.SubProviderEntry(UmapyoiBlockLoot::new, LootContextParamSets.BLOCK));
        return builder.build(packOutput);
    }

    private static HolderLookup.Provider append(HolderLookup.Provider original, RegistrySetBuilder builder) {
        return builder.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), original);
    }
}
