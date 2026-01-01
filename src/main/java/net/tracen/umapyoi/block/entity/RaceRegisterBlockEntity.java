package net.tracen.umapyoi.block.entity;

import cn.mcmod_mmf.mmlib.block.entity.SyncedBlockEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.container.RaceContainer;
import net.tracen.umapyoi.inventory.UniversalIOItemHandler;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.registry.races.Race;
import net.tracen.umapyoi.registry.races.RaceRegistry;
import net.tracen.umapyoi.registry.umadata.Growth;
import net.tracen.umapyoi.utils.UmaSoulUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.Objects;

import static net.tracen.umapyoi.item.UmaRacingSlipItem.getRaceID;

public class RaceRegisterBlockEntity extends SyncedBlockEntity implements MenuProvider {
    // No additional synchronized logic because provided by SyncedBlockEntity
    // Update by inventoryChanged (custom logic in SyncedBlockEntity, which is the super of this class)

    private final int MAX_RECIPE_TIME = 260; //13 seconds;

    private final ItemStackHandler inventory;
    private final LazyOptional<IItemHandler> IOHandler;
    private int recipeTime;
    protected final ContainerData tileData;

    public RaceRegisterBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.RACE_REGISTER_BLOCK_ENTITY.get(), pos, state);
        this.inventory = createHandler();
        this.IOHandler = LazyOptional.of(() -> new UniversalIOItemHandler(inventory, 2));
        this.tileData = createIntArray();
    }

    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    protected static boolean umaSoulConstraint(ItemStack stack) {
        return stack.is(ItemRegistry.UMA_SOUL.get()) && UmaSoulUtils.getGrowth(stack) == Growth.RETIRED;
    }

    protected static boolean raceConstraint(ItemStack stack) {
        return stack.is(ItemRegistry.UMA_RACING_SLIP.get());
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(6) {
            @Override
            protected void onContentsChanged(int slot) {
                inventoryChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (slot >= 2) return false;
                if (!super.isItemValid(slot, stack)) return false;
                if (slot == 0) {
                    return umaSoulConstraint(stack);
                } else {
                    return raceConstraint(stack);
                }
            }
        };
    }

    // NBT: {RecipeTime: this.recipeTime, Inventory: [...this.inventory]}

    @Override
    public void load(@Nonnull CompoundTag compound) {
        super.load(compound);
        inventory.deserializeNBT(compound.getCompound("Inventory"));
        recipeTime = compound.getInt("RecipeTime");
    }

    @Override
    public void saveAdditional(@Nonnull CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putInt("RecipeTime", recipeTime);
        compound.put("Inventory", inventory.serializeNBT());
    }

    @Nonnull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        this.load(tag);
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap.equals(ForgeCapabilities.ITEM_HANDLER)) {
            return this.IOHandler.cast(); //safe casting
        }
        return super.getCapability(cap, side);
    }

    private ContainerData createIntArray() {
        return new ContainerData() {
            @Override
            public int get(int index) {
                return index == 0 ? RaceRegisterBlockEntity.this.recipeTime : 0;
            }

            @Override
            public void set(int index, int value) {
                if (index == 0) RaceRegisterBlockEntity.this.recipeTime = value;
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState,
                                  RaceRegisterBlockEntity raceRegisterBlockEntity) {
        if (level.isClientSide())
            return;
        raceRegisterBlockEntity.serverTick();
    }

    public void serverTick() {
        boolean dirty = false;
        if (this.fulfill()) {
            dirty = this.processRecipe();
        } else {
            this.recipeTime = 0;
        }

        if (dirty) {
            this.inventoryChanged();
        }
    }

    public ItemStack insertItemToSlot(int slot, @NotNull ItemStack stack)
    {
        if (stack.isEmpty()) return ItemStack.EMPTY;
        ItemStack existing = this.inventory.getStackInSlot(slot);
        int limit = Math.min(this.inventory.getSlotLimit(slot), existing.getMaxStackSize());

        if (!existing.isEmpty()) {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing)) return stack;
            limit -= existing.getCount();
        }

        if (limit <= 0) return stack;
        boolean reachedLimit = stack.getCount() > limit;

        if (existing.isEmpty()) {
            this.inventory.setStackInSlot(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
        } else {
            existing.grow(reachedLimit ? limit : stack.getCount());
        }

        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
    }

    private boolean processRecipe() {
        if (level == null) return false;

        if (recipeTime == 0) {
            // other sanity check, no further process during non-empty output area
            int cnt = 0;
            for (int i = 2; i < 6; i++) {
                if (!this.inventory.getStackInSlot(i).isEmpty()) cnt++;
            }
            if (cnt == 4) return false;
        }

        recipeTime++;

        if (recipeTime < MAX_RECIPE_TIME) return false;

        recipeTime = 0; // done logic
        ItemStack resultStack = getResultItem();

        this.inventory.getStackInSlot(1).shrink(1);

        ResourceLocation raceID = getRaceID(this.inventory.getStackInSlot(1));

        Race race = RaceRegistry.REGISTRY.get().getValue(raceID);
        if (race != null) {
            Umapyoi.getLogger().info("Follow up");
            race.followUp(this.inventory.getStackInSlot(0));
        }
        // todo: increase uma soul status here (generic)

        // this.inventory.setStackInSlot(3, resultStack);
        for (int i = 2; i < 6 && !resultStack.isEmpty(); i++) {
            resultStack = this.insertItemToSlot(i, resultStack);
        }
        this.setChanged();
        return true;
    }

    public ItemStack getResultItem() {
        if (this.level == null) return ItemStack.EMPTY;
        ResourceLocation raceID = getRaceID(this.inventory.getStackInSlot(1));

        Race race = RaceRegistry.REGISTRY.get().getValue(raceID);
        ResourceLocation lootSpecify = new ResourceLocation(Umapyoi.MODID, "race/id/" + raceID.getPath());
        LootDataManager manager = Objects.requireNonNull(this.level.getServer()).getLootData();
        LootTable table = manager.getLootTable(raceID);
        if (table == LootTable.EMPTY) {
            Umapyoi.getLogger().debug("There doesn't exist an loot table for {}", lootSpecify);
            if (race == null) {
                Umapyoi.getLogger().error("No such race! {}", raceID);
                return ItemStack.EMPTY;
            }
            table = manager.getLootTable(new ResourceLocation(Umapyoi.MODID, "race/generic/race_" +
                    race.ranking.name().toLowerCase()));
        }

        LootParams lootParams = new LootParams.Builder((ServerLevel) this.level)
                .withParameter(LootContextParams.ORIGIN, this.worldPosition.getCenter())
                .create(LootContextParamSets.EMPTY);

        ObjectArrayList<ItemStack> returns = table.getRandomItems(lootParams);
        if (returns.isEmpty()) {
            Umapyoi.getLogger().error("Rolls Empty! {}", raceID);
            return ItemStack.EMPTY;
        }
        return returns.get(0);
    }

    public boolean fulfill() {
        ItemStack stackSoul = this.inventory.getStackInSlot(0);
        ItemStack stackRace = this.inventory.getStackInSlot(1);
        // sanity check
        if (!umaSoulConstraint(stackSoul)) return false;
        if (!raceConstraint(stackRace)) return false;
        if (this.level == null) return false;
        Race race = RaceRegistry.REGISTRY.get().getValue(getRaceID(stackRace));
        if (race == null) return false;
        return race.isAvailableToUmaSoul(stackSoul);
    }

    public NonNullList<ItemStack> getDroppableInventory() {
        NonNullList<ItemStack> drops = NonNullList.create();
        for (int i = 0; i < 6; ++i) {
            drops.add(inventory.getStackInSlot(i));
        }
        return drops;
    }

    @Nonnull
    @Override
    public Component getDisplayName() {
        return Component.translatable("container.umapyoi.race");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, @Nonnull Inventory inventory, @Nonnull Player player) {
        return new RaceContainer(i, inventory, this, this.tileData);
    }
}
