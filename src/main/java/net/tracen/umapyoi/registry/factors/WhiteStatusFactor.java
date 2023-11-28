package net.tracen.umapyoi.registry.factors;

import java.util.Random;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.utils.UmaSoulUtils;
import net.tracen.umapyoi.utils.UmaStatusUtils.StatusType;

public class WhiteStatusFactor extends UmaFactor {

    private final StatusType statusType;

    public WhiteStatusFactor(StatusType status) {
        super(FactorType.OTHER);
        this.statusType = status;
    }

    @Override
    public void applyFactor(ItemStack soul, UmaFactorStack stack) {
        int statusLevel = stack.getLevel();
        int maxStatusLevel = stack.getLevel();
        var chance = stack.getLevel() * 0.25;
        Random rand = new Random();
        for (int roll = 0; roll < stack.getLevel(); roll++) {
            if (rand.nextFloat() > chance)
                maxStatusLevel--;
        }
        UmaSoulUtils.getMaxProperty(soul)[statusType.getId()] += maxStatusLevel;
        for (int roll = 0; roll < stack.getLevel(); roll++) {
            if (rand.nextFloat() > chance)
                statusLevel--;
        }
        UmaSoulUtils.getProperty(soul)[statusType.getId()] += Math.min(maxStatusLevel, statusLevel + stack.getLevel());
    }

    @Override
    public Component getDescription(UmaFactorStack stack) {
        return this.getFullDescription(stack.getLevel());
    }

}
