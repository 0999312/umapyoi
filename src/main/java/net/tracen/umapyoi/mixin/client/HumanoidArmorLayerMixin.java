package net.tracen.umapyoi.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.data.tag.UmapyoiItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidArmorLayer.class)
public class HumanoidArmorLayerMixin {
    @Inject(method = "renderArmorPiece", at = @At(value = "HEAD"), cancellable = true)
    private void renderArmorPiece(PoseStack poseStack, MultiBufferSource buffer, LivingEntity entity,
                                  EquipmentSlot slot, int packedLight, HumanoidModel<LivingEntity> model,
                                  CallbackInfo ci) {
        if (UmapyoiConfig.VANILLA_ARMOR_RENDER.get() || !(entity instanceof Player))
            return;

        ItemStack umaSoul = UmapyoiAPI.getRenderingUmaSoul(entity);
        if (umaSoul.isEmpty()) return;

        ItemStack itemBySlot = entity.getItemBySlot(slot);
        if (itemBySlot.getItem() instanceof ArmorItem && !itemBySlot.is(UmapyoiItemTags.SHOULD_RENDER)) {
            ci.cancel();
        }
    }
}
