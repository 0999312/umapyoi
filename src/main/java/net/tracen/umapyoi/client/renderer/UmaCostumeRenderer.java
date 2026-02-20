package net.tracen.umapyoi.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tracen.umapyoi.registry.cosmetics.CosmeticData;
import net.tracen.umapyoi.utils.ClientUtils;

@OnlyIn(Dist.CLIENT)
public class UmaCostumeRenderer extends AbstractSuitRenderer {

	@Override
	public ResourceLocation getModel(ItemStack stack) {
		ResourceLocation loc = ResourceLocation.tryParse(stack.getOrCreateTag().getString("cosmetic"));

		CosmeticData data = ClientUtils.getClientCosmeticDataRegistry().get(loc);

		return data == null ? CosmeticData.DEFAULT_COSTUME.model() : data.model();
	}

	@Override
	public ResourceLocation getTexture(ItemStack stack, boolean tanned) {
		ResourceLocation loc = ResourceLocation.tryParse(stack.getOrCreateTag().getString("cosmetic"));
		CosmeticData data = ClientUtils.getClientCosmeticDataRegistry().get(loc);
		return data == null ? CosmeticData.DEFAULT_COSTUME.getTexture(tanned) : data.getTexture(tanned);
	}

	@Override
	public ResourceLocation getFlatModel(ItemStack stack) {
		ResourceLocation loc = ResourceLocation.tryParse(stack.getOrCreateTag().getString("cosmetic"));
		CosmeticData data = ClientUtils.getClientCosmeticDataRegistry().get(loc);
		return data == null ? CosmeticData.DEFAULT_COSTUME.flatModel().orElse(CosmeticData.DEFAULT_COSTUME.model())
				: data.flatModel().orElse(data.model());
	}

	@Override
	public ResourceLocation getFlatTexture(ItemStack stack, boolean tanned) {
		ResourceLocation loc = ResourceLocation.tryParse(stack.getOrCreateTag().getString("cosmetic"));
		CosmeticData data = ClientUtils.getClientCosmeticDataRegistry().get(loc);
		return data == null ? CosmeticData.DEFAULT_COSTUME.getFlatTexture(tanned) : data.getFlatTexture(tanned);
	}

}
