package net.tracen.umapyoi.data.loot;

import cn.mcmod_mmf.mmlib.data.loot.modifier.AddLootTableModifier;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class UmapyoiAddLootTableModifier extends AddLootTableModifier {
    private final ResourceKey<LootTable> lootTable;

    protected UmapyoiAddLootTableModifier(LootItemCondition[] conditionsIn, ResourceKey<LootTable> lootTable) {
        super(conditionsIn, lootTable);
        this.lootTable = lootTable;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        LootContext copyContext = new LootContext.Builder(context).withQueriedLootTableId(null).create(Optional.empty());
        return super.doApply(generatedLoot, copyContext);
    }

    public static final MapCodec<UmapyoiAddLootTableModifier> CODEC = RecordCodecBuilder.mapCodec((inst) -> codecStart(inst).and(ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("lootTable").forGetter((m) -> m.lootTable)).apply(inst, UmapyoiAddLootTableModifier::new));
}
