package net.tracen.umapyoi.capability;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.api.UmaSkillUtils;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.registry.UmaDataRegistry;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.skills.UmaSkill;
import net.tracen.umapyoi.registry.umadata.UmaStatus;

public class UmaCapability implements IUmaCapability {

    private final UmaStatus status;
    private final NonNullList<UmaSkill> skills = NonNullList.create();
    private UmaSkill selectSkill;
    private int cooldown = 0;

    public UmaCapability(ItemStack stack) {
        this.status = UmaStatus.CODEC.parse(NbtOps.INSTANCE, stack.getOrCreateTag().getCompound("status"))
                .resultOrPartial(msg -> {
                    Umapyoi.getLogger().error("Failed to parse {}: {}", stack.toString(), msg);
                    UmapyoiAPI.initUmaSoul(stack, UmaDataRegistry.COMMON_UMA.get());
                }).orElseGet(UmaCapability::defaultStatus);
        this.skills.add(UmaSkillRegistry.TEST_1.get());
        this.skills.add(UmaSkillRegistry.TEST_2.get());
        this.selectSkill = this.skills.get(0);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag result = new CompoundTag();
        result.put("status", this.getUmaStatus().serializeNBT());
        ListTag tagSkills = UmaSkillUtils.serializeNBT(getSkills());
        result.put("skills", tagSkills);
        result.putString("selectedSkill", this.getSelectedSkill().toString());
        result.putInt("skillCooldown", cooldown);
        return result;
    }

    @Override
    public void deserializeNBT(CompoundTag compound) {
        this.getUmaStatus().deserializeNBT(compound.getCompound("status"));
        UmaSkillUtils.deserializeNBT(this, compound);
        this.selectSkill = UmaSkillRegistry.REGISTRY.get()
                .getValue(new ResourceLocation(compound.getString("selectedSkill")));
        this.cooldown = compound.getInt("skillCooldown");
    }

    @Override
    public UmaStatus getUmaStatus() {
        return this.status;
    }

    public static UmaStatus defaultStatus() {
        return UmaDataRegistry.COMMON_UMA.get().status();
    }

    @Override
    public NonNullList<UmaSkill> getSkills() {
        return this.skills;
    }

    @Override
    public UmaSkill getSelectedSkill() {
        return this.selectSkill;
    }

    @Override
    public void selectFormerSkill() {
        if (this.getSkills().size() <= 1)
            return;
        int index = this.getSkills().contains(selectSkill) ? this.getSkills().indexOf(selectSkill) : 0;
        if (index == 0) {
            this.selectSkill = this.getSkills().get(this.getSkills().size() - 1);
        } else {
            this.selectSkill = this.getSkills().get(index - 1);
        }
    }

    @Override
    public void selectLatterSkill() {
        if (this.getSkills().size() <= 1)
            return;
        int index = this.getSkills().contains(selectSkill) ? this.getSkills().indexOf(selectSkill) : 0;
        if (index == this.getSkills().size() - 1) {
            this.selectSkill = this.getSkills().get(0);
        } else {
            this.selectSkill = this.getSkills().get(index + 1);
        }
    }
    
    @Override
    public int getCooldown() {
        return cooldown;
    }

    @Override
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    @Override
    public boolean isSkillReady() {
        return this.getCooldown() == 0;
    }
}
