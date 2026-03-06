package net.tracen.umapyoi.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;

public class SupportCardItemModel extends DynamicItemBakedModel {
    public SupportCardItemModel(BakedModel original, ModelBakery loader) {
        super(original, loader);
    }

    @Override
    public BakedModel resolveModel(BakedModel original, ItemStack stack, ClientLevel world, LivingEntity entity, int seed) {
        if (!stack.isEmpty()) {
            if (stack.getItem() == ItemRegistry.SUPPORT_CARD.get()) {
                var tag = stack.getComponents().get(DataComponentsTypeRegistry.GACHA_RANKING.get());
                if (tag == null) return this.getOriginalModel();
                ModelResourceLocation modelPath = new ModelResourceLocation(
                        ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "item/support_card/support_card_" +
                                tag.ranking().name().toLowerCase()),
                        "standalone"
                );
                BakedModel model = Minecraft.getInstance().getModelManager().getModel(modelPath);
                if (model == Minecraft.getInstance().getModelManager().getMissingModel()) return this.getOriginalModel();
                return model;
            }
        }
        return this.getOriginalModel();
    }
}
