package net.tracen.umapyoi.compat.fpm;

import dev.tr7zw.firstperson.api.FirstPersonAPI;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tracen.umapyoi.client.model.UmaPlayerModel;

@OnlyIn(Dist.CLIENT)
public final class FPMCompat {
    public static void hideHead(UmaPlayerModel<?> model) {
        if(FirstPersonAPI.isRenderingPlayer()) {
            model.head.visible = false;
            if(!model.hat.isEmpty()) model.hat.visible = false;
        }
    }
    
    public static void showHead(UmaPlayerModel<?> model) {
        model.head.visible = true;
        if(!model.hat.isEmpty()) model.hat.visible = true;
    }
}
