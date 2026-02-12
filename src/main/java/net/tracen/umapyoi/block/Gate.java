package net.tracen.umapyoi.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.tracen.umapyoi.utils.ThreeBlockPart;

import javax.annotation.Nullable;

public class Gate extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<ThreeBlockPart> PART = EnumProperty.create("part", ThreeBlockPart.class);

    protected static final VoxelShape LOW_BASE = Shapes.or(
            Block.box(0, 4, 0, 1, 10, 1),
            Block.box(0, 4, 15, 1, 10, 16),
            Block.box(15, 4, 0, 16, 10, 1),
            Block.box(15, 4, 15, 16, 10, 16)
    );

    protected static final VoxelShape LOW_NS_AABB = Shapes.or(
            LOW_BASE,
            Block.box(0, 10, 0, 1, 16, 16),
            Block.box(15, 10, 0, 16, 16, 16)
    );

    protected static final VoxelShape LOW_EW_AABB = Shapes.or(
            LOW_BASE,
            Block.box(0, 10, 0, 16, 16, 1),
            Block.box(0, 10, 15, 16, 16, 16)
    );

    protected static final VoxelShape UP_BASE = Shapes.or(
            Block.box(0, 0, 0, 1, 16, 1),
            Block.box(15, 0, 0, 16, 16, 1),
            Block.box(0, 0, 15, 1, 16, 16),
            Block.box(15, 0, 15, 16, 16, 16)
    );

    protected static final VoxelShape UP_NS_AABB = Shapes.or(
            UP_BASE,
            Block.box(0, 0, 0, 1, 6, 16),
            Block.box(15, 0, 0, 16, 6, 16)
    );

    protected static final VoxelShape UP_EW_AABB = Shapes.or(
            UP_BASE,
            Block.box(0, 0, 0, 16, 6, 1),
            Block.box(0, 0, 15, 16, 6, 16)
    );

    public Gate() {
        super(Properties.copy(Blocks.IRON_BLOCK).noOcclusion());
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        return switch (pState.getValue(PART)) {
            case LOWER -> switch (direction) {
                case NORTH, SOUTH -> LOW_NS_AABB;
                default -> LOW_EW_AABB;
            };
            case MIDDLE -> switch (direction) {
                case NORTH, SOUTH -> UP_NS_AABB;
                default -> LOW_NS_AABB;
            };
            default -> Block.box(0, 0, 0, 16, 16, 16);
        };
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        ThreeBlockPart part = pState.getValue(PART);
        if (pFacing.getAxis() == Direction.Axis.Y) {
            int checkOrdinal = part.ordinal() + (pFacing == Direction.UP ? 1 : -1);
            if (0 <= checkOrdinal && checkOrdinal <= 2) {
                return pFacingState.is(this) && pFacingState.getValue(PART) == ThreeBlockPart.values()[checkOrdinal] ? pState.setValue(FACING, pFacingState.getValue(FACING)) : Blocks.AIR.defaultBlockState();
            }
        }
        return part == ThreeBlockPart.LOWER && pFacing == Direction.DOWN && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    public static void preventCreativeDropFromOtherPart(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        ThreeBlockPart doubleblockhalf = pState.getValue(PART);
        if (doubleblockhalf != ThreeBlockPart.LOWER) {
            for (ThreeBlockPart otherPart: ThreeBlockPart.values()) {
                if (otherPart == doubleblockhalf) continue;
                BlockPos blockPos = new BlockPos(pPos.getX(), pPos.getY() + otherPart.ordinal() - doubleblockhalf.ordinal(), pPos.getZ());
                BlockState blockState = pLevel.getBlockState(blockPos);
                if (blockState.is(pState.getBlock()) && blockState.getValue(PART) == otherPart) {
                    BlockState blockStateReplace = blockState.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                    pLevel.setBlock(blockPos, blockStateReplace, 35);
                    pLevel.levelEvent(pPlayer, 2001, blockPos, Block.getId(blockState));
                }
            }
        }
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pLevel.isClientSide && pPlayer.isCreative()) {
            preventCreativeDropFromOtherPart(pLevel, pPos, pState, pPlayer);
        }

        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return switch (pType) {
            case LAND, AIR -> true;
            default -> false;
        };
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        if (blockpos.getY() < level.getMaxBuildHeight() - 2
                && level.getBlockState(blockpos.above()).canBeReplaced(pContext)
                && level.getBlockState(blockpos.above().above()).canBeReplaced(pContext)) {
            return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection()).setValue(PART, ThreeBlockPart.LOWER);
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        pLevel.setBlock(pPos.above(), pState.setValue(PART, ThreeBlockPart.MIDDLE), 3);
        pLevel.setBlock(pPos.above().above(), pState.setValue(PART, ThreeBlockPart.UPPER), 3);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        BlockState blockstate = pLevel.getBlockState(blockpos);
        return pState.getValue(PART) == ThreeBlockPart.LOWER ? blockstate.isFaceSturdy(pLevel, blockpos, Direction.UP) : blockstate.is(this);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(PART, FACING);
    }
}

