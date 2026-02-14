package net.tracen.umapyoi.data.loot;

import cn.mcmod_mmf.mmlib.data.loot.modifier.AddLootTableModifier;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class UmapyoiAddLootTableModifier extends AddLootTableModifier {
    public static final Supplier<Codec<UmapyoiAddLootTableModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst)
                    .and(ResourceLocation.CODEC.fieldOf("lootTable").forGetter((m) -> m.lootTable))
                    .apply(inst, UmapyoiAddLootTableModifier::new)));

    public final ResourceLocation lootTable;

    public UmapyoiAddLootTableModifier(LootItemCondition[] conditionsIn, ResourceLocation lootTable) {
        super(conditionsIn, lootTable);
        this.lootTable = lootTable;
    }

    @Nonnull
    @Override
    public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        LootContext copyContext = new LootContext.Builder(context).withQueriedLootTableId(null).create(null);
        return super.doApply(generatedLoot, copyContext);
    }
}
