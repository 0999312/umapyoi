package net.tracen.umapyoi.data;

import cn.mcmod_mmf.mmlib.data.AbstractItemModelProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.item.weapon.UmaWeaponItem;
import net.tracen.umapyoi.registry.races.RaceRegistry;
import net.tracen.umapyoi.utils.GachaRanking;
import net.tracen.umapyoi.utils.RaceRanking;

import java.io.PrintWriter;
import java.io.StringWriter;

public class UmapyoiItemModelProvider extends AbstractItemModelProvider {
    private final boolean ALLOW_CONTINUE_WITH_MISSING_TEXTURE = false;

    public UmapyoiItemModelProvider(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, Umapyoi.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ItemRegistry.ITEMS.getEntries().forEach((item) -> {
            if (item == ItemRegistry.HACHIMI_MID || item == ItemRegistry.UMA_SOUL_DISPLAY || item == ItemRegistry.HACHIMI_BIG)
                return;
            
            if (item.get() instanceof UmaWeaponItem)
                return;
            
            if (item == ItemRegistry.UMA_PEDESTAL) {
                withExistingParent(blockName(BlockRegistry.UMA_PEDESTAL),
                        new ResourceLocation("umapyoi:block/pedestal"));
                return;
            }
            
            if (item == ItemRegistry.SILVER_UMA_PEDESTAL) {
                withExistingParent(blockName(BlockRegistry.SILVER_UMA_PEDESTAL),
                        new ResourceLocation("umapyoi:block/silver_pedestal"));
                return;
            }

            if (item == ItemRegistry.SUPPORT_CARD) {
                ItemModelBuilder base = withExistingParent(ForgeRegistries.ITEMS.getKey(item.get()).getPath(), mcLoc("item/generated")).texture("layer0",
                        modLoc("item/" + ForgeRegistries.ITEMS.getKey(item.get()).getPath() + "_ssr"));
                for (GachaRanking rank: GachaRanking.values()) {
                    String path = ForgeRegistries.ITEMS.getKey(item.get()).getPath() + "_" + rank.name().toLowerCase();
                    withExistingParent(path, mcLoc("item/generated")).texture("layer0",
                            modLoc("item/" + path));
                    base.override()
                            .predicate(new ResourceLocation(Umapyoi.MODID, "ranking"), (float) rank.ordinal())
                            .model(getExistingFile(modLoc("item/" + path)))
                            .end();
                }
                return;
            }

            if (item == ItemRegistry.UMA_RACE_TICKET) {
                ItemModelBuilder base = withExistingParent(ForgeRegistries.ITEMS.getKey(item.get()).getPath(), mcLoc("item/generated")).texture("layer0",
                        modLoc("item/" + ForgeRegistries.ITEMS.getKey(item.get()).getPath() + "_common"));
                for (RaceRanking ranking: RaceRanking.values()) {
                    String path = ForgeRegistries.ITEMS.getKey(item.get()).getPath() + "_" + ranking.textureSuffix;
                    withExistingParent(path, mcLoc("item/generated")).texture("layer0",
                            modLoc("item/" + path));
                    base.override()
                            .predicate(new ResourceLocation(Umapyoi.MODID, "race_ranking"), (float) ranking.ordinal())
                            .model(getExistingFile(modLoc("item/" + path)))
                            .end();
                }
                String pathChampions = ForgeRegistries.ITEMS.getKey(item.get()).getPath() + "_champions";
                withExistingParent(pathChampions, mcLoc("item/generated")).texture("layer0",
                        modLoc("item/" + pathChampions));
                base.override()
                        .predicate(new ResourceLocation(Umapyoi.MODID, "race_ranking"), RaceRegistry.PREDICATE_CHALLENGES)
                        .model(getExistingFile(modLoc("item/" + pathChampions)))
                        .end();
                return;
            }
            
            if (item.get() instanceof BlockItem block
                    && !(item == ItemRegistry.THREE_GODDESS || item == ItemRegistry.UMA_STATUE)){
                try {
                    itemBlock(block::getBlock);
                } catch (IllegalStateException e) {
                    if (!ALLOW_CONTINUE_WITH_MISSING_TEXTURE) throw e;
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    Umapyoi.getLogger().error("====== EXCEPTION FROM BLOCK MODEL REGISTRATION ======");
                    for (String line: sw.toString().split("\n")) {
                        Umapyoi.getLogger().error(line);
                    }
                }
            }

            else {
                try {
                    normalItem(item);
                } catch (IllegalArgumentException e) {
                    if (!ALLOW_CONTINUE_WITH_MISSING_TEXTURE) throw e;
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    Umapyoi.getLogger().error("====== EXCEPTION FROM ITEM MODEL REGISTRATION ======");
                    for (String line: sw.toString().split("\n")) {
                        Umapyoi.getLogger().error(line);
                    }
                }
            }
        });
    }

}
