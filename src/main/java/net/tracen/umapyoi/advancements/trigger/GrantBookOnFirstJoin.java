package net.tracen.umapyoi.advancements.trigger;

import com.mojang.serialization.Codec;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.UmapyoiConfig;

import java.util.Optional;

@EventBusSubscriber
public class GrantBookOnFirstJoin extends SimpleCriterionTrigger<GrantBookOnFirstJoin.Instance> {
    @Override
    public Codec<Instance> codec() {
        return Codec.unit(Instance::new);
    }

    public static class Instance implements SimpleInstance {
        public boolean test(ServerPlayer player) {
            return UmapyoiConfig.GRANT_GUIDE_ON_FIRST_JOIN.get();
        }

        @Override
        public Optional<ContextAwarePredicate> player() {
            return Optional.empty();
        }
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, (I) -> I.test(player));
    }

    @SubscribeEvent
    public static void PlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer sp) {
            TriggerRegistry.GRANT_BOOK_ON_FIRST_JOIN.get().trigger(sp);
        }
    }
}
