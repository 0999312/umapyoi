package net.tracen.umapyoi;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.item.SupportCardItem;
import net.tracen.umapyoi.item.UmaSoulItem;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.item.data.DataLocation;
import net.tracen.umapyoi.registry.UmaFactorRegistry;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.factors.FactorType;
import net.tracen.umapyoi.registry.factors.UmaFactorStack;
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
                            if (item == ItemRegistry.UMA_FACTOR_ITEM) {
                                fillFactorContainer(output);
                                return;
                            }
                            if (item == ItemRegistry.UMA_SOUL) {
                                fillUmaSoul(output);
                                return;
                            }
                            if (item == ItemRegistry.SUPPORT_CARD) {
                                fillSupportCard(output);
                                return;
                            }
                            if (item == ItemRegistry.SKILL_BOOK) {
                                fillSkillBook(output);
                                return;
                            }
                            output.accept(item.get());
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

    private static void fillUmaSoul(CreativeModeTab.Output output) {
        if (Minecraft.getInstance().getConnection() != null) {
            UmaSoulItem.sortedUmaDataList().forEach(
                    entry -> {
                        ItemStack initUmaSoul = UmaSoulUtils.initUmaSoul(ItemRegistry.UMA_SOUL.get().getDefaultInstance(),
                            entry.getKey().location(), entry.getValue());
                        UmaSoulUtils.setPhysique(initUmaSoul, 5);
                        output.accept(initUmaSoul);
                        }
                    );
        }
    }

    private static void fillSupportCard(CreativeModeTab.Output output) {
        if (Minecraft.getInstance().getConnection() != null) {
            SupportCardItem.sortedCardDataList().forEach(card -> {
                if (card.getKey().location().equals(ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "blank_card")))
                    return;
                ItemStack result = SupportCard.init(card.getKey().location(), card.getValue());
                output.accept(result);
            });
        }
    }

    private static void fillSkillBook(CreativeModeTab.Output output) {
        for (ResourceLocation skill : UmaSkillRegistry.REGISTRY.keySet()) {
            ItemStack result = ItemRegistry.SKILL_BOOK.get().getDefaultInstance();
            result.set(DataComponentsTypeRegistry.DATA_LOCATION, new DataLocation(skill));
            output.accept(result);
        }
    }
}
