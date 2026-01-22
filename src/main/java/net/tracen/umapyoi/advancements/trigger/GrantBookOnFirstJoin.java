package net.tracen.umapyoi.advancements.trigger;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.UmapyoiConfig;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber
public class GrantBookOnFirstJoin extends SimpleCriterionTrigger<GrantBookOnFirstJoin.Instance> {
    private static final ResourceLocation ID = new ResourceLocation(Umapyoi.MODID, "grant_book_on_first_join");

    @Nonnull
    @Override protected Instance createInstance(JsonObject json, ContextAwarePredicate playerPredicate,
                                                DeserializationContext context) {
        return new Instance(playerPredicate);
    }

    @Nonnull
    @Override
    public ResourceLocation getId() { return ID; }

    public static class Instance extends AbstractCriterionTriggerInstance {
        public Instance(ContextAwarePredicate playerPredicate) {
            super(ID, playerPredicate);
        }

        public boolean test(ServerPlayer player) {
            return UmapyoiConfig.GRANT_GUIDE_ON_FIRST_JOIN.get();
        }
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, (I) -> I.test(player));
    }

    @SubscribeEvent
    public static void PlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer sp) {
            TriggerRegistry.GRANT_BOOK_ON_FIRST_JOIN.trigger(sp);
        }
    }
}
