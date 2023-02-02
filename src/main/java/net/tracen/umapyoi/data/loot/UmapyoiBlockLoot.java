package net.tracen.umapyoi.data.loot;

import cn.mcmod_mmf.mmlib.data.loot.AbstartctBlockLoot;
import net.tracen.umapyoi.block.BlockRegistry;

public class UmapyoiBlockLoot extends AbstartctBlockLoot {

    @Override
    public void addTables() {
        dropSelf(BlockRegistry.THREE_GODDESS.get());
        dropSelf(BlockRegistry.REGISTER_LECTERN.get());
        dropSelf(BlockRegistry.SKILL_LEARNING_TABLE.get());
        dropSelf(BlockRegistry.TRAINING_FACILITY.get());
    }

}
