package net.tracen.umapyoi.block.entity;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import cn.mcmod_mmf.mmlib.block.entity.SyncedBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.container.ThreeGoddessContainer;
import net.tracen.umapyoi.inventory.ThreeGoddessItemHandler;
import net.tracen.umapyoi.item.FadedUmaSoulItem;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.utils.UmaFactorUtils;
import net.tracen.umapyoi.utils.UmaSoulUtils;

public class ThreeGoddessBlockEntity extends SyncedBlockEntity implements MenuProvider {

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
    
    public ThreeGoddessBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.THREE_GODDESS.get(), pos, state);
        this.inventory = createHandler();
        this.inputHandler = LazyOptional.of(() -> new ThreeGoddessItemHandler(inventory, Direction.UP));
        this.outputHandler = LazyOptional.of(() -> new ThreeGoddessItemHandler(inventory, Direction.DOWN));
        this.tileData = createIntArray();
    }

    public static void workingTick(Level level, BlockPos pos, BlockState state, ThreeGoddessBlockEntity blockEntity) {
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

    public static void animationTick(Level level, BlockPos pos, BlockState state, ThreeGoddessBlockEntity blockEntity) {
        blockEntity.animationTime++;
        if (blockEntity.canWork())
            ThreeGoddessBlockEntity.addWorkingParticle(level, pos);
        blockEntity.animationTime %= 360;
    }

    private static void addWorkingParticle(Level pLevel, BlockPos pPos) {
        RandomSource pRand = pLevel.getRandom();
        List<BlockPos> posOffsets = BlockPos.betweenClosedStream(-2, 0, -2, 2, 1, 2).filter(pos -> {
            return Math.abs(pos.getX()) == 2 || Math.abs(pos.getZ()) == 2;
        }).map(BlockPos::immutable).toList();
        for (BlockPos spawnPos : posOffsets) {
            if (pRand.nextInt(32) == 0) {
                pLevel.addParticle(ParticleTypes.ENCHANT, (double) pPos.getX() + 0.5D, (double) pPos.getY() + 2.5D,
                        (double) pPos.getZ() + 0.5D, (double) ((float) spawnPos.getX() + pRand.nextFloat() * 2F) - 0.5D,
                        (double) ((float) spawnPos.getY() + 2D - pRand.nextFloat()),
                        (double) ((float) spawnPos.getZ() + pRand.nextFloat() * 2F) - 0.5D);
            }
        }
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
        
        this.inventory.setStackInSlot(3, resultStack.copy());
        
        this.inventory.getStackInSlot(0).shrink(1);
        
        this.getLevel().playSound(null, this.getBlockPos(), SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 1F, 1F);
        return true;
    }

    private ItemStack getResultItem() {
        if (this.level == null)
            return ItemStack.EMPTY;

        ItemStack left = this.inventory.getStackInSlot(1);
        ItemStack right = this.inventory.getStackInSlot(2);
        Registry<UmaData> registry = UmapyoiAPI.getUmaDataRegistry(this.getLevel());

        ResourceLocation name = ResourceLocation
                .tryParse(this.inventory.getStackInSlot(0).getOrCreateTag().getString("name"));
        name = registry.containsKey(name) ? name : UmaData.DEFAULT_UMA_ID;
        
        UmaData data = registry.getOptional(name).orElse(UmaData.DEFAULT_UMA);

        ItemStack result = UmaSoulUtils.initUmaSoul(ItemRegistry.UMA_SOUL.get().getDefaultInstance(), name, data)
                .copy();

        if (!left.isEmpty() && !right.isEmpty()) {
            UmaFactorUtils.deserializeNBT(left.getOrCreateTag()).forEach(fac -> fac.applyFactor(result));
            UmaFactorUtils.deserializeNBT(right.getOrCreateTag()).forEach(fac -> fac.applyFactor(result));
        }

        return result;
    }

    private boolean canWork() {
        if (!this.hasInput())
            return false;
        ItemStack outputStack = inventory.getStackInSlot(3);
        if (outputStack.isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean hasInput() {
        if (this.inventory.getStackInSlot(0).getItem() instanceof FadedUmaSoulItem) {
            if (inventory.getStackInSlot(1).isEmpty() == inventory.getStackInSlot(2).isEmpty())
                return true;
        }
        return false;
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
        for (int i = 0; i < 4; ++i) {
            drops.add(inventory.getStackInSlot(i));
        }
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
        return new ItemStackHandler(4) {
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
                    return ThreeGoddessBlockEntity.this.recipeTime;
                default:
                    return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                case 0:
                    ThreeGoddessBlockEntity.this.recipeTime = value;
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
    public AbstractContainerMenu createMenu(int id, Inventory player, Player entity) {
        return new ThreeGoddessContainer(id, player, this, this.tileData);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.umapyoi.three_goddess");
    }

}
