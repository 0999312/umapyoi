package net.tracen.umapyoi.utils;

import java.util.List;

import org.joml.Quaternionf;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.training.card.SupportCard;
import net.tracen.umapyoi.registry.umadata.UmaData;

@OnlyIn(Dist.CLIENT)
public class ClientUtils {

    public static final ResourceLocation TRAINNING_SUIT = getModel("trainning_suit");
    public static final ResourceLocation SUMMER_UNIFORM = getModel("summer_uniform");
    public static final ResourceLocation WINTER_UNIFORM = getModel("winter_uniform");
    public static final ResourceLocation KINDERGARTEN_UNIFORM = getModel("kindergarten_uniform");

    public static final ResourceLocation TRAINNING_SUIT_FLAT = getModel("trainning_suit_flat");
    public static final ResourceLocation SUMMER_UNIFORM_FLAT = getModel("summer_uniform_flat");
    public static final ResourceLocation WINTER_UNIFORM_FLAT = getModel("winter_uniform_flat");

    public static final ResourceLocation THREE_GODDESS = getModel("three_goddesses");
    public static final ResourceLocation UMA_STATUES = getModel("uma_statue");
    
    public static final ResourceLocation SWIMSUIT = getModel("swimsuit");
    public static final ResourceLocation SWIMSUIT_FLAT = getModel("swimsuit_flat");
    
    public static ResourceLocation getModel(String name) {
        return getModel(Umapyoi.MODID, name);
    }

    public static ResourceLocation getModel(String modid, String name) {
        return ResourceLocation.fromNamespaceAndPath(modid, name);
    }

    public static ResourceLocation getTexture(ResourceLocation name) {
        return ResourceLocation.fromNamespaceAndPath(name.getNamespace(), "textures/model/" + name.getPath() + ".png");
    }
    
    public static ResourceLocation getEmissiveTexture(ResourceLocation name) {
        return ResourceLocation.fromNamespaceAndPath(name.getNamespace(), "textures/model/" + name.getPath() + "_emissive.png");
    }

    public static Registry<UmaData> getClientUmaDataRegistry() {
        return Minecraft.getInstance().getConnection().registryAccess().registryOrThrow(UmaData.REGISTRY_KEY);
    }

    public static Registry<SupportCard> getClientSupportCardRegistry() {
        return Minecraft.getInstance().getConnection().registryAccess().registryOrThrow(SupportCard.REGISTRY_KEY);
    }
    
    public static void addSummonParticle(Level pLevel, BlockPos pPos) {
        RandomSource pRand = pLevel.getRandom();
        List<BlockPos> posOffsets = BlockPos.betweenClosedStream(-1, -1, -1, 1, 0, 1).filter(pos -> {
            return Math.abs(pos.getX()) == 1 || Math.abs(pos.getZ()) == 1;
        }).map(BlockPos::immutable).toList();
        for (BlockPos spawnPos : posOffsets) {
            if (pRand.nextInt(32) == 0) {
                pLevel.addParticle(ParticleTypes.ENCHANT, (double) pPos.getX() + 0.5D, (double) pPos.getY() + 2.65D,
                        (double) pPos.getZ() + 0.5D, (double) ((float) spawnPos.getX() + pRand.nextFloat()) - 0.5D,
                        (double) ((float) spawnPos.getY() + 1D - pRand.nextFloat()),
                        (double) ((float) spawnPos.getZ() + pRand.nextFloat()) - 0.5D);
            }
        }
    }

    public static void renderModelInInventory(
    		GuiGraphics guiGraphic, 
    		int x, 
    		int y, 
    		int scale, 
    		Quaternionf pose, Model pModel,
            ResourceLocation texture) {

        guiGraphic.pose().pushPose();
        guiGraphic.pose().translate((double)x, (double)y, 50.0D);
        guiGraphic.pose().scale(scale, scale, -scale);
        guiGraphic.pose().mulPose(pose);
        MultiBufferSource.BufferSource buffersource = guiGraphic.bufferSource();
        VertexConsumer vertexconsumer = buffersource
                .getBuffer(RenderType.entityTranslucent(ClientUtils.getTexture(texture)));
        pModel.renderToBuffer(guiGraphic.pose(), vertexconsumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        guiGraphic.flush();
        guiGraphic.pose().popPose();

     }
}
