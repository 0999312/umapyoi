package net.tracen.umapyoi.data.loot;

import cn.mcmod_mmf.mmlib.data.loot.AbstartctBlockLoot;
import net.minecraft.core.HolderLookup;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.utils.ThreeBlockPart;

public class UmapyoiBlockLoot extends AbstartctBlockLoot {

	public UmapyoiBlockLoot(HolderLookup.Provider provider) {
		super(provider);
	}

    @Override
    public void addTables() {
        dropSelf(BlockRegistry.THREE_GODDESS.get());
        dropSelf(BlockRegistry.REGISTER_LECTERN.get());
        dropSelf(BlockRegistry.SKILL_LEARNING_TABLE.get());
        dropSelf(BlockRegistry.TRAINING_FACILITY.get());
        dropSelf(BlockRegistry.UMA_PEDESTAL.get());
        dropSelf(BlockRegistry.SILVER_UMA_PEDESTAL.get());
        dropSelf(BlockRegistry.DISASSEMBLY_BLOCK.get());
        dropSelf(BlockRegistry.UMA_SELECT_BLOCK.get());
        dropSelf(BlockRegistry.UMA_STATUES.get());
        dropOther(BlockRegistry.SUPPORT_ALBUM_PEDESTAL.get(), ItemRegistry.UMA_PEDESTAL.get());
        dropOther(BlockRegistry.SILVER_SUPPORT_ALBUM_PEDESTAL.get(), ItemRegistry.SILVER_UMA_PEDESTAL.get());
        dropSelf(BlockRegistry.FACTOR_DECOMPOSE_TABLE.get());
        dropSelf(BlockRegistry.FACTOR_RESEARCH_TABLE.get());
        dropSelf(BlockRegistry.GATE.get());
        dropSelf(BlockRegistry.GATE_DOOR.get());
        dropSelf(BlockRegistry.RACE_REGISTER_BLOCK.get());
        add(BlockRegistry.GATE.get(), block -> createSinglePropConditionTable(block, ThreeBlockPart.PART, ThreeBlockPart.LOWER));
    }

}
