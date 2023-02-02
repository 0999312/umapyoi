package net.tracen.umapyoi.network;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.NetworkEvent;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.capability.CapabilityRegistry;
import net.tracen.umapyoi.events.SkillEvent;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
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
                    UmaSkill selectedSkill = UmaSkillRegistry.REGISTRY.get().getValue(cap.getSelectedSkill());
                    if(selectedSkill == null) {
                        player.displayClientMessage(new TranslatableComponent("umapyoi.unknown_skill"), true);
                        return;
                    }
                    if(MinecraftForge.EVENT_BUS.post(new SkillEvent.UseSkillEvent(cap.getSelectedSkill(), player.getLevel(), player)))
                        return ;
                    if(cap.getActionPoint() >= selectedSkill.getActionPoint()) {
                        player.connection.send(new ClientboundSoundPacket(selectedSkill.getSound(), SoundSource.PLAYERS, 
                                player.getX(), 
                                player.getY(), 
                                player.getZ(), 
                                1F, 1F));
                        selectedSkill.applySkill(player.getLevel(), player);
                        cap.setActionPoint(cap.getActionPoint() - selectedSkill.getActionPoint());
                        MinecraftForge.EVENT_BUS.post(new SkillEvent.ApplySkillEvent(selectedSkill.getRegistryName(), player.getLevel(), player));
                    } else {
                        player.displayClientMessage(new TranslatableComponent("umapyoi.not_enough_ap"), true);
                    }
                    
                });
            }

        });
        ctx.get().setPacketHandled(true);
    }

}
