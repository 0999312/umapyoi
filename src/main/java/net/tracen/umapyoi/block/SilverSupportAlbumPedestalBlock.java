package net.tracen.umapyoi.block;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.mojang.serialization.MapCodec;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.tracen.umapyoi.block.entity.BlockEntityRegistry;
import net.tracen.umapyoi.block.entity.SilverSupportAlbumPedestalBlockEntity;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SilverSupportAlbumPedestalBlock extends AbstractPedestalBlock
{
    public static final MapCodec<SilverSupportAlbumPedestalBlock> CODEC = simpleCodec(
            p -> new SilverSupportAlbumPedestalBlock());

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public SilverSupportAlbumPedestalBlock() {
        super(Properties.ofFullCopy(Blocks.STONE).noOcclusion());
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return BlockRegistry.SILVER_UMA_PEDESTAL.toStack();
    }

    @SuppressWarnings("deprecation")
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityRegistry.SILVER_SUPPORT_ALBUM_PEDESTAL.get().create(pos, state);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            BlockEntity tileEntity = level.getBlockEntity(pos);
            if (tileEntity instanceof SilverSupportAlbumPedestalBlockEntity blockEntity) {
                return interactBEWithoutItem(level, pos, player, blockEntity.isEmpty(), blockEntity.removeItem(),
                                             blockEntity
                );
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            BlockEntity tileEntity = level.getBlockEntity(pos);
            if (tileEntity instanceof SilverSupportAlbumPedestalBlockEntity blockEntity) {
                return interactBEWithItem(stack, level, pos, player, hand, blockEntity, false);
            }
        }
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof SilverSupportAlbumPedestalBlockEntity blockEntity) {
                Containers.dropContents(worldIn, pos, blockEntity.getDroppableInventory());
                worldIn.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntity) {

        if (level.isClientSide) {
            return createTickerHelper(blockEntity, BlockEntityRegistry.SILVER_SUPPORT_ALBUM_PEDESTAL.get(),
                                      SilverSupportAlbumPedestalBlockEntity::animationTick
            );
        }
        return createTickerHelper(blockEntity, BlockEntityRegistry.SILVER_SUPPORT_ALBUM_PEDESTAL.get(),
                                  SilverSupportAlbumPedestalBlockEntity::workingTick
        );
    }
}
