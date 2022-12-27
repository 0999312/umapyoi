package net.tracen.umapyoi.network;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.capability.CapabilityRegistry;
import net.tracen.umapyoi.registry.skills.UmaSkill;

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
            if(player.isSpectator()) return ;
            ItemStack umaSoul = UmapyoiAPI.getUmaSoul(player);
            if (!umaSoul.isEmpty()) {
                umaSoul.getCapability(CapabilityRegistry.UMACAP).ifPresent(cap -> {
                    if(cap.isSkillReady()) {
                        UmaSkill selectedSkill = cap.getSelectedSkill();
                        player.connection.send(new ClientboundSoundPacket(selectedSkill.getSound(), SoundSource.PLAYERS, 
                                player.getX(), 
                                player.getY(), 
                                player.getZ(), 
                                1F, 1F));
                        selectedSkill.applySkill(player.getLevel(), player);
                        cap.setMaxCooldown(selectedSkill.getCooldown());
                        cap.setCooldown(selectedSkill.getCooldown());
                    } else {
                        player.displayClientMessage(new TranslatableComponent("umapyoi.skill_not_ready", cap.getCooldown() / 20), true);
                    }
                    
                });
            }

        });
        ctx.get().setPacketHandled(true);
    }

}
