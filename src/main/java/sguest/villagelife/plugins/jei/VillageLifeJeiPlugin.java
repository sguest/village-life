package sguest.villagelife.plugins.jei;

import java.util.Collection;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import sguest.villagelife.VillageLife;
import sguest.villagelife.block.ModBlocks;
import sguest.villagelife.item.crafting.ModRecipes;

@JeiPlugin
public class VillageLifeJeiPlugin implements IModPlugin {
    public static final ResourceLocation WOODCUTTING = new ResourceLocation(VillageLife.MOD_ID, "woodcutting");

    private static ClientWorld world;

    private static ClientWorld getWorld() {
        if(world == null) {
            Minecraft mc = Minecraft.getInstance();
            world = mc.world;
        }
        return world;
    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(VillageLife.MOD_ID, "jei");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registration.addRecipeCategories(
            new WoodcuttingRecipeCategory(guiHelper)
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(getRecipes(ModRecipes.WOODCUTTING), WOODCUTTING);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.WOODCUTTER.get()), WOODCUTTING);
    }

    private <C extends IInventory, T extends IRecipe<C>> Collection<IRecipe<C>> getRecipes(IRecipeType<T> recipeType) {
        RecipeManager recipeManager = getWorld().getRecipeManager();
        return recipeManager.getRecipes(recipeType).values();
    }
}
