package net.tracen.umapyoi.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.container.FactorDecomposeMenu;

public class FactorDecomposeScreen extends AbstractContainerScreen<FactorDecomposeMenu> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Umapyoi.MODID,
            "textures/gui/factor_decompose.png");

    public FactorDecomposeScreen(FactorDecomposeMenu screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 176;
        this.imageHeight = 216;
    }

    @Override
    public void render(GuiGraphics graphic, final int mouseX, final int mouseY, float partialTicks) {
        this.renderBackground(graphic);
        super.render(graphic, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphic, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics graphic, int mouseX, int mouseY) {
        graphic.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY - 3, 0xFFFFFF);
        graphic.drawString(this.font, this.playerInventoryTitle, 8, this.imageHeight - 96 + 2, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics graphic, float partialTicks, int mouseX, int mouseY) {
        // Render UI background
        if (this.minecraft == null) {
            return;
        }
        graphic.blit(BACKGROUND_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 15, this.topPos + 23, 88, 93, 25, 28, 128, 128);

        if (this.menu.getSlot(0).hasItem() && (!this.menu.getSlot(1).hasItem() || this.menu.isTaking()))
            graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 80, this.topPos + 37, 176, 0, 19, 25);
    }
}
