package net.tracen.umapyoi.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.capability.CapabilityRegistry;
import net.tracen.umapyoi.registry.skills.UmaSkill;

public class SkillOverlay implements IIngameOverlay{
    public static final SkillOverlay INSTANCE = new SkillOverlay();
    private final Minecraft minecraft = Minecraft.getInstance();
    private SkillOverlay() {
    }
    
    private static final ResourceLocation HUD = new ResourceLocation(Umapyoi.MODID,
            "textures/gui/skill_hud.png");
    @Override
    public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height) {
        int x = width / 2;
        int y = height;
        
        Player player = minecraft.player;
        
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, HUD);
        if(!UmapyoiAPI.getUmaSoul(player).isEmpty()) {
            GuiComponent.blit(poseStack, x + 102, y - 21, 0, 0, 96, 20, 128, 64);
            renderSkill(UmapyoiAPI.getUmaSoul(player), poseStack, x + 102, y - 21);
        }
    }
    
    private void renderSkill(ItemStack soul, PoseStack poseStack, int x, int y) {
        soul.getCapability(CapabilityRegistry.UMACAP).ifPresent(cap->{
            int skillCD = cap.getCooldown();
            int cdProcess = skillCD != 0 ? skillCD * 96 / cap.getMaxCooldown() : 0;
            GuiComponent.blit(poseStack, x, y, 0, 20, cdProcess + 1, 20, 128, 64);
            UmaSkill skill = cap.getSelectedSkill();
            if(skill != null) {
                switch(skill.getType()) {
                case BUFF -> GuiComponent.blit(poseStack, x + 3, y + 2, 0, 48, 16, 16, 128, 64);
                case HINDER -> GuiComponent.blit(poseStack, x + 3, y + 2, 16, 48, 16, 16, 128, 64);
                case HEAL -> GuiComponent.blit(poseStack, x + 3, y + 2, 32, 48, 16, 16, 128, 64);
                }
                this.minecraft.font.draw(poseStack, skill.getDescription(), x + 22, y + 6, 0x794016);
            }
            
        });
    }
    
}
