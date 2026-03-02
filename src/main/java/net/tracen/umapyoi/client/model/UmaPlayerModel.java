package net.tracen.umapyoi.client.model;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.data.tag.UmapyoiUmaDataTags;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.utils.ClientUtils;
import net.tracen.umapyoi.utils.UmaSoulUtils;

import java.util.List;

import cn.mcmod_mmf.mmlib.client.model.BedrockHumanoidModel;
import cn.mcmod_mmf.mmlib.client.model.bedrock.BedrockPart;
import cn.mcmod_mmf.mmlib.client.model.pojo.BedrockModelPOJO;

public class UmaPlayerModel<T extends LivingEntity> extends BedrockHumanoidModel<T> {

    public BedrockPart rightArmDown;
    public BedrockPart leftArmDown;
    public BedrockPart rightLegDown;
    public BedrockPart leftLegDown;
    public BedrockPart rightEar;
    public BedrockPart leftEar;
    public BedrockPart rightEarHideParts;
    public BedrockPart leftEarHideParts;
    public BedrockPart rightFoot;
    public BedrockPart leftFoot;
    public BedrockPart rightLegHideParts;
    public BedrockPart leftLegHideParts;
    public BedrockPart hat;
    public BedrockPart hideParts;
    public BedrockPart tail;
    public BedrockPart tailDown;
    
    public BedrockPart cape;
    public List<BedrockPart> longHairParts = Lists.newArrayList();
    public UmaPlayerModel() {
        super();
    }
    
    public UmaPlayerModel(BedrockModelPOJO pojo) {
        super(pojo);
    }

