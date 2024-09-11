package net.tracen.umapyoi.registry.factors;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.registry.umadata.UmaDataBasicStatus;
import net.tracen.umapyoi.utils.UmaSoulUtils;
import net.tracen.umapyoi.utils.UmaStatusUtils.StatusType;

public class StatusFactor extends UmaFactor {

	private final StatusType statusType;

	public StatusFactor(StatusType status) {
		super(FactorType.STATUS);
		this.statusType = status;
	}

	public StatusType getStatusType() {
		return statusType;
	}

	@Override
	public void applyFactor(ItemStack soul, UmaFactorStack stack) {
		switch (statusType) {
		case SPEED -> {

			soul.update(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS, UmaSoulUtils.getMaxProperty(soul),
					data -> {
						return new UmaDataBasicStatus(data.speed() + stack.getLevel(), data.stamina(), data.strength(),
								data.guts(), data.wisdom());
					});
			soul.update(DataComponentsTypeRegistry.UMADATA_BASIC_STATUS, UmaSoulUtils.getProperty(soul), data -> {
				return new UmaDataBasicStatus(
						Math.min(soul.get(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS).speed(),
								data.speed() + stack.getLevel()),
						data.stamina(), data.strength(), data.guts(), data.wisdom());
			});
		}
		case STAMINA -> {
			soul.update(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS, UmaSoulUtils.getMaxProperty(soul),
					data -> {
						return new UmaDataBasicStatus(data.speed(), data.stamina() + stack.getLevel(), data.strength(),
								data.guts(), data.wisdom());
					});
			soul.update(DataComponentsTypeRegistry.UMADATA_BASIC_STATUS, UmaSoulUtils.getProperty(soul), data -> {
				return new UmaDataBasicStatus(data.speed(),
						Math.min(soul.get(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS).stamina(),
								data.stamina() + stack.getLevel()),
						data.strength(), data.guts(), data.wisdom());
			});
		}
		case STRENGTH -> {
			soul.update(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS, UmaSoulUtils.getMaxProperty(soul),
					data -> {
						return new UmaDataBasicStatus(data.speed(), data.stamina(), data.strength() + stack.getLevel(),
								data.guts(), data.wisdom());
					});
			soul.update(DataComponentsTypeRegistry.UMADATA_BASIC_STATUS, UmaSoulUtils.getProperty(soul), data -> {
				return new UmaDataBasicStatus(data.speed(), data.stamina(),
						Math.min(soul.get(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS).strength(),
								data.strength() + stack.getLevel()),
						data.guts(), data.wisdom());
			});
		}
		case GUTS -> {
			soul.update(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS, UmaSoulUtils.getMaxProperty(soul),
					data -> {
						return new UmaDataBasicStatus(data.speed(), data.stamina(), data.strength(),
								data.guts() + stack.getLevel(), data.wisdom());
					});

			soul.update(DataComponentsTypeRegistry.UMADATA_BASIC_STATUS, UmaSoulUtils.getProperty(soul), data -> {
				return new UmaDataBasicStatus(data.speed(), data.stamina(), data.strength(),
						Math.min(soul.get(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS).guts(),
								data.guts() + stack.getLevel()),
						data.wisdom());
			});
		}
		case WISDOM -> {

			soul.update(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS, UmaSoulUtils.getMaxProperty(soul),
					data -> {
						return new UmaDataBasicStatus(data.speed(), data.stamina(), data.strength(), data.guts(),
								data.wisdom() + stack.getLevel());
					});
			soul.update(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS, UmaSoulUtils.getMaxProperty(soul),
					data -> {
						return new UmaDataBasicStatus(data.speed(), data.stamina(), data.strength(), data.guts(),
								Math.min(soul.get(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS).wisdom(),
										data.wisdom() + stack.getLevel()));
					});
		}
		}

	}

	@Override
	public Component getDescription(UmaFactorStack stack) {
		return this.getFullDescription(stack.getLevel());
	}

}
