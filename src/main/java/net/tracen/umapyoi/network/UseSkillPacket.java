package net.tracen.umapyoi.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.events.SkillEvent;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.skills.UmaSkill;
import net.tracen.umapyoi.utils.UmaSoulUtils;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record UseSkillPacket(String message) implements CustomPacketPayload
{
    public static final Type<UseSkillPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "use_skill")
    );
    public static final StreamCodec<ByteBuf, UseSkillPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            UseSkillPacket::message,
            UseSkillPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(UseSkillPacket payload, IPayloadContext context) {
        if (context instanceof ServerPayloadContext serverCtx) {
            context.enqueueWork(() ->
                                {
                                    ServerPlayer player = serverCtx.player();
                                    if (player.isSpectator()) {
                                        return;
                                    }
                                    ItemStack umaSoul = UmapyoiAPI.getUmaSoul(player);

                                    if (!umaSoul.isEmpty()) {
                                        ResourceLocation selectedSkillName = UmaSoulUtils.getSelectedSkill(umaSoul);
                                        UmaSkill selectedSkill = UmaSkillRegistry.REGISTRY.get(selectedSkillName);
                                        if (selectedSkill == null) {
                                            player.displayClientMessage(Component.translatable("umapyoi.unknown_skill"),
                                                                        true
                                            );
                                            return;
                                        }
                                        if (NeoForge.EVENT_BUS
                                                .post(new SkillEvent.UseSkillEvent(
                                                              selectedSkillName,
                                                              player.level(),
                                                              player
                                                      )
                                                ).isCanceled()) {
                                            return;
                                        }
                                        int ap = UmaSoulUtils.getActionPoint(umaSoul);
                                        if (ap >= selectedSkill.getActionPoint()) {
                                            player.connection.send(new ClientboundSoundPacket(
                                                    BuiltInRegistries.SOUND_EVENT.getHolder(
                                                            selectedSkill.getSound().getLocation()).get(),
                                                    SoundSource.PLAYERS,
                                                    player.getX(), player.getY(), player.getZ(), 1F, 1F,
                                                    player.getRandom().nextLong()
                                            ));
                                            selectedSkill.applySkill(player.level(), player);
                                            UmaSoulUtils.setActionPoint(umaSoul, ap - selectedSkill.getActionPoint());
                                            NeoForge.EVENT_BUS.post(
                                                    new SkillEvent.ApplySkillEvent(
                                                            UmaSkillRegistry.REGISTRY.getKey(selectedSkill),
                                                            player.level(), player
                                                    ));
                                        }
                                        else {
                                            player.displayClientMessage(Component.translatable("umapyoi.not_enough_ap"),
                                                                        true
                                            );
                                        }
                                    }
                                });
        }
    }
}