    @Override
    public void loadModel(BedrockModelPOJO pojo) {
        super.loadModel(pojo);
        
        this.rightArmDown = this.getChild("right_arm_down");
        this.leftArmDown = this.getChild("left_arm_down");
        this.rightLegDown = this.getChild("right_leg_down");
        this.leftLegDown = this.getChild("left_leg_down");
        this.rightEar = this.getChild("right_ear");
        this.leftEar = this.getChild("left_ear");
        this.rightFoot = this.getChild("right_foot");
        this.leftFoot = this.getChild("left_foot");
        this.hat = this.getChild("hat");
        this.cape = this.getChild("cape");
        this.hideParts = this.getChild("hide_parts");
        this.rightEarHideParts = this.getChild("right_earmuffs");
        this.leftEarHideParts = this.getChild("left_earmuffs");
        this.rightLegHideParts = this.getChild("right_leg_hide_parts");
        this.leftLegHideParts = this.getChild("left_leg_hide_parts");
        this.tail = this.getChild("tail");
        this.tailDown = this.getChild("tail_down");
        this.longHairParts = Lists.newArrayList();
        this.getModelMap().forEach((name,part)->{
            if(name.startsWith("long_hair_") || name.equals("long_hair"))
                this.longHairParts.add(part);
        });
    }
    
    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay,
            float red, float green, float blue, float alpha) {
        super.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(T entityIn, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw,
            float pHeadPitch) {
        if (entityIn instanceof ArmorStand) {
            ArmorStand entityarmorstand = (ArmorStand) entityIn;
            this.head.xRot = 0.017453292F * entityarmorstand.getHeadPose().getX();
            this.head.yRot = 0.017453292F * entityarmorstand.getHeadPose().getY();
            this.head.zRot = 0.017453292F * entityarmorstand.getHeadPose().getZ();
            this.head.setPos(0.0F, 1.0F, 0.0F);
            this.body.xRot = 0.017453292F * entityarmorstand.getBodyPose().getX();
            this.body.yRot = 0.017453292F * entityarmorstand.getBodyPose().getY();
            this.body.zRot = 0.017453292F * entityarmorstand.getBodyPose().getZ();
            this.leftArm.xRot = 0.017453292F * entityarmorstand.getLeftArmPose().getX();
            this.leftArm.yRot = 0.017453292F * entityarmorstand.getLeftArmPose().getY();
            this.leftArm.zRot = 0.017453292F * entityarmorstand.getLeftArmPose().getZ();
            this.rightArm.xRot = 0.017453292F * entityarmorstand.getRightArmPose().getX();
            this.rightArm.yRot = 0.017453292F * entityarmorstand.getRightArmPose().getY();
            this.rightArm.zRot = 0.017453292F * entityarmorstand.getRightArmPose().getZ();
            this.leftLeg.xRot = 0.017453292F * entityarmorstand.getLeftLegPose().getX();
            this.leftLeg.yRot = 0.017453292F * entityarmorstand.getLeftLegPose().getY();
            this.leftLeg.zRot = 0.017453292F * entityarmorstand.getLeftLegPose().getZ();
            this.leftLeg.setPos(1.9F, 11.0F, 0.0F);
            this.rightLeg.xRot = 0.017453292F * entityarmorstand.getRightLegPose().getX();
            this.rightLeg.yRot = 0.017453292F * entityarmorstand.getRightLegPose().getY();
            this.rightLeg.zRot = 0.017453292F * entityarmorstand.getRightLegPose().getZ();
            this.rightLeg.setPos(-1.9F, 11.0F, 0.0F);
        } else {
            
            this.tail.copyFrom(this.body);

            if (this.crouching) {
                this.tail.xRot = 1.0F + pLimbSwingAmount * 0.5F;
                this.tail.z = 3.125F;
                this.tail.y = 11.0F;
                
                this.cape.xRot = 1.0F + pLimbSwingAmount * 0.5F;
            } else {
                this.tail.xRot = pLimbSwingAmount * 1F;
                this.tail.z = 1.75F;
                this.tail.y = 8.0F;
                
                this.cape.xRot = pLimbSwingAmount * 1F;
            }
            if (this.head.xRot < 0) {
            	this.longHairParts.forEach(part -> part.xRot = -this.head.xRot);
                }
            else 
            	this.longHairParts.forEach(part -> part.xRot = 0F);
        	ItemStack renderingUmaSoul = UmapyoiAPI.getRenderingUmaSoul(entityIn);
			boolean isStucked = ClientUtils.getClientUmaDataRegistry()
					.getHolder(ResourceKey.create(UmaData.REGISTRY_KEY, UmaSoulUtils.getName(renderingUmaSoul)))
					.get().is(UmapyoiUmaDataTags.STUCK_MODEL);
			if(!isStucked)
				animationEarTail(entityIn, pAgeInTicks);
        }
        this.hat.copyFrom(head);

    }

    private void animationEarTail(T entityIn, float pAgeInTicks) {
    	
        int ears_reminder = (int) ((pAgeInTicks + Math.abs(entityIn.getUUID().getLeastSignificantBits()) % 10)
                % UmapyoiConfig.EAR_ANIMATION_INTERVAL.get());
        int tail_reminder = (int) ((pAgeInTicks + Math.abs(entityIn.getUUID().getLeastSignificantBits()) % 10)
                % UmapyoiConfig.TAIL_ANIMATION_INTERVAL.get());
        float earRot = Mth.cos(ears_reminder) * 0.125F;
        if (0 < ears_reminder && ears_reminder < 8) {
            if (this.leftEarHideParts != null)
                this.leftEarHideParts.zRot = earRot;
            if (this.rightEarHideParts != null)
                this.rightEarHideParts.zRot = -earRot;
            this.leftEar.zRot = earRot;
            this.rightEar.zRot = -earRot;
        } else {
            if (this.leftEarHideParts != null)
                this.leftEarHideParts.zRot = 0F;
            if (this.rightEarHideParts != null)
                this.rightEarHideParts.zRot = 0F;
            this.leftEar.zRot = 0F;
            this.rightEar.zRot = 0F;
        }

        if (0 < tail_reminder && tail_reminder < 8) {
            this.tail.zRot = -Mth.cos(pAgeInTicks * 0.7F) * 0.5F;
            this.tail.yRot = Mth.cos(pAgeInTicks * 0.7F) * 0.5F;
        } else {
            this.tail.zRot = 0;
            this.tail.yRot = 0;
        }
    }

    public void setModelProperties(LivingEntity player) {
        boolean shouldSit = player.isPassenger()
                && (player.getVehicle() != null && player.getVehicle().shouldRiderSit());
        this.riding = shouldSit;

        if (player.isSpectator()) {
            this.setAllVisible(false);
            this.head.visible = true;
        } else {
            this.setAllVisible(true);

            this.crouching = player.isCrouching();
            if (UmapyoiConfig.VANILLA_ARMOR_RENDER.get() && !UmapyoiConfig.HIDE_PARTS_RENDER.get()) {
                var noHelmet = player.getItemBySlot(EquipmentSlot.HEAD).isEmpty();
                this.setHatAndEarsVisible(noHelmet, true);

                var chestEquipment = player.getItemBySlot(EquipmentSlot.CHEST);
                var noChestplate = chestEquipment.isEmpty() || (chestEquipment.getItem() instanceof ElytraItem);
                this.hideParts.visible = noChestplate;
                this.cape.visible = noChestplate;

                var noLeggings = player.getItemBySlot(EquipmentSlot.LEGS).isEmpty();
                this.rightLegHideParts.visible = noLeggings;
                this.leftLegHideParts.visible = noLeggings;

                var noBoots = player.getItemBySlot(EquipmentSlot.FEET).isEmpty();
                this.rightFoot.visible = noBoots;
                this.leftFoot.visible = noBoots;
            }
        }
    }

    @Override
    public void setAllVisible(boolean visible) {
        this.setHatAndEarsVisible(visible, visible);
        this.setHeadVisible(visible);
        this.setBodyVisible(visible);
        this.setCapeVisible(visible);
        this.setArmsVisible(visible);
        this.setLegsVisible(visible);
        this.setTailVisible(visible);
    }

    public void copyAnim(BedrockPart part, ModelPart old_part) {
        part.xRot = old_part.xRot;
        part.yRot = old_part.yRot;
        part.zRot = old_part.zRot;
        part.x = old_part.x;
        if (part == this.leftArm)
            part.x -= 1F;
        if (part == this.rightArm)
            part.x += 1F;

        if (part == this.leftLeg)
            part.x -= 0.125F;
        if (part == this.rightLeg)
            part.x += 0.125F;
        part.y = old_part.y;
        part.z = old_part.z;
        if (part == this.leftLeg)
            part.z -= 0.125F;
        if (part == this.rightLeg)
            part.z -= 0.125F;
    }

    /**
     * Set the visibility of both the hat and the ears. Should be preferred over setting the
     * hat's visibility directly to ensure that the correct ear parts are shown.
     */
    public void setHatAndEarsVisible(boolean hatVisible, boolean earsVisible) {
        this.hat.visible = hatVisible;
        setEarsVisible(earsVisible);
    }

    public void setEarsVisible(boolean visible) {
        setLeftEarVisible(visible);
        setRightEarVisible(visible);
    }

    public void setLeftEarVisible(boolean visible) {
        this.leftEarHideParts.visible = visible;
        // hide parts (earmuffs) are mounted to hat
        this.leftEar.visible = visible && (!this.hat.visible || this.leftEarHideParts.isEmpty());
    }

    public void setRightEarVisible(boolean visible) {
        this.rightEarHideParts.visible = visible;
        // hide parts (earmuffs) are mounted to hat
        this.rightEar.visible = visible && (!this.hat.visible || this.rightEarHideParts.isEmpty());
    }

    /**
     * Set the visibility of the head and long hair parts.
     */
    public void setHeadVisible(boolean visible) {
        this.head.visible = visible;
        this.setLongHairPartsVisible(visible);
    }

    public void setBodyVisible(boolean visible) {
        this.body.visible = visible;
        this.hideParts.visible = visible;
    }

    public void setCapeVisible(boolean visible) {
        this.cape.visible = visible;
    }

    public void setArmsVisible(boolean visible) {
        setLeftArmVisible(visible);
        setRightArmVisible(visible);
    }

    public void setLeftArmVisible(boolean visible) {
        this.leftArm.visible = visible;
        this.leftArmDown.visible = visible;
    }

    public void setRightArmVisible(boolean visible) {
        this.rightArm.visible = visible;
        this.rightArmDown.visible = visible;
    }

    public void setLegsVisible(boolean visible) {
        setLeftLegVisible(visible);
        setRightLegVisible(visible);
    }

    public void setLeftLegVisible(boolean visible) {
        this.leftLeg.visible = visible;
        this.leftLegDown.visible = visible;
        this.leftLegHideParts.visible = visible;
        this.leftFoot.visible = visible;
    }

    public void setRightLegVisible(boolean visible) {
        this.rightLeg.visible = visible;
        this.rightLegDown.visible = visible;
        this.rightLegHideParts.visible = visible;
        this.rightFoot.visible = visible;
    }

    public void setTailVisible(boolean visible) {
        this.tail.visible = visible;
        this.tailDown.visible = visible;
    }

    public void setLongHairPartsVisible(boolean visible) {
        this.longHairParts.forEach(part -> part.visible = visible);
    }
}
