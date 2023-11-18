package net.tracen.umapyoi.registry.factors;

import java.util.Random;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class WhiteExtraStatusFactor extends UmaFactor {

    private final int statusType;

    public WhiteExtraStatusFactor(int type) {
        super(FactorType.OTHER);
        this.statusType = type;
    }

    @Override
    public void applyFactor(ItemStack soul, UmaFactorStack stack) {
        int level = stack.getLevel();
        boolean isAP = this.statusType == 3;
        var chance = stack.getLevel() * 0.2;
        Random rand = new Random();
        for (int roll = 0; roll < stack.getLevel(); roll++) {
            if (rand.nextFloat() > chance)
                level--;
        }

        if (level != 0) {
            if (this.statusType == 0)
                UmaSoulUtils.getExtraProperty(soul)[statusType] = Math.min(5,
                        UmaSoulUtils.getExtraProperty(soul)[statusType] + level);
            else {
                if (isAP)
                    UmaSoulUtils.getExtraProperty(soul)[statusType] += level * 100;
                else
                    UmaSoulUtils.getExtraProperty(soul)[statusType] += level;
            }
        }
    }

    @Override
    public Component getDescription(UmaFactorStack stack) {
        return this.getFullDescription(stack.getLevel());
    }

}
