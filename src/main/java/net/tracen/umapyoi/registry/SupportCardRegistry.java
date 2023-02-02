package net.tracen.umapyoi.registry;

import java.util.function.Supplier;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.training.SupportType;
import net.tracen.umapyoi.registry.training.card.SupportCard;
import net.tracen.umapyoi.registry.training.card.SupportEntry;
import net.tracen.umapyoi.utils.UmaSkillUtils;

public class SupportCardRegistry {
    public static final DeferredRegister<SupportCard> SUPPORT_CARD = DeferredRegister.create(SupportCard.REGISTRY_KEY,
            Umapyoi.MODID);
    public static final Supplier<IForgeRegistry<SupportCard>> UMA_DATA_REGISTRY = SUPPORT_CARD.makeRegistry(SupportCard.class,
            () -> new RegistryBuilder<SupportCard>().disableSaving().dataPackRegistry(SupportCard.CODEC, SupportCard.CODEC));

    public static final RegistryObject<SupportCard> TEST_1 = SUPPORT_CARD.register("test_1", 
        SupportCard.Builder.create()
        .level(2)
        .supportType(SupportType.SPEED)
        .addSupport(new SupportEntry(TrainingSupportRegistry.SPEED_SUPPORT.getId(), 2))
        .addSupport(new SupportEntry(TrainingSupportRegistry.STRENGTH_SUPPORT.getId(), 1))
        ::build
    );
    
    public static final RegistryObject<SupportCard> TEST_2 = SUPPORT_CARD.register("test_2", 
        SupportCard.Builder.create()
        .level(2)
        .supportType(SupportType.STAMINA)
        .addSupport(new SupportEntry(TrainingSupportRegistry.STAMINA_SUPPORT.getId(), 2))
        .addSupport(new SupportEntry(TrainingSupportRegistry.GUTS_SUPPORT.getId(), 1))
        ::build
    );
    
    public static final RegistryObject<SupportCard> TEST_3 = SUPPORT_CARD.register("test_3", 
        SupportCard.Builder.create()
        .level(2)
        .supportType(SupportType.STRENGTH)
        .addSupport(new SupportEntry(TrainingSupportRegistry.STAMINA_SUPPORT.getId(), 1))
        .addSupport(new SupportEntry(TrainingSupportRegistry.STRENGTH_SUPPORT.getId(), 2))
        ::build
    );

    public static final RegistryObject<SupportCard> TEST_4 = SUPPORT_CARD.register("test_4", 
        SupportCard.Builder.create()
        .level(2)
        .supportType(SupportType.GUTS)
        .addSupport(new SupportEntry(TrainingSupportRegistry.SPEED_SUPPORT.getId(), 1))
        .addSupport(new SupportEntry(TrainingSupportRegistry.STRENGTH_SUPPORT.getId(), 1))
        .addSupport(new SupportEntry(TrainingSupportRegistry.GUTS_SUPPORT.getId(), 2))
        ::build
    );
        
    public static final RegistryObject<SupportCard> TEST_5 = SUPPORT_CARD.register("test_5", 
        SupportCard.Builder.create()
        .level(2)
        .supportType(SupportType.WISDOM)
        .addSupport(new SupportEntry(TrainingSupportRegistry.WISDOM_SUPPORT.getId(), 2))
        .addSupport(UmaSkillUtils.getSkillSupportEnrty(UmaSkillRegistry.LAST_LEG.getId()))
        ::build
    );
    
    public static final RegistryObject<SupportCard> TEST_SUPPOERS = SUPPORT_CARD.register("test_supports", 
        SupportCard.Builder.create()
        .supportType(SupportType.GROUP)
        .level(3)
        .addSupport(new SupportEntry(TrainingSupportRegistry.SPEED_SUPPORT.getId(), 3))
        .addSupport(new SupportEntry(TrainingSupportRegistry.STAMINA_SUPPORT.getId(), 3))
        .addSupport(new SupportEntry(TrainingSupportRegistry.STRENGTH_SUPPORT.getId(), 3))
        .addSupport(new SupportEntry(TrainingSupportRegistry.GUTS_SUPPORT.getId(), 3))
        .addSupport(new SupportEntry(TrainingSupportRegistry.WISDOM_SUPPORT.getId(), 3))
        .addSupporter(UmaDataRegistry.AGNUS_TACHYON.getId())
        .addSupporter(UmaDataRegistry.MANHATTAN_CAFE.getId())
        .addSupporter(UmaDataRegistry.RICE_SHOWER.getId())
        .addSupporter(UmaDataRegistry.HARU_URARA.getId())
        .addSupporter(UmaDataRegistry.OGURI_CAP.getId())
        .addSupporter(UmaDataRegistry.OGURI_CAP_XMAS.getId())
        ::build
    );
}
