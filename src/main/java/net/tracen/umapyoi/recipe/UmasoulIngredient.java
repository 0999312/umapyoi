package net.tracen.umapyoi.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;
import net.tracen.umapyoi.item.FadedUmaSoulItem;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.item.UmaSoulItem;
import net.tracen.umapyoi.registry.umadata.UmaData;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UmasoulIngredient implements ICustomIngredient {
    private final Set<Item> items;
    private final Set<ItemStack> dummyStack;
    private final RequestUma request;
    private final HolderGetter<UmaData> getter;

    public UmasoulIngredient(Set<Item> items, RequestUma request, HolderGetter<UmaData> getter) {
        if (items.isEmpty()) {
            throw new IllegalArgumentException("Cannot create a UmasoulIngredient with no items");
        }
        this.items = Collections.unmodifiableSet(items);
        this.request = request;
        this.getter = getter;
        dummyStack = items.stream().map(ItemStack::new).collect(Collectors.toSet());
    }

    @Override
    public boolean test(ItemStack input) {
        if (input == null)
            return false;
        return items.contains(input.getItem()) && this.request.test(input, getter);
    }

    @Override
    public Stream<ItemStack> getItems() {
        HolderLookup<UmaData> lookup = null;
        if (getter instanceof HolderLookup<UmaData> l) {
            lookup = l;
        } else if (FMLEnvironment.dist == Dist.CLIENT) {
            Level level = Minecraft.getInstance().level;
            if (level != null) {
                var opt = level.registryAccess().lookup(UmaData.REGISTRY_KEY);
                if (opt.isPresent()) {
                    lookup = opt.get();
                }
            }
        }
        if (lookup == null) return dummyStack.stream();
        final var lookupf = lookup;
        return items.stream().flatMap(i -> UmaSoulItem.sortedUmaDataList(lookupf).map(p -> FadedUmaSoulItem.genUmaSoul(p.key().location(), p.value())).filter(this::test));
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public IngredientType<?> getType() {
        return IngredientRegistry.UMASOUL_INGREDIENT.get();
    }

    public static final MapCodec<UmasoulIngredient> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            BuiltInRegistries.ITEM.byNameCodec().validate(item -> item == Items.AIR ? DataResult.error(() -> "Item must not be minecraft:air") : DataResult.success(item)).optionalFieldOf("item").forGetter(f -> f.items.size() == 1 ? Optional.of(f.items.iterator().next()) : Optional.empty()),
            Codec.list(BuiltInRegistries.ITEM.byNameCodec()).validate(item -> item == Items.AIR ? DataResult.error(() -> "Item must not be minecraft:air") : DataResult.success(item)).optionalFieldOf("items").forGetter(f -> f.items.size() == 1 ? Optional.empty() : Optional.of(new ArrayList<>(f.items))),
            RequestUma.CODEC.fieldOf("request").forGetter(f -> f.request),
            RegistryOps.retrieveGetter(UmaData.REGISTRY_KEY)
    ).apply(inst, (item, items, request, getter) -> {
        Set<Item> finalItems;
        if (item.isPresent()) {
            finalItems = Set.of(item.get());
        } else if (items.isPresent()) {
            finalItems = new HashSet<>(items.get());
        } else {
            finalItems = Set.of(ItemRegistry.BLANK_UMA_SOUL.get());
        }
        return new UmasoulIngredient(finalItems, request, getter);
    }));

    public static final StreamCodec<RegistryFriendlyByteBuf, UmasoulIngredient> STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs.list()), t -> t.items.stream().map(BuiltInRegistries.ITEM::getKey).toList(),
                    RequestUma.STREAM_CODEC, t -> t.request,
                    StreamCodec.of(
                            (buf, lookup) -> {},
                            buf -> buf.registryAccess().lookupOrThrow(UmaData.REGISTRY_KEY)
                    ), t -> null,
                    (items, request, lookup) ->
                            new UmasoulIngredient(
                                    items.stream().map(BuiltInRegistries.ITEM::get).collect(Collectors.toSet()),
                                    request, lookup
                            )
            );
}
