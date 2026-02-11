package net.tracen.umapyoi.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.tracen.umapyoi.block.Gate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(IronBarsBlock.class)
public class IronBarsBlockMixin {
    @Redirect(
            method = "updateShape",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;isFaceSturdy(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z"
            )
    )
    public boolean isFaceSturdyInUpdateShape(BlockState instance, BlockGetter blockGetter, BlockPos blockPos, Direction direction) {
        return umapyoi$isFaceSturdy(instance, blockGetter, blockPos, direction);
    }

    @Redirect(
            method = "getStateForPlacement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;isFaceSturdy(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z"
            )
    )
    public boolean isFaceSturdyInGetShape(BlockState instance, BlockGetter blockGetter, BlockPos blockPos, Direction direction) {
        return umapyoi$isFaceSturdy(instance, blockGetter, blockPos, direction);
    }

    @Unique
    private static boolean umapyoi$isFaceSturdy(BlockState instance, BlockGetter blockGetter, BlockPos blockPos, Direction direction){
        if (instance.isFaceSturdy(blockGetter, blockPos, direction)) return true;
        if (instance.getBlock() instanceof Gate) {
            try {
                Direction facing = instance.getValue(Gate.FACING);
                return switch (facing) {
                    case SOUTH, NORTH -> direction == Direction.EAST || direction == Direction.WEST;
                    case EAST, WEST -> direction == Direction.NORTH || direction == Direction.SOUTH;
                    default -> false;
                };
            } catch (IllegalArgumentException ignore) {}
        }
        return false;
    }
}
