package net.tracen.umapyoi.block.entity;

import java.util.Collection;
import java.util.Random;
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
import net.tracen.umapyoi.UmapyoiConfig;
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.data.tag.UmapyoiItemTags;
import net.tracen.umapyoi.inventory.ThreeGoddessItemHandler;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.registry.SupportCardRegistry;
import net.tracen.umapyoi.registry.training.card.SupportCard;
import net.tracen.umapyoi.utils.ClientUtils;
import net.tracen.umapyoi.utils.GachaRanking;
import net.tracen.umapyoi.utils.GachaUtils;

public class SilverSupportAlbumPedestalBlockEntity extends SyncedBlockEntity implements Gachable{

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
    private int animationTime;
    public int getAnimationTime() {
        return animationTime;
    }

    public SilverSupportAlbumPedestalBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.SILVER_SUPPORT_ALBUM_PEDESTAL.get(), pos, state);
        this.inventory = createHandler();
        this.inputHandler = LazyOptional.of(() -> new ThreeGoddessItemHandler(inventory, Direction.UP));
        this.outputHandler = LazyOptional.of(() -> new ThreeGoddessItemHandler(inventory, Direction.DOWN));
        this.tileData = createIntArray();
    }

    public static void workingTick(Level level, BlockPos pos, BlockState state,
            SilverSupportAlbumPedestalBlockEntity blockEntity) {
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

    public static void animationTick(Level level, BlockPos pos, BlockState state,
            SilverSupportAlbumPedestalBlockEntity blockEntity) {
        if (blockEntity.canWork())
            ClientUtils.addSummonParticle(level, pos);
        if(!blockEntity.getStoredItem().isEmpty()) {
            blockEntity.animationTime++;
            blockEntity.animationTime %= 360;
        } else {
            blockEntity.animationTime = 0;  
        }
        SilverSupportAlbumPedestalBlockEntity.bookAnimationTick(level, pos, state, blockEntity);
    }

    private static void bookAnimationTick(Level pLevel, BlockPos pPos, BlockState pState,
            SilverSupportAlbumPedestalBlockEntity pBlockEntity) {
        pBlockEntity.oOpen = pBlockEntity.open;
        pBlockEntity.oRot = pBlockEntity.rot;
        Player player = pLevel.getNearestPlayer((double) pPos.getX() + 0.5D, (double) pPos.getY() + 0.5D,
                (double) pPos.getZ() + 0.5D, 3.0D, false);
        if (player != null) {
            double d0 = player.getX() - ((double) pPos.getX() + 0.5D);
            double d1 = player.getZ() - ((double) pPos.getZ() + 0.5D);
            pBlockEntity.tRot = (float) Mth.atan2(d1, d0);
        } else {
            pBlockEntity.tRot += 0.02F;
        }

        if (!pBlockEntity.isEmpty()) {
            pBlockEntity.open += 0.1F;
            if (pBlockEntity.open < 0.5F || RANDOM.nextInt(40) == 0) {
                float f1 = pBlockEntity.flipT;

                do {
                    pBlockEntity.flipT += (float) (RANDOM.nextInt(4) - RANDOM.nextInt(4));
                } while (f1 == pBlockEntity.flipT);
            }
        } else {
            pBlockEntity.open -= 0.1F;
        }

        while (pBlockEntity.rot >= (float) Math.PI) {
            pBlockEntity.rot -= ((float) Math.PI * 2F);
        }

        while (pBlockEntity.rot < -(float) Math.PI) {
            pBlockEntity.rot += ((float) Math.PI * 2F);
        }

        while (pBlockEntity.tRot >= (float) Math.PI) {
            pBlockEntity.tRot -= ((float) Math.PI * 2F);
        }

        while (pBlockEntity.tRot < -(float) Math.PI) {
            pBlockEntity.tRot += ((float) Math.PI * 2F);
        }

        float f2;
        for (f2 = pBlockEntity.tRot - pBlockEntity.rot; f2 >= (float) Math.PI; f2 -= ((float) Math.PI * 2F))
            ;

        while (f2 < -(float) Math.PI) {
            f2 += ((float) Math.PI * 2F);
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
        
        @NotNull
        Collection<ResourceLocation> keys = registry.keySet().stream()
                .filter(this.getFilter(getLevel(), getStoredItem()))
                .collect(Collectors.toCollection(Lists::newArrayList));

        ResourceLocation key = keys.stream().skip(keys.isEmpty() ? 0 : rand.nextInt(keys.size())).findFirst()
                .orElse(SupportCardRegistry.BLANK_CARD.getId());
        
        ItemStack result = ItemRegistry.SUPPORT_CARD.get().getDefaultInstance();
        result.getOrCreateTag().putString("support_card", key.toString());
        result.getOrCreateTag().putString("ranking", registry.get(key).getGachaRanking().name().toLowerCase());
        result.getOrCreateTag().putInt("maxDamage", registry.get(key).getMaxDamage());
        return result;
    }

    private boolean canWork() {
        return !getStoredItem().isEmpty() && getStoredItem().is(UmapyoiItemTags.CARD_TICKET) && !getStoredItem().is(UmapyoiItemTags.SSR_CARD_TICKET);
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
                    return SilverSupportAlbumPedestalBlockEntity.this.recipeTime;
                default:
                    return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                case 0:
                    SilverSupportAlbumPedestalBlockEntity.this.recipeTime = value;
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
            if (!input.getOrCreateTag().getString("name").isBlank()) {
                return resloc.equals(ResourceLocation.tryParse(input.getOrCreateTag().getString("name")));
            }
            if (input.is(UmapyoiItemTags.SR_CARD_TICKET))
                return UmapyoiAPI.getSupportCardRegistry(level).get(resloc).getGachaRanking() == GachaRanking.SR;
            if (input.is(UmapyoiItemTags.COMMON_GACHA_ITEM))
                return UmapyoiAPI.getSupportCardRegistry(level).get(resloc).getGachaRanking() == GachaRanking.R;
            boolean cfgFlag = GachaUtils.checkGachaConfig();
            int gacha_roll;
            int ssrHit = cfgFlag ? UmapyoiConfig.GACHA_PROBABILITY_SSR.get()
                    : UmapyoiConfig.DEFAULT_GACHA_PROBABILITY_SSR;
            gacha_roll = level.getRandom().nextInt(
                    cfgFlag ? UmapyoiConfig.GACHA_PROBABILITY_SUM.get() : UmapyoiConfig.DEFAULT_GACHA_PROBABILITY_SUM);
            int srHit = ssrHit + (cfgFlag ? UmapyoiConfig.GACHA_PROBABILITY_SR.get() : UmapyoiConfig.DEFAULT_GACHA_PROBABILITY_SR);
            return UmapyoiAPI.getSupportCardRegistry(level).get(resloc)
                    .getGachaRanking() == (gacha_roll < srHit ? GachaRanking.SR : GachaRanking.R);
        };
    }

}
