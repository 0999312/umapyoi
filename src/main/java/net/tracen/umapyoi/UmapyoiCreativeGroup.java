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
                if (card.getKey().location().equals(new ResourceLocation(Umapyoi.MODID, "blank_card")))
                    return;
                ItemStack result = ItemRegistry.SUPPORT_CARD.get().getDefaultInstance();
                result.getOrCreateTag().putString("support_card", card.getKey().location().toString());
                result.getOrCreateTag().putString("ranking", card.getValue().getGachaRanking().name().toLowerCase());
                output.accept(result);
            });
        }
    }

    private static void fillSkillBook(CreativeModeTab.Output output) {
        for (ResourceLocation skill : UmaSkillRegistry.REGISTRY.get().getKeys()) {
            ItemStack result = ItemRegistry.SKILL_BOOK.get().getDefaultInstance();
            result.getOrCreateTag().putString("skill", skill.toString());
            output.accept(result);
        }
    }
}
