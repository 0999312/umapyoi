package net.tracen.umapyoi.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.api.UmapyoiAPI;

@Mixin(value = PlayerItemInHandLayer.class, priority = 10)
public class ItemInHandLayerMixin {
    // Correct coordinates by Mixin method
    @Inject(method = "renderArmWithItem", at = @At(value = "HEAD"))
    
    private void renderArmWithItemHead(LivingEntity pLivingEntity, ItemStack pItemStack,
            ItemDisplayContext pTransformType, HumanoidArm pArm, PoseStack pPoseStack, MultiBufferSource pBuffer,
            int pPackedLight, CallbackInfo ci) {
        if (!UmapyoiAPI.getRenderingUmaSoul(pLivingEntity).isEmpty()) {
            boolean leftArmFlag = pArm == HumanoidArm.LEFT;
            boolean slimArmFlag = false;
            // 1 / 16 = 0.0625D, right arm direction is the X positive direction
            PlayerItemInHandLayer<?, ?> layer = (PlayerItemInHandLayer<?, ?>) (Object) this;
            if (layer.getParentModel()instanceof PlayerModel<?> playerModel)
                if (playerModel.slim)
                    slimArmFlag = true;
            pPoseStack.translate((slimArmFlag ? 0.5 : 1) * (leftArmFlag ? -0.125D : 0.0625D), 0D, 0D);
        }
    }
}
