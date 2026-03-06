package net.tracen.umapyoi;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tracen.umapyoi.item.*;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.item.data.DataLocation;
import net.tracen.umapyoi.registry.UmaFactorRegistry;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.factors.FactorType;
import net.tracen.umapyoi.registry.factors.UmaFactor;
import net.tracen.umapyoi.registry.factors.UmaFactorStack;
import net.tracen.umapyoi.registry.races.RaceRegistry;
import net.tracen.umapyoi.registry.training.card.SupportCard;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.utils.UmaFactorUtils;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class UmapyoiCreativeGroup {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, Umapyoi.MODID);

    public static final Holder<CreativeModeTab> UMAPYOI_ITEMS = CREATIVE_MODE_TABS.register("umapyoi",
            () -> CreativeModeTab.builder().icon(ItemRegistry.HACHIMI_MID.get()::getDefaultInstance)
                    .title(Component.translatable("itemGroup.umapyoi")).displayItems((features, output) -> {
                        ItemRegistry.ITEMS.getEntries().forEach(item -> {
                            if (item == ItemRegistry.BLANK_UMA_SOUL) {
//                            	fillBlankSoul(features, output);
                                return;
                            }

                            if (item == ItemRegistry.UMA_COSTUME) {
                                fillCostume(features, output);
                                return;
                            }

                            if (item == ItemRegistry.UMA_FACTOR_ITEM) {
                                fillFactorContainer(output);
                                return;
                            }
                            if (item == ItemRegistry.UMA_SOUL || item == ItemRegistry.UMA_SOUL_DISPLAY) {
//                                fillUmaSoul(features, output);
                                return;
                            }
                            if (item == ItemRegistry.SUPPORT_CARD) {
//                                fillSupportCard(features, output);
                                return;
                            }
                            if (item == ItemRegistry.SKILL_BOOK) {
                                fillSkillBook(output);
                                return;
                            }
                            if (item == ItemRegistry.UMA_RACE_TICKET) {
                                return;
                            }
                            if (item == ItemRegistry.FACTOR_SHARD) {
                                return;
                            }
                            output.accept(item.get());
                        });
                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> UMAPYOI_BLANK_SOULS = CREATIVE_MODE_TABS.register("umapyoi_blank_souls",
            () -> CreativeModeTab.builder().icon(ItemRegistry.BLANK_UMA_SOUL.get()::getDefaultInstance)
                    .title(Component.translatable("itemGroup.umapyoi.blank_souls")).displayItems((features, output) -> {
                        ItemRegistry.ITEMS.getEntries().forEach(item -> {
                            if (item == ItemRegistry.BLANK_UMA_SOUL) {
                                fillBlankSoul(features, output);
                                return;
                            }
                        });
                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> UMAPYOI_SOULS = CREATIVE_MODE_TABS.register("umapyoi_souls",
            () -> CreativeModeTab.builder().icon(ItemRegistry.UMA_SOUL_DISPLAY.get()::getDefaultInstance)
                    .title(Component.translatable("itemGroup.umapyoi.souls")).displayItems((features, output) -> {
                        ItemRegistry.ITEMS.getEntries().forEach(item -> {
                            if (item == ItemRegistry.UMA_SOUL) {
                                fillUmaSoul(features, output);
                                return;
                            }
                        });
                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> UMAPYOI_CARDS = CREATIVE_MODE_TABS.register("umapyoi_cards",
            () -> CreativeModeTab.builder().icon(ItemRegistry.SUPPORT_CARD.get()::getDefaultInstance)
                    .title(Component.translatable("itemGroup.umapyoi.cards")).displayItems((features, output) -> {
                        ItemRegistry.ITEMS.getEntries().forEach(item -> {
                            if (item == ItemRegistry.SUPPORT_CARD) {
                                fillSupportCard(features, output);
                                return;
                            }
                        });
                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> UMAPYOI_RACETICKETS = CREATIVE_MODE_TABS.register("umapyoi_racetickets",
            () -> CreativeModeTab.builder().icon(ItemRegistry.UMA_RACE_TICKET.get()::getDefaultInstance)
                    .title(Component.translatable("itemGroup.umapyoi.race_tickets")).displayItems((features, output) -> {
                        ItemRegistry.ITEMS.getEntries().forEach(item -> {
                            if (item == ItemRegistry.UMA_RACE_TICKET) {
                                fillTicket(features, output);
                                return;
                            }
                        });
                    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> UMAPYOI_FACTORSHARDS = CREATIVE_MODE_TABS.register("umapyoi_factorshards",
            () -> CreativeModeTab.builder().icon(ItemRegistry.FACTOR_SHARD.get()::getDefaultInstance)
                    .title(Component.translatable("itemGroup.umapyoi.factor_shards")).displayItems((features, output) -> {
                        ItemRegistry.ITEMS.getEntries().forEach(item -> {
                            if (item == ItemRegistry.FACTOR_SHARD) {
                                fillShards(features, output);
                                return;
                            }
                        });
                    }).build());

    private static void fillFactorContainer(CreativeModeTab.Output output) {
        UmaFactorRegistry.REGISTRY.stream().forEach(factor->{
        	if (factor == UmaFactorRegistry.SKILL_FACTOR.get() || factor.getFactorType() == FactorType.UNIQUE)
                return;
            List<UmaFactorStack> stackList = Lists.newArrayList(new UmaFactorStack(factor, 1));
            
            ItemStack result = ItemRegistry.UMA_FACTOR_ITEM.get().getDefaultInstance();
            result.set(DataComponentsTypeRegistry.DATA_LOCATION, new DataLocation(UmaData.DEFAULT_UMA_ID));
            result.set(DataComponentsTypeRegistry.FACTOR_DATA, UmaFactorUtils.serializeData(stackList));
            output.accept(result);
        });
    }

    private static void fillUmaSoul(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
    	UmaSoulItem.sortedUmaDataList(parameters.holders()).forEach(entry->{
          ItemStack initUmaSoul = UmaSoulUtils.initUmaSoul(ItemRegistry.UMA_SOUL.get().getDefaultInstance(),
	          entry.key().location(), entry.value());
	      UmaSoulUtils.setPhysique(initUmaSoul, 5);
	      output.accept(initUmaSoul);
    	});
    }

    private static void fillCostume(CreativeModeTab.ItemDisplayParameters features, CreativeModeTab.Output output) {
        UmaCostumeItem.sortedCosmeticDataList(features.holders()).forEach(
                entry -> {
                    ItemStack result = ItemRegistry.UMA_COSTUME.get().getDefaultInstance();
                    result.set(DataComponentsTypeRegistry.COSMETIC_DATA, entry.key().location());
                    output.accept(result);
                }
        );
    }

    private static void fillBlankSoul(CreativeModeTab.ItemDisplayParameters features, CreativeModeTab.Output output) {
        UmaSoulItem.sortedUmaDataList(features.holders()).forEach(
                entry -> {
//                ItemStack result = ItemRegistry.BLANK_UMA_SOUL.get().getDefaultInstance();
//                result.getOrCreateTag().putString("name", entry.key().location().toString());
//                result.getOrCreateTag().putString("identifier", entry.value().getIdentifier().toString());
//                result.getOrCreateTag().putString("ranking", entry.value().getGachaRanking().toString().toLowerCase());
                    ItemStack result = FadedUmaSoulItem.genUmaSoul(entry.key().location(), entry.value());
                    output.accept(result);
                }
        );
    }

    private static void fillSupportCard(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {	
        SupportCardItem.sortedCardDataList(parameters.holders()).forEach(card -> {
            if (card.getKey().location().equals(ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "blank_card")))
                return;
            ItemStack result = SupportCard.init(card.getKey().location(), card.value());
            output.accept(result);
        });
        
    }

    private static void fillSkillBook(CreativeModeTab.Output output) {
        for (ResourceLocation skill : UmaSkillRegistry.REGISTRY.keySet()) {
            ItemStack result = ItemRegistry.SKILL_BOOK.get().getDefaultInstance();
            result.set(DataComponentsTypeRegistry.DATA_LOCATION, new DataLocation(skill));
            output.accept(result);
        }
    }

    private static void fillTicket(CreativeModeTab.ItemDisplayParameters features, CreativeModeTab.Output output) {
        UmaRaceTicketItem.sortedRaceList(features.holders()).forEachOrdered(race -> {
            if (race.key().location().equals(RaceRegistry.DEFAULT.location())) return;
            ItemStack result = ItemRegistry.UMA_RACE_TICKET.get().getDefaultInstance();
            result.set(DataComponentsTypeRegistry.RACE_DATA, race.key().location());
            output.accept(result);
        });
    }

    private static void fillShards(CreativeModeTab.ItemDisplayParameters features, CreativeModeTab.Output output) {
        UmaFactorRegistry.FACTORS.getEntries().stream()
                .filter(i -> i.get().getFactorType() != FactorType.UNIQUE)
                .filter(i -> i != UmaFactorRegistry.SKILL_FACTOR)
                .sorted(UmaFactor.UmaFactorComparator.INSTANCE)
                .map(DeferredHolder::get)
                .map(i -> new UmaFactorStack(i, i.getMaxLevel()))
                .map(i -> {
                    ItemStack result = ItemRegistry.FACTOR_SHARD.get().getDefaultInstance();
                    result.set(DataComponentsTypeRegistry.FACTOR_DATA, UmaFactorUtils.serializeData(List.of(i)));
                    return result;
                }).forEachOrdered(output::accept);
    }
}
