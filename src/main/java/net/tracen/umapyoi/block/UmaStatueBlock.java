package net.tracen.umapyoi.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.tracen.umapyoi.block.entity.BlockEntityRegistry;
import net.tracen.umapyoi.block.entity.UmaStatueBlockEntity;

public class UmaStatueBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public UmaStatueBlock() {
		super(Properties.copy(Blocks.STONE).noOcclusion());
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
	}

	@Override
	public RenderShape getRenderShape(BlockState pState) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	/* @SuppressWarnings("deprecation")
	@Override
	public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
		return super.canSurvive(pState, pLevel, pPos)
				&& (pLevel.getBlockState(pPos.above()).is(BlockRegistry.UMA_STATUES_UPPER.get())
						|| pLevel.getBlockState(pPos.above()).isAir());
	} */

	@SuppressWarnings("deprecation")
	@Override
	public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
		FluidState fluidstate = pLevel.getFluidState(pPos.above());
		pLevel.setBlock(pPos.above(), BlockRegistry.UMA_STATUES_UPPER.get().defaultBlockState()
				.setValue(StatuesUpperBlock.WATERLOGGED, fluidstate.is(Fluids.WATER)), UPDATE_ALL);
		super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn,
			BlockHitResult hit) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		if (tileEntity instanceof UmaStatueBlockEntity status) {
			ItemStack heldStack = player.getItemInHand(handIn);

			if (status.isEmpty() || status.isCostumeEmpty()) {
				if (!heldStack.isEmpty() && status.addItem(player.getAbilities().instabuild ? heldStack.copy() : heldStack)) {
					worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.STONE_PLACE,
							SoundSource.BLOCKS, 1.0F, 0.8F);
					return InteractionResult.SUCCESS;
				}

			}
			if (handIn.equals(InteractionHand.MAIN_HAND)) {
				if (!player.isCreative()) {
					if (!player.getInventory().add(status.removeItem())) {
						Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), status.removeItem());
					}
				} else {
					status.removeItem();
				}
				worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.WOOD_HIT, SoundSource.BLOCKS,
						0.25F, 0.5F);
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity tileEntity = worldIn.getBlockEntity(pos);
			if (tileEntity instanceof UmaStatueBlockEntity obon) {
				Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), obon.getStoredItem());
				Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), obon.getCostume());
				worldIn.updateNeighbourForOutputSignal(pos, this);
			}
			super.onRemove(state, worldIn, pos, newState, isMoving);
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockPos blockpos = context.getClickedPos();
		Level level = context.getLevel();
		if (blockpos.getY() < level.getMaxBuildHeight() - 1
				&& level.getBlockState(blockpos.above()).canBeReplaced(context)) {
			FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
			return this.defaultBlockState()
					.setValue(FACING, context.getHorizontalDirection().getOpposite())
					.setValue(WATERLOGGED, fluidstate.is(Fluids.WATER));
		} else {
			return null;
		}
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return BlockEntityRegistry.UMA_STATUES.get().create(pos, state);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, WATERLOGGED);
	}

	@Override
	public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
		if (pDirection == Direction.UP) {
			if (!pNeighborState.is(BlockRegistry.UMA_STATUES_UPPER.get())) {
				return pState.getValue(WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
			}
		}
		if (pState.getValue(WATERLOGGED)) {
			pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
		}
		return !pState.canSurvive(pLevel, pPos) ? (pState.getValue(WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState()) : super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}
}
