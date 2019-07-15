package sguest.villagelife.inventory.container;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import sguest.villagelife.block.ModBlocks;
import sguest.villagelife.item.crafting.WoodcuttingRecipe;

import java.util.List;

public class WoodcutterContainer extends Container {
    private final IWorldPosCallable worldPos;   //field_217088_g
    private final IntReferenceHolder selectedIndex;  //field_217089_h
    private List<WoodcuttingRecipe> recipeList; //field_217091_j
    final Slot inputSlot;   //field_217085_d
    private Runnable field_217094_m;
    public final IInventory inputInventory; //field_217087_f

    public WoodcutterContainer(int id, PlayerInventory inventory) {
        this(id, inventory, IWorldPosCallable.DUMMY);
    }

    public WoodcutterContainer(int id, PlayerInventory inventory, final IWorldPosCallable worldPos) {
        super(ModContainerType.WOODCUTTER, id);
        this.selectedIndex = IntReferenceHolder.single();
        this.recipeList = Lists.newArrayList();

        this.field_217094_m = () -> {
        };
        this.inputInventory = new Inventory(1) {
            public void markDirty() {
                super.markDirty();
                WoodcutterContainer.this.onCraftMatrixChanged(this);
                WoodcutterContainer.this.field_217094_m.run();
            }
        };

        this.worldPos = worldPos;
        this.inputSlot = this.addSlot(new Slot(this.inputInventory, 0, 20, 33));

        for(int k = 0; k < 3; ++k) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + k * 9 + 9, 8 + j * 18, 84 + k * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }
    }

    @OnlyIn(Dist.CLIENT)    //func_217076_f
    public List<WoodcuttingRecipe> getRecipeList() {
        return this.recipeList;
    }

    @OnlyIn(Dist.CLIENT)
    public int getSelectedIndex() { //func_217073_e
        return this.selectedIndex.get();
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
}
