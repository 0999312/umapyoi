package net.tracen.umapyoi.client.renderer.blockentity;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import cn.mcmod_mmf.mmlib.client.model.pojo.BedrockModelPOJO;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Axis;
import cn.mcmod_mmf.mmlib.client.model.SimpleBedrockModel;
import cn.mcmod_mmf.mmlib.utils.ClientUtil;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.block.UmaStatueBlock;
import net.tracen.umapyoi.block.entity.UmaStatueBlockEntity;
import net.tracen.umapyoi.client.model.UmaPlayerModel;
import net.tracen.umapyoi.client.renderer.SwimsuitRenderer;
import net.tracen.umapyoi.client.renderer.TrainningSuitRenderer;
import net.tracen.umapyoi.client.renderer.UmaUniformRenderer;
import net.tracen.umapyoi.data.tag.UmapyoiUmaDataTags;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.item.UmaSuitItem;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.utils.ClientUtils;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class UmaStatuesBlockRender implements BlockEntityRenderer<UmaStatueBlockEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Umapyoi.MODID, "textures/model/three_goddesses.png");
    private final TrainningSuitRenderer trainningSuitRenderer = new TrainningSuitRenderer();
    private final SwimsuitRenderer swimsuitRenderer = new SwimsuitRenderer();
    private final UmaUniformRenderer.SummerUniformRenderer summerUniformRenderer = new UmaUniformRenderer.SummerUniformRenderer();
    private final UmaUniformRenderer.WinterUniformRenderer winterUniformRenderer = new UmaUniformRenderer.WinterUniformRenderer();

    public UmaStatuesBlockRender(BlockEntityRendererProvider.Context context) {
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

    private void renderModel(UmaStatueBlockEntity tileEntity, Direction direction, PoseStack poseStack,
            MultiBufferSource buffer, int combinedLight, int combinedOverlay) {

        var model = tileEntity.model;
        var suitModel = tileEntity.suitModel;

        poseStack.pushPose();
        poseStack.translate(0.5D, 1.5D, 0.5D);

        poseStack.translate(tileEntity.getRelativeOffset().x, tileEntity.getRelativeOffset().y, tileEntity.getRelativeOffset().z);

        poseStack.mulPose(Axis.YN.rotationDegrees(direction.toYRot()));
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        ItemStack item = tileEntity.getStoredItem();
        var pojo = tileEntity.isSoulEmpty() ? ClientUtil.getModelPOJO(ClientUtils.UMA_STATUES) : ClientUtil.getModelPOJO(UmaSoulUtils.getName(item));

        if (model.needRefresh(pojo))
            model.loadModel(pojo);

        Optional<BedrockModelPOJO> suit = Optional.empty();

        var flat_flag = ClientUtils.getClientUmaDataRegistry()
                .getHolder(ResourceKey.create(UmaData.REGISTRY_KEY, UmaSoulUtils.getName(item)))
                .get().is(UmapyoiUmaDataTags.FLAT_CHEST);

        var tanned = ClientUtils.getClientUmaDataRegistry()
                .getHolder(ResourceKey.create(UmaData.REGISTRY_KEY, UmaSoulUtils.getName(item)))
                .get().is(UmapyoiUmaDataTags.TANNED_SKIN);

        ResourceLocation suitTexture = null;

        if(!tileEntity.getInventory().getStackInSlot(1).isEmpty()){
            if(tileEntity.getInventory().getStackInSlot(1).getItem() == ItemRegistry.TRAINNING_SUIT.get()) {
                suit = Optional.of(ClientUtil.getModelPOJO(trainningSuitRenderer.getModelPatched(tanned)));
                suitTexture = trainningSuitRenderer.getTexturePatched(flat_flag,tanned);
            }else if(tileEntity.getInventory().getStackInSlot(1).getItem() == ItemRegistry.SWIMSUIT.get()) {
                suit = Optional.of(ClientUtil.getModelPOJO(swimsuitRenderer.getModelPatched(tanned)));
                suitTexture = swimsuitRenderer.getTexturePatched(flat_flag, tanned);
            }else if(tileEntity.getInventory().getStackInSlot(1).getItem() == ItemRegistry.SUMMER_UNIFORM.get()) {
                suit = Optional.of(ClientUtil.getModelPOJO(summerUniformRenderer.getModelPatched(tanned)));
                suitTexture = summerUniformRenderer.getTexturePatched(flat_flag, tanned);
            }else if(tileEntity.getInventory().getStackInSlot(1).getItem() == ItemRegistry.WINTER_UNIFORM.get()) {
                suit = Optional.of(ClientUtil.getModelPOJO(winterUniformRenderer.getModelPatched(tanned)));
                suitTexture = winterUniformRenderer.getTexturePatched(flat_flag, tanned);
            }
        }

        if(suit.isPresent()){
            ClientUtils.ifNonNull(model.rightArm, ()->model.rightArm.visible = false);
            ClientUtils.ifNonNull(model.leftArm, ()->model.leftArm.visible = false);
            ClientUtils.ifNonNull(model.rightLeg, ()->model.rightLeg.visible = false);
            ClientUtils.ifNonNull(model.leftLeg, ()->model.leftLeg.visible = false);
            ClientUtils.ifNonNull(model.body, ()->model.body.visible = false);
            if(suitModel.needRefresh(suit.get())){
                suitModel.loadModel(suit.get());

                var head = suitModel.getChild("head");
                if(head != null)
                    head.visible = false;

                var tail = suitModel.getChild("tail");
                if(tail != null)
                    tail.visible = false;

                var hat = suitModel.getChild("hat");
                if(hat != null)
                    hat.visible = false;

            }
        }else{
            ClientUtils.ifNonNull(model.rightArm, ()->model.rightArm.visible = true);
            ClientUtils.ifNonNull(model.leftArm, ()->model.leftArm.visible = true);
            ClientUtils.ifNonNull(model.rightLeg, ()->model.rightLeg.visible = true);
            ClientUtils.ifNonNull(model.leftLeg, ()->model.leftLeg.visible = true);
            ClientUtils.ifNonNull(model.body, ()->model.body.visible = true);
        }


        for(Map.Entry<String, Vec3> entry : tileEntity.getRotations().entrySet()) {
            var bone = model.getChild(entry.getKey());
            var xRot = ClientUtil.convertRotation((float)entry.getValue().x);
            var yRot = ClientUtil.convertRotation((float)entry.getValue().y);
            var zRot = ClientUtil.convertRotation((float)entry.getValue().z);

            if(bone != null){
                bone.xRot = xRot;
                bone.yRot = yRot;
                bone.zRot = zRot;
            }

            var suitBone = suitModel.getChild(entry.getKey());
            if(suitBone != null){
                suitBone.xRot = xRot;
                suitBone.yRot = yRot;
                suitBone.zRot = zRot;
            }
        }

        VertexConsumer vertexConsumer = buffer
                .getBuffer(TestRenderType.test(tileEntity.isSoulEmpty() ? TEXTURE : ClientUtils.getTexture(UmaSoulUtils.getName(item))));
        model.renderToBuffer(poseStack, vertexConsumer, combinedLight, combinedOverlay, 1, 1, 1, 1);


        if(suitTexture != null){
            VertexConsumer suitVertexConsumer = buffer
                    .getBuffer(TestRenderType.test(suitTexture));
            suitModel.renderToBuffer(poseStack, suitVertexConsumer, combinedLight, combinedOverlay, 1, 1, 1, 1);
        }
        
        if(model.isEmissive()) {
            VertexConsumer emissiveConsumer = buffer
                    .getBuffer(RenderType.entityTranslucentEmissive(tileEntity.isSoulEmpty() ? TEXTURE : ClientUtils.getEmissiveTexture(UmaSoulUtils.getName(item))));
            model.renderEmissiveParts(poseStack, emissiveConsumer, combinedLight, combinedOverlay, 1, 1, 1, 1);
        }
        
        poseStack.popPose();
    }
    private static class TestRenderType extends RenderType{
	    public TestRenderType(String pName, VertexFormat pFormat, Mode pMode, int pBufferSize,
				boolean pAffectsCrumbling, boolean pSortOnUpload, Runnable pSetupState, Runnable pClearState) {
			super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
			// TODO Auto-generated constructor stub
		}

		private static final Function<ResourceLocation, RenderType> 
	    ENTITY_TRANSLUCENT_CULL = Util.memoize((p_286165_) -> {
	       RenderType.CompositeState rendertype$compositestate = 
	       RenderType.CompositeState.builder()
	       .setShaderState(RenderType.RENDERTYPE_ENTITY_TRANSLUCENT_SHADER)
	       .setTextureState(new RenderStateShard.TextureStateShard(p_286165_, false, false))
	       .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
	       .setCullState(NO_CULL)
	       .setLightmapState(LIGHTMAP)
	       .setOverlayState(OVERLAY)
	       .createCompositeState(true);
	       return RenderType.create(
	         "test_render", 
	         DefaultVertexFormat.NEW_ENTITY, 
	         VertexFormat.Mode.QUADS, 256, false, false, rendertype$compositestate);
	    });
		public static RenderType test(ResourceLocation texture) {
			return ENTITY_TRANSLUCENT_CULL.apply(texture);
		}
    }
}
