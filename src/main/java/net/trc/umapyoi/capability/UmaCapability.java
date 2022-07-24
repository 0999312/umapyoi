package net.trc.umapyoi.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.trc.umapyoi.api.UmaStatus;
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
        compoundTag.putInt(UmaStatus.SPEED.getName(), speed);
        compoundTag.putInt(UmaStatus.STAMINA.getName(), stamina);
        compoundTag.putInt(UmaStatus.STRENGTH.getName(), strength);
        compoundTag.putInt(UmaStatus.MENTALITY.getName(), mentality);
        compoundTag.putInt(UmaStatus.WISDOM.getName(), wisdom);
        
        compoundTag.putInt(UmaStatus.MAX_SPEED.getName(), max_speed);
        compoundTag.putInt(UmaStatus.MAX_STAMINA.getName(), max_stamina);
        compoundTag.putInt(UmaStatus.MAX_STRENGTH.getName(), max_strength);
        compoundTag.putInt(UmaStatus.MAX_MENTALITY.getName(), max_mentality);
        compoundTag.putInt(UmaStatus.MAX_WISDOM.getName(), max_wisdom);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag compound) {
        this.speed = compound.getInt(UmaStatus.SPEED.getName());
        this.stamina = compound.getInt(UmaStatus.STAMINA.getName());
        this.strength = compound.getInt(UmaStatus.STRENGTH.getName());
        this.mentality = compound.getInt(UmaStatus.MENTALITY.getName());
        this.wisdom = compound.getInt(UmaStatus.WISDOM.getName());
        
        this.max_speed = compound.getInt(UmaStatus.MAX_SPEED.getName());
        this.max_stamina = compound.getInt(UmaStatus.MAX_STAMINA.getName());
        this.max_strength = compound.getInt(UmaStatus.MAX_STRENGTH.getName());
        this.max_mentality = compound.getInt(UmaStatus.MAX_MENTALITY.getName());
        this.max_wisdom = compound.getInt(UmaStatus.MAX_WISDOM.getName());
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = Math.min(speed, this.getMaxSpeed());
    }

    @Override
    public int getStamina() {
        return stamina;
    }

    @Override
    public void setStamina(int stamina) {
        this.stamina = Math.min(stamina, this.getMaxStamina());
    }

    @Override
    public int getStrength() {
        return strength;
    }

    @Override
    public void setStrength(int strength) {
        this.strength = Math.min(strength, this.getMaxStrength());
    }

    @Override
    public int getMentality() {
        return mentality;
    }

    @Override
    public void setMentality(int mentality) {
        this.mentality = Math.min(mentality, this.getMaxMentality());
    }

    @Override
    public int getWisdom() {
        return wisdom;
    }

    @Override
    public void setWisdom(int wisdom) {
        this.wisdom = Math.min(wisdom, this.getMaxWisdom());
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
