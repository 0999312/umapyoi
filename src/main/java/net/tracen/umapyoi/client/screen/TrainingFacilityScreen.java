package net.tracen.umapyoi.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.container.TrainingFacilityContainer;
import net.tracen.umapyoi.registry.training.SupportContainer;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TrainingFacilityScreen extends AbstractContainerScreen<TrainingFacilityContainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID,
            "textures/gui/training_snap.png");

    public TrainingFacilityScreen(TrainingFacilityContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 176;
        this.imageHeight = 202;
    }

    @Override
    public void render(GuiGraphics graphic, final int mouseX, final int mouseY, float partialTicks) {
        this.renderBackground(graphic, mouseX, mouseY, partialTicks);
        super.render(graphic, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphic, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics graphic, int mouseX, int mouseY) {
        graphic.drawString(this.font, this.title,
                (this.imageWidth / 2) - (this.font.width(this.title.getVisualOrderText()) / 2),
                this.titleLabelY - 3, 0xFFFFFF);
        graphic.drawString(this.font, this.playerInventoryTitle, 8, this.imageHeight - 96 + 2, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics graphic, float partialTicks, int mouseX, int mouseY) {
        // Render UI background
        if (this.minecraft == null) {
            return;
        }
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        
        graphic.blit(BACKGROUND_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        this.renderSupportBG(graphic);
        this.renderTrainingAnim(graphic);
        this.renderSupportTypes(graphic);
    }

    private void renderSupportTypes(GuiGraphics graphic) {
        int[] types = { 0, 0, 0, 0, 0, 0, 0, 0 };
        for (int i = 1; i < 7; i++) {
            ItemStack stack = this.getMenu().inventory.getStackInSlot(i);
            if (stack.getItem() instanceof SupportContainer support) {
                switch (support.getSupportType(this.minecraft.level, stack)) {
                case SPEED -> {
                    types[0]++;
                    graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 9 + 0 * 23, this.topPos + 42, 3 + 0 * 23, 219, 12, 12);
                }
                case STAMINA -> {
                    types[1]++;
                    graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 9 + 1 * 23, this.topPos + 42, 3 + 1 * 23, 219, 12, 12);
                }
                case STRENGTH -> {
                    types[2]++;
                    graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 9 + 2 * 23, this.topPos + 42, 3 + 2 * 23, 219, 12, 12);
                }
                case GUTS -> {
                    types[3]++;
                    graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 9 + 3 * 23, this.topPos + 42, 3 + 3 * 23, 219, 12, 12);
                }
                case WISDOM -> {
                    types[4]++;
                    graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 9 + 4 * 23, this.topPos + 42, 3 + 4 * 23, 219, 12, 12);
                }
                case FRIENDSHIP -> {
                    types[5]++;
                    graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 9 + 5 * 23, this.topPos + 42, 3 + 5 * 23, 219, 12, 12);
                }
                case GROUP -> {
                    types[6]++;
                    graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 9 + 6 * 23, this.topPos + 42, 3 + 6 * 23, 219, 12, 12);
                }
                default -> {}
                }
            }
        }
        for (int i = 0; i < 7; i++) {
            if (types[i] > 0) {
                graphic.drawString(this.font, Component.literal(String.valueOf(types[i])), this.leftPos + 23 + i * 23,
                        this.topPos + 44, 0X86D008);
            }
        }
    }

    private void renderSupportBG(GuiGraphics graphic) {
        for (int i = 1; i < 4; i++) {
            ItemStack stack = this.getMenu().inventory.getStackInSlot(i);
            if (stack.getItem()instanceof SupportContainer support) {
                switch (support.getSupportLevel(this.minecraft.level, stack)) {
                case EASTER_EGG, R -> graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 7 + (i - 1) * 27, this.topPos + 14, 171, 205, 26, 26);
                    case SR -> graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 7 + (i - 1) * 27, this.topPos + 14, 198, 205, 26, 26);
                case SSR -> graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 7 + (i - 1) * 27, this.topPos + 14, 225, 205, 26, 26);
                default -> throw new IllegalArgumentException(
                        "Unexpected value: " + support.getSupportLevel(this.minecraft.level, stack));
                }

            }
        }

        for (int i = 4; i < 7; i++) {
            ItemStack stack = this.getMenu().inventory.getStackInSlot(i);
            if (stack.getItem()instanceof SupportContainer support) {
                switch (support.getSupportLevel(this.minecraft.level, stack)) {
                case EASTER_EGG, R -> graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 89 + (i - 4) * 27, this.topPos + 14, 171, 205, 26, 26);
                    case SR -> graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 89 + (i - 4) * 27, this.topPos + 14, 198, 205, 26, 26);
                case SSR -> graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 89 + (i - 4) * 27, this.topPos + 14, 225, 205, 26, 26);
                default -> throw new IllegalArgumentException(
                        "Unexpected value: " + support.getSupportLevel(this.minecraft.level, stack));
                }
            }
        }
    }

    private void renderTrainingAnim(GuiGraphics graphic) {
        int l = this.menu.getProgressionScaled();
        int n = this.menu.getAnimation();
        graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 10 + l, this.topPos + 68, 207, 88 + n * 24, 24, 24);

        graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 8, this.topPos + 64, 181, 84, 25, 27);
    }
}
