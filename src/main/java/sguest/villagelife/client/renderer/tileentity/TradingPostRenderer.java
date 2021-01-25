package sguest.villagelife.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import sguest.villagelife.block.TradingPostBlock;
import sguest.villagelife.tileentity.ModTileEntities;
import sguest.villagelife.tileentity.TradingPostTileEntity;

public class TradingPostRenderer extends TileEntityRenderer<TradingPostTileEntity> {

    public TradingPostRenderer(TileEntityRendererDispatcher rendererDispatcher) {
        super(rendererDispatcher);
    }

    @Override
    public void render(TradingPostTileEntity tradingPost, float partialTicks, MatrixStack matrixStack,
            IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {

        matrixStack.push();
        ItemStack displayedItem = tradingPost.getDisplayedItem();
        if(!displayedItem.isEmpty()) {
            Minecraft minecraft = Minecraft.getInstance();
            if(minecraft != null) {
                Direction facing = tradingPost.getBlockState().get(TradingPostBlock.FACING);
                ItemRenderer itemRenderer = minecraft.getItemRenderer();
                matrixStack.translate(0.5D, 0.5D, 0.5D);
                matrixStack.scale(0.5F, 0.5F, 0.5F);
                Quaternion rotation = facing.getOpposite().getRotation();
                rotation.multiply(Vector3f.XP.rotationDegrees(-90.0F));
                matrixStack.rotate(rotation);
                matrixStack.translate(0, 0, 0.65D);
                itemRenderer.renderItem(displayedItem, ItemCameraTransforms.TransformType.FIXED, combinedLight, OverlayTexture.NO_OVERLAY, matrixStack, buffer);
            }
        }
        matrixStack.pop();
    }

    public static void register() {
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.TRADING_POST.get(), TradingPostRenderer::new);
    }
}
