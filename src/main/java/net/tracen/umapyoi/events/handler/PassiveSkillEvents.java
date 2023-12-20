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
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.data.tag.UmapyoiBlockTags;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.utils.UmaSoulUtils;

@Mod.EventBusSubscriber
public class PassiveSkillEvents {

    public static final UUID PASSIVEUUID = UUID.fromString("306e284a-8a74-11ee-b9d1-0242ac120002");

    @SubscribeEvent
    public static void testPassiveSkill_att(PlayerEvent.BreakSpeed event) {
        Player player = event.getPlayer();
        var soul = UmapyoiAPI.getUmaSoul(player);
        if (UmaSoulUtils.hasSkill(soul, UmaSkillRegistry.DIG_SPEED.getId()))
            event.setNewSpeed(event.getOriginalSpeed() * 1.1F);
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
        AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);

        var test_speed = new AttributeModifier(PASSIVEUUID,
                "passive_skill_turf", 0.1D, Operation.MULTIPLY_TOTAL);
        if (UmapyoiAPI.getUmaSoul(player).isEmpty()) {
            movementSpeed.removeModifier(test_speed);
            return;
        }

        BlockPos groundPos = player.getY() % 1 < 0.5 ? player.blockPosition().below() : player.blockPosition();
        BlockState groundBlock = event.player.level.getBlockState(groundPos);

        if (UmaSoulUtils.hasSkill(UmapyoiAPI.getUmaSoul(player), UmaSkillRegistry.TURF_RUNNER.getId())) {
            handleMovementModifier(movementSpeed, test_speed, groundBlock, UmapyoiBlockTags.TRACK_TURF);
        }
    }
    
    @SubscribeEvent
    public static void passiveDirtRunner(TickEvent.PlayerTickEvent event) {
        var player = event.player;
        AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);

        var test_speed = new AttributeModifier(PASSIVEUUID,
                "passive_skill_dirt", 0.1D, Operation.MULTIPLY_TOTAL);
        if (UmapyoiAPI.getUmaSoul(player).isEmpty()) {
            movementSpeed.removeModifier(test_speed);
            return;
        }

        BlockPos groundPos = player.getY() % 1 < 0.5 ? player.blockPosition().below() : player.blockPosition();
        BlockState groundBlock = event.player.level.getBlockState(groundPos);

        if (UmaSoulUtils.hasSkill(UmapyoiAPI.getUmaSoul(player), UmaSkillRegistry.DIRT_RUNNER.getId())) {
            handleMovementModifier(movementSpeed, test_speed, groundBlock, UmapyoiBlockTags.TRACK_DIRT);
        }
    }
    
    @SubscribeEvent
    public static void passiveSnowRunner(TickEvent.PlayerTickEvent event) {
        var player = event.player;
        AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);

        var test_speed = new AttributeModifier(PASSIVEUUID,
                "passive_skill_snow", 0.1D, Operation.MULTIPLY_TOTAL);
        if (UmapyoiAPI.getUmaSoul(player).isEmpty()) {
            movementSpeed.removeModifier(test_speed);
            return;
        }

        BlockPos groundPos = player.getY() % 1 < 0.5 ? player.blockPosition().below() : player.blockPosition();
        BlockState groundBlock = event.player.level.getBlockState(groundPos);

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
