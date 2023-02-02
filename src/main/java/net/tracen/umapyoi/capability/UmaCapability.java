package net.tracen.umapyoi.capability;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.UmaDataRegistry;
import net.tracen.umapyoi.registry.umadata.UmaStatus;

public class UmaCapability implements IUmaCapability {
    private UmaStatus status;
    private List<ResourceLocation> skills;
    private int selectedSkill = 0;
    private int actionPoint = 0;
    private int maxActionPoint = 0;
    private int skillslots = 2;

    public static final Codec<UmaCapability> CODEC = RecordCodecBuilder.create(instance -> 
        instance.group(
                UmaStatus.CODEC.fieldOf("status").forGetter(UmaCapability::getUmaStatus),
                ResourceLocation.CODEC.listOf().fieldOf("skills")
                .xmap(deserialize -> deserialize.stream().collect(Collectors.toList()), serialize -> serialize)
                .forGetter(UmaCapability::getSkills),
                Codec.INT.optionalFieldOf("selectedSkill", 0).forGetter(UmaCapability::getSelectedSkillIndex),
                Codec.INT.optionalFieldOf("actionPoint", 200).forGetter(UmaCapability::getActionPoint),
                Codec.INT.optionalFieldOf("maxActionPoint", 200).forGetter(UmaCapability::getMaxActionPoint),
                Codec.INT.optionalFieldOf("skillSlots", 4).forGetter(UmaCapability::getSkillSlots)
        ).apply(instance, UmaCapability::applyCodec)
    );
    
    private static UmaCapability applyCodec(UmaStatus status, List<ResourceLocation> skills, int selectSkill, int ap, int maxAP, int slots) {
        return new UmaCapability(status, skills, selectSkill, ap, maxAP, slots);
    }
    
    public UmaCapability(ItemStack stack) {
        CompoundTag compound = stack.getOrCreateTag().getCompound("cap");
        UmaCapability result = UmaCapability.CODEC.parse(NbtOps.INSTANCE, compound).resultOrPartial(msg -> {
            Umapyoi.getLogger().error("Failed to parse UmaCapability: {}", msg);
        }).orElseThrow();
        this.status = result.status;
        this.skills = result.skills;
        this.selectedSkill = result.selectedSkill;
        this.actionPoint = result.actionPoint;
        this.maxActionPoint = result.maxActionPoint;
        this.skillslots = result.skillslots;
    }
    
    public UmaCapability(UmaStatus status, List<ResourceLocation> skills) {
        this(status, skills, 0, status.property()[4] * 200, status.property()[4] * 200, 4);
    }
    
    private UmaCapability(UmaStatus status, List<ResourceLocation> skills, int selectSkill, int ap, int maxAP, int slots) {
        this.status = status;
        this.skills = skills;
        this.selectedSkill = selectSkill;
        this.actionPoint = ap;
        this.maxActionPoint = maxAP;
        this.skillslots = slots;
    }

    @Override
    public CompoundTag serializeNBT() {
        Tag result = UmaCapability.CODEC.encodeStart(NbtOps.INSTANCE, this).resultOrPartial(msg -> {
            Umapyoi.getLogger().error("Failed to encode UmaCapability: {}", msg);
        }).orElseThrow();
        if(result instanceof CompoundTag compound) return compound;
        return new CompoundTag();
    }

    @Override
    public void deserializeNBT(CompoundTag compound) {
        UmaCapability result = UmaCapability.CODEC.parse(NbtOps.INSTANCE, compound).resultOrPartial(msg -> {
            Umapyoi.getLogger().error("Failed to parse UmaCapability: {}", msg);
        }).orElseThrow();
        this.status = result.status;
        this.skills = result.skills;
        this.selectedSkill = result.selectedSkill;
        this.actionPoint = result.actionPoint;
        this.maxActionPoint = result.maxActionPoint;
        this.skillslots = result.skillslots;
    }

    @Override
    public UmaStatus getUmaStatus() {
        return this.status;
    }

    public static UmaStatus defaultStatus() {
        return UmaDataRegistry.COMMON_UMA.get().status();
    }

    @Override
    public List<ResourceLocation> getSkills() {
        return this.skills;
    }

    @Override
    public ResourceLocation getSelectedSkill() {
        return this.getSkills().get(selectedSkill);
    }
    
    private int getSelectedSkillIndex() {
        return selectedSkill;
    }

    @Override
    public void selectFormerSkill() {
        if (this.getSkills().size() <= 1)
            return;
        if (selectedSkill == 0) {
            this.selectedSkill = this.getSkills().size() - 1;
        } else {
            this.selectedSkill --;
        }
        Umapyoi.getLogger().info(String.format("now selected num:%d, %s", this.selectedSkill, this.getSelectedSkill()));
    }

    @Override
    public void selectLatterSkill() {
        if (this.getSkills().size() <= 1)
            return;
        if (selectedSkill == this.getSkills().size() - 1) {
            this.selectedSkill = 0;
        } else {
            this.selectedSkill ++;
        }
        Umapyoi.getLogger().info(String.format("now selected num:%d, %s", this.selectedSkill, this.getSelectedSkill()));
    }
    
    @Override
    public int getActionPoint() {
        return actionPoint;
    }

    @Override
    public void setActionPoint(int ap) {
        this.actionPoint = ap;
    }

    @Override
    public int getMaxActionPoint() {
        return this.maxActionPoint;
    }

    @Override
    public void setMaxActionPoint(int ap) {
        this.maxActionPoint = ap;
    }

    @Override
    public int getSkillSlots() {
        return this.skillslots;
    }

    @Override
    public void setSkillSlots(int slots) {
        this.skillslots = slots;
    }
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("status", this.status)
                .add("skills", this.skills)
                .add("selectedSkillIndex", this.selectedSkill)
                .add("actionPoint", this.actionPoint)
                .add("maxActionPoint", this.maxActionPoint)
                .add("skillSlots", this.skillslots)
                .toString();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.status,this.skills,this.selectedSkill,this.actionPoint,this.maxActionPoint,this.skillslots);
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof UmaCapability otherCap) {
            return Objects.equals(this.status, otherCap.status) &&
                    Objects.equals(this.skills, otherCap.skills) &&
                    Objects.equals(this.selectedSkill, otherCap.selectedSkill) &&
                    Objects.equals(this.actionPoint, otherCap.actionPoint) &&
                    Objects.equals(this.maxActionPoint, otherCap.maxActionPoint) &&
                    Objects.equals(this.skillslots, otherCap.skillslots)
                    ;
        }
        return false;
    }
    
}
