package net.trc.umapyoi.client.model;

import cn.mcmod_mmf.mmlib.client.model.BedrockHumanoidModel;
import cn.mcmod_mmf.mmlib.client.model.bedrock.BedrockPart;
import cn.mcmod_mmf.mmlib.client.model.bedrock.BedrockVersion;
import cn.mcmod_mmf.mmlib.client.model.pojo.BedrockModelPOJO;
import cn.mcmod_mmf.mmlib.utils.BedrockAnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

public class UmaPlayerModel<T extends LivingEntity> extends BedrockHumanoidModel<T> {
    public final BedrockPart rightArmDown;
    public final BedrockPart leftArmDown;
    public final BedrockPart rightLegDown;
    public final BedrockPart leftLegDown;

    public final BedrockPart rightEar;
    public final BedrockPart leftEar;
    public final BedrockPart rightFoot;
    public final BedrockPart leftFoot;

    public final BedrockPart hideParts;
    public final BedrockPart tail;
    public final BedrockPart tailDown;

    public UmaPlayerModel(LivingEntity player, BedrockModelPOJO pojo, BedrockVersion version) {
        super(pojo, version);
        this.rightArmDown = this.getChild("right_arm_down");
        this.leftArmDown = this.getChild("left_arm_down");
        this.rightLegDown = this.getChild("right_leg_down");
        this.leftLegDown = this.getChild("left_leg_down");

        this.rightEar = this.getChild("right_ear");
        this.leftEar = this.getChild("left_ear");
        this.rightFoot = this.getChild("right_foot");
        this.leftFoot = this.getChild("left_foot");

        this.hideParts = this.getChild("hide_parts");
        this.tail = this.getChild("tail");
        this.tailDown = this.getChild("tail_down");
    }

    @Override
    public void prepareMobModel(T p_102861_, float p_102862_, float p_102863_, float p_102864_) {
        this.attackTime = this.getAttackAnim(p_102861_, p_102864_);
        super.prepareMobModel(p_102861_, p_102862_, p_102863_, p_102864_);
    }

