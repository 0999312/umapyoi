package net.tracen.umapyoi.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import cn.mcmod_mmf.mmlib.client.model.SimpleBedrockModel;
import cn.mcmod_mmf.mmlib.client.model.bedrock.BedrockPart;
import cn.mcmod_mmf.mmlib.utils.ClientUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.block.UmaStatueBlock;
import net.tracen.umapyoi.block.entity.UmaStatueBlockEntity;
import net.tracen.umapyoi.client.renderer.AbstractSuitRenderer;
import net.tracen.umapyoi.data.tag.UmapyoiCostumeDataTags;
import net.tracen.umapyoi.data.tag.UmapyoiUmaDataTags;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.item.UmaCostumeItem;
import net.tracen.umapyoi.item.UmaSuitItem;
import net.tracen.umapyoi.registry.cosmetics.CosmeticData;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.utils.ClientUtils;
import net.tracen.umapyoi.utils.UmaSoulUtils;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.Optional;

import static net.tracen.umapyoi.client.renderer.UmaSoulRenderer.getSuitTarget;

public class UmaStatuesBlockRender implements BlockEntityRenderer<UmaStatueBlockEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Umapyoi.MODID, "textures/model/three_goddesses.png");
    private final SimpleBedrockModel model;
    private final SimpleBedrockModel costumeModel;

    public UmaStatuesBlockRender(BlockEntityRendererProvider.Context context) {
        model = new SimpleBedrockModel();
        costumeModel = new SimpleBedrockModel();
    }
    
    

    @Override
    public void render(UmaStatueBlockEntity tileEntity, float partialTicks, PoseStack poseStack,
            MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        Level world = tileEntity.getLevel();
        boolean flag = world != null;
        BlockState blockstate = flag ? tileEntity.getBlockState()
                : BlockRegistry.UMA_STATUES.get().defaultBlockState();
        if (blockstate.getBlock() instanceof UmaStatueBlock) {
            Direction direction = tileEntity.getBlockState().getValue(UmaStatueBlock.FACING);
            renderModel(tileEntity, direction, poseStack, buffer, combinedLight, combinedOverlay);
        }
    }

    private static ResourceLocation getRenderTarget(UmaStatueBlockEntity tileEntity) {
        ItemStack item = tileEntity.getStoredItem();
        if (!tileEntity.isCostumeEmpty() && (tileEntity.getCostume().getItem() instanceof UmaSuitItem || tileEntity.getCostume().getItem() instanceof UmaCostumeItem)) {
            return getSuitTarget(item, ClientUtils.getClientUmaDataRegistry()
                    .getHolder(ResourceKey.create(UmaData.REGISTRY_KEY, UmaSoulUtils.getName(item)))
                    .get().is(UmapyoiUmaDataTags.ALTER_MODEL));
        }
        return UmaSoulUtils.getName(item);
    }

    private void renderModel(UmaStatueBlockEntity tileEntity, Direction direction, PoseStack poseStack,
            MultiBufferSource buffer, int combinedLight, int combinedOverlay) {

        poseStack.pushPose();
        poseStack.translate(0.5D, 1.5D, 0.5D);

        poseStack.mulPose(Axis.YN.rotationDegrees(direction.toYRot()));
        poseStack.mulPose(Axis.XP.rotationDegrees(180));

        ItemStack item = tileEntity.getStoredItem();
        ResourceLocation target = getRenderTarget(tileEntity);

        var pojo = (tileEntity.isEmpty() || !item.is(ItemRegistry.UMA_SOUL.get())) ?
                ClientUtil.getModelPOJO(ClientUtils.UMA_STATUES) :
                ClientUtil.getModelPOJO(target);

        if (model.needRefresh(pojo))
            model.loadModel(pojo);

        var leftArm = model.getChild("left_arm") != null ? model.getChild("left_arm") : new BedrockPart();
        var rightArm = model.getChild("right_arm") != null ? model.getChild("right_arm") : new BedrockPart();
        leftArm.zRot = ClientUtil.convertRotation(-5);
        rightArm.zRot = ClientUtil.convertRotation(5);

        boolean doRenderSuit = false;

        ResourceLocation costumeResource = null;

        ItemStack costumeItem = tileEntity.getCostume();
        if (item.is(ItemRegistry.UMA_SOUL.get()) && !tileEntity.isCostumeEmpty() && !tileEntity.isEmpty()) {
            Optional<ICurioRenderer> interfaceRenderer = CuriosRendererRegistry.getRenderer(costumeItem.getItem());
            if (interfaceRenderer.isEmpty()) return;
            ICurioRenderer ifExistRenderer = interfaceRenderer.get();
            if (ifExistRenderer instanceof AbstractSuitRenderer renderer) {
                boolean is_flat_chest = ClientUtils.isFlatUmamusume(item);
                boolean is_tanned = ClientUtils.isTannedSkin(item);

                var suitPojo = ClientUtil.getModelPOJO(is_flat_chest ? renderer.getFlatModel(costumeItem) : renderer.getModel(costumeItem));
                if (costumeModel.needRefresh(suitPojo)) costumeModel.loadModel(suitPojo);
                var leftArmCostume = costumeModel.getChild("left_arm") != null ? costumeModel.getChild("left_arm") : new BedrockPart();
                var rightArmCostume = costumeModel.getChild("right_arm") != null ? costumeModel.getChild("right_arm") : new BedrockPart();
                leftArmCostume.zRot = ClientUtil.convertRotation(-5);
                rightArmCostume.zRot = ClientUtil.convertRotation(5);
                Optional.ofNullable(costumeModel.getChild("head")).ifPresent(m -> m.visible = false);
                Optional.ofNullable(costumeModel.getChild("tail")).ifPresent(m -> m.visible = false);
                costumeResource = is_flat_chest ? renderer.getFlatTexture(costumeItem, is_tanned) : renderer.getTexture(costumeItem, is_tanned);
                doRenderSuit = true;
            }
        }

        if (doRenderSuit) {
            setAllVisible(false);
            if (!costumeModel.getChild("hat").isEmpty()) {
                ResourceLocation loc = UmaCostumeItem.getCostumeID(costumeItem);
                var costumeData = ClientUtils.getClientCosmeticDataRegistry()
                        .getHolder(ResourceKey.create(CosmeticData.REGISTRY_KEY, loc));
                if (costumeData.get().is(UmapyoiCostumeDataTags.HAT_HIDEHAIR)) {
                    model.getModelMap().entrySet().stream()
                            .filter(i -> i.getKey().startsWith("long_hair_") || i.getKey().equals("long_hair"))
                            .forEach(i -> i.getValue().visible = false);
                } else {
                    model.getModelMap().entrySet().stream()
                            .filter(i -> i.getKey().startsWith("long_hair_") || i.getKey().equals("long_hair"))
                            .forEach(i -> i.getValue().visible = true);
                }
                Optional.ofNullable(model.getChild("hat")).ifPresent(v -> v.visible = false);
            } else {
                Optional.ofNullable(model.getChild("hat")).ifPresent(v -> v.visible = true);
            }
        } else {
            setAllVisible(true);
            model.getModelMap().entrySet().stream()
                    .filter(i -> i.getKey().startsWith("long_hair_") || i.getKey().equals("long_hair"))
                    .forEach(i -> i.getValue().visible = true);
        }
        
        VertexConsumer vertexConsumer = buffer
                .getBuffer(RenderType.entityTranslucent(tileEntity.isEmpty() ? TEXTURE : ClientUtils.getTexture(target)));
        model.renderToBuffer(poseStack, vertexConsumer, combinedLight, combinedOverlay, 1, 1, 1, 1);
        
        if(model.isEmissive()) {
            VertexConsumer emissiveConsumer = buffer
                    .getBuffer(RenderType.entityTranslucentEmissive(tileEntity.isEmpty() ? TEXTURE : ClientUtils.getEmissiveTexture(target)));
            model.renderEmissiveParts(poseStack, emissiveConsumer, combinedLight, combinedOverlay, 1, 1, 1, 1);
        }

        if (doRenderSuit) {
            VertexConsumer vertexConsumerSuit = buffer.getBuffer(RenderType.entityTranslucentCull(costumeResource));
            costumeModel.renderToBuffer(poseStack, vertexConsumerSuit, combinedLight, combinedOverlay, 1, 1, 1, 1);
        }

        poseStack.popPose();
    }

    private void setAllVisible(boolean visible) {
        Optional.ofNullable(model.getChild("right_arm")).ifPresent(i -> i.visible = visible);
        Optional.ofNullable(model.getChild("left_arm")).ifPresent(i -> i.visible = visible);
        Optional.ofNullable(model.getChild("right_leg")).ifPresent(i -> i.visible = visible);
        Optional.ofNullable(model.getChild("left_leg")).ifPresent(i -> i.visible = visible);
        Optional.ofNullable(model.getChild("cape")).ifPresent(i -> i.visible = visible);
        Optional.ofNullable(model.getChild("body")).ifPresent(i -> i.visible = visible);
        Optional.ofNullable(model.getChild("hat")).ifPresent(v -> v.visible = visible);
    }

}
