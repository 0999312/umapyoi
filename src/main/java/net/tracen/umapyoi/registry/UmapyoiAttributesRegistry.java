package net.tracen.umapyoi.registry;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tracen.umapyoi.Umapyoi;

public class UmapyoiAttributesRegistry {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Umapyoi.MODID);

    public static final RegistryObject<Attribute> SPRINT_SPEED = ATTRIBUTES.register("sprint_speed",
            () -> new RangedAttribute("attribute.umapyoi.generic.sprint_speed", 0.7D, 0.0D, 1024.0D).setSyncable(true));

    public static final RegistryObject<Attribute> PARCOOL_EXHAUSTION_PENALTY = ATTRIBUTES.register("parcool_exhaustion_penalty",
            () -> new RangedAttribute("attribute.umapyoi.generic.exhaustion_penalty", 1D, 0D, 1D).setSyncable(true));
}
