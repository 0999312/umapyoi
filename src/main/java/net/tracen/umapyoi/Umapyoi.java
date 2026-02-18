package net.tracen.umapyoi;

import com.alrex.parcool.ParCool;
import com.mojang.logging.LogUtils;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.tracen.umapyoi.advancements.trigger.TriggerRegistry;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.block.entity.BlockEntityRegistry;
import net.tracen.umapyoi.command.CommandRegistry;
import net.tracen.umapyoi.compat.sbw.SBWCompat;
import net.tracen.umapyoi.container.ContainerRegistry;
import net.tracen.umapyoi.effect.MobEffectRegistry;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.data.loot.LootFunctionRegistry;
import net.tracen.umapyoi.network.NetPacketHandler;
import net.tracen.umapyoi.recipe.RecipeSerializerRegistry;
import net.tracen.umapyoi.registry.*;
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
        UmapyoiAttributesRegistry.ATTRIBUTES.register(modEventBus);
        MobEffectRegistry.EFFECTS.register(modEventBus);
        BlockRegistry.BLOCKS.register(modEventBus);
        BlockEntityRegistry.BLOCK_ENTITIES.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
        ContainerRegistry.CONTAINER_TYPES.register(modEventBus);
        VillageRegistry.POI_TYPES.register(modEventBus);
        VillageRegistry.PROFESSIONS.register(modEventBus);
        RecipeSerializerRegistry.RECIPE_SERIALIZER.register(modEventBus);
        CommandRegistry.ARGUMENT_TYPES.register(modEventBus);
        SoundRegistry.SOUNDS.register(modEventBus);
        LootFunctionRegistry.LOOT_FUNCTIONS.register(modEventBus);
        modEventBus.addListener(this::onEntityAttributeModification);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, UmapyoiConfig.COMMON_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, UmapyoiConfig.CLIENT_CONFIG);
        
        if(ModList.get().isLoaded("superbwarfare")) {
        	MinecraftForge.EVENT_BUS.register(SBWCompat.class);
        }
        
        if(ModList.get().isLoaded("parcool")) {
        	MinecraftForge.EVENT_BUS.register(ParCool.class);
        }
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(NetPacketHandler::registerMessage);
        TriggerRegistry.registerAll(event);
        VillageRegistry.registerHeroOfTheVillage(event);
    }
    
    private void onEntityAttributeModification(final EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, UmapyoiAttributesRegistry.SPRINT_SPEED.get());
    }

    public static Logger getLogger() {
        return LOGGER;
    }

}
