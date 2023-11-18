package net.tracen.umapyoi.compat.fpm;

import dev.tr7zw.firstperson.FirstPersonModelCore;
import net.tracen.umapyoi.client.model.UmaPlayerModel;

public final class FPMCompat {
    public static void hideHead(UmaPlayerModel<?> model) {
        if(FirstPersonModelCore.isRenderingPlayer) {
            model.head.visible = false;
            if(!model.hat.isEmpty()) model.hat.visible = false;
        }
    }
    
    public static void showHead(UmaPlayerModel<?> model) {
        model.head.visible = true;
        if(!model.hat.isEmpty()) model.hat.visible = true;
        if(FirstPersonModelCore.isRenderingPlayer) FirstPersonModelCore.isRenderingPlayer = false;
    }
}
