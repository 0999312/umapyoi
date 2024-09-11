package net.tracen.umapyoi;

import net.neoforged.neoforge.common.ModConfigSpec;

public class UmapyoiConfig {
    
    public static final int DEFAULT_GACHA_PROBABILITY_SUM = 100;
    public static final int DEFAULT_GACHA_PROBABILITY_R = 70;
    public static final int DEFAULT_GACHA_PROBABILITY_SR = 20;
    public static final int DEFAULT_GACHA_PROBABILITY_SSR = 10;
    
    public static ModConfigSpec COMMON_CONFIG;
    
    public static ModConfigSpec.IntValue STAT_LIMIT_VALUE;
    public static ModConfigSpec.DoubleValue STAT_LIMIT_REDUCTION_RATE;
    
    public static ModConfigSpec.DoubleValue CHANCE_MOTIVATION_EFFECT;
    public static ModConfigSpec.DoubleValue DAMAGE_MOTIVATION_EFFECT;
    
    public static ModConfigSpec.IntValue GACHA_PROBABILITY_SUM;
    public static ModConfigSpec.IntValue GACHA_PROBABILITY_R;
    public static ModConfigSpec.IntValue GACHA_PROBABILITY_SR;
    public static ModConfigSpec.IntValue GACHA_PROBABILITY_SSR;

    public static ModConfigSpec CLIENT_CONFIG;
    public static ModConfigSpec.BooleanValue VANILLA_ARMOR_RENDER;
    public static ModConfigSpec.BooleanValue HIDE_PARTS_RENDER;
    public static ModConfigSpec.BooleanValue ELYTRA_RENDER;

    public static ModConfigSpec.IntValue EAR_ANIMATION_INTERVAL;
    public static ModConfigSpec.IntValue TAIL_ANIMATION_INTERVAL;

    public static ModConfigSpec.BooleanValue OVERLAY_SWITCH;
    public static ModConfigSpec.BooleanValue TOOLTIP_SWITCH;
    
    public static ModConfigSpec.DoubleValue UMASOUL_MAX_SPEED;
    public static ModConfigSpec.DoubleValue UMASOUL_MAX_STRENGTH_ATTACK;
    public static ModConfigSpec.DoubleValue UMASOUL_MAX_STAMINA_HEALTH;
    public static ModConfigSpec.DoubleValue UMASOUL_MAX_GUTS_ARMOR;
    public static ModConfigSpec.DoubleValue UMASOUL_MAX_GUTS_ARMOR_TOUGHNESS;
    
    public static ModConfigSpec.BooleanValue UMASOUL_SPEED_PRECENT_ENABLE;
    public static ModConfigSpec.BooleanValue UMASOUL_STRENGTH_PRECENT_ENABLE;
    public static ModConfigSpec.BooleanValue UMASOUL_STAMINA_PRECENT_ENABLE;
    public static ModConfigSpec.BooleanValue UMASOUL_GUTS_PRECENT_ENABLE;

