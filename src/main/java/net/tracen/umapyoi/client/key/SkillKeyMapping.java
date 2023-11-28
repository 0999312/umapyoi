package net.tracen.umapyoi.client.key;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tracen.umapyoi.network.NetPacketHandler;
import net.tracen.umapyoi.network.SelectSkillPacket;
import net.tracen.umapyoi.network.UseSkillPacket;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class SkillKeyMapping {
    public static final KeyMapping KEY_USE_SKILL = new KeyMapping("key.umapyoi.use_skill", KeyConflictContext.IN_GAME,
            KeyModifier.NONE, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_BRACKET, "key.category.umapyoi");
    public static final KeyMapping KEY_FORMER_SKILL = new KeyMapping("key.umapyoi.select_former_skill",
            KeyConflictContext.IN_GAME, KeyModifier.NONE, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UP,
            "key.category.umapyoi");
    public static final KeyMapping KEY_LATTER_SKILL = new KeyMapping("key.umapyoi.select_latter_skill",
            KeyConflictContext.IN_GAME, KeyModifier.NONE, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_DOWN,
            "key.category.umapyoi");

    @SubscribeEvent
    public static void onKeyboardInput(InputEvent.Key event) {
        if (KEY_USE_SKILL.isDown()) {
            NetPacketHandler.INSTANCE.sendToServer(new UseSkillPacket("check check"));
            return;
        }
        if (KEY_FORMER_SKILL.isDown()) {
            NetPacketHandler.INSTANCE.sendToServer(new SelectSkillPacket("former"));
            return;
        }
        if (KEY_LATTER_SKILL.isDown()) {
            NetPacketHandler.INSTANCE.sendToServer(new SelectSkillPacket("latter"));
            return;
        }
    }
}