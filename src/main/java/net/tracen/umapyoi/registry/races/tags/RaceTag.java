package net.tracen.umapyoi.registry.races.tags;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.races.Race;
import net.tracen.umapyoi.utils.UmaSoulUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

public record RaceTag(int maximum, ResourceLocation id, boolean isUnique, int[] propertyReward) {
    public static final Codec<RaceTag> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    Codec.INT.fieldOf("max").forGetter(RaceTag::maximum),
                    ResourceLocation.CODEC.fieldOf("id").forGetter(RaceTag::id),
                    Codec.BOOL.fieldOf("is_unique").forGetter(RaceTag::isUnique),
                    Codec.INT_STREAM.xmap(IntStream::toArray, Arrays::stream)
                            .optionalFieldOf("property_reward", new int[5])
                            .forGetter(RaceTag::propertyReward)
            ).apply(instance, RaceTag::new));

    public static final ResourceKey<Registry<RaceTag>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(new ResourceLocation(Umapyoi.MODID, "race_tags"));

    public boolean applyToUmaSoul(ItemStack soul, Race race) {
        boolean isFulfill;
        CompoundTag tagRace = soul.getOrCreateTagElement("attend_race_tag");
        if (!this.isUnique) {
            int current = tagRace.contains(this.id.toString(), CompoundTag.TAG_INT) ? tagRace.getInt(id.toString()) : 0;
            if (current > this.maximum) return false;
            current++;
            tagRace.putInt(this.id.toString(), current);
            isFulfill = current == this.maximum;
        } else {
            ListTag tagList = tagRace.getList(this.id.toString(), Tag.TAG_STRING);
            int current = tagList.size();
            if (current >= this.maximum) return false;
            for (Tag tag: tagList) {
                if (tag.getAsString().equals(race.id.toString())) return false;
            }
            tagList.add(StringTag.valueOf(race.id.toString()));
            tagRace.put(this.id.toString(), tagList);
            isFulfill = tagList.size() == this.maximum;
        }
        if (isFulfill) {
            int[] properties = UmaSoulUtils.getProperty(soul);
            int[] propertiesCeil = UmaSoulUtils.getMaxProperty(soul);
            
            for (int i = 0; i < 5; i++) {
            	propertiesCeil[i] = Math.min(propertiesCeil[i] + propertyReward[i], 39);
            }
            
            for (int i = 0; i < 5; i++) {
                properties[i] = Math.min(properties[i] + propertyReward[i], propertiesCeil[i]);
            }
        }
        soul.getOrCreateTag().put("attend_race_tag", tagRace);
        return true;
    }

    public static Map<ResourceLocation, Integer> queryUmaSoulTags(ItemStack soul) {
        if (!soul.getOrCreateTag().contains("attend_race_tag", CompoundTag.TAG_COMPOUND)) return Map.of();
        HashMap<ResourceLocation, Integer> hmap = new HashMap<>();
        CompoundTag tagRace = soul.getOrCreateTagElement("attend_race_tag");
        tagRace.getAllKeys().stream().map(ResourceLocation::tryParse).filter(Objects::nonNull).forEach(l -> hmap.put(l, queryUmaSoulTagCount(soul, l)));
        return hmap;
    }

    public static int queryUmaSoulTagCount(ItemStack soul, ResourceLocation id) {
        CompoundTag tagRace = soul.getOrCreateTagElement("attend_race_tag");
        if (tagRace.contains(id.toString(), CompoundTag.TAG_INT)) {
            return tagRace.getInt(id.toString());
        } else if (tagRace.contains(id.toString(), CompoundTag.TAG_LIST)) {
            return tagRace.getList(id.toString(), CompoundTag.TAG_STRING).size();
        }
        return 0;
    }

    public int queryUmaSoulTagCount(ItemStack soul) {
        return queryUmaSoulTagCount(soul, this.id);
    }
}