    static {
        ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();
        COMMON_BUILDER.comment("General settings").push("general");
        
        UMASOUL_SPEED_PRECENT_ENABLE = COMMON_BUILDER.comment("Determines whether to add speed bouns as a percentage.",
                "If enable, the bonus will be calculated as a percentage.").define("umasoul_speed_percent_enable", true);
        
        UMASOUL_MAX_SPEED = COMMON_BUILDER
                .comment("Determining the max speed of umamusume soul.",
                        "Set to 0 will not add any speed.").defineInRange("umasoul_max_speed", 1.20, 0.0, Double.MAX_VALUE);
        
        UMASOUL_STRENGTH_PRECENT_ENABLE = COMMON_BUILDER.comment("Determines whether to add strength bouns as a percentage.",
                "If enable, the bonus will be calculated as a percentage.").define("umasoul_strength_percent_enable", true);
        
        UMASOUL_MAX_STRENGTH_ATTACK = COMMON_BUILDER
                .comment("Determining the max attack of umamusume soul.",
                        "Set to 0 will not add any attack.").defineInRange("umasoul_max_strength_attack", 2.0, 0.0, Double.MAX_VALUE);
        
        UMASOUL_STAMINA_PRECENT_ENABLE = COMMON_BUILDER.comment("Determines whether to add stamina bouns as a percentage.",
                "If enable, the bonus will be calculated as a percentage.").define("umasoul_stamina_percent_enable", false);
        
        UMASOUL_MAX_STAMINA_HEALTH = COMMON_BUILDER
                .comment("Determining the max health of umamusume soul.",
                        "Set to 0 will not add any health.").defineInRange("umasoul_max_stamina_health", 20, 0.0, Double.MAX_VALUE);
        
        UMASOUL_GUTS_PRECENT_ENABLE = COMMON_BUILDER.comment("Determines whether to add guts bouns as a percentage.",
                "If enable, the bonus will be calculated as a percentage.").define("umasoul_guts_percent_enable", false);
        
        UMASOUL_MAX_GUTS_ARMOR = COMMON_BUILDER
                .comment("Determining the max armor of umamusume soul.",
                        "Set to 0 will not add any health.").defineInRange("umasoul_max_guts_armor", 5.0, 0.0, Double.MAX_VALUE);
        
        UMASOUL_MAX_GUTS_ARMOR_TOUGHNESS = COMMON_BUILDER
                .comment("Determining the max armor toughness of umamusume soul.",
                        "Set to 0 will not add any health.").defineInRange("umasoul_max_guts_armor_toughness", 4.0, 0.0, Double.MAX_VALUE);

        CHANCE_MOTIVATION_EFFECT = COMMON_BUILDER.comment("Determining the chance that damage will effect motivation.",
                "Set to 0 to turn off.").defineInRange("chance_motivation_effect", 0.5, 0.0, 1.0);

        DAMAGE_MOTIVATION_EFFECT = COMMON_BUILDER
                .comment("Determining the damage that will effect motivation.",
                        "Set to 0 to turn off.").defineInRange("damage_motivation_effect", 4.0, 0.0, Double.MAX_VALUE);

        GACHA_PROBABILITY_SUM = COMMON_BUILDER
                .comment("Determining the sum of gacha probabilities.",
                        "If the sum of the three gacha probabilities does not equal the expected sum, follow the default configuration.")
                .defineInRange("gacha_probability_sum", DEFAULT_GACHA_PROBABILITY_SUM, 3, Integer.MAX_VALUE);
        
        GACHA_PROBABILITY_R = COMMON_BUILDER
                .comment("Determining the R ranking probability of gacha probabilities.",
                        "If the sum of the three gacha probabilities does not equal the expected sum, follow the default configuration.")
                .defineInRange("gacha_probability_r", DEFAULT_GACHA_PROBABILITY_R, 1, Integer.MAX_VALUE);
        
        GACHA_PROBABILITY_SR = COMMON_BUILDER
                .comment("Determining the SR ranking probability of gacha probabilities.",
                        "If the sum of the three gacha probabilities does not equal the expected sum, follow the default configuration.")
                .defineInRange("gacha_probability_sr", DEFAULT_GACHA_PROBABILITY_SR, 1, Integer.MAX_VALUE);
        
        GACHA_PROBABILITY_SSR = COMMON_BUILDER
                .comment("Determining the SSR ranking probability of gacha probabilities.",
                        "If the sum of the three gacha probabilities does not equal the expected sum, follow the default configuration.")
                .defineInRange("gacha_probability_ssr", DEFAULT_GACHA_PROBABILITY_SSR, 1, Integer.MAX_VALUE);
        
        STAT_LIMIT_VALUE = COMMON_BUILDER.comment("Determines the threshold for all base stat values.",
                "If the threshold value is exceeded, the attribute effect will be reduced according to stat_limit_reduction.",
                "Some attribute effects no longer increase after exceeding the threshold")
                .defineInRange("stat_limit_value", 18, 18, Integer.MAX_VALUE);
        STAT_LIMIT_REDUCTION_RATE = COMMON_BUILDER.comment("Determines the reduction for all base stat values.",
                "If the threshold value is exceeded, the attribute effect will be reduced according to this value.")
                .defineInRange("stat_limit_reduction", 0.6D, 0D, 1D);
        
        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
    static {
        ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();
        CLIENT_BUILDER.comment("Client settings").push("client");
        VANILLA_ARMOR_RENDER = CLIENT_BUILDER.comment("Determines whether to render vanilla armor slot's model.",
                "Player's armor won't render after closing this.").define("vanilla_armor_render", false);

        OVERLAY_SWITCH = CLIENT_BUILDER.comment("Decide Overlays' switch")
                .comment("Uma's selected skill and motivation won't render after closing this.")
                .define("overlay_switch", true);

        TOOLTIP_SWITCH = CLIENT_BUILDER.comment("Deciding whether to omit details.")
                .comment("After enabling, some details need to be pressed to display.").define("tooltip_switch", true);

        HIDE_PARTS_RENDER = CLIENT_BUILDER
                .comment("Determines whether to render model's hiden parts.",
                        "Hiden parts like breasts won't render after closing this.")
                .define("hiden_parts_render", false);
        ELYTRA_RENDER = CLIENT_BUILDER
                .comment("Determines whether to render player's elytra after closed vanilla armor render.",
                        "Player's elytra won't render after closing this.")
                .define("elytra_render", true);

        EAR_ANIMATION_INTERVAL = CLIENT_BUILDER.comment("Determining the interval tick between twice ear animation.")
                .defineInRange("ear_animation_interval", 100, 10, Integer.MAX_VALUE);
        TAIL_ANIMATION_INTERVAL = CLIENT_BUILDER.comment("Determining the interval tick between twice tail animation.")
                .defineInRange("tail_animation_interval", 200, 10, Integer.MAX_VALUE);

        CLIENT_BUILDER.pop();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }
}
