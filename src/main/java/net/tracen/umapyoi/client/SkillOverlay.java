package net.tracen.umapyoi.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.skills.UmaSkill;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class SkillOverlay implements IGuiOverlay {
    public static final SkillOverlay INSTANCE = new SkillOverlay();
    private final Minecraft minecraft = Minecraft.getInstance();

    private SkillOverlay() {
    }

    private static final ResourceLocation HUD = new ResourceLocation(Umapyoi.MODID, "textures/gui/skill_hud.png");

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int width, int height) {
        if (!UmapyoiConfig.OVERLAY_SWITCH.get())
            return;
        int x = width / 2;
        int y = height;

        Player player = minecraft.player;
        if (player.isSpectator())
            return;

        if (!UmapyoiAPI.getUmaSoul(player).isEmpty()) {
            guiGraphics.blit(HUD, x + 102, y - 21, 0, 0, 96, 20, 128, 64);
            renderSkill(UmapyoiAPI.getUmaSoul(player), gui, guiGraphics, x + 102, y - 21);
        }
    }

    private void renderSkill(ItemStack soul, ForgeGui gui, GuiGraphics guiGraphics, int x, int y) {
        UmaSkill skill = UmaSkillRegistry.REGISTRY.get().getValue(UmaSoulUtils.getSelectedSkill(soul));
        if (skill != null) {
            switch (skill.getType()) {
            case BUFF -> guiGraphics.blit(HUD, x + 3, y + 2, 0, 48, 16, 16, 128, 64);
            case HINDER -> guiGraphics.blit(HUD, x + 3, y + 2, 16, 48, 16, 16, 128, 64);
            case HEAL -> guiGraphics.blit(HUD, x + 3, y + 2, 32, 48, 16, 16, 128, 64);
            }
            guiGraphics.drawString(gui.getFont(), skill.getDescription(), x + 22, y + 6, 0x794016);
        } else {
            guiGraphics.blit(HUD, x, y, 0, 20, 96, 20, 128, 64);
        }
    }

}
