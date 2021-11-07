package sguest.villagelife.item.crafting;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import sguest.villagelife.block.ModBlocks;

public class WoodcuttingRecipe extends SingleItemRecipe {
    public static final String RECIPE_ID = "woodcutting";

    public WoodcuttingRecipe(ResourceLocation id, String group, Ingredient ingredient, ItemStack result) {
        super(ModRecipes.WOODCUTTING, ModRecipeSerializers.WOODCUTTING.get(), id, group, ingredient, result);
    }

    /**
    * Used to check if a recipe matches current crafting inventory
    */
    public boolean matches(IInventory inv, World worldIn) {
        return this.ingredient.test(inv.getStackInSlot(0));
    }

    public ItemStack getIcon() {
        return new ItemStack(ModBlocks.WOODCUTTER.get());
    }

    public IRecipeSerializer<WoodcuttingRecipe> getSerializer() {
        return new WoodcuttingRecipe.Serializer<WoodcuttingRecipe>(WoodcuttingRecipe::new);
    }
}
