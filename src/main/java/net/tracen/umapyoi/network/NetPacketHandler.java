package net.tracen.umapyoi.network;

import net.minecraft.resources.ResourceLocation;
import net.tracen.umapyoi.Umapyoi;

public class NetPacketHandler {
    public static SimpleChannel INSTANCE;
    public static final String PROTOCOL_VERSION = "1.0";
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessage() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(Umapyoi.MODID, "network"),
                () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

        INSTANCE.messageBuilder(UseSkillPacket.class, nextID(), NetworkDirection.PLAY_TO_SERVER)
                .encoder(UseSkillPacket::toBytes).decoder(UseSkillPacket::new).consumerNetworkThread(UseSkillPacket::handler).add();
        INSTANCE.messageBuilder(SelectSkillPacket.class, nextID(), NetworkDirection.PLAY_TO_SERVER)
                .encoder(SelectSkillPacket::toBytes).decoder(SelectSkillPacket::new)
                .consumerNetworkThread(SelectSkillPacket::handler).add();
        
        INSTANCE.messageBuilder(SetupResultPacket.class, nextID(), NetworkDirection.PLAY_TO_SERVER)
                .encoder(SetupResultPacket::toBytes).decoder(SetupResultPacket::new)
                .consumerNetworkThread(SetupResultPacket::handler).add();
        INSTANCE.messageBuilder(EmptyResultPacket.class, nextID(), NetworkDirection.PLAY_TO_SERVER)
                .encoder(EmptyResultPacket::toBytes).decoder(EmptyResultPacket::new)
                .consumerNetworkThread(EmptyResultPacket::handler).add();
    }
}
