package net.tracen.umapyoi.block;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.tracen.umapyoi.block.entity.BlockEntityRegistry;
import net.tracen.umapyoi.block.entity.ThreeGoddessBlockEntity;

public class ThreeGoddessBlock extends BaseEntityBlock {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public ThreeGoddessBlock() {
		super(Properties.copy(Blocks.POLISHED_ANDESITE).noOcclusion());
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	public RenderShape getRenderShape(BlockState pState) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	/* @Override
	public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
		return pLevel.getBlockState(pPos.above()).is(BlockRegistry.THREE_GODDESS_UPPER.get())
				|| pLevel.getBlockState(pPos.above()).isAir();
	} */

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, WATERLOGGED);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
		FluidState fluidstate = pLevel.getFluidState(pPos.above());
		pLevel.setBlock(pPos.above(), BlockRegistry.THREE_GODDESS_UPPER.get().defaultBlockState()
				.setValue(StatuesUpperBlock.WATERLOGGED, fluidstate.is(Fluids.WATER)), UPDATE_ALL);
		super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return BlockEntityRegistry.THREE_GODDESS.get().create(pos, state);
	}

	@Override
	public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
		if (pDirection == Direction.UP) {
			if (!pNeighborState.is(BlockRegistry.THREE_GODDESS_UPPER.get())) {
				return pState.getValue(WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
			}
		}
		if (pState.getValue(WATERLOGGED)) {
			pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
		}
		return !pState.canSurvive(pLevel, pPos) ? (pState.getValue(WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState()) : super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
	}

	@Override
	public FluidState getFluidState(BlockState pState) {
		return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockPos blockpos = context.getClickedPos();
		Level level = context.getLevel();
		if (blockpos.getY() < level.getMaxBuildHeight() - 1
				&& level.getBlockState(blockpos.above()).canBeReplaced(context)) {
			BlockState state = this.defaultBlockState().setValue(FACING,
					context.getHorizontalDirection().getOpposite())
					.setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).is(Fluids.WATER));
			return state;
		} else {
			return null;
		}
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn,
			BlockHitResult result) {
		if (!world.isClientSide) {
			BlockEntity tileEntity = world.getBlockEntity(pos);
			if (tileEntity instanceof ThreeGoddessBlockEntity blockEntity) {
				NetworkHooks.openScreen((ServerPlayer) player, blockEntity, pos);
			}
		}
		return InteractionResult.SUCCESS;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity tileEntity = worldIn.getBlockEntity(pos);
			if (tileEntity instanceof ThreeGoddessBlockEntity blockEntity) {
				Containers.dropContents(worldIn, pos, blockEntity.getDroppableInventory());
				worldIn.updateNeighbourForOutputSignal(pos, this);
			}
			if (worldIn.getBlockState(pos.above()).is(BlockRegistry.THREE_GODDESS_UPPER.get())) {
				worldIn.removeBlock(pos.above(), false);
			}
			super.onRemove(state, worldIn, pos, newState, isMoving);
		}
	}

	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
			BlockEntityType<T> blockEntity) {
		if (level.isClientSide) {
			return createTickerHelper(blockEntity, BlockEntityRegistry.THREE_GODDESS.get(),
					ThreeGoddessBlockEntity::animationTick);
		}
		return createTickerHelper(blockEntity, BlockEntityRegistry.THREE_GODDESS.get(),
				ThreeGoddessBlockEntity::workingTick);
	}
}
