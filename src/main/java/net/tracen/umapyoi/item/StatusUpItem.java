package net.tracen.umapyoi.item;

import java.util.function.Consumer;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.capability.CapabilityRegistry;
import net.tracen.umapyoi.registry.umadata.UmaStatus;

public class StatusUpItem extends Item {
    private final Consumer<UmaStatus> consumer;

    public StatusUpItem(Properties prop, Consumer<UmaStatus> consumer) {
        super(prop);
        this.consumer = consumer;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide())
            return super.use(level, player, hand);
        ItemStack uma_soul = UmapyoiAPI.getUmaSoul(player);
        if (!uma_soul.isEmpty()) {
            uma_soul.getCapability(CapabilityRegistry.UMACAP).ifPresent(cap -> {
                consumer.accept(cap.getUmaStatus());
                player.getCooldowns().addCooldown(player.getItemInHand(hand).getItem(), 30);
                player.getItemInHand(hand).shrink(1);
            });
        }
        return super.use(level, player, hand);
    }
}
