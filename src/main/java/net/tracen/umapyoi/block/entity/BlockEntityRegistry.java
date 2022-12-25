package net.tracen.umapyoi.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.block.BlockRegistry;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
            .create(ForgeRegistries.BLOCK_ENTITIES, Umapyoi.MODID);

    public static final RegistryObject<BlockEntityType<ThreeGoddessBlockEntity>> THREE_GODDESS = BLOCK_ENTITIES
            .register("three_goddess", () -> BlockEntityType.Builder
                    .of(ThreeGoddessBlockEntity::new, BlockRegistry.THREE_GODDESS.get()).build(null));
    
    public static final RegistryObject<BlockEntityType<TrainingFacilityBlockEntity>> TRAINING_FACILITY = BLOCK_ENTITIES
            .register("training_facility", () -> BlockEntityType.Builder
                    .of(TrainingFacilityBlockEntity::new, BlockRegistry.TRAINING_FACILITY.get()).build(null));
}
