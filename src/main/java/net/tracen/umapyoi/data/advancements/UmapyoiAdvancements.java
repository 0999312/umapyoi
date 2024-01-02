package net.tracen.umapyoi.data.advancements;

import java.util.function.Consumer;

import cn.mcmod_mmf.mmlib.data.advancement.AbstractAdvancements;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemUsedOnBlockTrigger;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.item.ItemRegistry;

public class UmapyoiAdvancements extends AbstractAdvancements {
    @Override
    public void accept(Consumer<Advancement> comsumer) {
        Advancement root = findItem(getRoot(new ItemStack(ItemRegistry.JEWEL.get()),
                new ResourceLocation("minecraft:textures/block/granite.png"), FrameType.TASK, true, false, false),
                new ItemStack(ItemRegistry.JEWEL.get())).save(comsumer, "umapyoi:root");

        Advancement three_goddesses = findItem(getAdvancement(root, new ItemStack(ItemRegistry.THREE_GODDESS.get()),
                "three_goddesses", FrameType.TASK, true, false, false), new ItemStack(ItemRegistry.THREE_GODDESS.get()))
                        .save(comsumer, "umapyoi:three_goddesses");

        Advancement summon_pedestal = findItem(
                getAdvancement(root, new ItemStack(ItemRegistry.SILVER_UMA_PEDESTAL.get()), "summon_pedestal",
                        FrameType.TASK, true, false, false),
                new ItemStack(ItemRegistry.SILVER_UMA_PEDESTAL.get())).save(comsumer, "umapyoi:summon_pedestal");

        getAdvancement(summon_pedestal, new ItemStack(ItemRegistry.SUPPORT_CARD.get()), "support_pedestal", FrameType.TASK, true,
                false, false)
                        .addCriterion("buildSupport",
                                ItemUsedOnBlockTrigger.TriggerInstance.itemUsedOnBlock(
                                        LocationPredicate.Builder.location()
                                                .setBlock(BlockPredicate.Builder.block().of(BlockRegistry.SILVER_UMA_PEDESTAL.get()).build()),
                                        ItemPredicate.Builder.item().of(Items.BOOK)))
                        .save(comsumer, "umapyoi:support_pedestal");

        findItem(getAdvancement(summon_pedestal, new ItemStack(ItemRegistry.UMA_PEDESTAL.get()), "gold_pedestal", FrameType.TASK,
                true, false, false), new ItemStack(ItemRegistry.UMA_PEDESTAL.get())).save(comsumer,
                        "umapyoi:gold_pedestal");
        
        findItem(getAdvancement(summon_pedestal, new ItemStack(ItemRegistry.BLANK_UMA_SOUL.get()),
                "blank_uma_soul", FrameType.TASK, true, false, false), new ItemStack(ItemRegistry.BLANK_UMA_SOUL.get()))
                        .save(comsumer, "umapyoi:blank_uma_soul");

        Advancement uma_soul = findItem(getAdvancement(three_goddesses, new ItemStack(ItemRegistry.UMA_SOUL.get()),
                "uma_soul", FrameType.TASK, true, false, false), new ItemStack(ItemRegistry.UMA_SOUL.get()))
                        .save(comsumer, "umapyoi:uma_soul");

        Advancement training = findItem(getAdvancement(uma_soul, new ItemStack(ItemRegistry.TRAINING_FACILITY.get()),
                "training", FrameType.TASK, true, false, false), new ItemStack(ItemRegistry.TRAINING_FACILITY.get()))
                        .save(comsumer, "umapyoi:training");

        Advancement register_lectern = findItem(
                getAdvancement(training, new ItemStack(ItemRegistry.REGISTER_LECTERN.get()), "register_lectern",
                        FrameType.TASK, true, false, false),
                new ItemStack(ItemRegistry.REGISTER_LECTERN.get())).save(comsumer, "umapyoi:register_lectern");

        findItem(getAdvancement(register_lectern, new ItemStack(ItemRegistry.UMA_FACTOR_ITEM.get()), "inheritance",
                FrameType.TASK, true, false, false), new ItemStack(ItemRegistry.UMA_FACTOR_ITEM.get())).save(comsumer,
                        "umapyoi:inheritance");

        findItem(getAdvancement(register_lectern, new ItemStack(ItemRegistry.DISASSEMBLY_BLOCK.get()), "transfer",
                FrameType.TASK, true, false, false), new ItemStack(ItemRegistry.DISASSEMBLY_BLOCK.get())).save(comsumer,
                        "umapyoi:transfer");

        Advancement skill_learning_table = findItem(
                getAdvancement(root, new ItemStack(ItemRegistry.SKILL_LEARNING_TABLE.get()), "skill_learning_table",
                        FrameType.TASK, true, false, false),
                new ItemStack(ItemRegistry.SKILL_LEARNING_TABLE.get())).save(comsumer, "umapyoi:skill_learning_table");

        findItem(getAdvancement(skill_learning_table, new ItemStack(ItemRegistry.SKILL_BOOK.get()), "skill_book",
                FrameType.TASK, true, false, false), new ItemStack(ItemRegistry.SKILL_BOOK.get())).save(comsumer,
                        "umapyoi:skill_book");

        findItem(getAdvancement(summon_pedestal, new ItemStack(ItemRegistry.BLANK_TICKET.get()),
                "uma_ticket", FrameType.TASK, true, false, false), new ItemStack(ItemRegistry.BLANK_TICKET.get()))
                        .save(comsumer, "umapyoi:uma_ticket");

    }
}
