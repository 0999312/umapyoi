package net.trc.umapyoi.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IUmaCapability extends INBTSerializable<CompoundTag> {
    
    public int getSpeed();
    public void setSpeed(int speed);

    public int getStamina();
    public void setStamina(int stamina);

    public int getStrength();
    public void setStrength(int strength);

    public int getMentality();
    public void setMentality(int mentality);

    public int getWisdom();
    public void setWisdom(int wisdom);
    
    public int getMaxSpeed();
    public void setMaxSpeed(int speed);

    public int getMaxStamina();
    public void setMaxStamina(int stamina);

    public int getMaxStrength();
    public void setMaxStrength(int strength);

    public int getMaxMentality();
    public void setMaxMentality(int mentality);

    public int getMaxWisdom();
    public void setMaxWisdom(int wisdom);
}
