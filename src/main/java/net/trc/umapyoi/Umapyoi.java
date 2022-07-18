package net.trc.umapyoi;

import com.mojang.logging.LogUtils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.trc.umapyoi.item.ItemRegistry;
import net.trc.umapyoi.registry.UmaDataRegistry;
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
        UmaDataRegistry.UMA_DATA.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);

    }

    private void setup(final FMLCommonSetupEvent event) {

    }
    
    private void enqueueIMC(final InterModEnqueueEvent event)  {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> new SlotTypeMessage.Builder("uma_soul").icon(new ResourceLocation("curios:slot/uma_soul_slot")).size(1).build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> new SlotTypeMessage.Builder("uma_suit").icon(new ResourceLocation("curios:slot/uma_suit_slot")).size(0).build());
    }

    public static Logger getLogger() {
        return LOGGER;
    }


}
