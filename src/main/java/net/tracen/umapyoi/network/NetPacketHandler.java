package net.tracen.umapyoi.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.tracen.umapyoi.Umapyoi;

@EventBusSubscriber(modid = Umapyoi.MODID, bus = EventBusSubscriber.Bus.MOD)
public class NetPacketHandler
{
    public static final String PROTOCOL_VERSION = "1.0";

    @SubscribeEvent
    public static void onNetworkRegistry(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);
        registrar.playToServer(
                         UseSkillPacket.TYPE,
                         UseSkillPacket.STREAM_CODEC,
                         UseSkillPacket::handle
                 )
                 .playToServer(
                         SelectSkillPacket.TYPE,
                         SelectSkillPacket.STREAM_CODEC,
                         SelectSkillPacket::handle
                 )
                 .playToServer(
                         SetupResultPacket.TYPE,
                         SetupResultPacket.STREAM_CODEC,
                         SetupResultPacket::handle
                 )
                 .playToServer(
                         EmptyResultPacket.TYPE,
                         EmptyResultPacket.STREAM_CODEC,
                         EmptyResultPacket::handle
                 );
    }
}
