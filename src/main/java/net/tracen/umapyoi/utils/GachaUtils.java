package net.tracen.umapyoi.utils;

import net.tracen.umapyoi.UmapyoiConfig;

public class GachaUtils {
    public static boolean checkGachaConfig() {
        return (UmapyoiConfig.GACHA_PROBABILITY_R.get() 
                + UmapyoiConfig.GACHA_PROBABILITY_SR.get() 
                + UmapyoiConfig.GACHA_PROBABILITY_SSR.get()) == UmapyoiConfig.GACHA_PROBABILITY_SUM.get();
    }
    
}
