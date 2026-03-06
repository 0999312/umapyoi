package net.tracen.umapyoi.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.block.entity.BlockEntityRegistry;
import net.tracen.umapyoi.block.entity.RaceRegisterBlockEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RaceRegisterBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    protected static final VoxelShape SHAPE = Shapes.or(
    		Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), 
    		Block.box(4.0D, 6.0D, 11.0D, 16.0D, 15.0D, 14.0D)
    );
    
    protected static final VoxelShape SHAPE_WEST = Shapes.or(
    		Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), 
    		Block.box(11.0D, 6.0D, 0.0D, 14.0D, 15.0D, 12.0D)
    );
    
    protected static final VoxelShape SHAPE_SOUTH = Shapes.or(
    		Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), 
    		Block.box(0.0D, 6.0D, 2.0D, 12.0D, 15.0D, 5.0D)
    );
    
    protected static final VoxelShape SHAPE_EAST = Shapes.or(
    		Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), 
    		Block.box(2.0D, 6.0D, 4.0D, 5.0D, 15.0D, 16.0D)
    );
    
    public RaceRegisterBlock() {
        super(Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    public static final MapCodec<RaceRegisterBlock> CODEC = simpleCodec((p) -> new RaceRegisterBlock());

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            BlockEntity tileEntity = level.getBlockEntity(pos);

            if (tileEntity instanceof RaceRegisterBlockEntity blockEntity) {
                player.openMenu(blockEntity, pos);
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            BlockEntity tileEntity = level.getBlockEntity(pos);

            if (tileEntity instanceof RaceRegisterBlockEntity blockEntity) {
                player.openMenu(blockEntity, pos);
                return ItemInteractionResult.CONSUME;
            }
        }
        return ItemInteractionResult.SUCCESS;
    }

    @Deprecated
    @Override
    public void onRemove(BlockState state, @Nonnull Level worldIn, @Nonnull BlockPos pos,
                         BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof RaceRegisterBlockEntity blockEntity) {
                Containers.dropContents(worldIn, pos, blockEntity.getDroppableInventory());
                worldIn.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@Nonnull Level level, @Nonnull BlockState state,
                                                                  @Nonnull BlockEntityType<T> blockEntity) {
        return createTickerHelper(blockEntity, BlockEntityRegistry.RACE_REGISTER_BLOCK_ENTITY.get(),
                RaceRegisterBlockEntity::serverTick);
    }

    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return BlockEntityRegistry.RACE_REGISTER_BLOCK_ENTITY.get().create(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
    
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch ((Direction) state.getValue(FACING)) {
        case NORTH:
            return SHAPE;
        case SOUTH:
            return SHAPE_SOUTH;
        case EAST:
            return SHAPE_EAST;
        case WEST:
            return SHAPE_WEST;
        default:
            return SHAPE;
        }
    }

}
