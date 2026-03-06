package net.tracen.umapyoi.advancements.trigger;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tracen.umapyoi.Umapyoi;

import java.util.function.Supplier;

public class TriggerRegistry {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS =
            DeferredRegister.create(Registries.TRIGGER_TYPE, Umapyoi.MODID);

    public static Supplier<GrantBookOnFirstJoin> GRANT_BOOK_ON_FIRST_JOIN = TRIGGERS.register("grant_book_on_first_join", GrantBookOnFirstJoin::new);
}
