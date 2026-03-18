package net.tracen.umapyoi.data;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.stream.Stream;

import cn.mcmod_mmf.mmlib.data.AbstractItemModelProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.item.weapon.UmaWeaponItem;
import net.tracen.umapyoi.utils.GachaRanking;
import net.tracen.umapyoi.utils.RaceRanking;

public class UmapyoiItemModelProvider extends AbstractItemModelProvider {

	public UmapyoiItemModelProvider(PackOutput generator, ExistingFileHelper existingFileHelper) {
		super(generator, Umapyoi.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		ItemRegistry.ITEMS.getEntries().forEach((item) -> {
			if (item == ItemRegistry.HACHIMI_MID || item == ItemRegistry.UMA_SOUL_DISPLAY
					|| item == ItemRegistry.HACHIMI_BIG || item == ItemRegistry.GATE_DOOR || item == ItemRegistry.GATE)
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
				withExistingParent(basePath, mcLoc("item/generated")).texture("layer0",
						modLoc("item/" + basePath + "_ssr"));
				for (GachaRanking rank : GachaRanking.values()) {
					String path = basePath + "_" + rank.name().toLowerCase();
					String[] sep = path.split("/");
					StringWriter builder = new StringWriter();
					builder.write("item/");
					for (int i = 0; i < sep.length - 1; i++) {
						builder.write(sep[i]);
						builder.write('/');
					}
					builder.write("support_card/");
					builder.write(sep[sep.length - 1]);
					String finalPath = builder.toString();
					withExistingParent(finalPath, mcLoc("item/generated")).texture("layer0", modLoc("item/" + path));
				}
				return;
			}

			if (item == ItemRegistry.UMA_RACE_TICKET) {
				String basePath = ForgeRegistries.ITEMS.getKey(item.get()).getPath();
				withExistingParent(basePath, mcLoc("item/generated")).texture("layer0",
						modLoc("item/" + basePath + "_common"));

				Stream.concat(Arrays.stream(RaceRanking.values()).map(r -> r.textureSuffix), Stream.of("champions"))
						.forEachOrdered((suffix) -> {
							String path = basePath + "_" + suffix;
							String[] sep = path.split("/");
							StringWriter builder = new StringWriter();
							builder.write("item/");
							for (int i = 0; i < sep.length - 1; i++) {
								builder.write(sep[i]);
								builder.write('/');
							}
							builder.write("race_ticket/");
							builder.write(sep[sep.length - 1]);
							String finalPath = builder.toString();
							withExistingParent(finalPath, mcLoc("item/generated")).texture("layer0",
									modLoc("item/" + path));
						});
				return;
			}

			if (item.get() instanceof BlockItem block
					&& !(item == ItemRegistry.THREE_GODDESS || item == ItemRegistry.UMA_STATUE)) {
				itemBlock(block::getBlock);
			} else {
				normalItem(item);
			}
		});
	}

}
