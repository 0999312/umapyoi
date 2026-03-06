package net.tracen.umapyoi.data.loot;

import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.tracen.umapyoi.Umapyoi;

import java.util.function.Supplier;

public class GLMRegistry {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLM = DeferredRegister.create(NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Umapyoi.MODID);
    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> ADD_LOOT_TABLE = GLM.register("add_loot_table", () -> UmapyoiAddLootTableModifier.CODEC);
}
