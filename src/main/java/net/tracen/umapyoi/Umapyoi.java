package net.tracen.umapyoi;

import com.mojang.logging.LogUtils;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.block.entity.BlockEntityRegistry;
import net.tracen.umapyoi.container.ContainerRegistry;
import net.tracen.umapyoi.effect.MobEffectRegistry;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.network.NetPacketHandler;
import net.tracen.umapyoi.registry.TrainingSupportRegistry;
import net.tracen.umapyoi.registry.UmaFactorRegistry;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.villager.VillageRegistry;

import org.slf4j.Logger;

@Mod(Umapyoi.MODID)
public class Umapyoi {
    public static final String MODID = "umapyoi";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static Item.Properties defaultItemProperties() {
        return new Item.Properties();
    }

    public Umapyoi() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        UmapyoiCreativeGroup.CREATIVE_MODE_TABS.register(modEventBus);
        TrainingSupportRegistry.SUPPORTS.register(modEventBus);
        UmaSkillRegistry.SKILLS.register(modEventBus);
        UmaFactorRegistry.FACTORS.register(modEventBus);
        MobEffectRegistry.EFFECTS.register(modEventBus);
        BlockRegistry.BLOCKS.register(modEventBus);
        BlockEntityRegistry.BLOCK_ENTITIES.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
        ContainerRegistry.CONTAINER_TYPES.register(modEventBus);
        VillageRegistry.POI_TYPES.register(modEventBus);
        VillageRegistry.PROFESSIONS.register(modEventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, UmapyoiConfig.COMMON_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, UmapyoiConfig.CLIENT_CONFIG);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(NetPacketHandler::registerMessage);
    }

    public static Logger getLogger() {
        return LOGGER;
    }

}
