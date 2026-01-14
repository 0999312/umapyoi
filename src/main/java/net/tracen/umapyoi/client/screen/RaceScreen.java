package net.tracen.umapyoi.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.container.RaceContainer;

import javax.annotation.Nonnull;

public class RaceScreen extends AbstractContainerScreen<RaceContainer> {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Umapyoi.MODID,
            "textures/gui/race_screen.png");

    public RaceScreen(RaceContainer container, Inventory inv, Component title) {
        super(container, inv, title);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 175;
        this.imageHeight = 197;
    }

    @Override
    public void render(@Nonnull GuiGraphics graphic, final int mouseX, final int mouseY, float partialTicks) {
        this.renderBackground(graphic);
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
        if (this.minecraft == null) {
            return;
        }
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        graphic.blit(BACKGROUND_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        this.renderProgress(graphic);
    }

    private void renderProgress(GuiGraphics graphic) {
        if (!(this.menu.inventory.getStackInSlot(0).isEmpty() || this.menu.inventory.getStackInSlot(1).isEmpty())
        && !this.menu.tileEntity.fulfill()) {
            graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 55, this.topPos + 20, 159, 237, 64, 19);
            return;
        }
        float progress = this.menu.getProgression();
        int renderLength = Math.round(progress * 64);
        graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 55, this.topPos + 20, 3, 237, 64, 19);
        graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 55, this.topPos + 20, 80, 237, renderLength, 19);
    }
}
