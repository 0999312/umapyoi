package net.tracen.umapyoi.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class MotivationOverlay implements IGuiOverlay {
    public static final MotivationOverlay INSTANCE = new MotivationOverlay();
    private final Minecraft minecraft = Minecraft.getInstance();

    private MotivationOverlay() {
    }

    private static final ResourceLocation HUD = new ResourceLocation(Umapyoi.MODID, "textures/gui/motivations.png");

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int width, int height) {
        if (!UmapyoiConfig.OVERLAY_SWITCH.get())
            return;
        int x = width / 2;
        int y = height;

        Player player = this.minecraft.player;
        if (player.isSpectator())
            return;
        
        if (!UmapyoiAPI.getUmaSoul(player).isEmpty()) {
            switch (UmaSoulUtils.getMotivation(UmapyoiAPI.getUmaSoul(player))) {
            case BAD -> {
                guiGraphics.blit(HUD, x + 118, y - 37, 0, 60, 64, 14, 64, 96);
                guiGraphics.drawString(gui.getFont(), Component.translatable("umapyoi.motivation.bad"), x + 132,
                        y - 34, 0XFFFFFF);
            }
            case DOWN -> {
                guiGraphics.blit(HUD, x + 118, y - 37, 0, 45, 64, 14, 64, 96);
                guiGraphics.drawString(gui.getFont(), Component.translatable("umapyoi.motivation.down"), x + 132,
                        y - 34, 0XFFFFFF);
            }
            case NORMAL -> {
                guiGraphics.blit(HUD, x + 118, y - 37, 0, 30, 64, 14, 64, 96);
                guiGraphics.drawString(gui.getFont(), Component.translatable("umapyoi.motivation.normal"),
                        x + 132, y - 34, 0XFFFFFF);
            }
            case GOOD -> {
                guiGraphics.blit(HUD, x + 118, y - 37, 0, 15, 64, 14, 64, 96);
                guiGraphics.drawString(gui.getFont(), Component.translatable("umapyoi.motivation.good"), x + 132,
                        y - 34, 0XFFFFFF);
            }

            case PERFECT -> {
                guiGraphics.blit(HUD, x + 118, y - 37, 0, 0, 64, 14, 64, 96);
                guiGraphics.drawString(gui.getFont(), Component.translatable("umapyoi.motivation.perfect"),
                        x + 132, y - 34, 0XFFFFFF);
            }
            default -> throw new IllegalArgumentException(
                    "Unexpected value: " + UmaSoulUtils.getMotivation(UmapyoiAPI.getUmaSoul(player)));
            }

        }
    }

}
