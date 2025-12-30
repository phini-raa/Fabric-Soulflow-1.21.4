package net.phini.soulflow.block.entity.custom;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.server.world.ServerWorld;
import net.phini.soulflow.block.entity.ImplementedInventory;
import net.phini.soulflow.block.entity.ModBlockEntities;
import net.phini.soulflow.item.ModItems;
import net.phini.soulflow.recipe.AlchemyTableRecipe;
import net.phini.soulflow.recipe.AlchemyTableRecipeInput;
import net.phini.soulflow.recipe.ModRecipes;
import net.phini.soulflow.screen.custom.AlchemyTableScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class AlchemyTableBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
    private static final int INPUT_SLOT_1 = 0;
    private static final int INPUT_SLOT_2 = 1;
    private static final int INPUT_SLOT_3 = 2;
    private static final int OUTPUT_SLOT = 3;

    private int progress = 0;
    private int maxProgress = 20*30;


    @Override
    public int[] getAvailableSlots(Direction side) {


        if (side == Direction.DOWN) {
            // Bottom hopper extracts output only
            return new int[]{3};
        }

        return new int[]{0, 1, 2};
    }


    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction dir) {
        if (dir == Direction.DOWN) return false;

        if (slot == 1) {
            return stack.isOf(ModItems.HOLLOW_TOTEM);
        }

        if (slot == 2) {
            return stack.isOf(ModItems.SOUL);
        }

        if (slot == 0) {
            return !stack.isOf(ModItems.HOLLOW_TOTEM)
                    && !stack.isOf(ModItems.SOUL);
        }

        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        // Only allow extraction from the output slot via the bottom
        return dir == Direction.DOWN && slot == 3;
    }



    protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> progress;
                case 1 -> maxProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> progress = value;
                case 1 -> maxProgress = value;
            }
        }

        @Override
        public int size() {
            return 2;
        }
    };

    public AlchemyTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ALCHEMY_TABLE_BE, pos, state);
    }

    public BlockPos getScreenOpeningData(PlayerEntity player) {
        return this.pos;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.soulflow.alchemy_table");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new AlchemyTableScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt("alchemy_table.progress", progress);
        nbt.putInt("alchemy_table.max_progress", maxProgress);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
        progress = nbt.getInt("alchemy_table.progress");
        maxProgress = nbt.getInt("alchemy_table.max_progress");
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (hasRecipe()) {

            progress++;
            markDirty(world, pos, state);

            if (progress >= maxProgress) {
                craftItem();
                progress = 0;
            }
        } else {
            progress = 0;
        }
    }

    private boolean hasRecipe() {
        Optional<RecipeEntry<AlchemyTableRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return false;

        ItemStack output = recipe.get().value().getOutput();
        return canInsertItemIntoOutputSlot(output) && canInsertAmountIntoOutputSlot(output.getCount());
    }

    private void craftItem() {
        Optional<RecipeEntry<AlchemyTableRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return;
        world.playSound(null,pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS,1.0f,1.0f);
        ItemStack output = recipe.get().value().getOutput();
        decrementInputStacks();
        insertOutput(output);
    }

    private void decrementInputStacks() {
        removeStack(INPUT_SLOT_1, 1);
        removeStack(INPUT_SLOT_2, 1);
        removeStack(INPUT_SLOT_3, 1);
    }

    private void insertOutput(ItemStack output) {
        ItemStack currentOutput = getStack(OUTPUT_SLOT);
        if (currentOutput.isEmpty()) {
            setStack(OUTPUT_SLOT, output.copy());
        } else {
            currentOutput.increment(output.getCount());
            setStack(OUTPUT_SLOT, currentOutput);
        }
    }

    private Optional<RecipeEntry<AlchemyTableRecipe>> getCurrentRecipe() {
        if (world == null || world.isClient()) return Optional.empty();

        ServerRecipeManager.MatchGetter<CraftingRecipeInput, AlchemyTableRecipe> getter =
                ServerRecipeManager.createCachedMatchGetter(ModRecipes.ALCHEMY_TABLE_TYPE);

        AlchemyTableRecipeInput input = new AlchemyTableRecipeInput(
                inventory.get(INPUT_SLOT_1),
                inventory.get(INPUT_SLOT_2),
                inventory.get(INPUT_SLOT_3)
        );

        if (world == null || world.isClient()) return Optional.empty();

        // Get server recipe manager
        ServerRecipeManager manager = ((ServerWorld) world).getRecipeManager();

        // Iterate all recipes
        for (RecipeEntry<?> entry : manager.values()) {
            if (entry.value() instanceof AlchemyTableRecipe recipe) {
                if (input.matches(recipe)) {
                    @SuppressWarnings("unchecked")
                    RecipeEntry<AlchemyTableRecipe> matching = (RecipeEntry<AlchemyTableRecipe>) (Object) entry;
                    return Optional.of(matching);
                }
            }
        }

        return Optional.empty();

    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        ItemStack current = getStack(OUTPUT_SLOT);
        return current.isEmpty() || current.getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        ItemStack current = getStack(OUTPUT_SLOT);
        int maxCount = current.isEmpty() ? 64 : current.getMaxCount();
        return current.getCount() + count <= maxCount;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return this.pos;
    }

    private float rotation = 0;

    public float getRenderingRotation() {
        rotation += 0.5f;
        if(rotation >= 360) {
            rotation = 0;
        }
        return rotation;
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if(this.world != null) {
            this.world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    }


}
