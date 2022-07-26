package net.trc.umapyoi.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.trc.umapyoi.api.UmaStatus;
import net.trc.umapyoi.capability.CapabilityRegistry;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class StatusUpItem extends Item {
    private final UmaStatus status;
    private final int value;

    public StatusUpItem(Properties prop, UmaStatus status, int value) {
        super(prop);
        this.status = status;
        this.value = value;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(level.isClientSide())
            return super.use(level, player, hand);
        if (CuriosApi.getCuriosHelper().getCuriosHandler(player).isPresent()) {
            var itemHandler = CuriosApi.getCuriosHelper().getCuriosHandler(player).orElse(null);
            if (itemHandler.getStacksHandler("uma_soul").isPresent()) {
                var stacksHandler = itemHandler.getStacksHandler("uma_soul").orElse(null);
                IDynamicStackHandler stackHandler = stacksHandler.getStacks();

                if (stackHandler.getSlots() > 0) {
                    stackHandler.getStackInSlot(0).getCapability(CapabilityRegistry.UMACAP).ifPresent(cap -> {
                        switch (status) {
                        case SPEED: {
                            cap.setSpeed(cap.getSpeed() + value);
                            break;
                        }
                        case STAMINA: {
                            cap.setStamina(cap.getStamina() + value);
                            break;
                        }
                        case STRENGTH: {
                            cap.setStrength(cap.getStrength() + value);
                            break;
                        }
                        case MENTALITY: {
                            cap.setMentality(cap.getMentality() + value);
                            break;
                        }
                        case WISDOM: {
                            cap.setWisdom(cap.getWisdom() + value);
                            break;
                        }
                        case MAX_SPEED: {
                            cap.setMaxSpeed(cap.getMaxSpeed() + value);
                            break;
                        }
                        case MAX_STAMINA: {
                            cap.setMaxStamina(cap.getMaxStamina() + value);
                            break;
                        }
                        case MAX_STRENGTH: {
                            cap.setMaxStrength(cap.getMaxStrength() + value);
                            break;
                        }
                        case MAX_MENTALITY: {
                            cap.setMaxMentality(cap.getMaxMentality() + value);
                            break;
                        }
                        case MAX_WISDOM: {
                            cap.setMaxWisdom(cap.getMaxWisdom() + value);
                            break;
                        }
                        default:
                            throw new IllegalArgumentException("Unexpected value: " + status);
                        }
                    });
                    player.getCooldowns().addCooldown(player.getItemInHand(hand).getItem(), 30);
                    player.getItemInHand(hand).shrink(1);
                }
            }
        }
        return super.use(level, player, hand);
    }
}
