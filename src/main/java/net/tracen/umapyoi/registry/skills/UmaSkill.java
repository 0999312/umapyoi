package net.tracen.umapyoi.registry.skills;

import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.tracen.umapyoi.Umapyoi;

public class UmaSkill extends ForgeRegistryEntry<UmaSkill> {
    private final SkillType type;
    private final int requiredWisdom;
    private final int successRate;
    private final int cooldown;

    private final ResourceLocation parentSkill;
    private final ResourceLocation upperSkill;

    private final ResourceLocation requiredUma;
    private String descriptionId;

    public static final ResourceKey<Registry<UmaSkill>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(new ResourceLocation(Umapyoi.MODID, "skill"));

    public UmaSkill(Builder builder) {
        this.type = builder.type;
        this.requiredWisdom = builder.requiredWisdom;
        this.successRate = builder.successRate;
        this.cooldown = builder.cooldown;
        this.parentSkill = builder.parentSkill;
        this.upperSkill = builder.upperSkill;
        this.requiredUma = builder.requiredUma;
    }

    public SkillType getType() {
        return type;
    }

    public int getRequiredWisdom() {
        return requiredWisdom;
    }

    public int getSuccessRate() {
        return successRate;
    }

    public ResourceLocation getParentSkill() {
        return parentSkill;
    }

    public ResourceLocation getUpperSkill() {
        return upperSkill;
    }

    public ResourceLocation getRequiredUma() {
        return requiredUma;
    }

    public int getCooldown() {
        return cooldown;
    }

    public Component getDescription() {
        return new TranslatableComponent(this.getDescriptionId());
    }

    public String toString() {
        return this.getRegistryName().toString();
    }

    protected String getOrCreateDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("uma_skill", this.getRegistryName());
        }
        return this.descriptionId;
    }

    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }

    public void applySkill(Level level, Player user) {
        Umapyoi.getLogger().error(String.format("Wait, %s is an empty skill! Call the dev!", this.toString()));
    }

    public static class Builder {
        private SkillType type = SkillType.BUFF;
        private int requiredWisdom = 0;
        private int successRate = 100;
        private int cooldown = 1200;

        private ResourceLocation parentSkill = null;
        private ResourceLocation upperSkill = null;
        private ResourceLocation requiredUma = null;

        public void type(SkillType type) {
            this.type = type;
        }

        public void requiredWisdom(int requiredWisdom) {
            this.requiredWisdom = requiredWisdom;
        }

        public void successRate(int successRate) {
            this.successRate = successRate;
        }

        public void cooldown(int tick) {
            this.cooldown = tick;
        }

        public void parentSkill(ResourceLocation parentSkill) {
            this.parentSkill = parentSkill;
        }

        public void upperSkill(ResourceLocation upperSkill) {
            this.upperSkill = upperSkill;
        }

        public void requiredUma(ResourceLocation requiredUma) {
            this.requiredUma = requiredUma;
        }
    }
}
