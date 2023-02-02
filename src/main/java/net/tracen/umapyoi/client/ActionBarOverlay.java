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
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.capability.CapabilityRegistry;

public class ActionBarOverlay implements IIngameOverlay{
    public static final ActionBarOverlay INSTANCE = new ActionBarOverlay();
    private final Minecraft minecraft = Minecraft.getInstance();
    private ActionBarOverlay() {
    }
    
    private static final ResourceLocation HUD = new ResourceLocation(Umapyoi.MODID,
            "textures/gui/actionbar.png");
    @Override
    public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height) {
        if(!UmapyoiConfig.OVERLAY_SWITCH.get()) 
            return ;
        int x = width;
        int y = height;
        
        Player player = minecraft.player;
        if(player.isSpectator()) return ;
        
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, HUD);
        if(!UmapyoiAPI.getUmaSoul(player).isEmpty()) {
            renderSkill(UmapyoiAPI.getUmaSoul(player), poseStack, x - 8, y - 64);
        }
    }
    
    private void renderSkill(ItemStack soul, PoseStack poseStack, int x, int y) {
        soul.getCapability(CapabilityRegistry.UMACAP).ifPresent(cap->{
          if(cap.getActionPoint() == cap.getMaxActionPoint()) return;
            
          GuiComponent.blit(poseStack, x, y - 128, 11, 0, 5, 128, 16, 128);
          int ap = cap.getActionPoint();
          int apbar = ap != 0 ? ap * 128 / cap.getMaxActionPoint() : 0;
          GuiComponent.blit(poseStack, x, y - apbar, 6, 128 - apbar, 5, apbar, 16, 128);
          GuiComponent.blit(poseStack, x - 7, y - 3 - apbar, 0, 0, 6, 7, 16, 128);
          String str = String.valueOf(cap.getActionPoint());
          this.minecraft.font.draw(poseStack, str, x - 8 - this.minecraft.font.width(str), y - 3 - apbar, 0xFFFFFF);
        });
    }
    
}
