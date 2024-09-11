package net.tracen.umapyoi.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.tracen.umapyoi.block.entity.AbstractPedestalBlockEntity;

public abstract class AbstractPedestalBlock extends BaseEntityBlock
{
    public AbstractPedestalBlock(Properties properties) {
        super(properties);
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

    protected ItemInteractionResult interactBEWithItem(ItemStack stack, Level level, BlockPos pos, Player player, InteractionHand hand, AbstractPedestalBlockEntity blockEntity, boolean checkBook) {
        // Wtf why useItemOn can use EMPTY item???
        // sbmj
        if (stack.isEmpty()) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (blockEntity.isEmpty()) {
            if (hand == InteractionHand.MAIN_HAND && !player.getOffhandItem().isEmpty() && stack.getItem() instanceof BlockItem) {
                return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
            }

            if (checkBook && stack.is(Items.BOOK)) {
                transformOnBook(level, pos);
            }
            else if (blockEntity.addItem(player.getAbilities().instabuild ? stack.copy() : stack)) {
                level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.END_PORTAL_FRAME_FILL,
                                SoundSource.BLOCKS, 1.0F, 0.8F
                );
                return ItemInteractionResult.CONSUME;
            }
            // Impossible to reach
            return ItemInteractionResult.FAIL;
        }
        else {
            player.displayClientMessage(Component.translatable("umapyoi.uma_pedestal.cannot_add_item"), true);
            return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        }
    }

    protected void transformOnBook(Level level, BlockPos pos) {
    }
}
