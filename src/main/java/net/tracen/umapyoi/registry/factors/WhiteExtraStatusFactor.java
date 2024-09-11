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
        var chance = stack.getLevel() * 0.2;
        Random rand = new Random();
        for (int roll = 0; roll < stack.getLevel(); roll++) {
            if (rand.nextFloat() > chance)
                level--;
        }

        if (level != 0) {
            switch (this.statusType) {
    		case 0 ->
    			UmaSoulUtils.setPhysique(soul, Math.min(5, UmaSoulUtils.getPhysique(soul) + level));
    		case 1 -> UmaSoulUtils.setLearningTimes(soul, UmaSoulUtils.getLearningTimes(soul) + level);
    		case 2 -> UmaSoulUtils.setSkillSlots(soul, UmaSoulUtils.getSkillSlots(soul)+ level);
    		case 3 -> UmaSoulUtils.setExtraActionPoint(soul, UmaSoulUtils.getExtraActionPoint(soul) + level * 100);
    		default ->
    			throw new IllegalArgumentException("Unexpected value: " + this.statusType);
    		}
        }
    }

    @Override
    public Component getDescription(UmaFactorStack stack) {
        return this.getFullDescription(stack.getLevel());
    }

}
