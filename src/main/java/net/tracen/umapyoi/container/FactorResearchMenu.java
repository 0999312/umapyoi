package net.tracen.umapyoi.container;

import com.google.common.base.Equivalence;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.item.factor.FactorReport;
import net.tracen.umapyoi.registry.factors.FactorType;
import net.tracen.umapyoi.registry.factors.UmaFactorStack;
import net.tracen.umapyoi.utils.UmaFactorUtils;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.tracen.umapyoi.item.ItemRegistry.UMA_FACTOR_ITEM;

public class FactorResearchMenu extends ItemCombinerMenu {
    public FactorResearchMenu(int pContainerId, Inventory pPlayerInventory) {
        this(pContainerId, pPlayerInventory, ContainerLevelAccess.NULL);
    }

    public FactorResearchMenu(int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
        super(ContainerRegistry.FACTOR_RESEARCH_MENU.get(), pContainerId, pPlayerInventory, pAccess);
    }

    @Override
    protected boolean mayPickup(Player pPlayer, boolean pHasStack) {
        return this.hasResult();
    }

    private boolean hasResult() {
        ItemStack left = this.inputSlots.getItem(0);
        ItemStack right = this.inputSlots.getItem(1);
        if (!(isSrcAvailable(left) && isFactorResearch(right))) return false;
        List<UmaFactorStack> rightStack = FactorReport.getFactorStacks(right);
        if (rightStack.isEmpty()) return false;
        if (isFactorItem(left)) return true;
        List<UmaFactorStack> leftStack = FactorReport.getFactorStacks(left);
        if (leftStack.isEmpty()) return false;
        if (leftStack.get(0).getFactor().getFactorType() != rightStack.get(0).getFactor().getFactorType()) return false;
        if (leftStack.get(0).getFactor().getFactorType() == FactorType.OTHER) return true;
        return leftStack.get(0).getFactor() == rightStack.get(0).getFactor();
    }

    private static class eq extends Equivalence<UmaFactorStack> {
        @Override
        protected boolean doEquivalent(UmaFactorStack umaFactorStack, UmaFactorStack t1) {
            return umaFactorStack.getFactor().withStackEquals(umaFactorStack, t1);
        }

        @Override
        protected int doHash(UmaFactorStack umaFactorStack) {
            return umaFactorStack.getFactor().hashCode(umaFactorStack);
            // Note: Any factor implementation, if NBT related, shall ensure their hashCode(UmaFactorStack) function
            // is having a reasonable implementation or a degradation to O(n^2) time complexity in the implementation
            // below is likely to be happened.
        }
    }
    private final eq EQInstance = new eq();

    @Override
    public void createResult() {
        if (!this.hasResult()) {
            this.resultSlots.clearContent();
        } else {
            ItemStack left = this.inputSlots.getItem(0);
            ItemStack right = this.inputSlots.getItem(1);
            List<UmaFactorStack> stackLeft = UmaFactorUtils.deserializeNBT(left.getOrCreateTag());
            List<UmaFactorStack> stackRight = UmaFactorUtils.deserializeNBT(right.getOrCreateTag());
            List<UmaFactorStack> outputs;
            if (isFactorItem(left) || stackLeft.get(0).getFactor().getFactorType() == FactorType.OTHER) {
                outputs = Stream.concat(
                        stackLeft.stream().map(EQInstance::wrap).distinct(),
                        stackRight.stream().map(EQInstance::wrap).distinct()
                ).collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                Function.identity(),
                                Equivalence.Wrapper::get,
                                UmaFactorUtils::merge,
                                LinkedHashMap::new
                        ),
                        m -> new ArrayList<>(m.values())
                ));
            } else {
                UmaFactorStack leftStatusSingle = stackLeft.get(0);
                outputs = List.of(stackRight.stream()
                        .filter(s -> s.getFactor().equals(leftStatusSingle.getFactor()))
                        .findFirst()
                        .map((rightStack) -> rightStack.getLevel() == leftStatusSingle.getLevel() ?
                                UmaFactorUtils.cloneWithLevel(rightStack, rightStack.getLevel() + 1, false) :
                                (rightStack.getLevel() > leftStatusSingle.getLevel() ? rightStack : leftStatusSingle))
                        .orElse(leftStatusSingle));
            }
            ItemStack returnItem = isFactorItem(left) ? left.copyWithCount(1) : new ItemStack(ItemRegistry.FACTOR_SHARD.get(), 1);
            // todo: I am not sure about it, but, it seems that both can be simply copied with left.copyWithCount(1)
            returnItem.getOrCreateTag().put("factors", UmaFactorUtils.serializeNBT(outputs));
            this.resultSlots.setItem(0, returnItem);
        }
    }

    @Override
    protected boolean isValidBlock(BlockState pState) {
        return pState.is(BlockRegistry.FACTOR_RESEARCH_TABLE.get());
    }

    @Override
    public boolean canTakeItemForPickAll(@Nonnull ItemStack pStack, Slot pSlot) {
        return pSlot.container != this.resultSlots && super.canTakeItemForPickAll(pStack, pSlot);
    }

    private static boolean isSrcAvailable(ItemStack stack) {
        return isFactorItem(stack) || isFactorResearch(stack);
    }
    private static boolean isFactorItem(ItemStack stack) {
        return stack.is(UMA_FACTOR_ITEM.get());
    }
    private static boolean isFactorResearch(ItemStack stack) {
        return stack.is(ItemRegistry.FACTOR_SHARD.get());
    }

    @Nonnull
    @Override
    protected ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
        return ItemCombinerMenuSlotDefinition.create()
                .withSlot(0, 27, 47, FactorResearchMenu::isSrcAvailable)
                .withSlot(1, 76, 47, FactorResearchMenu::isFactorResearch)
                .withResultSlot(2, 134, 48)
                .build();
    }

    private List<ItemStack> getRelevantItems() {
        return List.of(this.inputSlots.getItem(0), this.inputSlots.getItem(1));
    }

    private void shrinkStackInSlot(int pIndex) {
        ItemStack itemstack = this.inputSlots.getItem(pIndex);
        itemstack.shrink(1);
        this.inputSlots.setItem(pIndex, itemstack);
    }

    @Override
    protected void onTake(Player player, ItemStack resultStack) {
        resultStack.onCraftedBy(player.level(), player, resultStack.getCount());
        this.resultSlots.awardUsedRecipes(player, this.getRelevantItems());
        this.shrinkStackInSlot(0);
        this.shrinkStackInSlot(1);
        if (player.level().isClientSide())
            player.playSound(SoundEvents.AMETHYST_CLUSTER_BREAK, 1F, 1F);
    }
}
