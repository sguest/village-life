package sguest.villagelife.item.crafting;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import sguest.villagelife.block.ModBlocks;

public class WoodcuttingRecipe extends SingleItemRecipe {
    public WoodcuttingRecipe(ResourceLocation id, String group, Ingredient ingredient, ItemStack result) {
        super(ModRecipes.WOODCUTTING_TYPE, ModRecipes.WOODCUTTING, id, group, ingredient, result);
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return this.ingredient.test(inv.getStackInSlot(0));
    }

    @Override
    public ItemStack getIcon() { return new ItemStack(ModBlocks.WOODCUTTER); }
}
