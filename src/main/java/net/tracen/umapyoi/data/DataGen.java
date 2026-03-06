package net.tracen.umapyoi.data;

import java.util.List;
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
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.data.builtin.CostumeDataRegistry;
import net.tracen.umapyoi.data.builtin.SupportCardRegistry;
import net.tracen.umapyoi.data.builtin.UmaDataRegistry;
import net.tracen.umapyoi.data.loot.UmaSkillLootTable;
import net.tracen.umapyoi.data.loot.UmapyoiBlockLoot;
import net.tracen.umapyoi.data.tag.*;
import net.tracen.umapyoi.registry.cosmetics.CosmeticData;
import net.tracen.umapyoi.registry.races.Race;
import net.tracen.umapyoi.registry.races.RaceRegistry;
import net.tracen.umapyoi.registry.races.field.RaceField;
import net.tracen.umapyoi.registry.races.field.RaceFieldRegistry;
import net.tracen.umapyoi.registry.races.tags.RaceTag;
import net.tracen.umapyoi.registry.races.tags.RaceTagRegistry;
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
        dataGenerator.addProvider(event.includeClient(), new UmapyoiSoundDefinitionProvider(packOutput, existingFileHelper));

        final RegistrySetBuilder umaDataBuilder = new RegistrySetBuilder().add(UmaData.REGISTRY_KEY,
                UmaDataRegistry::registerAll);

        final RegistrySetBuilder supportCardBuilder = new RegistrySetBuilder().add(SupportCard.REGISTRY_KEY,
                SupportCardRegistry::registerAll);

        final RegistrySetBuilder CostumeBuilder = new RegistrySetBuilder().add(CosmeticData.REGISTRY_KEY,
                CostumeDataRegistry::registerAll);

        final RegistrySetBuilder raceBuilder = new RegistrySetBuilder().add(Race.REGISTRY_KEY,
                RaceRegistry::registerAll);

        final RegistrySetBuilder raceFieldBuilder = new RegistrySetBuilder().add(RaceField.REGISTRY_KEY,
                RaceFieldRegistry::registerAll);

        final RegistrySetBuilder raceTagBuilder = new RegistrySetBuilder().add(RaceTag.REGISTRY_KEY,
                RaceTagRegistry::registerAll);

        dataGenerator.addProvider(event.includeServer(),
                new DatapackBuiltinEntriesProvider(
                		packOutput, 
                		lookupProvider, 
                		umaDataBuilder, Set.of(Umapyoi.MODID)) {
                    
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

        dataGenerator.addProvider(event.includeServer(),
                new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, CostumeBuilder, Set.of(Umapyoi.MODID)) {

                    @Override
                    public String getName() {
                        return "Costume Registry";
                    }
                });

        dataGenerator.addProvider(event.includeServer(),
                new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, raceBuilder, Set.of(Umapyoi.MODID)) {
                    @Override
                    public String getName() {
                        return "Race Registry";
                    }
                });

        dataGenerator.addProvider(event.includeServer(),
                new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, raceFieldBuilder, Set.of(Umapyoi.MODID)) {
                    @Override
                    public String getName() {
                        return "Race Field Registry";
                    }
                });

        dataGenerator.addProvider(event.includeServer(),
                new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, raceTagBuilder, Set.of(Umapyoi.MODID)) {
                    @Override
                    public String getName() {
                        return "Race Tag Registry";
                    }
                });

        UmapyoiBlockTagProvider blockTagProvider = new UmapyoiBlockTagProvider(packOutput, lookupProvider,
                existingFileHelper);
        dataGenerator.addProvider(event.includeServer(), blockTagProvider);
        dataGenerator.addProvider(event.includeServer(), new UmapyoiItemTagsProvider(packOutput, lookupProvider,
                blockTagProvider.contentsGetter(), existingFileHelper));
        dataGenerator.addProvider(event.includeServer(), DataGen.getLootTableProvider(packOutput, lookupProvider));
        dataGenerator.addProvider(event.includeServer(), new UmaDataTagProvider(packOutput,
                lookupProvider.thenApply(r -> append(umaDataBuilder)), existingFileHelper));
        dataGenerator.addProvider(event.includeServer(), new CosmeticDataTagProvider(packOutput,
                lookupProvider.thenApply(r -> append(CostumeBuilder)), existingFileHelper));
        dataGenerator.addProvider(event.includeServer(), new UmapyoiPOITagsProvider(packOutput, lookupProvider, existingFileHelper));
        dataGenerator.addProvider(event.includeServer(), new UmapyoiRecipeProvider(packOutput, lookupProvider));

    }

    private static LootTableProvider getLootTableProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
    	return new LootTableProvider(packOutput, 
    			Set.of(UmaSkillLootTable.SIMPLE_SKILLS, UmaSkillLootTable.COMPLEX_SKILLS), List.of(
    					new LootTableProvider.SubProviderEntry(UmapyoiBlockLoot::new, LootContextParamSets.BLOCK),
    					new LootTableProvider.SubProviderEntry(UmaSkillLootTable::new, LootContextParamSets.CHEST)
    					), lookupProvider);
    }

    private static HolderLookup.Provider append(RegistrySetBuilder builder) {
        return builder.build(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY));
    }
}
