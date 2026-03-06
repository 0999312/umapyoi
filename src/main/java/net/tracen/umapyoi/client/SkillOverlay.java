package net.tracen.umapyoi.client;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.skills.UmaSkill;
import net.tracen.umapyoi.utils.UmaSoulUtils;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SkillOverlay implements LayeredDraw.Layer {
    public static final SkillOverlay INSTANCE = new SkillOverlay();
    private final Minecraft minecraft = Minecraft.getInstance();

    private SkillOverlay() {
    }

    public static final ResourceLocation HUD = ResourceLocation.fromNamespaceAndPath(Umapyoi.MODID, "textures/gui/skill_hud.png");

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if (!UmapyoiConfig.OVERLAY_SWITCH.get())
            return;
        int x = guiGraphics.guiWidth() / 2;
        int y = guiGraphics.guiHeight();

        Player player = minecraft.player;
        if (player.isSpectator())
            return;

        if (!UmapyoiAPI.getUmaSoul(player).isEmpty()) {
            int xOffset = UmapyoiConfig.TOPLEFT_COORD_SKILL_X.get();
            int yOffset = UmapyoiConfig.TOPLEFT_COORD_SKILL_Y.get();
            guiGraphics.blit(HUD, x + xOffset, y + yOffset, 0, 0, 96, 20, 128, 64);
            renderSkill(UmapyoiAPI.getUmaSoul(player), guiGraphics, x + xOffset, y + yOffset);
        }
    }

    private void renderSkill(ItemStack soul, GuiGraphics guiGraphics, int x, int y) {
        UmaSkill skill = UmaSkillRegistry.REGISTRY.get(UmaSoulUtils.getSelectedSkill(soul));
        renderSkill(skill, minecraft.font, guiGraphics, x, y);
    }

    public static void renderSkill(UmaSkill skill, Font font, GuiGraphics guiGraphics, int x, int y) {
        if (skill != null) {
            switch (skill.getType()) {
                case BUFF -> guiGraphics.blit(HUD, x + 3, y + 2, 0, 48, 16, 16, 128, 64);
                case HINDER -> guiGraphics.blit(HUD, x + 3, y + 2, 16, 48, 16, 16, 128, 64);
                case HEAL -> guiGraphics.blit(HUD, x + 3, y + 2, 32, 48, 16, 16, 128, 64);
                case PASSIVE -> guiGraphics.blit(HUD, x + 3, y + 2, 48, 48, 16, 16, 128, 64);
            }
            guiGraphics.drawString(font, skill.getDescription(), x + 22, y + 6, 0x794016);
        } else {
            guiGraphics.blit(HUD, x, y, 0, 20, 96, 20, 128, 64);
        }
    }

}
