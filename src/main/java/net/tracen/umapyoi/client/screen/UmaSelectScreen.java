package net.tracen.umapyoi.client.screen;

import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.container.UmaSelectMenu;
import net.tracen.umapyoi.data.tag.UmapyoiItemTags;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.network.EditSearchPacket;
import net.tracen.umapyoi.network.NetPacketHandler;
import net.tracen.umapyoi.registry.training.card.SupportCard;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.utils.ClientUtils;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class UmaSelectScreen extends AbstractContainerScreen<UmaSelectMenu> implements ContainerListener {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Umapyoi.MODID,
            "textures/gui/select_test.png");

    private EditBox searchBox;

    private static final int SCROLLER_WIDTH = 12;
    private static final int SCROLLER_HEIGHT = 15;
    private static final int RECIPES_COLUMNS = 4;
    private static final int RECIPES_ROWS = 3;
    private static final int RECIPES_IMAGE_SIZE_WIDTH = 16;
    private static final int RECIPES_IMAGE_SIZE_HEIGHT = 18;
    private static final int SCROLLER_FULL_HEIGHT = 54;
    private static final int RECIPES_X = 49;
    private static final int RECIPES_Y = 30;
    private float scrollOffs;
    /** Is {@code true} if the player clicked on the scroll wheel in the GUI. */
    private boolean scrolling;
    /**
     * The index of the first recipe to display. The number of recipes displayed at
     * any time is 12 (4 recipes per row, and 3 rows). If the player scrolled down
     * one row, this value would be 4 (representing the index of the first slot on
     * the second row).
     */
    private int startIndex;
    private boolean displayRecipes;

    public UmaSelectScreen(UmaSelectMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        /** The Y size of the inventory window in pixels. */
        this.imageHeight = 186;
        pMenu.registerUpdateListener(this::containerChanged);

    }

    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        RenderSystem.disableBlend();
        this.renderFg(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    public void containerTick() {
        super.containerTick();
        this.searchBox.tick();
    }

    public void renderFg(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.searchBox.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        this.font.draw(pPoseStack, this.title,
                (this.imageWidth / 2.0F) - (this.font.width(this.title.getVisualOrderText()) / 2.0F),
                (float) this.titleLabelY - 3, 0xFFFFFF);
        this.font.draw(pPoseStack, this.playerInventoryTitle, (float) this.inventoryLabelX,
                (float) this.inventoryLabelY + 20, 4210752);
    }

    protected void subInit() {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.searchBox = new EditBox(this.font, i + 42, j + 17, 90, 12, new TranslatableComponent("container.repair"));
        this.searchBox.setCanLoseFocus(false);
        this.searchBox.setTextColor(-1);
        this.searchBox.setTextColorUneditable(-1);
        this.searchBox.setBordered(false);
        this.searchBox.setMaxLength(50);
        this.searchBox.setResponder(this::onNameChanged);
        this.searchBox.setValue("");
        this.addWidget(this.searchBox);
        this.setInitialFocus(this.searchBox);
        this.searchBox.setEditable(false);
    }

    public void resize(Minecraft pMinecraft, int pWidth, int pHeight) {
        String s = this.searchBox.getValue();
        this.init(pMinecraft, pWidth, pHeight);
        this.searchBox.setValue(s);
    }

    @Override
    protected void init() {
        super.init();
        this.subInit();
        this.menu.addSlotListener(this);
    }

    public void removed() {
        super.removed();
        this.menu.removeSlotListener(this);
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == 256) {
            this.minecraft.player.closeContainer();
        }

        return !this.searchBox.keyPressed(pKeyCode, pScanCode, pModifiers) && !this.searchBox.canConsumeInput()
                ? super.keyPressed(pKeyCode, pScanCode, pModifiers)
                : true;
    }

    private void onNameChanged(String name) {
        boolean hasItems = this.menu.getSlot(0).hasItem() && this.menu.getSlot(1).hasItem();
        if(!hasItems)
            return;
        String s = name;
        if (s.length() <= 50 && !this.menu.getItemName().equalsIgnoreCase(s)) {
            this.menu.setItemName(s);
            NetPacketHandler.INSTANCE.sendToServer(new EditSearchPacket(s));
        }
    }

    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pX, int pY) {
        this.renderBackground(pPoseStack);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(pPoseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
        int k = (int) (41.0F * this.scrollOffs);
        this.blit(pPoseStack, i + 116, j + 31 + k, 176 + (this.isScrollBarActive() ? 0 : SCROLLER_WIDTH), 0,
                SCROLLER_WIDTH, SCROLLER_HEIGHT);
        int l = this.leftPos + RECIPES_X;
        int i1 = this.topPos + RECIPES_Y;
        int j1 = this.startIndex + SCROLLER_WIDTH;
        this.renderButtons(pPoseStack, pX, pY, l, i1, j1);
        this.renderRecipes(l, i1, j1);
    }

    protected void renderTooltip(PoseStack pPoseStack, int pX, int pY) {
        super.renderTooltip(pPoseStack, pX, pY);
        if (this.displayRecipes) {
            int i = this.leftPos + RECIPES_X;
            int j = this.topPos + RECIPES_Y;
            int k = this.startIndex + SCROLLER_WIDTH;
            List<ResourceLocation> list = this.menu.getRecipes();

            for (int l = this.startIndex; l < k && l < this.menu.getNumRecipes(); ++l) {
                int i1 = l - this.startIndex;
                int j1 = i + i1 % RECIPES_COLUMNS * RECIPES_IMAGE_SIZE_WIDTH;
                int k1 = j + i1 / RECIPES_COLUMNS * RECIPES_IMAGE_SIZE_HEIGHT + 2;
                if (pX >= j1 && pX < j1 + RECIPES_IMAGE_SIZE_WIDTH && pY >= k1 && pY < k1 + RECIPES_IMAGE_SIZE_HEIGHT) {
                    this.renderTooltip(pPoseStack, this.getResultItem(list.get(l)), pX, pY);
                }
            }
        }
    }

    private void renderButtons(PoseStack pPoseStack, int pMouseX, int pMouseY, int pX, int pY,
            int pLastVisibleElementIndex) {
        if (this.displayRecipes) {
            for (int i = this.startIndex; i < pLastVisibleElementIndex && i < this.menu.getNumRecipes(); ++i) {
                int j = i - this.startIndex;
                int k = pX + j % RECIPES_COLUMNS * RECIPES_IMAGE_SIZE_WIDTH;
                int l = j / RECIPES_COLUMNS;
                int i1 = pY + l * RECIPES_IMAGE_SIZE_HEIGHT + 2;
                int j1 = 15;
                if (i == this.menu.getSelectedRecipeIndex()) {
                    j1 += RECIPES_IMAGE_SIZE_HEIGHT;
                } else if (pMouseX >= k && pMouseY >= i1 && pMouseX < k + RECIPES_IMAGE_SIZE_WIDTH
                        && pMouseY < i1 + RECIPES_IMAGE_SIZE_HEIGHT) {
                    j1 += 36;
                }

                this.blit(pPoseStack, k, i1 - 1, 176, j1, RECIPES_IMAGE_SIZE_WIDTH, RECIPES_IMAGE_SIZE_HEIGHT);
            }
        }
    }

    private void renderRecipes(int pLeft, int pTop, int pRecipeIndexOffsetMax) {
        if (this.displayRecipes) {
            List<ResourceLocation> list = this.menu.getRecipes();

            for (int i = this.startIndex; i < pRecipeIndexOffsetMax && i < this.menu.getNumRecipes(); ++i) {
                int j = i - this.startIndex;
                int k = pLeft + j % RECIPES_COLUMNS * RECIPES_IMAGE_SIZE_WIDTH;
                int l = j / RECIPES_COLUMNS;
                int i1 = pTop + l * RECIPES_IMAGE_SIZE_HEIGHT + 2;
                this.minecraft.getItemRenderer().renderAndDecorateItem(this.getResultItem(list.get(i)), k, i1);
            }
        }
    }

    private ItemStack getResultItem(ResourceLocation name) {
        if (this.getMenu().getSlot(0).getItem().is(UmapyoiItemTags.CARD_TICKET)) {
            Registry<SupportCard> registry = ClientUtils.getClientSupportCardRegistry();
            ItemStack result = ItemRegistry.SUPPORT_CARD.get().getDefaultInstance();
            result.getOrCreateTag().putString("support_card", name.toString());
            result.getOrCreateTag().putString("ranking", registry.get(name).getGachaRanking().name().toLowerCase());
            result.getOrCreateTag().putInt("maxDamage", registry.get(name).getMaxDamage());
            return result;
        } else {
            Registry<UmaData> registry = ClientUtils.getClientUmaDataRegistry();
            var initUmaSoul = UmaSoulUtils.initUmaSoul(ItemRegistry.UMA_SOUL.get().getDefaultInstance(), name,
                    registry.get(name));
            UmaSoulUtils.setPhysique(initUmaSoul, 5);
            return initUmaSoul;
        }
    }

    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        this.scrolling = false;
        if (this.displayRecipes) {
            int i = this.leftPos + RECIPES_X;
            int j = this.topPos + RECIPES_Y;
            int k = this.startIndex + SCROLLER_WIDTH;

            for (int l = this.startIndex; l < k; ++l) {
                int i1 = l - this.startIndex;
                double d0 = pMouseX - (double) (i + i1 % RECIPES_COLUMNS * RECIPES_IMAGE_SIZE_WIDTH);
                double d1 = pMouseY - (double) (j + i1 / RECIPES_COLUMNS * 18);
                if (d0 >= 0.0D && d1 >= 0.0D && d0 < 16.0D && d1 < 18.0D
                        && this.menu.clickMenuButton(this.minecraft.player, l)) {
                    Minecraft.getInstance().getSoundManager()
                            .play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                    this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, l);
                    return true;
                }
            }

            i = this.leftPos + 119;
            j = this.topPos + 9;
            if (pMouseX >= (double) i && pMouseX < (double) (i + SCROLLER_WIDTH) && pMouseY >= (double) j
                    && pMouseY < (double) (j + SCROLLER_FULL_HEIGHT)) {
                this.scrolling = true;
            }
        }

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (this.scrolling && this.isScrollBarActive()) {
            int i = this.topPos + RECIPES_Y;
            int j = i + SCROLLER_FULL_HEIGHT;
            this.scrollOffs = ((float) pMouseY - (float) i - 7.5F) / ((float) (j - i) - 15.0F);
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
            this.startIndex = (int) ((double) (this.scrollOffs * (float) this.getOffscreenRows()) + 0.5D)
                    * RECIPES_COLUMNS;
            return true;
        } else {
            return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
        }
    }

    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        if (this.isScrollBarActive()) {
            int i = this.getOffscreenRows();
            float f = (float) pDelta / (float) i;
            this.scrollOffs = Mth.clamp(this.scrollOffs - f, 0.0F, 1.0F);
            this.startIndex = (int) ((double) (this.scrollOffs * (float) i) + 0.5D) * RECIPES_COLUMNS;
        }

        return true;
    }

    private boolean isScrollBarActive() {
        return this.displayRecipes && this.menu.getNumRecipes() > 12;
    }

    protected int getOffscreenRows() {
        return (this.menu.getNumRecipes() + RECIPES_COLUMNS - 1) / RECIPES_COLUMNS - RECIPES_ROWS;
    }

    /**
     * Called every time this screen's container is changed (is marked as dirty).
     */
    private void containerChanged() {
        this.displayRecipes = this.menu.hasInputItem();
        if (!this.displayRecipes) {
            this.scrollOffs = 0.0F;
            this.startIndex = 0;
        }

    }

    @Override
    public void slotChanged(AbstractContainerMenu pContainerToSend, int pDataSlotIndex, ItemStack pStack) {
        boolean hasItems = pContainerToSend.getSlot(0).hasItem() && pContainerToSend.getSlot(1).hasItem();
        this.searchBox.setValue(!hasItems ? "" : this.searchBox.getValue());
        this.searchBox.setEditable(hasItems);
        this.setFocused(this.searchBox);
    }

    @Override
    public void dataChanged(AbstractContainerMenu pContainerMenu, int pDataSlotIndex, int pValue) {

    }
}
