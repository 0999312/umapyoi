package net.tracen.umapyoi.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import cn.mcmod_mmf.mmlib.client.RenderUtils;
import cn.mcmod_mmf.mmlib.client.model.SimpleBedrockModel;
import cn.mcmod_mmf.mmlib.utils.ClientUtil;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.container.ThreeGoddessContainer;
import net.tracen.umapyoi.registry.UmaDataRegistry;
import net.tracen.umapyoi.utils.ClientUtils;

public class ThreeGoddessScreen extends AbstractContainerScreen<ThreeGoddessContainer> {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Umapyoi.MODID,
            "textures/gui/three_goddess.png");

    public ThreeGoddessScreen(ThreeGoddessContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 176;
        this.imageHeight = 220;
    }

    @Override
    public void render(PoseStack ms, final int mouseX, final int mouseY, float partialTicks) {
        this.renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
        this.renderModels(ms);
    }

    protected void renderModels(PoseStack ms) {
        ItemStack fatherFactor = this.menu.inventory.getStackInSlot(1);
        ItemStack motherFactor = this.menu.inventory.getStackInSlot(2);
        if (!fatherFactor.isEmpty()) {
            ResourceLocation name = new ResourceLocation(fatherFactor.getTag().getString("name"));
            renderModel(this.leftPos + 33, this.topPos + 55, 25, Quaternion.fromXYZDegrees(new Vector3f(30F, -45f, 0)),
                    name);
        }
        if (!motherFactor.isEmpty()) {
            ResourceLocation name = new ResourceLocation(motherFactor.getTag().getString("name"));
            renderModel(this.leftPos + 142, this.topPos + 55, 25, Quaternion.fromXYZDegrees(new Vector3f(30F, 45f, 0)),
                    name);
        }
    }

    protected void renderModel(int pPosX, int pPosY, int pScale, Quaternion pQuaternion, ResourceLocation name) {
        if (!ClientUtils.getClientUmaDataRegistry().containsKey(name)) {
            name = UmaDataRegistry.COMMON_UMA.getId();
        }
        SimpleBedrockModel model = new SimpleBedrockModel(ClientUtil.getModelPOJO(name));
        ClientUtils.renderModelInInventory(pPosX, pPosY, pScale, pQuaternion, model, name);
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

        // Render progress bar
        int l = this.menu.getProgressionScaled();
        this.blit(ms, this.leftPos + 9, this.topPos + 112, 0, 220, l + 1, 5);

    }

}
