package net.tracen.umapyoi.block;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.tracen.umapyoi.block.entity.BlockEntityRegistry;
import net.tracen.umapyoi.block.entity.SupportAlbumPedestalBlockEntity;

public class SupportAlbumPedestalBlock extends AbstractPedestalBlock {
    public SupportAlbumPedestalBlock() {
        super(Properties.copy(Blocks.STONE).noOcclusion());
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos,
            Player player) {
        return BlockRegistry.UMA_PEDESTAL.get().getCloneItemStack(state, target, level, pos, player);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityRegistry.SUPPORT_ALBUM_PEDESTAL.get().create(pos, state);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            BlockEntity tileEntity = level.getBlockEntity(pos);
            if (tileEntity instanceof SupportAlbumPedestalBlockEntity blockEntity) {
                return interactBEWithoutItem(level, pos, player, blockEntity.isEmpty(), blockEntity.removeItem()
                );
            }
        }
        return InteractionResult.SUCCESS;
    }


    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            BlockEntity tileEntity = level.getBlockEntity(pos);
            if (tileEntity instanceof SupportAlbumPedestalBlockEntity blockEntity) {
                return interactBEWithItem(stack, level, pos, player, hand, blockEntity, false);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof SupportAlbumPedestalBlockEntity blockEntity) {
                Containers.dropContents(worldIn, pos, blockEntity.getDroppableInventory());
                worldIn.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
            BlockEntityType<T> blockEntity) {

        if (level.isClientSide) {
            return createTickerHelper(blockEntity, BlockEntityRegistry.SUPPORT_ALBUM_PEDESTAL.get(),
                    SupportAlbumPedestalBlockEntity::animationTick);
        }
        return createTickerHelper(blockEntity, BlockEntityRegistry.SUPPORT_ALBUM_PEDESTAL.get(),
                SupportAlbumPedestalBlockEntity::workingTick);
    }
}
