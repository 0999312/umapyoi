package net.tracen.umapyoi.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringUtil;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.container.UmaSelectMenu;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record SetupResultPacket(String message) implements CustomPacketPayload
{
    public static final Type<SetupResultPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "setup_result")
    );
    public static final StreamCodec<ByteBuf, SetupResultPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            SetupResultPacket::message,
            SetupResultPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SetupResultPacket payload, IPayloadContext context) {
       if (context instanceof ServerPayloadContext serverContext)
        {
            context.enqueueWork(() ->
                            {
                                ServerPlayer player = serverContext.player();
                                if (player.containerMenu instanceof UmaSelectMenu menu) {
                                    String s = StringUtil.filterText(payload.message);
                                    Umapyoi.getLogger().info("Packet received:{}", s);
                                    if (s.length() <= 50) {
                                        menu.setItemName(ResourceLocation.tryParse(s));
                                    }
                                }

                            });
    }
    }
}
