package net.tracen.umapyoi.item.food;

import java.util.function.Consumer;

import cn.mcmod_mmf.mmlib.item.ItemFoodBase;
import cn.mcmod_mmf.mmlib.item.info.FoodInfo;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.capability.CapabilityRegistry;
import net.tracen.umapyoi.registry.umadata.UmaStatus;

public class UmaFoodItem extends ItemFoodBase {
    private final Consumer<UmaStatus> consumer;

    public UmaFoodItem(Consumer<UmaStatus> consumer, FoodInfo info) {
        super(Umapyoi.defaultItemProperties(), info);
        this.consumer = consumer;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack itemstack = this.isEdible() ? this.eatAsUma(stack, level, entity) : stack;
        if (stack.getCount() > 0) {
            if (entity instanceof Player) {
                Player entityplayer = (Player) entity;
                if (entityplayer.getAbilities().instabuild)
                    return itemstack;
                if (!entityplayer.addItem(this.getContainerItem(stack)))
                    entityplayer.drop(this.getContainerItem(stack), true);
            }
            return itemstack;
        }
        return entity instanceof Player && ((Player) entity).getAbilities().instabuild ? itemstack
                : this.getContainerItem(stack);
    }

    private ItemStack eatAsUma(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player player) {
            if (UmapyoiAPI.getUmaSoul(player).isEmpty()) {
                player.getFoodData().eat(this, stack, entity);
                if (!player.getAbilities().instabuild)
                    stack.shrink(1);
                return stack;
            } else {
                UmapyoiAPI.getUmaSoul(player).getCapability(CapabilityRegistry.UMACAP)
                        .ifPresent(cap -> this.consumer.accept(cap.getUmaStatus()));
            }
        }
        return entity.eat(level, stack);
    }

}
