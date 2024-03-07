package net.tracen.umapyoi.network;

import java.util.function.Supplier;

import net.minecraft.SharedConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.container.UmaSelectMenu;

public class SetupResultPacket {
    private final String message;

    public SetupResultPacket(FriendlyByteBuf buffer) {
        message = buffer.readUtf(Short.MAX_VALUE);
    }

    public SetupResultPacket(String message) {
        this.message = message;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.message);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player.containerMenu instanceof UmaSelectMenu menu) {
                String s = SharedConstants.filterText(this.message);
                Umapyoi.getLogger().info("Packet received:{}",s);
                if (s.length() <= 50) {
                    menu.setItemName(ResourceLocation.tryParse(s));
                }
           }
            
        });
        ctx.get().setPacketHandled(true);
    }

}
