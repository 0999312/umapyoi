package net.tracen.umapyoi.registry.training;

import net.minecraft.world.item.ItemStack;
import net.tracen.umapyoi.item.data.DataComponentsTypeRegistry;
import net.tracen.umapyoi.registry.umadata.UmaDataBasicStatus;
import net.tracen.umapyoi.utils.UmaSoulUtils;
import net.tracen.umapyoi.utils.UmaStatusUtils.StatusType;

public class StatusSupport extends TrainingSupport {
	private final StatusType statusType;

	public StatusSupport(StatusType status) {
		super();
		this.statusType = status;
	}

	@Override
	public boolean applySupport(ItemStack soul, SupportStack stack) {
		switch (statusType) {
		case SPEED -> {
			soul.update(DataComponentsTypeRegistry.UMADATA_BASIC_STATUS, UmaSoulUtils.getProperty(soul), data -> {
				return new UmaDataBasicStatus(
						Math.min(soul.get(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS).speed(),
								data.speed() + stack.getLevel()),
						data.stamina(), data.strength(), data.guts(), data.wisdom());
			});
			return true;
		}
		case STAMINA -> {
			soul.update(DataComponentsTypeRegistry.UMADATA_BASIC_STATUS, UmaSoulUtils.getProperty(soul), data -> {
				return new UmaDataBasicStatus(data.speed(),
						Math.min(soul.get(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS).stamina(),
								data.stamina() + stack.getLevel()),
						data.strength(), data.guts(), data.wisdom());
			});
			return true;
		}
		case STRENGTH -> {
			soul.update(DataComponentsTypeRegistry.UMADATA_BASIC_STATUS, UmaSoulUtils.getProperty(soul), data -> {
				return new UmaDataBasicStatus(data.speed(), data.stamina(),
						Math.min(soul.get(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS).strength(),
								data.strength() + stack.getLevel()),
						data.guts(), data.wisdom());
			});
			return true;
		}
		case GUTS -> {

			soul.update(DataComponentsTypeRegistry.UMADATA_BASIC_STATUS, UmaSoulUtils.getProperty(soul), data -> {
				return new UmaDataBasicStatus(data.speed(), data.stamina(), data.strength(),
						Math.min(soul.get(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS).guts(),
								data.guts() + stack.getLevel()),
						data.wisdom());
			});
			return true;
		}
		case WISDOM -> {
			soul.update(DataComponentsTypeRegistry.UMADATA_BASIC_STATUS, UmaSoulUtils.getProperty(soul),
					data -> {
						return new UmaDataBasicStatus(data.speed(), data.stamina(), data.strength(), data.guts(),
								Math.min(soul.get(DataComponentsTypeRegistry.UMADATA_MAX_BASIC_STATUS).wisdom(),
										data.wisdom() + stack.getLevel()));
					});
			return true;
		}
		}

		return false;
	}

}
