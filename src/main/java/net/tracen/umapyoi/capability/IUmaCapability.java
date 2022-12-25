package net.tracen.umapyoi.capability;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import net.tracen.umapyoi.registry.skills.UmaSkill;
import net.tracen.umapyoi.registry.umadata.UmaStatus;

public interface IUmaCapability extends INBTSerializable<CompoundTag> {
    public UmaStatus getUmaStatus();
    
    public NonNullList<UmaSkill> getSkills();
    public UmaSkill getSelectedSkill();
    public void selectFormerSkill();
    public void selectLatterSkill();
    
    public int getCooldown();
    public void setCooldown(int cooldown);
    public int getMaxCooldown();
    public void setMaxCooldown(int cooldown);
    public boolean isSkillReady();
    
}
