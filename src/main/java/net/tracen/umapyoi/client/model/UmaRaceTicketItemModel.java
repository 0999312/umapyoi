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
import net.tracen.umapyoi.item.UmaRaceTicketItem;

import java.util.Optional;

public class UmaRaceTicketItemModel extends DynamicItemBakedModel {
    public UmaRaceTicketItemModel(BakedModel original, ModelBakery loader) {
        super(original, loader);
    }

    @Override
    public BakedModel resolveModel(BakedModel original, ItemStack stack, ClientLevel world, LivingEntity entity, int seed) {
        if (!stack.isEmpty()) {
            if (stack.getItem() == ItemRegistry.UMA_RACE_TICKET.get()) {
                return Optional.ofNullable(UmaRaceTicketItem.getRace(stack, world))
                        .map(race -> race.texturePredicateOverride == null
                                ? race.ranking.textureSuffix
                                : race.texturePredicateOverride)
                        .map(suffix -> new ModelResourceLocation(
                                ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "item/race_ticket/race_ticket_" + suffix), "standalone"
                        ))
                        .map(Minecraft.getInstance().getModelManager()::getModel)
                        .map(model -> model == Minecraft.getInstance().getModelManager().getMissingModel() ? null : model)
                        .orElse(this.getOriginalModel());
            }
        }
        return this.getOriginalModel();
    }
}
