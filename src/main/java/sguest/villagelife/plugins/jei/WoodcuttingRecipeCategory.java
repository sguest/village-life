package sguest.villagelife.plugins.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sguest.villagelife.block.ModBlocks;
import sguest.villagelife.item.crafting.WoodcuttingRecipe;

public class WoodcuttingRecipeCategory implements IRecipeCategory<WoodcuttingRecipe> {
    private static final ResourceLocation JEI_VANILLA_GUI_TEXTURE = new ResourceLocation("jei", "textures/gui/gui_vanilla.png");

    private final String title;
    private final IDrawable icon;
    private final IDrawable background;

    public WoodcuttingRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(JEI_VANILLA_GUI_TEXTURE, 0, 220, 82, 34);
        title = I18n.format("villagelife.jei.category.woodcutting");
        icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.WOODCUTTER.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return VillageLifeJeiPlugin.WOODCUTTING;
    }

    @Override
    public Class<? extends WoodcuttingRecipe> getRecipeClass() {
        return WoodcuttingRecipe.class;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(WoodcuttingRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());

    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, WoodcuttingRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
        stacks.init(0, true, 0, 8);
        stacks.init(1, false, 60, 8);

        stacks.set(ingredients);
    }
}
