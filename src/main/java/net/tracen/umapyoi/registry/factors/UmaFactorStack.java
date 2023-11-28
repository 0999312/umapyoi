package net.tracen.umapyoi.registry.factors;

import java.util.Optional;

import javax.annotation.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.tracen.umapyoi.events.ApplyFactorEvent;
import net.tracen.umapyoi.registry.UmaFactorRegistry;

public class UmaFactorStack {
    private final UmaFactor factor;
    private final int level;
    @Nullable
    private CompoundTag tag;

    public static final Codec<UmaFactorStack> CODEC = RecordCodecBuilder
            .create(instance -> instance
                    .group(UmaFactor.CODEC.fieldOf("factor").forGetter(UmaFactorStack::getFactor),
                            Codec.INT.fieldOf("level").forGetter(UmaFactorStack::getLevel),
                            CompoundTag.CODEC.optionalFieldOf("Tag")
                                    .forGetter(stack -> Optional.ofNullable(stack.getTag())))
                    .apply(instance, UmaFactorStack::new));

    public UmaFactorStack(UmaFactor factor, int level) {
        this.factor = factor;
        this.level = level;
    }

    public UmaFactorStack(UmaFactor factor, int level, CompoundTag nbt) {
        this(factor, level);
        if (nbt != null) {
            tag = nbt.copy();
        }
    }

    public UmaFactorStack(UmaFactor factor, int level, Optional<CompoundTag> nbt) {
        this(factor, level);
        nbt.ifPresent(this::setTag);
    }

    public UmaFactor getFactor() {
        return factor;
    }

    public int getLevel() {
        return level;
    }

    public void applyFactor(ItemStack soul) {
        if (!MinecraftForge.EVENT_BUS.post(new ApplyFactorEvent.Pre(this, soul))) {
            this.getFactor().applyFactor(soul, this);
            MinecraftForge.EVENT_BUS.post(new ApplyFactorEvent.Post(this, soul));
        }
    }

    public Component getDescription() {
        return this.getFactor().getDescription(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj instanceof UmaFactorStack stack)
            return stack.level == this.level && stack.factor == this.factor;
        return false;
    }

    @Override
    public int hashCode() {
        int code = 31 * Integer.hashCode(getLevel()) + UmaFactorRegistry.REGISTRY.get().getKey(this.getFactor()).hashCode();
        if (tag != null)
            code = 31 * code + tag.hashCode();
        return code;
    }

    @Override
    public String toString() {
        return String.format("level:%d, factor:%s", this.getLevel(), this.factor);
    }

    // Copied from ItemStack, for Tags.

    /**
     * Returns true if the SupportStack has an NBTTagCompound.
     */
    public boolean hasTag() {
        return this.tag != null && !this.tag.isEmpty();
    }

    @Nullable
    public CompoundTag getTag() {
        return this.tag;
    }

    public CompoundTag getOrCreateTag() {
        if (this.tag == null) {
            this.setTag(new CompoundTag());
        }
        return this.tag;
    }

    public CompoundTag getOrCreateTagElement(String pKey) {
        if (this.tag != null && this.tag.contains(pKey, 10)) {
            return this.tag.getCompound(pKey);
        } else {
            CompoundTag compoundtag = new CompoundTag();
            this.addTagElement(pKey, compoundtag);
            return compoundtag;
        }
    }

    /**
     * Get an NBTTagCompound from this stack's NBT data.
     */
    @Nullable
    public CompoundTag getTagElement(String pKey) {
        return this.tag != null && this.tag.contains(pKey, 10) ? this.tag.getCompound(pKey) : null;
    }

    public void addTagElement(String pKey, Tag pTag) {
        this.getOrCreateTag().put(pKey, pTag);
    }

    public void removeTagKey(String pKey) {
        if (this.tag != null && this.tag.contains(pKey)) {
            this.tag.remove(pKey);
            if (this.tag.isEmpty()) {
                this.tag = null;
            }
        }
    }

    /**
     * Assigns a NBTTagCompound to the stack
     */
    public void setTag(@Nullable CompoundTag tag) {
        this.tag = tag;
    }
}
