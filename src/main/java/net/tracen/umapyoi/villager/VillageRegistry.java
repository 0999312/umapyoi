package net.tracen.umapyoi.villager;

import java.util.Collection;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableSet;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
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
            .create(ForgeRegistries.PROFESSIONS, Umapyoi.MODID);

    public static final RegistryObject<PoiType> TRAINER_POI = POI_TYPES.register("trainer_poi",
            () -> createPOI(new ResourceLocation(Umapyoi.MODID, "trainer_poi"),
                    assembleStates(BlockRegistry.TRAINING_FACILITY.get())));

    public static final RegistryObject<VillagerProfession> TRAINER = PROFESSIONS.register("trainer",
            () -> createProf(new ResourceLocation(Umapyoi.MODID, "trainer"), TRAINER_POI.get(),
                    SoundEvents.VILLAGER_WORK_LIBRARIAN));

    private static PoiType createPOI(ResourceLocation name, Collection<BlockState> block) {
        return new PoiType(name.toString(), ImmutableSet.copyOf(block), 1, 1);
    }

    private static VillagerProfession createProf(ResourceLocation name, PoiType poi, SoundEvent sound) {
        return new VillagerProfession(name.toString(), poi, ImmutableSet.<Item>builder().build(),
                ImmutableSet.<Block>builder().build(), sound);
    }

    private static Collection<BlockState> assembleStates(Block block) {
        return block.getStateDefinition().getPossibleStates().stream().collect(Collectors.toList());
    }
}
