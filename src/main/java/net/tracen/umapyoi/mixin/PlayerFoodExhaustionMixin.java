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
                    double exhaustionMultipler = 1.2D - this.getExactProperty(umaSoul, StatusType.STAMINA, 0.85F);
                    player.getFoodData().addExhaustion((float) (pExhaustion * exhaustionMultipler));
                }
             }
            ci.cancel();
        }
    }
    
    public double getExactProperty(ItemStack stack, StatusType type, double limit) {
        var retiredValue = !(stack.has(DataComponentsTypeRegistry.UMADATA_TRAINING)) ? 1.0D : 0.25D;
        int rate = 0;
    	switch (type) {
			case SPEED -> rate = UmaSoulUtils.getPropertyRate(stack).speed();
			case STAMINA -> rate = UmaSoulUtils.getPropertyRate(stack).stamina();
			case STRENGTH -> rate = UmaSoulUtils.getPropertyRate(stack).strength();
			case GUTS -> rate = UmaSoulUtils.getPropertyRate(stack).guts();
			case WISDOM -> rate = UmaSoulUtils.getPropertyRate(stack).wisdom();
		}
        var propertyRate = 1.0D + (rate / 100.0D);
        var totalProperty = propertyPercentage(stack, type);
        return UmaSoulUtils.getMotivation(stack).getMultiplier() * limit * propertyRate * retiredValue * totalProperty;
    }

    private double propertyPercentage(ItemStack stack, StatusType type) {
    	int x = 0;
    	switch (type) {
			case SPEED -> x = UmaSoulUtils.getProperty(stack).speed();
			case STAMINA -> x = UmaSoulUtils.getProperty(stack).stamina();
			case STRENGTH -> x = UmaSoulUtils.getProperty(stack).strength();
			case GUTS -> x = UmaSoulUtils.getProperty(stack).guts();
			case WISDOM -> x = UmaSoulUtils.getProperty(stack).wisdom();
		}
        
        
        var statLimit = UmapyoiConfig.STAT_LIMIT_VALUE.get();
        var denominator = 1 + Math.pow(Math.E, 
                (x > statLimit ? (-0.125 * UmapyoiConfig.STAT_LIMIT_REDUCTION_RATE.get()) : -0.125) * 
                (x - statLimit));
        return 1 / denominator;
    }
}
