package net.tracen.umapyoi.registry.factors;

import java.util.Random;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class ExtraStatusFactor extends UmaFactor {

    private final int statusType;

    public ExtraStatusFactor(int type) {
        super(FactorType.EXTRASTATUS);
        this.statusType = type;
    }

    @Override
    public void applyFactor(ItemStack soul, UmaFactorStack stack) {
        int level = stack.getLevel();
        var chance = stack.getLevel() * 0.33;
        Random rand = new Random();
        for (int roll = 1; roll < stack.getLevel(); roll++) {
            if (rand.nextFloat() > chance)
                level--;
        }

        if (level != 0) {
            if (this.statusType == 0)
                UmaSoulUtils.getExtraProperty(soul)[statusType] = Math.min(5,
                        UmaSoulUtils.getExtraProperty(soul)[statusType] + level);
            else if (this.statusType == 3)
                UmaSoulUtils.getExtraProperty(soul)[statusType] += level * 100;
            else
                UmaSoulUtils.getExtraProperty(soul)[statusType] += level;
        }
    }

    @Override
    public Component getDescription(UmaFactorStack stack) {
        return this.getFullDescription(stack.getLevel());
    }

}
