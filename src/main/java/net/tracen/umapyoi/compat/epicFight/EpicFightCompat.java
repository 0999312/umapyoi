package net.tracen.umapyoi.compat.epicFight;

import cn.mcmod_mmf.mmlib.utils.ClientUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.client.model.UmaPlayerModel;
import net.tracen.umapyoi.compat.epicFight.adapter.IArmatureOverride;
import net.tracen.umapyoi.events.client.RenderingUmaSoulEvent;
import net.tracen.umapyoi.utils.ClientUtils;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.config.ClientConfig;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;

import javax.annotation.Nullable;
import java.util.HashMap;

import static net.tracen.umapyoi.client.renderer.UmaSoulRenderer.getRenderTarget;
import static net.tracen.umapyoi.compat.epicFight.UmaModelTransformer.transformModel;

public class EpicFightCompat {
    public static final HashMap<ResourceLocation, UmaModelTransformer.UmaMesh> MESH_MAP = new HashMap<>();

    public static class Client {
        @SubscribeEvent
        public static void OnModelsReload(RegisterClientReloadListenersEvent event) {
            MESH_MAP.clear();
        }
    }

    @SubscribeEvent
    public static void RenderingUmaSoulEventListen(RenderingUmaSoulEvent.Pre pre) {
        if (!ClientConfig.enableOriginalModel) pre.setCanceled(true);
    }

    @Nullable
    public static UmaModelTransformer.UmaMesh OnPreparingModel(LivingEntity entity, IArmatureOverride armatureInterface) {
        armatureInterface.umapyoi$setDoReplaceMesh(false);
        if (entity == null) return null;
        ItemStack stack = UmapyoiAPI.getRenderingUmaSoul(entity);
        if (stack.isEmpty()) return null;

        UmaPlayerModel<LivingEntity> baseModel = new UmaPlayerModel<>();

        ResourceLocation renderTarget = getRenderTarget(stack, entity);

        armatureInterface.umapyoi$setDoReplaceMesh(true);
        armatureInterface.umapyoi$setTexture(ClientUtils.getTexture(renderTarget));
        UmaModelTransformer.UmaMesh mesh = MESH_MAP.computeIfAbsent(renderTarget, (target) -> {
            var pojo = ClientUtil.getModelPOJO(renderTarget);
            baseModel.loadModel(pojo);
            return transformModel(baseModel, false);
        });
        armatureInterface.umapyoi$setMeshReplace(mesh);
        if (mesh.getEmissiveMesh() != null) {
            armatureInterface.umapyoi$setEmissiveTexture(ClientUtils.getEmissiveTexture(renderTarget));
        }
        return mesh;
    }

    public static void appendJointTransformation(EntityPatch<LivingEntity> entityPatch, Armature armature, Pose pose, float partialTick) {
        pose.putJointData("tail", pose.get("body"));
        pose.putJointData("hat", pose.get("head"));
    }
}
