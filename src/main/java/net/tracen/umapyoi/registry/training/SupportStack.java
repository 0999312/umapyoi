package net.tracen.umapyoi.registry.training;

import java.util.Optional;

import javax.annotation.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.tracen.umapyoi.events.ApplyTrainingSupportEvent;
import net.tracen.umapyoi.registry.TrainingSupportRegistry;

public class SupportStack {
    private final TrainingSupport factor;
    private final int level;
    @Nullable
    private CompoundTag tag;

    public static final Codec<SupportStack> CODEC = RecordCodecBuilder
            .create(instance -> instance
                    .group(TrainingSupport.CODEC.fieldOf("support").forGetter(SupportStack::getFactor),
                            Codec.INT.fieldOf("level").forGetter(SupportStack::getLevel),
                            CompoundTag.CODEC.optionalFieldOf("tag")
                                    .forGetter(stack -> Optional.ofNullable(stack.getTag())))
                    .apply(instance, SupportStack::new));

    public static final SupportStack EMPTY = new SupportStack(null, 0);

    public SupportStack(TrainingSupport factor, int level) {
        this.factor = factor;
        this.level = level;
    }

    public SupportStack(TrainingSupport factor, int level, CompoundTag nbt) {
        this(factor, level);
        if (nbt != null) {
            tag = nbt.copy();
        }
    }

    public SupportStack(TrainingSupport factor, int level, Optional<CompoundTag> nbt) {
        this(factor, level);
        nbt.ifPresent(this::setTag);
    }

    public TrainingSupport getFactor() {
        return factor;
    }

    public int getLevel() {
        return level;
    }

    public boolean isEmpty() {
        if (this == EMPTY) {
            return true;
        } else if (this.getFactor() != null) {
            return this.getLevel() <= 0;
        } else {
            return true;
        }
    }

    public boolean applySupport(ItemStack soul) {
        if (!MinecraftForge.EVENT_BUS.post(new ApplyTrainingSupportEvent.Pre(this, soul))) {
            boolean result = this.getFactor().applySupport(soul, this);
            return result && !MinecraftForge.EVENT_BUS.post(new ApplyTrainingSupportEvent.Post(this, soul));
        } else
            return false;
    }

    public Component getDescription() {
        return this.getFactor().getDescription(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj instanceof SupportStack stack)
            return stack.level == this.level && stack.factor == this.factor;
        return false;
    }

    @Override
    public int hashCode() {
        int code = 31 * Integer.hashCode(getLevel()) + TrainingSupportRegistry.REGISTRY.get().getKey(this.getFactor()).hashCode();
        if (tag != null)
            code = 31 * code + tag.hashCode();
        return code;
    }

    @Override
    public String toString() {
        return String.format("level:%d, support:%s", this.getLevel(), this.factor);
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
