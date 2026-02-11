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
import net.tracen.umapyoi.utils.GachaRanking;
import net.tracen.umapyoi.utils.RaceRanking;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public class UmapyoiItemModelProvider extends AbstractItemModelProvider {
    // DO NOT turn this on in production. DEVELOPMENT SWITCH ONLY
    private final boolean ALLOW_CONTINUE_WITH_MISSING_TEXTURE = true;

    public UmapyoiItemModelProvider(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, Umapyoi.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        if (ALLOW_CONTINUE_WITH_MISSING_TEXTURE) {
            Umapyoi.getLogger().warn("Warning: Current allow continue with missing texture is turned on");
            Umapyoi.getLogger().warn("This option shall only be turned on in DEVELOP ENVIRONMENT");
            Umapyoi.getLogger().warn("If you wish to continue, enter \"yes\" in standard input.");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter \"yes\" to continue: ");
            String line = scanner.nextLine();
            if (!Objects.equals(line, "yes")) {
                Umapyoi.getLogger().error("User has aborted the data generation.");
                throw new IllegalStateException("User aborted data generation.");
            }
            Umapyoi.getLogger().warn("User wish to proceed data generation. WE HAVE WARNED YOU.");
            Umapyoi.getLogger().warn("One last time: DO NOT proceed to production in this mode.");
        }
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
                String basePath = ForgeRegistries.ITEMS.getKey(item.get()).getPath();
                ItemModelBuilder base = withExistingParent(basePath, mcLoc("item/generated")).texture("layer0",
                        modLoc("item/" + basePath + "_ssr"));
                for (GachaRanking rank: GachaRanking.values()) {
                    String path = basePath + "_" + rank.name().toLowerCase();
                    String[] sep = path.split("/");
                    StringWriter builder = new StringWriter();
                    builder.write("item/");
                    for (int i = 0; i < sep.length - 1; i++){
                        builder.write(sep[i]);
                        builder.write('/');
                    }
                    builder.write("support_card/");
                    builder.write(sep[sep.length - 1]);
                    String finalPath = builder.toString();
                    withExistingParent(finalPath, mcLoc("item/generated")).texture("layer0",
                            modLoc("item/" + path));
                }
                return;
            }

            if (item == ItemRegistry.UMA_RACE_TICKET) {
                String basePath = ForgeRegistries.ITEMS.getKey(item.get()).getPath();
                withExistingParent(basePath, mcLoc("item/generated")).texture("layer0",
                        modLoc("item/" + basePath + "_common"));

                Stream.concat(
                        Arrays.stream(RaceRanking.values()).map(r -> r.textureSuffix),
                        Stream.of("champions")
                ).forEachOrdered((suffix) -> {
                    String path = basePath + "_" + suffix;
                    String[] sep = path.split("/");
                    StringWriter builder = new StringWriter();
                    builder.write("item/");
                    for (int i = 0; i < sep.length - 1; i++){
                        builder.write(sep[i]);
                        builder.write('/');
                    }
                    builder.write("race_ticket/");
                    builder.write(sep[sep.length - 1]);
                    String finalPath = builder.toString();
                    Umapyoi.getLogger().debug("path: {}, reg: {}", path, finalPath);
                    withExistingParent(finalPath, mcLoc("item/generated")).texture("layer0",
                            modLoc("item/" + path));
                });
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
