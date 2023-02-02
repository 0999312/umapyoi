package net.tracen.umapyoi.block.entity;

import java.util.List;
import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import cn.mcmod_mmf.mmlib.block.entity.SyncedBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.inventory.ThreeGoddessItemHandler;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.registry.SupportCardRegistry;
import net.tracen.umapyoi.registry.training.card.SupportCard;

public class SupportAlbumPedestalBlockEntity extends SyncedBlockEntity{

    public int time;
    public float flip;
    public float oFlip;
    public float flipT;
    public float flipA;
    public float open;
    public float oOpen;
    public float rot;
    public float oRot;
    public float tRot;
    private static final Random RANDOM = new Random();
    
    public static final int MAX_PROCESS_TIME = 200;
    private final ItemStackHandler inventory;
    private final LazyOptional<IItemHandler> inputHandler;
    private final LazyOptional<IItemHandler> outputHandler;

    protected final ContainerData tileData;

    private int recipeTime;

    public int getProcessTime() {
        return recipeTime;
    }

    public SupportAlbumPedestalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.SUPPORT_ALBUM_PEDESTAL.get(), pos, state);
        this.inventory = createHandler();
        this.inputHandler = LazyOptional.of(() -> new ThreeGoddessItemHandler(inventory, Direction.UP));
        this.outputHandler = LazyOptional.of(() -> new ThreeGoddessItemHandler(inventory, Direction.DOWN));
        this.tileData = createIntArray();
    }

    public static void workingTick(Level level, BlockPos pos, BlockState state, SupportAlbumPedestalBlockEntity blockEntity) {
        if(level.isClientSide()) 
            return ;
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

    public static void animationTick(Level level, BlockPos pos, BlockState state, SupportAlbumPedestalBlockEntity blockEntity) {
        if (blockEntity.canWork())
            SupportAlbumPedestalBlockEntity.addWorkingParticle(level, pos);
        SupportAlbumPedestalBlockEntity.bookAnimationTick(level, pos, state, blockEntity);
    }
    
    public static void bookAnimationTick(Level pLevel, BlockPos pPos, BlockState pState, SupportAlbumPedestalBlockEntity pBlockEntity) {
        pBlockEntity.oOpen = pBlockEntity.open;
        pBlockEntity.oRot = pBlockEntity.rot;
        Player player = pLevel.getNearestPlayer((double)pPos.getX() + 0.5D, (double)pPos.getY() + 0.5D, (double)pPos.getZ() + 0.5D, 3.0D, false);
        if (player != null) {
           double d0 = player.getX() - ((double)pPos.getX() + 0.5D);
           double d1 = player.getZ() - ((double)pPos.getZ() + 0.5D);
           pBlockEntity.tRot = (float)Mth.atan2(d1, d0);
        } else {
           pBlockEntity.tRot += 0.02F;
        }
        
        if(!pBlockEntity.isEmpty()) {
            pBlockEntity.open += 0.1F;
            if (pBlockEntity.open < 0.5F || RANDOM.nextInt(40) == 0) {
               float f1 = pBlockEntity.flipT;

               do {
                  pBlockEntity.flipT += (float)(RANDOM.nextInt(4) - RANDOM.nextInt(4));
               } while(f1 == pBlockEntity.flipT);
            }
        }else {
            pBlockEntity.open -= 0.1F;
        }

        while(pBlockEntity.rot >= (float)Math.PI) {
           pBlockEntity.rot -= ((float)Math.PI * 2F);
        }

        while(pBlockEntity.rot < -(float)Math.PI) {
           pBlockEntity.rot += ((float)Math.PI * 2F);
        }

        while(pBlockEntity.tRot >= (float)Math.PI) {
           pBlockEntity.tRot -= ((float)Math.PI * 2F);
        }

        while(pBlockEntity.tRot < -(float)Math.PI) {
           pBlockEntity.tRot += ((float)Math.PI * 2F);
        }

        float f2;
        for(f2 = pBlockEntity.tRot - pBlockEntity.rot; f2 >= (float)Math.PI; f2 -= ((float)Math.PI * 2F)) ;

        while(f2 < -(float)Math.PI) {
           f2 += ((float)Math.PI * 2F);
        }

        pBlockEntity.rot += f2 * 0.4F;
        pBlockEntity.open = Mth.clamp(pBlockEntity.open, 0.0F, 1.0F);
        ++pBlockEntity.time;
        pBlockEntity.oFlip = pBlockEntity.flip;
        float f = (pBlockEntity.flipT - pBlockEntity.flip) * 0.4F;
        float f3 = 0.2F;
        f = Mth.clamp(f, -0.2F, f3);
        pBlockEntity.flipA += (f - pBlockEntity.flipA) * 0.9F;
        pBlockEntity.flip += pBlockEntity.flipA;
     }

    private static void addWorkingParticle(Level pLevel, BlockPos pPos) {
        Random pRand = pLevel.getRandom();
        List<BlockPos> posOffsets = BlockPos.betweenClosedStream(-2, 0, -2, 2, 1, 2).filter(pos -> {
            return Math.abs(pos.getX()) == 2 || Math.abs(pos.getZ()) == 2;
        }).map(BlockPos::immutable).toList();
        for (BlockPos spawnPos : posOffsets) {
            if (pRand.nextInt(32) == 0) {
                pLevel.addParticle(ParticleTypes.ENCHANT, (double) pPos.getX() + 0.5D, (double) pPos.getY() + 1.5D,
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
        this.inventory.setStackInSlot(0, resultStack.copy());
        this.getLevel().playSound(null, this.getBlockPos(), SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 1F, 1F);
        return true;
    }

    private ItemStack getResultItem() {
        if (this.level == null)
            return ItemStack.EMPTY;
        
        Random rand = this.getLevel().getRandom();
        Registry<SupportCard> registry = UmapyoiAPI.getSupportCardRegistry(this.getLevel());

        ResourceLocation key = registry.keySet().stream()
                .skip(registry.keySet().isEmpty() ? 0 : rand.nextInt(registry.keySet().size()))
                .findFirst()
                .orElse(SupportCardRegistry.TEST_1.getId());
        

        ItemStack result = ItemRegistry.SUPPORT_CARD.get().getDefaultInstance();
        result.getOrCreateTag().putString("support_card", key.toString());

        return result;
    }

    private boolean canWork() {
        return !getStoredItem().isEmpty() && getStoredItem().is(ItemRegistry.JEWEL.get());
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
        if (cap.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
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
                    return SupportAlbumPedestalBlockEntity.this.recipeTime;
                default:
                    return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                case 0:
                    SupportAlbumPedestalBlockEntity.this.recipeTime = value;
                    break;
                }
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
    }

}
