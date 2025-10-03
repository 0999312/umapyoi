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
            () -> new RangedAttribute("attribute.umapyoi.generic.sprint_speed", (double)0.7F, 0.0D, 1024.0D).setSyncable(true));
}
