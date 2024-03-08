package net.tracen.umapyoi.villager;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableSet;

import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.block.BlockRegistry;

public class VillageRegistry {
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES,
            Umapyoi.MODID);

    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister
            .create(ForgeRegistries.VILLAGER_PROFESSIONS, Umapyoi.MODID);

    public static final RegistryObject<PoiType> TRAINER_POI = POI_TYPES.register("trainer_poi",
            () -> createPOI(assembleStates(BlockRegistry.TRAINING_FACILITY.get())));

    public static final RegistryObject<VillagerProfession> TRAINER = PROFESSIONS.register("trainer",
            () -> createProf("trainer", TRAINER_POI, SoundEvents.VILLAGER_WORK_LIBRARIAN));

    private static PoiType createPOI(Collection<BlockState> block) {
        return new PoiType(ImmutableSet.copyOf(block), 1, 1);
    }

    private static VillagerProfession createProf(String name, RegistryObject<PoiType> poi, SoundEvent sound) {
        ResourceKey<PoiType> poiName = Objects.requireNonNull(poi.getKey());
        return new VillagerProfession(
                name,
                holder -> holder.is(poiName),
                holder -> holder.is(poiName),
                ImmutableSet.of(),
                ImmutableSet.of(),
                sound
        );
    }

    private static Collection<BlockState> assembleStates(Block block) {
        return block.getStateDefinition().getPossibleStates().stream().collect(Collectors.toList());
    }
}
