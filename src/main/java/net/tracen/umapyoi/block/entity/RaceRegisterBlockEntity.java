package net.tracen.umapyoi.block.entity;

import cn.mcmod_mmf.mmlib.block.entity.SyncedBlockEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
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
import net.tracen.umapyoi.api.UmapyoiAPI;
import net.tracen.umapyoi.container.RaceContainer;
import net.tracen.umapyoi.inventory.UniversalIOItemHandler;
import net.tracen.umapyoi.item.ItemRegistry;
import net.tracen.umapyoi.registry.races.Race;
import net.tracen.umapyoi.registry.races.RaceRegistry;
import net.tracen.umapyoi.registry.umadata.UmaData;
import net.tracen.umapyoi.utils.Position;
import net.tracen.umapyoi.utils.RaceRanking;
import net.tracen.umapyoi.utils.UmaSoulUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import static net.tracen.umapyoi.item.UmaRaceTicketItem.getRaceID;

public class RaceRegisterBlockEntity extends SyncedBlockEntity implements MenuProvider {
    // No additional synchronized logic because provided by SyncedBlockEntity
    // Update by inventoryChanged (custom logic in SyncedBlockEntity, which is the super of this class)

    // public static final int MAX_RECIPE_TIME = 260; //13 seconds;

    private final ItemStackHandler inventory;
    private final LazyOptional<IItemHandler> IOHandler;
    private int recipeTime;
    private int maxRecipeTime = 0;
    protected final ContainerData tileData;

    @Nullable private Long winnerRenderSeed = null;
    private long safeGetWinnerRenderSeed() {
        if (winnerRenderSeed == null) {
            RandomSource randomSeq = this.level == null ? RandomSource.create(this.getBlockPos().asLong()) : this.level.random.fork();
            this.winnerRenderSeed = randomSeq.nextLong();
        }
        return this.winnerRenderSeed;
    }

