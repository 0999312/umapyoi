package net.tracen.umapyoi.registry.cosmetics;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.utils.ClientUtils;

public record CosmeticData(ResourceLocation model, Optional<ResourceLocation> flatModel,
		Optional<ResourceLocation> texture, Optional<ResourceLocation> flatTexture) {

	public static final Codec<CosmeticData> CODEC = RecordCodecBuilder.create(instance -> instance
			.group(ResourceLocation.CODEC.fieldOf("model").forGetter(CosmeticData::model),
					ResourceLocation.CODEC.optionalFieldOf("flatModel").forGetter(CosmeticData::flatModel),
					ResourceLocation.CODEC.optionalFieldOf("texture").forGetter(CosmeticData::texture),
					ResourceLocation.CODEC.optionalFieldOf("flatTexture").forGetter(CosmeticData::flatTexture))
			.apply(instance, CosmeticData::new));
	
	public static final ResourceLocation COMMON_COSTUME = new ResourceLocation(Umapyoi.MODID, "common_costume");
	
	public static final CosmeticData DEFAULT_COSTUME = new CosmeticData(COMMON_COSTUME);

	public CosmeticData(ResourceLocation model) {
		this(model, Optional.of(model), Optional.of(model), Optional.of(model));
	}
	
	public CosmeticData(ResourceLocation model, ResourceLocation texture) {
		this(model, Optional.of(model), Optional.of(texture), Optional.of(texture));
	}

	public CosmeticData(ResourceLocation model, ResourceLocation flatModel,
			ResourceLocation texture, ResourceLocation flatTexture) {
		this(model, Optional.of(flatModel), Optional.of(texture), Optional.of(flatTexture));
	}

	public static final ResourceKey<Registry<CosmeticData>> REGISTRY_KEY = ResourceKey
			.createRegistryKey(new ResourceLocation(Umapyoi.MODID, "cosmetic_data"));

	public ResourceLocation getFlatModel() {
		return this.flatModel.orElse(this.model);
	}
	
	public ResourceLocation getTexture(boolean tanned) {
		ResourceLocation result = this.texture.orElse(this.model);
		if(tanned) 
			result = new ResourceLocation(result.getNamespace(), result.getPath()+"_tanned");

		return ClientUtils.getTexture(result);
	}

	public ResourceLocation getFlatTexture(boolean tanned) {
		ResourceLocation result = this.flatTexture.orElse(this.texture.orElse(this.model));
		if(tanned) 
			result = new ResourceLocation(result.getNamespace(), result.getPath()+"_tanned");

		return ClientUtils.getTexture(result);
	}
	
}
