package net.tracen.umapyoi.compat.epicFight.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.compat.epicFight.UmaModelTransformer;
import net.tracen.umapyoi.compat.epicFight.adapter.IArmatureOverride;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.client.model.SkinnedMesh;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.client.renderer.patched.entity.PatchedEntityRenderer;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import static net.tracen.umapyoi.compat.epicFight.EpicFightCompat.OnPreparingModel;

@Mixin(value = PatchedLivingEntityRenderer.class, remap = false)
public abstract class PatchedLivingEntityRendererMixin<E extends LivingEntity, T extends LivingEntityPatch<E>,
        M extends EntityModel<E>, R extends LivingEntityRenderer<E, M>, AM extends SkinnedMesh> extends
        PatchedEntityRenderer<E, T, R, AM>{

    @WrapOperation(method = "render(Lnet/minecraft/world/entity/LivingEntity;Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;Lnet/minecraft/client/renderer/MultiBufferSource;Lcom/mojang/blaze3d/vertex/PoseStack;IF)V",
    at = @At(value = "INVOKE", target = "Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;getArmature()Lyesman/epicfight/api/model/Armature;", remap = false), remap = false)
    public Armature InjectArmature(LivingEntityPatch<?> instance, Operation<Armature> original) {
        Armature val = original.call(instance);
        OnPreparingModel(instance.getOriginal(), (IArmatureOverride) val);
        return val;
    }

    @WrapOperation(method = "render(Lnet/minecraft/world/entity/LivingEntity;Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;Lnet/minecraft/client/renderer/MultiBufferSource;Lcom/mojang/blaze3d/vertex/PoseStack;IF)V",
    at = @At(value = "INVOKE", target = "Lyesman/epicfight/api/asset/AssetAccessor;get()Ljava/lang/Object;", remap = false), remap = false)
    public Object InjectMeshGet(AssetAccessor<SkinnedMesh> instance, Operation<SkinnedMesh> original, @Local Armature armature) {
        IArmatureOverride IArmature = (IArmatureOverride) armature;
        Object originalVal = original.call(instance);
        if (IArmature.umapyoi$getDoReplaceMesh()) {
            if (IArmature.umapyoi$getMeshReplace() == null) return originalVal;
            return IArmature.umapyoi$getMeshReplace();
        }
        return originalVal;
    }

    @WrapOperation(method = "render(Lnet/minecraft/world/entity/LivingEntity;Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;Lnet/minecraft/client/renderer/MultiBufferSource;Lcom/mojang/blaze3d/vertex/PoseStack;IF)V",
    at = @At(value = "INVOKE", target = "Lyesman/epicfight/client/renderer/patched/entity/PatchedLivingEntityRenderer;prepareModel(Lyesman/epicfight/api/client/model/SkinnedMesh;Lnet/minecraft/world/entity/LivingEntity;Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;)V", remap = false), remap = false)
    public void OvertakePrepareModel(PatchedLivingEntityRenderer<?,?,?,?,?> instance, SkinnedMesh mesh, LivingEntity entity, LivingEntityPatch<? extends LivingEntity> entitypatch, LivingEntityRenderer<LivingEntity, EntityModel<LivingEntity>> renderer, Operation<Void> original, @Local Armature armature) {
        original.call(instance, mesh, entity, entitypatch, renderer);
        if (mesh instanceof UmaModelTransformer.UmaMesh umaMesh) {
            if (umaMesh.getEmissiveMesh() != null) {
                original.call(instance, umaMesh.getEmissiveMesh(), entity, entitypatch, renderer);
            }
        }
    }
}
