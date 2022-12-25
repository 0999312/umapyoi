package net.tracen.umapyoi;

import net.minecraftforge.common.ForgeConfigSpec;

public class UmapyoiConfig {
    public static ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.IntValue ITEM_LOW_VALUE;
    public static ForgeConfigSpec.IntValue ITEM_MID_VALUE;
    public static ForgeConfigSpec.IntValue ITEM_HIGH_VALUE;

    public static ForgeConfigSpec CLIENT_CONFIG;
    public static ForgeConfigSpec.BooleanValue VANILLA_ARMOR_RENDER;
    public static ForgeConfigSpec.BooleanValue HIDE_PARTS_RENDER;
    public static ForgeConfigSpec.BooleanValue ELYTRA_RENDER;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.comment("General settings").push("general");

        ITEM_LOW_VALUE = COMMON_BUILDER.comment("Determining the values that Notepads can provide.")
                .defineInRange("stat_limit_value", 1, 0, 10);

        ITEM_MID_VALUE = COMMON_BUILDER.comment("Determining the values that Writings can provide.")
                .defineInRange("stat_limit_value", 2, 0, 10);

        ITEM_HIGH_VALUE = COMMON_BUILDER.comment("Determining the values that Scrolls can provide.")
                .defineInRange("stat_limit_value", 3, 0, 10);

        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
    static {
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
        CLIENT_BUILDER.comment("Client settings").push("client");
        VANILLA_ARMOR_RENDER = CLIENT_BUILDER.comment("Determines whether to render vanilla armor slot's model.",
                "Player's armor won't render after closing this.").define("vanilla_armor_render", false);
        HIDE_PARTS_RENDER = CLIENT_BUILDER.comment("Determines whether to render model's hiden parts.",
                "Hiden parts like breasts won't render after closing this.").define("hiden_parts_render", false);
        ELYTRA_RENDER = CLIENT_BUILDER
                .comment("Determines whether to render player's elytra after closed vanilla armor render.",
                        "Player's elytra won't render after closing this.")
                .define("elytra_render", true);
        CLIENT_BUILDER.pop();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }
}