    public RaceRegisterBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.RACE_REGISTER_BLOCK_ENTITY.get(), pos, state);
        this.inventory = createHandler();
        this.IOHandler = LazyOptional.of(() -> new UniversalIOItemHandler(inventory, 2));
        this.tileData = createIntArray();
        this.maxRecipeTime = 0;
    }

    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    protected static boolean umaSoulConstraint(ItemStack stack) {
        return stack.is(ItemRegistry.UMA_SOUL.get());
    }

    protected static boolean raceConstraint(ItemStack stack) {
        return stack.is(ItemRegistry.UMA_RACE_TICKET.get());
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
        winnerRenderSeed = compound.getLong("WinnerRenderSeed");
    }

    @Override
    public void saveAdditional(@Nonnull CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putInt("RecipeTime", recipeTime);
        compound.put("Inventory", inventory.serializeNBT());
        compound.putLong("WinnerRenderSeed", this.safeGetWinnerRenderSeed());
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

    public static final int DATA_SLOT_SIZE = 9;

    private ContainerData createIntArray() {
        return new ContainerData() {
            @Override
            public int get(int index) {
                return switch(index) {
                    case 0 -> RaceRegisterBlockEntity.this.recipeTime;
                    case 1 -> RaceRegisterBlockEntity.this.maxRecipeTime;
                    case 2 -> RaceRegisterBlockEntity.this.shallSoulWin() ? 1 : 0;
                    case 3 -> (int) (RaceRegisterBlockEntity.this.safeGetWinnerRenderSeed());
                    case 4 -> (int) (RaceRegisterBlockEntity.this.safeGetWinnerRenderSeed() >> 32);
                    case 5 -> RaceRegisterBlockEntity.this.getRaceVariant();
                    case 6 -> RaceRegisterBlockEntity.this.getRenderScaleFactor();
                    // case 7 -> (int) (Double.doubleToLongBits(RaceRegisterBlockEntity.this.getRenderScaleFactor()) >> 32);
                    case 8 -> RaceRegisterBlockEntity.this.getSoulTactic();
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                if (index == 0) RaceRegisterBlockEntity.this.recipeTime = value;
                if (index == 1) RaceRegisterBlockEntity.this.maxRecipeTime = value;
                if (index == 3) RaceRegisterBlockEntity.this.winnerRenderSeed = (RaceRegisterBlockEntity.this.safeGetWinnerRenderSeed() & 0xffffffff00000000L) | value;
                if (index == 4) RaceRegisterBlockEntity.this.winnerRenderSeed = (RaceRegisterBlockEntity.this.safeGetWinnerRenderSeed() & 0xffffffffL) | ((long) value << 32);
            }

            @Override
            public int getCount() {
                return DATA_SLOT_SIZE;
            }
        };
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState,
                                  RaceRegisterBlockEntity raceRegisterBlockEntity) {
        if (level.isClientSide()) return;
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
        if (level == null) {
            this.maxRecipeTime = 0;
            return false;
        }

        if (recipeTime == 0) {
            // other sanity check, no further process during non-empty output area
            int cnt = 0;
            for (int i = 2; i < 6; i++) {
                if (!this.inventory.getStackInSlot(i).isEmpty()) cnt++;
            }
            if (cnt == 4) {
                this.maxRecipeTime = 0;
                return false;
            }
        }

        ItemStack stack = this.inventory.getStackInSlot(1);
        if (stack == ItemStack.EMPTY) { // sanity check
            this.maxRecipeTime = 0;
            return false;
        }

        ResourceLocation raceID = getRaceID(this.inventory.getStackInSlot(1));
        Race race = UmapyoiAPI.getRaceRegistry(this.level).get(raceID);
        if (race == null) {
            this.maxRecipeTime = 0;
            return false;
        }

        this.maxRecipeTime = race.length(this.inventory.getStackInSlot(0)) * 3 / 20;

        recipeTime++;

        if (recipeTime < this.maxRecipeTime) return false;

        recipeTime = 0; // done logic

        stack.shrink(1);
        ItemStack resultStack = getResultItem(raceID);
        long maxSize = Math.round(resultStack.getCount() * race.getUmaFactorCorrection(this.inventory.getStackInSlot(0), this.level));
        ArrayList<ItemStack> listItems = new ArrayList<>();
        while (maxSize >= 0 && listItems.size() < 4) {
            int cnt = Math.toIntExact(Math.min(resultStack.getMaxStackSize(), maxSize));
            listItems.add(resultStack.copyWithCount(cnt));
            maxSize -= cnt;
        }

        Umapyoi.getLogger().info("Follow up");
        race.followUp(this.inventory.getStackInSlot(0), this.level);
        // todo: increase uma soul status here (generic)

        // this.inventory.setStackInSlot(3, resultStack);
        listItems.forEach((fillStack) -> {
            for (int i = 2; i < 6 && !fillStack.isEmpty(); i++) {
                fillStack = this.insertItemToSlot(i, fillStack);
            }
        });
        this.winnerRenderSeed = this.level.random.fork().nextLong();
        this.setChanged();
        return true;
    }

    public ItemStack getResultItem(ResourceLocation raceID) {
        if (this.level == null) return ItemStack.EMPTY;

        Race race = UmapyoiAPI.getRaceRegistry(this.level).get(raceID);
        if (race != null) {
            Umapyoi.getLogger().debug("Run {} with following properties: Distance={}, Surface={}", raceID, race.distance(this.inventory.getStackInSlot(0)), race.surface(this.inventory.getStackInSlot(0)));
        }
        ResourceLocation lootSpecify = new ResourceLocation(raceID.getNamespace(), "race/id/" + raceID.getPath());
        LootDataManager manager = Objects.requireNonNull(this.level.getServer()).getLootData();
        LootTable table = manager.getLootTable(lootSpecify);
        if (table == LootTable.EMPTY) {
            Umapyoi.getLogger().debug("There doesn't exist a loot table for {}, falling back to generic rank + field table", raceID);
            if (race == null) {
                Umapyoi.getLogger().error("No such race! {}", raceID);
                return ItemStack.EMPTY;
            }
            ItemStack stackSoul = this.inventory.getStackInSlot(0);
            RaceRanking rank = race.ranking;
            if (stackSoul.equals(ItemStack.EMPTY)) {
                Umapyoi.getLogger().error("Umasoul is no longer present.");
                table = manager.getLootTable(new ResourceLocation(Umapyoi.MODID, "race/generic/race_" +
                        rank.name().toLowerCase()));
            } else {
                ResourceLocation field = race.field(this.level, stackSoul).id();
                table = manager.getLootTable(new ResourceLocation(field.getNamespace(), "race/generic/field/race_"
                        + field.getPath() + "_" + rank.name().toLowerCase()));
                if (table == LootTable.EMPTY) {
                    Umapyoi.getLogger().debug("There doesn't exist a loot table for {} {}, falling back to generic table", field, rank);
                    table = manager.getLootTable(new ResourceLocation(Umapyoi.MODID, "race/generic/race_" +
                            rank.name().toLowerCase()));
                }
            }
        }

        LootParams lootParams = new LootParams.Builder((ServerLevel) this.level)
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
        Race race = UmapyoiAPI.getRaceRegistry(this.level).get(getRaceID(stackRace));
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

    // Render-helper function
    public boolean shallSoulWin() {
        ItemStack stackSoul = this.inventory.getStackInSlot(0);
        if (stackSoul.isEmpty()) return false;
        ItemStack stackRace = this.inventory.getStackInSlot(1);
        if (stackRace.isEmpty()) return false;
        if (this.level == null) return false;
        Race race = UmapyoiAPI.getRaceRegistry(this.level).get(getRaceID(stackRace));
        if (race == null) return false;
        return race.isPassed(stackSoul, this.level);
    }

    public int getRaceVariant() {
        ItemStack stackRace = this.inventory.getStackInSlot(1);
        if (stackRace.isEmpty()) return -1;
        Level level = Optional.ofNullable(this.level).orElse(Minecraft.getInstance().level);
        if (level == null) return -1;
        Race race = UmapyoiAPI.getRaceRegistry(level).get(getRaceID(stackRace));
        if (race == null) return -1;
        if (race.texturePredicateOverride != null) {
            return switch (race.texturePredicateOverride) {
                case RaceRegistry.PREDICATE_CHAMPIONS -> -4;
                default -> -1;
            };
        }
        return race.ranking.ordinal();
    }

    public double getRenderScaleFactorInDouble() {
        ItemStack stackSoul = this.inventory.getStackInSlot(0);
        if (stackSoul.isEmpty()) return 1d;
        ItemStack stackRace = this.inventory.getStackInSlot(1);
        if (stackRace.isEmpty()) return 1d;
        if (this.level == null) return 1d;
        Race race = UmapyoiAPI.getRaceRegistry(this.level).get(getRaceID(stackRace));
        if (race == null) return 1d;
        double retValue = race.offScalar(stackSoul, this.level);
        Umapyoi.getLogger().debug("Render scale = {}", retValue);
        return retValue;
    }

    public int getRenderScaleFactor() {
        double factor = this.getRenderScaleFactorInDouble();
        double enlargedFactor = factor * ((1L << 32) - 1);
        long valueInLong = Math.min((long) enlargedFactor, 4294967295L);
        return (int) (valueInLong & 0xffffffffL);
    }

    public int getSoulTactic() {
        ItemStack stackSoul = this.inventory.getStackInSlot(0);
        if (stackSoul.isEmpty()) return Position.FRONT_RUNNER.ordinal();
        Level world = this.level == null ? Minecraft.getInstance().level : this.level;
        if (world == null) return Position.FRONT_RUNNER.ordinal();
        ResourceLocation nameLoc = UmaSoulUtils.getName(stackSoul);
        UmaData umaData = UmapyoiAPI.getUmaDataRegistry(world).getOptional(nameLoc).orElseGet(() -> {
            Umapyoi.getLogger().info("Warning: {} doesn't exist.", nameLoc);
            return UmaData.DEFAULT_UMA;
        });
        Position umaPosition = umaData.position();
        return umaPosition.ordinal();
    }
}
