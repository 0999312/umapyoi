package net.tracen.umapyoi.block;

import javax.annotation.Nullable;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.tracen.umapyoi.block.entity.BlockEntityRegistry;
import net.tracen.umapyoi.block.entity.UmaPedestalBlockEntity;

public class UmaPedestalBlock extends BaseEntityBlock {
    public UmaPedestalBlock() {
        super(Properties.copy(Blocks.STONE).noOcclusion());
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityRegistry.UMA_PEDESTAL.get().create(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn,
            BlockHitResult result) {
        if (!world.isClientSide) {
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof UmaPedestalBlockEntity blockEntity) {
                ItemStack heldStack = player.getItemInHand(handIn);
                ItemStack offhandStack = player.getOffhandItem();
                if (blockEntity.isEmpty()) {
                    if (!offhandStack.isEmpty()) {
                        if (handIn.equals(InteractionHand.MAIN_HAND) && !(heldStack.getItem() instanceof BlockItem)) {
                            return InteractionResult.PASS; // Pass to off-hand if that item is placeable
                        }
                    }
                    if (heldStack.isEmpty()) {
                        return InteractionResult.PASS;
                    } else if (heldStack.is(Items.BOOK)) {
                        if (player instanceof ServerPlayer serverPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, heldStack);
                        }
                        world.destroyBlock(pos, false);
                        world.setBlock(pos, BlockRegistry.SUPPORT_ALBUM_PEDESTAL.get().defaultBlockState(), UPDATE_ALL);
                    } else if (blockEntity.addItem(player.getAbilities().instabuild ? heldStack.copy() : heldStack)) {
                        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.END_PORTAL_FRAME_FILL,
                                SoundSource.BLOCKS, 1.0F, 0.8F);
                        if (player instanceof ServerPlayer serverPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, heldStack);
                        }
                        return InteractionResult.SUCCESS;
                    }
                } else {
                    if (heldStack.isEmpty()) {
                        if (!player.getInventory().add(blockEntity.removeItem())) {
                            Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(),
                                    blockEntity.removeItem());
                        }

                        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP,
                                SoundSource.BLOCKS, 0.25F, 0.5F);
                        return InteractionResult.SUCCESS;
                    } else {
                        player.displayClientMessage(new TranslatableComponent("umapyoi.uma_pedestal.cannot_add_item"),
                                true);
                        return InteractionResult.PASS;
                    }

                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof UmaPedestalBlockEntity blockEntity) {
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
            return createTickerHelper(blockEntity, BlockEntityRegistry.UMA_PEDESTAL.get(),
                    UmaPedestalBlockEntity::animationTick);
        }
        return createTickerHelper(blockEntity, BlockEntityRegistry.UMA_PEDESTAL.get(),
                UmaPedestalBlockEntity::workingTick);
    }
}
