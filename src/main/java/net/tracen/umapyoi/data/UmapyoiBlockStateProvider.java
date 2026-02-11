package net.tracen.umapyoi.data;

import cn.mcmod_mmf.mmlib.data.AbstractBlockStateProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.block.Gate;

public class UmapyoiBlockStateProvider extends AbstractBlockStateProvider {

    public UmapyoiBlockStateProvider(PackOutput gen, ExistingFileHelper exFileHelper) {
        super(gen, Umapyoi.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        horizontalBlock(BlockRegistry.THREE_GODDESS.get(),
                models().getExistingFile(new ResourceLocation("umapyoi:block/three_goddess")));
        horizontalBlock(BlockRegistry.UMA_STATUES.get(),
                models().getExistingFile(new ResourceLocation("umapyoi:block/uma_statue")));
        horizontalBlock(BlockRegistry.TRAINING_FACILITY.get(),
                models().getExistingFile(new ResourceLocation("umapyoi:block/training_facility")));
        horizontalBlock(BlockRegistry.DISASSEMBLY_BLOCK.get(),
                models().getExistingFile(new ResourceLocation("umapyoi:block/disassembly_block")));
        horizontalBlock(BlockRegistry.REGISTER_LECTERN.get(),
                models().getExistingFile(new ResourceLocation("umapyoi:block/register_lectern")));
        horizontalBlock(BlockRegistry.UMA_SELECT_BLOCK.get(),
                models().getExistingFile(new ResourceLocation("umapyoi:block/uma_select_block")));
        /* horizontalBlock(BlockRegistry.RACE_REGISTER_BLOCK.get(),
                models().getExistingFile(new ResourceLocation("umapyoi:block/race_register"))); */
        getVariantBuilder(BlockRegistry.RACE_REGISTER_BLOCK.get()).forAllStates(state ->
            ConfiguredModel.builder()
                    .modelFile(models().getExistingFile(new ResourceLocation(Umapyoi.MODID, "block/race_register")))
                    .rotationY((state.getValue(BlockStateProperties.HORIZONTAL_FACING).get2DDataValue() * 90) % 360)
                    .build());

        getVariantBuilder(BlockRegistry.GATE.get()).forAllStates(state ->
                ConfiguredModel.builder()
                        .modelFile(models().getExistingFile(
                                new ResourceLocation(Umapyoi.MODID, "block/gate_" + state.getValue(Gate.PART).name().toLowerCase())
                        ))
                        .rotationY((state.getValue(BlockStateProperties.HORIZONTAL_FACING).get2DDataValue() * 90 + 180) % 360)
                        .build());

        simpleBlock(BlockRegistry.SKILL_LEARNING_TABLE.get(),
                models().getExistingFile(new ResourceLocation("umapyoi:block/skill_learning_table")));

        simpleBlock(BlockRegistry.UMA_PEDESTAL.get(),
                models().getExistingFile(new ResourceLocation("umapyoi:block/pedestal")));

        simpleBlock(BlockRegistry.SUPPORT_ALBUM_PEDESTAL.get(),
                models().getExistingFile(new ResourceLocation("umapyoi:block/pedestal")));
        
        simpleBlock(BlockRegistry.SILVER_UMA_PEDESTAL.get(),
                models().getExistingFile(new ResourceLocation("umapyoi:block/silver_pedestal")));

        simpleBlock(BlockRegistry.SILVER_SUPPORT_ALBUM_PEDESTAL.get(),
                models().getExistingFile(new ResourceLocation("umapyoi:block/silver_pedestal")));


        simpleBlock(BlockRegistry.THREE_GODDESS_UPPER.get(),
                models().getExistingFile(new ResourceLocation("umapyoi:block/three_goddess")));
        simpleBlock(BlockRegistry.UMA_STATUES_UPPER.get(),
                models().getExistingFile(new ResourceLocation("umapyoi:block/uma_statue")));
        simpleBlock(BlockRegistry.GATE_DOOR.get(),
                models().getExistingFile(new ResourceLocation(Umapyoi.MODID, "block/gate_door")));
    }

}
