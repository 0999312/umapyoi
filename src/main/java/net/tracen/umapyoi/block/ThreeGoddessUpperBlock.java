package net.tracen.umapyoi.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ThreeGoddessUpperBlock extends Block {

    public ThreeGoddessUpperBlock() {
        super(Properties.copy(Blocks.POLISHED_ANDESITE).noOcclusion());
    }
    
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.INVISIBLE;
    }
    
    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos.below()).is(BlockRegistry.THREE_GODDESS.get());
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if(pLevel.getBlockState(pPos.below()).is(BlockRegistry.THREE_GODDESS.get())) {
            pLevel.destroyBlock(pPos.below(), true);
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
    
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (!pLevel.isClientSide) {
           if (!pState.canSurvive(pLevel, pPos)) {
              pLevel.removeBlock(pPos, false);
           }
        }
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
            BlockHitResult pHit) {
        return pLevel.getBlockState(pPos.below()).getBlock().use(pLevel.getBlockState(pPos.below()), pLevel, pPos.below(), pPlayer, pHand, pHit);
    }
}
