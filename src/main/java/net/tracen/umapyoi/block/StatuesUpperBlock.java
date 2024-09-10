package net.tracen.umapyoi.block;

import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StatuesUpperBlock extends Block {

    private final Holder<Block> bottomBlock;
    
    protected final VoxelShape shape;
    
    public StatuesUpperBlock(Holder<Block> bottom) {
        this(bottom, Shapes.block());
    }
    
    public StatuesUpperBlock(Holder<Block> bottom, VoxelShape shape) {
        super(Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).noOcclusion());
        this.bottomBlock = bottom;
        this.shape = shape;
    }

    
    @Override
    public Item asItem() {
        return this.bottomBlock.value().asItem();
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.INVISIBLE;
    }

    public Holder<Block> getBottomBlock() {
        return bottomBlock;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return this.shape;
    }
    
    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos.below()).is(this.getBottomBlock());
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pLevel.getBlockState(pPos.below()).is(this.getBottomBlock())) {
            pLevel.destroyBlock(pPos.below(), true);
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos,
            boolean pIsMoving) {
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
        return pLevel.getBlockState(pPos.below()).getBlock().use(pLevel.getBlockState(pPos.below()), pLevel,
                pPos.below(), pPlayer, pHand, pHit);
    }
}
