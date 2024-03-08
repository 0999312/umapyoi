package net.tracen.umapyoi.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.block.BlockRegistry;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
            .create(ForgeRegistries.BLOCK_ENTITY_TYPES, Umapyoi.MODID);

    public static final RegistryObject<BlockEntityType<ThreeGoddessBlockEntity>> THREE_GODDESS = BLOCK_ENTITIES
            .register("three_goddess", () -> BlockEntityType.Builder
                    .of(ThreeGoddessBlockEntity::new, BlockRegistry.THREE_GODDESS.get()).build(null));

    public static final RegistryObject<BlockEntityType<TrainingFacilityBlockEntity>> TRAINING_FACILITY = BLOCK_ENTITIES
            .register("training_facility", () -> BlockEntityType.Builder
                    .of(TrainingFacilityBlockEntity::new, BlockRegistry.TRAINING_FACILITY.get()).build(null));

    public static final RegistryObject<BlockEntityType<UmaPedestalBlockEntity>> UMA_PEDESTAL = BLOCK_ENTITIES
            .register("uma_pedestal", () -> BlockEntityType.Builder
                    .of(UmaPedestalBlockEntity::new, BlockRegistry.UMA_PEDESTAL.get()).build(null));

    public static final RegistryObject<BlockEntityType<SupportAlbumPedestalBlockEntity>> SUPPORT_ALBUM_PEDESTAL = BLOCK_ENTITIES
            .register("support_album_pedestal", () -> BlockEntityType.Builder
                    .of(SupportAlbumPedestalBlockEntity::new, BlockRegistry.SUPPORT_ALBUM_PEDESTAL.get()).build(null));
    
    public static final RegistryObject<BlockEntityType<SilverUmaPedestalBlockEntity>> SILVER_UMA_PEDESTAL = BLOCK_ENTITIES
            .register("silver_uma_pedestal", () -> BlockEntityType.Builder
                    .of(SilverUmaPedestalBlockEntity::new, BlockRegistry.SILVER_UMA_PEDESTAL.get()).build(null));

    public static final RegistryObject<BlockEntityType<SilverSupportAlbumPedestalBlockEntity>> SILVER_SUPPORT_ALBUM_PEDESTAL = BLOCK_ENTITIES
            .register("silver_support_album_pedestal", () -> BlockEntityType.Builder
                    .of(SilverSupportAlbumPedestalBlockEntity::new, BlockRegistry.SILVER_SUPPORT_ALBUM_PEDESTAL.get()).build(null));
    public static final RegistryObject<BlockEntityType<UmaStatueBlockEntity>> UMA_STATUES = BLOCK_ENTITIES
            .register("uma_statues", () -> BlockEntityType.Builder
                    .of(UmaStatueBlockEntity::new, BlockRegistry.UMA_STATUES.get()).build(null));
}
