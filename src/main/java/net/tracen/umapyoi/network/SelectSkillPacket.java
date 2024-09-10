package net.tracen.umapyoi.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.utils.UmaSoulUtils;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record SelectSkillPacket(String message) implements CustomPacketPayload
{
    public static final Type<SelectSkillPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "select_skill")
    );
    public static final StreamCodec<ByteBuf, SelectSkillPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            SelectSkillPacket::message,
            SelectSkillPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SelectSkillPacket payload, IPayloadContext context) {
        if (context instanceof ServerPayloadContext serverContext) {
            context.enqueueWork(() ->
                                {
                                    ServerPlayer player = serverContext.player();
                                    if (player.isSpectator()) {
                                        return;
                                    }
                                    ItemStack umaSoul = UmapyoiAPI.getUmaSoul(player);
                                    if (!umaSoul.isEmpty()) {

                                        if (payload.message.equals("latter")) {
                                            UmaSoulUtils.selectLatterSkill(umaSoul);
                                        }
                                        else if (payload.message.equals("former")) {
                                            UmaSoulUtils.selectFormerSkill(umaSoul);
                                        }
                                        else {
                                            Umapyoi.getLogger().warn("Some one send a weird packet.");
                                        }

                                    }

                                });
        }
    }
}
