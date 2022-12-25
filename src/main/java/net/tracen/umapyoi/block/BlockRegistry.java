package net.tracen.umapyoi.block;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tracen.umapyoi.Umapyoi;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            Umapyoi.MODID);
    
    public static final RegistryObject<Block> THREE_GODDESS = BLOCKS.register("three_goddess", ThreeGoddessBlock::new);
    public static final RegistryObject<Block> TRAINING_FACILITY = BLOCKS.register("training_facility", TrainingFacilityBlock::new);
    public static final RegistryObject<Block> SKILL_LEARNING_TABLE = BLOCKS.register("skill_learning_table", SkillLearningTableBlock::new);
    public static final RegistryObject<Block> REGISTER_LECTERN = BLOCKS.register("register_lectern", RetireRegisterLecternBlock::new);
}
