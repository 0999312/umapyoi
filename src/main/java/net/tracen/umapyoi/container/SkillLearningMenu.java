package net.tracen.umapyoi.container;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.tracen.umapyoi.block.BlockRegistry;
import net.tracen.umapyoi.item.SkillBookItem;
import net.tracen.umapyoi.item.UmaSoulItem;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.skills.UmaSkill;
import net.tracen.umapyoi.registry.umadata.Growth;
import net.tracen.umapyoi.utils.UmaSkillUtils;
import net.tracen.umapyoi.utils.UmaSoulUtils;
import net.tracen.umapyoi.utils.UmaStatusUtils;

public class SkillLearningMenu extends ItemCombinerMenu {

    public SkillLearningMenu(int pContainerId, Inventory pPlayerInventory) {
        this(pContainerId, pPlayerInventory, ContainerLevelAccess.NULL);
    }

    public SkillLearningMenu(int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
        super(ContainerRegistry.SKILL_LEARNING_TABLE.get(), pContainerId, pPlayerInventory, pAccess);
    }

    @Override
    protected boolean mayPickup(Player pPlayer, boolean pHasStack) {
        return this.hasResult();
    }

    private boolean hasResult() {
        ItemStack inputSoul = this.inputSlots.getItem(0);
        ItemStack inputSkill = this.inputSlots.getItem(1);
        if (isUmaSoul(inputSoul) && isSkillBook(inputSkill)) {
            ResourceLocation skillRL = ResourceLocation.tryParse(inputSkill.getOrCreateTag().getString("skill"));
            if (UmaSkillRegistry.REGISTRY.get().containsKey(skillRL)) {
                var upperSkill = UmaSkillRegistry.REGISTRY.get().getValue(skillRL).getUpperSkill();
                if (upperSkill != null && UmaSkillUtils.hasLearnedSkill(inputSoul, upperSkill)) 
                    return false;
                
                if (!UmaSoulUtils.hasEmptySkillSlot(inputSoul) && UmaSkillUtils.getLowerSkillIndex(inputSkill, skillRL) == -1)
                    return false;

                UmaSkill skill = UmaSkillRegistry.REGISTRY.get().getValue(skillRL);
                boolean result = UmaSkillUtils.hasLearnedSkill(inputSoul, skillRL);
                return UmaSoulUtils.getProperty(inputSoul)[UmaStatusUtils.StatusType.WISDOM.getId()] >= skill
                        .getRequiredWisdom() && !result;
            }
        }
        return false;
    }

    private boolean isUmaSoul(ItemStack stack) {
        if (stack.getItem() instanceof UmaSoulItem) {
            return UmaSoulUtils.getGrowth(stack) != Growth.RETIRED;
        }
        return false;
    }

    private boolean isSkillBook(ItemStack stack) {
        if (stack.getItem()instanceof SkillBookItem skillbook) {
            return skillbook.getSkill(stack) != null;
        }
        return false;
    }

    @Override
    protected void onTake(Player player, ItemStack resultStack) {
        resultStack.onCraftedBy(player.level, player, resultStack.getCount());
        this.resultSlots.awardUsedRecipes(player);
        this.shrinkStackInSlot(0);
        this.shrinkStackInSlot(1);
        this.access.execute((level, pos) -> {
            player.playSound(SoundEvents.PLAYER_LEVELUP, 1F, 1F);
        });
    }

    private void shrinkStackInSlot(int pIndex) {
        ItemStack itemstack = this.inputSlots.getItem(pIndex);
        itemstack.shrink(1);
        this.inputSlots.setItem(pIndex, itemstack);
    }

    @Override
    protected boolean isValidBlock(BlockState pState) {
        return pState.is(BlockRegistry.SKILL_LEARNING_TABLE.get());
    }

    @Override
    public void createResult() {
        if (!this.hasResult()) {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
        } else {
            this.resultSlots.setItem(0, this.getResultItem());
        }
    }

    private ItemStack getResultItem() {
        ItemStack result = this.inputSlots.getItem(0).copy();
        ItemStack supportItem = this.inputSlots.getItem(1).copy();
        if (supportItem.getItem()instanceof SkillBookItem skillBook) {
            UmaSkillUtils.learnSkill(result, skillBook.getSkill(supportItem).getRegistryName());
        }
        return result;
    }

    @Override
    protected boolean shouldQuickMoveToAdditionalSlot(ItemStack pStack) {
        return isSkillBook(pStack);
    }

    /**
     * Called to determine if the current slot is valid for the stack merging
     * (double-click) code. The stack passed in is null for the initial slot that
     * was double-clicked.
     */
    @Override
    public boolean canTakeItemForPickAll(ItemStack pStack, Slot pSlot) {
        return pSlot.container != this.resultSlots && super.canTakeItemForPickAll(pStack, pSlot);
    }
}
