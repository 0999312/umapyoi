package net.tracen.umapyoi.compat.parcool.mixin;

import com.alrex.parcool.common.action.ActionProcessor;
import net.minecraft.world.entity.player.Player;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.api.UmapyoiAPI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ActionProcessor.class, remap = false)
public class ActionProcessorMixin {
    @Redirect(method = "onTick", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;setSprinting(Z)V"),
            remap = true
    )
    public void setSprintOnTick(Player instance, boolean b) {
        if ((!UmapyoiConfig.ENABLE_PARCOOL_COMPATIBILITY.get()) || UmapyoiAPI.getUmaSoul(instance).isEmpty()) {
            instance.setSprinting(b);
        }
    }
}
