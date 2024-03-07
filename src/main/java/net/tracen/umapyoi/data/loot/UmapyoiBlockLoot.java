package net.tracen.umapyoi.data.loot;

import cn.mcmod_mmf.mmlib.data.loot.AbstartctBlockLoot;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.item.ItemRegistry;

public class UmapyoiBlockLoot extends AbstartctBlockLoot {

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
    }

}
