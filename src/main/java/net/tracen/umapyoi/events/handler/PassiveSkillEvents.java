package net.tracen.umapyoi.events.handler;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.data.tag.UmapyoiBlockTags;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.utils.UmaSoulUtils;

@EventBusSubscriber
public class PassiveSkillEvents {

    public static final ResourceLocation SKILL_HEIGHT = 
    		ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "passive_skill_height");
    public static final ResourceLocation SKILL_TURF = 
    		ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "passive_skill_turf");
    public static final ResourceLocation SKILL_DIRT = 
    		ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "passive_skill_dirt");
    public static final ResourceLocation SKILL_SNOW = 
    		ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "passive_skill_snow");
	
    @SubscribeEvent
    public static void testPassiveSkill_att(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        var soul = UmapyoiAPI.getUmaSoul(player);
        if (UmaSoulUtils.hasSkill(soul, UmaSkillRegistry.DIG_SPEED.getId()))
            event.setNewSpeed(event.getOriginalSpeed() * 1.1F);
    }

    @SubscribeEvent
    public static void passiveStepHeight(PlayerTickEvent event) {
        var player = event.getEntity();
        AttributeInstance stepHeight = player.getAttribute(Attributes.STEP_HEIGHT);
        var heightModifier = new AttributeModifier(SKILL_HEIGHT
                , 0.5D, Operation.ADD_VALUE);
        if (UmapyoiAPI.getUmaSoul(player).isEmpty()) {
            stepHeight.removeModifier(heightModifier);
            return;
        }
        
        if (UmaSoulUtils.hasSkill(UmapyoiAPI.getUmaSoul(player), UmaSkillRegistry.MOUNTAIN_CLIMBER.getId())) {
            if (!stepHeight.hasModifier(SKILL_HEIGHT))
                stepHeight.addTransientModifier(heightModifier);
        } else {
            stepHeight.removeModifier(heightModifier);
        }
    }

    @SubscribeEvent
    public static void passiveTurfRunner(PlayerTickEvent event) {
        var player = event.getEntity();
        AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);

        var test_speed = new AttributeModifier(SKILL_TURF, 0.1D, Operation.ADD_MULTIPLIED_TOTAL);
        if (UmapyoiAPI.getUmaSoul(player).isEmpty()) {
            movementSpeed.removeModifier(test_speed);
            return;
        }

        BlockPos groundPos = player.getY() % 1 < 0.5 ? player.blockPosition().below() : player.blockPosition();
        BlockState groundBlock = player.level().getBlockState(groundPos);

        if (UmaSoulUtils.hasSkill(UmapyoiAPI.getUmaSoul(player), UmaSkillRegistry.TURF_RUNNER.getId())) {
            handleMovementModifier(movementSpeed, test_speed, groundBlock, UmapyoiBlockTags.TRACK_TURF);
        }
    }
    
    @SubscribeEvent
    public static void passiveDirtRunner(PlayerTickEvent event) {
        var player = event.getEntity();
        AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);

        var test_speed = new AttributeModifier(SKILL_DIRT, 0.1D, Operation.ADD_MULTIPLIED_TOTAL);
        if (UmapyoiAPI.getUmaSoul(player).isEmpty()) {
            movementSpeed.removeModifier(test_speed);
            return;
        }

        BlockPos groundPos = player.getY() % 1 < 0.5 ? player.blockPosition().below() : player.blockPosition();
        BlockState groundBlock = player.level().getBlockState(groundPos);

        if (UmaSoulUtils.hasSkill(UmapyoiAPI.getUmaSoul(player), UmaSkillRegistry.DIRT_RUNNER.getId())) {
            handleMovementModifier(movementSpeed, test_speed, groundBlock, UmapyoiBlockTags.TRACK_DIRT);
        }
    }
    
    @SubscribeEvent
    public static void passiveSnowRunner(PlayerTickEvent event) {
    	var player = event.getEntity();
        AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);

        var test_speed = new AttributeModifier(SKILL_SNOW, 0.1D, Operation.ADD_MULTIPLIED_TOTAL);
        if (UmapyoiAPI.getUmaSoul(player).isEmpty()) {
            movementSpeed.removeModifier(test_speed);
            return;
        }

        BlockPos groundPos = player.getY() % 1 < 0.5 ? player.blockPosition().below() : player.blockPosition();
        BlockState groundBlock = player.level().getBlockState(groundPos);

        if (UmaSoulUtils.hasSkill(UmapyoiAPI.getUmaSoul(player), UmaSkillRegistry.SNOW_RUNNER.getId())) {
            handleMovementModifier(movementSpeed, test_speed, groundBlock, player.getBlockStateOn(), UmapyoiBlockTags.TRACK_SNOW);
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
            if (attribute.hasModifier(modifier.id()))
                attribute.removeModifier(modifier);
            return ;
        }
        if (!attribute.hasModifier(modifier.id()))
            attribute.addTransientModifier(modifier);
    }
}
