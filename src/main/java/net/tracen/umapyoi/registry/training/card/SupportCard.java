package net.tracen.umapyoi.registry.training.card;

import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.TrainingSupportRegistry;
import net.tracen.umapyoi.registry.training.SupportStack;
import net.tracen.umapyoi.registry.training.SupportType;
import net.tracen.umapyoi.utils.GachaRanking;

public class SupportCard extends ForgeRegistryEntry<SupportCard> {
    public static final Codec<SupportCard> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            GachaRanking.CODEC.optionalFieldOf("ranking", GachaRanking.EASTER_EGG)
                    .forGetter(SupportCard::getGachaRanking),
            SupportType.CODEC.fieldOf("type").forGetter(SupportCard::getSupportType),
            SupportEntry.CODEC.listOf().fieldOf("supports").forGetter(SupportCard::getSupports), ResourceLocation.CODEC
                    .listOf().optionalFieldOf("supporters", Lists.newArrayList()).forGetter(SupportCard::getSupporters),
            Codec.INT.fieldOf("max_damage").forGetter(SupportCard::getMaxDamage)        
            )
            .apply(instance, SupportCard::new));

    public static final ResourceKey<Registry<SupportCard>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(new ResourceLocation(Umapyoi.MODID, "support_card"));

    private final GachaRanking ranking;
    private final SupportType type;
    private final List<SupportEntry> supports;
    private final List<ResourceLocation> supporters;
    private final int maxDamage;

    private SupportCard(GachaRanking level, SupportType type, List<SupportEntry> supports,
            List<ResourceLocation> supporters, int maxDamage) {
        this.ranking = level;
        this.type = type;
        this.supports = supports;
        this.supporters = supporters;
        this.maxDamage = maxDamage;
    }

    public GachaRanking getGachaRanking() {
        return ranking;
    }

    public List<SupportEntry> getSupports() {
        return supports;
    }
    
    public int getMaxDamage() {
        return maxDamage;
    }

    public List<SupportStack> getSupportStacks() {
        List<SupportStack> result = Lists.newArrayList();
        this.getSupports().forEach(
                sp -> result.add(new SupportStack(TrainingSupportRegistry.REGISTRY.get().getValue(sp.getFactor()),
                        sp.getLevel(), sp.getTag())));
        return result;
    }

    public List<ResourceLocation> getSupporters() {
        return supporters;
    }

    public SupportType getSupportType() {
        return type;
    }

    public static class Builder {
        private GachaRanking level = GachaRanking.R;
        private SupportType type = SupportType.SPEED;
        private List<SupportEntry> supports = Lists.newArrayList();
        private List<ResourceLocation> supporters = Lists.newArrayList();
        private int damage = 3;
        private Builder() {
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder ranking(GachaRanking level) {
            this.level = level;
            return this;
        }
        
        public Builder maxDamage(int damage) {
            this.damage = damage;
            return this;
        }

        public Builder supportType(SupportType type) {
            this.type = type;
            return this;
        }

        public Builder addSupport(SupportEntry support) {
            this.supports.add(support);
            return this;
        }

        public Builder addSupporter(ResourceLocation name) {
            this.supporters.add(name);
            return this;
        }

        public SupportCard build() {
            return new SupportCard(level, type, supports, supporters, damage);
        }
    }
}
