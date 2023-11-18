package net.tracen.umapyoi.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.container.SkillLearningMenu;
import net.tracen.umapyoi.item.SkillBookItem;
import net.tracen.umapyoi.registry.skills.UmaSkill;
import net.tracen.umapyoi.registry.umadata.Growth;
import net.tracen.umapyoi.utils.UmaSoulUtils;
import net.tracen.umapyoi.utils.UmaStatusUtils;

public class SkillLearningScreen extends ItemCombinerScreen<SkillLearningMenu> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Umapyoi.MODID,
            "textures/gui/skill_learning.png");

    public SkillLearningScreen(SkillLearningMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, BACKGROUND_TEXTURE);
    }

    @Override
    protected void renderLabels(PoseStack ms, int mouseX, int mouseY) {
        this.font.draw(ms, this.title,
                (this.imageWidth / 2.0F) - (this.font.width(this.title.getVisualOrderText()) / 2.0F),
                (float) this.titleLabelY - 3, 0xFFFFFF);
        this.font.draw(ms, this.playerInventoryTitle, 8.0f, this.imageHeight - 96 + 2, 4210752);
        UmaSkill skill = this.getBookSkill();
        if (skill != null) {
            this.font.draw(ms, skill.getDescription(), 51, 20, 0x794016);
            ItemStack soul = this.getMenu().getSlot(0).hasItem() ? this.getMenu().getSlot(0).getItem()
                    : ItemStack.EMPTY;
            boolean has_retired = UmaSoulUtils.getGrowth(soul) == Growth.RETIRED;
            boolean has_learned = UmaSoulUtils.getSkills(soul)
                    .contains(StringTag.valueOf(skill.getRegistryName().toString()));
            boolean slot_needed = !soul.isEmpty() && !UmaSoulUtils.hasEmptySkillSlot(soul);
            if (has_learned)
                this.font.draw(ms, new TranslatableComponent("umapyoi.skill.has_learned_skill"), 51, 31, 0x794016);
            else if (has_retired)
                this.font.draw(ms, new TranslatableComponent("umapyoi.skill.has_retired"), 51, 31, 0x794016);
            else if (slot_needed)
                this.font.draw(ms, new TranslatableComponent("umapyoi.skill.slot_needed"), 51, 31, 0x794016);
            else if (skill.getRequiredWisdom() > 0)
                this.font.draw(ms, new TranslatableComponent("umapyoi.skill.require_wisdom",
                        UmaStatusUtils.getStatusLevel(skill.getRequiredWisdom())), 51, 31, 0x794016);
            else
                this.font.draw(ms, new TranslatableComponent("umapyoi.skill.no_require"), 51, 31, 0x794016);
        }

    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pX, int pY) {
        super.renderBg(pPoseStack, pPartialTick, pX, pY);
        UmaSkill skill = this.getBookSkill();
        if (skill != null) {
            int i = (this.width - this.imageWidth) / 2;
            int j = (this.height - this.imageHeight) / 2;
            switch (skill.getType()) {
            case BUFF -> this.blit(pPoseStack, i + 31, j + 21, 176, 21, 16, 16);
            case HINDER -> this.blit(pPoseStack, i + 31, j + 21, 176, 37, 16, 16);
            case HEAL -> this.blit(pPoseStack, i + 31, j + 21, 176, 53, 16, 16);
            case PASSIVE -> throw new UnsupportedOperationException("Unimplemented case: " + skill.getType());
            default -> throw new IllegalArgumentException("Unexpected value: " + skill.getType());
            }
        }
    }

    private UmaSkill getBookSkill() {
        ItemStack book = this.getMenu().getSlot(1).hasItem() ? this.getMenu().getSlot(1).getItem() : ItemStack.EMPTY;
        if (book.getItem()instanceof SkillBookItem skillBook) {
            UmaSkill skill = skillBook.getSkill(book);
            return skill;
        }
        return null;
    }
}
