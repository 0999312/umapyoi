package net.tracen.umapyoi.item.data;

import java.util.List;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.factors.FactorData;
import net.tracen.umapyoi.registry.races.UmaRaceHistory;
import net.tracen.umapyoi.registry.umadata.*;

public class DataComponentsTypeRegistry {
	public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister
			.createDataComponents(Registries.DATA_COMPONENT_TYPE, Umapyoi.MODID);
	
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<DataLocation>> DATA_LOCATION = 
		DATA_COMPONENTS.registerComponentType("data_location", 
				builder -> builder
		        .persistent(DataLocation.CODEC)
		        .networkSynchronized(DataLocation.STREAM)
	);
	
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<GachaRankingData>> GACHA_RANKING = 
		DATA_COMPONENTS.registerComponentType("ranking", 
				builder -> builder
		        .persistent(GachaRankingData.CODEC)
		        .networkSynchronized(GachaRankingData.STREAM)
	);
	
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<UmaDataBasicStatus>> UMADATA_BASIC_STATUS = 
		DATA_COMPONENTS.registerComponentType("umadata_basic_status", 
				builder -> builder
		        .persistent(UmaDataBasicStatus.CODEC)
		        .networkSynchronized(UmaDataBasicStatus.STREAM)
	);
	
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<UmaDataBasicStatus>> UMADATA_MAX_BASIC_STATUS = 
		DATA_COMPONENTS.registerComponentType("umadata_max_basic_status", 
				builder -> builder
		        .persistent(UmaDataBasicStatus.CODEC)
		        .networkSynchronized(UmaDataBasicStatus.STREAM)
	);
	
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<UmaDataBasicStatus>> UMADATA_STATUS_RATE = 
			DATA_COMPONENTS.registerComponentType("umadata_status_rate", 
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
	
	public static final DeferredHolder<DataComponentType<?>, DataComponentType<UmaDataTraining>> UMADATA_TRAINING =
		DATA_COMPONENTS.registerComponentType("umadata_training", 
				builder -> builder
		        .persistent(UmaDataTraining.CODEC)
		        .networkSynchronized(UmaDataTraining.STREAM)
	);

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<UmaDataAptitude>> UMADATA_APTITUDE =
		DATA_COMPONENTS.registerComponentType("umadata_aptitude",
			builder -> builder
				.persistent(UmaDataAptitude.CODEC)
				.networkSynchronized(UmaDataAptitude.STREAM));

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<FactorData>>> FACTOR_DATA = 
		DATA_COMPONENTS.registerComponentType("factor_data", 
				builder -> builder
		        .persistent(FactorData.CODEC.listOf())
		        .networkSynchronized(FactorData.STREAM.apply(ByteBufCodecs.list()))
	);

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<UmaRaceHistory>> UMA_RACE_HISTORY =
		DATA_COMPONENTS.registerComponentType("uma_race_history",
				builder -> builder
				.persistent(UmaRaceHistory.CODEC)
				.networkSynchronized(UmaRaceHistory.STREAM)
		);

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<ResourceLocation>> COSMETIC_DATA =
			DATA_COMPONENTS.registerComponentType("cosmetic",
					builder -> builder
							.persistent(ResourceLocation.CODEC)
							.networkSynchronized(ResourceLocation.STREAM_CODEC)
			);

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<ResourceLocation>> RACE_DATA =
			DATA_COMPONENTS.registerComponentType("race",
					builder -> builder.persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC));
}
