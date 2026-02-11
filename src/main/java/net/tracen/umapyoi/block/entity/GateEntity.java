package net.tracen.umapyoi.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.tracen.umapyoi.block.Gate;

import javax.annotation.Nonnull;

public class GateEntity extends BlockEntity {
    public GateEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegistry.GATE.get(), pPos, pBlockState);
        this.open = safeGetOpen(pBlockState) ? MAX_OPEN : 0;
    }

    public static int MAX_OPEN = 3;
    public int open = 0;

    public static void animationTick(
            Level level,
            BlockPos pos,
            BlockState state,
            @Nonnull GateEntity blockEntity
    ) {
        blockEntity.animationTick(level, pos, state);
    }

    public static boolean safeGetOpen(BlockState state) {
        try {
            return state.getValue(Gate.OPEN);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public void animationTick(Level level, BlockPos pos, BlockState state) {
        open = Mth.clamp(open + (safeGetOpen(state) ? 1 : -1), 0, MAX_OPEN);
    }
}
