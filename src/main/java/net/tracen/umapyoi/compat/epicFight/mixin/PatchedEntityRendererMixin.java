package net.tracen.umapyoi.compat.epicFight.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.LivingEntity;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.compat.epicFight.adapter.IArmatureOverride;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.client.renderer.patched.entity.PatchedEntityRenderer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import static net.tracen.umapyoi.compat.epicFight.EpicFightCompat.appendJointTransformation;

@Mixin(value = PatchedEntityRenderer.class, remap = false)
public class PatchedEntityRendererMixin {
    @Inject(method = "setArmaturePose", at = @At(value = "INVOKE", target = "Lyesman/epicfight/client/renderer/patched/entity/PatchedEntityRenderer;setJointTransforms(Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;Lyesman/epicfight/api/model/Armature;Lyesman/epicfight/api/animation/Pose;F)V", shift = At.Shift.AFTER, remap = false), remap = false)
    public void appendJointTransform(LivingEntityPatch<LivingEntity> entitypatch, Armature armature, float partialTicks, CallbackInfo ci, @Local Pose pose) {
        IArmatureOverride IArmature = (IArmatureOverride) armature;
        if (IArmature.umapyoi$getDoReplaceMesh() && (IArmature.umapyoi$getMeshReplace() != null)) {
            appendJointTransformation(entitypatch, armature, pose, partialTicks);
        }
    }
}
