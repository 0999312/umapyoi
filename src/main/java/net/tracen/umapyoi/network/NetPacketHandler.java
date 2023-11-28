package net.tracen.umapyoi.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
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
    }
}
