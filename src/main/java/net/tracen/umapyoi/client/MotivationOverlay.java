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

    public static final ResourceLocation HUD = new ResourceLocation(Umapyoi.MODID, "textures/gui/motivations.png");

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int width, int height) {
        if (!UmapyoiConfig.OVERLAY_SWITCH.get())
            return;
        
        if(minecraft.options.hideGui)
        	return;
        
        int x = width / 2;
        int y = height;

        Player player = this.minecraft.player;
        if (player.isSpectator())
            return;
        
        if (!UmapyoiAPI.getUmaSoul(player).isEmpty()) {
            int xOffset = UmapyoiConfig.TOPLEFT_COORD_MOTIVATION_X.get();
            int yOffset = UmapyoiConfig.TOPLEFT_COORD_MOTIVATION_Y.get();
            switch (UmaSoulUtils.getMotivation(UmapyoiAPI.getUmaSoul(player))) {
            case BAD -> {
                guiGraphics.blit(HUD, x + xOffset, y + yOffset, 0, 60, 64, 14, 64, 96);
                guiGraphics.drawString(gui.getFont(), Component.translatable("umapyoi.motivation.bad"), x + xOffset + 14,
                        y + yOffset + 3, 0XFFFFFF);
            }
            case DOWN -> {
                guiGraphics.blit(HUD, x + xOffset, y + yOffset, 0, 45, 64, 14, 64, 96);
                guiGraphics.drawString(gui.getFont(), Component.translatable("umapyoi.motivation.down"), x + xOffset + 14,
                        y + yOffset + 3, 0XFFFFFF);
            }
            case NORMAL -> {
                guiGraphics.blit(HUD, x + xOffset, y + yOffset, 0, 30, 64, 14, 64, 96);
                guiGraphics.drawString(gui.getFont(), Component.translatable("umapyoi.motivation.normal"),
                        x + xOffset + 14, y + yOffset + 3, 0XFFFFFF);
            }
            case GOOD -> {
                guiGraphics.blit(HUD, x + xOffset, y + yOffset, 0, 15, 64, 14, 64, 96);
                guiGraphics.drawString(gui.getFont(), Component.translatable("umapyoi.motivation.good"), x + xOffset + 14,
                        y + yOffset + 3, 0XFFFFFF);
            }

            case PERFECT -> {
                guiGraphics.blit(HUD, x + xOffset, y + yOffset, 0, 0, 64, 14, 64, 96);
                guiGraphics.drawString(gui.getFont(), Component.translatable("umapyoi.motivation.perfect"),
                        x + xOffset + 14, y + yOffset + 3, 0XFFFFFF);
            }
            default -> throw new IllegalArgumentException(
                    "Unexpected value: " + UmaSoulUtils.getMotivation(UmapyoiAPI.getUmaSoul(player)));
            }

        }
    }

}
