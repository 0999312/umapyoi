package net.tracen.umapyoi.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.tracen.umapyoi.block.entity.BlockEntityRegistry;
import net.tracen.umapyoi.block.entity.GateEntity;
import net.tracen.umapyoi.registry.SoundRegistry;

import javax.annotation.Nullable;

public class GateDoor extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    protected static final VoxelShape SOUTH_AABB = Block.box(0, 4, 7, 16, 16, 10);
    protected static final VoxelShape NORTH_AABB = Block.box(0, 4, 6, 16, 16, 9);
    protected static final VoxelShape WEST_AABB = Block.box(6, 4, 0, 9, 16, 16);
    protected static final VoxelShape EAST_AABB = Block.box(7, 4, 0, 10, 16, 16);
    protected static final VoxelShape SOUTH_COLLISION_AABB = Block.box(0, 0, 7, 16, 24, 10);
    protected static final VoxelShape NORTH_COLLISION_AABB = Block.box(0, 0, 6, 16, 24, 9);
    protected static final VoxelShape WEST_COLLISION_AABB = Block.box(6, 0, 0, 9, 24, 16);
    protected static final VoxelShape EAST_COLLISION_AABB = Block.box(7, 0, 0, 10, 24, 16);

    protected static final VoxelShape SOUTH_OPEN_AABB = Shapes.or(
            Block.box(15, 4, 7, 16, 16, 15.5),
            Block.box(0, 4, 7, 1, 16, 15.5)
    );
    protected static final VoxelShape NORTH_OPEN_AABB = Shapes.or(
            Block.box(15, 4, 0.5, 16, 16, 9),
            Block.box(0, 4, 0.5, 1, 16, 9)
    );
    protected static final VoxelShape WEST_OPEN_AABB = Shapes.or(
            Block.box(0.5, 4, 15, 9, 16, 16),
            Block.box(0.5, 4, 0, 9, 16, 1)
    );
    protected static final VoxelShape EAST_OPEN_AABB = Shapes.or(
            Block.box(7, 4, 15, 15.5, 16, 16),
            Block.box(7, 4, 0, 15.5, 16, 1)
    );
    protected static final VoxelShape SOUTH_OPEN_COLLISION_AABB = Shapes.or(
            Block.box(15, 0, 7, 16, 24, 15.5),
            Block.box(0, 0, 7, 1, 24, 15.5)
    );
    protected static final VoxelShape NORTH_OPEN_COLLISION_AABB = Shapes.or(
            Block.box(15, 0, 0.5, 16, 24, 9),
            Block.box(0, 0, 0.5, 1, 24, 9)
    );
    protected static final VoxelShape WEST_OPEN_COLLISION_AABB = Shapes.or(
            Block.box(0.5, 0, 15, 9, 24, 16),
            Block.box(0.5, 0, 0, 9, 24, 1)
    );
    protected static final VoxelShape EAST_OPEN_COLLISION_AABB = Shapes.or(
            Block.box(7, 0, 15, 15.5, 24, 16),
            Block.box(7, 0, 0, 15.5, 24, 1)
    );

    public GateDoor() {
        super(Properties.copy(Blocks.IRON_BARS).noOcclusion());
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        boolean open = pState.getValue(OPEN);
        return switch (direction) {
            case SOUTH -> open ? SOUTH_OPEN_AABB : SOUTH_AABB;
            case WEST -> open ? WEST_OPEN_AABB : WEST_AABB;
            case NORTH -> open ? NORTH_OPEN_AABB : NORTH_AABB;
            default -> open ? EAST_OPEN_AABB : EAST_AABB;
        };
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        boolean open = pState.getValue(OPEN);
        return switch (direction) {
            case SOUTH -> open ? SOUTH_OPEN_COLLISION_AABB : SOUTH_COLLISION_AABB;
            case WEST -> open ? WEST_OPEN_COLLISION_AABB : WEST_COLLISION_AABB;
            case NORTH -> open ? NORTH_OPEN_COLLISION_AABB : NORTH_COLLISION_AABB;
            default -> open ? EAST_OPEN_COLLISION_AABB : EAST_COLLISION_AABB;
        };
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        switch (pType) {
            case LAND, AIR:
                return pState.getValue(OPEN);
            default:
                return false;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        boolean flag = level.hasNeighborSignal(blockpos);
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection()).setValue(POWERED, flag).setValue(OPEN, flag);
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        boolean flag = pLevel.hasNeighborSignal(pPos);
        if (!this.defaultBlockState().is(pBlock) && flag != pState.getValue(POWERED)) {
            if (flag != pState.getValue(OPEN)) {
                this.playSound(null, pLevel, pPos, flag);
                pLevel.gameEvent(null, flag ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pPos);
            }

            pLevel.setBlock(pPos, pState.setValue(POWERED, Boolean.valueOf(flag)).setValue(OPEN, Boolean.valueOf(flag)), 2);
        }
    }

    public void playSound(Entity entity, Level level, BlockPos pos, boolean isOpen) {
        level.playSound(entity, pos, isOpen ? SoundRegistry.GATE_OPEN.get() : SoundRegistry.GATE_CLOSE.get(), SoundSource.BLOCKS, 1.0F, 1.0f);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        BlockState blockstate = pLevel.getBlockState(blockpos);
        return blockstate.isFaceSturdy(pLevel, blockpos, Direction.UP);
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FACING, OPEN, POWERED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return BlockEntityRegistry.GATE.get().create(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide)
            return createTickerHelper(pBlockEntityType, BlockEntityRegistry.GATE.get(), GateEntity::animationTick);
        return null;
    }
}
