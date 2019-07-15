package sguest.villagelife.client.gui.screen.inventory;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import sguest.villagelife.inventory.container.WoodcutterContainer;
import sguest.villagelife.item.crafting.WoodcuttingRecipe;

import java.util.List;

public class WoodcutterScreen extends ContainerScreen<WoodcutterContainer> {
    private static final ResourceLocation menuTexture = new ResourceLocation("textures/gui/container/stonecutter.png"); //field_214146_k
    private float scrollPosition;   //field_214147_l
    private boolean hasScrollBar;   //field_214150_o
    private int firstDisplayedRecipeIndex; //field_214149_n

    public WoodcutterScreen(WoodcutterContainer container, PlayerInventory playerInventory, ITextComponent textComponent) {
        super(container, playerInventory, textComponent);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.renderBackground();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(menuTexture);
        int guiLeft = this.guiLeft;
        int guiTop = this.guiTop;
        this.blit(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
        int scrollPosRelative = (int)(41.0F * this.scrollPosition);
        this.blit(guiLeft + 119, guiTop + 15 + scrollPosRelative, 176 + (this.renderScrollBar() ? 0 : 12), 0, 12, 15);
        int recipeAreaTop = this.guiLeft + 52;
        int recipeAreaLeft = this.guiTop + 14;
        int lastDisplayedRecipeIndex = this.firstDisplayedRecipeIndex + 12;
        this.drawRecipeBackgrounds(mouseX, mouseY, recipeAreaTop, recipeAreaLeft, lastDisplayedRecipeIndex);
        this.drawRecipes(recipeAreaTop, recipeAreaLeft, lastDisplayedRecipeIndex);
    }

    //func_214141_a
    private void drawRecipeBackgrounds(int mouseX, int mouseY, int guiTop, int guiLeft, int lastDisplayedRecipeIndex) {
        for(int i = this.firstDisplayedRecipeIndex; i < lastDisplayedRecipeIndex && i < this.container.recipeCount(); ++i) {
            int relativeIndex = i - this.firstDisplayedRecipeIndex;
            int itemTop = guiTop + relativeIndex % 4 * 16;
            int column = relativeIndex / 4;
            int itemLeft = guiLeft + column * 18 + 2;
            int backgroundOffset = this.ySize;
            if (i == this.container.getSelectedIndex()) {
                backgroundOffset += 18;
            } else if (mouseX >= itemTop && mouseY >= itemLeft && mouseX < itemTop + 16 && mouseY < itemLeft + 18) {
                backgroundOffset += 36;
            }

            this.blit(itemTop, itemLeft - 1, 0, backgroundOffset, 16, 18);
        }
    }

    //func_214142_b
    private void drawRecipes(int guiTop, int guiLeft, int lastDisplayedRecipeIndex) {
        RenderHelper.enableGUIStandardItemLighting();
        List<WoodcuttingRecipe> recipeList = this.container.getRecipeList();

        for(int i = this.firstDisplayedRecipeIndex; i < lastDisplayedRecipeIndex && i < this.container.recipeCount(); ++i) {
            int relativeIndex = i - this.firstDisplayedRecipeIndex;
            int itemTop = guiTop + relativeIndex % 4 * 16;
            int column = relativeIndex / 4;
            int itemLeft = guiLeft + column * 18 + 2;
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(recipeList.get(i).getRecipeOutput(), itemTop, itemLeft);
        }

        RenderHelper.disableStandardItemLighting();
    }

    private boolean renderScrollBar() {   //func_214143_c
        return this.hasScrollBar && (this.container).recipeCount() > 12;
    }

}
