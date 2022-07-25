package net.trc.umapyoi.curios;

import java.util.UUID;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import cn.mcmod_mmf.mmlib.utils.MathUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.trc.umapyoi.api.UmaStatus;
import net.trc.umapyoi.api.UmapyoiAPI;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class UmaSoulCuriosWrapper implements ICurio {
    private final ItemStack stack;

    public UmaSoulCuriosWrapper(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public SoundInfo getEquipSound(SlotContext slotContext) {
        return new SoundInfo(SoundEvents.ARMOR_EQUIP_LEATHER, 1.0f, 1.0f);
    }

    @Override
    public ItemStack getStack() {
        return stack;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.MOVEMENT_SPEED,
                new AttributeModifier(uuid, "speed_bonus",
                        MathUtil.lerp( ((double)UmapyoiAPI.getUmaSoulData(getStack()).getInt(UmaStatus.SPEED.getName()) / 1200.0D), 0.1, 3.0),
                        AttributeModifier.Operation.MULTIPLY_TOTAL));
        atts.put(Attributes.ATTACK_DAMAGE,
                new AttributeModifier(uuid, "strength_attack_bonus",
                        MathUtil.lerp((double) (UmapyoiAPI.getUmaSoulData(getStack()).getInt(UmaStatus.STRENGTH.getName()) / 1200), 0.0, 2),
                        AttributeModifier.Operation.MULTIPLY_TOTAL));
        atts.put(Attributes.ATTACK_SPEED,
                new AttributeModifier(uuid, "strength_speed_bonus",
                        MathUtil.lerp((double) (UmapyoiAPI.getUmaSoulData(getStack()).getInt(UmaStatus.STRENGTH.getName()) / 1200), 0.0, 1.25),
                        AttributeModifier.Operation.MULTIPLY_TOTAL));
        CuriosApi.getCuriosHelper().addSlotModifier(atts, "uma_suit", uuid, 1.0, AttributeModifier.Operation.ADDITION);
        return atts;
    }

}
