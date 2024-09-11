package net.tracen.umapyoi.registry.factors;

import java.util.Random;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.registry.umadata.UmaDataBasicStatus;
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
        final int max = maxStatusLevel;
        for (int roll = 0; roll < stack.getLevel(); roll++) {
            if (rand.nextFloat() > chance)
                statusLevel--;
        }
        final int status = statusLevel;
		switch (statusType) {
		case SPEED -> {

			soul.update(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS, UmaSoulUtils.getMaxProperty(soul),
					data -> {
						return new UmaDataBasicStatus(data.speed() + max, data.stamina(), data.strength(),
								data.guts(), data.wisdom());
					});
			soul.update(DataComponentsTypeRegistry.UMADATA_BASIC_STATUS, UmaSoulUtils.getProperty(soul), data -> {
				return new UmaDataBasicStatus(
						Math.min(soul.get(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS).speed(),
								data.speed() + status),
						data.stamina(), data.strength(), data.guts(), data.wisdom());
			});
		}
		case STAMINA -> {
			soul.update(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS, UmaSoulUtils.getMaxProperty(soul),
					data -> {
						return new UmaDataBasicStatus(data.speed(), data.stamina() + max, data.strength(),
								data.guts(), data.wisdom());
					});
			soul.update(DataComponentsTypeRegistry.UMADATA_BASIC_STATUS, UmaSoulUtils.getProperty(soul), data -> {
				return new UmaDataBasicStatus(data.speed(),
						Math.min(soul.get(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS).stamina(),
								data.stamina() + status),
						data.strength(), data.guts(), data.wisdom());
			});
		}
		case STRENGTH -> {
			soul.update(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS, UmaSoulUtils.getMaxProperty(soul),
					data -> {
						return new UmaDataBasicStatus(data.speed(), data.stamina(), data.strength() + max,
								data.guts(), data.wisdom());
					});
			soul.update(DataComponentsTypeRegistry.UMADATA_BASIC_STATUS, UmaSoulUtils.getProperty(soul), data -> {
				return new UmaDataBasicStatus(data.speed(), data.stamina(),
						Math.min(soul.get(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS).strength(),
								data.strength() + status),
						data.guts(), data.wisdom());
			});
		}
		case GUTS -> {
			soul.update(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS, UmaSoulUtils.getMaxProperty(soul),
					data -> {
						return new UmaDataBasicStatus(data.speed(), data.stamina(), data.strength(),
								data.guts() + max, data.wisdom());
					});

			soul.update(DataComponentsTypeRegistry.UMADATA_BASIC_STATUS, UmaSoulUtils.getProperty(soul), data -> {
				return new UmaDataBasicStatus(data.speed(), data.stamina(), data.strength(),
						Math.min(soul.get(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS).guts(),
								data.guts() + status),
						data.wisdom());
			});
		}
		case WISDOM -> {

			soul.update(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS, UmaSoulUtils.getMaxProperty(soul),
					data -> {
						return new UmaDataBasicStatus(data.speed(), data.stamina(), data.strength(), data.guts(),
								data.wisdom() + max);
					});
			soul.update(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS, UmaSoulUtils.getMaxProperty(soul),
					data -> {
						return new UmaDataBasicStatus(data.speed(), data.stamina(), data.strength(), data.guts(),
								Math.min(soul.get(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS).wisdom(),
										data.wisdom() + status));
					});
		}
		}

    }

    @Override
    public Component getDescription(UmaFactorStack stack) {
        return this.getFullDescription(stack.getLevel());
    }

}
