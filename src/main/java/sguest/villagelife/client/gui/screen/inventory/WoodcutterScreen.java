package sguest.villagelife.client.gui.screen.inventory;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import sguest.villagelife.inventory.container.WoodcutterContainer;
import sguest.villagelife.item.crafting.WoodcuttingRecipe;

import java.util.List;

public class WoodcutterScreen extends ContainerScreen<WoodcutterContainer> {
    private static final ResourceLocation menuTexture = new ResourceLocation("textures/gui/container/stonecutter.png"); //field_214146_k
    private float scrollPosition;   //field_214147_l
    private boolean isScrolling; //field_214148_m
    private int firstDisplayedRecipeIndex; //field_214149_n
    private boolean hasRecipes;   //field_214150_o

    public WoodcutterScreen(WoodcutterContainer container, PlayerInventory playerInventory, ITextComponent textComponent) {
        super(container, playerInventory, textComponent);
        container.setScreenInitializer(this::initRecipes);
    }

    @Override
    public void render(int mouseX, int mouseY, float var3) {
        super.render(mouseX, mouseY, var3);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString(this.title.getFormattedText(), 8.0F, 4.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 94), 4210752);
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
        this.blit(guiLeft + 119, guiTop + 15 + scrollPosRelative, 176 + (this.hasScrollBar() ? 0 : 12), 0, 12, 15);
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

    public boolean mouseClicked(double mouseX, double mouseY, int keyCode) {
        this.isScrolling = false;
        if (this.hasRecipes) {
            int leftBound = this.guiLeft + 52;
            int topBound = this.guiTop + 14;
            int lastDisplayedRecipeIndex = this.firstDisplayedRecipeIndex + 12;

            for(int i = this.firstDisplayedRecipeIndex; i < lastDisplayedRecipeIndex; ++i) {
                int relativeIndex = i - this.firstDisplayedRecipeIndex;
                double deltaLeft = mouseX - (double)(leftBound + relativeIndex % 4 * 16);
                double deltaTop = mouseY - (double)(topBound + relativeIndex / 4 * 18);
                if (deltaLeft >= 0.0D && deltaTop >= 0.0D && deltaLeft < 16.0D && deltaTop < 18.0D && this.container.enchantItem(this.minecraft.player, i)) {
                    Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                    this.minecraft.playerController.sendEnchantPacket(this.container.windowId, i);
                    return true;
                }
            }

            leftBound = this.guiLeft + 119;
            topBound = this.guiTop + 9;
            if (mouseX >= (double)leftBound && mouseX < (double)(leftBound + 12) && mouseY >= (double)topBound && mouseY < (double)(topBound + 54)) {
                this.isScrolling = true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, keyCode);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double var4, double var5) {
        if (this.isScrolling && this.hasScrollBar()) {
            int scrollBarTop = this.guiTop + 14;
            int scrollBarBottom = scrollBarTop + 54;
            this.scrollPosition = ((float)mouseY - (float)scrollBarTop - 7.5F) / ((float)(scrollBarBottom - scrollBarTop) - 15.0F);
            this.scrollPosition = MathHelper.clamp(this.scrollPosition, 0.0F, 1.0F);
            this.firstDisplayedRecipeIndex = (int)((double)(this.scrollPosition * (float)this.recipeRows()) + 0.5D) * 4;
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, var4, var5);
        }
    }

    @Override
    public boolean mouseScrolled(double var1, double var2, double scrollDistance) {
        if (this.hasScrollBar()) {
            int recipeRows = this.recipeRows();
            this.scrollPosition = (float)((double)this.scrollPosition - scrollDistance / (double)recipeRows);
            this.scrollPosition = MathHelper.clamp(this.scrollPosition, 0.0F, 1.0F);
            this.firstDisplayedRecipeIndex = (int)((double)(this.scrollPosition * (float)recipeRows) + 0.5D) * 4;
        }

        return true;
    }
    private boolean hasScrollBar() {   //func_214143_c
        return this.hasRecipes && (this.container).recipeCount() > 12;
    }

    //func_214144_b
    protected int recipeRows() {
        return (this.container.recipeCount() + 4 - 1) / 4 - 3;
    }

    private void initRecipes() {
        this.hasRecipes = this.container.hasRecipes();
        if (!this.hasRecipes) {
            this.scrollPosition = 0.0F;
            this.firstDisplayedRecipeIndex = 0;
        }
    }
}
