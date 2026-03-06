package net.tracen.umapyoi.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.container.RaceContainer;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.utils.Position;

import javax.annotation.Nonnull;
import java.util.*;

import static net.tracen.umapyoi.item.UmaRaceTicketItem.getRaceNameInRawComponent;
import static net.tracen.umapyoi.item.UmaRaceTicketItem.getRaceNameInStyledComponent;

public class RaceScreen extends AbstractContainerScreen<RaceContainer> {

    private static final ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID,
            "textures/gui/race_screen.png");

    public RaceScreen(RaceContainer container, Inventory inv, Component title) {
        super(container, inv, title);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 175;
        this.imageHeight = 201;
    }

    private static double bezier(double t, double v0, double v1, double v2, double v3) {
        double u = 1 - t;
        return u*u*u*v0 + 3*u*u*t*v1 + 3*u*t*t*v2 + t*t*t*v3;
    }

    public static final int SAMPLES = 10000;
    public static final HashMap<Position, Map.Entry<double[], double[]>> CONST_PROGRESS_MAP = new HashMap<>();

    public static Map.Entry<double[], double[]> calculateForPosition(Position pos) {
        double[] xs = new double[SAMPLES];
        double[] ys = new double[SAMPLES];
        for (int i = 0; i < SAMPLES; i++) {
            double t = (double) i / (SAMPLES - 1);
            xs[i] = bezier(t, 0, pos.x1, pos.x2, 1);
            ys[i] = bezier(t, 0, pos.y1, pos.y2, 1);
        }
        return new AbstractMap.SimpleEntry<>(xs, ys);
    }

    static {
        for (Position position: Position.values()) {
            CONST_PROGRESS_MAP.put(position, calculateForPosition(position));
        }
    }

    public double mapProgress(double progress, Position position) {
        Map.Entry<double[], double[]> tables = CONST_PROGRESS_MAP.computeIfAbsent(position, RaceScreen::calculateForPosition);
        double[] xs = tables.getKey(), ys = tables.getValue();
        int idx = Arrays.binarySearch(xs, progress);
        if (idx >= 0) return ys[idx];
        int insertPoint = -idx - 1;
        if (insertPoint == 0) return ys[0];
        if (insertPoint == xs.length) return ys[xs.length - 1];
        double x0 = xs[insertPoint - 1], x1 = xs[insertPoint];
        double y0 = ys[insertPoint - 1], y1 = ys[insertPoint];
        return y0 + (y1 - y0) * (progress - x0) / (x1 - x0);
    }

    @Override
    public void render(@Nonnull GuiGraphics graphic, final int mouseX, final int mouseY, float partialTicks) {
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
        // Background image (with slot)
        graphic.blit(BACKGROUND_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth,
                this.imageHeight, 420, 256);
        this.renderDetail(graphic);
    }

    private Component raceLabel = null;

    @Override
    protected void renderTooltip(GuiGraphics pGuiGraphics, int pX, int pY) {
        super.renderTooltip(pGuiGraphics, pX, pY);
        if (raceLabel == null) return;
        if (this.isHovering(55, 16, 66, 12, pX, pY)) {
            pGuiGraphics.renderComponentTooltip(this.font, List.of(this.raceLabel), pX, pY);
        }
    }

    private void renderMainUma(GuiGraphics pGuiGraphics, double progress, Position tactic) {
        int variance = this.menu.getAnimationTickMod(4);
        double mapped = mapProgress(progress, tactic);
        int startPixel = (int) Mth.lerp(mapped, 13d, 143d);
        int uOffset = mapped >= 0.75d ? 182 : 208;
        int vOffset = 81 + 24 * variance;
        pGuiGraphics.blit(BACKGROUND_TEXTURE, this.leftPos + startPixel, this.topPos + 35, uOffset, vOffset,
                17, 19, 420, 256);
    }

    private void renderDummyUma(GuiGraphics pGuiGrapahics, double progress, DummyUmaDefinition umaDefinition) {
        int startPixel = (int) Mth.lerp(mapProgress(progress, umaDefinition.tactic), umaDefinition.start, umaDefinition.end);
        pGuiGrapahics.blit(umaDefinition.texture, this.leftPos + startPixel,
                this.topPos + 54 - umaDefinition.height, umaDefinition.uOffset, umaDefinition.vOffset,
                umaDefinition.width, umaDefinition.height, umaDefinition.textureWidth, umaDefinition.textureHeight);
    }

    private void renderLabelImage(GuiGraphics graphic, int backgroundWidth) {
        if ((backgroundWidth & 1) == 1) backgroundWidth += 1;
        int repeatZone = backgroundWidth - 46;
        int startPos = this.leftPos + (this.imageWidth / 2) - (backgroundWidth / 2);
        graphic.blit(BACKGROUND_TEXTURE, startPos, this.topPos + 16, 184, 4, 16, 12, 420, 256);
        startPos += 16;
        while (repeatZone > 0) {
            int renderChunkWidth = Math.min(repeatZone, 20);
            graphic.blit(BACKGROUND_TEXTURE, startPos, this.topPos + 16, 200, 4, renderChunkWidth, 12, 420, 256);
            repeatZone -= renderChunkWidth;
            startPos += renderChunkWidth;
        }
        graphic.blit(BACKGROUND_TEXTURE, startPos, this.topPos + 16, 220, 4, 30, 12, 420, 256);
    }

    private void renderDetail(GuiGraphics graphic) {
        if (this.menu.inventory.getStackInSlot(0).isEmpty() || this.menu.inventory.getStackInSlot(1).isEmpty()) {
            raceLabel = null;
            return;
        };
        if (!this.menu.tileEntity.fulfill()) {
            // Condition doesn't satisfy. However, no place left for error message in GUI.
            raceLabel = null;
            return;
        }
        // Goal (Most underlay)
        graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 143, this.topPos + 31,
                232, 82 + this.menu.getGoalType() * 26, 19, 23,
                420, 256);

        // Render Uma's here (farther from camera, underlay)
        float progress = this.menu.getProgression();
        if (Float.isNaN(progress)) progress = 0;
        RandomSource random = RandomSource.create(this.menu.getWinnerReplaceSeed());
        double[] factors = new double[3];
        int whoShallWin = this.menu.shallSoulWin() ? 0 : random.nextIntBetweenInclusive(1, 2);
        for (int i = 0; i < 3; i++) {
            if (i == whoShallWin) {
                factors[i] = 1d;
            } else {
                if (i == 0) {
                    factors[i] = this.menu.getBaseScaleFactor();
                } else {
                    factors[i] = Mth.lerp(random.nextDouble(), 0.75d, 1d);
                }
            }
        }
        for (int i = 1; i <= 2; i++) {
            renderDummyUma(graphic, progress * factors[i],
                    DummyUmaDefinition.values()[random.nextInt(0, DummyUmaDefinition.values().length)]
            );
        }
        renderMainUma(graphic, progress * factors[0], this.menu.getSoulTactic());
        // Render anything here that is closer to camera (overlay)
        // Starting gates
        graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 13, this.topPos + 31, 346, 225,
                22, 24, 420, 256);

        // Race label
        ItemStack stackOfTicket = this.menu.inventory.getStackInSlot(1);
        if (stackOfTicket.is(ItemRegistry.UMA_RACE_TICKET.get())) {
            MutableComponent component = getRaceNameInRawComponent(stackOfTicket);
            raceLabel = getRaceNameInStyledComponent(stackOfTicket);
            int fullWidth = this.font.width(component.getVisualOrderText());
            if (fullWidth <= 157) {
                if (fullWidth <= 61) {
                    graphic.blit(BACKGROUND_TEXTURE, this.leftPos + 55, this.topPos + 16, 184, 4,
                            66, 12, 420, 256);
                } else {
                    int backgroundWidth = fullWidth + 5;
                    renderLabelImage(graphic, backgroundWidth);
                }
                graphic.drawString(this.font, component,
                        this.leftPos + (this.imageWidth / 2) - fullWidth / 2, this.topPos + 18, 0xFFFFFF);
            } else {
                int ellipseWidth = this.font.width("...");
                int maxWidth = 157 - ellipseWidth;
                List<FormattedCharSequence> split = this.font.split(component, maxWidth);
                FormattedCharSequence combinedSequence = FormattedCharSequence.composite(
                        split.get(0), Component.literal("...").getVisualOrderText()
                );
                int textWidth = this.font.width(combinedSequence);
                renderLabelImage(graphic, textWidth + 5);
                graphic.drawString(this.font, combinedSequence,
                        this.leftPos + (this.imageWidth / 2) - textWidth / 2,
                        this.topPos + 18, 0xffffff);
            }
        } else {
            raceLabel = null;
        }
    }

    public enum DummyUmaDefinition {
        U1(21, 19, 181, 176, 13, 140, Position.PACE_CHASER),
        U2(18, 19, 208, 176, 13, 143, Position.PACE_CHASER),
        U3(18, 19, 183, 198, 13, 143, Position.END_CLOSER),
        U4(18, 19, 208, 198, 13, 143, Position.LATE_SURGER);

        public final int width;
        public final int height;
        public final int uOffset;
        public final int vOffset;
        public final int start;
        public final int end;
        public final Position tactic;
        public final ResourceLocation texture;
        public final int textureHeight;
        public final int textureWidth;

        DummyUmaDefinition(int w, int h, int u, int v, int s, int e, Position tactic, ResourceLocation rl, int textureWidth, int textureHeight) {
            this.width = w;
            this.height = h;
            this.uOffset = u;
            this.vOffset = v;
            this.start = s;
            this.end = e;
            this.tactic = tactic;
            this.textureWidth = textureWidth;
            this.textureHeight = textureHeight;
            this.texture = rl;
        }

        DummyUmaDefinition(int w, int h, int u, int v, int s, int e, Position tactic) {
            this(w, h, u, v, s, e, tactic, BACKGROUND_TEXTURE, 420, 256);
        }
    }
}
