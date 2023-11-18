package net.tracen.umapyoi.registry.skills;

import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.tracen.umapyoi.Umapyoi;

public class UmaSkill extends ForgeRegistryEntry<UmaSkill> {
    private final SkillType type;
    private final int requiredWisdom;
    private final int actionPoint;
    private final int level;
    private final SoundEvent sound;

    private String descriptionId;

    public static final ResourceKey<Registry<UmaSkill>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(new ResourceLocation(Umapyoi.MODID, "skill"));

    public UmaSkill(Builder builder) {
        this.type = builder.type;
        this.requiredWisdom = builder.requiredWisdom;
        this.level = builder.level;
        this.actionPoint = builder.actionPoint;
        this.sound = builder.sound;
    }

    public SkillType getType() {
        return type;
    }

    public int getRequiredWisdom() {
        return requiredWisdom;
    }
    
    public int getSkillLevel() {
        return level;
    }

    public int getActionPoint() {
        return actionPoint;
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

    public SoundEvent getSound() {
        return sound;
    }

    public static class Builder {
        private SkillType type = SkillType.BUFF;
        private int requiredWisdom = 0;
        private int actionPoint = 200;
        private int level = 1;
        private SoundEvent sound = SoundEvents.PLAYER_ATTACK_SWEEP;

        public Builder type(SkillType type) {
            this.type = type;
            return this;
        }

        public Builder requiredWisdom(int requiredWisdom) {
            this.requiredWisdom = requiredWisdom;
            return this;
        }

        public Builder sound(SoundEvent sound) {
            this.sound = sound;
            return this;
        }

        public Builder actionPoint(int ap) {
            this.actionPoint = ap;
            return this;
        }
        
        public Builder level(int level) {
            this.level = level;
            return this;
        }

    }

}
