package net.tracen.umapyoi.network;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.capability.CapabilityRegistry;

public class UseSkillPacket {
    private final String message;

    public UseSkillPacket(FriendlyByteBuf buffer) {
        message = buffer.readUtf(Short.MAX_VALUE);
    }

    public UseSkillPacket(String message) {
        this.message = message;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(this.message);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            ItemStack umaSoul = UmapyoiAPI.getUmaSoul(player);
            if (!umaSoul.isEmpty()) {
                umaSoul.getCapability(CapabilityRegistry.UMACAP).ifPresent(cap -> {
                    if(cap.isSkillReady()) {
                        cap.getSelectedSkill().applySkill(player.getLevel(), player);
                        cap.setCooldown(cap.getSelectedSkill().getCooldown());
                    } else {
                        player.displayClientMessage(new TextComponent(String.format("Not ready : %d", cap.getCooldown())), false);
                    }
                    
                });
            }

        });
        ctx.get().setPacketHandled(true);
    }

}
