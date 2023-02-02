package net.tracen.umapyoi.utils;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;

import net.minecraft.Util;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.capability.UmaCapability;
import net.tracen.umapyoi.item.UmaSoulItem;
import net.tracen.umapyoi.registry.UmaDataRegistry;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.registry.umadata.UmaStatus;

public class UmaSoulUtils {
    
    public static ResourceLocation getUmaSoulName(ItemStack stack) {
        return Optional.ofNullable(
                ResourceLocation.tryParse(
                        stack.getOrCreateTag()
                        .getCompound("cap")
                        .getCompound("status")
                        .getString("name")
                        )
                ).orElse(UmaDataRegistry.COMMON_UMA.getId());
    }
    
    public static Component getTranslatedUmaName(ItemStack stack) {
        return UmaSoulUtils.getTranslatedUmaName(UmaSoulUtils.getUmaSoulName(stack));
    }
    
    public static Component getTranslatedUmaName(ResourceLocation name) {
        return new TranslatableComponent(Util.makeDescriptionId("umadata", name));
    }
    
    public static ItemStack initUmaSoul(ItemStack stack, UmaData data) {
        return initUmaSoul(stack, data.status(), Lists.newArrayList(data.uniqueSkill()));
    }

    public static ItemStack initUmaSoul(ItemStack stack, UmaStatus status, List<ResourceLocation> skills) {
        if (stack.getItem() instanceof UmaSoulItem) {
            Tag result = UmaCapability.CODEC.encodeStart(NbtOps.INSTANCE, new UmaCapability(status, skills))
                    .resultOrPartial(msg -> {
                        Umapyoi.getLogger().error("Failed to encode UmaCapability: {}", msg);
                    }).orElseThrow();
            stack.getOrCreateTag().put("cap", result);
        }
        return stack;
    }
}
