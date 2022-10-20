package net.tracen.umapyoi.api;

import java.util.List;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.tracen.umapyoi.capability.IUmaCapability;
import net.tracen.umapyoi.registry.UmaSkillRegistry;
import net.tracen.umapyoi.registry.skills.UmaSkill;

public class UmaSkillUtils {
    public static ListTag serializeNBT(List<UmaSkill> skills) {
        ListTag result = new ListTag();
        for (UmaSkill skill : skills) {
            result.add(StringTag.valueOf(skill.toString()));
        }
        return result;
    }

    public static void deserializeNBT(IUmaCapability cap, CompoundTag compound) {
        compound.getList("skills", Tag.TAG_COMPOUND).forEach(tag -> {
            UmaSkill skill = UmaSkillRegistry.REGISTRY.get().getValue(ResourceLocation.tryParse(tag.getAsString()));
            cap.getSkills().add(skill);
        });
    }
}
