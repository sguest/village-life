package sguest.villagelife.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import sguest.villagelife.VillageLifeMod;
import sguest.villagelife.blocks.ModBlocks;

public class ModItems {
    public static final Item WOODCUTTER = new BlockItem(ModBlocks.WOODCUTTER, new FabricItemSettings().group(ItemGroup.DECORATIONS));

    public static void initialize()
    {
        Registry.register(Registry.ITEM, new Identifier(VillageLifeMod.MODID, "woodcutter"), WOODCUTTER);
    }
}
