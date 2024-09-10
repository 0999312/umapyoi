package net.tracen.umapyoi.data;

import cn.mcmod_mmf.mmlib.data.AbstractBlockStateProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.block.BlockRegistry;

public class UmapyoiBlockStateProvider extends AbstractBlockStateProvider {

    public UmapyoiBlockStateProvider(PackOutput gen, ExistingFileHelper exFileHelper) {
        super(gen, Umapyoi.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        horizontalBlock(BlockRegistry.THREE_GODDESS.get(),
                models().getExistingFile(ResourceLocation.tryParse("umapyoi:block/three_goddess")));
        horizontalBlock(BlockRegistry.UMA_STATUES.get(),
                models().getExistingFile(ResourceLocation.tryParse("umapyoi:block/uma_statue")));
        horizontalBlock(BlockRegistry.TRAINING_FACILITY.get(),
                models().getExistingFile(ResourceLocation.tryParse("umapyoi:block/training_facility")));
        horizontalBlock(BlockRegistry.DISASSEMBLY_BLOCK.get(),
                models().getExistingFile(ResourceLocation.tryParse("umapyoi:block/disassembly_block")));
        horizontalBlock(BlockRegistry.REGISTER_LECTERN.get(),
                models().getExistingFile(ResourceLocation.tryParse("umapyoi:block/register_lectern")));
        horizontalBlock(BlockRegistry.UMA_SELECT_BLOCK.get(),
                models().getExistingFile(ResourceLocation.tryParse("umapyoi:block/uma_select_block")));
        simpleBlock(BlockRegistry.SKILL_LEARNING_TABLE.get(),
                models().getExistingFile(ResourceLocation.tryParse("umapyoi:block/skill_learning_table")));

        simpleBlock(BlockRegistry.UMA_PEDESTAL.get(),
                models().getExistingFile(ResourceLocation.tryParse("umapyoi:block/pedestal")));

        simpleBlock(BlockRegistry.SUPPORT_ALBUM_PEDESTAL.get(),
                models().getExistingFile(ResourceLocation.tryParse("umapyoi:block/pedestal")));
        
        simpleBlock(BlockRegistry.SILVER_UMA_PEDESTAL.get(),
                models().getExistingFile(ResourceLocation.tryParse("umapyoi:block/silver_pedestal")));

        simpleBlock(BlockRegistry.SILVER_SUPPORT_ALBUM_PEDESTAL.get(),
                models().getExistingFile(ResourceLocation.tryParse("umapyoi:block/silver_pedestal")));


        simpleBlock(BlockRegistry.THREE_GODDESS_UPPER.get(),
                models().getExistingFile(ResourceLocation.tryParse("umapyoi:block/three_goddess")));
        simpleBlock(BlockRegistry.UMA_STATUES_UPPER.get(),
                models().getExistingFile(ResourceLocation.tryParse("umapyoi:block/uma_statue")));
    }

}
