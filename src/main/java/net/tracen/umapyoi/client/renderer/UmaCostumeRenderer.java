package net.tracen.umapyoi.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.registry.cosmetics.CosmeticData;
import net.tracen.umapyoi.utils.ClientUtils;

@OnlyIn(Dist.CLIENT)
public class UmaCostumeRenderer extends AbstractSuitRenderer {

	@Override
	public ResourceLocation getModel(ItemStack stack) {
		ResourceLocation loc = stack.get(DataComponentsTypeRegistry.COSMETIC_DATA);

		CosmeticData data = ClientUtils.getClientCosmeticDataRegistry().get(loc);

		return data == null ? CosmeticData.DEFAULT_COSTUME.model() : data.model();
	}

	@Override
	public ResourceLocation getTexture(ItemStack stack, boolean tanned) {
		ResourceLocation loc = stack.get(DataComponentsTypeRegistry.COSMETIC_DATA);
		CosmeticData data = ClientUtils.getClientCosmeticDataRegistry().get(loc);
		return data == null ? CosmeticData.DEFAULT_COSTUME.getTexture(tanned) : data.getTexture(tanned);
	}

	@Override
	public ResourceLocation getFlatModel(ItemStack stack) {
		ResourceLocation loc = stack.get(DataComponentsTypeRegistry.COSMETIC_DATA);
		CosmeticData data = ClientUtils.getClientCosmeticDataRegistry().get(loc);
		return data == null ? CosmeticData.DEFAULT_COSTUME.flatModel().orElse(CosmeticData.DEFAULT_COSTUME.model())
				: data.flatModel().orElse(data.model());
	}

	@Override
	public ResourceLocation getFlatTexture(ItemStack stack, boolean tanned) {
		ResourceLocation loc = stack.get(DataComponentsTypeRegistry.COSMETIC_DATA);
		CosmeticData data = ClientUtils.getClientCosmeticDataRegistry().get(loc);
		return data == null ? CosmeticData.DEFAULT_COSTUME.getFlatTexture(tanned) : data.getFlatTexture(tanned);
	}

}
