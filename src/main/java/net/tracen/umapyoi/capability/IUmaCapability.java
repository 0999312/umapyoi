package net.tracen.umapyoi.capability;

import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;
import net.tracen.umapyoi.registry.umadata.UmaStatus;

public interface IUmaCapability extends INBTSerializable<CompoundTag> {
    public UmaStatus getUmaStatus();
    
    public List<ResourceLocation> getSkills();
    public ResourceLocation getSelectedSkill();
    public int getSkillSlots();
    public void setSkillSlots(int slots);
    public void selectFormerSkill();
    public void selectLatterSkill();
    
    public int getActionPoint();
    public void setActionPoint(int ap);
    public int getMaxActionPoint();
    public void setMaxActionPoint(int ap);
    
}
