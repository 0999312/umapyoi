package net.tracen.umapyoi.registry.training;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.registry.umadata.UmaDataBasicStatus;
import net.tracen.umapyoi.utils.UmaSoulUtils;
import net.tracen.umapyoi.utils.UmaStatusUtils;

public class BorealisSupport extends TrainingSupport {

    public BorealisSupport() {
        super();
    }

    @Override
    public Component getDescription(SupportStack stack) {
        return this.getDescription();
    }

    @Override
    public boolean applySupport(ItemStack soul, RandomSource rand, SupportStack stack) {
        var chance = rand.nextFloat();
        if(chance < UmapyoiConfig.ACUPUNCTUIST_SUPPORT_CHANCE.get())
            this.applySuccessEvent(soul, rand);
        else
            UmaStatusUtils.downMotivation(soul);

        return true;
    }

    public void applySuccessEvent(ItemStack soul, RandomSource rand) {
        switch (AcupuncturistEventTypes.getRandomType(rand)) {
            case STATUS ->{
                soul.update(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS, UmaDataBasicStatus.DEFAULT_MAX_STATUS, maxProperty -> new UmaDataBasicStatus(
                        Math.min(39, maxProperty.speed() + 1),
                        Math.min(39, maxProperty.stamina() + 1),
                        Math.min(39, maxProperty.strength() + 1),
                        Math.min(39, maxProperty.guts() + 1),
                        Math.min(39, maxProperty.wisdom() + 1)
                ));
                UmaDataBasicStatus maxProperty = soul.getOrDefault(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS, UmaDataBasicStatus.DEFAULT_MAX_STATUS);
                soul.update(DataComponentsTypeRegistry.UMADATA_BASIC_STATUS, UmaDataBasicStatus.DEFAULT_STATUS, basicProp -> new UmaDataBasicStatus(
                        Math.min(maxProperty.speed(), basicProp.speed() + 1),
                        Math.min(maxProperty.stamina(), basicProp.stamina() + 1),
                        Math.min(maxProperty.strength(), basicProp.strength() + 1),
                        Math.min(maxProperty.guts(), basicProp.guts() + 1),
                        Math.min(maxProperty.wisdom(), basicProp.wisdom() + 1)
                ));
            }

            case PHYSIQUE ->{
                int phy = Math.min(UmaSoulUtils.getPhysique(soul) + 1, 5);
                UmaSoulUtils.setPhysique(soul, phy);
            }

            case MOTIVATION ->{
                UmaStatusUtils.addMotivation(soul);
            }

            default ->
                    throw new IllegalArgumentException("Unexpected value: " + AcupuncturistEventTypes.getRandomType(rand));
        }
    }

    public static enum AcupuncturistEventTypes {
        STATUS, PHYSIQUE, MOTIVATION;

        public static AcupuncturistEventTypes getRandomType(RandomSource rand) {
            return AcupuncturistEventTypes.values()[rand.nextInt(AcupuncturistEventTypes.values().length)];
        }
    }
}
