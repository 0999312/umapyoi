package net.tracen.umapyoi.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.container.RetireRegisterMenu;
import net.tracen.umapyoi.item.UmaSoulItem;
import net.tracen.umapyoi.registry.umadata.UmaDataBasicStatus;
import net.tracen.umapyoi.utils.UmaSoulUtils;
import net.tracen.umapyoi.utils.UmaStatusUtils;

public class RetireRegisterScreen extends AbstractContainerScreen<RetireRegisterMenu> {

    private static final ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID,
            "textures/gui/retire_gui.png");

    public RetireRegisterScreen(RetireRegisterMenu screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 176;
        this.imageHeight = 176;
    }

    @Override
    public void render(GuiGraphics graphic, final int mouseX, final int mouseY, float partialTicks) {
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

        ItemStack input = this.getMenu().getSlot(0).getItem();
        if (this.menu.getSlot(0).hasItem() && !this.menu.getSlot(1).hasItem())
            graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 74, this.topPos + 57, 176, 0, 29, 19);

        else if (input.getItem() instanceof UmaSoulItem) {
            UmaDataBasicStatus status = UmaSoulUtils.getProperty(input);
            graphic.drawString(this.font, UmaStatusUtils.getStatusLevel(status.speed()), this.leftPos + 21,
                    this.topPos + 31, 0x40C100);
            graphic.drawString(this.font, UmaStatusUtils.getStatusLevel(status.stamina()), this.leftPos + 52,
                    this.topPos + 31, 0x40C100);
            graphic.drawString(this.font, UmaStatusUtils.getStatusLevel(status.strength()), this.leftPos + 83,
                    this.topPos + 31, 0x40C100);
            graphic.drawString(this.font, UmaStatusUtils.getStatusLevel(status.guts()), this.leftPos + 114,
                    this.topPos + 31, 0x40C100);
            graphic.drawString(this.font, UmaStatusUtils.getStatusLevel(status.wisdom()), this.leftPos + 146,
                    this.topPos + 31, 0x40C100);
        }
    }

}
