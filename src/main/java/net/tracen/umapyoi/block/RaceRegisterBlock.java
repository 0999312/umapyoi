package net.tracen.umapyoi.block;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkHooks;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.block.entity.BlockEntityRegistry;
import net.tracen.umapyoi.block.entity.RaceRegisterBlockEntity;

import javax.annotation.Nullable;
import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = Umapyoi.MODID)
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
        super(Properties.copy(Blocks.IRON_BLOCK).noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
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

    @Nonnull
    @Override
    @Deprecated
    public InteractionResult use(@Nonnull BlockState state, Level world, @Nonnull BlockPos pos, @Nonnull Player player,
                                 @Nonnull InteractionHand handIn, @Nonnull BlockHitResult result) {
        if (!world.isClientSide) {
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof RaceRegisterBlockEntity blockEntity) {
                NetworkHooks.openScreen((ServerPlayer) player, blockEntity, pos);
            }
        }
        return InteractionResult.SUCCESS;
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
