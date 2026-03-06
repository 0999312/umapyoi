package net.tracen.umapyoi.mixin;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;
import net.tracen.umapyoi.events.PlayerSleepInBedEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ServerPlayer.class)
public class ServerPlayerBackportMixin {
    @Inject(method = "startSleepInBed", at = @At("HEAD"), cancellable = true)
    public void backportMethod(BlockPos at, CallbackInfoReturnable<Either<Player.BedSleepingProblem, Unit>> cir) {
        Optional<BlockPos> optAt = Optional.of(at);
        Player.BedSleepingProblem ret = NeoForge.EVENT_BUS.post(new PlayerSleepInBedEvent((ServerPlayer) (Object) this, optAt)).getResultStatus();
        if (ret != null) {
            cir.setReturnValue(Either.left(ret));
            cir.cancel();
        }
    }
}
