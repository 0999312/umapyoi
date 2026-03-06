package net.tracen.umapyoi.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.Optional;

public class PlayerSleepInBedEvent extends PlayerEvent {
    private Player.BedSleepingProblem result = null;
    private final Optional<BlockPos> pos;

    public PlayerSleepInBedEvent(Player player, Optional<BlockPos> pos)
    {
        super(player);
        this.pos = pos;
    }

    public Player.BedSleepingProblem getResultStatus()
    {
        return result;
    }

    public void setResult(Player.BedSleepingProblem result)
    {
        this.result = result;
    }

    public BlockPos getPos()
    {
        return pos.orElse(null);
    }

    public Optional<BlockPos> getOptionalPos() {
        return pos;
    }
}
