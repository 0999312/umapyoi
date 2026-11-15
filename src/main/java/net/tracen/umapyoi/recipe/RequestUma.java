package net.tracen.umapyoi.recipe;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.utils.GachaRanking;
import net.tracen.umapyoi.utils.UmaSoulUtils;

import java.util.List;
import java.util.Optional;

public class RequestUma {
    private final Optional<ResourceLocation> name;
    private final Optional<ResourceLocation> identifier;
    private final List<GachaRanking> ranking;

    public static final Codec<RequestUma> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    ResourceLocation.CODEC.optionalFieldOf("name")
                            .forGetter(RequestUma::getName),
                    ResourceLocation.CODEC.optionalFieldOf("identifier")
                            .forGetter(RequestUma::getIdentifier),
                    GachaRanking.CODEC.listOf().optionalFieldOf("ranking", Lists.newArrayList())
                            .forGetter(RequestUma::getRanking))
            .apply(instance, RequestUma::new));

    public static final StreamCodec<FriendlyByteBuf, RequestUma> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), t -> t.name,
            ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), t -> t.identifier,
            NeoForgeStreamCodecs.enumCodec(GachaRanking.class).apply(ByteBufCodecs.list()), t -> t.ranking,
            RequestUma::new
    );

    public RequestUma() {
        this.name = Optional.empty();
        this.identifier = Optional.empty();
        this.ranking = Lists.newArrayList();
    }

    public RequestUma(Optional<ResourceLocation> name, Optional<ResourceLocation> identifier, List<GachaRanking> ranking) {
        this.name = name;
        this.identifier = identifier;
        this.ranking = ranking;
    }

    public Optional<ResourceLocation> getName() {
        return name;
    }

    public Optional<ResourceLocation> getIdentifier() {
        return identifier;
    }

    public List<GachaRanking> getRanking() {
        return ranking;
    }
    public boolean test(ItemStack soul, HolderGetter<UmaData> getter) {
        var soulname = UmaSoulUtils.getName(soul);
        var name = this.name.map(soulname::equals).orElse(true);
        var id = this.identifier.map(resourceLocation ->
                getter.get(ResourceKey.create(UmaData.REGISTRY_KEY, soulname)).map(Holder.Reference::value).map(UmaData::identifier).map(resourceLocation::equals).orElse(false)
        ).orElse(true);
        var ranking = this.ranking.isEmpty() || this.ranking.contains(GachaRanking.getGachaRanking(soul));
        return name && id && ranking;
    }
}
