package net.tracen.umapyoi.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import cn.mcmod_mmf.mmlib.client.RenderUtils;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.capability.CapabilityRegistry;
import net.tracen.umapyoi.container.RetireRegisterMenu;
import net.tracen.umapyoi.item.UmaSoulItem;
import net.tracen.umapyoi.registry.umadata.UmaStatus;
import net.tracen.umapyoi.utils.UmaStatusUtils;
import net.tracen.umapyoi.utils.UmaStatusUtils.StatusType;

public class RetireRegisterScreen extends AbstractContainerScreen<RetireRegisterMenu> {
    
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Umapyoi.MODID,
            "textures/gui/retire_gui.png");

    public RetireRegisterScreen(RetireRegisterMenu screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 176;
        this.imageHeight = 176;
    }

    @Override
    public void render(PoseStack ms, final int mouseX, final int mouseY, float partialTicks) {
        this.renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
        
    }

    @Override
    protected void renderLabels(PoseStack ms, int mouseX, int mouseY) {
        this.font.draw(ms, this.title, (float)this.titleLabelX, (float)this.titleLabelY - 3, 0xFFFFFF);
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
        
        ItemStack input = this.getMenu().getSlot(0).getItem();
        if(this.menu.getSlot(0).hasItem() && !this.menu.getSlot(1).hasItem())
            this.blit(ms, this.leftPos + 74, this.topPos + 57, 176, 0, 29, 19);
        
        else if(input.getItem() instanceof UmaSoulItem) {
            input.getCapability(CapabilityRegistry.UMACAP).ifPresent(cap->{
                UmaStatus status = cap.getUmaStatus();                        
                this.font.draw(ms, UmaStatusUtils.getStatusLevel(status.property()[StatusType.SPEED.getId()]), this.leftPos + 21, this.topPos + 31, 0x40C100);
                this.font.draw(ms, UmaStatusUtils.getStatusLevel(status.property()[StatusType.STAMINA.getId()]), this.leftPos + 52, this.topPos + 31, 0x40C100);
                this.font.draw(ms, UmaStatusUtils.getStatusLevel(status.property()[StatusType.STRENGTH.getId()]), this.leftPos + 83, this.topPos + 31, 0x40C100);
                this.font.draw(ms, UmaStatusUtils.getStatusLevel(status.property()[StatusType.GUTS.getId()]), this.leftPos + 114, this.topPos + 31, 0x40C100);
                this.font.draw(ms, UmaStatusUtils.getStatusLevel(status.property()[StatusType.WISDOM.getId()]), this.leftPos + 146, this.topPos + 31, 0x40C100);           
            });
        }
    }
    
}
