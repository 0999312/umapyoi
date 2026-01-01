package net.tracen.umapyoi;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.tracen.umapyoi.item.*;
import net.tracen.umapyoi.registry.UmaFactorRegistry;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.factors.FactorType;
import net.tracen.umapyoi.registry.factors.UmaFactor;
import net.tracen.umapyoi.registry.factors.UmaFactorStack;
import net.tracen.umapyoi.registry.races.Race;
import net.tracen.umapyoi.registry.races.RaceRegistry;
import net.tracen.umapyoi.utils.RaceRanking;
import net.tracen.umapyoi.utils.UmaFactorUtils;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class UmapyoiCreativeGroup {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, Umapyoi.MODID);

    public static final RegistryObject<CreativeModeTab> UMAPYOI_ITEMS = CREATIVE_MODE_TABS.register("umapyoi",
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
                            if (item == ItemRegistry.UMA_RACING_SLIP) {
                                return;
                            }
                            output.accept(item.get());
                        });
                    }).build());
    
    public static final RegistryObject<CreativeModeTab> UMAPYOI_BLANK_SOULS = CREATIVE_MODE_TABS.register("umapyoi_blank_souls",
            () -> CreativeModeTab.builder().icon(ItemRegistry.BLANK_UMA_SOUL.get()::getDefaultInstance)
                    .title(Component.translatable("itemGroup.umapyoi.blank_souls")).displayItems((features, output) -> {
                        ItemRegistry.ITEMS.getEntries().forEach(item -> {
                            if (item == ItemRegistry.BLANK_UMA_SOUL) {
                            	fillBlankSoul(features, output);
                                return;
                            }
                        });
                    }).build());
    
    public static final RegistryObject<CreativeModeTab> UMAPYOI_SOULS = CREATIVE_MODE_TABS.register("umapyoi_souls",
            () -> CreativeModeTab.builder().icon(ItemRegistry.UMA_SOUL_DISPLAY.get()::getDefaultInstance)
                    .title(Component.translatable("itemGroup.umapyoi.souls")).displayItems((features, output) -> {
                        ItemRegistry.ITEMS.getEntries().forEach(item -> {
                            if (item == ItemRegistry.UMA_SOUL) {
                                fillUmaSoul(features, output);
                                return;
                            }
                        });
                    }).build());
    
    public static final RegistryObject<CreativeModeTab> UMAPYOI_CARDS = CREATIVE_MODE_TABS.register("umapyoi_cards",
            () -> CreativeModeTab.builder().icon(ItemRegistry.SUPPORT_CARD.get()::getDefaultInstance)
                    .title(Component.translatable("itemGroup.umapyoi.cards")).displayItems((features, output) -> {
                        ItemRegistry.ITEMS.getEntries().forEach(item -> {
                            if (item == ItemRegistry.SUPPORT_CARD) {
                                fillSupportCard(features, output);
                                return;
                            }
                        });
                    }).build());

    public static final RegistryObject<CreativeModeTab> UMAPYOI_RACESLIPS = CREATIVE_MODE_TABS.register("umapyoi_raceslips",
            () -> CreativeModeTab.builder().icon(ItemRegistry.UMA_RACING_SLIP.get()::getDefaultInstance)
                    .title(Component.translatable("itemGroup.umapyoi.race_slips")).displayItems((features, output) -> {
                        ItemRegistry.ITEMS.getEntries().forEach(item -> {
                            if (item == ItemRegistry.UMA_RACING_SLIP) {
                                fillSlip(features, output);
                                return;
                            }
                        });
                    }).build());

    private static void fillFactorContainer(CreativeModeTab.Output output) {
        for (UmaFactor factor : UmaFactorRegistry.REGISTRY.get().getValues()) {
            if (factor == UmaFactorRegistry.SKILL_FACTOR.get() || factor.getFactorType() == FactorType.UNIQUE)
                continue;
            List<UmaFactorStack> stackList = Lists.newArrayList(new UmaFactorStack(factor, 1));
            ItemStack result = ItemRegistry.UMA_FACTOR_ITEM.get().getDefaultInstance();
            result.getOrCreateTag().putString("name", "umapyoi:common_uma");
            result.getOrCreateTag().put("factors", UmaFactorUtils.serializeNBT(stackList));
            output.accept(result);
        }
    }

    private static void fillUmaSoul(CreativeModeTab.ItemDisplayParameters features, CreativeModeTab.Output output) {
        UmaSoulItem.sortedUmaDataList(features.holders()).forEach(
            entry -> {
                ItemStack initUmaSoul = UmaSoulUtils.initUmaSoul(ItemRegistry.UMA_SOUL.get().getDefaultInstance(),
                    entry.key().location(), entry.value());
                UmaSoulUtils.setPhysique(initUmaSoul, 5);
                output.accept(initUmaSoul);
            }
        );
    }
    
    private static void fillCostume(CreativeModeTab.ItemDisplayParameters features, CreativeModeTab.Output output) {
        UmaCostumeItem.sortedCosmeticDataList(features.holders()).forEach(
            entry -> {	
                ItemStack result = ItemRegistry.UMA_COSTUME.get().getDefaultInstance();
                result.getOrCreateTag().putString("cosmetic", entry.key().location().toString());
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
                ItemStack result = FadedUmaSoulItem.genUmaSoul(entry.key().location().toString(), entry.value());
                output.accept(result);
            }
        );
    }

    private static void fillSupportCard(CreativeModeTab.ItemDisplayParameters features, CreativeModeTab.Output output) {
        SupportCardItem.sortedCardDataList(features.holders()).forEach(card -> {
            if (card.key().location().equals(new ResourceLocation(Umapyoi.MODID, "blank_card")))
                return;
            ItemStack result = ItemRegistry.SUPPORT_CARD.get().getDefaultInstance();
            result.getOrCreateTag().putString("support_card", card.key().location().toString());
            result.getOrCreateTag().putString("ranking", card.value().getGachaRanking().name().toLowerCase());
            output.accept(result);
        });
        
    }

    private static void fillSkillBook(CreativeModeTab.Output output) {
        for (ResourceLocation skill : UmaSkillRegistry.REGISTRY.get().getKeys()) {
            ItemStack result = ItemRegistry.SKILL_BOOK.get().getDefaultInstance();
            result.getOrCreateTag().putString("skill", skill.toString());
            output.accept(result);
        }
    }

    private static class RaceComparator implements Comparator<Map.Entry<ResourceKey<Race>, Race>> {
        @Override
        public int compare(Map.Entry<ResourceKey<Race>, Race> o1, Map.Entry<ResourceKey<Race>, Race> o2) {
            RaceRanking leftRanking = o1.getValue().ranking;
            RaceRanking rightRanking = o2.getValue().ranking;
            if (leftRanking == rightRanking) {
                int leftDistance = o1.getValue().length;
                int rightDistance = o2.getValue().length;
                if (leftDistance == rightDistance) {
                    String leftName = o1.getKey().location().toString();
                    String rightName = o2.getKey().location().toString();
                    return leftName.compareToIgnoreCase(rightName);
                }
                return leftDistance - rightDistance;
            }
            return leftRanking.compareTo(rightRanking);
        }
    }

    private static final RaceComparator RACE_COMPARATOR = new RaceComparator();

    private static void fillSlip(CreativeModeTab.ItemDisplayParameters features, CreativeModeTab.Output output) {
        RaceRegistry.REGISTRY.get().getEntries().stream().sorted(RACE_COMPARATOR).forEachOrdered(race -> {
            Umapyoi.getLogger().info("{}", race.getKey());
            if (race.getKey().location().equals(RaceRegistry.DEFAULT.getId())) return;
            ItemStack result = ItemRegistry.UMA_RACING_SLIP.get().getDefaultInstance();
            result.getOrCreateTag().putString("race", race.getKey().location().toString());
            result.getOrCreateTag().putString("race_ranking", race.getValue().ranking.name().toLowerCase());
            result.getOrCreateTag().putInt("race_length", race.getValue().length);
            output.accept(result);
        });
    }
}
