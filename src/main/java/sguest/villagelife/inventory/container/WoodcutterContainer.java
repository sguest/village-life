package sguest.villagelife.inventory.container;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import sguest.villagelife.block.ModBlocks;
import sguest.villagelife.item.crafting.ModRecipes;
import sguest.villagelife.item.crafting.WoodcuttingRecipe;

public class WoodcutterContainer extends Container {
    private final IWorldPosCallable worldPosCallable;
    private final IntReferenceHolder selectedRecipe = IntReferenceHolder.single();
    private final World world;

    private long lastOnTake;

    private List<WoodcuttingRecipe> recipes = new ArrayList<>();
    private ItemStack inputItemStack = ItemStack.EMPTY;
    private Runnable inventoryUpdateListener = () -> {
    };

    final Slot inputInventorySlot;
    final Slot outputInventorySlot;

    public final IInventory inputInventory = new Inventory(1) {
        /**
        * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think
        * it hasn't changed and skip it.
        */
        public void markDirty() {
            super.markDirty();
            WoodcutterContainer.this.onCraftMatrixChanged(this);
            WoodcutterContainer.this.inventoryUpdateListener.run();
        }
    };
    private final CraftResultInventory outputInventory = new CraftResultInventory();

    public WoodcutterContainer(int windowId, PlayerInventory playerInventory) {
        this(windowId, playerInventory, IWorldPosCallable.DUMMY);
    }

    public WoodcutterContainer(int windowId, PlayerInventory playerInventory, final IWorldPosCallable worldPosCallable) {
        super(ModContainerTypes.WOODCUTTER.get(), windowId);
        
        this.worldPosCallable = worldPosCallable;
        this.world = playerInventory.player.world;
        this.inputInventorySlot = this.addSlot(new Slot(this.inputInventory, 0, 20, 33));
        this.outputInventorySlot = this.addSlot(new Slot(this.outputInventory, 1, 143, 33) {
            public boolean isItemValid(ItemStack itemStack) {
                return false;
            }

            public ItemStack onTake(PlayerEntity player, ItemStack itemStack) {
                itemStack.onCrafting(player.world, player, itemStack.getCount());
                WoodcutterContainer.this.outputInventory.onCrafting(player);
                ItemStack inputStack = WoodcutterContainer.this.inputInventorySlot.decrStackSize(1);
                if(!inputStack.isEmpty()) {
                    WoodcutterContainer.this.updateRecipeResultSlot();
                }

                worldPosCallable.consume((world, blockPos) -> {
                    long gameTime = world.getGameTime();
                    if(WoodcutterContainer.this.lastOnTake != gameTime) {
                        world.playSound((PlayerEntity)null, blockPos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        WoodcutterContainer.this.lastOnTake = gameTime;
                    }
                });
                return super.onTake(player, itemStack);
            }
        });
        
        for(int row = 0; row < 3; ++row) {
            for(int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
            }
        }
        
        for(int index = 0; index < 9; ++index) {
            this.addSlot(new Slot(playerInventory, index, 8 + index * 18, 142));
        }
        
        this.trackInt(this.selectedRecipe);
    }

    public boolean canInteractWith(PlayerEntity player) {
        return isWithinUsableDistance(this.worldPosCallable, player, ModBlocks.WOODCUTTER.get());
    }

    public void onCraftMatrixChanged(IInventory inventory) {
        ItemStack itemstack = this.inputInventorySlot.getStack();
        if (itemstack.getItem() != this.inputItemStack.getItem()) {
            this.inputItemStack = itemstack.copy();
            this.updateAvailableRecipes(inventory, itemstack);
        }
    }

    private void updateAvailableRecipes(IInventory inventory, ItemStack stack) {
        this.recipes.clear();
        this.selectedRecipe.set(-1);
        this.outputInventorySlot.putStack(ItemStack.EMPTY);
        if (!stack.isEmpty()) {
            this.recipes = this.world.getRecipeManager().getRecipes(ModRecipes.WOODCUTTING, inventory, this.world);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public int getSelectedRecipeIndex() {
        return this.selectedRecipe.get();
    }

    @OnlyIn(Dist.CLIENT)
    public void setInventoryUpdateListener(Runnable listener) {
        this.inventoryUpdateListener = listener;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean hasItemsinInputSlot() {
        return this.inputInventorySlot.getHasStack() && !this.recipes.isEmpty();
    }

    @OnlyIn(Dist.CLIENT)
    public List<WoodcuttingRecipe> getRecipeList() {
        return this.recipes;
    }

    public ContainerType<?> getType() {
        return ModContainerTypes.WOODCUTTER.get();
    }

    public boolean enchantItem(PlayerEntity playerIn, int recipeIndex) {
        if (isValidRecipeIndex(recipeIndex)) {
            this.selectedRecipe.set(recipeIndex);
            this.updateRecipeResultSlot();
        }

        return true;
    }

    private boolean isValidRecipeIndex(int recipeIndex) {
        return recipeIndex >= 0 && recipeIndex < this.recipes.size();
    }

    private void updateRecipeResultSlot() {
        if (!this.recipes.isEmpty() && this.isValidRecipeIndex(this.selectedRecipe.get())) {
            WoodcuttingRecipe woodcuttingRecipe = this.recipes.get(this.selectedRecipe.get());
            this.outputInventory.setRecipeUsed(woodcuttingRecipe);
            this.outputInventorySlot.putStack(woodcuttingRecipe.getCraftingResult(this.inputInventory));
        } else {
            this.outputInventorySlot.putStack(ItemStack.EMPTY);
        }
        this.detectAndSendChanges();
    }

    public boolean canMergeSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.outputInventory && super.canMergeSlot(stack, slot);
    }

    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack resultStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack inputStack = slot.getStack();
            Item item = inputStack.getItem();
            resultStack = inputStack.copy();
            if (index == 1) {
                item.onCreated(inputStack, player.world, player);
                if (!this.mergeItemStack(inputStack, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(inputStack, resultStack);
            } else if (index == 0) {
                if (!this.mergeItemStack(inputStack, 2, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.world.getRecipeManager().getRecipe(ModRecipes.WOODCUTTING, new Inventory(inputStack), this.world).isPresent()) {
                if (!this.mergeItemStack(inputStack, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 2 && index < 29) {
                if (!this.mergeItemStack(inputStack, 29, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 29 && index < 38 && !this.mergeItemStack(inputStack, 2, 29, false)) {
                return ItemStack.EMPTY;
            }

            if (inputStack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            }

            slot.onSlotChanged();
            if (inputStack.getCount() == resultStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, inputStack);
            this.detectAndSendChanges();
        }

        return resultStack;
    }

    public void onContainerClosed(PlayerEntity player) {
        super.onContainerClosed(player);
        this.outputInventory.removeStackFromSlot(1);
        this.worldPosCallable.consume((world, blockPos) -> {
            this.clearContainer(player, player.world, this.inputInventory);
        });
    }
}
