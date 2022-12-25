package net.tracen.umapyoi;

import com.mojang.logging.LogUtils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.block.entity.BlockEntityRegistry;
import net.tracen.umapyoi.container.ContainerRegistry;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.network.NetPacketHandler;
import net.tracen.umapyoi.registry.TrainingSupportRegistry;
import net.tracen.umapyoi.registry.UmaDataRegistry;
import net.tracen.umapyoi.registry.UmaFactorRegistry;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import top.theillusivec4.curios.api.SlotTypeMessage;
import org.slf4j.Logger;

@Mod(Umapyoi.MODID)
public class Umapyoi {
    public static final String MODID = "umapyoi";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final CreativeModeTab GROUP = new CreativeModeTab(MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemRegistry.HACHIMI_MID.get());
        }
    };

    public static Item.Properties defaultItemProperties() {
        return new Item.Properties().tab(Umapyoi.GROUP);
    }

    public Umapyoi() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::enqueueIMC);
        TrainingSupportRegistry.SUPPORTS.register(modEventBus);
        UmaSkillRegistry.SKILLS.register(modEventBus);
        UmaDataRegistry.UMA_DATA.register(modEventBus);
        UmaFactorRegistry.FACTORS.register(modEventBus);
        BlockRegistry.BLOCKS.register(modEventBus);
        BlockEntityRegistry.BLOCK_ENTITIES.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
        ContainerRegistry.CONTAINER_TYPES.register(modEventBus);
        
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, UmapyoiConfig.COMMON_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, UmapyoiConfig.CLIENT_CONFIG);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(NetPacketHandler::registerMessage);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("uma_soul")
                .icon(new ResourceLocation("curios:slot/uma_soul_slot")).size(1).build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("uma_suit")
                .icon(new ResourceLocation("curios:slot/uma_suit_slot")).size(0).build());
    }

    public static Logger getLogger() {
        return LOGGER;
    }

}
