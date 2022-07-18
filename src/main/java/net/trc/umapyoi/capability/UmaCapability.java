package net.trc.umapyoi.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.trc.umapyoi.api.UmapyoiAPI;

public class UmaCapability implements IUmaCapability {
    
    private int speed = 1;
    private int stamina = 1;
    private int strength = 1;
    private int mentality = 1;
    private int wisdom = 1;
    
    private int max_speed = 1200;
    private int max_stamina = 1200;
    private int max_strength = 1200;
    private int max_mentality = 1200;
    private int max_wisdom = 1200;
    
    public UmaCapability(ItemStack stack) {
        this.setSpeed(UmapyoiAPI.getUmaData(stack).property().get(0));
        this.setStamina(UmapyoiAPI.getUmaData(stack).property().get(1));
        this.setStrength(UmapyoiAPI.getUmaData(stack).property().get(2));
        this.setMentality(UmapyoiAPI.getUmaData(stack).property().get(3));
        this.setWisdom(UmapyoiAPI.getUmaData(stack).property().get(4));
        
        this.setMaxSpeed(UmapyoiAPI.getUmaData(stack).maxProperty().get(0));
        this.setMaxStamina(UmapyoiAPI.getUmaData(stack).maxProperty().get(1));
        this.setMaxStrength(UmapyoiAPI.getUmaData(stack).maxProperty().get(2));
        this.setMaxMentality(UmapyoiAPI.getUmaData(stack).maxProperty().get(3));
        this.setMaxWisdom(UmapyoiAPI.getUmaData(stack).maxProperty().get(4));
    }
    
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putInt("speed", speed);
        compoundTag.putInt("stamina", stamina);
        compoundTag.putInt("strength", strength);
        compoundTag.putInt("mentality", mentality);
        compoundTag.putInt("wisdom", wisdom);
        
        compoundTag.putInt("max_speed", max_speed);
        compoundTag.putInt("max_stamina", max_stamina);
        compoundTag.putInt("max_strength", max_strength);
        compoundTag.putInt("max_mentality", max_mentality);
        compoundTag.putInt("max_wisdom", max_wisdom);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag compound) {
        this.speed = compound.getInt("speed");
        this.stamina = compound.getInt("stamina");
        this.strength = compound.getInt("strength");
        this.mentality = compound.getInt("mentality");
        this.wisdom = compound.getInt("wisdom");
        
        this.max_speed = compound.getInt("max_speed");
        this.max_stamina = compound.getInt("max_stamina");
        this.max_strength = compound.getInt("max_strength");
        this.max_mentality = compound.getInt("max_mentality");
        this.max_wisdom = compound.getInt("max_wisdom");
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public int getStamina() {
        return stamina;
    }

    @Override
    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    @Override
    public int getStrength() {
        return strength;
    }

    @Override
    public void setStrength(int strength) {
        this.strength = strength;
    }

    @Override
    public int getMentality() {
        return mentality;
    }

    @Override
    public void setMentality(int mentality) {
        this.mentality = mentality;
    }

    @Override
    public int getWisdom() {
        return wisdom;
    }

    @Override
    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }


    @Override
    public int getMaxSpeed() {
        return max_speed;
    }

    @Override
    public void setMaxSpeed(int speed) {
        this.max_speed = speed;
    }

    @Override
    public int getMaxStamina() {
        return max_stamina;
    }

    @Override
    public void setMaxStamina(int stamina) {
        this.max_stamina = stamina;
    }

    @Override
    public int getMaxStrength() {
        return max_strength;
    }

    @Override
    public void setMaxStrength(int strength) {
        this.max_strength = strength;
    }

    @Override
    public int getMaxMentality() {
        return max_mentality;
    }

    @Override
    public void setMaxMentality(int mentality) {
        this.max_mentality = mentality;
    }

    @Override
    public int getMaxWisdom() {
        return max_wisdom;
    }

    @Override
    public void setMaxWisdom(int wisdom) {
        this.max_wisdom = wisdom;
    }

}
