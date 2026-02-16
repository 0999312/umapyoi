package net.tracen.umapyoi.registry.factors;

import com.mojang.serialization.Codec;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.UmaFactorRegistry;

import java.util.Comparator;

public class UmaFactor{
    public static class UmaFactorComparator implements Comparator<RegistryObject<UmaFactor>> {
        public static UmaFactorComparator INSTANCE = new UmaFactorComparator();
        @Override
        public int compare(RegistryObject<UmaFactor> o1, RegistryObject<UmaFactor> o2) {
            UmaFactor leftFactor = o1.get();
            UmaFactor rightFactor = o2.get();
            if (leftFactor.type != rightFactor.type) return leftFactor.type.compareTo(rightFactor.type);
            return o1.getId() == null || o2.getId() == null ? 0 : o1.getId().compareTo(o2.getId());
        }
    }

    private final FactorType type;
    private String descriptionId;
    private String detailId;

    public static final ResourceKey<Registry<UmaFactor>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(new ResourceLocation(Umapyoi.MODID, "factor"));

    public static final Codec<UmaFactor> CODEC = ResourceLocation.CODEC
            .xmap(loc -> UmaFactorRegistry.REGISTRY.get().getValue(loc), instance -> UmaFactorRegistry.REGISTRY.get().getKey(instance));

    public UmaFactor(FactorType type) {
        this.type = type;
    }

    public void applyFactor(ItemStack soul, UmaFactorStack stack) {

    }

    public FactorType getFactorType() {
        return type;
    }

    public String toString() {
        return UmaFactorRegistry.REGISTRY.get().getKey(this).toString();
    }

    public Component getDescription() {
        return Component.translatable(this.getDescriptionId());
    }

    public Component getDescription(UmaFactorStack stack) {
        return this.getDescription();
    }

    protected String getOrCreateDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("uma_factor", UmaFactorRegistry.REGISTRY.get().getKey(this));
        }
        return this.descriptionId;
    }

    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }

    public Component getFullDescription(int pLevel) {
        MutableComponent mutablecomponent = this.getDescription().copy();
        mutablecomponent.withStyle(ChatFormatting.GRAY);
        mutablecomponent.append(" ").append(Component.translatable("enchantment.level." + pLevel));
        return mutablecomponent;
    }

	public int getMaxLevel() {
		return this.getFactorType().getMaxLevel();
	}
	
	public Component getDescriptionDetail(UmaFactorStack stack) {
        return Component.translatable(this.getDetailDescriptionId());
    }

    protected String getOrCreateDescriptionDetail() {
    	if (this.detailId == null) {
    		this.detailId =  this.getDescriptionId() + ".desc";
    	}
    	return this.detailId;
    }
    
    public String getDetailDescriptionId() {
        return this.getOrCreateDescriptionDetail();
    }

    public boolean withStackEquals(UmaFactorStack left, UmaFactorStack right) {
        return left.getFactor() == right.getFactor();
    }

    public int hashCode(UmaFactorStack stack) {
        return this.hashCode();
    }
}
