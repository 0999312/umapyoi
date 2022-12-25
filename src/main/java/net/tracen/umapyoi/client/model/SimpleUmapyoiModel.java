package net.tracen.umapyoi.client.model;

import cn.mcmod_mmf.mmlib.client.model.BedrockHumanoidModel;
import cn.mcmod_mmf.mmlib.client.model.bedrock.BedrockPart;
import cn.mcmod_mmf.mmlib.client.model.bedrock.BedrockVersion;
import cn.mcmod_mmf.mmlib.client.model.pojo.BedrockModelPOJO;
import net.minecraft.world.entity.LivingEntity;

public class SimpleUmapyoiModel extends BedrockHumanoidModel<LivingEntity> {
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

    public SimpleUmapyoiModel(BedrockModelPOJO pojo, BedrockVersion version) {
        super(pojo, version);
        this.rightArmDown = this.getChild("right_arm_down");
        this.leftArmDown = this.getChild("left_arm_down");
        this.rightLegDown = this.getChild("right_leg_down");
        this.leftLegDown = this.getChild("left_leg_down");

        this.rightEar = this.getChild("right_ear");
        this.leftEar = this.getChild("left_ear");
        this.rightFoot = this.getChild("right_foot");
        this.leftFoot = this.getChild("left_foot");

        this.hideParts = this.getChild("hide_parts") != null ? this.getChild("hide_parts") : new BedrockPart();
        this.tail = this.getChild("tail");
        this.tailDown = this.getChild("tail_down");
    }

    @Override
    public void setupAnim(LivingEntity entityIn, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw,
            float pHeadPitch) {
    }

}
