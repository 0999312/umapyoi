package net.tracen.umapyoi.client.screen;

import org.joml.Quaternionf;

import com.mojang.blaze3d.systems.RenderSystem;

import cn.mcmod_mmf.mmlib.client.model.SimpleBedrockModel;
import cn.mcmod_mmf.mmlib.utils.ClientUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.container.ThreeGoddessContainer;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.item.data.DataLocation;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.utils.ClientUtils;

public class ThreeGoddessScreen extends AbstractContainerScreen<ThreeGoddessContainer> {

	private static final ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID,
			"textures/gui/three_goddess.png");

	public ThreeGoddessScreen(ThreeGoddessContainer screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);
		this.leftPos = 0;
		this.topPos = 0;
		this.imageWidth = 176;
		this.imageHeight = 220;
	}

	@Override
	public void render(GuiGraphics graphic, final int mouseX, final int mouseY, float partialTicks) {
		super.render(graphic, mouseX, mouseY, partialTicks);
		this.renderTooltip(graphic, mouseX, mouseY);
		this.renderModels(graphic);
	}

	protected void renderModels(GuiGraphics graphic) {
		ItemStack fatherFactor = this.menu.inventory.getStackInSlot(1);
		ItemStack motherFactor = this.menu.inventory.getStackInSlot(2);
		if (!fatherFactor.isEmpty()) {
			ResourceLocation name = fatherFactor
					.getOrDefault(DataComponentsTypeRegistry.DATA_LOCATION, new DataLocation(UmaData.DEFAULT_UMA_ID))
					.name();
			renderModel(graphic, this.leftPos + 33, this.topPos + 55, 25,
					new Quaternionf().rotateXYZ((float) (Math.PI / 6), (float) (-Math.PI / 4F), 0), name);
		}
		if (!motherFactor.isEmpty()) {
			ResourceLocation name = motherFactor
					.getOrDefault(DataComponentsTypeRegistry.DATA_LOCATION, new DataLocation(UmaData.DEFAULT_UMA_ID))
					.name();
			renderModel(graphic, this.leftPos + 142, this.topPos + 55, 25,
					new Quaternionf().rotateXYZ((float) (Math.PI / 6), (float) (Math.PI / 4F), 0), name);
		}
	}

	protected void renderModel(GuiGraphics graphic, int pPosX, int pPosY, int pScale, Quaternionf pQuaternion,
			ResourceLocation name) {
		if (!ClientUtils.getClientUmaDataRegistry().containsKey(name))
			name = UmaData.DEFAULT_UMA_ID;
		SimpleBedrockModel model = new SimpleBedrockModel(ClientUtil.getModelPOJO(name));
		ClientUtils.renderModelInInventory(graphic, pPosX, pPosY, pScale, pQuaternion, model, name);
	}

	@Override
	protected void renderLabels(GuiGraphics graphic, int mouseX, int mouseY) {
		graphic.drawString(this.font, this.title,
				(this.imageWidth / 2) - (this.font.width(this.title.getVisualOrderText()) / 2), this.titleLabelY - 3,
				0xFFFFFF);
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

		// Render progress bar
		int l = this.menu.getProgressionScaled();
		graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 9, this.topPos + 112, 0, 220, l + 1, 5);

	}

}
