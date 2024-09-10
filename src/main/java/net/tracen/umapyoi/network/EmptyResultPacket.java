package net.tracen.umapyoi.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.container.UmaSelectMenu;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record EmptyResultPacket(String empty) implements CustomPacketPayload
{
    public static final String MESSAGE = "empty";
    public static final Type<EmptyResultPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "empty_result")
    );
    public static final StreamCodec<ByteBuf, EmptyResultPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            EmptyResultPacket::empty,
            EmptyResultPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static EmptyResultPacket packet() {
        return new EmptyResultPacket(MESSAGE);
    }

    public static void handle(EmptyResultPacket payload, IPayloadContext context) {
        if (context instanceof ServerPayloadContext serverContext) {
            context.enqueueWork(() ->
                                  {
                                      ServerPlayer player = serverContext.player();
                                      if (player.containerMenu instanceof UmaSelectMenu menu) {
                                          menu.setItemName(null);
                                      }
                                  });
        }
    }
}
