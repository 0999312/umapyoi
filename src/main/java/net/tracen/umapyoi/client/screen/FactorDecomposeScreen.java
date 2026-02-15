package net.tracen.umapyoi.client.screen;

import cn.mcmod_mmf.mmlib.client.model.SimpleBedrockModel;
import cn.mcmod_mmf.mmlib.client.model.bedrock.BedrockPart;
import cn.mcmod_mmf.mmlib.utils.ClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.container.FactorDecomposeMenu;
import net.tracen.umapyoi.data.builtin.UmaDataRegistry;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.utils.ClientUtils;
import org.joml.Quaternionf;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class FactorDecomposeScreen extends AbstractContainerScreen<FactorDecomposeMenu> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Umapyoi.MODID,
            "textures/gui/factor_decompose.png");

    public FactorDecomposeScreen(FactorDecomposeMenu screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 176;
        this.imageHeight = 226;
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
        graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 20, this.topPos + 17, 176, 55, 64, 64);
        this.renderUma(graphic);
        graphic.blit(BACKGROUND_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
//        graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 15, this.topPos + 23, 88, 93, 25, 28, 128, 128);

        if (this.menu.getSlot(0).hasItem() && (!this.menu.getSlot(1).hasItem() || this.menu.isTaking()))
            graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 69, this.topPos + 94, 176, 0, 22, 15);
    }

    protected void renderUma(GuiGraphics graphic) {
        ItemStack rawStack = this.menu.getSlot(0).getItem();
        if (rawStack.isEmpty()) return;
        ResourceLocation name = Optional.ofNullable(rawStack.getTag())
                .map(r -> r.getString("name"))
                .map(ResourceLocation::tryParse)
                .orElse(UmaData.DEFAULT_UMA_ID);
        if (!ClientUtils.getClientUmaDataRegistry().containsKey(name)) name = UmaData.DEFAULT_UMA_ID;
        SimpleBedrockModel model = new SimpleBedrockModel(ClientUtil.getModelPOJO(name)) {
            @Override
            public List<BedrockPart> getShouldRender() {
                return new ArrayList<>(Stream.of("head", "hat").map(this.getModelMap()::get).filter(Objects::nonNull).toList());
            }
        };
        Optional.ofNullable(model.getModelMap().get("long_hair")).ifPresent(i -> i.xRot = (float) (Math.PI / 6f));
        ClientUtils.renderModelInInventory(graphic, this.leftPos + 52, this.topPos + 60, 50, new Quaternionf().rotateXYZ((float) (Math.PI / 6f), (float) (-Math.PI / 4f), 0), model, name);
    }
}
