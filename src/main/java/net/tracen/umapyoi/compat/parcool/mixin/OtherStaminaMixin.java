package net.tracen.umapyoi.compat.parcool.mixin;

import com.alrex.parcool.common.capability.stamina.OtherStamina;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.registry.UmapyoiAttributesRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(value = OtherStamina.class, remap = false)
public abstract class OtherStaminaMixin {
    @Accessor(value = "EXHAUSTED_SPEED_MODIFIER_UUID", remap = false)
    public abstract UUID getExhaustedSpeedModifierUUID();

    @Accessor(value = "EXHAUSTED_SPEED_MODIFIER_NAME", remap = false)
    public abstract String getExhaustedSpeedModifierName();

    @Accessor(value = "player", remap = false)
    public abstract Player getPlayer();

    @Redirect(method = "tick", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;setSprinting(Z)V",
            remap = true
    ))
    public void setSprintingOverride(Player instance, boolean b) {
        if (UmapyoiConfig.ENABLE_PARCOOL_COMPATIBILITY.get() && UmapyoiAPI.getUmaSoul(instance).isEmpty()) {
            instance.setSprinting(b);
        }
    }

    @Inject(method = "tick", at = @At(value = "HEAD"), remap = false)
    public void atTick(CallbackInfo ci) {
        Player player = getPlayer();
        UUID uuid = getExhaustedSpeedModifierUUID();
        AttributeInstance instanceOfSprintSpeed = player.getAttribute(UmapyoiAttributesRegistry.SPRINT_SPEED.get());
        if (instanceOfSprintSpeed != null && instanceOfSprintSpeed.getModifier(uuid) != null) {
            instanceOfSprintSpeed.removeModifier(uuid);
        }
    }

    @Redirect(method = "tick", at = @At(value="INVOKE", target="Lnet/minecraft/world/entity/ai/attributes/AttributeInstance;addTransientModifier(Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;)V", remap = true))
    public void addTransientModifierOverride(AttributeInstance instance, AttributeModifier pModifier) {
        if (!UmapyoiConfig.ENABLE_PARCOOL_COMPATIBILITY.get()) {
            instance.addTransientModifier(pModifier);
            return;
        }
        Player player = getPlayer();
        UUID uuid = getExhaustedSpeedModifierUUID();
        AttributeInstance instanceOfSprintSpeed = player.getAttribute(UmapyoiAttributesRegistry.SPRINT_SPEED.get());
        AttributeInstance instanceOfMinusPenalty = player.getAttribute(UmapyoiAttributesRegistry.PARCOOL_EXHAUSTION_PENALTY.get());
        if (!player.isSprinting() || UmapyoiAPI.getUmaSoul(getPlayer()).isEmpty()) {
            instance.addTransientModifier(pModifier);
            return;
        }
        if (instanceOfMinusPenalty == null || instanceOfSprintSpeed == null) return;
        double tune = -Mth.lerp(Mth.clamp(instanceOfMinusPenalty.getValue(), 0d, 1d), UmapyoiConfig.MIN_EXHAUSTION_PENALTY.get(), UmapyoiConfig.MAX_EXHAUSTION_PENALTY.get());
        instanceOfSprintSpeed.addTransientModifier(new AttributeModifier(uuid, getExhaustedSpeedModifierName(), tune, AttributeModifier.Operation.MULTIPLY_TOTAL));
    }
}
