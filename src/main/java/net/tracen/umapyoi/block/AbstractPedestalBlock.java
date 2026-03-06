package net.tracen.umapyoi.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.tracen.umapyoi.block.entity.AbstractPedestalBlockEntity;

public abstract class AbstractPedestalBlock extends BaseEntityBlock {
    protected AbstractPedestalBlock(Properties pProperties) {
        super(pProperties);
    }

    protected InteractionResult interactBEWithoutItem(Level level, BlockPos pos, Player player, boolean empty, ItemStack itemStack) {
        if (empty) {
            return InteractionResult.PASS;
        }
        else {
            if (!player.getInventory().add(itemStack)) {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(),
                        itemStack
                );
            }

            level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP,
                    SoundSource.BLOCKS, 0.25F, 0.5F
            );
            // Server needs consume
            return InteractionResult.CONSUME;
        }
    }

    protected InteractionResult interactBEWithItem(ItemStack stack, Level level, BlockPos pos, Player player, InteractionHand hand, AbstractPedestalBlockEntity blockEntity, boolean checkBook) {
        // Wtf why useItemOn can use EMPTY item???
        // sbmj
        if (stack.isEmpty()) {
            return InteractionResult.PASS;
        }
        if (blockEntity.isEmpty()) {
            if (hand == InteractionHand.MAIN_HAND && !player.getOffhandItem().isEmpty() && stack.getItem() instanceof BlockItem) {
                return InteractionResult.PASS;
            }

            if (checkBook && stack.is(Items.BOOK)) {
                transformOnBook(level, pos);
            }
            else if (blockEntity.addItem(player.getAbilities().instabuild ? stack.copy() : stack)) {
                level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.END_PORTAL_FRAME_FILL,
                        SoundSource.BLOCKS, 1.0F, 0.8F
                );
                return InteractionResult.SUCCESS;
            }
            // Impossible to reach
            return InteractionResult.FAIL;
        }
        else {
            player.displayClientMessage(Component.translatable("umapyoi.uma_pedestal.cannot_add_item"), true);
            return InteractionResult.PASS;
        }
    }

    protected abstract InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult);
    protected abstract InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult);

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack heldStack = pPlayer.getItemInHand(pHand);
        ItemStack offhandStack = pPlayer.getOffhandItem();
        if (heldStack.isEmpty() && offhandStack.isEmpty()) {
            return useWithoutItem(pState, pLevel, pPos, pPlayer, pHit);
        } else {
            return useItemOn(heldStack, pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
    }

    protected void transformOnBook(Level level, BlockPos pos) {
    }
}
