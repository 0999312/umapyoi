package net.tracen.umapyoi.data;

import cn.mcmod_mmf.mmlib.data.AbstractBlockStateProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
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
                models().getExistingFile(ResourceLocation.parse("umapyoi:block/three_goddess")));
        horizontalBlock(BlockRegistry.UMA_STATUES.get(),
                models().getExistingFile(ResourceLocation.parse("umapyoi:block/uma_statue")));
        horizontalBlock(BlockRegistry.TRAINING_FACILITY.get(),
                models().getExistingFile(ResourceLocation.parse("umapyoi:block/training_facility")));
        horizontalBlock(BlockRegistry.DISASSEMBLY_BLOCK.get(),
                models().getExistingFile(ResourceLocation.parse("umapyoi:block/disassembly_block")));
        horizontalBlock(BlockRegistry.REGISTER_LECTERN.get(),
                models().getExistingFile(ResourceLocation.parse("umapyoi:block/register_lectern")));
        horizontalBlock(BlockRegistry.UMA_SELECT_BLOCK.get(),
                models().getExistingFile(ResourceLocation.parse("umapyoi:block/uma_select_block")));
        horizontalBlock(BlockRegistry.RACE_REGISTER_BLOCK.get(),
                models().getExistingFile(ResourceLocation.parse("umapyoi:block/race_register")));
        horizontalBlock(BlockRegistry.RACE_SELECT_BLOCK.get(),
                models().getExistingFile(ResourceLocation.parse("umapyoi:block/race_select_block")));

        getVariantBuilder(BlockRegistry.GATE.get()).forAllStates(state ->
                ConfiguredModel.builder()
                        .modelFile(models().getExistingFile(
                                ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "block/gate_" + state.getValue(Gate.PART).name().toLowerCase())
                        ))
                        .rotationY((state.getValue(BlockStateProperties.HORIZONTAL_FACING).get2DDataValue() * 90 + 180) % 360)
                        .build());

        simpleBlock(BlockRegistry.SKILL_LEARNING_TABLE.get(),
                models().getExistingFile(ResourceLocation.parse("umapyoi:block/skill_learning_table")));

        simpleBlock(BlockRegistry.UMA_PEDESTAL.get(),
                models().getExistingFile(ResourceLocation.parse("umapyoi:block/pedestal")));

        simpleBlock(BlockRegistry.SUPPORT_ALBUM_PEDESTAL.get(),
                models().getExistingFile(ResourceLocation.parse("umapyoi:block/pedestal")));
        
        simpleBlock(BlockRegistry.SILVER_UMA_PEDESTAL.get(),
                models().getExistingFile(ResourceLocation.parse("umapyoi:block/silver_pedestal")));

        simpleBlock(BlockRegistry.SILVER_SUPPORT_ALBUM_PEDESTAL.get(),
                models().getExistingFile(ResourceLocation.parse("umapyoi:block/silver_pedestal")));


        simpleBlock(BlockRegistry.THREE_GODDESS_UPPER.get(),
                models().getExistingFile(ResourceLocation.parse("umapyoi:block/three_goddess")));
        simpleBlock(BlockRegistry.UMA_STATUES_UPPER.get(),
                models().getExistingFile(ResourceLocation.parse("umapyoi:block/uma_statue")));
        simpleBlock(BlockRegistry.GATE_DOOR.get(),
                models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "block/gate_door")));
        horizontalBlock(BlockRegistry.FACTOR_RESEARCH_TABLE.get(),
                models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "block/factor_research_table")));
        horizontalBlock(BlockRegistry.FACTOR_DECOMPOSE_TABLE.get(),
                models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "block/factor_decompose_table")));
    }

}
