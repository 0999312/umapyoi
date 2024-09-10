package net.tracen.umapyoi.item.data;

import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.umadata.UmaDataBasicStatus;
import net.tracen.umapyoi.registry.umadata.UmaDataExtraStatus;
import net.tracen.umapyoi.registry.umadata.UmaDataIdentifier;
import net.tracen.umapyoi.registry.umadata.UmaDataSkills;

public class DataComponentsTypeRegistry {
	public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister
			.createDataComponents(Umapyoi.MODID);
	
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<UmaDataIdentifier>> UMADATA_IDENTIFIER = 
		DATA_COMPONENTS.registerComponentType("umadata_identifier", 
				builder -> builder
		        .persistent(UmaDataIdentifier.CODEC)
		        .networkSynchronized(UmaDataIdentifier.STREAM)
	);
	
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<UmaDataBasicStatus>> UMADATA_BASIC_STATUS = 
		DATA_COMPONENTS.registerComponentType("umadata_basic_status", 
				builder -> builder
		        .persistent(UmaDataBasicStatus.CODEC)
		        .networkSynchronized(UmaDataBasicStatus.STREAM)
	);
	
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<UmaDataExtraStatus>> UMADATA_EXTRA_STATUS = 
		DATA_COMPONENTS.registerComponentType("umadata_extra_status", 
				builder -> builder
		        .persistent(UmaDataExtraStatus.CODEC)
		        .networkSynchronized(UmaDataExtraStatus.STREAM)
	);
	
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<UmaDataSkills>> UMADATA_SKILLS = 
		DATA_COMPONENTS.registerComponentType("umadata_skills", 
				builder -> builder
		        .persistent(UmaDataSkills.CODEC)
		        .networkSynchronized(UmaDataSkills.STREAM)
	);
	
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<GachaRankingData>> GACHA_RANKING = 
			DATA_COMPONENTS.registerComponentType("ranking", 
					builder -> builder
			        .persistent(GachaRankingData.CODEC)
			        .networkSynchronized(GachaRankingData.STREAM)
		);
}
