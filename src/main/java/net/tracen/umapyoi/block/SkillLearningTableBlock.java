package net.tracen.umapyoi.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.tracen.umapyoi.container.SkillLearningMenu;

public class SkillLearningTableBlock extends CraftingTableBlock {
    private static final Component CONTAINER_TITLE = Component.translatable("container.umapyoi.skill_learning");

    public SkillLearningTableBlock() {
        super(Properties.copy(Blocks.OAK_WOOD).noOcclusion());
    }

    @Override
    public MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
        return new SimpleMenuProvider((id, inventory, player) -> {
            return new SkillLearningMenu(id, inventory, ContainerLevelAccess.create(pLevel, pPos));
        }, CONTAINER_TITLE);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
            BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos));
            return InteractionResult.CONSUME;
        }
    }
}
