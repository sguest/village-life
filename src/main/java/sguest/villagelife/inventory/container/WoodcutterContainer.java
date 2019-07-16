package sguest.villagelife.inventory.container;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
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
import net.minecraft.item.Items;
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

import java.util.List;

public class WoodcutterContainer extends Container {
    static final ImmutableList<Item> validInputs;
    private final IWorldPosCallable worldPos;   //field_217088_g
    private final IntReferenceHolder selectedIndex;  //field_217089_h
    private final World world;  //field_217090_i
    private List<WoodcuttingRecipe> recipeList; //field_217091_j
    private ItemStack inputItemStack; //field_217092_k
    private long lastSoundTime; //field_217093_l
    final Slot inputSlot;   //field_217085_d
    final Slot outputSlot;  //field_217086_e
    private Runnable screenInitializer; //field_217094_m
    public final IInventory inputInventory; //field_217087_f
    private final CraftResultInventory outputInventory; //field_217095_n

    public WoodcutterContainer(int id, PlayerInventory inventory) {
        this(id, inventory, IWorldPosCallable.DUMMY);
    }

    public WoodcutterContainer(int id, PlayerInventory inventory, final IWorldPosCallable worldPos) {
        super(ModContainerType.WOODCUTTER, id);
        this.selectedIndex = IntReferenceHolder.single();
        this.recipeList = Lists.newArrayList();
        this.inputItemStack = ItemStack.EMPTY;
        this.screenInitializer = () -> {
        };
        this.inputInventory = new Inventory(1) {
            @Override
            public void markDirty() {
                super.markDirty();
                WoodcutterContainer.this.onCraftMatrixChanged(this);
                WoodcutterContainer.this.screenInitializer.run();
            }
        };
        this.outputInventory = new CraftResultInventory();
        this.worldPos = worldPos;
        this.world = inventory.player.world;
        this.inputSlot = this.addSlot(new Slot(this.inputInventory, 0, 20, 33));
        this.outputSlot = this.addSlot(new Slot(this.outputInventory, 1, 143, 33) {
            @Override
            public boolean isItemValid(ItemStack stack) { return false; }

            @Override
            public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
                ItemStack itemStack = WoodcutterContainer.this.inputSlot.decrStackSize(1);
                if(!itemStack.isEmpty()) {
                    WoodcutterContainer.this.selectRecipe();
                }

                stack.getItem().onCreated(stack, thePlayer.world, thePlayer);
                worldPos.consume((world, blockPos) -> {
                    long time = world.getGameTime();
                    if (WoodcutterContainer.this.lastSoundTime != time) {
                        world.playSound(null, blockPos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        WoodcutterContainer.this.lastSoundTime = time;
                    }
                });
                return super.onTake(thePlayer, stack);
            }
        });

        for(int k = 0; k < 3; ++k) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + k * 9 + 9, 8 + j * 18, 84 + k * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }

        this.func_216958_a(this.selectedIndex);
    }

    @OnlyIn(Dist.CLIENT)
    public int getSelectedIndex() { //func_217073_e
        return this.selectedIndex.get();
    }

    @OnlyIn(Dist.CLIENT)    //func_217076_f
    public List<WoodcuttingRecipe> getRecipeList() {
        return this.recipeList;
    }

    @OnlyIn(Dist.CLIENT)
    public int recipeCount() {  //func_217075_g
        return this.recipeList.size();
    }

    @OnlyIn(Dist.CLIENT)
    public boolean hasRecipes() {   //func_217083_h
        return this.inputSlot.getHasStack() && !this.recipeList.isEmpty();
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(this.worldPos, playerIn, ModBlocks.WOODCUTTER);
    }

    @Override
    public boolean enchantItem(PlayerEntity playerIn, int id) {
        if (id >= 0 && id < this.recipeList.size()) {
            this.selectedIndex.set(id);
            this.selectRecipe();
        }
        return true;
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn) {
        ItemStack itemStack = this.inputSlot.getStack();
        if (itemStack.getItem() != this.inputItemStack.getItem()) {
            this.inputItemStack = itemStack.copy();
            this.handleInputChanged(inventoryIn, itemStack);
        }
    }

    //func_217074_a
    private void handleInputChanged(IInventory inventoryIn, ItemStack itemStack) {
        this.recipeList.clear();
        this.selectedIndex.set(-1);
        this.outputSlot.putStack(ItemStack.EMPTY);
        if (!itemStack.isEmpty()) {
            this.recipeList = this.world.getRecipeManager().getRecipes(ModRecipes.WOODCUTTING_TYPE, inventoryIn, this.world);
        }

    }

    private void selectRecipe() {   //func_217082_i
        if (!this.recipeList.isEmpty()) {
            WoodcuttingRecipe woodcuttingRecipe = this.recipeList.get(this.selectedIndex.get());
            this.outputSlot.putStack(woodcuttingRecipe.getCraftingResult(this.inputInventory));
        } else {
            this.outputSlot.putStack(ItemStack.EMPTY);
        }

        this.detectAndSendChanges();
    }

    @Override
    public ContainerType<?> getType() { return ModContainerType.WOODCUTTER; }

    @OnlyIn(Dist.CLIENT)
    public void setScreenInitializer(Runnable screenInitializer) {
        this.screenInitializer = screenInitializer;
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
        return false;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemStackOutput = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack = slot.getStack();
            Item item = itemStack.getItem();
            itemStackOutput = itemStack.copy();
            if (index == 1) {
                item.onCreated(itemStack, playerIn.world, playerIn);
                if (!this.mergeItemStack(itemStack, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemStack, itemStackOutput);
            } else if (index == 0) {
                if (!this.mergeItemStack(itemStack, 2, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (validInputs.contains(item)) {
                if (!this.mergeItemStack(itemStack, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 2 && index < 29) {
                if (!this.mergeItemStack(itemStack, 29, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 29 && index < 38 && !this.mergeItemStack(itemStack, 2, 29, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            }

            slot.onSlotChanged();
            if (itemStack.getCount() == itemStackOutput.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemStack);
            this.detectAndSendChanges();
        }

        return itemStackOutput;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        this.outputInventory.removeStackFromSlot(1);
        this.worldPos.consume((world, blockPos) -> {
            this.clearContainer(playerIn, playerIn.world, this.inputInventory);
        });
    }

    static {
        validInputs = ImmutableList.of(Items.OAK_LOG, Items. OAK_WOOD, Items.STRIPPED_OAK_LOG, Items.STRIPPED_OAK_WOOD, Items.OAK_PLANKS);
    }
}