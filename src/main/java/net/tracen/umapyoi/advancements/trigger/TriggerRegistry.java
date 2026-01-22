package net.tracen.umapyoi.advancements.trigger;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.ArrayList;

public class TriggerRegistry {
    private static ArrayList<SimpleCriterionTrigger<?>> REGISTRY;

    public static GrantBookOnFirstJoin GRANT_BOOK_ON_FIRST_JOIN = (GrantBookOnFirstJoin) Register(new GrantBookOnFirstJoin());

    public static SimpleCriterionTrigger<?> Register(SimpleCriterionTrigger<?> trigger) {
        if (REGISTRY == null) REGISTRY = new ArrayList<>();
        REGISTRY.add(trigger);
        return trigger;
    }

    public static void registerAll(FMLCommonSetupEvent evt) {
        evt.enqueueWork(() -> REGISTRY.forEach(CriteriaTriggers::register));
    }
}
