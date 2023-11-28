package net.tracen.umapyoi.network;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class SelectSkillPacket {
    private final String message;

    public SelectSkillPacket(FriendlyByteBuf buffer) {
        message = buffer.readUtf(Short.MAX_VALUE);
    }

    public SelectSkillPacket(String message) {
        this.message = message;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.message);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player.isSpectator())
                return;
            ItemStack umaSoul = UmapyoiAPI.getUmaSoul(player);
            if (!umaSoul.isEmpty()) {

                if (this.message.equals("latter")) {
                    UmaSoulUtils.selectLatterSkill(umaSoul);
                } else if (this.message.equals("former")) {
                    UmaSoulUtils.selectFormerSkill(umaSoul);
                } else {
                    Umapyoi.getLogger().warn("Some one send a weird packet.");
                }

            }

        });
        ctx.get().setPacketHandled(true);
    }

}
