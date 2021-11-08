package sguest.villagelife.client;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import sguest.villagelife.blocks.ModBlocks;

public class ModRenderLayers {
    public static void initialize() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WOODCUTTER, RenderLayer.getCutout());
    }
}