    @Override
    public void setupAnim(T entityIn, float p_102867_, float p_102868_, float p_102869_, float p_102870_,
            float p_102871_) {
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
            boolean flag = entityIn.getFallFlyingTicks() > 4;
            boolean flag1 = entityIn.isVisuallySwimming();
            this.head.yRot = p_102870_ * ((float) Math.PI / 180F);
            if (flag) {
                this.head.xRot = (-(float) Math.PI / 4F);
            } else if (this.swimAmount > 0.0F) {
                if (flag1) {
                    this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot, (-(float) Math.PI / 4F));
                } else {
                    this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot,
                            p_102871_ * ((float) Math.PI / 180F));
                }
            } else {
                this.head.xRot = p_102871_ * ((float) Math.PI / 180F);
            }

            this.body.yRot = 0.0F;
            this.rightArm.z = 0.0F;
            this.rightArm.x = -4.0F;
            this.leftArm.z = 0.0F;
            this.leftArm.x = 4.0F;
            float f = 1.0F;
            if (flag) {
                f = (float) entityIn.getDeltaMovement().lengthSqr();
                f /= 0.2F;
                f *= f * f;
            }

            if (f < 1.0F) {
                f = 1.0F;
            }

            this.rightArm.xRot = Mth.cos(p_102867_ * 0.6662F + (float) Math.PI) * p_102868_ / f;
            this.leftArm.xRot = Mth.cos(p_102867_ * 0.6662F) * p_102868_ / f;
            this.rightArm.zRot = 0.0F;
            this.leftArm.zRot = 0.0F;
            this.rightLeg.xRot = Mth.cos(p_102867_ * 0.6662F) * 1.4F * p_102868_ / f;
            this.leftLeg.xRot = Mth.cos(p_102867_ * 0.6662F + (float) Math.PI) * 1.4F * p_102868_ / f;
            this.rightLeg.yRot = 0.0F;
            this.leftLeg.yRot = 0.0F;
            this.rightLeg.zRot = 0.0F;
            this.leftLeg.zRot = 0.0F;
            if (this.riding) {
                this.rightArm.xRot += (-(float) Math.PI / 5F);
                this.leftArm.xRot += (-(float) Math.PI / 5F);
                this.leftLeg.xRot = -1.4137167F;
                this.leftLeg.yRot = (-(float) Math.PI / 10F);
                this.leftLeg.zRot = 0.07853982F;
                this.rightLeg.xRot = -1.4137167F;
                this.rightLeg.yRot = ((float) Math.PI / 10F);
                this.rightLeg.zRot = -0.07853982F;
            }

            this.rightArm.yRot = 0.0F;
            this.leftArm.yRot = 0.0F;
            boolean flag2 = entityIn.getMainArm() == HumanoidArm.RIGHT;
            if (entityIn.isUsingItem()) {
                boolean flag3 = entityIn.getUsedItemHand() == InteractionHand.MAIN_HAND;
                if (flag3 == flag2) {
                    this.poseRightArm(entityIn);
                } else {
                    this.poseLeftArm(entityIn);
                }
            } else {
                boolean flag4 = flag2 ? this.leftArmPose.isTwoHanded() : this.rightArmPose.isTwoHanded();
                if (flag2 != flag4) {
                    this.poseLeftArm(entityIn);
                    this.poseRightArm(entityIn);
                } else {
                    this.poseRightArm(entityIn);
                    this.poseLeftArm(entityIn);
                }
            }

            this.setupAttackAnimation(entityIn, p_102869_);
            if (this.crouching) {
                this.body.xRot = 0.5F;
                this.rightArm.xRot += 0.4F;
                this.leftArm.xRot += 0.4F;
                this.rightLeg.z = 4.75F;
                this.leftLeg.z = 4.75F;
                this.rightLeg.y = 12.2F;
                this.leftLeg.y = 12.2F;
                this.head.y = 4.2F;
                this.body.y = 3.2F;
                this.leftArm.y = 5.2F;
                this.rightArm.y = 5.2F;
            } else {
                this.body.xRot = 0.0F;
                this.rightLeg.z = 0.1F;
                this.leftLeg.z = 0.1F;
                this.rightLeg.y = 12.0F;
                this.leftLeg.y = 12.0F;
                this.head.y = 0.0F;
                this.body.y = 0.0F;
                this.leftArm.y = 2.0F;
                this.rightArm.y = 2.0F;
            }

            if (this.rightArmPose != HumanoidModel.ArmPose.SPYGLASS) {
                BedrockAnimationUtils.bobModelPart(this.rightArm, p_102869_, 1.0F);
            }

            if (this.leftArmPose != HumanoidModel.ArmPose.SPYGLASS) {
                BedrockAnimationUtils.bobModelPart(this.leftArm, p_102869_, -1.0F);
            }

            if (this.swimAmount > 0.0F) {
                float f5 = p_102867_ % 26.0F;
                HumanoidArm humanoidarm = this.getAttackArm(entityIn);
                float f1 = humanoidarm == HumanoidArm.RIGHT && this.attackTime > 0.0F ? 0.0F : this.swimAmount;
                float f2 = humanoidarm == HumanoidArm.LEFT && this.attackTime > 0.0F ? 0.0F : this.swimAmount;
                if (!entityIn.isUsingItem()) {
                    if (f5 < 14.0F) {
                        this.leftArm.xRot = this.rotlerpRad(f2, this.leftArm.xRot, 0.0F);
                        this.rightArm.xRot = Mth.lerp(f1, this.rightArm.xRot, 0.0F);
                        this.leftArm.yRot = this.rotlerpRad(f2, this.leftArm.yRot, (float) Math.PI);
                        this.rightArm.yRot = Mth.lerp(f1, this.rightArm.yRot, (float) Math.PI);
                        this.leftArm.zRot = this.rotlerpRad(f2, this.leftArm.zRot, (float) Math.PI
                                + 1.8707964F * this.quadraticArmUpdate(f5) / this.quadraticArmUpdate(14.0F));
                        this.rightArm.zRot = Mth.lerp(f1, this.rightArm.zRot, (float) Math.PI
                                - 1.8707964F * this.quadraticArmUpdate(f5) / this.quadraticArmUpdate(14.0F));
                    } else if (f5 >= 14.0F && f5 < 22.0F) {
                        float f6 = (f5 - 14.0F) / 8.0F;
                        this.leftArm.xRot = this.rotlerpRad(f2, this.leftArm.xRot, ((float) Math.PI / 2F) * f6);
                        this.rightArm.xRot = Mth.lerp(f1, this.rightArm.xRot, ((float) Math.PI / 2F) * f6);
                        this.leftArm.yRot = this.rotlerpRad(f2, this.leftArm.yRot, (float) Math.PI);
                        this.rightArm.yRot = Mth.lerp(f1, this.rightArm.yRot, (float) Math.PI);
                        this.leftArm.zRot = this.rotlerpRad(f2, this.leftArm.zRot, 5.012389F - 1.8707964F * f6);
                        this.rightArm.zRot = Mth.lerp(f1, this.rightArm.zRot, 1.2707963F + 1.8707964F * f6);
                    } else if (f5 >= 22.0F && f5 < 26.0F) {
                        float f3 = (f5 - 22.0F) / 4.0F;
                        this.leftArm.xRot = this.rotlerpRad(f2, this.leftArm.xRot,
                                ((float) Math.PI / 2F) - ((float) Math.PI / 2F) * f3);
                        this.rightArm.xRot = Mth.lerp(f1, this.rightArm.xRot,
                                ((float) Math.PI / 2F) - ((float) Math.PI / 2F) * f3);
                        this.leftArm.yRot = this.rotlerpRad(f2, this.leftArm.yRot, (float) Math.PI);
                        this.rightArm.yRot = Mth.lerp(f1, this.rightArm.yRot, (float) Math.PI);
                        this.leftArm.zRot = this.rotlerpRad(f2, this.leftArm.zRot, (float) Math.PI);
                        this.rightArm.zRot = Mth.lerp(f1, this.rightArm.zRot, (float) Math.PI);
                    }
                }

                this.leftLeg.xRot = Mth.lerp(this.swimAmount, this.leftLeg.xRot,
                        0.3F * Mth.cos(p_102867_ * 0.33333334F + (float) Math.PI));
                this.rightLeg.xRot = Mth.lerp(this.swimAmount, this.rightLeg.xRot,
                        0.3F * Mth.cos(p_102867_ * 0.33333334F));
            }

            this.tail.copyFrom(this.body);

            if (this.crouching) {
                this.tail.xRot = 1.0F;
                this.tail.z = 3.125F;
                this.tail.y = 11.0F;
            } else {
                this.tail.xRot = 0F;
                this.tail.z = 3.0F;
                this.tail.y = 8.0F;
            }
        }
    }

    public void setModelProperties(LivingEntity player) {
        this.setModelProperties(player, false);
    }

    public void setModelProperties(LivingEntity player, boolean render_head_only) {
        this.setModelProperties(player, render_head_only, false);
    }

    public void setModelProperties(LivingEntity player, boolean render_head_only, boolean hide_head) {

        boolean shouldSit = player.isPassenger()
                && (player.getVehicle() != null && player.getVehicle().shouldRiderSit());
        this.riding = shouldSit;

        if (player.isSpectator()) {
            this.setAllVisible(false);
            this.head.visible = true;
        } else {
            this.setAllVisible(true);
            if (render_head_only) {
                this.setAllVisible(false);
                this.head.visible = true;
                this.tail.visible = true;
            } else if (hide_head) {
                this.head.visible = false;
                this.tail.visible = false;
            }
            this.crouching = player.isCrouching();
            if (!player.getItemBySlot(EquipmentSlot.CHEST).isEmpty() && !(player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ElytraItem)) {
                this.hideParts.visible = false;
            }

            if (!player.getItemBySlot(EquipmentSlot.FEET).isEmpty()) {
                this.rightFoot.visible = false;
                this.leftFoot.visible = false;
            }
            HumanoidModel.ArmPose humanoidmodel$armpose = getArmPose(player, InteractionHand.MAIN_HAND);
            HumanoidModel.ArmPose humanoidmodel$armpose1 = getArmPose(player, InteractionHand.OFF_HAND);
            if (humanoidmodel$armpose.isTwoHanded()) {
                humanoidmodel$armpose1 = player.getOffhandItem().isEmpty() ? HumanoidModel.ArmPose.EMPTY
                        : HumanoidModel.ArmPose.ITEM;
            }

            if (player.getMainArm() == HumanoidArm.RIGHT) {
                this.rightArmPose = humanoidmodel$armpose;
                this.leftArmPose = humanoidmodel$armpose1;
            } else {
                this.rightArmPose = humanoidmodel$armpose1;
                this.leftArmPose = humanoidmodel$armpose;
            }
        }
    }

    protected float getAttackAnim(T p_115343_, float p_115344_) {
        return p_115343_.getAttackAnim(p_115344_);
    }

    protected void setupAttackAnimation(T p_102858_, float p_102859_) {
        if (!(this.attackTime <= 0.0F)) {
            HumanoidArm humanoidarm = this.getAttackArm(p_102858_);
            BedrockPart modelpart = this.getArm(humanoidarm);
            float f = this.attackTime;
            this.body.yRot = Mth.sin(Mth.sqrt(f) * ((float) Math.PI * 2F)) * 0.2F;
            if (humanoidarm == HumanoidArm.LEFT) {
                this.body.yRot *= -1.0F;
            }

            this.rightArm.z = Mth.sin(this.body.yRot) * 5.0F;
            this.rightArm.x = -Mth.cos(this.body.yRot) * 4.0F;
            this.leftArm.z = -Mth.sin(this.body.yRot) * 5.0F;
            this.leftArm.x = Mth.cos(this.body.yRot) * 4.0F;
            this.rightArm.yRot += this.body.yRot;
            this.leftArm.yRot += this.body.yRot;
            this.leftArm.xRot += this.body.yRot;
            f = 1.0F - this.attackTime;
            f *= f;
            f *= f;
            f = 1.0F - f;
            float f1 = Mth.sin(f * (float) Math.PI);
            float f2 = Mth.sin(this.attackTime * (float) Math.PI) * -(this.head.xRot - 0.7F) * 0.75F;
            modelpart.xRot -= f1 * 1.2F + f2;
            modelpart.yRot += this.body.yRot * 2.0F;
            modelpart.zRot += Mth.sin(this.attackTime * (float) Math.PI) * -0.4F;
        }
    }

    private static HumanoidModel.ArmPose getArmPose(LivingEntity player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.isEmpty()) {
            return HumanoidModel.ArmPose.EMPTY;
        } else {
            if (player.getUsedItemHand() == hand && player.getUseItemRemainingTicks() > 0) {
                UseAnim useanim = itemstack.getUseAnimation();
                if (useanim == UseAnim.BLOCK) {
                    return HumanoidModel.ArmPose.BLOCK;
                }

                if (useanim == UseAnim.BOW) {
                    return HumanoidModel.ArmPose.BOW_AND_ARROW;
                }

                if (useanim == UseAnim.SPEAR) {
                    return HumanoidModel.ArmPose.THROW_SPEAR;
                }

                if (useanim == UseAnim.CROSSBOW && hand == player.getUsedItemHand()) {
                    return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
                }

                if (useanim == UseAnim.SPYGLASS) {
                    return HumanoidModel.ArmPose.SPYGLASS;
                }
            } else if (!player.swinging && itemstack.getItem() instanceof CrossbowItem
                    && CrossbowItem.isCharged(itemstack)) {
                return HumanoidModel.ArmPose.CROSSBOW_HOLD;
            }

            return HumanoidModel.ArmPose.ITEM;
        }
    }
}
