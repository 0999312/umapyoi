package net.tracen.umapyoi.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import cn.mcmod_mmf.mmlib.client.RenderUtils;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.container.TrainingFacilityContainer;
import net.tracen.umapyoi.registry.training.SupportContainer;

public class TrainingFacilityScreen extends AbstractContainerScreen<TrainingFacilityContainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Umapyoi.MODID,
            "textures/gui/training_snap.png");

    public TrainingFacilityScreen(TrainingFacilityContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 176;
        this.imageHeight = 202;
    }

    @Override
    public void render(PoseStack ms, final int mouseX, final int mouseY, float partialTicks) {
        this.renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);

    }

    @Override
    protected void renderLabels(PoseStack ms, int mouseX, int mouseY) {
        this.font.draw(ms, this.title,
                (this.imageWidth / 2.0F) - (this.font.width(this.title.getVisualOrderText()) / 2.0F),
                (float) this.titleLabelY - 3, 0xFFFFFF);
        this.font.draw(ms, this.playerInventoryTitle, 8.0f, this.imageHeight - 96 + 2, 4210752);
    }

    @Override
    protected void renderBg(PoseStack ms, float partialTicks, int mouseX, int mouseY) {
        // Render UI background
        if (this.minecraft == null) {
            return;
        }
        RenderUtils.setup(BACKGROUND_TEXTURE);
        this.blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        this.renderSupportBG(ms);
        this.renderTrainingAnim(ms);
        this.renderSupportTypes(ms);
    }

    private void renderSupportTypes(PoseStack ms) {
        int[] types = { 0, 0, 0, 0, 0, 0, 0, 0 };
        for (int i = 1; i < 7; i++) {
            ItemStack stack = this.getMenu().inventory.getStackInSlot(i);
            if (stack.getItem() instanceof SupportContainer support) {
                switch (support.getSupportType(this.minecraft.level, stack)) {
                case SPEED -> {
                    types[0]++;
                    this.blit(ms, this.leftPos + 9 + 0 * 23, this.topPos + 42, 3 + 0 * 23, 219, 12, 12);
                }
                case STAMINA -> {
                    types[1]++;
                    this.blit(ms, this.leftPos + 9 + 1 * 23, this.topPos + 42, 3 + 1 * 23, 219, 12, 12);
                }
                case STRENGTH -> {
                    types[2]++;
                    this.blit(ms, this.leftPos + 9 + 2 * 23, this.topPos + 42, 3 + 2 * 23, 219, 12, 12);
                }
                case GUTS -> {
                    types[3]++;
                    this.blit(ms, this.leftPos + 9 + 3 * 23, this.topPos + 42, 3 + 3 * 23, 219, 12, 12);
                }
                case WISDOM -> {
                    types[4]++;
                    this.blit(ms, this.leftPos + 9 + 4 * 23, this.topPos + 42, 3 + 4 * 23, 219, 12, 12);
                }
                case FRIENDSHIP -> {
                    types[5]++;
                    this.blit(ms, this.leftPos + 9 + 5 * 23, this.topPos + 42, 3 + 5 * 23, 219, 12, 12);
                }
                case GROUP -> {
                    types[6]++;
                    this.blit(ms, this.leftPos + 9 + 6 * 23, this.topPos + 42, 3 + 6 * 23, 219, 12, 12);
                }
                default -> {}
                }
            }
        }
        for (int i = 0; i < 7; i++) {
            if (types[i] > 0) {
                this.font.draw(ms, new TextComponent(String.valueOf(types[i])), this.leftPos + 23.0F + i * 23.0F,
                        this.topPos + 44, 0X86D008);
            }
        }
    }

    private void renderSupportBG(PoseStack ms) {
        for (int i = 1; i < 4; i++) {
            ItemStack stack = this.getMenu().inventory.getStackInSlot(i);
            if (stack.getItem()instanceof SupportContainer support) {
                switch (support.getSupportLevel(this.minecraft.level, stack)) {
                case EASTER_EGG -> this.blit(ms, this.leftPos + 7 + (i - 1) * 27, this.topPos + 14, 171, 205, 26, 26);
                case R -> this.blit(ms, this.leftPos + 7 + (i - 1) * 27, this.topPos + 14, 171, 205, 26, 26);
                case SR -> this.blit(ms, this.leftPos + 7 + (i - 1) * 27, this.topPos + 14, 198, 205, 26, 26);
                case SSR -> this.blit(ms, this.leftPos + 7 + (i - 1) * 27, this.topPos + 14, 225, 205, 26, 26);
                default -> throw new IllegalArgumentException(
                        "Unexpected value: " + support.getSupportLevel(this.minecraft.level, stack));
                }

            }
        }

        for (int i = 4; i < 7; i++) {
            ItemStack stack = this.getMenu().inventory.getStackInSlot(i);
            if (stack.getItem()instanceof SupportContainer support) {
                switch (support.getSupportLevel(this.minecraft.level, stack)) {
                case EASTER_EGG -> this.blit(ms, this.leftPos + 89 + (i - 4) * 27, this.topPos + 14, 171, 205, 26, 26);
                case R -> this.blit(ms, this.leftPos + 89 + (i - 4) * 27, this.topPos + 14, 171, 205, 26, 26);
                case SR -> this.blit(ms, this.leftPos + 89 + (i - 4) * 27, this.topPos + 14, 198, 205, 26, 26);
                case SSR -> this.blit(ms, this.leftPos + 89 + (i - 4) * 27, this.topPos + 14, 225, 205, 26, 26);
                default -> throw new IllegalArgumentException(
                        "Unexpected value: " + support.getSupportLevel(this.minecraft.level, stack));
                }
            }
        }
    }

    private void renderTrainingAnim(PoseStack ms) {
        int l = this.menu.getProgressionScaled();
        int n = this.menu.getAnimation();
        this.blit(ms, this.leftPos + 10 + l, this.topPos + 68, 207, 88 + n * 24, 24, 24);
        PoseStack ms1 = new PoseStack();
        ms1.translate(0, 0, 1);
        this.blit(ms1, this.leftPos + 8, this.topPos + 64, 181, 84, 25, 27);
    }
}
