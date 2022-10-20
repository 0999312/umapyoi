package net.tracen.umapyoi;

import net.minecraftforge.common.ForgeConfigSpec;

public class UmapyoiConfig {
    public static ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.IntValue ITEM_LOW_VALUE;
    public static ForgeConfigSpec.IntValue ITEM_MID_VALUE;
    public static ForgeConfigSpec.IntValue ITEM_HIGH_VALUE;

    public static ForgeConfigSpec.IntValue STAT_LIMIT_VALUE;
    public static ForgeConfigSpec.DoubleValue STAT_LIMIT_REDUCTION_RATE;

    public static ForgeConfigSpec CLIENT_CONFIG;
    public static ForgeConfigSpec.BooleanValue VANILLA_ARMOR_RENDER;
    public static ForgeConfigSpec.BooleanValue ELYTRA_RENDER;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.comment("General settings").push("general");

        ITEM_LOW_VALUE = COMMON_BUILDER.comment("Determining the values that Notepads can provide.")
                .defineInRange("stat_limit_value", 3, 0, Integer.MAX_VALUE);

        ITEM_MID_VALUE = COMMON_BUILDER.comment("Determining the values that Writings can provide.")
                .defineInRange("stat_limit_value", 7, 0, Integer.MAX_VALUE);

        ITEM_HIGH_VALUE = COMMON_BUILDER.comment("Determining the values that Scrolls can provide.")
                .defineInRange("stat_limit_value", 15, 0, Integer.MAX_VALUE);

        STAT_LIMIT_VALUE = COMMON_BUILDER.comment("Determines the threshold for all base stat values.",
                "If the threshold value is exceeded, the attribute effect will be reduced according to stat_limit_reduction.",
                "Some attribute effects no longer increase after exceeding the threshold")
                .defineInRange("stat_limit_value", 1200, 0, Integer.MAX_VALUE);
        STAT_LIMIT_REDUCTION_RATE = COMMON_BUILDER.comment("Determines the reduction for all base stat values.",
                "If the threshold value is exceeded, the attribute effect will be reduced according to this value.")
                .defineInRange("stat_limit_reduction", 0.5D, 0D, 1D);
        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
    static {
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
        CLIENT_BUILDER.comment("Client settings").push("client");
        VANILLA_ARMOR_RENDER = CLIENT_BUILDER.comment("Determines whether to render vanilla armor slot's model.",
                "Player's armor won't render after closing this.").define("vanilla_armor_render", false);
        ELYTRA_RENDER = CLIENT_BUILDER
                .comment("Determines whether to render player's elytra after closed vanilla armor render.",
                        "Player's elytra won't render after closing this.")
                .define("elytra_render", true);
        CLIENT_BUILDER.pop();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }
}
