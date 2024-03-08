package net.tracen.umapyoi.network;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.tracen.umapyoi.container.UmaSelectMenu;

public class EmptyResultPacket {
    
    public EmptyResultPacket() {}
    
    public EmptyResultPacket(FriendlyByteBuf buffer) {
        buffer.readUtf(Short.MAX_VALUE);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf("empty");
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player.containerMenu instanceof UmaSelectMenu menu) {
                menu.setItemName(null);
           }
            
        });
        ctx.get().setPacketHandled(true);
    }

}
