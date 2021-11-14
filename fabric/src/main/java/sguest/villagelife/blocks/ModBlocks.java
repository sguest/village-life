package sguest.villagelife.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import sguest.villagelife.VillageLifeMod;

public class ModBlocks {
    public static final Block WOODCUTTER = new WoodcutterBlock(FabricBlockSettings.of(Material.WOOD).strength(2.5f).sounds(BlockSoundGroup.WOOD));

    public static void initialize() {
        Registry.register(Registry.BLOCK, Identifiers.WOODCUTTER, WOODCUTTER);
    }

    public class Identifiers {
        public static final Identifier WOODCUTTER = new Identifier(VillageLifeMod.MODID, "woodcutter");
    }
}
