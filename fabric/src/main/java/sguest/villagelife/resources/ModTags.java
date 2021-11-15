package sguest.villagelife.resources;

import net.devtech.arrp.json.tags.JTag;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import sguest.villagelife.blocks.ModBlocks;

public class ModTags {
    public static void initialize() {
        ModResourcePack.RESOURCE_PACK.addTag(new Identifier("c", "items/iron_ingots"),
            new JTag().add(new Identifier("minecraft", "iron_ingot")));

        addMineable(ModBlocks.WOODCUTTER, "axe");
    }

    private static void addMineable(Block block, String mineType) {
        ModResourcePack.RESOURCE_PACK.addTag(new Identifier("minecraft", "blocks/mineable/axe"),
            new JTag().add(Registry.BLOCK.getId(block)));
    }
}
