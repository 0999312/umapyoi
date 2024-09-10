package net.tracen.umapyoi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.utils.UmaSoulUtils;
import net.tracen.umapyoi.utils.UmaStatusUtils.StatusType;


@Mixin(value = Player.class, priority = 10)
public class PlayerFoodExhaustionMixin {
    // Correct coordinates by Mixin method
    @Inject(method = "causeFoodExhaustion", at = @At(value = "HEAD"), cancellable = true)
    private void foodExhaustion(float pExhaustion, CallbackInfo ci) {
        Player player = (Player)(Object) this;
        ItemStack umaSoul = UmapyoiAPI.getUmaSoul(player);
        if (!umaSoul.isEmpty()) {
            if (!player.getAbilities().invulnerable) {
                if (!player.level().isClientSide()) {
                    float exhaustionMultipler = 1.2F - this.getExactProperty(umaSoul, StatusType.STAMINA.getId(), 0.85F);
                    player.getFoodData().addExhaustion(pExhaustion * exhaustionMultipler);
                }
             }
            ci.cancel();
        }
    }
    
    private float getExactProperty(ItemStack stack, int num, double limit) {
        var retiredValue = stack.has(DataComponentsTypeRegistry.UMADATA_TRAINING) ? 1.0D : 0.25D;
        var totalProperty = propertyPercentage(stack, num);
        return (float) (UmaSoulUtils.getMotivation(stack).getMultiplier() * limit * retiredValue * totalProperty);
    }

    private double propertyPercentage(ItemStack stack, int num) {
        var x = UmaSoulUtils.getProperty(stack)[num];
        var statLimit = UmapyoiConfig.STAT_LIMIT_VALUE.get();
        var denominator = 1 + Math.pow(Math.E, 
                (x > statLimit ? (-0.125 * UmapyoiConfig.STAT_LIMIT_REDUCTION_RATE.get()) : -0.125) * 
                (x - statLimit));
        return 1 / denominator;
    }
}
