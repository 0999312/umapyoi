package net.tracen.umapyoi;

import net.minecraftforge.common.ForgeConfigSpec;

public class UmapyoiConfig {
    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec.DoubleValue CHANCE_MOTIVATION_EFFECT;
    public static ForgeConfigSpec.DoubleValue DAMAGE_MOTIVATION_EFFECT;

    public static ForgeConfigSpec CLIENT_CONFIG;
    public static ForgeConfigSpec.BooleanValue VANILLA_ARMOR_RENDER;
    public static ForgeConfigSpec.BooleanValue HIDE_PARTS_RENDER;
    public static ForgeConfigSpec.BooleanValue ELYTRA_RENDER;
    
    public static ForgeConfigSpec.IntValue EAR_ANIMATION_INTERVAL;
    public static ForgeConfigSpec.IntValue TAIL_ANIMATION_INTERVAL;
    
    public static ForgeConfigSpec.BooleanValue OVERLAY_SWITCH;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.comment("General settings").push("general");
        
        CHANCE_MOTIVATION_EFFECT = COMMON_BUILDER.comment("Determining the chance that damage will effect motivation.")
                .comment("Set to 0 to turn off.")
                .defineInRange("chance_motivation_effect", 0.5, 0.0, 1.0);
        
        DAMAGE_MOTIVATION_EFFECT = COMMON_BUILDER.comment("Determining the damage that will effect motivation.")
                .comment("Set to 0 to turn off.")
                .defineInRange("damage_motivation_effect", 4.0, 0.0, Double.MAX_VALUE);

        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
    static {
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
        CLIENT_BUILDER.comment("Client settings").push("client");
        VANILLA_ARMOR_RENDER = CLIENT_BUILDER.comment("Determines whether to render vanilla armor slot's model.",
                "Player's armor won't render after closing this.").define("vanilla_armor_render", false);
        
        OVERLAY_SWITCH = CLIENT_BUILDER.comment("Decide Overlays' switch")
                .comment("Uma's selected skill and motivation won't render after closing this.")
                .define("overlay_switch", true);
        
        HIDE_PARTS_RENDER = CLIENT_BUILDER.comment("Determines whether to render model's hiden parts.",
                "Hiden parts like breasts won't render after closing this.").define("hiden_parts_render", false);
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
