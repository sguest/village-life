package sguest.villagelife.resources;

import net.devtech.arrp.json.tags.JTag;
import net.minecraft.util.Identifier;
import sguest.villagelife.blocks.ModBlocks;

public class ModTags {
    public static void initialize() {
        ModResourcePack.RESOURCE_PACK.addTag(new Identifier("c", "items/iron_ingots"),
            new JTag().add(new Identifier("minecraft", "iron_ingot")));
        ModResourcePack.RESOURCE_PACK.addTag(new Identifier("minecraft", "blocks/mineable/axe"),
            new JTag().add(ModBlocks.Identifiers.WOODCUTTER));
    }
}
