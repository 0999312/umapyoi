package net.tracen.umapyoi.block.entity;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.Lists;

import cn.mcmod_mmf.mmlib.block.entity.SyncedBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.data.tag.UmapyoiItemTags;
import net.tracen.umapyoi.inventory.ThreeGoddessItemHandler;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.utils.ClientUtils;
import net.tracen.umapyoi.utils.GachaRanking;
import net.tracen.umapyoi.utils.GachaUtils;

public class UmaPedestalBlockEntity extends SyncedBlockEntity implements Gachable {

    public static final int MAX_PROCESS_TIME = 200;
    private final ItemStackHandler inventory;
    private final LazyOptional<IItemHandler> inputHandler;
    private final LazyOptional<IItemHandler> outputHandler;

    protected final ContainerData tileData;

    private int recipeTime;

    public int getProcessTime() {
        return recipeTime;
    }

    private int animationTime;
    public int getAnimationTime() {
        return animationTime;
    }

    public UmaPedestalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.UMA_PEDESTAL.get(), pos, state);
        this.inventory = createHandler();
        this.inputHandler = LazyOptional.of(() -> new ThreeGoddessItemHandler(inventory, Direction.UP));
        this.outputHandler = LazyOptional.of(() -> new ThreeGoddessItemHandler(inventory, Direction.DOWN));
        this.tileData = createIntArray();
    }

    public static void workingTick(Level level, BlockPos pos, BlockState state, UmaPedestalBlockEntity blockEntity) {
        if (level.isClientSide())
            return;
        boolean didInventoryChange = false;

        if (blockEntity.canWork()) {
            didInventoryChange = blockEntity.processRecipe();
        } else {
            blockEntity.recipeTime = 0;
        }

        if (didInventoryChange) {
            blockEntity.inventoryChanged();
        }
    }

    public static void animationTick(Level level, BlockPos pos, BlockState state, UmaPedestalBlockEntity blockEntity) {
        blockEntity.animationTime++;
        if (blockEntity.canWork())
            ClientUtils.addSummonParticle(level, pos);
        blockEntity.animationTime %= 360;
    }

    private boolean processRecipe() {
        if (level == null) {
            return false;
        }

        ++recipeTime;
        if (recipeTime < MAX_PROCESS_TIME) {
            return false;
        }

        recipeTime = 0;

        ItemStack resultStack = getResultItem();
        this.inventory.setStackInSlot(0, resultStack.copy());
        this.getLevel().playSound(null, this.getBlockPos(), SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 1F, 1F);
        return true;
    }

    private ItemStack getResultItem() {
        if (this.level == null)
            return ItemStack.EMPTY;

        RandomSource rand = this.getLevel().getRandom();
        Registry<UmaData> registry = UmapyoiAPI.getUmaDataRegistry(this.getLevel());

        @NotNull
        Collection<ResourceLocation> keys = registry.keySet().stream()
                .filter(this.getFilter(getLevel(), getStoredItem()))
                .collect(Collectors.toCollection(Lists::newArrayList));

        ResourceLocation holder = keys.stream().skip(keys.isEmpty() ? 0 : rand.nextInt(keys.size())).findFirst()
                .orElse(UmaData.DEFAULT_UMA_ID);

        ItemStack result = ItemRegistry.BLANK_UMA_SOUL.get().getDefaultInstance();
        result.getOrCreateTag().putString("name", holder.toString());

        return result;
    }

    private boolean canWork() {
        return !getStoredItem().isEmpty() && getStoredItem().is(UmapyoiItemTags.UMA_TICKET);
    }

    public ItemStack getStoredItem() {
        return inventory.getStackInSlot(0);
    }

    public boolean isEmpty() {
        return inventory.getStackInSlot(0).isEmpty();
    }

    public boolean addItem(ItemStack itemStack) {
        if (isEmpty() && !itemStack.isEmpty()) {
            inventory.setStackInSlot(0, itemStack.split(1));
            inventoryChanged();
            return true;
        }
        return false;
    }

    public ItemStack removeItem() {
        if (!isEmpty()) {
            ItemStack item = getStoredItem().split(1);
            inventoryChanged();
            return item;
        }
        return ItemStack.EMPTY;
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap.equals(ForgeCapabilities.ITEM_HANDLER)) {
            if (side == null || side.equals(Direction.UP)) {
                return inputHandler.cast();
            } else {
                return outputHandler.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public NonNullList<ItemStack> getDroppableInventory() {
        NonNullList<ItemStack> drops = NonNullList.create();
        drops.add(inventory.getStackInSlot(0));
        return drops;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        inputHandler.invalidate();
        outputHandler.invalidate();
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        inventory.deserializeNBT(compound.getCompound("Inventory"));
        recipeTime = compound.getInt("RecipeTime");
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putInt("RecipeTime", recipeTime);
        compound.put("Inventory", inventory.serializeNBT());
    }

    private CompoundTag writeItems(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("Inventory", inventory.serializeNBT());
        return compound;
    }

    @Override
    public CompoundTag getUpdateTag() {
        return writeItems(new CompoundTag());
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                inventoryChanged();
            }
        };
    }

    private ContainerData createIntArray() {
        return new ContainerData() {
            @Override
            public int get(int index) {
                switch (index) {
                case 0:
                    return UmaPedestalBlockEntity.this.recipeTime;
                default:
                    return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                case 0:
                    UmaPedestalBlockEntity.this.recipeTime = value;
                    break;
                }
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
    }

    @Override
    public Predicate<? super ResourceLocation> getFilter(Level level, ItemStack input) {
        return resloc -> {
            if (input.is(UmapyoiItemTags.SSR_UMA_TICKET))
                return UmapyoiAPI.getUmaDataRegistry(level).get(resloc).getGachaRanking() == GachaRanking.SSR;
            if (input.is(UmapyoiItemTags.COMMON_GACHA_ITEM))
                return UmapyoiAPI.getUmaDataRegistry(level).get(resloc).getGachaRanking() == GachaRanking.R;
            boolean cfgFlag = GachaUtils.checkGachaConfig();
            int gacha_roll;
            int ssrHit = cfgFlag ? UmapyoiConfig.GACHA_PROBABILITY_SSR.get()
                    : UmapyoiConfig.DEFAULT_GACHA_PROBABILITY_SSR;
            if (input.is(UmapyoiItemTags.SR_UMA_TICKET)) {
                gacha_roll = level.getRandom()
                        .nextInt(cfgFlag
                                ? UmapyoiConfig.GACHA_PROBABILITY_SUM.get() - UmapyoiConfig.GACHA_PROBABILITY_R.get()
                                : 30);

                return UmapyoiAPI.getUmaDataRegistry(level).get(resloc)
                        .getGachaRanking() == (gacha_roll < ssrHit ? GachaRanking.SSR : GachaRanking.SR);
            }
            gacha_roll = level.getRandom().nextInt(
                    cfgFlag ? UmapyoiConfig.GACHA_PROBABILITY_SUM.get() : UmapyoiConfig.DEFAULT_GACHA_PROBABILITY_SUM);
            int srHit = ssrHit + (cfgFlag ? UmapyoiConfig.GACHA_PROBABILITY_SR.get() : UmapyoiConfig.DEFAULT_GACHA_PROBABILITY_SR);
            return UmapyoiAPI.getUmaDataRegistry(level).get(resloc)
                    .getGachaRanking() == (gacha_roll < ssrHit ? GachaRanking.SSR
                            : gacha_roll < srHit ? GachaRanking.SR : GachaRanking.R);
        };
    }

}
