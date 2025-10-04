package net.tracen.umapyoi.events.handler;

import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.data.tag.UmapyoiBlockTags;
import net.tracen.umapyoi.events.ApplyUmasoulAttributeEvent;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.UmapyoiAttributesRegistry;
import net.tracen.umapyoi.utils.UmaSoulUtils;
import net.tracen.umapyoi.utils.UmaStatusUtils.StatusType;

@Mod.EventBusSubscriber
public class PassiveSkillEvents {

    public static final UUID PASSIVEUUID = UUID.fromString("306e284a-8a74-11ee-b9d1-0242ac120002");
    public static final UUID SPRINTUUID = UUID.fromString("0591c346-7c25-4171-b2bd-66e9824f1c90");

    @SubscribeEvent
    public static void testPassiveSkill_im(ApplyUmasoulAttributeEvent event) {
        var soul = event.getUmaSoul();
        if (UmaSoulUtils.hasSkill(soul, UmaSkillRegistry.INQUISITIVE_MIND.getId())) {
        	var speedFlag = UmaSoulUtils.getProperty(soul)[StatusType.SPEED.getId()] >= 12;
        	var wisdomFlag = UmaSoulUtils.getProperty(soul)[StatusType.WISDOM.getId()] >= 12;
        	event.getAttributes().put(Attributes.ATTACK_SPEED, new AttributeModifier(PASSIVEUUID, "passive_speed_bonus",
        			speedFlag && wisdomFlag ? 0.075D :0.05D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
    }
    
    @SubscribeEvent
    public static void testPassiveSkill_att(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        var soul = UmapyoiAPI.getUmaSoul(player);
        if (UmaSoulUtils.hasSkill(soul, UmaSkillRegistry.DIG_SPEED.getId()))
            event.setNewSpeed(event.getOriginalSpeed() * 1.1F);
    }
    
    @SubscribeEvent
    public static void sprintSpeedTick(TickEvent.PlayerTickEvent event) {
        var player = event.player;
        AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);

        var speedModifier = new AttributeModifier(SPRINTUUID,
                "sprint_speed_bonus", player.getAttributeValue(UmapyoiAttributesRegistry.SPRINT_SPEED.get()), 
                UmapyoiConfig.UMASOUL_SPEED_PRECENT_ENABLE.get() ? AttributeModifier.Operation.MULTIPLY_TOTAL
                        : AttributeModifier.Operation.ADDITION);
        if (UmapyoiAPI.getUmaSoul(player).isEmpty()) {
            movementSpeed.removeModifier(speedModifier);
            return;
        }

        if (player.isSprinting()) {
            if (!movementSpeed.hasModifier(speedModifier))
            	movementSpeed.addTransientModifier(speedModifier);
        } else {
        	movementSpeed.removeModifier(speedModifier);
        }
    }

    @SubscribeEvent
    public static void passiveStepHeight(TickEvent.PlayerTickEvent event) {
        var player = event.player;
        AttributeInstance stepHeight = player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get());
        var heightModifier = new AttributeModifier(PASSIVEUUID,
                "passive_skill_height", 0.5D, Operation.ADDITION);
        if (UmapyoiAPI.getUmaSoul(player).isEmpty()) {
            stepHeight.removeModifier(heightModifier);
            return;
        }
        
        if (UmaSoulUtils.hasSkill(UmapyoiAPI.getUmaSoul(player), UmaSkillRegistry.MOUNTAIN_CLIMBER.getId())) {
            if (!stepHeight.hasModifier(heightModifier))
                stepHeight.addTransientModifier(heightModifier);
        } else {
            stepHeight.removeModifier(heightModifier);
        }
    }

    @SubscribeEvent
    public static void passiveTurfRunner(TickEvent.PlayerTickEvent event) {
        var player = event.player;
        AttributeInstance movementSpeed = player.getAttribute(UmapyoiAttributesRegistry.SPRINT_SPEED.get());

        var test_speed = new AttributeModifier(PASSIVEUUID,
                "passive_skill_turf", 0.1D, Operation.MULTIPLY_TOTAL);
        if (UmapyoiAPI.getUmaSoul(player).isEmpty()) {
            movementSpeed.removeModifier(test_speed);
            return;
        }

        BlockPos groundPos = player.getY() % 1 < 0.5 ? player.blockPosition().below() : player.blockPosition();
        BlockState groundBlock = event.player.level().getBlockState(groundPos);

        if (UmaSoulUtils.hasSkill(UmapyoiAPI.getUmaSoul(player), UmaSkillRegistry.TURF_RUNNER.getId())) {
            handleMovementModifier(movementSpeed, test_speed, groundBlock, UmapyoiBlockTags.TRACK_TURF);
        }
    }
    
    @SubscribeEvent
    public static void passiveDirtRunner(TickEvent.PlayerTickEvent event) {
        var player = event.player;
        AttributeInstance movementSpeed = player.getAttribute(UmapyoiAttributesRegistry.SPRINT_SPEED.get());

        var test_speed = new AttributeModifier(PASSIVEUUID,
                "passive_skill_dirt", 0.1D, Operation.MULTIPLY_TOTAL);
        if (UmapyoiAPI.getUmaSoul(player).isEmpty()) {
            movementSpeed.removeModifier(test_speed);
            return;
        }

        BlockPos groundPos = player.getY() % 1 < 0.5 ? player.blockPosition().below() : player.blockPosition();
        BlockState groundBlock = event.player.level().getBlockState(groundPos);

        if (UmaSoulUtils.hasSkill(UmapyoiAPI.getUmaSoul(player), UmaSkillRegistry.DIRT_RUNNER.getId())) {
            handleMovementModifier(movementSpeed, test_speed, groundBlock, UmapyoiBlockTags.TRACK_DIRT);
        }
    }
    
    @SubscribeEvent
    public static void passiveSnowRunner(TickEvent.PlayerTickEvent event) {
        var player = event.player;
        AttributeInstance movementSpeed = player.getAttribute(UmapyoiAttributesRegistry.SPRINT_SPEED.get());

        var test_speed = new AttributeModifier(PASSIVEUUID,
                "passive_skill_snow", 0.1D, Operation.MULTIPLY_TOTAL);
        if (UmapyoiAPI.getUmaSoul(player).isEmpty()) {
            movementSpeed.removeModifier(test_speed);
            return;
        }

        BlockPos groundPos = player.getY() % 1 < 0.5 ? player.blockPosition().below() : player.blockPosition();
        BlockState groundBlock = event.player.level().getBlockState(groundPos);

        if (UmaSoulUtils.hasSkill(UmapyoiAPI.getUmaSoul(player), UmaSkillRegistry.SNOW_RUNNER.getId())) {
            handleMovementModifier(movementSpeed, test_speed, groundBlock, event.player.getFeetBlockState(), UmapyoiBlockTags.TRACK_SNOW);
        }
    }

    private static void handleMovementModifier(AttributeInstance attribute, AttributeModifier modifier,
            BlockState groundBlock, TagKey<Block> tagIn) {
        handleMovementModifier(attribute, modifier, groundBlock, groundBlock, tagIn);
    }

    private static void handleMovementModifier(AttributeInstance attribute, AttributeModifier modifier,
            BlockState groundBlock, BlockState feetblock, TagKey<Block> tagIn) {
        if (groundBlock.isAir() && feetblock.isAir())
            return ;
        if (!groundBlock.is(tagIn) && !feetblock.is(tagIn)) {
            if (attribute.hasModifier(modifier))
                attribute.removeModifier(modifier);
            return ;
        }
        if (!attribute.hasModifier(modifier))
            attribute.addTransientModifier(modifier);
    }
}
