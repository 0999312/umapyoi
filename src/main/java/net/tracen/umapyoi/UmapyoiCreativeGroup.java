package net.tracen.umapyoi;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.tracen.umapyoi.item.FadedUmaSoulItem;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.item.SupportCardItem;
import net.tracen.umapyoi.item.UmaCostumeItem;
import net.tracen.umapyoi.item.UmaSoulItem;
import net.tracen.umapyoi.registry.UmaFactorRegistry;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.factors.FactorType;
import net.tracen.umapyoi.registry.factors.UmaFactor;
import net.tracen.umapyoi.registry.factors.UmaFactorStack;
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
}
