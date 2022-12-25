package net.tracen.umapyoi.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.api.UmaStatusUtils;
import net.tracen.umapyoi.container.SkillLearningMenu;
import net.tracen.umapyoi.item.SkillBookItem;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.skills.UmaSkill;

public class SkillLearningScreen extends ItemCombinerScreen<SkillLearningMenu> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Umapyoi.MODID,
            "textures/gui/skill_learning.png");

    public SkillLearningScreen(SkillLearningMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
       super(pMenu, pPlayerInventory, pTitle, BACKGROUND_TEXTURE);
    }

    @Override
    protected void renderLabels(PoseStack ms, int mouseX, int mouseY) {
        this.font.draw(ms, this.title, (float)this.titleLabelX, (float)this.titleLabelY - 3, 0xFFFFFF);
        this.font.draw(ms, this.playerInventoryTitle, 8.0f, this.imageHeight - 96 + 2, 4210752);
        UmaSkill skill = this.getBookSkill();
        if(skill != null) {
            this.font.draw(ms, skill.getDescription(), 51, 20, 0x794016);
            if(skill.getRequiredWisdom() > 0)
                this.font.draw(ms, new TranslatableComponent("umapyoi.skill.require_wisdom", UmaStatusUtils.getStatusLevel(skill.getRequiredWisdom())), 51, 31, 0x794016);
            else
                this.font.draw(ms, new TranslatableComponent("umapyoi.skill.no_require"), 51, 31, 0x794016);
        }
    }
    
    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pX, int pY) {
        super.renderBg(pPoseStack, pPartialTick, pX, pY);
        UmaSkill skill = this.getBookSkill();
        if(skill != null) {
            int i = (this.width - this.imageWidth) / 2;
            int j = (this.height - this.imageHeight) / 2;
            switch(skill.getType()) {
            case BUFF -> this.blit(pPoseStack, i + 31, j + 21, 176, 21, 16, 16);
            case HINDER -> this.blit(pPoseStack, i + 31, j + 21, 176, 37, 16, 16);
            case HEAL -> this.blit(pPoseStack, i + 31, j + 21, 176, 53, 16, 16);
            }
        }
    }

    private UmaSkill getBookSkill() {
        ItemStack book = this.getMenu().getSlot(1).hasItem() ? this.getMenu().getSlot(1).getItem() : ItemStack.EMPTY;
        if(book.getItem() instanceof SkillBookItem skillBook) {
            ResourceLocation skillLoc = ResourceLocation
                    .tryParse(book.getOrCreateTag().getCompound("support").getCompound("tag").getString("skill"));
            UmaSkill skill = UmaSkillRegistry.REGISTRY.get().containsKey(skillLoc)
                    ? UmaSkillRegistry.REGISTRY.get().getValue(skillLoc)
                    : null;
            return skill;
        }
        return null;
    }
}
