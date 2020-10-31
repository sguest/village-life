package sguest.villagelife.client.gui.screen.inventory;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import sguest.villagelife.inventory.container.WoodcutterContainer;
import sguest.villagelife.item.crafting.WoodcuttingRecipe;

public class WoodcutterScreen extends ContainerScreen<WoodcutterContainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("textures/gui/container/stonecutter.png");
    private static final int RECIPES_SHOWN = 12;
    
    private float sliderProgress;
    /** Is {@code true} if the player clicked on the scroll wheel in the GUI. */
    private boolean clickedOnSroll;
    /**
    * The index of the first recipe to display.
    * The number of recipes displayed at any time is 12 (4 recipes per row, and 3 rows). If the player scrolled down one
    * row, this value would be 4 (representing the index of the first slot on the second row).
    */
    private int recipeIndexOffset;
    private boolean hasItemsInInputSlot;
    
    public WoodcutterScreen(WoodcutterContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);
        container.setInventoryUpdateListener(this::onInventoryUpdate);
        --this.titleY;
    }
    
    private void onInventoryUpdate() {
        this.hasItemsInInputSlot = this.container.hasItemsinInputSlot();
        if (!this.hasItemsInInputSlot) {
            this.sliderProgress = 0.0F;
            this.recipeIndexOffset = 0;
        }
    }
    
    private boolean canScroll() {
        return this.hasItemsInInputSlot && this.container.getRecipeList().size() > 12;
    }
    
    @Override
    public void render(MatrixStack transform, int mouseX, int mouseY, float partialTicks)
    {
        super.render(transform, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(transform, mouseX, mouseY);
    }

    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        this.renderBackground(matrixStack);
        //RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        int guiLeft = this.guiLeft;
        int guiTop = this.guiTop;
        this.blit(matrixStack, guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
        int scrollY = (int)(41.0F * this.sliderProgress);
        this.blit(matrixStack, guiLeft + 119, guiTop + 15 + scrollY, 176 + (this.canScroll() ? 0 : 12), 0, 12, 15);
        int recipeAreaLeft = this.guiLeft + 52;
        int recipeAreaTop = this.guiTop + 14;
        int lastRecipeIndex = this.recipeIndexOffset + 12;
        this.drawRecipeBackgrounds(matrixStack, x, y, recipeAreaLeft, recipeAreaTop);
        this.drawRecipeItems(recipeAreaLeft, recipeAreaTop, lastRecipeIndex);
    }
    
    private void drawRecipeBackgrounds(MatrixStack matrixStack, int x, int y, int recipeLeft, int recipeTop) {
        for(int index = this.recipeIndexOffset; index < this.recipeIndexOffset + RECIPES_SHOWN && index < this.container.getRecipeList().size(); index++) {
            int firstRecipeIndex = index - this.recipeIndexOffset;
            int itemLeft = recipeLeft + firstRecipeIndex % 4 * 16;
            int columnIndex = firstRecipeIndex / 4;
            int itemTop = recipeTop + columnIndex * 18 + 2;
            int backgroundOffset = this.ySize;
            if (index == this.container.getSelectedRecipeIndex()) {
                backgroundOffset += 18;
            } else if (x >= itemLeft && y >= itemTop && x < itemLeft + 16 && y < itemTop + 18) {
                backgroundOffset += 36;
            }
            
            this.blit(matrixStack, itemLeft, itemTop - 1, 0, backgroundOffset, 16, 18);
        }
    }
    
    private void drawRecipeItems(int left, int top, int recipeIndexOffsetMax) {
        List<WoodcuttingRecipe> list = this.container.getRecipeList();

        for(int index = this.recipeIndexOffset; index < recipeIndexOffsetMax && index < list.size(); ++index) {
            int relativeIndex = index - this.recipeIndexOffset;
            int itemLeft = left + relativeIndex % 4 * 16;
            int columnIndex = relativeIndex / 4;
            int itemTop = top + columnIndex * 18 + 2;
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI(list.get(index).getRecipeOutput(), itemLeft, itemTop);
        }
    }
}
