package net.tracen.umapyoi.data;

import cn.mcmod_mmf.mmlib.data.AbstractBlockStateProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.block.BlockRegistry;

public class UmapyoiBlockStateProvider extends AbstractBlockStateProvider {

    public UmapyoiBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Umapyoi.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        horizontalBlock(BlockRegistry.THREE_GODDESS.get(),
                models().getExistingFile(new ResourceLocation("umapyoi:block/three_goddess")));
        horizontalBlock(BlockRegistry.TRAINING_FACILITY.get(),
                models().getExistingFile(new ResourceLocation("umapyoi:block/training_facility")));
        horizontalBlock(BlockRegistry.REGISTER_LECTERN.get(),
                models().getExistingFile(new ResourceLocation("umapyoi:block/register_lectern")));
        simpleBlock(BlockRegistry.SKILL_LEARNING_TABLE.get(),
                models().getExistingFile(new ResourceLocation("umapyoi:block/skill_learning_table")));
    }

}
