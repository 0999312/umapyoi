package net.tracen.umapyoi.compat.parcool;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.events.ApplyUmasoulAttributeEvent;
import net.tracen.umapyoi.registry.UmapyoiAttributesRegistry;
import net.tracen.umapyoi.utils.UmaStatusUtils;

import static com.alrex.parcool.api.Attributes.MAX_STAMINA;

public class Parcool {
    @SubscribeEvent
    public static void onApplyUmasoulAttributeEvent(ApplyUmasoulAttributeEvent event) {
        if (!UmapyoiConfig.ENABLE_PARCOOL_COMPATIBILITY.get()) return;
        Multimap<Attribute, AttributeModifier> attributes = event.getAttributes();
        attributes.put(MAX_STAMINA.get(),
                new AttributeModifier(event.getUUID(), "stamina_parcool_stamina_bonus",
                        event.getExactProperty(UmaStatusUtils.StatusType.STAMINA, UmapyoiConfig.MAX_PARCOOL_STAMINA_BONUS.get()),
                        UmapyoiConfig.UMASOUL_STAMINA_PRECENT_ENABLE.get() ?
                                AttributeModifier.Operation.MULTIPLY_TOTAL :
                                AttributeModifier.Operation.ADDITION));
        attributes.put(UmapyoiAttributesRegistry.PARCOOL_EXHAUSTION_PENALTY.get(),
                new AttributeModifier(event.getUUID(), "guts_parcool_exhaustion_penalty",
                        -event.getExactProperty(UmaStatusUtils.StatusType.GUTS, 1d),
                        AttributeModifier.Operation.MULTIPLY_TOTAL));
    }
}
