package net.tracen.umapyoi.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tracen.umapyoi.Umapyoi;

public class BlockRegistry {
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Umapyoi.MODID);
	public static final DeferredBlock<SilverUmaPedestalBlock> SILVER_UMA_PEDESTAL = BLOCKS
			.register("silver_uma_pedestal", SilverUmaPedestalBlock::new);
	public static final DeferredBlock<SilverSupportAlbumPedestalBlock> SILVER_SUPPORT_ALBUM_PEDESTAL = BLOCKS
			.register("silver_support_album_pedestal", SilverSupportAlbumPedestalBlock::new);
	public static final DeferredBlock<UmaPedestalBlock> UMA_PEDESTAL = BLOCKS.register("uma_pedestal",
			UmaPedestalBlock::new);
	public static final DeferredBlock<SupportAlbumPedestalBlock> SUPPORT_ALBUM_PEDESTAL = BLOCKS
			.register("support_album_pedestal", SupportAlbumPedestalBlock::new);
	public static final DeferredBlock<ThreeGoddessBlock> THREE_GODDESS = BLOCKS.register("three_goddess",
			ThreeGoddessBlock::new);
	public static final DeferredBlock<StatuesUpperBlock> THREE_GODDESS_UPPER = BLOCKS.register("three_goddess_upper",
			() -> new StatuesUpperBlock(THREE_GODDESS, Shapes.block(), true));
	public static final DeferredBlock<TrainingFacilityBlock> TRAINING_FACILITY = BLOCKS.register("training_facility",
			TrainingFacilityBlock::new);
	public static final DeferredBlock<SkillLearningTableBlock> SKILL_LEARNING_TABLE = BLOCKS
			.register("skill_learning_table", SkillLearningTableBlock::new);
	public static final DeferredBlock<RetireRegisterLecternBlock> REGISTER_LECTERN = BLOCKS.register("register_lectern",
			RetireRegisterLecternBlock::new);

	public static final DeferredBlock<DisassemblyBlock> DISASSEMBLY_BLOCK = BLOCKS.register("disassembly_block",
			DisassemblyBlock::new);

	public static final DeferredBlock<Block> UMA_STATUES = BLOCKS.register("uma_statues", UmaStatueBlock::new);

	public static final DeferredBlock<StatuesUpperBlock> UMA_STATUES_UPPER = BLOCKS.register("uma_statues_upper",
			() -> new StatuesUpperBlock(UMA_STATUES, Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D), true));

	public static final DeferredBlock<UmaSelectBlock> UMA_SELECT_BLOCK = BLOCKS.register("uma_select_block",
			UmaSelectBlock::new);

	public static final DeferredBlock<RaceSelectBlock> RACE_SELECT_BLOCK = BLOCKS.register("race_select_block",
			RaceSelectBlock::new);

	public static final DeferredBlock<FactorDecomposeTable> FACTOR_DECOMPOSE_TABLE = BLOCKS.register("factor_decompose_table",
			FactorDecomposeTable::new);

	public static final DeferredBlock<FactorResearchTableBlock> FACTOR_RESEARCH_TABLE = BLOCKS.register("factor_research_table",
			FactorResearchTableBlock::new);

	public static final DeferredBlock<RaceRegisterBlock> RACE_REGISTER_BLOCK = BLOCKS.register("race_register",
			RaceRegisterBlock::new);

	public static final DeferredBlock<GateDoor> GATE_DOOR = BLOCKS.register("gate_door", GateDoor::new);

	public static final DeferredBlock<Gate> GATE = BLOCKS.register("gate", Gate::new);
}
